package org.expensetracker.database.dao.impl;

import org.expensetracker.database.dao.IncomeDao;
import org.expensetracker.database.entity.Income;
import org.expensetracker.database.util.ConnectionPool;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeDaoImpl implements IncomeDao {
    private final ConnectionPool connectionPool;

    public IncomeDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<Income> findAll() {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from incomes");
             var resultSet = preparedStatement.executeQuery()
        ) {
            List<Income> incomes = new ArrayList<>();

            while (resultSet.next()) {
                incomes.add(mapResultSetToIncome(resultSet));
            }
            connectionPool.returnConnection(connection);
            return incomes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Income findById(Long id) {
        Income income = null;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from incomes where id = ?")) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                income = mapResultSetToIncome(resultSet);
                connectionPool.returnConnection(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return income;
    }

    @Override
    public Income save(Income income) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO incomes (amount, income_source_id, date) VALUES (?, ?, ?) RETURNING id")) {
            preparedStatement.setBigDecimal(1, income.getAmount());
            preparedStatement.setLong(2, income.getIncomeSourceId());
            preparedStatement.setDate(3, Date.valueOf(income.getDate()));
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                income.setId(generatedId);
            }
            connectionPool.returnConnection(connection);
            return income;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Income updateById(Long id, Income income) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("update incomes set amount = ?, income_source_id = ?, date = ? where id = ?")
        ) {
            preparedStatement.setBigDecimal(1, income.getAmount());
            preparedStatement.setLong(2, income.getIncomeSourceId());
            preparedStatement.setDate(3, Date.valueOf(income.getDate()));
            preparedStatement.setLong(4, id);

            var resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                connectionPool.returnConnection(connection);
                return income;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("delete from incomes where id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Income mapResultSetToIncome(ResultSet resultSet) throws SQLException {
        Income income = new Income();
        income.setId(resultSet.getLong(1));
        income.setAmount(new BigDecimal(resultSet.getString("amount").replaceAll("[,$]", "")));
        income.setIncomeSourceId(resultSet.getLong(3));
        income.setDate(resultSet.getDate(4).toLocalDate());
        return income;
    }
}
