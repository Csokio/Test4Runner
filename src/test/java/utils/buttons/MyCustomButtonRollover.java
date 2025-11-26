package utils.buttons;
import org.example.enums.FontType;
import org.example.enums.UniqueFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MyCustomButtonRollover extends JButton {

    private Font normalFont;
    private Font rolloverFont;

    public MyCustomButtonRollover(String text, Color normalColor, Color rolloverColor) throws IOException, FontFormatException {
        super(text);


        setFont(UniqueFont.ACLONICA.createUniqueFont(FontType.BOLD,12));

        // Set the normal background color
        setBackground(normalColor);
        setForeground(Color.WHITE);

        // Load the custom font for rollover state
        try {

            rolloverFont = UniqueFont.INDIE_FLOWER.createUniqueFont(FontType.BOLD,14);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            rolloverFont = getFont().deriveFont(Font.BOLD); // Fallback to default font
        }

        // Store the original font
        normalFont = getFont();

        // Add a mouse listener to handle rollover events
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(rolloverColor);
                setForeground(Color.BLACK);
                setFont(rolloverFont);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
                setForeground(Color.WHITE);
                setFont(normalFont);
            }
        });
    }
}