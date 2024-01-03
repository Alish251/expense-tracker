package org.expensetracker.service;

import jakarta.validation.constraints.NotNull;
import org.expensetracker.service.model.IncomeDto;

import java.util.List;

public interface IncomeService {
    List<IncomeDto> getAll();

    IncomeDto getById(@NotNull Long id);

    List<IncomeDto> getAllByAccountId(@NotNull Long id);

    IncomeDto add(@NotNull IncomeDto incomeDto);

    IncomeDto updateById(@NotNull Long id, @NotNull IncomeDto incomeDto);

    void deleteById(@NotNull Long id);
}
