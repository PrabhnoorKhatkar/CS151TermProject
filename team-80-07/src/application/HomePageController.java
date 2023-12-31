package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePageController {

	@FXML
	private Button newProjectButton;

	@FXML
	private Button ShowProjectButton;

	@FXML
	private Button newTicketButton;

	@FXML
	private Button goToSearchTicket;

	@FXML
	private Button goToSearchProject;

	private Stage HomepageStage;

	/**
	 * This method handles the action event when a user wants to go to the new
	 * project scene.
	 * It loads the "NewProjectPage.fxml" file, creates a new scene, and sets it as
	 * the current scene.
	 * It also updates the stage to display the new scene.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws IOException If there is an error loading the FXML file.
	 */
	public void goToNewProjectScene(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("NewProjectPage.fxml"));
		Node source = (Node) event.getSource();
		
		HomepageStage = (Stage) source.getScene().getWindow();
		
		Scene newProjectScene = new Scene(root);
		
		HomepageStage.setScene(newProjectScene);
		
		HomepageStage.show();

	}

	/**
	 * This method handles the action event when a user wants to go to the Show
	 * Projects scene.
	 * It loads the "ShowProjectPage.fxml" file, creates a new scene, and sets it as
	 * the current scene.
	 * It also updates the stage to display the new scene.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws IOException If there is an error loading the FXML file.
	 */
	public void goToShowProjects(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("ShowProjectPage.fxml"));
		Node source = (Node) event.getSource();
		
		HomepageStage = (Stage) source.getScene().getWindow();
		
		Scene newProjectScene = new Scene(root);
		
		HomepageStage.setScene(newProjectScene);
		
		HomepageStage.show();

	}

	/**
	 * This method handles the action event when a user wants to go to the New
	 * Ticket scene.
	 * It loads the "NewTicketPage.fxml" file, creates a new scene, and sets it as
	 * the current scene.
	 * It also updates the stage to display the new scene.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws IOException If there is an error loading the FXML file.
	 */
	public void goToNewTicket(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("NewTicketPage.fxml"));
		Node source = (Node) event.getSource();
		
		HomepageStage = (Stage) source.getScene().getWindow();
		
		Scene newProjectScene = new Scene(root);
		
		HomepageStage.setScene(newProjectScene);
		
		HomepageStage.show();

	}

	/**
	 * This method handles the action event when a user wants to go to the New
	 * Ticket scene.
	 * It loads the "SearchTicketPage.fxml" file, creates a new scene, and sets it
	 * as the current scene.
	 * It also updates the stage to display the new scene.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws IOException If there is an error loading the FXML file.
	 */
	public void goToSearchTicket(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("SearchTicketPage.fxml"));
		Node source = (Node) event.getSource();
		
		HomepageStage = (Stage) source.getScene().getWindow();
		
		Scene newProjectScene = new Scene(root);
		
		HomepageStage.setScene(newProjectScene);
		
		HomepageStage.show();

	}

	/**
	 * This method handles the action event when a user wants to go to the New
	 * Ticket scene.
	 * It loads the "SearchTicketPage.fxml" file, creates a new scene, and sets it
	 * as the current scene.
	 * It also updates the stage to display the new scene.
	 *
	 * @param event The ActionEvent triggered by the user's interaction.
	 * @throws IOException If there is an error loading the FXML file.
	 */
	public void goToSearchProject(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("SearchProjectPage.fxml"));
		Node source = (Node) event.getSource();
		
		HomepageStage = (Stage) source.getScene().getWindow();
		
		Scene newProjectScene = new Scene(root);
		
		HomepageStage.setScene(newProjectScene);
		
		HomepageStage.show();

	}

}
