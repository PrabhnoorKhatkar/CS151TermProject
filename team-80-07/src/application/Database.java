package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database 
{

    private final String jdbcUrl = "jdbc:sqlite:Data/database.db";


	public Connection connect() 
    {
		Connection conn = null;
		try 
        {
			conn = DriverManager.getConnection(jdbcUrl);
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
		}
		return conn;
	}

    // create tables for data storage
	public void createTables() 
    {
        try (Connection connection = connect())
        {
        try
         {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS projects (" + // Updated table name to "projects"
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "project_name TEXT NOT NULL," +
                    "project_date TEXT NOT NULL," +
                    "project_description TEXT" +
                    ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) 
            {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating the table: " + e.getMessage());
        }

        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tickets (" + // Updated table name to "tickets"
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "project_name TEXT NOT NULL," +
                    "ticket_name TEXT NOT NULL," +
                    "ticket_description TEXT," +
                    "ticketID TEXT NOT NULL" +
                    ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating the table: " + e.getMessage());
        }

        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS comments (" + // Updated table name to "tickets"
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ticketID TEXT NOT NULL," +
                    "timestamp TEXT NOT NULL," +
                    "comment_description TEXT NOT NULL" +
                    ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                preparedStatement.executeUpdate();
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            System.err.println("Error creating the table: " + e.getMessage());
        }
            

        } 
        catch (Exception e) 
        {
       
        }
        

    }

    


    
}
