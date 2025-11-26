package org.example.menudialogs;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ReportViewer extends JFrame {
    private JTextArea textArea;

    public ReportViewer() {
        setTitle("Test Report");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Apply FlatLaf Dark Theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBackground(new Color(40, 44, 52));
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        loadReport();
        setVisible(true);
    }

    private void loadReport() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\imagereport.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException ex) {
            textArea.setText("Error loading report!");
        }
    }

    public static void openReport() {
        SwingUtilities.invokeLater(() -> new ReportViewer());
    }
}
