package org.expensetracker.database.repository;

import org.expensetracker.database.entity.IncomeSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeSourceRepository extends JpaRepository<IncomeSource,Long> {
//    Optional<List<IncomeSource>> findAll();
//
//    Optional<IncomeSource> findById(@NotNull Long id);
//
//    Optional<IncomeSource> add(@NotNull IncomeSource incomeSource);
//
//    Optional<IncomeSource> updateById(@NotNull Long id, @NotNull IncomeSource incomeSource);
//
//    Optional<Long> deleteById(@NotNull Long id);
}
