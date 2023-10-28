package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Comment;
import application.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ShowTicketPageController implements Initializable
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
  	private Label ticketNameDisplay;
      
    private Ticket passedInTicket;
    
    @FXML
    private ListView<String> commentList = new ListView<String>();
    
    private static final String jdbcUrl = "jdbc:sqlite:Data/database.db";
    
    
    
	public void initData(Ticket selectedTicketOrProject) 
	{
		
		passedInTicket = selectedTicketOrProject;
		ticketNameDisplay.setText(passedInTicket.getName());
		
	}
	
	
	
	 /**
     * This method handles the action when the "Back" button is clicked.
     * Navigates back to the home page.
     * @param event The action event.
     * @throws Exception If navigation fails.
     */
    @FXML
    public void backButton(ActionEvent event) throws Exception 
    {
    	 Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/HomePage.fxml"));
         Stage stage = (Stage) backButton.getScene().getWindow();
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    	
    }
    
    public void editTicket(ActionEvent event) throws Exception 
    {
    	
    	
    }
    
    public void goToShowComments(ActionEvent event) throws Exception 
    {
    	
    	
    }
    
    @FXML
    public void deleteTicket(ActionEvent event) throws Exception {
        if (passedInTicket != null) {
            String ticketName = passedInTicket.getTicketName(); 
    
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
    }


    
    public void addComment(ActionEvent event) throws Exception 
    {
    	
    	FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        loader.setLocation(getClass().getClassLoader().getResource("view/AddCommentPage.fxml"));
        root = loader.load();
        AddCommentPageController controller = loader.getController();
        
        
        controller.initData((Ticket) passedInTicket);
        
       
        Stage stage = (Stage) addCommentButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    	
    	
    	
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// Initialize the activeProjects list view with project names
		//ObservableList<String> listOfComments =  FXCollections.observableArrayList(getComments("comment", "ticketID", passedInTicket.getTicketID()));
		//commentList.setItems(listOfComments);
		
		
		
	}


/*
	public List<Comment> getCommentsByTicketID(String ticketID) 
	{
        List<Comment> commentList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);

            String sql = "SELECT timestamp, comment_description FROM comments WHERE ticketID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, ticketID);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) 
            {
                String timestamp = resultSet.getString("timestamp");
                String commentDescription = resultSet.getString("comment_description");

                Comment comment = new Comment(timestamp, commentDescription);
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

    
    
    */
    

}
