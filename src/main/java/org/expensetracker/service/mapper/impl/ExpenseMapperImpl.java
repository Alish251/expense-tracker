package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Category;
import org.expensetracker.database.entity.Expense;
import org.expensetracker.service.mapper.ExpenseMapper;
import org.expensetracker.service.model.ExpenseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpenseMapperImpl implements ExpenseMapper {


    @Override
    public ExpenseDto toDto(Expense entity) {
        if (entity == null) {
            return null;
        }
        final ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setId(entity.getId());
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
            final Category category = new Category();
            category.setId(expenseDto.getCategoryId());
            expense.setCategory(category);
        }

        if (expenseDto.getAccountId() != null) {
            final Account account = new Account();
            account.setId(expenseDto.getAccountId());

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
