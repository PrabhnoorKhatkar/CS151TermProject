package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
 * Controller for the New Project Page in the JavaFX application. This class
 * handles creating and saving new projects to a SQLite database.
 */
public class EditCommentPageController {

	@FXML
	private Button saveCommentButton;

	@FXML
	private TextField autoPopulatedTicketName;

	@FXML
	private TextArea commentDescriptionArea;

	@FXML
	private TextField timestampTextField;

	@FXML
	private Button cancelButton;

	@FXML
	private Button clearButton;

	private Ticket storedTicket;

	private Comment storedComment;

	public void initData(Comment passedInComment) {

		storedComment = passedInComment;

	
		createTable();
		getTicket();

		autoPopulatedTicketName.setText(storedTicket.getTicketName());
		autoPopulatedTicketName.setFocusTraversable(false);

		timestampTextField.setText(passedInComment.getTimestamp());
		timestampTextField.setFocusTraversable(false);

		commentDescriptionArea.setText(passedInComment.getDescription());
		commentDescriptionArea.setFocusTraversable(false);

	}

	public void getTicket() {
		String ticketID = storedComment.getTicketID();
		try {
			Connection connection = DatabaseConnection.getSingleInstance().getConnection();

			String sql = "SELECT project_name, ticket_name, ticket_description FROM tickets WHERE ticketID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, ticketID);

			ResultSet resultSet = pstmt.executeQuery();

			if (resultSet.next()) {
				String projectName = resultSet.getString("project_name");
				String ticketName = resultSet.getString("ticket_name");
				String ticketDescription = resultSet.getString("ticket_description");

				Ticket ticket = new Ticket(projectName, ticketName, ticketDescription, ticketID);
				storedTicket = ticket;
			}

			resultSet.close();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error retrieving ticket details: " + e.getMessage());
		}

	}

	public void saveComment(ActionEvent event) throws Exception 
	{
		
       String newCommentDescription = commentDescriptionArea.getText();
       
       LocalDateTime currentDateTime = LocalDateTime.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Change the pattern as
                                                                                         // needed

       String newTimestamp = currentDateTime.format(formatter);
       
       editComment(storedComment.getTicketID(), storedComment.getTimestamp(), newTimestamp, newCommentDescription);
       
       // After saving the project, you can navigate back to the main page
       navigateToTicketPage(event);
       
      
		
	}

	private void editComment(String ticketID, String oldTimestamp, String newTimestamp, String newCommentDescription) 
	{
	    try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
	        String updateQuery = "UPDATE comments SET timestamp = ?, comment_description = ? WHERE timestamp = ?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
	            preparedStatement.setString(1, newTimestamp);
	            preparedStatement.setString(2, newCommentDescription);
	            preparedStatement.setString(3, oldTimestamp); 

	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Comment updated successfully.");

	                storedComment.setTimestamp(newTimestamp);
	                storedComment.setDescripton(newCommentDescription);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Database connection error: " + e.getMessage());
	        System.err.println("Comment not updated.");
	    }
	}

	/**
	 * This method handles the action when the "Cancel" button is clicked. Navigates
	 * back to the main page.
	 *
	 * @param event The action event.
	 * @throws Exception If navigation fails.
	 */
	@FXML
	public void cancelButton(ActionEvent event) throws Exception {
		navigateToTicketPage(event);
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

	/**
	 * This method handles the action when the "Clear" button is clicked. Clears the
	 * input fields and sets the date to the default value.
	 *
	 * @param event The action event.
	 * @throws Exception If clearing the fields fails.
	 */
	@FXML
	public void clearButton(ActionEvent event) throws Exception {

		autoPopulatedTicketName.setText(storedTicket.getTicketName());
		autoPopulatedTicketName.setFocusTraversable(false);

		timestampTextField.setText(storedComment.getTimestamp());
		timestampTextField.setFocusTraversable(false);

		commentDescriptionArea.setText(storedComment.getDescription());
		commentDescriptionArea.setFocusTraversable(false);

	}
	

	/**
	 * Creates the "comments" table in the database if it doesn't exist. The table
	 * includes columns for comment ID, ticket ID, timestamp, and comment
	 * description.
	 */
	private void createTable() {
		try (Connection connection = DatabaseConnection.getSingleInstance().getConnection()) {
			String createTableSQL = "CREATE TABLE IF NOT EXISTS comments (" + // Updated table name to "tickets"
					"id INTEGER PRIMARY KEY AUTOINCREMENT," + "ticketID TEXT NOT NULL," + "timestamp TEXT NOT NULL,"
					+ "comment_description TEXT NOT NULL" + ")";
			try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error creating the table: " + e.getMessage());
		}
	}

}