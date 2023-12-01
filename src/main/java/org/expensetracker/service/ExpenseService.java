package org.expensetracker.service;

import org.expensetracker.service.model.ExpenseDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDto> getAll();

    ExpenseDto getById(@NotNull Long id);

    List<ExpenseDto> getAllByAccountId(@NotNull Long id);

    ExpenseDto add(@NotNull ExpenseDto expenseDto);

    ExpenseDto updateById(@NotNull Long id, @NotNull ExpenseDto expenseDto);

    void deleteById(@NotNull Long id);
}
