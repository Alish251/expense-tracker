package org.expensetracker.database.dao.impl;

import org.expensetracker.database.dao.CategoryDao;
import org.expensetracker.database.entity.Category;
import org.expensetracker.database.util.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private final ConnectionPool connectionPool;

    public CategoryDaoImpl() {
        connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<Category> findAll() {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from categories");
             var resultSet = preparedStatement.executeQuery()
        ) {
            List<Category> categoryList = new ArrayList<>();
            while (resultSet.next()) {
                categoryList.add(mapResultSetToCategory(resultSet));
            }
            connectionPool.returnConnection(connection);
            return categoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category findById(Long id) {
        Category category = null;
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("select * from categories where id = ?")) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                category = mapResultSetToCategory(resultSet);
                connectionPool.returnConnection(connection);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    @Override
    public Category save(Category category) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("INSERT INTO categories (name, description) VALUES (?, ?) RETURNING id")
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                category.setId(generatedId);
            }
            connectionPool.returnConnection(connection);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category updateById(Long id, Category category) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("update categories set name = ?, description = ? where id = ?")
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setLong(3, id);

            var resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                connectionPool.returnConnection(connection);
                return category;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (var connection = connectionPool.getConnection();
             var preparedStatement = connection.prepareStatement("delete from categories where id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category mapResultSetToCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong(1));
        category.setName(resultSet.getString(2));
        category.setDescription(resultSet.getString(3));
        return category;
    }

    public static void main(String[] args) {
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();

        List<Category> categoryList = new ArrayList<>(categoryDao.findAll());


        for (Category c : categoryList) {
            System.out.println(c);
        }

        Category categoryUpdateTest = new Category(2L, "Transport", "Bus");

        categoryDao.save(categoryUpdateTest);
    }
}
