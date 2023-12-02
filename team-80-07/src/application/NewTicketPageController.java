package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * This class handles creating and saving new projects to a SQLite database.
 */
public class NewTicketPageController implements Initializable {

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
    
    private Project storedProject;
    
    private boolean fromProjectController = false;

	/**
	 * This method handles the action when the "Save" button is clicked. Creates a
	 * new project and saves it to the database.
	 * 
	 * @param event The action event.
	 * @throws Exception If saving the project or navigation fails.
	 */
	public void saveTicket(ActionEvent event) throws Exception {

		String selectedProject = listProjects.getValue();
		String ticketName = ticketNameField.getText();
		String ticketDesc = ticketDescriptionArea.getText();

		String uuid = UUID.randomUUID().toString();

		String ticketID = uuid.replaceAll("-", "");

		try {

			if (selectedProject.isEmpty() || ticketName.isEmpty()) {
				
				showAlert("Validation Error", "Selected project and ticket name are required fields.");
				
			} else {

				insertTicket(selectedProject, ticketName, ticketDesc, ticketID);

				System.out.print("Ticket saved successfully.");

				if (fromProjectController) {
					fromProjectController = false;
					navigateToProjectPage(event);

				} else {
					navigateToMainPage(event);
				}
			}

		} catch (NullPointerException e) {

		}

	}
    
	/**
	 * Displays an error alert with the specified title and message.
	 *
	 * @param title   The title of the error alert.
	 * @param message The detailed error message to be displayed.
	 */
	private void showAlert(String title, String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}


    /**
     * This method handles the action when the "Cancel" button is clicked.
     * Navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void cancelButton(ActionEvent event) throws Exception 
    {
    	
    	if(fromProjectController)
        {
        	fromProjectController = false;
        	navigateToProjectPage(event);
        	
        }
        else
        {
        	navigateToMainPage(event);
        }
    }

    /**
     * This method navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    private void navigateToMainPage(ActionEvent event) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * This method navigates back to the main page.
     * 
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    private void navigateToProjectPage(ActionEvent event) throws Exception {
    	 FXMLLoader loader = new FXMLLoader();
			Parent root = null;

			loader.setLocation(getClass().getResource("ShowProjectPage2.fxml"));
			root = loader.load();

			ShowProjectPageController2 controller = loader.getController();
			controller.initData(storedProject);

			Stage stage = (Stage) clearButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
    }

    /**
     * Creates the "tickets" table in the database if it doesn't exist.
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
    private void insertTicket(String projectName, String ticketName, String ticketDescription, String ticketID) {
        try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
            String insertQuery = "INSERT INTO tickets (project_name, ticket_name, ticket_description, ticketID) VALUES (?, ?, ?, ?)"; // Updated
                                                                                                                                      // table
                                                                                                                                      // name
                                                                                                                                      // to
                                                                                                                                      // "tickets"
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
     * This method initializes the New Project Page by setting the default date and
     * creating the database table.
     * 
     * @param location  The location used to resolve the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable(); // Call the createTable method when the application initializes
        ticketDescriptionArea.setFocusTraversable(false);
        ObservableList<String> projectNames = FXCollections
                .observableArrayList(getProjectNames("projects", "project_name"));
        listProjects.setItems(projectNames);

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
        ticketNameField.clear();
        ticketDescriptionArea.clear();
        listProjects.valueProperty().set(null);

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
     * Initializes data for the controller with the provided project.
     * Sets the stored project, updates the selected value in the projects list,
     * and sets the flag indicating if called from the project controller.
     *
     * @param passedInProject The project to initialize the data with.
     */
    public void initData(Project passedInProject) {
        storedProject = passedInProject;
        listProjects.setValue(passedInProject.getProjectName());
        fromProjectController = true;
    }


}