package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class Documents extends Application {

    private TextArea documentTextArea;

    @Override
    public void start(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Patient ID");
        dialog.setHeaderText("Enter Patient ID:");
        dialog.setContentText("Patient ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(patientID -> {
            if (!patientID.trim().isEmpty()) {
                initializeUI(primaryStage);
                loadDocument(patientID.trim());
            } else {
                showAlert(AlertType.ERROR, "No Patient ID provided. Exiting application.");
                System.exit(0); // or handle accordingly
            }
        });
    }

    private void initializeUI(Stage primaryStage) {
        documentTextArea = new TextArea();
        documentTextArea.setEditable(false);

        StackPane root = new StackPane();
        root.getChildren().add(documentTextArea);

        Scene scene = new Scene(root, 400, 500);

        primaryStage.setTitle("Document Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadDocument(String patientID) {
        File documentFile = new File(patientID + "_PatientInfo.txt");

        if (documentFile.exists() && !documentFile.isDirectory()) {
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(documentFile))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    contentBuilder.append(currentLine).append("\n");
                }
            } catch (IOException e) {
                showAlert(AlertType.ERROR, "Error reading the document.");
                e.printStackTrace();
            }
            documentTextArea.setText(contentBuilder.toString());
        } else {
            showAlert(AlertType.INFORMATION, "No such file exists");
        }
    }

    private void showAlert(AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
