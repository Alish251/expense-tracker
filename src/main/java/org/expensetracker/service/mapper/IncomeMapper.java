package org.expensetracker.service.mapper;

import org.expensetracker.database.entity.Income;
import org.expensetracker.service.model.IncomeDto;

import java.util.List;

public interface IncomeMapper {
    IncomeDto toDto(Income entity);

    Income toEntity(IncomeDto incomeDto);

    List<IncomeDto> toDto(List<Income> entities);

    List<Income> toEntity(List<IncomeDto> incomeDtoList);
}
