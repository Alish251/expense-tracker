package org.expensetracker.service.mapper;

import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.service.model.IncomeSourceDto;

import java.util.List;

public interface IncomeSourceMapper {
    IncomeSourceDto toDto(IncomeSource entity);

    IncomeSource toEntity(IncomeSourceDto incomeSourceDto);

    List<IncomeSourceDto> toDto(List<IncomeSource> entities);

    List<IncomeSource> toEntity(List<IncomeSourceDto> incomeSourceDtoList);
}
