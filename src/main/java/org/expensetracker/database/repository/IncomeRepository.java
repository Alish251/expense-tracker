package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Long> {
    List<Income> getAllByAccount_Id(Long accountId);
//    Optional<List<Income>> findAll();
//
//    Optional<Income> findById(@NotNull Long id);
//
//    Optional<List<Income>> findByAccountId(@NotNull Long id);
//
//    Optional<Income> add(@NotNull Income income);
//
//    Optional<Income> updateById(@NotNull Long id, @NotNull Income income);
//
//    Optional<Long> deleteById(@NotNull Long id);
}
