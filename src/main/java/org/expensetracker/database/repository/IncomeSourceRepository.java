package org.expensetracker.database.repository;

import org.expensetracker.database.entity.IncomeSource;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface IncomeSourceRepository {
    Optional<List<IncomeSource>> findAll();

    Optional<IncomeSource> findById(@NotNull Long id);

    Optional<IncomeSource> add(@NotNull IncomeSource incomeSource);

    Optional<IncomeSource> updateById(@NotNull Long id, @NotNull IncomeSource incomeSource);

    Optional<Long> deleteById(@NotNull Long id);
}
