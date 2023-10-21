package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

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

  
    public void onInputMethodTextChangedProperty(ActionEvent event)
    {
    	String searchInput = ticketNameField.getText();
    	ProjectsAndTickets.getItems().clear();
    	ProjectsAndTickets.getItems().addAll(searchProjectsAndTickets(searchInput, getProjectsAndTickets("projects", "project_name", "tickets", "ticket_name")));
    	ProjectsAndTickets.getSelectionModel().selectedItemProperty().addListener(new Event);
    	
    	
    	
    }
    
    
    private String searchProjectsAndTickets(String searchInput, List<String> projectsAndTickets2) 
    {
	
    	
    	
		return null;
	}


    
    private List<String> getProjectsAndTickets(String string, String string2, String string3, String string4) 
    {
		
    	
    	
    	
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
		ObservableList<String> projectAndTicketNames =  FXCollections.observableArrayList(getProjectsAndTickets("projects", "project_name", "tickets", "ticket_name"));
		ProjectsAndTickets.setItems(projectAndTicketNames);
     
		
	}
	
		
}