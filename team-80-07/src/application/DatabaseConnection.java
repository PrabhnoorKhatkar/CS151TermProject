package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";
    private static Connection connection;
    
    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public static Connection getInstance() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    new DatabaseConnection();
                }
            }
        }
        return connection;
    }
}