package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Expense;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {
    Optional<List<Expense>> findAll();

    Optional<Expense> findById(@NotNull Long id);

    Optional<Expense> add(@NotNull Expense expense);

    Optional<Expense> updateById(@NotNull Long id, @NotNull Expense expense);

    Optional<Long> deleteById(@NotNull Long id);
}
