package org.expensetracker.database.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Properties properties = loadProperties();
    private static final String PROPERTIES_FILE = "src/main/resources/config.properties";
    private static final String URL = properties.getProperty("db.URL");
    private static final String USERNAME = properties.getProperty("db.USERNAME");
    private static final String PASSWORD = properties.getProperty("db.PASSWORD");
    private static final int MAX_CONNECTIONS = Integer.parseInt(properties.getProperty("db.MAX_CONNECTIONS"));

    private static ConnectionPool connectionPool;
    private final BlockingQueue<Connection> blockingQueue;

    private ConnectionPool() {
        blockingQueue = new ArrayBlockingQueue<>(MAX_CONNECTIONS);
        try {
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                Connection connection = createConnection();
                blockingQueue.put(connection);
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectionPool getConnectionPool() {
        synchronized (ConnectionPool.class) {
            if (connectionPool == null) {
                connectionPool = new ConnectionPool();
            }
            return connectionPool;
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public synchronized Connection getConnection() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        try {
            blockingQueue.put(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
