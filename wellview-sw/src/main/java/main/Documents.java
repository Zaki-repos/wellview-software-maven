package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Documents {

    private String currentUID;
    private JFrame frame;
    private JTextArea documentTextArea;
    private JButton loadButton;

    public Documents(String UID) {
        this.currentUID = UID;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Document Viewer");
        documentTextArea = new JTextArea(20, 40);
        loadButton = new JButton("Load Document");

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDocument();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(documentTextArea));
        panel.add(loadButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void loadDocument() {
        // Simulate checking if a document exists for the current UID
        String documentContent = getDocumentContentForUID(currentUID);
        
        if (documentContent != null) {
            documentTextArea.setText(documentContent);
        } else {
            JOptionPane.showMessageDialog(frame, "No document exists for UID: " + currentUID, "Document Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // This method is a stub for the actual document retrieval logic.
    private String getDocumentContentForUID(String UID) {
        

        if ("User123".equals(UID)) { // Replace this condition with actual existence check
            return "This is the content of the document for UID: User123.";
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        // The main method is for demonstration; typically you'd call this from your application logic.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Documents("User123"); // Replace User123 with the actual UID to check
            }
        });
    }
}

