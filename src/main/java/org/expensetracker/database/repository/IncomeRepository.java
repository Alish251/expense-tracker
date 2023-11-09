package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Income;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository {
    Optional<List<Income>> findAll();

    Optional<Income> findById(@NotNull Long id);

    Optional<Income> add(@NotNull Income income);

    Optional<Income> updateById(@NotNull Long id, @NotNull Income income);

    Optional<Long> deleteById(@NotNull Long id);
}
