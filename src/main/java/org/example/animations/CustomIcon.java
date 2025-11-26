package org.example.animations;

import javax.swing.*;
import java.awt.*;

public class CustomIcon implements Icon {
    private int width;
    private int height;
    private Color color;


    public CustomIcon(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Set the color and draw a filled rectangle
        g.setColor(this.color);
        g.fillRect(x, y, width, height);
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }
}
