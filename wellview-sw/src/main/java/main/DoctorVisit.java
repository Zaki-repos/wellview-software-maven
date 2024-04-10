package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DoctorVisit extends UI {
	
    private ComboBox<String> newPatientIdInput;
    private TextField newDateInput = new TextField();
    private TextArea newSummaryArea = new TextArea();
    
    Pane doctorViewPane = new Pane();
    
    public DoctorVisit(BorderPane border) {
        
        doctorViewPane.setPadding(new Insets(10));
        VBox bottomArea = setupBottomArea();
        bottomArea.setPrefSize(WellViewMain.WINDOW_WIDTH, WellViewMain.WINDOW_HEIGHT);
        doctorViewPane.getChildren().add(bottomArea);
    }


    private VBox setupBottomArea() {
        VBox bottomArea = new VBox(10);
        bottomArea.setPadding(new Insets(15));
        bottomArea.setStyle("-fx-background-color: #336699;");

        Label createPrescriptionLabel = new Label("Create New Visit Summary:");
        createPrescriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        createPrescriptionLabel.setTextFill(Color.WHITE);
        
        HBox idAndDate = new HBox(10);
        idAndDate.setSpacing(250);
        HBox patientIdBox = new HBox(10);
        Label patientIdLabel = new Label("Patient ID:");
        patientIdLabel.setFont(Font.font("Arial", 18));
        patientIdLabel.setTextFill(Color.WHITE);
        
        newPatientIdInput = new ComboBox<String>(FXCollections.observableArrayList(getPatientIDs()));
        
        HBox dateBox = new HBox(10);
        Label dateLabel = new Label("Date:");
        dateLabel.setFont(Font.font("Arial", 18));
        dateLabel.setTextFill(Color.WHITE);
        newDateInput.setPromptText("Date");

        newSummaryArea.setPromptText("Enter visit summary here...");
        newSummaryArea.setPrefHeight(400);

        Button saveButton = new Button("Save New/Changes");
        saveButton.setOnAction(new EventHandler<>() {  //if the Save button was pressed, save the data into the file, overwriting it if necessary.
            public void handle(ActionEvent event){ 
            	saveNewSummary();
            }
		});

        patientIdBox.getChildren().addAll(patientIdLabel, newPatientIdInput);
        dateBox.getChildren().addAll(dateLabel, newDateInput);
        idAndDate.getChildren().addAll(patientIdBox, dateBox);
        bottomArea.getChildren().addAll(createPrescriptionLabel, idAndDate, newSummaryArea, saveButton);
        return bottomArea;
    }

    private void saveNewSummary() {
        String patientId = newPatientIdInput.getValue().toString().trim();
        String summaryDetails = newSummaryArea.getText().trim();
        String date = newDateInput.getText().trim();
        if (patientId.isEmpty() || summaryDetails.isEmpty() || date.isEmpty()) {
            showError("Patient ID, Date, and Summary are all required for a new visit summary.");
            return;
        }

        File file = new File(patientId + "_VisitRecords.txt");
        try {
        	if (file.createNewFile()) {
        		System.out.println("Created new file");
        	}
        	else {
        		System.out.println("File already exists");
        	}
        	FileReader fr = new FileReader(file);
        	Scanner scnr = new Scanner(fr);
        	String currentData = "";
        	while (scnr.hasNextLine()) {
        		currentData += "\n" + scnr.nextLine();
        	}
        	fr.close();
        	scnr.close();
        	FileWriter fw = new FileWriter(file);
        	String newData = date + "- " + summaryDetails;
        	String totalData = newData + "\n" + currentData;
        	fw.write(totalData);
        	fw.close();
            newPatientIdInput.setValue(null);
            newSummaryArea.clear();
            showAlert("Success", "Summary saved successfully for Patient ID: " + patientId);
        } catch (IOException ex) {
            showError("Failed to save the new summary. " + ex.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public Pane showPane() {
		WellViewMain.currentPage = "Visit Summary";
		return doctorViewPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public Pane getPane() {
		return doctorViewPane;
	}
	public String[] getPatientIDs() {
		File folder = new File(".");
		File[] fileList = folder.listFiles(new FilenameFilter() {
			public boolean accept(File folder, String name) {
				return name.toLowerCase().endsWith("patientinfo.txt");
			}
		});
		String[] IDs = new String[fileList.length];
		for (int i = 0; i < fileList.length; i++){
			String name = fileList[i].getName();
			int cutoff = name.indexOf("_PatientInfo.txt");
			name = name.substring(0, cutoff);
			IDs[i] = name;
		}
		return IDs;
	}
}

