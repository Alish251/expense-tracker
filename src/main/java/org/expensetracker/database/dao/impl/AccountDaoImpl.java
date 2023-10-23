package org.expensetracker.database.dao.impl;

import org.expensetracker.database.dao.AccountDao;
import org.expensetracker.database.entity.Account;
import org.expensetracker.database.util.ConnectionPool;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private final ConnectionPool connectionPool;

    public AccountDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<Account> findAll() {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from accounts"); var resultSet = preparedStatement.executeQuery()) {
            List<Account> accountList = new ArrayList<>();
            while (resultSet.next()) {
                accountList.add(mapResultSetToAccount(resultSet));
            }
            connectionPool.returnConnection(connection);
            return accountList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from accounts where id = ?");) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var account = mapResultSetToAccount(resultSet);
                connectionPool.returnConnection(connection);
                return account;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Account save(Account account) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO accounts (balance, expense_id, income_id, user_id) VALUES (?, ?, ?, ?) RETURNING id")) {
            preparedStatement.setBigDecimal(1, account.getBalance());
            preparedStatement.setLong(2, account.getExpenseId());
            preparedStatement.setLong(3, account.getIncomeId());
            preparedStatement.setLong(4, account.getUserId());

            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                account.setId(generatedId);
            }
            connectionPool.returnConnection(connection);
            return account;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account updateById(Long id, Account account) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("update accounts set balance = ?, expense_id = ?, income_id = ?, user_id = ? where id = ?");
        ) {
            preparedStatement.setBigDecimal(1, account.getBalance());
            preparedStatement.setLong(2, account.getExpenseId());
            preparedStatement.setLong(3, account.getIncomeId());
            preparedStatement.setLong(4, account.getUserId());
            preparedStatement.setLong(5, id);

            var resultSet = preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
            if (resultSet > 0) {
                return account;
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("delete from accounts where id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong("id"));
        account.setBalance(new BigDecimal(resultSet.getString("balance").replaceAll("[,$]", "")));
        account.setExpenseId(resultSet.getLong("expense_id"));
        account.setIncomeId(resultSet.getLong("income_id"));
        account.setUserId(resultSet.getLong("user_id"));
        return account;
    }

    public static void main(String[] args) {
        long id = 1;
        BigDecimal bigDecimal = BigDecimal.valueOf(2800);
        long incomeId = 1;
        long expenseId = 1;
        long userId = 1;

        Account account1 = new Account(id, bigDecimal, incomeId, expenseId, userId);

        AccountDaoImpl accountDao = new AccountDaoImpl();

        List<Account> accountList = new ArrayList<>(accountDao.findAll());

        for (Account a : accountList) {
            System.out.println(a);
        }

        //System.out.println(accountDao.findById(3L));

        accountDao.updateById(5l,account1);

    }
}


