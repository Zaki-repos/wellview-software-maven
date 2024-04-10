package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
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
            String selectedChat = chatList.getSelectionModel().getSelectedItem();
            if (selectedChat != null) {
                readChatContent(selectedChat);
            }
        });

        leftBar.getChildren().addAll(chatsLabel, newChatButton, chatList);
        return leftBar;
    }

    private void createNewChat() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Chat");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter chat name:");

        dialog.showAndWait().ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                File chatFile = new File(name.trim() + ".txt");
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

    private void populateChatList() {
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        ObservableList<String> items = FXCollections.observableArrayList();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                items.add(file.getName());
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
            String selectedChat = chatList.getSelectionModel().getSelectedItem();
            try {
                Files.write(Paths.get(selectedChat), ("\n" + message).getBytes(), StandardOpenOption.APPEND);
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
}
