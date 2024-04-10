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

public class Chat{

    private ListView<String> chatList = new ListView<>();
    private TextArea chatContentArea = new TextArea();
    private TextField messageInputField = new TextField();
    BorderPane borderPane = new BorderPane();

    public Chat(BorderPane border) {

        borderPane.setPadding(new Insets(10));

        // Sidebar for chat history
        VBox leftBar = setupLeftBar();
        borderPane.setLeft(leftBar);

        // Main area for viewing and writing to selected chat
        BorderPane chatViewArea = setupChatViewArea();
        borderPane.setCenter(chatViewArea);

        listChats();
    }

    private VBox setupLeftBar() {
        VBox leftBar = new VBox(10);
        leftBar.setPadding(new Insets(10));
        leftBar.setPrefWidth(200);
        leftBar.setStyle("-fx-background-color: #4A5568;");
        leftBar.setAlignment(Pos.TOP_CENTER);

        Label chatsLabel = new Label("Chats");
        chatsLabel.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 16));
        chatsLabel.setTextFill(Color.WHITE);

        chatList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        chatList.setOnMouseClicked(event -> {
            String selectedChat = chatList.getSelectionModel().getSelectedItem();
            if (selectedChat != null && !selectedChat.isEmpty()) {
                readChatContent(selectedChat);
            }
        });

        leftBar.getChildren().addAll(chatsLabel, chatList);
        return leftBar;
    }

    private BorderPane setupChatViewArea() {
        BorderPane chatViewArea = new BorderPane();
        chatViewArea.setPadding(new Insets(10));
        
        chatContentArea.setEditable(false);
        chatContentArea.setWrapText(true);
        chatViewArea.setCenter(new ScrollPane(chatContentArea));
        
        HBox messageInputArea = new HBox(10);
        messageInputArea.setPadding(new Insets(10, 0, 0, 0));
        messageInputField.setPrefWidth(600);
        Button sendMessageButton = new Button("Send");
        sendMessageButton.setOnAction(e -> sendMessage());
        messageInputArea.getChildren().addAll(messageInputField, sendMessageButton);
        chatViewArea.setBottom(messageInputArea);

        return chatViewArea;
    }

    private void listChats() {
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] listOfFiles = folder.listFiles((dir, name) -> name.startsWith("Chat_")); //use files ending in txt

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
                Files.write(Paths.get(selectedChat), ("\n"+ WellViewMain.currentUserUID +": "+ message).getBytes(), StandardOpenOption.APPEND);
                readChatContent(selectedChat); // Refresh chat content
                messageInputField.clear(); // Clear input field
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
		return borderPane;
	}
	
	/*
	 * getPane: returns the pane.
	 */ 
	public BorderPane getPane() {
		return borderPane;
	}
}