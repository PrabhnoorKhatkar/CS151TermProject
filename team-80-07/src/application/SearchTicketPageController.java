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
import javafx.scene.control.ListCell;
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
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import application.Project;
import application.ProjectItem;
import application.Ticket;

/**
 * Controller for the New Project Page in the JavaFX application. This class
 * handles creating and saving new projects to a SQLite database.
 */
public class SearchTicketPageController implements ProjectItem, Initializable {

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
	 * This method handles the action when the "Back" button is clicked. Navigates
	 * back to the home page.
	 * 
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
	 * This method handles the action when the "Back" button is clicked. Navigates
	 * back to the home page.
	 * 
	 * @param event The action event.
	 * @throws Exception If navigation fails.
	 */
	@FXML
	public void selectButton(ActionEvent event) throws Exception 
	{
		
		 if (selectedTicketOrProject != null) {
		        FXMLLoader loader = new FXMLLoader();
		        Parent root = null;

		        if (selectedTicketOrProject instanceof Project) 
		        {
		            loader.setLocation(getClass().getResource("ShowProjectPage.fxml"));
		            root = loader.load();
		        } 
		        else if (selectedTicketOrProject instanceof Ticket)
		        {
		            loader.setLocation(getClass().getResource("ShowTicketPage.fxml"));
		            root = loader.load();
		            ShowTicketPageController controller = loader.getController();
		            controller.initData((Ticket) selectedTicketOrProject);
		        }

		        Stage stage = (Stage) selectButton.getScene().getWindow();
		        Scene scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();
		    }
	
	}
	/**
	 * When search button is pressed it clears the ListView and gets a new list of tickets and projects with the gotten substring search word
	 * @param event
	 */
	public void onInputMethodTextChangedProperty(ActionEvent event) {
		String searchInput = ticketNameField.getText();
		ProjectsAndTickets.getItems().clear();
		ProjectsAndTickets.getItems().addAll(searchProjectsAndTickets(searchInput, retrieveProjectsAndTickets()));
		ProjectsAndTickets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProjectItem>() {

			@Override
			public void changed(ObservableValue<? extends ProjectItem> observable, ProjectItem oldValue, ProjectItem newValue) {
				selectedTicketOrProject = (ProjectItem) ProjectsAndTickets.getSelectionModel().getSelectedItem();
				
				

			}

		});
		
		
	}
	/**
	 * This iterates through the list and looks for all names of tickets and projects that contain the search word and return a new list that holds the substring searched list
	 * @param searchInput word to find substring
	 * @param passInprojectsAndTickets the full list of all tickets and projects
	 * @return List<ProjectItem> both tickets and projects in one List
	 */
	private List<ProjectItem> searchProjectsAndTickets(String searchInput, List<ProjectItem> passInprojectsAndTickets) {

		// return ((SearchTicketPageController)
		// passInprojectsAndTickets).getName().stream().filter(name ->
		// name.toLowerCase().contains(searchInput.toLowerCase())).collect(Collectors.toList());

		return passInprojectsAndTickets.stream().filter(item -> item.getName().toLowerCase().contains(searchInput.toLowerCase())).collect(Collectors.toList());

	}

	/**
	 * This method initializes the New Project Page by setting the default date and
	 * creating the database table.
	 * 
	 * @param location  The location used to resolve the root object.
	 * @param resources The resources used to localize the root object.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Initialize the projectAndTicketNames list view with all project names and
		// ticket names
		ObservableList<ProjectItem> projectAndTicketNames = FXCollections.observableArrayList(retrieveProjectsAndTickets());

		ProjectsAndTickets.setItems(projectAndTicketNames);
		ProjectsAndTickets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProjectItem>() {

			@Override
			public void changed(ObservableValue<? extends ProjectItem> observable, ProjectItem oldValue, ProjectItem newValue) {
				selectedTicketOrProject = (ProjectItem) ProjectsAndTickets.getSelectionModel().getSelectedItem();
				
				

			}

		});
		
		// Set up a custom cell factory to display only the project name in the ListView
	    ProjectsAndTickets.setCellFactory(listView -> new ListCell<ProjectItem>() 
	    {
	        @Override
	        protected void updateItem(ProjectItem item, boolean empty) 
	        {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.getName());
	            }
	        }
	    });

	}

	/**
	 * Retrieves the list of projects and tickets from the database.
	 *
	 * @return A list of ProjectItem objects representing projects and tickets.
	 */
	public List<ProjectItem> retrieveProjectsAndTickets() 
	{
		List<ProjectItem> projectAndTicketList = new ArrayList<>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try (Connection connection = DriverManager.getConnection(jdbcUrl)) 
		{
			//The table to retrieve project data
			String projectQuery = "SELECT project_name, project_date, project_description FROM projects";

			try (PreparedStatement projectStatement = connection.prepareStatement(projectQuery); ResultSet projectResuitSet = projectStatement.executeQuery()) 
			{
				
				while (projectResuitSet.next()) 
				{
					String projectName = projectResuitSet.getString("project_name");
					Date projectDate = dateFormat.parse(projectResuitSet.getString("project_date")); 
					String projectDescription = projectResuitSet.getString("project_description");
					Project project = new Project(projectName,
							projectDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), projectDescription);
					projectAndTicketList.add(project);
				}
			}
			// The table to retrieve ticket data
			String ticketQuery = "SELECT project_name, ticket_name, ticket_description, ticketID FROM tickets";
			
			try (PreparedStatement ticketStatement = connection.prepareStatement(ticketQuery); ResultSet ticketResultSet = ticketStatement.executeQuery()) 
			{
				while (ticketResultSet.next()) 
				{
					String projectName = ticketResultSet.getString("project_name");
					String ticketName = ticketResultSet.getString("ticket_name");
					String ticketDescription = ticketResultSet.getString("ticket_description");
					String ticketID = ticketResultSet.getString("ticketID");
					Ticket ticket = new Ticket(projectName, ticketName, ticketDescription, ticketID);
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
	public String getName() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}