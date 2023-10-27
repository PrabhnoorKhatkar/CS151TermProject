package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the New Project Page in the JavaFX application.
 * This class handles creating and saving new projects to a SQLite database.
 */
public class AddCommentPageController implements Initializable
{
	
    @FXML
    private TextField autoTicketName;

    @FXML
    private TextArea ticketDescriptionArea;

    @FXML
    private Button saveTicketButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private Button clearButton;
    
    private Ticket passedInProject;
    
    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";


	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		autoTicketName.setText(passedInProject.getTicketName()); 
		
	}

	public void initData(Ticket selectedTicketOrProject) 
	{
		// TODO Auto-generated method stub
		System.out.println("TEMP");
		
		passedInProject = selectedTicketOrProject;
		
		
	}
 

    
		
	
}