package org.expensetracker.database.dao;

import org.expensetracker.database.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User updateById(Long id, User user);

    void deleteById(Long id);

}
