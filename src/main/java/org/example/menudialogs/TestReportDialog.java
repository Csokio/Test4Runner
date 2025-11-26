package org.example.menudialogs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestReportDialog extends JFrame{

    public TestReportDialog() {
        setTitle("Tesztjelentés");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        try (BufferedReader reader = new BufferedReader(new FileReader("\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        add(scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}