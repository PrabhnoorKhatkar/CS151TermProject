import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection 
{
    private final String jdbcUrl = "jdbc:sqlite:Data/database.db";

    private static DatabaseConnection singleInstance = new DatabaseConnection();

    private DatabaseConnection() 
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

    }

    public static DatabaseConnection getSingleInstance() 
    {
        return singleInstance;
    }

}