package org.expensetracker.database.dao;

import org.expensetracker.database.entity.Account;

import java.util.List;

public interface AccountDao {
    List<Account> findAll();

    Account findById(Long id);

    Account save(Account account);

    Account updateById(Long id, Account account);

    void deleteById(Long id);
}
