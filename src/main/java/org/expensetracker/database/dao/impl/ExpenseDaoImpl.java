package org.expensetracker.database.dao.impl;

import org.expensetracker.database.dao.ExpenseDao;
import org.expensetracker.database.entity.Category;
import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.util.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDaoImpl implements ExpenseDao {
    private final ConnectionPool connectionPool;

    public ExpenseDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<Expense> findAll() {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from expenses");
             var resultSet = preparedStatement.executeQuery()
        ) {
            List<Expense> expenseList = new ArrayList<>();

            while (resultSet.next()) {
                expenseList.add(mapResultSetToExpense(resultSet));
            }
            connectionPool.returnConnection(connection);
            return expenseList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Expense findById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from expenses where id = ?");) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var expense = mapResultSetToExpense(resultSet);
                connectionPool.returnConnection(connection);
                return expense;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Expense save(Expense expense) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO expenses (amount, category_id, date) VALUES (?, ?, ?) RETURNING id")) {
            preparedStatement.setBigDecimal(1, expense.getAmount());
            preparedStatement.setLong(2, expense.getCategoryId());
            preparedStatement.setDate(3, Date.valueOf(expense.getDate()));
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                expense.setId(generatedId);
            }
            connectionPool.returnConnection(connection);
            return expense;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Expense updateById(Long id, Expense expense) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("update expenses set amount = ?, category_id = ?, date = ? where id = ?");
        ) {
            preparedStatement.setBigDecimal(1, expense.getAmount());
            preparedStatement.setLong(2, expense.getCategoryId());
            preparedStatement.setDate(3, Date.valueOf(expense.getDate()));
            preparedStatement.setLong(4, id);

            var resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                connectionPool.returnConnection(connection);
                return expense;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("delete from expenses where id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Expense mapResultSetToExpense(ResultSet resultSet) throws SQLException {
        Expense expense = new Expense();
        expense.setId(resultSet.getLong(1));
        expense.setAmount(new BigDecimal(resultSet.getString("amount").replaceAll("[,$]", "")));
        expense.setCategoryId(resultSet.getLong(3));
        expense.setDate(resultSet.getDate(4).toLocalDate());
        return expense;
    }

    public static void main(String[] args) {
        System.out.println("Ok");

        ExpenseDaoImpl expenseDao = new ExpenseDaoImpl();

        List<Expense> expenseList = expenseDao.findAll();

        /*for (Expense expense : expenseDao.findAll()){
            System.out.println(expense);
        }*/

        for (Expense expense : expenseList) {
            System.out.println(expense);
        }

        System.out.println(expenseDao.findById(1L));

        Expense expense = new Expense(2L, new BigDecimal("2750.50"), 4L, LocalDate.now());

        //expenseDao.deleteById(4L);
        //expenseDao.updateById(4L,expense);


    }
}
