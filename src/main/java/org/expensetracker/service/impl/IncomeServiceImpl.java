package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Income;
import org.expensetracker.database.repository.IncomeRepository;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.IncomeService;
import org.expensetracker.service.mapper.IncomeMapper;
import org.expensetracker.service.model.IncomeDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {
    private final IncomeRepository repository;
    private final IncomeMapper mapper;
    private final AccountService accountService;

    public IncomeServiceImpl(IncomeRepository repository, IncomeMapper mapper, AccountService accountService) {
        this.repository = repository;
        this.mapper = mapper;
        this.accountService = accountService;
    }

    @Override
    public List<IncomeDto> getAll() {
        Optional<List<Income>> optionalIncomes = repository.findAll();
        List<Income> incomes = optionalIncomes
                .orElseThrow(() -> new RuntimeException("No incomes found"));
        return mapper.toDto(incomes);
    }

    @Override
    public IncomeDto getById(Long id) {
        Optional<Income> optionalIncome = repository.findById(id);
        Income income = optionalIncome
                .orElseThrow(() -> new RuntimeException("No income found"));
        return mapper.toDto(income);
    }

    public List<IncomeDto> getAllByAccountId(Long id) {
        Optional<List<Income>> optionalIncomeDtoList = repository.findByAccountId(id);
        List<Income> incomeList = optionalIncomeDtoList
                .orElseThrow(() -> new RuntimeException("No incomes found for selected account"));
        return mapper.toDto(incomeList);
    }

    @Override
    public IncomeDto add(IncomeDto incomeDto) {
        if (incomeDto == null) {
            return null;
        }
        if (incomeDto.getDate() == null) {
            incomeDto.setDate(LocalDate.now());
        }
        Income income = mapper.toEntity(incomeDto);
        Income savedIncome = repository.add(income)
                .orElseThrow(() -> new RuntimeException("Income not added"));

        accountService.updateBalance(incomeDto.getAccountId(), incomeDto.getAmount());

        return mapper.toDto(savedIncome);
    }

    @Override
    public IncomeDto updateById(Long id, IncomeDto incomeDto) {
        if (incomeDto == null) {
            return null;
        }
        if (incomeDto.getDate() == null) {
            incomeDto.setDate(LocalDate.now());
        }

        Income oldIncome = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No income found"));

        Income newIncome = repository.updateById(id, mapper.toEntity(incomeDto))
                .orElseThrow(() -> new RuntimeException("Income not updated"));

        BigDecimal amountDifference = newIncome.getAmount().subtract(oldIncome.getAmount());

        accountService.updateBalance(incomeDto.getAccountId(), amountDifference);


        return mapper.toDto(newIncome);
    }

    @Override
    public void deleteById(Long id) {
        Income income = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No income found"));

        repository.deleteById(id);

        accountService.updateBalance(income.getAccount().getId(), income.getAmount().negate());
    }
}
