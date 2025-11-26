package org.example.mayuse;



import org.example.animations.MyRectangle;
import org.example.animations.ScreenImage;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MyPanel2 extends JPanel implements ActionListener {

    private int PANEL_WIDTH = 1000;
    private int PANEL_HEIGHT = 500;
    private Image backgroundImage;
    private final List<Image> foregroundImage;
    private final List<ScreenImage> foregroundScreenImage;
    private int index;
    private HashMap<Integer, Integer> foregroundImageCoordinates = new LinkedHashMap<>();
    private HashMap<Integer, Integer> foregroundImageVelocities = new LinkedHashMap<>();
    private Rectangle[] rectangles = new Rectangle[2];
    private Timer timer;

    public MyPanel2(String backgroundImage) {
        this.foregroundScreenImage = new LinkedList<>();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black);
        this.foregroundImage = new LinkedList<>();
        timer = new Timer(10, this);
        timer.start();
    }

    public void setPANEL_WIDTH(Integer panelWidth) {
        PANEL_WIDTH = panelWidth;
        resizePanel();
    }

    public void setPANEL_HEIGHT(Integer panelHeight) {
        PANEL_HEIGHT = panelHeight;
        resizePanel();
    }

    private void resizePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.revalidate();
        this.repaint();
    }

    public void initializeImageOnPanel(ScreenImage screenImage) {
        this.foregroundImage.add(screenImage.getForegroundImage());
        this.foregroundScreenImage.add(screenImage);
        foregroundImageCoordinates.put(screenImage.getX(), screenImage.getY());
        foregroundImageVelocities.put(screenImage.getXVelocity(), screenImage.getYVelocity());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(backgroundImage, 0, 0, null);
        for (ScreenImage screenImage : foregroundScreenImage) {
            g2D.drawImage(screenImage.getForegroundImage(), screenImage.getX(), screenImage.getY(), null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int entryIndex = 0;
        HashMap<Integer, Integer> updateFICoordinates = new LinkedHashMap<>();
        HashMap<Integer, Integer> updateVelocityCoordinates = new LinkedHashMap<>();

        for (Image image : foregroundImage) {
            if (entryIndex >= foregroundImageCoordinates.size()) {
                break;
            }
            Map.Entry<Integer, Integer> entryCoordinate = new ArrayList<>(foregroundImageCoordinates.entrySet()).get(entryIndex);
            Map.Entry<Integer, Integer> entryVelocity = new ArrayList<>(foregroundImageVelocities.entrySet()).get(entryIndex);

            int x = entryCoordinate.getKey();
            int y = entryCoordinate.getValue();
            int xVelocity = entryVelocity.getKey();
            int yVelocity = entryVelocity.getValue();

            if (x > PANEL_WIDTH - image.getWidth(null) || x < 0) {
                xVelocity = xVelocity * -1;
            }

            if (y > PANEL_HEIGHT - image.getHeight(null) || y < 0) {
                yVelocity = yVelocity * -1;
            }

            x = x + xVelocity;
            y = y + yVelocity;

            if (entryIndex == 0) {
                rectangles[entryIndex] = new MyRectangle(x, y, 100, 100).toRectangle();
            }
            if (entryIndex == 1) {
                rectangles[entryIndex] = new MyRectangle(x, y, 120, 120).toRectangle();
            }

            foregroundScreenImage.get(entryIndex).setCoordinates(x, y);
            foregroundScreenImage.get(entryIndex).setVelocity(xVelocity, yVelocity);

            updateFICoordinates.put(x, y);
            updateVelocityCoordinates.put(xVelocity, yVelocity);
            entryIndex++;
        }

        foregroundImageCoordinates = updateFICoordinates;
        foregroundImageVelocities = updateVelocityCoordinates;

        repaint();
    }

    public void addRectangles() {
        rectangles[0] = new MyRectangle(0, 0, 100, 100).toRectangle();
        rectangles[1] = new MyRectangle(30, 300, 120, 120).toRectangle();
    }

    private boolean isRectangleIntersected() {
        return rectangles[0].intersects(rectangles[1]);
    }
}
