package org.example.mayuse;

import javax.swing.*;
import java.awt.*;

public class LightWindowMenu {
    public static void main(String[] args) throws Exception {
        // FlatLaf Look and Feel
        //UIManager.setLookAndFeel(new FlatLightLaf());

        // Create window
        JFrame window = new JFrame("Modern Képek Böngészése");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // File Menu
        JMenu fileMenu = new JMenu("Fájl");
        fileMenu.add(new JMenuItem("Tesztjelentés megnyitása"));
        fileMenu.add(new JMenuItem("HTML Report"));
        fileMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Kilépés");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        // Add Menus
        JMenu originalMenu = new JMenu("ORIGINAL PICS");
        originalMenu.add(new JMenuItem("Eredeti képek betöltése"));
        menuBar.add(originalMenu);

        JMenu actualMenu = new JMenu("ACTUAL PICS");
        actualMenu.add(new JMenuItem("Aktuális képek betöltése"));
        menuBar.add(actualMenu);

        JMenu diffMenu = new JMenu("DIFFERENCES PICS");
        diffMenu.add(new JMenuItem("Különbségek megtekintése"));
        menuBar.add(diffMenu);

        JMenu sameMenu = new JMenu("SAME PICS");
        sameMenu.add(new JMenuItem("Egyező képek megtekintése"));
        menuBar.add(sameMenu);

        window.setJMenuBar(menuBar);

        // Center Panel
        JLabel welcomeLabel = new JLabel("Üdvözöljük a modern menüben!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        window.add(welcomeLabel, BorderLayout.CENTER);

        // Display window
        window.setVisible(true);
    }
}
