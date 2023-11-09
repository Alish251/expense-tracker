package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.service.mapper.IncomeSourceMapper;
import org.expensetracker.service.model.IncomeSourceDto;

import java.util.List;
import java.util.stream.Collectors;

public class IncomeSourceMapperImpl implements IncomeSourceMapper {
    @Override
    public IncomeSourceDto toDto(IncomeSource entity) {
        if (entity == null) {
            return null;
        }
        final IncomeSourceDto incomeSourceDto = new IncomeSourceDto();
        incomeSourceDto.setName(entity.getName());
        incomeSourceDto.setDescription(entity.getDescription());

        return incomeSourceDto;
    }

    @Override
    public IncomeSource toEntity(IncomeSourceDto incomeSourceDto) {
        if (incomeSourceDto == null) {
            return null;
        }
        final IncomeSource incomeSource = new IncomeSource();
        incomeSource.setName(incomeSourceDto.getName());
        incomeSource.setDescription(incomeSourceDto.getDescription());

        return incomeSource;
    }

    @Override
    public List<IncomeSourceDto> toDto(List<IncomeSource> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IncomeSource> toEntity(List<IncomeSourceDto> incomeSourceDtoList) {
        if (incomeSourceDtoList == null) {
            return null;
        }
        return incomeSourceDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
