package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Category;
import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.CategoryRepository;
import org.expensetracker.service.mapper.ExpenseMapper;
import org.expensetracker.service.model.ExpenseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseMapperImpl implements ExpenseMapper {
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseMapperImpl(AccountRepository accountRepository, CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ExpenseDto toDto(Expense entity) {
        if (entity == null) {
            return null;
        }
        final ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(entity.getAmount());
        expenseDto.setDate(entity.getDate());
        expenseDto.setAccountId(entity.getAccount().getId());
        expenseDto.setCategoryId(entity.getCategory().getId());

        return expenseDto;
    }

    @Override
    public Expense toEntity(ExpenseDto expenseDto) {
        if (expenseDto == null) {
            return null;
        }
        final Expense expense = new Expense();
        expense.setAmount(expenseDto.getAmount());
        expense.setDate(expenseDto.getDate());

        if (expenseDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(expenseDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            expense.setCategory(category);
        }

        if (expenseDto.getAccountId() != null) {
            Account account = accountRepository.findById(expenseDto.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            expense.setAccount(account);
        }
        return expense;

    }

    @Override
    public List<ExpenseDto> toDto(List<Expense> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> toEntity(List<ExpenseDto> expenseDtoList) {
        if (expenseDtoList == null) {
            return null;
        }
        return expenseDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
