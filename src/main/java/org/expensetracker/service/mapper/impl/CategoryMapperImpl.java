package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Category;
import org.expensetracker.service.mapper.CategoryMapper;
import org.expensetracker.service.model.CategoryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryDto toDto(Category entity) {
        if (entity == null) {
            return null;
        }
        final CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());
        categoryDto.setDescription(entity.getDescription());
        /*if (entity.getExpenses() != null && !entity.getExpenses().isEmpty()) {
            Set<ExpenseDto> expenseDtoSet = entity.getExpenses().stream().map(
                            expense -> {
                                ExpenseDto expenseDto = new ExpenseDto();
                                expenseDto.setAmount(expense.getAmount());
                                expenseDto.setCategoryId(expense.getCategory().getId());
                                expenseDto.setAccountId(expense.getAccount().getId());
                                expenseDto.setDate(expense.getDate());

                                return expenseDto;
                            })
                    .collect(Collectors.toSet());
            categoryDto.setExpenseDtoSet(expenseDtoSet);
        }*/

        return categoryDto;
    }

    @Override
    public Category toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }
        final Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        /*if (categoryDto.getExpenseDtoSet() != null && !categoryDto.getExpenseDtoSet().isEmpty()) {
            Set<Expense> expenseSet = categoryDto.getExpenseDtoSet().stream().map(
                            expenseDto -> {
                                Expense expense = new Expense();
                                expense.setAmount(expenseDto.getAmount());
                                expense.setDate(expenseDto.getDate());
                                expense.setCategory(category);
                                //expense.setAccount(expenseDto.getAccountId()); //todo - check

                                return expense;
                            })
                    .collect(Collectors.toSet());
        }*/
        return category;
    }

    @Override
    public List<CategoryDto> toDto(List<Category> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> toEntity(List<CategoryDto> categoryDtoList) {
        if (categoryDtoList == null) {
            return null;
        }
        return categoryDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
