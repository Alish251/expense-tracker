package org.expensetracker.database.dao.impl;

import org.expensetracker.database.dao.UserDao;
import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.entity.User;
import org.expensetracker.database.util.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final ConnectionPool connectionPool;

    public UserDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<User> findAll() {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from users");
             var resultSet = preparedStatement.executeQuery()
        ) {
            List<User> userArrayList = new ArrayList<>();

            while (resultSet.next()) {
                userArrayList.add(mapResultSetToUser(resultSet));
            }
            connectionPool.returnConnection(connection);
            return userArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from users where id = ?");) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var user = mapResultSetToUser(resultSet);
                connectionPool.returnConnection(connection);
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User save(User user) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO users (firstname, lastname, email) VALUES (?, ?, ?) RETURNING id")) {
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                user.setId(generatedId);
            }
            connectionPool.returnConnection(connection);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updateById(Long id, User user) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("update users set firstname = ?, lastname = ?, email = ? where id = ?");
        ) {
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, id);

            var resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                connectionPool.returnConnection(connection);
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("delete from users where id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setFirstname(resultSet.getString(2));
        user.setLastname(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        return user;
    }

    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();

        List<User> userList = userDao.findAll();

        for (User user : userList) {
            System.out.println(user);
        }

        System.out.println(userDao.findById(1l));

        User user = new User();

        user.setId(2l);
        user.setFirstname("Adam");
        user.setLastname("Alex");
        user.setEmail("adamalex@gmail.com");

        //System.out.println(userDao.save(user));

        user.setEmail("adamalex@mail.com");

        System.out.println(userDao.updateById(2l, user));

        userDao.deleteById(2l);
    }
}
