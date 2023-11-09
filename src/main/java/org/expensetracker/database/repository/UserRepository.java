package org.expensetracker.database.repository;

import org.expensetracker.database.entity.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<List<User>> findAll();

    Optional<User> findById(@NotNull Long id);

    Optional<User> add(@NotNull User user);

    Optional<User> updateById(@NotNull Long id, @NotNull User user);

    Optional<Long> deleteById(@NotNull Long id);
}
