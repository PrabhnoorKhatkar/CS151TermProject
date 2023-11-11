package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
import java.util.List;
import java.util.ResourceBundle;

public class ShowProjectPageController implements Initializable {

    @FXML
    private TextField projectNameField;

    @FXML
    private Button backButton;

    @FXML

    private ListView<String> activeProjects = new ListView<String>();

    /**
     * This method handles the action when the "Back" button is clicked.
     * Navigates back to the home page.
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
     * This method initializes the Show Project Page.
     * Retrieves a list of project names from the database and populates the
     * ListView.
     * 
     * @param location  The location used to resolve the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the activeProjects list view with project names
        ObservableList<String> projectNames = FXCollections
                .observableArrayList(getProjectNames("projects", "project_name"));
        activeProjects.setItems(projectNames);

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

        return returnStringList;
    }

}