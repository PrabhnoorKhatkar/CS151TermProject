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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShowTicketPageController {

	@FXML
	private TextField projectNameField;

	@FXML
	private Button editTicket;

	@FXML
	private Button editComment;

	@FXML
	private Button deleteTicket;

	@FXML
	private Button backButton;

	@FXML
	private Button addCommentButton;
	@FXML
	private Button editCommentButton;
	@FXML
	private Button deleteCommentButton;

	@FXML
	private Label ticketNameDisplay;

	private Ticket passedInTicket;

	private Comment selectedComment;

	@FXML
	private ListView<Comment> commentList = new ListView<Comment>();

	/**
	 * Initializes the display and the instance variable to the selected ticket
	 * 
	 * @param selectedTicketOrProject passed in ticket from searchTicketPage
	 */
	public void initData(Ticket selectedTicketOrProject) {
		editCommentButton.setVisible(false);
		deleteCommentButton.setVisible(false);
		passedInTicket = selectedTicketOrProject;
		ticketNameDisplay.setText(passedInTicket.getTicketName());

		createTable();

		try {
			// Initialize the activeProjects list view with project names
			ObservableList<Comment> listOfComments = FXCollections
					.observableArrayList(getCommentsByTicketID(passedInTicket.getTicketID()));
			commentList.setItems(listOfComments);

			commentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Comment>() {
				@Override
				public void changed(ObservableValue<? extends Comment> observable, Comment oldValue, Comment newValue) {
					selectedComment = (Comment) commentList.getSelectionModel().getSelectedItem();
					editCommentButton.setVisible(true);
					deleteCommentButton.setVisible(true);

				}
			});

			// Set up a custom cell factory to display only the project name in the ListView
			commentList.setCellFactory(listView -> new ListCell<Comment>() {
				@Override
				protected void updateItem(Comment item, boolean empty) {
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

	/**
	 * Handles the action event for editing a ticket. Loads the
	 * "EditTicketPage.fxml" file and initializes the controller with data from the
	 * current ticket.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws Exception If an error occurs during the loading or initialization
	 *                   process.
	 */
	public void editTicket(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = null;

		loader.setLocation(getClass().getResource("EditTicketPage.fxml"));
		root = loader.load();

		EditTicketPageController controller = loader.getController();
		controller.initData(passedInTicket, null, false);

		Stage stage = (Stage) editTicket.getScene().getWindow();
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
	public void deleteTicket(ActionEvent event) throws Exception 
	{
		if (passedInTicket != null) {
			String ticketName = passedInTicket.getTicketID();

			if (ticketName != null && !ticketName.isEmpty()) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete Ticket");
				alert.setHeaderText("Confirm Deletion");
				alert.setContentText("Are you sure you want to delete this ticket?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

					Ticket.deleteTicket(ticketName);
				}
			} else {

				System.out.println("Invalid ticket name");
			}
		}
		backButton(event);
		
	}

	/**
	 * When add comment button is pressed it loads and intilaizes the new page with
	 * the ticket, so it can be used in addcommentPage.fxml
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void addComment(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		Parent root = null;
		loader.setLocation(getClass().getResource("AddCommentPage.fxml"));
		root = loader.load();
		AddCommentPageController controller = loader.getController();

		controller.initData((Ticket) passedInTicket);

		Stage stage = (Stage) addCommentButton.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	/**
     * Edits a comment by loading the `EditCommentPage` and initializing it with the selected comment.
     *
     * @param event The action event.
     * @throws Exception If editing the comment or navigating to the edit page fails.
     */
	public void editComment(ActionEvent event) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		Parent root = null;
		loader.setLocation(getClass().getResource("EditCommentPage.fxml"));
		root = loader.load();
		EditCommentPageController controller = loader.getController();

		controller.initData(selectedComment);

		Stage stage = (Stage) addCommentButton.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	 /**
     * Deletes a comment after confirming the deletion with a dialog.
     *
     * @param event The action event.
     * @throws Exception If deleting the comment or handling the confirmation dialog fails.
     */
	public void deleteComment(ActionEvent event) throws Exception {

		if (selectedComment != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Comment");
			alert.setHeaderText("Confirm Deletion");
			alert.setContentText("Are you sure you want to delete this comment?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				Comment.deleteComment(selectedComment.getTimestamp());
				resetCommentListListener(); // Reset the listener
				refreshCommentList(); // Refresh the comment list to reflect the deletion								
			}
		}
	}

	/**
	 * Retrieves a list of comments associated with a specific ticket ID from the
	 * database.
	 *
	 * @param ticketID The unique identifier of the ticket for which comments are to
	 *                 be retrieved.
	 * @return A List of Comment objects representing comments associated with the
	 *         specified ticket.
	 */
	public List<Comment> getCommentsByTicketID(String ticketID) {
		List<Comment> commentList = new ArrayList<>();

		try {
			Connection connection = DatabaseConnection.getSingleInstance().getConnection();

			String sql = "SELECT timestamp, comment_description FROM comments WHERE ticketID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, ticketID);

			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				String timestamp = resultSet.getString("timestamp");
				String commentDescription = resultSet.getString("comment_description");

				Comment comment = new Comment(commentDescription, timestamp, ticketID);
				commentList.add(comment);
			}

			resultSet.close();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error retrieving comments: " + e.getMessage());
		}

		return commentList;
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

	/**
     * Refreshes the comment list based on the comments associated with the currently selected ticket.
     * If the list is empty, it hides the edit and delete buttons.
     */
	private void refreshCommentList() 
	{
		ObservableList<Comment> updatedListOfComments = FXCollections
				.observableArrayList(getCommentsByTicketID(passedInTicket.getTicketID()));
		commentList.setItems(updatedListOfComments);
		
		if(updatedListOfComments.isEmpty())
		{
			editCommentButton.setVisible(false);
			deleteCommentButton.setVisible(false);			
		}			
	}
	
	/**
     * Resets the listener for comment list selection changes.
     */
	private void resetCommentListListener() {
	    commentList.getSelectionModel().selectedItemProperty().removeListener(this::commentSelectionChanged);
	    commentList.getSelectionModel().selectedItemProperty().addListener(this::commentSelectionChanged);
	}

	/**
     * Handles the event when the selected comment in the comment list changes.
     *
     * @param observable The observable value.
     * @param oldValue    The old value.
     * @param newValue    The new value.
     */
	private void commentSelectionChanged(ObservableValue<? extends Comment> observable, Comment oldValue, Comment newValue) {
	    selectedComment = commentList.getSelectionModel().getSelectedItem();
	    editCommentButton.setVisible(selectedComment != null);
	    deleteCommentButton.setVisible(selectedComment != null);
	}

}
