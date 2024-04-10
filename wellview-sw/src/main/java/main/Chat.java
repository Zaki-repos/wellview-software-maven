package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Chat {

    private static final double LEFT_BAR_WIDTH = 200;
    private static final double MESSAGE_INPUT_WIDTH = 600;
    private static final String BACKGROUND_COLOR = "#4A5568";
    private static final String FONT_ARIAL = "Arial";
    private static final int FONT_SIZE = 16;

    private ListView<String> chatList = new ListView<>();
    private TextArea chatContentArea = new TextArea();
    private TextField messageInputField = new TextField();
    BorderPane mainLayout = new BorderPane();
    
    public Chat(BorderPane border) {

        mainLayout.setPadding(new Insets(10));

        mainLayout.setLeft(createLeftBar());
        mainLayout.setCenter(createChatViewArea());

        populateChatList();
    }

    private VBox createLeftBar() {
        VBox leftBar = new VBox(10);
        leftBar.setPadding(new Insets(10));
        leftBar.setPrefWidth(LEFT_BAR_WIDTH);
        leftBar.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        leftBar.setAlignment(Pos.TOP_CENTER);

        Label chatsLabel = new Label("Chats");
        chatsLabel.setFont(javafx.scene.text.Font.font(FONT_ARIAL, javafx.scene.text.FontWeight.BOLD, FONT_SIZE));
        chatsLabel.setTextFill(Color.WHITE);

        Button newChatButton = new Button("New Chat");
        newChatButton.setOnAction(e -> createNewChat());

        chatList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        chatList.setOnMouseClicked(event -> {
            String selectedChat;
            if (WellViewMain.currentUserType.equals("Patient")) {
        		selectedChat = WellViewMain.currentUserUID + "--" + chatList.getSelectionModel().getSelectedItem() + "Chat.txt";
        	}else {
        		selectedChat = chatList.getSelectionModel().getSelectedItem() + "Chat.txt";
        	}
            if (selectedChat != null) {
                readChatContent(selectedChat);
            }
        });

        leftBar.getChildren().addAll(chatsLabel, newChatButton, chatList);
        return leftBar;
    }

    /**
     * TODO:
     * - This is going to be a very tricky button to implement but if the user is a patient,
     * - then they should only be able to message Doctor and Nurse accounts.
     * - If the user is a Doctor or Nurse, then they should be able to message anyone.
     * - Let's change this so that it doesn't prompt for chat name but a drop down menu like
     * - Spencer created (I know...).
     */
    private void createNewChat() {
        Dialog<String> dialog = new Dialog();
        dialog.setTitle("New Chat");
        dialog.setHeaderText("Create a Subject and Recipient.");
        TextField textField = new TextField();
        textField.setPromptText("Subject");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ComboBox<String> newPatientIdInput = new ComboBox<String>(FXCollections.observableArrayList(getPatientIDs()));
        newPatientIdInput.setValue("Recipient");
        if (WellViewMain.currentUserType.equals("Patient")) {
        	dialog.setHeaderText("Create a Subject");
            dialogPane.setContent(new VBox(8, textField));
        }else {
            dialogPane.setContent(new VBox(8, textField, newPatientIdInput));        	
        }
        
        dialog.setResultConverter((ButtonType button) ->{
        	if (button == ButtonType.OK) {
        		if (WellViewMain.currentUserType.equals("Patient")) {
            		return WellViewMain.currentUserUID + "--" + textField.getText();
        		}
        		return newPatientIdInput.getValue() + "--" + textField.getText();
        	}
        	return null;
        });
        
        dialog.showAndWait().ifPresent(name -> {
            if (!name.trim().isEmpty()) {
            	System.out.println(name.trim());
                File chatFile = new File(name.trim() + "Chat.txt");
                if (!chatFile.exists()) {
                    try {
                        chatFile.createNewFile();
                        populateChatList(); // Refresh the chat list
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private BorderPane createChatViewArea() {
        BorderPane chatViewArea = new BorderPane();
        chatViewArea.setPadding(new Insets(10));
        
        chatContentArea.setEditable(false);
        chatContentArea.setWrapText(true);
        chatViewArea.setCenter(new ScrollPane(chatContentArea));
        
        HBox messageInputArea = new HBox(10);
        messageInputArea.setPadding(new Insets(10));
        messageInputField.setPrefWidth(MESSAGE_INPUT_WIDTH);
        Button sendMessageButton = new Button("Send");
        sendMessageButton.setOnAction(e -> sendMessage());
        messageInputArea.getChildren().addAll(messageInputField, sendMessageButton);
        chatViewArea.setBottom(messageInputArea);

        return chatViewArea;
    }

    /**
     * TODO: 
     * - The chat list should show only the currently logged in user's chat files.
     * - My initial idea was that chat files are stored like uid_Chat_uid.txt or 
     * - something like that and we scan for any matches where the current UID matches
     * - a text file with that UID (a person in that conversation).
     * - Feel free to do whatever makes sense, but don't forget it is a two way street.
     */
    private void populateChatList() {
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] listOfFiles;
        if (WellViewMain.currentUserType.equals("Patient")) {
            listOfFiles = folder.listFiles((dir, name) -> name.endsWith("Chat.txt") && name.startsWith(WellViewMain.currentUserUID));
        }else {
            listOfFiles = folder.listFiles((dir, name) -> name.endsWith("Chat.txt"));	
        }

        ObservableList<String> items = FXCollections.observableArrayList();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
            	if (WellViewMain.currentUserType.equals("Patient")) {
                    items.add(file.getName().substring(file.getName().indexOf("--") + 2, file.getName().indexOf("Chat.txt")));
            	}else {
                    items.add(file.getName().substring(0, file.getName().indexOf("Chat.txt")));	
            	}
            }
        }
        chatList.setItems(items);
    }

    private void readChatContent(String fileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            chatContentArea.setText(content);
        } catch (IOException e) {
            chatContentArea.setText("Failed to load chat: " + fileName);
        }
    }

    private void sendMessage() {
        String message = messageInputField.getText().trim();
        if (!message.isEmpty() && !chatList.getSelectionModel().isEmpty()) {
        	String selectedChat;
        	if (WellViewMain.currentUserType.equals("Patient")) {
        		selectedChat = WellViewMain.currentUserUID + "--" + chatList.getSelectionModel().getSelectedItem() + "Chat.txt";
        	}else {
        		selectedChat = chatList.getSelectionModel().getSelectedItem() + "Chat.txt";
        	}
            try {
                Files.write(Paths.get(selectedChat), ("\n" + WellViewMain.currentUserUID + ": " 
                										+ message).getBytes(), StandardOpenOption.APPEND);
                readChatContent(selectedChat); // Refresh chat content
                messageInputField.clear(); // Clear input field after sending message
            } catch (IOException e) {
                chatContentArea.setText("Failed to send message to: " + selectedChat);
            }
        }
    }
    
	/*
	 * showPane: adds all the UI components to a pane and returns that pane.
	 */ 
	public BorderPane showPane() {
		WellViewMain.currentPage = "Chat";
		return mainLayout;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public BorderPane getPane() {
		return mainLayout;
	}

	public String[] getPatientIDs() {
		File folder = new File(".");
		File[] fileList = folder.listFiles(new FilenameFilter() {
			public boolean accept(File folder, String name) {
				return name.toLowerCase().endsWith("patientinfo.txt");
			}
		});
		String[] IDs = new String[fileList.length];
		for (int i = 0; i < fileList.length; i++){
			String name = fileList[i].getName();
			int cutoff = name.indexOf("_PatientInfo.txt");
			name = name.substring(0, cutoff);
			IDs[i] = name;
		}
		return IDs;
	}
}
