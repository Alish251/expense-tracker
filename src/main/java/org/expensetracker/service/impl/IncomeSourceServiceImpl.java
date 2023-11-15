package org.expensetracker.service.impl;

import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.database.repository.IncomeSourceRepository;
import org.expensetracker.service.IncomeSourceService;
import org.expensetracker.service.mapper.IncomeSourceMapper;
import org.expensetracker.service.model.IncomeSourceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeSourceServiceImpl implements IncomeSourceService {
    private final IncomeSourceRepository repository;
    private final IncomeSourceMapper mapper;

    public IncomeSourceServiceImpl(IncomeSourceRepository repository, IncomeSourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<IncomeSourceDto> getAll() {
        Optional<List<IncomeSource>> optionalIncomes = repository.findAll();
        List<IncomeSource> incomes = optionalIncomes.orElseThrow(() -> new RuntimeException("No income sources found"));
        return mapper.toDto(incomes);
    }

    @Override
    public IncomeSourceDto getById(Long id) {
        Optional<IncomeSource> optionalIncome = repository.findById(id);
        IncomeSource incomeSource = optionalIncome.orElseThrow(() -> new RuntimeException("No income source found"));
        return mapper.toDto(incomeSource);
    }

    @Override
    public IncomeSourceDto add(IncomeSourceDto incomeSourceDto) {
        if (incomeSourceDto == null) {
            return null;
        }
        IncomeSource incomeSource = mapper.toEntity(incomeSourceDto);
        IncomeSource savedIncomeSource = repository.add(incomeSource).orElseThrow(() -> new RuntimeException("Income source not added"));
        return mapper.toDto(savedIncomeSource);
    }

    @Override
    public IncomeSourceDto updateById(Long id, IncomeSourceDto incomeSourceDto) {
        if (incomeSourceDto == null) {
            return null;
        }

        IncomeSource incomeSource = repository.updateById(id, mapper.toEntity(incomeSourceDto)).orElseThrow(() -> new RuntimeException("Income source not updated"));

        return mapper.toDto(incomeSource);
    }

    @Override
    public void deleteById(Long id) {
        Long optionalLong = repository.deleteById(id).orElseThrow(() -> new RuntimeException("No income source found"));
    }
}
