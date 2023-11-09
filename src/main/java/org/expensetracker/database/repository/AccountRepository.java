package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Account;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<List<Account>> findAll();

    Optional<Account> findById(@NotNull Long id);

    Optional<Account> add(@NotNull Account account);

    Optional<Account> updateById(@NotNull Long id, @NotNull Account account);

    Optional<Long> deleteById(@NotNull Long id);
}
