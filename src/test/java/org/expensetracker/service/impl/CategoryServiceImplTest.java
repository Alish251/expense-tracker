package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Category;
import org.expensetracker.database.repository.CategoryRepository;
import org.expensetracker.service.mapper.CategoryMapper;
import org.expensetracker.service.model.CategoryDto;
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

@ExtendWith({MockitoExtension.class})
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository repository;
    @Mock
    private CategoryMapper mapper;
    @InjectMocks
    private CategoryServiceImpl service;

    private Category category;
    private CategoryDto categoryDto;

    private Long id;


    @BeforeEach
    public void init() {
        id = 1L;

        category = new Category();
        category.setId(id);
        category.setName("Test Name");
        category.setDescription("Test Description");

        categoryDto = new CategoryDto();
        categoryDto.setName("Test Name");
        categoryDto.setDescription("Test Description");
    }

    @Test
    void getAll() {
        var entities = Collections.singletonList(category);
        var dtos = Collections.singletonList(categoryDto);

        when(repository.findAll()).thenReturn(Optional.of(entities));
        when(mapper.toDto(entities)).thenReturn(dtos);

        var result = service.getAll();

        assertEquals(dtos, result);
        verify(repository).findAll();
        verify(mapper).toDto(entities);
    }

    @Test
    void getById() {
        when(repository.findById(id)).thenReturn(Optional.of(category));
        when(mapper.toDto(category)).thenReturn(categoryDto);
        var result = service.getById(id);
        assertEquals(categoryDto, result);

        verify(repository).findById(id);
        verify(mapper).toDto(category);
    }

    @Test
    void add() {
        Category mappedCategory = new Category();
        mappedCategory.setName("Test Name Add");
        mappedCategory.setDescription("Test Description Add");

        when(mapper.toEntity(categoryDto)).thenReturn(mappedCategory);
        when(repository.add(mappedCategory)).thenReturn(Optional.of(category));
        categoryDto.setId(id);

        when(mapper.toDto(category)).thenReturn(categoryDto);

        var result = service.add(categoryDto);

        assertEquals(categoryDto, result);
        verify(mapper).toEntity(categoryDto);
        verify(repository).add(mappedCategory);
        verify(mapper).toDto(category);
    }

    @Test
    void whenNoCategory_thenThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(id));
    }

    @Test
    void updateById() {
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Name");
        updatedCategory.setDescription("Updated Description");

        CategoryDto updatedCategoryDto = new CategoryDto();
        updatedCategoryDto.setName("Updated Name");
        updatedCategoryDto.setDescription("Updated Description");

        when(repository.updateById(id, mapper.toEntity(updatedCategoryDto)))
                .thenReturn(Optional.of(updatedCategory));
        when(mapper.toDto(updatedCategory)).thenReturn(updatedCategoryDto);

        var result = service.updateById(id, updatedCategoryDto);

        assertEquals(updatedCategoryDto, result);
        verify(repository).updateById(id, mapper.toEntity(updatedCategoryDto));
        verify(mapper).toDto(updatedCategory);
    }

    @Test
    void deleteById() {
        when(repository.deleteById(id)).thenReturn(Optional.of(id));

        service.deleteById(id);

        verify(repository).deleteById(id);
    }
}