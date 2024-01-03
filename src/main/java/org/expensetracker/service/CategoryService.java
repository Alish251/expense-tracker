package org.expensetracker.service;

import jakarta.validation.constraints.NotNull;
import org.expensetracker.service.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll();

    CategoryDto getById(@NotNull Long id);

    CategoryDto add(@NotNull CategoryDto categoryDto);

    CategoryDto updateById(@NotNull Long id, @NotNull CategoryDto categoryDto);

    void deleteById(@NotNull Long id);
}
