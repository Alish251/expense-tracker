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
        List<Category> categoryList = repository.findAll();
//        List<Category> categories = optionalCategories
//                .orElseThrow(() -> new RuntimeException("No categories found"));
        return mapper.toDto(categoryList);
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
        if (categoryDto.getDescription() == null) {
            categoryDto.setDescription("");
        }
        Category category = mapper.toEntity(categoryDto);
        Category savedCategory = repository.save(category);
        return mapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateById(Long id, CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        Category oldCategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No category found"));

        if (categoryDto.getName()!= null){
            oldCategory.setName(categoryDto.getName());
        }
        if (categoryDto.getDescription()!= null){
            oldCategory.setDescription(categoryDto.getDescription());
        }

        Category newCategory = repository.save(oldCategory);

        return mapper.toDto(newCategory);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
