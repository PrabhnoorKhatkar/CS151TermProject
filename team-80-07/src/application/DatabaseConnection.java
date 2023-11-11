package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String jdbcUrl = "jdbc:sqlite:Data/database.db";
    private static DatabaseConnection singleInstance = new DatabaseConnection();
    private Connection connection;

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public static DatabaseConnection getSingleInstance() {
        return singleInstance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(jdbcUrl);
        }
        return connection;
    }
}