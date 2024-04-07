package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Prescription extends UI {

    private TextField patientIdInput = new TextField();
    private TextArea prescriptionInfoArea = new TextArea();
    private ListView<String> prescriptionList = new ListView<>();
    private TextField newPatientIdInput = new TextField();
    private TextArea newPrescriptionArea = new TextArea();
    private String currentEditingFile = "";
    
    BorderPane borderPane = new BorderPane();
    
    public Prescription(BorderPane border) {
        
        borderPane.setPadding(new Insets(10));

        VBox topBar = setupTopBar();
        borderPane.setTop(topBar);

        SplitPane centerArea = setupCenterArea();
        borderPane.setCenter(centerArea);

        VBox bottomArea = setupBottomArea();
        borderPane.setBottom(bottomArea);
    }

    private VBox setupTopBar() {
        VBox topBar = new VBox(10);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #336699;");

        Label headerLabel = new Label("Prescription Viewer");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.WHITE);

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        Label patientIdLabel = new Label("Patient ID:");
        patientIdLabel.setTextFill(Color.WHITE);
        patientIdInput.setPromptText("Enter Patient ID");
        patientIdInput.setMinWidth(200);
        patientIdInput.setStyle("-fx-font-size: 14pt;");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-font-size: 14pt; -fx-background-color: #29BB89; -fx-text-fill: white;");
        searchButton.setOnAction(e -> searchAndDisplayPrescription());

        searchBox.getChildren().addAll(patientIdLabel, patientIdInput, searchButton);
        topBar.getChildren().addAll(headerLabel, searchBox);
        return topBar;
    }

    private SplitPane setupCenterArea() {
        SplitPane centerSplitPane = new SplitPane();
        prescriptionList.setMinHeight(200);
        prescriptionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        prescriptionList.setOnMouseClicked(event -> {
            String selectedFile = prescriptionList.getSelectionModel().getSelectedItem();
            if (selectedFile != null && !selectedFile.isEmpty()) {
                String prescriptionContent = readPrescriptionContent(selectedFile);
                prescriptionInfoArea.setText(prescriptionContent);
                currentEditingFile = selectedFile; // Track the current file being edited
            }
        });
        listPrescriptionFiles();

        prescriptionInfoArea.setEditable(true); // Allow editing
        prescriptionInfoArea.setWrapText(true);
        prescriptionInfoArea.setStyle("-fx-background-color: #F2F1F9; -fx-font-size: 14pt;");

        VBox listContainer = new VBox(new Label("Available Prescriptions:"), prescriptionList);
        VBox infoContainer = new VBox(new Label("Prescription Details:"), prescriptionInfoArea);
        listContainer.setPadding(new Insets(10));
        infoContainer.setPadding(new Insets(10));
        centerSplitPane.getItems().addAll(listContainer, infoContainer);
        centerSplitPane.setDividerPositions(0.3f);
        return centerSplitPane;
    }

    private VBox setupBottomArea() {
        VBox bottomArea = new VBox(10);
        bottomArea.setPadding(new Insets(15));
        bottomArea.setStyle("-fx-background-color: #336699;");

        Label createPrescriptionLabel = new Label("Create/Edit Prescription");
        createPrescriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        createPrescriptionLabel.setTextFill(Color.WHITE);

        HBox patientIdBox = new HBox(10);
        Label patientIdLabel = new Label("Patient ID for New:");
        patientIdLabel.setTextFill(Color.WHITE);
        newPatientIdInput.setPromptText("New Patient ID");

        newPrescriptionArea.setPromptText("Enter prescription details here...");
        newPrescriptionArea.setPrefHeight(100);

        Button saveButton = new Button("Save New/Changes");
        saveButton.setOnAction(e -> {
            if (!currentEditingFile.isEmpty()) {
                saveEditedPrescription(); // Save edits to the currently selected file
            } else {
                saveNewPrescription(); // Save a new prescription
            }
        });

        patientIdBox.getChildren().addAll(patientIdLabel, newPatientIdInput);
        bottomArea.getChildren().addAll(createPrescriptionLabel, patientIdBox, newPrescriptionArea, saveButton);
        return bottomArea;
    }

    private void listPrescriptionFiles() {
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith("Prescription.txt"));

        ObservableList<String> items = FXCollections.observableArrayList();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                items.add(file.getName());
            }
        }
        prescriptionList.setItems(items);
    }

    private String readPrescriptionContent(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            showError("An error occurred while trying to read the prescription: " + fileName);
            return null;
        }
    }

    private void searchAndDisplayPrescription() {
        String patientID = patientIdInput.getText();
        File prescriptionFile = findPrescriptionFile(patientID);
        if (prescriptionFile != null) {
            String prescriptionContent = readPrescriptionContent(prescriptionFile.getPath());
            prescriptionInfoArea.setText(prescriptionContent);
            currentEditingFile = prescriptionFile.getName(); // Track the current file being edited
        } else {
            showError("No prescriptions found for Patient ID: " + patientID);
            prescriptionInfoArea.clear();
        }
    }

    private File findPrescriptionFile(String patientID) {
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] matchingFiles = folder.listFiles((dir, name) -> name.startsWith(patientID) && name.endsWith("Prescription.txt")); //only locates files with Prescription in the name

        if (matchingFiles != null && matchingFiles.length > 0) {
            return matchingFiles[0];
        }
        return null;
    }

    private void saveNewPrescription() {
        String patientId = newPatientIdInput.getText().trim();
        String prescriptionDetails = newPrescriptionArea.getText().trim();
        if (patientId.isEmpty() || prescriptionDetails.isEmpty()) {
            showError("Both Patient ID and prescription details are required for a new prescription.");
            return;
        }

        File file = new File(patientId + "-Prescription.txt");
        try {
            Files.write(Paths.get(file.getName()), prescriptionDetails.getBytes(), StandardOpenOption.CREATE);
            newPatientIdInput.clear();
            newPrescriptionArea.clear();
            listPrescriptionFiles(); // Refresh the prescription list
            showAlert("Success", "Prescription saved successfully for Patient ID: " + patientId);
            currentEditingFile = ""; // Reset current editing file
        } catch (IOException ex) {
            showError("Failed to save the new prescription. " + ex.getMessage());
        }
    }

    private void saveEditedPrescription() {
        String editedContent = prescriptionInfoArea.getText();
        try {
            Files.write(Paths.get(currentEditingFile), editedContent.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            showAlert("Success", "Prescription updated successfully.");
            listPrescriptionFiles(); // Refresh the prescription list
            currentEditingFile = ""; // Reset current editing file
        } catch (IOException ex) {
            showError("Failed to save the edited prescription. " + ex.getMessage());
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
	public BorderPane showPane() {
		WellViewMain.currentPage = "Prescription";
		return borderPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public BorderPane getPane() {
		return borderPane;
	}
}

