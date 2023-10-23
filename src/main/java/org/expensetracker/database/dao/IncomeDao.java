package org.expensetracker.database.dao;

import org.expensetracker.database.entity.Income;

import java.util.List;

public interface IncomeDao {
    List<Income> findAll();

    Income findById(Long id);

    Income save(Income income);

    Income updateById(Long id, Income income);

    void deleteById(Long id);

}
