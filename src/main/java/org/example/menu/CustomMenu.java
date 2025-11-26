package org.example.menu;

import java.awt.*;
import javax.swing.*;

public class CustomMenu extends JMenu{

    private Color backgroundColor;

    public CustomMenu(String text, Color backgroundColor) {
        super(text);
        this.backgroundColor = backgroundColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(10, 10, getWidth(), getHeight());

        super.paintComponent(g);

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(getText(), 0, getHeight() / 2 + g2d.getFontMetrics().getHeight() / 2);
        g2d.dispose();
    }
}