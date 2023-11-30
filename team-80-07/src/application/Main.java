package application;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The main class responsible for launching the JavaFX application.
 */
public class Main extends Application {

    private Stage primaryStage;
    private Parent root; 
<<<<<<< HEAD

=======
>>>>>>> 8ea4ad4046aa83e8b12f0df6f5ee3ac07cd8335a
    /**
     * The main entry point for the JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
<<<<<<< HEAD

=======
>>>>>>> 8ea4ad4046aa83e8b12f0df6f5ee3ac07cd8335a
    /**
     * Starts the JavaFX application by loading the splash screen.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        splashScreenLoader();
    }

    /**
     * Loads and displays the splash screen with fade-in and fade-out transitions.
     */
    private void splashScreenLoader() {
        try {
            root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
            Scene splashScene = new Scene(root);

<<<<<<< HEAD
            // Setting up the fade-in transition for the splash screen
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            // Setting up the fade-out transition for the splash screen
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), root);
=======
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), root);
>>>>>>> 8ea4ad4046aa83e8b12f0df6f5ee3ac07cd8335a
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            SequentialTransition sequence = new SequentialTransition(fadeIn, fadeOut);
            sequence.setOnFinished(e -> loadMainPage());

            primaryStage.setScene(splashScene);
            primaryStage.show();

            sequence.play();

<<<<<<< HEAD
            // Add image rotation after a delay using Platform.runLater
=======
>>>>>>> 8ea4ad4046aa83e8b12f0df6f5ee3ac07cd8335a
            Platform.runLater(() -> {
                ImageView imageView = (ImageView) root.lookup("#SplashImage2"); 
                if (imageView != null) {
                    rotateImage(imageView);
                }
            });

<<<<<<< HEAD
            // Add image rotation after a delay using Platform.runLater
=======
            
>>>>>>> 8ea4ad4046aa83e8b12f0df6f5ee3ac07cd8335a
            Platform.runLater(() -> {
                ImageView imageView = (ImageView) root.lookup("#SplashImage1"); 
                if (imageView != null) {
                    rotateImage(imageView);
                }
            });
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the main page with image rotation after a delay.
     */
    private void loadMainPage() {
        try {
            root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Scene mainScene = new Scene(root, 600, 400);
            primaryStage.setScene(mainScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies a rotation animation to the specified ImageView.
     *
     * @param imageView The ImageView to be rotated.
     */
    private void rotateImage(ImageView imageView) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), imageView);
        rotateTransition.setByAngle(360); 
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE); 
        rotateTransition.play();
    }
}
