package org.expensetracker.service.mapper;

import org.expensetracker.database.entity.Category;
import org.expensetracker.service.model.CategoryDto;

import java.util.List;

public interface CategoryMapper {
    CategoryDto toDto(Category entity);

    Category toEntity(CategoryDto categoryDto);

    List<CategoryDto> toDto(List<Category> entities);

    List<Category> toEntity(List<CategoryDto> categoryDtoList);
}
