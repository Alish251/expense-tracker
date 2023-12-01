package org.expensetracker.service.impl;

import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.database.repository.IncomeSourceRepository;
import org.expensetracker.service.mapper.IncomeSourceMapper;
import org.expensetracker.service.model.IncomeSourceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncomeSourceServiceImplTest {
    @Mock
    private IncomeSourceRepository repository;
    @Mock
    private IncomeSourceMapper mapper;
    @InjectMocks
    private IncomeSourceServiceImpl service;

    private IncomeSource incomeSource;
    private IncomeSourceDto incomeSourceDto;

    private Long id;

    @BeforeEach
    public void init() {
        id = 1L;

        incomeSource = new IncomeSource();
        incomeSource.setId(id);
        incomeSource.setName("Test Name");
        incomeSource.setDescription("Test Description");

        incomeSourceDto = new IncomeSourceDto();
        incomeSourceDto.setName("Test Name");
        incomeSourceDto.setDescription("Test Description");
    }

    @Test
    void getAll() {
        var entities = Collections.singletonList(incomeSource);
        var dtos = Collections.singletonList(incomeSourceDto);

        when(repository.findAll()).thenReturn(Optional.of(entities));
        when(mapper.toDto(entities)).thenReturn(dtos);

        var result = service.getAll();

        assertEquals(dtos, result);
        verify(repository).findAll();
        verify(mapper).toDto(entities);
    }

    @Test
    void getById() {
        when(repository.findById(id)).thenReturn(Optional.of(incomeSource));
        when(mapper.toDto(incomeSource)).thenReturn(incomeSourceDto);
        var result = service.getById(id);
        assertEquals(incomeSourceDto, result);

        verify(repository).findById(id);
        verify(mapper).toDto(incomeSource);
    }

    @Test
    void add() {
        IncomeSource mappedIncomeSource = new IncomeSource();
        mappedIncomeSource.setName("Test Name Add");
        mappedIncomeSource.setDescription("Test Description Add");

        when(mapper.toEntity(incomeSourceDto)).thenReturn(mappedIncomeSource);
        when(repository.add(mappedIncomeSource)).thenReturn(Optional.of(incomeSource));
        incomeSourceDto.setId(id);

        when(mapper.toDto(incomeSource)).thenReturn(incomeSourceDto);

        var result = service.add(incomeSourceDto);

        assertEquals(incomeSourceDto, result);
        verify(mapper).toEntity(incomeSourceDto);
        verify(repository).add(mappedIncomeSource);
        verify(mapper).toDto(incomeSource);
    }

    @Test
    void whenNoIncomeSource_thenThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(id));
    }


    @Test
    void updateById() {
        IncomeSource updatedIncomeSource = new IncomeSource();
        updatedIncomeSource.setName("Updated Name");
        updatedIncomeSource.setDescription("Updated Description");

        IncomeSourceDto updatedIncomeSourceDto = new IncomeSourceDto();
        updatedIncomeSourceDto.setName("Updated Name");
        updatedIncomeSourceDto.setDescription("Updated Description");

        when(repository.updateById(id, mapper.toEntity(updatedIncomeSourceDto)))
                .thenReturn(Optional.of(updatedIncomeSource));
        when(mapper.toDto(updatedIncomeSource)).thenReturn(updatedIncomeSourceDto);

        var result = service.updateById(id, updatedIncomeSourceDto);

        assertEquals(updatedIncomeSourceDto, result);
        verify(repository).updateById(id, mapper.toEntity(updatedIncomeSourceDto));
        verify(mapper).toDto(updatedIncomeSource);
    }

    @Test
    void deleteById() {
        when(repository.deleteById(id)).thenReturn(Optional.of(id));

        service.deleteById(id);

        verify(repository).deleteById(id);
    }
}