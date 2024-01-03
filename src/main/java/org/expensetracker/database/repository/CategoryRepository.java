package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
//    Optional<List<Category>> findAll();
//
//    Optional<Category> findById(@NotNull Long id);
//
//    Optional<Category> add(@NotNull Category category);
//
//    Optional<Category> updateById(@NotNull Long id, @NotNull Category category);
//
//    Optional<Long> deleteById(@NotNull Long id);
}
