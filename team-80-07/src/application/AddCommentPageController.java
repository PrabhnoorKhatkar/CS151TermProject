package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class AddCommentPageController {

    @FXML
    private TextField autoPopulatedTicketName;

    @FXML
    private TextField timestampTextField;

    @FXML
    private TextArea commentDescriptionArea;

    @FXML
    private Button saveCommentButton;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker projectDatePicker;

    @FXML
    private Button clearButton;

    private Ticket passedInProject;

    /**
     * initializes data from previous controller, passes in selectedTicket
     * 
     * @param selectedTicketOrProject
     */
    public void initData(Ticket selectedTicketOrProject) {

        passedInProject = selectedTicketOrProject;
        autoPopulatedTicketName.setText(passedInProject.getTicketName());
        commentDescriptionArea.setFocusTraversable(false);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Change the pattern as
                                                                                          // needed

        timestampTextField.setText(currentDateTime.format(formatter));

    }

    /**
     * When save button is pressed takes in parameters and stores them in ticket
     * table sql
     * 
     * @param event
     * @throws Exception
     */
    public void saveComment(ActionEvent event) throws Exception {
        String timestamp = timestampTextField.getText();
        String commentDesc = commentDescriptionArea.getText();
        String ticketID = passedInProject.getTicketID();

        // TODO if empty dont SAVE
        if (commentDesc.isEmpty()) {
            // Handle validation or show an error message
            // TODO
        } else {
            // Insert the project into the database
            insertComment(timestamp, commentDesc, ticketID);

            // Provide user feedback that the project was saved successfully
            // Example: showSuccessAlert("Project saved successfully.");

            // After saving the project, you can navigate back to the main page
            navigateToTicketPage(event);
        }

    }

    /**
     * Inserts a new comment into the database.
     *
     * @param timestamp     The timestamp of the comment. 
     * @param commentDesc   The description or content of the comment.
     * @param ticketID      The unique identifier of the ticket associated with the comment.
     */
    private void insertComment(String timestamp, String commentDesc, String ticketID) {
        // TODO Auto-generated method stub
        try (Connection connection = DatabaseConnection.getInstance()) {
            String insertQuery = "INSERT INTO comments (ticketID, timestamp, comment_description) VALUES (?, ?, ?)"; // Updated
                                                                                                                     // table
                                                                                                                     // name
                                                                                                                     // to
                                                                                                                     // "tickets"
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, ticketID);
                preparedStatement.setString(2, timestamp); // Store date as a string
                preparedStatement.setString(3, commentDesc);

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
     * Clears all enterable textfields in the AddCommentPage
     * 
     * @param event
     * @throws Exception
     */
    public void clearButton(ActionEvent event) throws Exception {
        commentDescriptionArea.clear();
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
        controller.initData((Ticket) passedInProject);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}