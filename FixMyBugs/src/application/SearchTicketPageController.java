package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class SearchTicketPageController implements Initializable
{

	@FXML
    private Button backButton;
	
	@FXML
    private TextField ticketNameField;
	
	@FXML
    private Button searchButton;
	
	@FXML
	private String selectedTicketOrProject;
	
	@FXML
	private Button selectButton;
	
	@FXML
    private ListView<String> ProjectsAndTickets = new ListView<String>();
	
	

    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";

 
	  /**
     * This method handles the action when the "Back" button is clicked.
     * Navigates back to the home page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void backButton(ActionEvent event) throws Exception {
    	 Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
         Stage stage = (Stage) backButton.getScene().getWindow();
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }
    
    /**
     * This method handles the action when the "Back" button is clicked.
     * Navigates back to the home page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void selectButton(ActionEvent event) throws Exception {
    	 Parent root = FXMLLoader.load(getClass().getResource("ShowTicket.fxml"));
         Stage stage = (Stage) backButton.getScene().getWindow();
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }

  
    public void onInputMethodTextChangedProperty(ActionEvent event)
    {
    	String searchInput = ticketNameField.getText();
    	ProjectsAndTickets.getItems().clear();
    	ProjectsAndTickets.getItems().addAll(searchProjectsAndTickets(searchInput, retrieveProjectAndTicketNames()));
    	ProjectsAndTickets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
				selectedTicketOrProject = ProjectsAndTickets.getSelectionModel().getSelectedItem();
				
			}
    		
    	});
    	
  
    }
    
    
    private List<String> searchProjectsAndTickets(String searchInput, List<String> projectsAndTickets2) 
    {
    	
    	   return projectsAndTickets2.stream().filter(name -> name.toLowerCase().contains(searchInput.toLowerCase())).collect(Collectors.toList());

		
	}


 

	/**
     * This method initializes the New Project Page by setting the default date and creating the database table.
     * @param location The location used to resolve the root object.
     * @param resources The resources used to localize the root object.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		// Initialize the projectAndTicketNames list view with all project names and ticket names
		ObservableList<String> projectAndTicketNames =  FXCollections.observableArrayList(retrieveProjectAndTicketNames());
		ProjectsAndTickets.setItems(projectAndTicketNames);
     
		
	}
	
	  public List<String> retrieveProjectAndTicketNames() 
	  {
	        List<String> result = new ArrayList<>();

	        try (Connection connection = DriverManager.getConnection(jdbcUrl)) 
	        {
	            // Query the projects table to retrieve project names
	            String projectQuery = "SELECT project_name FROM projects";
	            
	            try (PreparedStatement projectStatement = connection.prepareStatement(projectQuery);
	            		
	                 ResultSet projectResultSet = projectStatement.executeQuery()) 
	            {
	                while (projectResultSet.next()) 
	                {
	                    String projectName = projectResultSet.getString("project_name");
	                    result.add(projectName);

	                    // Query the tickets table for associated ticket names
	                    String ticketQuery = "SELECT ticket_name FROM tickets WHERE project_name = ?";
	                    try (PreparedStatement ticketStatement = connection.prepareStatement(ticketQuery)) 
	                    {
	                        ticketStatement.setString(1, projectName);
	                        try (ResultSet ticketResultSet = ticketStatement.executeQuery()) 
	                        {
	                            while (ticketResultSet.next()) 
	                            {
	                                String ticketName = ticketResultSet.getString("ticket_name");
	                                result.add(ticketName);
	                            }
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return result;
	    }
	
	
		
}