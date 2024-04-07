package main;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class UI {
	
	
	/*
	 * These methods are methods that can be used to manipulate UI objects
	 * and program behavior. Referenced from Prof. Carter's EvenNumberRecognizer program.
	 */
	public void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}

	public void setupTextFieldUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}
	
	public void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	public void setupHomeButtonUI(Button b, double w, double h, String f) {
		b.setMinHeight(h);
		b.setMinWidth(w);
		Image img = new Image(UI.class.getResourceAsStream(f));
		ImageView iv = new ImageView(img);
		iv.setFitHeight(h-50);
		iv.setFitWidth(w-50);
		iv.setPreserveRatio(true);
		b.setGraphic(iv);
	}
	
	public void setupTextFlowUI(TextFlow t, double x, double y) {
		t.setLayoutX(x);
		t.setLayoutY(y);	
	}
	
	public void setupTextFlowUIAdv(TextFlow t, Node n0, Node n1, double w) {
		t.getChildren().addAll(n0, n1);
		t.setMaxWidth(w);
	}
	
	public void setupImage(ImageView i, double x, double y, double w, double h) {
		i.setX(x);
		i.setY(y);
		i.setFitHeight(h);
		i.setFitWidth(w);
	}
	
}
