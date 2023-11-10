package application;

import java.sql.Connection;

public class DatabaseConnector 
{
    private Database database;
	private Connection connection;

    public DatabaseConnector()
    {
        database = new Database();
        database.createTables();
        connection = database.connect();

    }
    
}
