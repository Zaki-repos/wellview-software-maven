package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class Home extends UI{
	
	private TextFlow account = new TextFlow();
	private TextFlow patients = new TextFlow();
	private TextFlow visits = new TextFlow();
	private TextFlow addVisit = new TextFlow();
	private TextFlow chat = new TextFlow();
	private TextFlow prescription = new TextFlow();
	private TextFlow vitals = new TextFlow();
	private TextFlow documents = new TextFlow();
	private TextFlow help = new TextFlow();
	
	private Button acc_btn = new Button();
	private Button pat_btn = new Button();
	private Button visit_btn = new Button();
	private Button addVisit_btn = new Button();
	private Button chat_btn = new Button();
	private Button pres_btn = new Button();
	private Button vital_btn = new Button();
	private Button doc_btn = new Button();
	private Button help_btn = new Button();
	
	private Label acc_label = new Label("View or Edit Your Profile");
	private Label pat_label = new Label("View the Patient List");
	private Label visit_label = new Label("View Your Visits");
	private Label addVisit_label = new Label("Add a Summary for a Patient");
	private Label chat_label = new Label("Send a Message");
	private Label pres_label = new Label("Add a Prescription for a Patient");
	private Label vital_label = new Label("Add Vitals for a Patient");
	private Label doc_label = new Label("View All Your Stored Documents");
	private Label help_label = new Label("Need Help?");
	

	private TextFlow[] textflows = new TextFlow[9];
	
	Pane home = new Pane();
	GridPane gp = new GridPane();
	VBox vb = new VBox();
	ScrollPane sp = new ScrollPane();
	
	public Home(BorderPane border) {
		
		setupHomeButtonUI(acc_btn, 200, 200, "../images/account.png");
		acc_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
            	Profile newProf = new Profile(border);
            	border.setCenter(newProf.showPane());
            }
        });
		setupHomeButtonUI(pat_btn, 200, 200, "../images/patients.png");
		pat_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
            	Patients newPat = new Patients(border);
            	border.setCenter(newPat.showPane());
            }
        });
		setupHomeButtonUI(visit_btn, 200, 200, "../images/visits.png");
		visit_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Visit newVisit = new Visit(border);
		    	border.setCenter(newVisit.showPane());
            }
        });
		setupHomeButtonUI(addVisit_btn, 200, 200, "../images/addVisit.png");
		addVisit_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				DoctorVisit newDocVis = new DoctorVisit(border);
		    	border.setCenter(newDocVis.showPane());
            }
        });
		setupHomeButtonUI(chat_btn, 200, 200, "../images/chat.png");
		chat_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Chat newChat = new Chat(border);
		    	border.setCenter(newChat.showPane());
            }
        });
		setupHomeButtonUI(pres_btn, 200, 200, "../images/prescription.png");
		pres_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
            	Prescription newPres = new Prescription(border);
            	border.setCenter(newPres.showPane());
            }
        });
		setupHomeButtonUI(vital_btn, 200, 200, "../images/vitals.png");
    	vital_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Vitals newVitals = new Vitals(border);
		    	border.setCenter(newVitals.showPane());
            }
        });
		setupHomeButtonUI(doc_btn, 200, 200, "../images/documents.png");
		doc_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
            	Documents newDocs = new Documents(border);
            	border.setCenter(newDocs.showPane());
            }
        });
		setupHomeButtonUI(help_btn, 200, 200, "../images/help.png");
		help_btn.setOnAction(new EventHandler<ActionEvent>() {  
			@Override
            public void handle(ActionEvent event) {
				Help newHelp = new Help(border);
		    	border.setCenter(newHelp.showPane());
            }
        });
		
		setupTextFlowUIAdv(account, acc_btn, acc_label, 200);
		setupTextFlowUIAdv(patients, pat_btn, pat_label, 200);
		setupTextFlowUIAdv(visits, visit_btn, visit_label, 200);
		setupTextFlowUIAdv(addVisit, addVisit_btn, addVisit_label, 200);
		setupTextFlowUIAdv(chat, chat_btn, chat_label, 200);
		setupTextFlowUIAdv(prescription, pres_btn, pres_label, 200);
		setupTextFlowUIAdv(vitals, vital_btn, vital_label, 200);
		setupTextFlowUIAdv(documents, doc_btn, doc_label, 200);
		setupTextFlowUIAdv(help, help_btn, help_label, 200);
		
		textflows[0] = account;		
		textflows[3] = chat;
		textflows[7] = documents;
		textflows[8] = help;
		
		if (WellViewMain.currentUserType.equals("Doctor") || WellViewMain.currentUserType.equals("Nurse") || WellViewMain.currentUserType.equals("Master"))
			textflows[1] = patients;
		if (WellViewMain.currentUserType.equals("Patient") || WellViewMain.currentUserType.equals("Master"))
			textflows[2] = visits;
		if (WellViewMain.currentUserType.equals("Doctor") || WellViewMain.currentUserType.equals("Master"))
		{
			textflows[4] = prescription;
			textflows[6] = addVisit;
		}
		if (WellViewMain.currentUserType.equals("Nurse") || WellViewMain.currentUserType.equals("Master"))
			textflows[5] = vitals;
		
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < 9; i++) {
			if(textflows[i] != null) {
				gp.add(textflows[i], x, y);
				if ( x == 2) {
					y++;
					x = 0;
				} else {
					x++;
				}
			}
		}
		
		gp.setHgap(20);
		gp.setVgap(45);
		
		vb.getChildren().add(gp);
		VBox.setMargin(gp, new Insets(80, 120, 80, 120));
		sp.setContent(vb);

		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Horizontal scroll bar never appears
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar appears as needed

	}
	
	
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public ScrollPane showPane() {
		WellViewMain.currentPage = "Home";
		//home.getChildren().addAll(sp);
		return sp;
	}
	
	/*
	 * hidePane: clears all UI components on the pane, returns the empty pane.
	 */ 
	public Pane hidePane() {
		return null;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public Pane getPane() {
		return home;
	}

}
