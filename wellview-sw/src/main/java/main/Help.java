package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Help{

	BorderPane root = new BorderPane();
	
    public Help(BorderPane border) {
        
        VBox departmentsBox = new VBox(10);
        departmentsBox.setAlignment(Pos.CENTER);
        departmentsBox.setPadding(new Insets(15, 20, 15, 20));
        
        // Sample data
        String[][] departmentChairs = {
            {"Cardiology", "Dr. Heartbeat", "heartbeat@wellviewhospital.com"},
            {"Neurology", "Dr. Brainstorm", "brainstorm@wellviewhospital.com"},
            {"Orthopedics", "Dr. Bonefix", "bonefix@wellviewhospital.com"},
            {"Pediatrics", "Dr. Kidcare", "kidcare@wellviewhospital.com"},
            {"General Medicine", "Dr. Wellbeing", "wellbeing@wellviewhospital.com"}
        };
        
        for (String[] chair : departmentChairs) {
            Button btn = new Button("Contact " + chair[0]);
            btn.setOnAction(e -> showContactInfo(chair));
            departmentsBox.getChildren().add(btn);
        }
        
        Button techSupportBtn = new Button("Technical Support");
        techSupportBtn.setOnAction(e -> showTechSupportInfo());
        departmentsBox.getChildren().add(techSupportBtn);
        
        root.setCenter(departmentsBox);
        
        // Motivational Message
        Text motivationalMessage = new Text("We care deeply about your health and wellbeing!");
        motivationalMessage.setFont(new Font(14));
        motivationalMessage.setFill(Color.GREEN);
        root.setTop(motivationalMessage);
        BorderPane.setAlignment(motivationalMessage, Pos.CENTER);
        BorderPane.setMargin(motivationalMessage, new Insets(12, 0, 12, 0));
    }

    private void showContactInfo(String[] chair) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(chair[0] + " Department");
        infoAlert.setHeaderText("Chair: " + chair[1]);
        infoAlert.setContentText("Email: " + chair[2] + "\nWe're here to help you with utmost care and compassion.");
        infoAlert.showAndWait();
    }
    
    private void showTechSupportInfo() {
        Alert techSupportAlert = new Alert(Alert.AlertType.INFORMATION);
        techSupportAlert.setTitle("Technical Support");
        techSupportAlert.setHeaderText("WellView Technical Help Desk");
        techSupportAlert.setContentText("Email: techsupport@wellviewhospital.com\nPhone: 123-456-7890\nOur technical team is here to assist you!");
        techSupportAlert.showAndWait();
    }
    
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public BorderPane showPane() {
		WellViewMain.currentPage = "Help";
		return root;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public BorderPane getPane() {
		return root;
	}
}
