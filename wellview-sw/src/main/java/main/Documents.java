package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.List;

// Hypothetical UserSession class to simulate fetching the current user's UID
// Replace this with your actual UserSession import

public class Documents {

    private TextArea documentTextArea = new TextArea();
    private ListView<String> documentListView = new ListView<>();
    private BorderPane borderPane = new BorderPane();
    private String userUID; // This will be fetched from the UserSession class

    
    public Documents(BorderPane pane) {
        // Fetch the UID from UserSession or similar class
        userUID = WellViewMain.currentUserUID; // Adjust this call to your actual method for fetching the UID
    	
        borderPane.setLeft(setupDocumentList());
        borderPane.setCenter(documentTextArea);

        loadDocumentList();
    }

    private VBox setupDocumentList() {
        documentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        documentListView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) { // Double-click to open document
                String selectedDocument = documentListView.getSelectionModel().getSelectedItem();
                if (selectedDocument != null && !selectedDocument.isEmpty()) {
                    loadDocument(selectedDocument);
                }
            }
        });

        VBox vbox = new VBox(documentListView);
        vbox.setPrefWidth(300);
        return vbox;
    }

    private void loadDocumentList() {
        try {
            Path parentFolder = Paths.get(""); // Adjust this to the parent folder path if needed
            List<String> documentNames = Files.walk(parentFolder)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(userUID))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
            
            documentListView.getItems().clear();
            documentListView.getItems().addAll(documentNames);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading documents.");
            e.printStackTrace();
        }
    }

    private void loadDocument(String documentName) {
        File documentFile = new File(documentName);

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
            showAlert(Alert.AlertType.INFORMATION, "Document not found: " + documentName);
        }
    }

    private void showAlert(Alert.AlertType alertType, String content) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null); // Removes the header
        alert.setContentText(content);
        alert.showAndWait();
    }

	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public BorderPane showPane() {
		WellViewMain.currentPage = "Documents";
		return borderPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public BorderPane getPane() {
		return borderPane;
	}
}
