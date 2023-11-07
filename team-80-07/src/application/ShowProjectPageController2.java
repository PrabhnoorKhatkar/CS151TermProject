package application;

import java.sql.Connection;
import java.sql.DriverManager;
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

public class ShowProjectPageController2
{
	 
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
  	private Label projectNameDisplay;
      
    private Project passedInProject;
   
    
    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";
    
   

}
	
   
    


