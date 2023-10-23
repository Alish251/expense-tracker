package org.expensetracker.database.dao;

import org.expensetracker.database.entity.IncomeSource;

import java.util.List;

public interface IncomeSourceDao {
    List<IncomeSource> findAll();

    IncomeSource findById(Long id);

    IncomeSource save(IncomeSource incomeSource);

    IncomeSource updateById(Long id, IncomeSource incomeSource);

    void deleteById(Long id);
}
