package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Vitals extends UI {
	
	//These are the things we want inputted for Vitals.
	private TextArea patientIdInput = new TextArea();
	//Height and weight may use metric or imperial system, and since it isn't critical to be used in our software, the hospital can decide
    private TextArea heightEntry = new TextArea();
    private TextArea weightEntry = new TextArea();
    //Heart Rate, Blood Pressure, and Temperature are all things I was personally checked for, so I included them
    private TextArea hrEntry = new TextArea();
    private TextArea bpEntry = new TextArea();
    //Can be imperial or metric, it will be saved as a string anyways
    private TextArea tempEntry = new TextArea();
    
    //these are prompts
    private Label patientPrompt = new Label("Patient ID");
    private Label heightPrompt = new Label("Height");
    private Label weightPrompt = new Label("Weight");
    private Label hrPrompt = new Label("Heart Rate");
    private Label bpPrompt = new Label("Blood Pressure");
    private Label tempPrompt = new Label("Temperature");
    
    //These are the HBoxes that will tie the prompts and textAreas
    private HBox idBox = new HBox();
    private HBox heightBox = new HBox();
    private HBox weightBox = new HBox();
    private HBox hrBox = new HBox();
    private HBox bpBox = new HBox();
    private HBox tempBox = new HBox();
    
    private Label titleLabel = new Label("Patient Entry Form");
    
    
    //for the bottom
    //private Button backButton = new Button("Back");
    private Button saveButton = new Button("Save");
    
    
    BorderPane borderPane = new BorderPane();
    
    public Vitals(BorderPane border) {
        
        borderPane.setPadding(new Insets(10));

        VBox topBar = setupTopBar();
        borderPane.setTop(topBar);

        VBox centerArea = setupCenterArea();
        borderPane.setCenter(centerArea);

        HBox bottomArea = setupBottomArea();
        borderPane.setBottom(bottomArea);
    }
    
    //thank you Yaman for providing the template for this
    private VBox setupTopBar() {
    	VBox vb = new VBox(10);
    	vb.setPadding(new Insets(10));
    	
    	vb.getChildren().addAll(titleLabel);
    	return vb;
    }
    
    private VBox setupCenterArea() {
    	VBox midBox = new VBox();
    	midBox.setPadding(new Insets(10));
    	idBox.getChildren().addAll(patientPrompt, patientIdInput);
    	heightBox.getChildren().addAll(heightPrompt, heightEntry);
    	weightBox.getChildren().addAll(weightPrompt, weightEntry);
    	hrBox.getChildren().addAll(hrPrompt, hrEntry);
    	bpBox.getChildren().addAll(bpPrompt, bpEntry);
    	tempBox.getChildren().addAll(tempPrompt, tempEntry);
    	
    	midBox.getChildren().addAll(idBox, heightBox, weightBox, hrBox, bpBox, tempBox);
    	return midBox;
    	
    }
    
    private HBox setupBottomArea()  {
    	HBox botBox = new HBox();
    	botBox.setPadding(new Insets(10));
    	botBox.getChildren().addAll(saveButton);
    	
    	
    	 saveButton.setOnAction(new EventHandler<ActionEvent>() {
         	public void handle(ActionEvent event) {
         		saveForm();
         	}
         });
    	
    	
    	return botBox;
    }
    
        
    private void saveForm() {
        String patientId = patientIdInput.getText().trim();
        String height = heightEntry.getText().trim();
        String weight = weightEntry.getText().trim();
        String hr = hrEntry.getText().trim();
        String bp = bpEntry.getText().trim();
        String temp = tempEntry.getText().trim();
        
        
        if (patientId.isEmpty() || height.isEmpty() || weight.isEmpty() || hr.isEmpty() || bp.isEmpty() || temp.isEmpty()) {
            showError("One of the Fields is empty. If it isn't measured, enter \"N/A\"");
            return;
        }
        
        String filename = patientId + "-Vitals.txt";
        try {
        	FileWriter file = new FileWriter(filename);
        	BufferedWriter output = new BufferedWriter(file);
        	
        	output.write(patientId);
        	output.newLine();
        	output.write(height);
        	output.newLine();
        	output.write(weight);
        	output.newLine();
        	output.write(hr);
        	output.newLine();
        	output.write(bp);
        	output.newLine();
        	output.write(temp);
        	output.newLine();
        	output.close();
        	
        	showAlert("Success", "File: " + filename + " Created");
        }
        catch(IOException e) {
        	showError("An Unexpected Error Occurred");
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
		WellViewMain.currentPage = "Vitals";
		return borderPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public BorderPane getPane() {
		return borderPane;
	}
}


