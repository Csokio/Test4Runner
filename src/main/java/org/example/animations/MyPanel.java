package org.example.animations;

import com.github.dockerjava.api.model.Link;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MyPanel extends JPanel implements ActionListener {

    private int PANEL_WIDTH = 600;
    private int PANEL_HEIGHT = 500;
    Image backgroundImage;
    private final List<Image> foregroundImage;

    private final List<ScreenImage> foregroundScreenImage;
    private int index;
    private HashMap<Integer, Integer> foregroundImageCoordinates = new LinkedHashMap<>();
    private HashMap<Integer, Integer> foregroundImageVelocities = new LinkedHashMap<>();

    Rectangle[] rectangles = new Rectangle[2];


    Timer timer;
    //private int xVelocity = 2;
    //private int yVelocity = 1;
    //private int x = 0;
    //private int y = 0;

    public void setPANEL_WIDTH(Integer panelWidth){
        PANEL_WIDTH = panelWidth;
    }
    public void setPANEL_HEIGHT(Integer panelHeight){
        PANEL_HEIGHT = panelHeight;
    }


    public MyPanel(String backgroundImage){
        this.foregroundScreenImage = new LinkedList<>();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black);
        //this.backgroundImage = new ImageIcon(backgroundImage).getImage();
        this.foregroundImage = new LinkedList<>();
        timer = new Timer(10, this);
        timer.start();
    }

    public void initializeImageOnPanel(ScreenImage screenImage){
        this.foregroundImage.add(screenImage.getForegroundImage());
        this.foregroundScreenImage.add(screenImage);
        foregroundImageCoordinates.put(screenImage.getX(), screenImage.getY());
        foregroundImageVelocities.put(screenImage.getXVelocity(), screenImage.getYVelocity());
    }

    /*public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }*/

    public void paint(Graphics g) {

        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(backgroundImage, 0, 0, null);
        //g2D.drawImage(foregroundImage,x,y,null);
        /*foregroundImageCoordinates.forEach((key, value) -> {

            int xCoordinate = key;
            int yCoordinate = value;

            Image image = foregroundImage.get(index++);
            g2D.drawImage(image, xCoordinate, yCoordinate, null);
        });
        index = 0;*/
        for (ScreenImage screenImage : foregroundScreenImage) {
            g2D.drawImage(screenImage.getForegroundImage(), screenImage.getX(), screenImage.getY(), null);
            //g2D.draw(rectangles[0]);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int entryIndex = 0;
        int otherIndex;

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


            /*if (x > PANEL_WIDTH - image.getWidth(null) || x < 0 ||
                    isRectangleIntersected()) {
                xVelocity = xVelocity * -1;
            }

            if (y > PANEL_HEIGHT - image.getHeight(null) || y < 0 ||
                    isRectangleIntersected()) {
                yVelocity = yVelocity * -1;
            }*/
            if (x > PANEL_WIDTH - image.getWidth(null) || x < 0) {
                xVelocity = xVelocity * -1;
            }

            if (y > PANEL_HEIGHT - image.getHeight(null) || y < 0) {
                yVelocity = yVelocity * -1;
            }



            x = x + xVelocity;
            y = y + yVelocity;

            if(entryIndex == 0){
                rectangles[entryIndex] = new MyRectangle(x,y,100,100).toRectangle();
            }
            if(entryIndex == 1){
                rectangles[entryIndex] = new MyRectangle(x,y,120,120).toRectangle();
            }

            foregroundScreenImage.get(entryIndex).setCoordinates(x,y);
            foregroundScreenImage.get(entryIndex).setVelocity(xVelocity,yVelocity);

            updateFICoordinates.put(x,y);
            updateVelocityCoordinates.put(xVelocity, yVelocity);
            entryIndex++;
        }

        foregroundImageCoordinates = updateFICoordinates;
        foregroundImageVelocities = updateVelocityCoordinates;

        repaint();
    }

    public void addRectangles(){
        rectangles[0] = new MyRectangle(0,0,100,100).toRectangle();
        rectangles[1] = new MyRectangle(30,300,120,120).toRectangle();

    }

    private boolean isRectangleIntersected(){
        return rectangles[0].intersects(rectangles[1]);
    }




}