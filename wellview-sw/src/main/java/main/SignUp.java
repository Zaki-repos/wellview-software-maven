package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SignUp extends UI{
	
	/*
	 * UI COMPONENTS Variables
	 * 
	 */
	private Label title_label = new Label("WellView");
	private Label createAccount_label = new Label("Create An Account");
	private Button back_btn = new Button("Back");
	
	private Label firstname_label = new Label("First Name");
	private Label lastname_label = new Label("Last Name");
	
	private TextField firstname_field = new TextField();
	private TextField lastname_field = new TextField();
	
	private Label password_label = new Label("Password");
	private Label confirmPass_label = new Label("Confirm Password");
	
	private TextField password_field = new TextField();
	private TextField confirmPass_field = new TextField();
	
	private Label email_label = new Label("Email");
	
	private TextField email_field = new TextField();
	
	private Label birthdate_label = new Label("Date of Birth");
	private TextField month_field = new TextField(); //month mm
	private TextField day_field = new TextField();   //day   dd
	private TextField year_field = new TextField();  //year  yyyy
	
	private Label sex_label = new Label("Sex: ");
	
	private ToggleGroup sex_group = new ToggleGroup();
	private RadioButton male_sel = new RadioButton("Male");
	private RadioButton female_sel = new RadioButton("Female");
	
	private CheckBox verification_box = new CheckBox();
	
	private Button create_btn = new Button("Create");
	
	Pane signUpPane = new Pane();
	private String errorMessage = "Sign Up Failed";
	
	private TextField[] tf = {firstname_field, lastname_field, password_field, confirmPass_field, email_field
	                          , month_field, day_field, year_field};
	/*
	 * End of Variables
	 * -------------------------------------------------------------------------------------------
	 */
	
	/*
	 * SignUp Constructor: Formats all the UI
	 */
	public SignUp(BorderPane border) {
		
		setupLabelUI(title_label, "Ariel", 20, WellViewMain.WINDOW_WIDTH, Pos.CENTER, 0, 10);
		setupLabelUI(createAccount_label, "Ariel", 18, WellViewMain.WINDOW_WIDTH, Pos.CENTER, 0, 40);
		setupButtonUI(back_btn, "Ariel", 18, 50, Pos.CENTER, WellViewMain.WINDOW_WIDTH-200, 150);
		
		back_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) { 
            	Login newLogin = new Login(border);
            	border.setCenter(newLogin.showPane());
            }
        });
		
		setupLabelUI(firstname_label, "Ariel", 14, WellViewMain.WINDOW_WIDTH, Pos.CENTER_LEFT, 350, 70);
		setupLabelUI(lastname_label, "Ariel", 14, WellViewMain.WINDOW_WIDTH, Pos.CENTER_LEFT, 350, 130);
		setupLabelUI(password_label, "Ariel", 14, WellViewMain.WINDOW_WIDTH, Pos.CENTER_LEFT, 350, 190);
		setupLabelUI(confirmPass_label, "Ariel", 14, WellViewMain.WINDOW_WIDTH, Pos.CENTER_LEFT, 350, 250);
		setupLabelUI(email_label, "Ariel", 14, WellViewMain.WINDOW_WIDTH, Pos.CENTER_LEFT, 350, 310);
		setupLabelUI(birthdate_label, "Ariel", 14, WellViewMain.WINDOW_WIDTH, Pos.CENTER_LEFT, 350, 370);
		setupLabelUI(sex_label, "Ariel", 14, WellViewMain.WINDOW_WIDTH, Pos.CENTER_LEFT, 350, 430);
		
		setupTextFieldUI(firstname_field, "Ariel", 16, 200, Pos.CENTER, 350, 90, true);
		setupTextFieldUI(lastname_field, "Ariel", 16, 200, Pos.CENTER, 350, 150, true);
		setupTextFieldUI(password_field, "Ariel", 16, 200, Pos.CENTER, 350, 210, true);
		setupTextFieldUI(confirmPass_field, "Ariel", 16, 200, Pos.CENTER, 350, 270, true);
		setupTextFieldUI(email_field, "Ariel", 16, 200, Pos.CENTER, 350, 330, true);
		
		setupTextFieldUI(month_field, "Ariel", 16, 60, Pos.CENTER, 350, 390, true);
		month_field.setPromptText("mm");
		setupTextFieldUI(day_field, "Ariel", 16, 60, Pos.CENTER, 420, 390, true);
		day_field.setPromptText("dd");
		setupTextFieldUI(year_field, "Ariel", 16, 60, Pos.CENTER, 490, 390, true);
		year_field.setPromptText("yyyy");
		
		male_sel.setLayoutX(400);
		male_sel.setLayoutY(430);
		female_sel.setLayoutX(470);
		female_sel.setLayoutY(430);
		male_sel.setToggleGroup(sex_group);
		female_sel.setToggleGroup(sex_group);
		
		verification_box.setLayoutX(350);
		verification_box.setLayoutY(460);
		verification_box.setText("I verify that the above information\n is complete and true.");
		
		setupButtonUI(create_btn, "Ariel", 18, 50, Pos.CENTER, 400, 520);
		
		create_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {   
            	
            	if (verifyAccount()) {
            		String fn, ln, pw, email, bd, sx;
                	fn = firstname_field.getText();
                	ln = lastname_field.getText();
                	pw = password_field.getText();
                	email = email_field.getText();
                	bd = month_field.getText() + day_field.getText() + year_field.getText();
                	RadioButton rb = (RadioButton) sex_group.getSelectedToggle();
                	sx = rb.getText();
                	User newUser = new User(fn, ln, pw, bd, email, sx);
                	
                	WellViewMain.userList.add(newUser);
                	Login newLogin = new Login(border);
                	border.setCenter(newLogin.showPane());
            	}
            	else
            	{
					Alert infoAlert = new Alert(Alert.AlertType.WARNING);
			        infoAlert.setTitle("Sign Up Error");
			        infoAlert.setContentText(errorMessage);
			        infoAlert.showAndWait();
            		//System.out.println("failed");
            	}
            }
        });
		
	}
	
	private Boolean verifyAccount() {
    	int x = tf.length;
		for (int i = 0; i < x; i++) {
			if (tf[i].getText().equals("")) {
				errorMessage = "Complete all fields.";
				return false;
			}
		}
		
		if (sex_group.getSelectedToggle() == null) {
			errorMessage = "Select a sex.";
			return false;
		}

		String pw = tf[2].getText();
		String cpw = tf[3].getText();
		if (!pw.equals(cpw)) {
			errorMessage = "Passwords do not match!";
			return false;
		}
		
		if (!verification_box.isSelected()) {
			errorMessage = "Please select verify.";
			return false;
		}
    	
    	return true;
	}
	
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public Pane showPane() {
		WellViewMain.currentPage = "Signup";
		signUpPane.getChildren().addAll(title_label, createAccount_label, firstname_label, lastname_label
				, password_label, confirmPass_label, email_label, birthdate_label, sex_label
				, firstname_field, lastname_field, password_field, confirmPass_field, email_field
				, month_field, day_field, year_field, male_sel, female_sel, back_btn, verification_box, create_btn);
		return signUpPane;
	}
	
	/*
	 * hidePane: clears all UI components on the pane, returns the empty pane.
	 */ 
	public Pane hidePane() {
		
		signUpPane.getChildren().clear();
		return signUpPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public Pane getPane() {
		return signUpPane;
	}

}
