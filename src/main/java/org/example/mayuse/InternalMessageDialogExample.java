package org.example.mayuse;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InternalMessageDialogExample {
    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Internal Message Dialog Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JOptionPane.showMessageDialog(frame, "popup message");

        // Create a desktop pane (required for internal frames)
        JDesktopPane desktopPane = new JDesktopPane();

        // Create an internal frame
        JInternalFrame internalFrame = new JInternalFrame("Internal Frame", true, true, true, true);
        internalFrame.setSize(400, 300);
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);

        // Add a button to the internal frame
        JButton button = new JButton("Show Information Dialog");
        internalFrame.add(button);

        // Add action listener to the button to show the internal message dialog
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showInternalMessageDialog(internalFrame, "information", "information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add the desktop pane to the main frame
        frame.setContentPane(desktopPane);

        // Display the main frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event dispatch thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}