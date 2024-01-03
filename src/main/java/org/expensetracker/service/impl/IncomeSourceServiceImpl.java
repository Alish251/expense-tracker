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
        List<IncomeSource> incomeSourceList = repository.findAll();
//        List<IncomeSource> incomes = optionalIncomes
//                .orElseThrow(() -> new RuntimeException("No income sources found"));
        return mapper.toDto(incomeSourceList);
    }

    @Override
    public IncomeSourceDto getById(Long id) {
        Optional<IncomeSource> optionalIncome = repository.findById(id);
        IncomeSource incomeSource = optionalIncome
                .orElseThrow(() -> new RuntimeException("No income source found"));
        return mapper.toDto(incomeSource);
    }

    @Override
    public IncomeSourceDto add(IncomeSourceDto incomeSourceDto) {
        if (incomeSourceDto == null) {
            return null;
        }
        if (incomeSourceDto.getDescription() == null) {
            incomeSourceDto.setDescription("");
        }
        IncomeSource incomeSource = mapper.toEntity(incomeSourceDto);
        IncomeSource savedIncomeSource = repository.save(incomeSource);
        return mapper.toDto(savedIncomeSource);
    }

    @Override
    public IncomeSourceDto updateById(Long id, IncomeSourceDto incomeSourceDto) {
        if (incomeSourceDto == null) {
            return null;
        }
        IncomeSource oldIncomeSource = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No category found"));

        if (incomeSourceDto.getName()!= null){
            oldIncomeSource.setName(incomeSourceDto.getName());
        }
        if (incomeSourceDto.getDescription()!= null){
            oldIncomeSource.setDescription(incomeSourceDto.getDescription());
        }

        IncomeSource newIncomeSource = repository.save(oldIncomeSource);
        return mapper.toDto(newIncomeSource);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
