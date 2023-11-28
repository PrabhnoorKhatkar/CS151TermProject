package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnection class represents a singleton connection to a SQLite database.
 * It provides a single instance of the database connection and ensures that only one
 * connection is established throughout the application's lifecycle.
 */
public class DatabaseConnection {
	/**
     * The JDBC URL for connecting to the SQLite database.
     */
    private final String jdbcUrl = "jdbc:sqlite:Data/database.db";
    /**
     * The single instance of the DatabaseConnection class.
     */
    private static DatabaseConnection singleInstance = new DatabaseConnection();
    /**
     * The Connection object representing the database connection.
     */
    private Connection connection;
    /**
     * Private constructor to create a new DatabaseConnection instance and establish
     * a connection to the SQLite database.
     */
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the single instance of the DatabaseConnection class.
     *
     * @return The single instance of DatabaseConnection.
     */
    public static DatabaseConnection getSingleInstance() {
        return singleInstance;
    }

    /**
     * Retrieves the database connection. If the connection is closed or null, a new
     * connection is established.
     *
     * @return The Connection object representing the database connection.
     * @throws SQLException If a database access error occurs.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(jdbcUrl);
        }
        return connection;
    }
}