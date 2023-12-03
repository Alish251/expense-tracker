package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.repository.ExpenseRepository;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.ExpenseService;
import org.expensetracker.service.mapper.ExpenseMapper;
import org.expensetracker.service.model.ExpenseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository repository;
    private final ExpenseMapper mapper;

    private final AccountService accountService;

    public ExpenseServiceImpl(ExpenseRepository repository, ExpenseMapper mapper, AccountService accountService) {
        this.repository = repository;
        this.mapper = mapper;
        this.accountService = accountService;
    }

    @Override
    public List<ExpenseDto> getAll() {
        Optional<List<Expense>> optionalUsers = repository.findAll();
        List<Expense> expenses = optionalUsers
                .orElseThrow(() -> new RuntimeException("No expenses found"));
        return mapper.toDto(expenses);
    }

    @Override
    public ExpenseDto getById(Long id) {
        Optional<Expense> optionalUser = repository.findById(id);
        Expense expense = optionalUser
                .orElseThrow(() -> new RuntimeException("No expense found"));
        return mapper.toDto(expense);
    }

    @Override
    public List<ExpenseDto> getAllByAccountId(Long id) {
        Optional<List<Expense>> optionalExpenseList = repository.findByAccountId(id);
        List<Expense> expenseList = optionalExpenseList
                .orElseThrow(() -> new RuntimeException("No incomes found for selected account"));
        return mapper.toDto(expenseList);
    }

    @Override
    public ExpenseDto add(ExpenseDto expenseDto) {
        if (expenseDto == null) {
            return null;
        }
        if (expenseDto.getDate() == null) {
            expenseDto.setDate(LocalDate.now());
        }
        Expense expense = mapper.toEntity(expenseDto);
        Expense savedExpense = repository.add(expense)
                .orElseThrow(() -> new RuntimeException("Expense not added"));

        accountService.updateBalance(expenseDto.getAccountId(), expense.getAmount().negate());

        return mapper.toDto(savedExpense);
    }

    @Override
    public ExpenseDto updateById(Long id, ExpenseDto expenseDto) {
        if (expenseDto == null) {
            return null;
        }
        if (expenseDto.getDate() == null) {
            expenseDto.setDate(LocalDate.now());
        }

        Expense oldIncome = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No income found"));

        Expense newIncome = repository.updateById(id, mapper.toEntity(expenseDto))
                .orElseThrow(() -> new RuntimeException("Income not updated"));

        BigDecimal amountDifference = newIncome.getAmount().subtract(oldIncome.getAmount());

        accountService.updateBalance(expenseDto.getAccountId(), amountDifference);

        Expense expense = repository.updateById(id, mapper.toEntity(expenseDto))
                .orElseThrow(() -> new RuntimeException("Expense not updated"));

        return mapper.toDto(expense);
    }

    @Override
    public void deleteById(Long id) {
        Expense expense = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No expense found"));

        repository.deleteById(id);

        accountService.updateBalance(expense.getAccount().getId(), expense.getAmount());
    }
}
