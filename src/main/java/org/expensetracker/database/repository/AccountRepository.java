package org.expensetracker.database.repository;

import org.expensetracker.database.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
//    Optional<List<Account>> findAll();
//
//    Optional<Account> findById(@NotNull Long id);
//
//    Optional<Account> add(@NotNull Account account);
//
//    Optional<Account> updateById(@NotNull Long id, @NotNull Account account);
//
//    Optional<Long> deleteById(@NotNull Long id)

}
