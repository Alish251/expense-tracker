package org.expensetracker.service.mapper;

import org.expensetracker.database.entity.Expense;
import org.expensetracker.service.model.ExpenseDto;

import java.util.List;

public interface ExpenseMapper {
    ExpenseDto toDto(Expense entity);

    Expense toEntity(ExpenseDto expenseDto);

    List<ExpenseDto> toDto(List<Expense> entities);

    List<Expense> toEntity(List<ExpenseDto> expenseDtoList);
}
