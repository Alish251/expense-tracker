package org.expensetracker.database.dao;

import org.expensetracker.database.entity.Expense;

import java.util.List;

public interface ExpenseDao {
    List<Expense> findAll();

    Expense findById(Long id);

    Expense save(Expense expense);

    Expense updateById(Long id, Expense expense);

    void deleteById(Long id);

}
