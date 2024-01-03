package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> getAllByAccount_Id(Long accountId);
//    Optional<List<Expense>> findAll();
//
//    Optional<Expense> findById(@NotNull Long id);
//
//    Optional<List<Expense>> findByAccountId(@NotNull Long accountId);
//
//    Optional<Expense> add(@NotNull Expense expense);
//
//    Optional<Expense> updateById(@NotNull Long id, @NotNull Expense expense);
//
//    Optional<Long> deleteById(@NotNull Long id);
}
