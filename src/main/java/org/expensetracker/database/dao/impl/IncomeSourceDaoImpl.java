package org.expensetracker.database.dao.impl;

import org.expensetracker.database.dao.IncomeSourceDao;
import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.database.util.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeSourceDaoImpl implements IncomeSourceDao {
    private final ConnectionPool connectionPool;

    public IncomeSourceDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<IncomeSource> findAll() {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from income_sources");
             var resultSet = preparedStatement.executeQuery()
        ) {
            List<IncomeSource> incomeSources = new ArrayList<>();
            while (resultSet.next()) {
                incomeSources.add(mapResultSetToIncomeSource(resultSet));
            }
            connectionPool.returnConnection(connection);
            return incomeSources;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IncomeSource findById(Long id) {
        IncomeSource incomeSource = null;

        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from income_sources where id = ?")) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                incomeSource = mapResultSetToIncomeSource(resultSet);
                connectionPool.returnConnection(connection);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return incomeSource;
    }

    @Override
    public IncomeSource save(IncomeSource incomeSource) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO income_sources (name, description) VALUES (?, ?) RETURNING id")
        ) {
            preparedStatement.setString(1, incomeSource.getName());
            preparedStatement.setString(2, incomeSource.getDescription());
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                incomeSource.setId(generatedId);
            }
            connectionPool.returnConnection(connection);
            return incomeSource;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IncomeSource updateById(Long id, IncomeSource incomeSource) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("update income_sources set name = ?, description = ? where id = ?")
        ) {
            preparedStatement.setString(1, incomeSource.getName());
            preparedStatement.setString(2, incomeSource.getDescription());
            preparedStatement.setLong(3, id);

            var resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                connectionPool.returnConnection(connection);
                return incomeSource;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("delete from income_sources where id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private IncomeSource mapResultSetToIncomeSource(ResultSet resultSet) throws SQLException {
        IncomeSource incomeSource = new IncomeSource();
        incomeSource.setId(resultSet.getLong(1));
        incomeSource.setName(resultSet.getString(2));
        incomeSource.setDescription(resultSet.getString(3));
        return incomeSource;
    }
}
