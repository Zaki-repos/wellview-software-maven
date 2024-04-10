package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Visit extends UI {
	TextField allVisits = new TextField("");
	Pane visitPane = new Pane();
	public Visit(BorderPane border, String ID) {
		setupTextFieldUI(allVisits, "Arial", 18, WellViewMain.WINDOW_WIDTH, Pos.CENTER, 0, 0, false);
		File visitFile = new File(ID + "_VisitRecords.txt");
		String records = "No visits to show.";
		if (visitFile.exists()) {
			try {
				records = "Visits: \n";
				FileReader fr = new FileReader(visitFile);
				Scanner scanner = new Scanner(fr);
				while (scanner.hasNextLine()) {
					records+= '\n' + scanner.nextLine();
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
