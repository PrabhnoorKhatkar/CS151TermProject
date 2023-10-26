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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class SearchTicketPageController extends ProjectItem implements Initializable
{

	@FXML
    private Button backButton;
	
	@FXML
    private TextField ticketNameField;
	
	@FXML
    private Button searchButton;
	
	@FXML
	private ProjectItem selectedTicketOrProject;
	
	@FXML
	private Button selectButton;
	
	@FXML
    private ListView<ProjectItem> ProjectsAndTickets = new ListView<ProjectItem>();

	
	

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
    	ProjectsAndTickets.getItems().addAll(searchProjectsAndTickets(searchInput, retrieveProjectsAndTickets()));
    	ProjectsAndTickets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProjectItem>() {

		
			@Override
			public void changed(ObservableValue<? extends ProjectItem> observable, ProjectItem oldValue,
					ProjectItem newValue) {
				selectedTicketOrProject = (ProjectItem) ProjectsAndTickets.getSelectionModel().getSelectedItem();
				
			}
    		
    	});
    	
  
    }
    
    
    private List<ProjectItem> searchProjectsAndTickets(String searchInput, List<ProjectItem> passInprojectsAndTickets) 
    {
    	
    	  // return passInprojectsAndTickets.getName().stream().filter(name -> name.toLowerCase().contains(searchInput.toLowerCase())).collect(Collectors.toList());
return null;
		
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
		ObservableList<ProjectItem> projectAndTicketNames =  FXCollections.observableArrayList(retrieveProjectsAndTickets());
		
		ProjectsAndTickets.setItems(projectAndTicketNames);
     
		
	}
	
	
	
	 public List<ProjectItem> retrieveProjectsAndTickets() 
	 {
	        List<ProjectItem> projectAndTicketList = new ArrayList<>();

	        try (Connection connection = DriverManager.getConnection(jdbcUrl))  {
	            // Query the projects table to retrieve project data
	            String projectQuery = "SELECT project_name, project_date, project_description FROM projects";

	            try (PreparedStatement projectStatement = connection.prepareStatement(projectQuery);
	                 ResultSet projectResultSet = projectStatement.executeQuery()) {
	                while (projectResultSet.next()) {
	                    String projectName = projectResultSet.getString("project_name");
	                    LocalDate projectDate = projectResultSet.getDate("project_date").toLocalDate();
	                    String projectDescription = projectResultSet.getString("project_description");

	                    Project project = new Project(projectName, projectDate, projectDescription);
	                    projectAndTicketList.add(project);
	                }
	            }

	            // Query the tickets table to retrieve ticket data
	            String ticketQuery = "SELECT project_name, ticket_name, ticket_description FROM tickets";

	            try (PreparedStatement ticketStatement = connection.prepareStatement(ticketQuery);
	                 ResultSet ticketResultSet = ticketStatement.executeQuery()) {
	                while (ticketResultSet.next()) {
	                    String projectName = ticketResultSet.getString("project_name");
	                    String ticketName = ticketResultSet.getString("ticket_name");
	                    String ticketDescription = ticketResultSet.getString("ticket_description");

	                    Ticket ticket = new Ticket(projectName, ticketName, ticketDescription);
	                    projectAndTicketList.add(ticket);
	                }
	            }
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }

	        return projectAndTicketList;
	    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}



	
	
		
}