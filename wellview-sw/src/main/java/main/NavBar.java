package main;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;

public class NavBar extends UI{
	
	private MenuBar menu = new MenuBar();
	
	private TextFlow title = new TextFlow();
	private Label title_label = new Label("WellView");
	private ImageView logoView;
	
	private Menu home_menu = new Menu("Home");
	private Menu patientList_menu = new Menu("Patient List");
	private Menu visits_menu = new Menu("Visits");
	private Menu chat_menu = new Menu("Chat");
	private Menu prescribe_menu = new Menu("Prescribe");
	private Menu summary_menu = new Menu("Add Summary");
	private Menu vitals_menu = new Menu("Vitals");
	private Menu documents_menu = new Menu("Documents");
	private Menu help_menu = new Menu("Help");
	private Menu profile_menu = new Menu("Profile");
	
	AnchorPane ap = new AnchorPane();
	
	public NavBar() {
		
		Image image = new Image(Login.class.getResourceAsStream("../images/wellview_logo.png"));
		logoView = new ImageView(image);
		
		setupImage(logoView, 0, 0, 25, 25);
		setupLabelUI(title_label, "Ariel", 25, 0, Pos.CENTER_LEFT, 0, 0);
		title.getChildren().addAll(logoView, title_label);
		menu.setStyle("-fx-pref-height: 20px; -fx-font-size: 16px;");

		if (WellViewMain.currentUserType.equals("Patient")) {
			menu.getMenus().addAll(home_menu, visits_menu, chat_menu
					, documents_menu, help_menu, profile_menu);
		}
		else if (WellViewMain.currentUserType.equals("Doctor")) {
			menu.getMenus().addAll(home_menu, patientList_menu, chat_menu, prescribe_menu, summary_menu
					, documents_menu, help_menu, profile_menu);
		}
		else if (WellViewMain.currentUserType.equals("Nurse")) {
			
			menu.getMenus().addAll(home_menu, patientList_menu, chat_menu
					, vitals_menu, documents_menu, help_menu, profile_menu);
			
		} else {
			menu.getMenus().addAll(home_menu, patientList_menu, visits_menu, chat_menu, prescribe_menu
					, vitals_menu, documents_menu, help_menu, profile_menu);
		}
		
		
		
		AnchorPane.setRightAnchor(menu, 0.0);
		ap.setStyle(
				"-fx-padding: 0.0em 0.666667em 0.0em 0.666667em;"
				+ " -fx-background-color: linear-gradient(to bottom, derive(-fx-base,75%) 0%, "
				+ "-fx-outer-border 90%),linear-gradient(to bottom, derive(-fx-base,46.9%) 2%, derive(-fx-base,-2.1%) 95%);"
				+ " -fx-background-insets: 0 0 0 0, 1 0 1 0; "
				+ "-fx-background-radius: 0, 0 ;");
	}
	
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public MenuBar getNavBar() {
		return menu;
	}
	
	public AnchorPane showPane() {
		ap.getChildren().addAll(menu, title);
		return ap;
	}
	
	public void disableMenu() {
		
		switch(WellViewMain.currentPage) {
		case "Home": 
			home_menu.setDisable(true);
			break;
		case "Patient_List":
			patientList_menu.setDisable(true);
			break;
		case "Visits":
			visits_menu.setDisable(true);
			break;
		case "Chat":
			chat_menu.setDisable(true);
			break;
		case "Prescribe":
			prescribe_menu.setDisable(true);
			break;
		case "Add_Vitals":
			vitals_menu.setDisable(true);
			break;
		case "Documents":
			documents_menu.setDisable(true);
			break;
		case "Help":
			help_menu.setDisable(true);
			break;
		case "Profile":
			profile_menu.setDisable(true);
			break;
		default:
			break;
		}
	}
}
