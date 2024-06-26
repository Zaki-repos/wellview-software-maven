package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;

public class NavBar extends UI{
	
	private TextFlow title = new TextFlow();
	private Label title_label = new Label("WellView");
	private ImageView logoView;
	
	private Button home_menu = new Button("Home");
	private Button patientList_menu = new Button("Patient List");
	private Button visits_menu = new Button("Visits");
	private Button chat_menu = new Button("Chat");
	private Button add_visit_menu = new Button("Add Visit");
	private Button prescribe_menu = new Button("Prescribe");
	private Button vitals_menu = new Button("Vitals");
	private Button documents_menu = new Button("Documents");
	private Button help_menu = new Button("Help");
	private Button profile_menu = new Button("Profile");
	private Button logout_menu = new Button("Logout");
	
	private ToolBar toolbar = new ToolBar();
	
	AnchorPane ap = new AnchorPane();
	
	public NavBar(BorderPane border) {
		
		Image image = new Image(Login.class.getResourceAsStream("../images/wellview_logo.png"));
		logoView = new ImageView(image);
		
		setupImage(logoView, 0, 0, 25, 25);
		setupLabelUI(title_label, "Ariel", 25, 0, Pos.CENTER_LEFT, 0, 0);
		title.getChildren().addAll(logoView, title_label);
		
		toolbar.setStyle("-fx-pref-height: 20px; -fx-font-size: 16px;");

		setActionsOnButtons(border);
		
		if (WellViewMain.currentUserType.equals("Patient")) {
			
			toolbar.getItems().addAll(home_menu, visits_menu, chat_menu
					, documents_menu, help_menu, profile_menu, logout_menu);
		}
		else if (WellViewMain.currentUserType.equals("Doctor")) {
			toolbar.getItems().addAll(home_menu, patientList_menu, chat_menu, prescribe_menu
					, add_visit_menu, documents_menu, help_menu, profile_menu, logout_menu);
		}
		else if (WellViewMain.currentUserType.equals("Nurse")) {
			
			toolbar.getItems().addAll(home_menu, patientList_menu, chat_menu
					, vitals_menu, documents_menu, help_menu, profile_menu, logout_menu);
			
		} else {
			toolbar.getItems().addAll(home_menu, patientList_menu, visits_menu, chat_menu, prescribe_menu
					, add_visit_menu, vitals_menu, documents_menu, help_menu, profile_menu, logout_menu);
		}
		
		
		
		AnchorPane.setRightAnchor(toolbar, 0.0);
		ap.setStyle(
				"-fx-padding: 0.0em 0.666667em 0.0em 0.666667em;"
				+ " -fx-background-color: linear-gradient(to bottom, derive(-fx-base,75%) 0%, "
				+ "-fx-outer-border 90%),linear-gradient(to bottom, derive(-fx-base,46.9%) 2%, derive(-fx-base,-2.1%) 95%);"
				+ " -fx-background-insets: 0 0 0 0, 1 0 1 0; "
				+ "-fx-background-radius: 0, 0 ;");
	}
	
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public AnchorPane showPane() {
		ap.getChildren().addAll(toolbar, title);
		return ap;
	}
	
	private void setActionsOnButtons(BorderPane border) {
		
		profile_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
            	Profile newProf = new Profile(border);
            	border.setCenter(newProf.showPane());
            }
        });
		
		home_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {     
            	Home newHome = new Home(border);
            	border.setCenter(newHome.showPane());
            }
        });
		
		patientList_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
            	Patients newPat = new Patients(border);
            	border.setCenter(newPat.showPane());
            }
        });
		
		prescribe_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Prescription newPres = new Prescription(border);
		    	border.setCenter(newPres.showPane());
            }
        });
		
		documents_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Documents newDocs = new Documents(border);
		    	border.setCenter(newDocs.showPane());
            }
        });

		chat_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Chat newChat = new Chat(border);
		    	border.setCenter(newChat.showPane());
            }
        });
		
		vitals_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Vitals newVitals = new Vitals(border);
		    	border.setCenter(newVitals.showPane());
            }
        });
		
		add_visit_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				DoctorVisit newDocVis = new DoctorVisit(border);
		    	border.setCenter(newDocVis.showPane());
            }
        });
		
		help_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Help newHelp = new Help(border);
		    	border.setCenter(newHelp.showPane());
            }
        });
		
		visits_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Visit newVisit = new Visit(border);
		    	border.setCenter(newVisit.showPane());
            }
        });
		
		logout_menu.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Login lg = new Login(border);
				WellViewMain.currentUserType = "";
				WellViewMain.currentUserUID = "";
				border.setTop(null);
				border.setCenter(lg.showPane());
            }
        });
	}
	
}
