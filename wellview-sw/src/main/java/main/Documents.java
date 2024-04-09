package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Documents extends Application {

    private TextArea documentTextArea = new TextArea();
    private TextField patientIdInput = new TextField();
    private BorderPane borderPane = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        borderPane.setTop(setupTopBar());
        borderPane.setCenter(setupCenterArea());
        
        // Setup the scene and stage
        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setTitle("Document Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox setupTopBar() {
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15, 12, 15, 12));
        topBar.setStyle("-fx-background-color: #336699;");
        topBar.setAlignment(Pos.CENTER);

        patientIdInput.setPromptText("Enter Patient ID");
        patientIdInput.setMinWidth(300);
        patientIdInput.setOnAction(e -> loadDocument(patientIdInput.getText().trim())); // Action on Enter key press

        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> loadDocument(patientIdInput.getText().trim()));

        topBar.getChildren().addAll(new Label("Patient ID:"), patientIdInput, viewButton);
        return topBar;
    }

    private StackPane setupCenterArea() {
        documentTextArea.setEditable(false);
        StackPane centerArea = new StackPane();
        centerArea.getChildren().add(documentTextArea);
        return centerArea;
    }

    private void loadDocument(String patientID) {
        if (patientID.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "No Patient ID provided.");
            return;
        }

        File documentFile = new File(patientID + "_PatientInfo.txt");

        if (documentFile.exists() && !documentFile.isDirectory()) {
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(documentFile))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    contentBuilder.append(currentLine).append("\n");
                }
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error reading the document.");
                e.printStackTrace();
            }
            documentTextArea.setText(contentBuilder.toString());
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Document not found for Patient ID: " + patientID);
        }
    }

    private void showAlert(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null); // Removes the header
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
launch(args);
    }
}
