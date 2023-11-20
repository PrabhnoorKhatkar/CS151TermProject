package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ShowProjectPageController2 {

	@FXML
	private TextField projectNameField;

	@FXML
	private Button editProject;

	@FXML
	private Button editTicket;
	@FXML
	private Button deleteProject;
	@FXML
	private Button deleteTicket;
	@FXML
	private Button selectTicket;

	@FXML
	private Button backButton;

	@FXML
	private Button addTicketButton;

	@FXML
	private Label projectNameDisplay;

	private Project passedInProject;

	private Ticket selectedTicket;

	@FXML
	private ListView<Ticket> ticketList = new ListView<Ticket>();

	/**
	 * Initializes the display and the instance variable to the selected ticket
	 * 
	 * @param selectedProject passed in ticket from searchTicketPage
	 */
	public void initData(Project selectedProject) {
		passedInProject = selectedProject;
		editTicket.setVisible(false);
		deleteTicket.setVisible(false);
		projectNameDisplay.setText(passedInProject.getName());

		createTable();

		try {
			// Initialize the activeProjects list view with project names
			ObservableList<Ticket> listOfTickets = FXCollections
					.observableArrayList(retrieveTickets(passedInProject.getProjectName()));
			ticketList.setItems(listOfTickets);

			ticketList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Ticket>() {
				@Override
				public void changed(ObservableValue<? extends Ticket> observable, Ticket oldValue, Ticket newValue) {
					selectedTicket = ticketList.getSelectionModel().getSelectedItem();
					editTicket.setVisible(true);
					deleteTicket.setVisible(true);
				}
			});

			// Set up a custom cell factory to display only the project name in the ListView
			ticketList.setCellFactory(listView -> new ListCell<Ticket>() {
				@Override
				protected void updateItem(Ticket item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
					} else {
						setText(item.toString());
					}
				}
			});

		} catch (NullPointerException e) {
			System.out.println("FAIL??");
		}

	}

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

	public void editTicket(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = null;

		loader.setLocation(getClass().getResource("EditTicketPage.fxml"));
		root = loader.load();
		
		boolean fromProjectController = true;
		
		EditTicketPageController controller = loader.getController();
		controller.initData(selectedTicket, passedInProject, fromProjectController);

		Stage stage = (Stage) editProject.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Handles the action event for editing a project.
	 * Loads the "EditProjectPage.fxml" file and initializes the controller with
	 * data from the current project.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws Exception If an error occurs during the loading or initialization
	 *                   process.
	 */
	public void editProject(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = null;

		loader.setLocation(getClass().getResource("EditProjectPage.fxml"));
		root = loader.load();

		EditProjectPageController controller = loader.getController();
		controller.initData(passedInProject);

		Stage stage = (Stage) editProject.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Deletes ticket and warns users plenty of times it will be deleted
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void deleteTicket(ActionEvent event) throws Exception {
		if (selectedTicket != null) {
			String ticketID = selectedTicket.getTicketID();

			if (ticketID != null && !ticketID.isEmpty()) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete Ticket");
				alert.setHeaderText("Confirm Deletion");
				alert.setContentText("Are you sure you want to delete this ticket?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

					Ticket.deleteTicket(ticketID);
					resetTicketListListener(); // Reset the listener
					refreshTicketList(); // Refresh the comment list to reflect the deletion
				}
			} else {

				System.out.println("Invalid ticket name");
			}
		}
	}

	public void addTicket(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = null;

		loader.setLocation(getClass().getResource("NewTicketPage.fxml"));
		root = loader.load();

		NewTicketPageController controller = loader.getController();
		controller.initData(passedInProject);

		Stage stage = (Stage) editProject.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Handles the action event for deleting a project.
	 * Deletes the project, its associated tickets, and comments.
	 * Navigates back to the home page.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws Exception If an error occurs during the deletion or navigation
	 *                   process.
	 */
	public void deleteProject(ActionEvent event) throws Exception {
		if (passedInProject != null) {
			String projectName = passedInProject.getProjectName();

			if (projectName != null && !projectName.isEmpty()) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete Project");
				alert.setHeaderText("Confirm Deletion");
				alert.setContentText(
						"Are you sure you want to delete this project? This action will also delete associated tickets and comments.");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					// Delete the project, associated tickets, and comments
					Project.deleteProjectContent(projectName);

					// Navigate back to the home page
					backButton(event);
				}
			} else {
				System.out.println("Invalid project name");
			}
		}
	}

	/**
	 * Handles the action event when a ticket is selected. Loads the
	 * ShowTicketPage.fxml, initializes its controller with data from the selected
	 * ticket, and displays it in a new stage.
	 *
	 * @param event The ActionEvent triggering the method.
	 * @throws Exception If an exception occurs during the loading or initialization
	 *                   process.
	 */
	public void selectTicket(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = null;

		loader.setLocation(getClass().getResource("ShowTicketPage.fxml"));
		root = loader.load();
		ShowTicketPageController controller = loader.getController();
		controller.initData((Ticket) selectedTicket);

		Stage stage = (Stage) selectTicket.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Retrieves the list of projects and tickets from the database.
	 *
	 * @return A list of ProjectItem objects representing projects and tickets.
	 */
	public List<Ticket> retrieveTickets(String projectName) {
		List<Ticket> ticketList = new ArrayList<>();

		try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
			// The table to retrieve ticket data
			String ticketQuery = "SELECT project_name, ticket_name, ticket_description, ticketID FROM tickets WHERE project_name = ?";

			try (PreparedStatement ticketStatement = connection.prepareStatement(ticketQuery)) {
				ticketStatement.setString(1, projectName); // Set the project name parameter
				try (ResultSet ticketResultSet = ticketStatement.executeQuery()) {
					while (ticketResultSet.next()) {
						String ticketName = ticketResultSet.getString("ticket_name");
						String ticketDescription = ticketResultSet.getString("ticket_description");
						String ticketID = ticketResultSet.getString("ticketID");
						Ticket ticket = new Ticket(projectName, ticketName, ticketDescription, ticketID);
						ticketList.add(ticket);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketList;
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

	private void refreshTicketList() {
		ObservableList<Ticket> updatedListOfTicket = FXCollections
				.observableArrayList(retrieveTickets(passedInProject.getProjectName()));
		ticketList.setItems(updatedListOfTicket);

		if (updatedListOfTicket.isEmpty()) {
			editTicket.setVisible(false);
			deleteTicket.setVisible(false);

		}

	}

	private void resetTicketListListener() {
		ticketList.getSelectionModel().selectedItemProperty().removeListener(this::ticketSelectionChanged);
		ticketList.getSelectionModel().selectedItemProperty().addListener(this::ticketSelectionChanged);
	}

	private void ticketSelectionChanged(ObservableValue<? extends Ticket> observable, Ticket oldValue,
			Ticket newValue) {
		selectedTicket = ticketList.getSelectionModel().getSelectedItem();
		editTicket.setVisible(selectedTicket != null);
		deleteTicket.setVisible(selectedTicket != null);
	}

}
