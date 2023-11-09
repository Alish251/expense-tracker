package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Category;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<List<Category>> findAll();

    Optional<Category> findById(@NotNull Long id);

    Optional<Category> add(@NotNull Category category);

    Optional<Category> updateById(@NotNull Long id, @NotNull Category category);

    Optional<Long> deleteById(@NotNull Long id);
}
