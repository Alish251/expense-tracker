package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Category;
import org.expensetracker.database.repository.CategoryRepository;
import org.expensetracker.service.CategoryService;
import org.expensetracker.service.mapper.CategoryMapper;
import org.expensetracker.service.model.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryDto> getAll() {
        Optional<List<Category>> optionalCategories = repository.findAll();
        List<Category> categories = optionalCategories
                .orElseThrow(() -> new RuntimeException("No categories found"));
        return mapper.toDto(categories);
    }

    @Override
    public CategoryDto getById(Long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        Category category = optionalCategory
                .orElseThrow(() -> new RuntimeException("No category found"));
        return mapper.toDto(category);
    }

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category category = mapper.toEntity(categoryDto);
        Category savedCategory = repository.add(category)
                .orElseThrow(() -> new RuntimeException("Category not added"));
        return mapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateById(Long id, CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        Category category = repository.updateById(id, mapper.toEntity(categoryDto))
                .orElseThrow(() -> new RuntimeException("Category not updated"));

        return mapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        Long optionalLong = repository.deleteById(id)
                .orElseThrow(() -> new RuntimeException("No category found"));
    }
}
