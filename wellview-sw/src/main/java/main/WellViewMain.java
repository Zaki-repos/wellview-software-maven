package main;

import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;

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
	
	public static String currentUserType = "";
	public static String currentPage = "";
	
	//TEMPORARY LIST OF USERS
	public static ArrayList<User> userList = new ArrayList<User>();
	
	/*
	 * start: entry point for javaFX program
	 */ 
	public void start(Stage primaryStage) {
		
		
		try {
			FileInputStream serviceAccount =
					new FileInputStream("./serviceAccountKey.json");

					FirebaseOptions options = FirebaseOptions.builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .build();

					FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();
            System.out.println(db);

            // Now you can use `db` to interact with Firestore
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
		
		
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

