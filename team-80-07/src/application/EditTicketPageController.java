package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class EditTicketPageController 
{

    @FXML
    private TextField projectNameField;

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

    private Ticket storedTicket;
    
    private Project storedProject;
    
    private boolean fromProjectController = false;

    /**
     * This method handles the action when the "Save" button is clicked.
     * Creates a new project and saves it to the database.
     * 
     * @param event The action event.
     * @throws Exception If saving the project or navigation fails.
     */
    public void saveTicket(ActionEvent event) throws Exception {

        String selectedProject = projectNameField.getText();
        String ticketName = ticketNameField.getText();
        String ticketDesc = ticketDescriptionArea.getText();
        String ticketID = storedTicket.getTicketID();

        // TODO if empty dont SAVE
        if (selectedProject.isEmpty() || ticketName.isEmpty()) 
        {
            // Handle validation or show an error message
            // TODO
        } 
        else 
        {
            // Insert the project into the database
            editTicket(selectedProject, ticketName, ticketDesc, ticketID);

            // Provide user feedback that the project was saved successfully
            // Example: showSuccessAlert("Project saved successfully.");

            // After saving the project, you can navigate back to the main page
            if(fromProjectController)
            {
            	fromProjectController = false;
            	navigateToProjectPage(event);
            	
            }
            else
            {
            	navigateToTicketPage(event);
            }
        }

    }

    /**
     * This method handles the action when the "Cancel" button is clicked.
     * Navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void cancelButton(ActionEvent event) throws Exception {
    	if(fromProjectController)
        {
        	fromProjectController = false;
        	navigateToProjectPage(event);
        	
        }
        else
        {
        	navigateToTicketPage(event);
        }
    }

    /**
     * This method navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    private void navigateToTicketPage(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        loader.setLocation(getClass().getResource("ShowTicketPage.fxml"));
        root = loader.load();

        ShowTicketPageController controller = loader.getController();
        controller.initData(storedTicket);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    private void navigateToProjectPage(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        loader.setLocation(getClass().getResource("ShowProjectPage2.fxml"));
        root = loader.load();

        ShowProjectPageController2 controller = loader.getController();
        controller.initData(storedProject);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This method creates the "tickets" table in the database if it doesn't exist.
     * The table includes columns for ticket ID, project name, ticket name, ticket
     * description, and ticket identifier.
     */
    private void createTable() {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
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
     * 
     * @param projectName        The name of the project.
     * @param ticketID
     * @param projectDate        The date of the project.
     * @param projectDescription The description of the project.
     * @param ticketID           UUID of ticket which connects comments and tickets
     */
    private void editTicket(String projectName, String ticketName, String ticketDescription, String ticketID) {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
            String updateQuery = "UPDATE tickets SET ticket_name = ?, ticket_description = ? WHERE ticketID = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, ticketName);
                preparedStatement.setString(2, ticketDescription);
                preparedStatement.setString(3, ticketID); // assuming ticketID is the unique identifier

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) 
                {
                    System.out.println("Ticket updated successfully.");
                    
                    storedTicket.setTicketDescription(ticketDescription);
                    storedTicket.setTicketName(ticketName);
                    
                   
              
                   
                    
                    //TODO get the project details from projectname and store in storedProject
                    
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error: " + e.getMessage());
            System.err.println("Ticket not updated.");
        }
        
    }

    /**
     * This method handles the action when the "Clear" button is clicked.
     * Clears the input fields and sets the date to the default value.
     * 
     * @param event The action event.
     * @throws Exception If clearing the fields fails.
     */
    @FXML
    public void clearButton(ActionEvent event) throws Exception {
    	  projectNameField.setText(storedTicket.getProjects());
          projectNameField.setFocusTraversable(false);

          ticketNameField.setText(storedTicket.getTicketName()); 

          ticketDescriptionArea.setText(storedTicket.getTicketDescription());
          ticketDescriptionArea.setFocusTraversable(false);

    }

    /**
     * This method retrieves a list of project names from the specified database
     * table and column.
     * 
     * @param table The database table name.
     * @param col   The column containing project names.
     * @return A list of project names.
     */
    public List<String> getProjectNames(String table, String col) {
        List<String> returnStringList = new ArrayList<String>();

        try {
            Connection connection = DatabaseConnection.getSingleInstance().getConnection();

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

        catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error retrieving column data: " + e.getMessage());
        }

        Collections.sort(returnStringList, String.CASE_INSENSITIVE_ORDER);

        return returnStringList;

    }

    /**
     * Initializes the user interface with data from the provided ticket and ensures
     * the "tickets" table exists in the database.
     *
     * @param passedInTicket The ticket containing data to initialize the UI.
     * @param passedInProject 
     * @param fromProjectController2 
     */
    public void initData(Ticket passedInTicket, Project passedInProject, boolean fromProjectController2) 
    {
    	if(fromProjectController2)
    	{
    		fromProjectController = fromProjectController2;
    		storedProject = passedInProject;
    	}
    	
        storedTicket = passedInTicket;

        createTable(); // Call the createTable method when the application initializes
        projectNameField.setText(passedInTicket.getProjects());
        projectNameField.setFocusTraversable(false);

        ticketNameField.setText(passedInTicket.getName());
        ticketNameField.setFocusTraversable(false);

        ticketDescriptionArea.setText(passedInTicket.getTicketDescription());
        ticketDescriptionArea.setFocusTraversable(false);

    }

}