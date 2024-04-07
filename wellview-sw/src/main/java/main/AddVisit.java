package main;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AddVisit extends UI {
	
	private Label patientId_label = new Label();
	private Label summary_label = new Label();
	
	private TextField patientId_field = new TextField();
	private TextArea summary_field = new TextArea();
	
	Pane av = new Pane();
	
	public AddVisit() {
		
	}
	
	
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public Pane showPane() {
		WellViewMain.currentPage = "Add_Visit";
		av.getChildren().addAll();
		return av;
	}
	
	/*
	 * hidePane: clears all UI components on the pane, returns the empty pane.
	 */ 
	public Pane hidePane() {
		av.getChildren().clear();
		return av;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public Pane getPane() {
		return av;
	}
	
}
