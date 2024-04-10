package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Profile extends UI {
	Pane profilePane;
	String password, userType, firstName, lastName, email, dob, sex;
	TextField passwordTF, userTypeTF, firstNameTF, lastNameTF, emailTF, dobTF, sexTF;
	Label passwordL = new Label("Password:"), userTypeL = new Label("User Type:"), firstNameL = new Label("First Name:"),
			lastNameL = new Label("Last Name:"), emailL = new Label("Email:"), dobL = new Label("Date of Birth:"), sexL = new Label("Sex:");
	Button saveBtn = new Button("Save");
	public Profile (BorderPane border, String ID) {
		getDataFromFile(ID);
		passwordTF.setText(password);
		userTypeTF.setText(userType);
		firstNameTF.setText(firstName);
		lastNameTF.setText(lastName);
		emailTF.setText(email);
		dobTF.setText(dob);
		sexTF.setText(sex);		
		
		double xPosition = 100, width = 100, xOffset = 1.25 * width;
		double currentYPosition = 100, ySpacing = 150;
		
		setupLabelUI(passwordL, "Arial", 25, width, Pos.CENTER, xPosition, currentYPosition);
		setupTextFieldUI(passwordTF, "Arial", 18, 2*width, Pos.CENTER, xPosition + xOffset, currentYPosition, true);
		currentYPosition += ySpacing;
		
		setupLabelUI(userTypeL, "Arial", 25, width, Pos.CENTER, xPosition, currentYPosition);
		setupTextFieldUI(userTypeTF, "Arial", 18, 2*width, Pos.CENTER, xPosition + xOffset, currentYPosition, true);
		currentYPosition += ySpacing;
		
		setupLabelUI(firstNameL, "Arial", 25, width, Pos.CENTER, xPosition, currentYPosition);
		setupTextFieldUI(firstNameTF, "Arial", 18, 2*width, Pos.CENTER, xPosition + xOffset, currentYPosition, true);
		currentYPosition += ySpacing;

		setupLabelUI(lastNameL, "Arial", 25, width, Pos.CENTER, xPosition, currentYPosition);
		setupTextFieldUI(lastNameTF, "Arial", 18, 2*width, Pos.CENTER, xPosition + xOffset, currentYPosition, true);
		currentYPosition += ySpacing;

		setupLabelUI(emailL, "Arial", 25, width, Pos.CENTER, xPosition, currentYPosition);
		setupTextFieldUI(emailTF, "Arial", 18, 2*width, Pos.CENTER, xPosition + xOffset, currentYPosition, true);
		currentYPosition += ySpacing;

		setupLabelUI(dobL, "Arial", 25, width, Pos.CENTER, xPosition, currentYPosition);
		setupTextFieldUI(dobTF, "Arial", 18, 2*width, Pos.CENTER, xPosition + xOffset, currentYPosition, true);
		currentYPosition += ySpacing;

		setupLabelUI(sexL, "Arial", 25, width, Pos.CENTER, xPosition, currentYPosition);
		setupTextFieldUI(sexTF, "Arial", 18, 2*width, Pos.CENTER, xPosition + xOffset, currentYPosition, true);
		setupButtonUI(saveBtn, "Arial", 18, 100, Pos.CENTER, xPosition + xOffset * 3, currentYPosition);
		currentYPosition += ySpacing;
		
		saveBtn.setOnAction(new EventHandler<>() {  //if the Save button was pressed, save the data into the file, overwriting it if necessary.
            public void handle(ActionEvent event){ 
            	saveDataIntoFile(ID);
            }
		});
		
	}
	public void getDataFromFile(String ID) {
		try {
			File file = new File(ID + "_PatientInfo.txt");
			FileReader fr = new FileReader(file);
			Scanner scanner = new Scanner(fr);
			password = scanner.nextLine();
			userType = scanner.nextLine();
			firstName = scanner.nextLine();
			lastName = scanner.nextLine();
			email = scanner.nextLine();
			dob = scanner.nextLine();
			sex = scanner.nextLine();
			fr.close();
			scanner.close();
		} catch (IOException e) {
			System.out.println("An error occurred trying to read the file");
    		e.printStackTrace();
		}
	}
	public void saveDataIntoFile(String ID) {
		password = passwordTF.getText();
		userType = userTypeTF.getText();
		firstName = firstNameTF.getText();
		lastName = lastNameTF.getText();
		email = emailTF.getText();
		dob = dobTF.getText();
		sex = sexTF.getText();
		String data = password + '\n' + userType + '\n' + firstName + '\n' + lastName + '\n' + email + '\n' + dob + '\n' + sex;
		try {
			File file = new File(ID + "_PatientInfo.txt");
			FileWriter fw = new FileWriter(file);
			fw.write(data);
		} catch (IOException exception) { //Catches any errors that could possibly occur.
    		System.out.println("An error occurred while trying to save.");
    		exception.printStackTrace();
    	}
	}
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public Pane showPane() {
		WellViewMain.currentPage = "Profile";
		profilePane.getChildren().addAll(passwordL, passwordTF, userTypeL, userTypeTF, firstNameL, firstNameTF, lastNameL, lastNameTF, emailL, emailTF, dobL, dobTF, sexL, sexTF, saveBtn);
		return profilePane;
	}
	
	/*
	 * hidePane: clears all UI components on the pane, returns the empty pane.
	 */ 
	public Pane hidePane() {
		profilePane.getChildren().clear();
		return profilePane;
	}
}
