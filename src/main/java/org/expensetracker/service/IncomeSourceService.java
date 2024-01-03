package org.expensetracker.service;

import jakarta.validation.constraints.NotNull;
import org.expensetracker.service.model.IncomeSourceDto;

import java.util.List;

public interface IncomeSourceService {
    List<IncomeSourceDto> getAll();

    IncomeSourceDto getById(@NotNull Long id);

    IncomeSourceDto add(@NotNull IncomeSourceDto incomeSourceDto);

    IncomeSourceDto updateById(@NotNull Long id, @NotNull IncomeSourceDto incomeSourceDto);

    void deleteById(@NotNull Long id);
}
