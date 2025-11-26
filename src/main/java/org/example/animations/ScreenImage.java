package org.example.animations;

import javax.swing.*;
import java.awt.*;

public class ScreenImage{

    private int xVelocity = 2;
    private int yVelocity = 1;
    private int x = 0;
    private int y = 0;

    private Image foregroundImage;

    private  Integer width;
    private  Integer height;


    public ScreenImage(){

    }
    public ScreenImage(String foregroundImage){
        this.foregroundImage = new ImageIcon(foregroundImage).getImage();
        width = new ImageIcon(foregroundImage).getIconWidth();
        height = new ImageIcon(foregroundImage).getIconHeight();

    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }


    public Image getForegroundImage(){
        return this.foregroundImage;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getXVelocity(){
        return this.xVelocity;
    }
    public int getYVelocity(){
        return this.yVelocity;
    }

    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setVelocity(int xVelocity, int yVelocity){
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void setForegroundImageSize(int width, int height){
        this.foregroundImage = this.foregroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }



}
