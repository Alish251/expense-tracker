package org.expensetracker.database.repository;

import org.expensetracker.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
//    Optional<List<User>> findAll();
//
//    Optional<User> findById(@NotNull Long id);
//
//    Optional<User> add(@NotNull User user);
//
//    Optional<User> updateById(@NotNull Long id, @NotNull User user);
//
//    Optional<Long> deleteById(@NotNull Long id);
}
