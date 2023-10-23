package org.expensetracker.database.dao;

import org.expensetracker.database.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    Category updateById(Long id, Category category);

    void deleteById(Long id);
}
