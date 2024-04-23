package main;

import javafx.stage.Stage;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class WellViewMain extends Application {
	
	public final static double WINDOW_WIDTH = 900;
	public final static double WINDOW_HEIGHT = 600;
	
	public static String currentUserUID = "";
	public static String currentUserType = "";
	public static String currentPage = "";
	
	/*
	 * start: entry point for javaFX program
	 */ 
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("WellView");
		
		BorderPane border = new BorderPane();
		
		Login lg = new Login(border);
		
		border.setCenter(lg.showPane());
		
		Scene theScene = new Scene(border, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
		primaryStage.setScene(theScene);						  			// Set the scene on the stage
		primaryStage.setResizable(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

