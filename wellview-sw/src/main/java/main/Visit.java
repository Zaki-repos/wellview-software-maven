package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class Visit extends UI {
	
	TextArea allVisits = new TextArea("");
	Pane visitPane = new Pane();
	
	public Visit(BorderPane border) {
		allVisits.setFont(Font.font("Arial", 18));
		allVisits.setMinWidth(WellViewMain.WINDOW_WIDTH);
		allVisits.setLayoutX(0);
		allVisits.setLayoutY(0);		
		allVisits.setEditable(false);
		allVisits.setMinHeight(WellViewMain.WINDOW_HEIGHT);
		File visitFile = new File(WellViewMain.currentUserUID + "_VisitRecords.txt");
		String records = "No visits to display.";
		if (visitFile.exists()) {
			try {
				records = "Visits: \n";
				FileReader fr = new FileReader(visitFile);
				Scanner scanner = new Scanner(fr);
				while (scanner.hasNextLine()) {
					records+= "\n" + scanner.nextLine();
				}
				fr.close();
				scanner.close();
			}catch (IOException e) {		    	
		    	System.out.println("An error occurred.");
		    	e.printStackTrace();
		    }
		}
		allVisits.setText(records);
	}
	
	public Pane showPane() {
		WellViewMain.currentPage = "Visit";
		visitPane.getChildren().addAll(allVisits);
		return visitPane;
	}
	
	/*
	 * hidePane: clears all UI components on the pane, returns the empty pane.
	 */ 
	public Pane hidePane() {
		visitPane.getChildren().clear();
		return visitPane;
	}
}
