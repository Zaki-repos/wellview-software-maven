package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Patients {
	private TextField patientIdInput = new TextField();
    private TextArea patientInfoArea = new TextArea(); // TextArea for patient information
    private ListView<String> fileList = new ListView<>(); // ListView to display .txt files
    

    private BorderPane borderPane = new BorderPane();
    
    public Patients(BorderPane border) {

        // Top bar
        HBox topBar = setupTopBar();
        borderPane.setTop(topBar);

        // Center area to display .txt files
        fileList.setMinHeight(200); 
        fileList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listTxtFiles(); // Populate the ListView with .txt files
        borderPane.setCenter(fileList);

        // Bottom bar with "View" button
        VBox bottomBar = setupBottomBar();
        borderPane.setBottom(bottomBar);
    }
    
    private HBox setupTopBar() {
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15, 12, 15, 12));
        topBar.setStyle("-fx-background-color: #336699;");
        topBar.setAlignment(Pos.CENTER);

        patientIdInput.setPromptText("Patient ID");
        patientIdInput.setMinWidth(300);
        patientIdInput.setStyle("-fx-font-size: 14pt;");

        Label patientLabel = new Label("Patient:");
        patientLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(e -> viewPatientInfo(patientIdInput.getText()));

        topBar.getChildren().addAll(patientLabel, patientIdInput, enterButton);
        return topBar;
    }

    private VBox setupBottomBar() {
        VBox bottomBar = new VBox(10);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(15, 12, 15, 12));

        Button viewButton = new Button("View");
        viewButton.setMinSize(100, 40);
        viewButton.setStyle("-fx-font-size: 14pt;");
        viewButton.setOnAction(e -> {
            String selectedFile = fileList.getSelectionModel().getSelectedItem();
            if (selectedFile != null) {
                readFileContent(selectedFile);
            } else {
                showError("Please select a file to view.");
            }
        });

        patientInfoArea.setEditable(false);
        patientInfoArea.setWrapText(true);

        // Add components to the VBox
        bottomBar.getChildren().addAll(viewButton, patientInfoArea);
        return bottomBar;
    }
    
    private void readFileContent(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            patientInfoArea.setText(content);
        } catch (IOException e) {
            showError("An error occurred while trying to read the file: " + fileName);
        }
    }

    private void listTxtFiles() {
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith("_PatientInfo.txt")); //only finds files that end with 2024

        ObservableList<String> items = FXCollections.observableArrayList();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                items.add(file.getName());
            }
        }
        fileList.setItems(items);
    }

    private void viewPatientInfo(String patientID) {
        File dir = new File("."); 
        File[] matchingFiles = dir.listFiles((d, name) -> name.startsWith(patientID));

        if (matchingFiles != null && matchingFiles.length > 0) {
            StringBuilder combinedInfo = new StringBuilder();

            for (File file : matchingFiles) {
                try {
                    combinedInfo.append("File: ").append(file.getName()).append("\n");
                    combinedInfo.append(new String(Files.readAllBytes(Paths.get(file.getPath()))));
                    combinedInfo.append("\n\n"); 
                } catch (IOException e) {
                    showError("Failed to read information from file: " + file.getName());
                }
            }

            showPatientInfoDialog(combinedInfo.toString());
        } else {
            showError("No information found for Patient ID: " + patientID);
        }
    }
    
    private void showPatientInfoDialog(String patientInfo) {
        TextArea infoArea = new TextArea(patientInfo);
        infoArea.setEditable(false);
        infoArea.setWrapText(true);

        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Patient Information");
        infoAlert.setHeaderText("Details for Patient ID: " + patientIdInput.getText());
        infoAlert.getDialogPane().setContent(infoArea);
        infoAlert.showAndWait();
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
		WellViewMain.currentPage = "Patients";
		return borderPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public BorderPane getPane() {
		return borderPane;
	}
}
