package org.example.animations;

import java.awt.*;

public class MyRectangle extends ScreenImage{


    private int x;
    private int y;
    private final int width;
    private final int height;


    public MyRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public Rectangle toRectangle(){
        return new Rectangle(x, y, width, height);
    }

}
