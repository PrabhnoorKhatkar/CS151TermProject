package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ShowTicketPageController 
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
    
    
    private Ticket passedInTicket;
    
    @FXML
    private ListView commentList;
    
    
    
	public void initData(Ticket selectedTicketOrProject) 
	{
		// TODO Auto-generated method stub
		System.out.println("TEMP");
		
		passedInTicket = selectedTicketOrProject;
		
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
    	
    	
    }
    
    public void editTicket(ActionEvent event) throws Exception 
    {
    	
    	
    }
    
    public void goToShowComments(ActionEvent event) throws Exception 
    {
    	
    	
    }
    
    public void deleteTicket(ActionEvent event) throws Exception 
    {
    	
    	
    }
    
    public void addComment(ActionEvent event) throws Exception 
    {
    	
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
    
    
    
    
    
    
    

	
	

}
