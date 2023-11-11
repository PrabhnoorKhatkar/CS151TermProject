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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the New Project Page in the JavaFX application. This class
 * handles creating and saving new projects to a SQLite database.
 */
public class SearchProjectPageController implements Initializable {

	@FXML
	private Button backButton;

	@FXML
	private TextField projectNameField;

	@FXML
	private Button searchButton;

	@FXML
	private Project selectedProject;

	@FXML
	private Button selectButton;

	@FXML
	private ListView<Project> ProjectsList = new ListView<Project>();

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
	public void selectButton(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		Parent root = null;

		loader.setLocation(getClass().getResource("ShowProjectPage2.fxml"));
		root = loader.load();

		ShowProjectPageController2 controller = loader.getController();
		controller.initData(selectedProject);

		Stage stage = (Stage) selectButton.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * When search button is pressed it clears the ListView and gets a new list of
	 * tickets and projects with the gotten substring search word
	 * 
	 * @param event
	 */
	public void searchSubstring(ActionEvent event) {
		String searchInput = projectNameField.getText();
		ProjectsList.getItems().clear();
		ProjectsList.getItems().addAll(searchProjects(searchInput, retrieveProjects()));
		ProjectsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {

			@Override
			public void changed(ObservableValue<? extends Project> observable, Project oldValue,
					Project newValue) {
				selectedProject = ProjectsList.getSelectionModel().getSelectedItem();

			}

		});

	}

	/**
	 * This iterates through the list and looks for all names of tickets and
	 * projects that contain the search word and return a new list that holds the
	 * substring searched list
	 * 
	 * @param searchInput              word to find substring
	 * @param passInprojectsAndTickets the full list of all tickets and projects
	 * @return List<ProjectItem> both tickets and projects in one List
	 */
	private List<Project> searchProjects(String searchInput, List<Project> passInprojects) {

		// return ((SearchTicketPageController)
		// passInprojectsAndTickets).getName().stream().filter(name ->
		// name.toLowerCase().contains(searchInput.toLowerCase())).collect(Collectors.toList());

		return passInprojects.stream().filter(item -> item.getName().toLowerCase().contains(searchInput.toLowerCase()))
				.collect(Collectors.toList());

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
		ObservableList<Project> projectNames = FXCollections.observableArrayList(retrieveProjects());

		ProjectsList.setItems(projectNames);
		ProjectsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {

			@Override
			public void changed(ObservableValue<? extends Project> observable, Project oldValue,
					Project newValue) {
				selectedProject = ProjectsList.getSelectionModel().getSelectedItem();

			}

		});

		// Set up a custom cell factory to display only the project name in the ListView
		ProjectsList.setCellFactory(listView -> new ListCell<Project>() {
			@Override
			protected void updateItem(Project item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getName());
				}
			}
		});

		// Add a listener to the TextField to trigger searchSubstring on text change
		projectNameField.textProperty().addListener((observable, oldValue, newValue) -> {
			searchSubstring(null);
		});

	}

	/**
	 * Retrieves the list of projects and tickets from the database.
	 *
	 * @return A list of ProjectItem objects representing projects and tickets.
	 */
	public List<Project> retrieveProjects() {
		List<Project> projectList = new ArrayList<>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
			// The table to retrieve project data
			String projectQuery = "SELECT project_name, project_date, project_description FROM projects";

			try (PreparedStatement projectStatement = connection.prepareStatement(projectQuery);
					ResultSet projectResuitSet = projectStatement.executeQuery()) {

				while (projectResuitSet.next()) {
					String projectName = projectResuitSet.getString("project_name");
					Date projectDate = dateFormat.parse(projectResuitSet.getString("project_date"));
					String projectDescription = projectResuitSet.getString("project_description");
					Project project = new Project(projectName,
							projectDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), projectDescription);
					projectList.add(project);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return projectList;
	}

}