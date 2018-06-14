package CookBookView.firstview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import CookBookView.loginview.LoginView;
import javafx.event.ActionEvent;

public class fvController{
	@FXML
	private Button loginbutton;
	@FXML
	private Button signupbutton;
	
	private Stage primaryStage; 
	

	// Event Listener on Button[#loginbutton].onAction
	@FXML
	
	public void initialize() {
		
	}
	
	
	public void loginView(ActionEvent event) throws IOException {
		
		GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root, 290, 470);
        Stage stage = new Stage();
        stage.setScene(scene);
		stage.show();
		
		
		
		
		
		// TODO Autogenerated
	}
	// Event Listener on Button[#signupbutton].onAction
	@FXML
	public void signupView(ActionEvent event) throws IOException {
		
		GridPane root = (GridPane) FXMLLoader.load(getClass().getResource("registerView.fxml"));
        Scene scene = new Scene(root, 290, 470);
        Stage stage = new Stage();
        stage.setScene(scene);
		stage.show();
		
		
		// TODO Autogenerated
	}
}