package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Login extends UI{
	
	/*
	 * UI COMPONENTS Variables
	 */
	private Label title_label = new Label("WellView"); 
	private Label signIn_label = new Label("Sign In");
	private TextField username_label = new TextField();
	private TextField password_label = new TextField();
	private Text noAcc_text = new Text("Don't have an account? ");
	private Button click_btn = new Button("Click Here");
	private TextFlow noAcc = new TextFlow();
	private Button login_button = new Button("LOGIN");
	
	Pane loginPane = new Pane();
	
	/*
	 * Login Constructor: Formats all the UI
	 */
	public Login(BorderPane border) {
		
		setupLabelUI(title_label, "Ariel", 25, WellViewMain.WINDOW_WIDTH, Pos.CENTER, 0, 80);
		setupLabelUI(signIn_label, "Ariel", 18, WellViewMain.WINDOW_WIDTH, Pos.CENTER, 0, 150);
		
		setupTextFieldUI(username_label, "Ariel", 18, 300, Pos.CENTER, 300, 180, true);
		username_label.setPromptText("Username");
		setupTextFieldUI(password_label, "Ariel", 18, 300, Pos.CENTER, 300, 230, true);
		password_label.setPromptText("Password");
		
		setupTextFlowUI(noAcc, 350, 550);
		
		click_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
            	SignUp newSignUp = new SignUp(border);
            	border.setCenter(newSignUp.showPane());
            }
        });
		
		login_button.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {    
				
				if (verifyAccount()) {
					hidePane();
					NavBar newNav = new NavBar(border);
					border.setTop(newNav.showPane());
					
	            	Home newHome = new Home(border);
	            	border.setCenter(newHome.showPane());
	            	
				} else {
					Alert infoAlert = new Alert(Alert.AlertType.ERROR);
			        infoAlert.setTitle("Login Attempt");
			        infoAlert.setContentText("Incorrect Login Information");
			        infoAlert.showAndWait();
				}
				
            }
        });
		
		noAcc.getChildren().add(noAcc_text);
		noAcc.getChildren().add(click_btn);
		
		setupButtonUI(login_button, "Ariel", 18, 150, Pos.CENTER, 375, 300);
		
		Image image = new Image(Login.class.getResourceAsStream("../images/wellview_logo.png"));
		ImageView logoView = new ImageView(image);
		setupImage(logoView, 300, 30, 100, 100);
		
		loginPane.getChildren().add(logoView);
		
	}
	
	public boolean verifyAccount() {
		File temp = new File(username_label.getText() + "_PatientInfo.txt");
		if (!temp.exists()) {
			return false;
		}
		else {
			Scanner sc;
			try {
				sc = new Scanner(temp);
				String pw = sc.nextLine();
				if(!pw.equals(password_label.getText())) {
					sc.close();
					return false;
				}
				WellViewMain.currentUserType = sc.nextLine();
				WellViewMain.currentUserUID = username_label.getText();
				sc.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public Pane showPane() {
		WellViewMain.currentPage = "Login";
		loginPane.getChildren().addAll(title_label, signIn_label, username_label, password_label, noAcc, login_button);
		return loginPane;
	}
	
	/*
	 * hidePane: clears all UI components on the pane, returns the empty pane.
	 */ 
	public Pane hidePane() {
		loginPane.getChildren().clear();
		return loginPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public Pane getPane() {
		return loginPane;
	}

}
