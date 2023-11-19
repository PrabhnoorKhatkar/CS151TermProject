package application;

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
public class EditCommentPageController 
{
	
	@FXML
	private Button saveCommentButton;
	
	@FXML
	private TextArea commentDescriptionArea;
	
	@FXML
	private TextField timestampTextField;
	
	@FXML
	private TextField autoPopulatedTicketName;

	@FXML
	private Button cancelButton;

	@FXML
	private Button clearButton;

	private Ticket storedTicket;

	private Comment storedComment;

	public void initData(Comment passedInComment) {
		// TODO Auto-generated method stub
		storedComment = passedInComment;

	}

	public void saveComment(ActionEvent event) throws Exception 
	{

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

	}

}