package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Income;
import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.service.mapper.IncomeMapper;
import org.expensetracker.service.model.IncomeDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncomeMapperImpl implements IncomeMapper {
    @Override
    public IncomeDto toDto(Income entity) {
        if (entity == null) {
            return null;
        }
        final IncomeDto incomeDto = new IncomeDto();
        incomeDto.setId(entity.getId());
        incomeDto.setAmount(entity.getAmount());
        incomeDto.setDate(entity.getDate());
        incomeDto.setAccountId(entity.getAccount().getId());
        incomeDto.setIncomeSourceId(entity.getIncomeSource().getId());

        return incomeDto;
    }

    @Override
    public Income toEntity(IncomeDto incomeDto) {
        if (incomeDto == null) {
            return null;
        }
        final Income income = new Income();
        income.setAmount(incomeDto.getAmount());
        income.setDate(incomeDto.getDate());

        if (incomeDto.getIncomeSourceId() != null) {
            final IncomeSource incomeSource = new IncomeSource();
            incomeSource.setId(incomeDto.getIncomeSourceId());
            income.setIncomeSource(incomeSource);
        }

        if (incomeDto.getAccountId() != null) {
            final Account account = new Account();
            account.setId(incomeDto.getAccountId());
            income.setAccount(account);
        }
        return income;
    }

    @Override
    public List<IncomeDto> toDto(List<Income> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Income> toEntity(List<IncomeDto> incomeDtoList) {
        if (incomeDtoList == null) {
            return null;
        }
        return incomeDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
