package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class NewTicketPageController implements Initializable
{

	@FXML
	private ComboBox<String> listProjects;
	
    @FXML
    private TextField ticketNameField;

    @FXML
    private TextArea ticketDescriptionArea;

    @FXML
    private Button saveTicketButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button clearButton;
    

    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";

 

    /**
     * This method handles the action when the "Save" button is clicked.
     * Creates a new project and saves it to the database.
     * @param event The action event.
     * @throws Exception If saving the project or navigation fails.
     */
    public void saveTicket(ActionEvent event) throws Exception 
    {
    	
    	String selectedProject = listProjects.getValue();
    	String ticketName = ticketNameField.getText();
    	String ticketDesc = ticketDescriptionArea.getText();
    	
    	 
    	// Generate a UUID (Universally Unique Identifier)
    	String uuid = UUID.randomUUID().toString();
    	        
    	// Remove hyphens and any other special characters
    	String ticketID = uuid.replaceAll("-", "");
    	
    
    	
    	//TODO if empty dont SAVE
    	 if (selectedProject.isEmpty() || ticketName.isEmpty()) {
             // Handle validation or show an error message
    		 //TODO
         } 
    	 else 
         {
             // Insert the project into the database
             insertTicket(selectedProject, ticketName, ticketDesc, ticketID);

             // Provide user feedback that the project was saved successfully
             // Example: showSuccessAlert("Project saved successfully.");

             // After saving the project, you can navigate back to the main page
             navigateToMainPage(event);
         }
    	
    	
    	
    	
       
    }

    /**
     * This method handles the action when the "Cancel" button is clicked.
     * Navigates back to the main page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void cancelButton(ActionEvent event) throws Exception {
        navigateToMainPage(event);
    }

    /**
     * This method navigates back to the main page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    private void navigateToMainPage(ActionEvent event) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/HomePage.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method creates the "projects" table in the database if it doesn't exist.
     */
    private void createTable() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
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
    }

    /**
     * This method inserts a new project into the database.
     * @param projectName The name of the project.
     * @param ticketID 
     * @param projectDate The date of the project.
     * @param projectDescription The description of the project.
     * @param ticketID UUID of ticket which connects comments and tickets
     */
    private void insertTicket(String projectName, String ticketName, String ticketDescription, String ticketID) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            String insertQuery = "INSERT INTO tickets (project_name, ticket_name, ticket_description, ticketID) VALUES (?, ?, ?, ?)"; // Updated table name to "tickets"
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, projectName);
                preparedStatement.setString(2, ticketName); // Store date as a string
                preparedStatement.setString(3, ticketDescription);
                preparedStatement.setString(4, ticketID);
                

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // The data was successfully inserted
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Project data not saved.");
        }
    }
    
    /**
     * This method initializes the New Project Page by setting the default date and creating the database table.
     * @param location The location used to resolve the root object.
     * @param resources The resources used to localize the root object.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
        createTable(); // Call the createTable method when the application initializes
        ticketDescriptionArea.setFocusTraversable(false); 
     	ObservableList<String> projectNames =  FXCollections.observableArrayList(getProjectNames("projects", "project_name"));
     	listProjects.setItems(projectNames);
		
	}
	
	/**
     * This method handles the action when the "Clear" button is clicked.
     * Clears the input fields and sets the date to the default value.
     * @param event The action event.
     * @throws Exception If clearing the fields fails.
     */
	 @FXML
	 public void clearButton(ActionEvent event) throws Exception 
	 {
		  ticketNameField.clear();
		  ticketDescriptionArea.clear();
		  listProjects.valueProperty().set(null);

	       
	 }
		
		 /**
	     * This method retrieves a list of project names from the specified database table and column.
	     * @param table The database table name.
	     * @param col The column containing project names.
	     * @return A list of project names.
	     */
		public List<String> getProjectNames(String table, String col) 
		{
			List<String> returnStringList = new ArrayList<String>();
			
			try 
			{
				Connection connection = DriverManager.getConnection(jdbcUrl);
				
				PreparedStatement pstmt = connection.prepareStatement("SELECT " + col + " FROM " + table);
				ResultSet resultSet = pstmt.executeQuery();
			        
			        while (resultSet.next()) {
			            String value = resultSet.getString(col);
			            returnStringList.add(value);
			        }

			        resultSet.close();
			        pstmt.close();
			        connection.close();
				
			}

		 catch (SQLException e) 
			{
	        e.printStackTrace();
	        System.err.println("Error retrieving column data: " + e.getMessage());
	    }
			
			Collections.sort(returnStringList, String.CASE_INSENSITIVE_ORDER);
			
			return returnStringList;
			
		}
		
	
}