package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application 
{
	
	/**
	 * This method initializes the primary stage and sets up the initial scene.
	 *     
	 * @param primaryStage The primary stage where the application's UI is displayed.
	 */
	
	@Override
	public void start(Stage primaryStage) 
	{
		try {
			// Load the "HomePage.fxml" file and create a root Pane from it
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("HomePage.fxml"));
			// Create a new scene with the root Pane and set its dimensions (400x400)
			Scene scene = new Scene(root,400,400);
			// Set the created scene as the scene for the primary stage
			primaryStage.setScene(scene);
			// Show the primary stage to display the UI
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is meant for launching the Java application.
	 * It calls the launch method to start the JavaFX application.
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
