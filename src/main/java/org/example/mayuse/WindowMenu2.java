package org.example.mayuse;

import org.example.animations.MyPanel;
import org.example.animations.MyRectangle;
import org.example.animations.ScreenImage;
import org.example.menudialogs.CompareImage;
import org.example.menudialogs.CompareImageDialog;
import org.example.menudialogs.TestReportDialog;
import org.example.utils.DeleteByCode;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

public class WindowMenu2 {

    private static Queue<JMenu> allMenus = new LinkedList<>();
    private final CompareImage compareImage = new CompareImage();

    public void run() {
        MyPanel animationPanel = createAnimationPanel();
        initializeWindow(animationPanel);
    }

    private MyPanel createAnimationPanel() {
        MyPanel animationPanel = new MyPanel("shellsonbeach.jpg" );
        animationPanel.addRectangles();

        ScreenImage shellImage = new ScreenImage("shell_image_icon.png");
        shellImage.setCoordinates(0,0);
        shellImage.setVelocity(1,2);
        shellImage.setForegroundImageSize(100,100);

        ScreenImage wonderLineImage = new ScreenImage("wonderline_imgicon.png");
        wonderLineImage.setCoordinates(30,300);
        wonderLineImage.setVelocity(2,1);
        wonderLineImage.setForegroundImageSize(120,120);

        animationPanel.initializeImageOnPanel(wonderLineImage);
        animationPanel.initializeImageOnPanel(shellImage);

        return animationPanel;
    }

    private void initializeWindow(MyPanel animationPanel) {
        JFrame window = new JFrame("Képek böngészése");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = createMenuBar();
        window.setJMenuBar(menuBar);

        window.getContentPane().setLayout(new GridLayout());
        window.getContentPane().add(animationPanel, BorderLayout.CENTER);

        window.getContentPane().setBackground(Color.WHITE);
        window.pack();
        window.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = createFileMenu();
        JMenu menu1 = createMenu("Első mappa", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures");
        JMenu menu2 = createMenu("Második mappa", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime");
        JMenu menu3 = createMenu("Harmadik mappa", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences");
        JMenu fileMenu2 = createFileMenu2();

        allMenus.add(fileMenu);
        allMenus.add(menu1);
        allMenus.add(menu2);
        allMenus.add(fileMenu2);
        allMenus.add(menu3);
        formatMenus();

        menuBar.add(fileMenu);
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(fileMenu2);
        menuBar.add(menu3);

        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("Fájl");
        JMenuItem exitMenuItem = new JMenuItem("Kilépés");

        exitMenuItem.addActionListener(e -> {
            deleteImagesProperties("src/main/resources/images.properties");
            deleteImagesProperties("src/main/resources/images_copy.properties");
            System.exit(0);
        });

        JMenuItem openReportItem = new JMenuItem("Tesztjelentés megnyitása");
        openReportItem.addActionListener(e -> new TestReportDialog());

        fileMenu.add(openReportItem);
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createMenu(String name, String directoryPath) {
        JMenu menu = new JMenu(name);
        JMenuItem menuItem = new JMenuItem("Képek betöltése");

        menuItem.addActionListener(e -> loadImagesFromDirectory(directoryPath));

        menu.add(menuItem);
        return menu;
    }

    private JMenu createFileMenu2() {
        JMenu fileMenu2 = new JMenu("Megegyező képek");
        JMenuItem sameImagesItem = new JMenuItem("Egyező fájlok megnyitása");

        sameImagesItem.addActionListener(e -> {
            try {
                new CompareImageDialog();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (FontFormatException ex) {
                throw new RuntimeException(ex);
            }
        });

        fileMenu2.add(sameImagesItem);
        return fileMenu2;
    }

    private void formatMenus() {
        allMenus.forEach(menu -> {
            Font font = new Font("Arial", Font.BOLD, 14);
            Color background = Color.ORANGE;
            Color foreground = Color.BLACK;
            Border border = BorderFactory.createLineBorder(Color.BLACK, 10);

            menu.setOpaque(true);
            menu.setFont(font);
            menu.setBackground(background);
            menu.setForeground(foreground);
            menu.setBorder(border);
        });
    }

    private void loadImagesFromDirectory(String directoryPath) {
        // Implement your image loading logic here
    }

    private void deleteImagesProperties(String filePath) {
        // Implement your properties deletion logic here
    }
}

