package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Income;
import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.IncomeSourceRepository;
import org.expensetracker.service.mapper.IncomeMapper;
import org.expensetracker.service.model.IncomeDto;

import java.util.List;
import java.util.stream.Collectors;

public class IncomeMapperImpl implements IncomeMapper {
    private final AccountRepository accountRepository;
    private final IncomeSourceRepository incomeSourceRepository;

    public IncomeMapperImpl(AccountRepository accountRepository, IncomeSourceRepository incomeSourceRepository) {
        this.accountRepository = accountRepository;
        this.incomeSourceRepository = incomeSourceRepository;
    }

    @Override
    public IncomeDto toDto(Income entity) {
        if (entity == null) {
            return null;
        }
        final IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(entity.getAmount());
        incomeDto.setDate(entity.getDate());
        incomeDto.setAccountId(entity.getAccount().getId());
        incomeDto.setIncomeSourceId(entity.getIncomeSource().getId());

        return incomeDto;
    }

    @Override
    public Income toEntity(IncomeDto incomeDto) {
        if (incomeDto == null) {
            return null;
        }
        final Income income = new Income();
        income.setAmount(incomeDto.getAmount());
        income.setDate(incomeDto.getDate());

        if (incomeDto.getIncomeSourceId() != null) {
            IncomeSource incomeSource = incomeSourceRepository.findById(incomeDto.getIncomeSourceId())
                    .orElseThrow(() -> new RuntimeException("Income source not found"));
            income.setIncomeSource(incomeSource);
        }

        if (incomeDto.getAccountId() != null) {
            Account account = accountRepository.findById(incomeDto.getAccountId()).orElseThrow(() -> new RuntimeException("Account not found"));
            income.setAccount(account);
        }
        return income;
    }

    @Override
    public List<IncomeDto> toDto(List<Income> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Income> toEntity(List<IncomeDto> incomeDtoList) {
        if (incomeDtoList == null) {
            return null;
        }
        return incomeDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
