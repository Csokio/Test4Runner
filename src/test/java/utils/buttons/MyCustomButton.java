package utils.buttons;

import org.example.enums.FontType;
import org.example.enums.UniqueFont;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MyCustomButton extends JButton {

    private boolean isYellow = false;
    private final Color normalColor = new Color(121, 136, 180);
    private final Color selectedCountryColor = new Color(111, 231, 111); // Assuming a rollover color
    private final Color rollOverColor = new Color(229, 210, 131);


    public MyCustomButton(String text) {
        super(text);
        setBackground(normalColor);
        setForeground(Color.WHITE);
        try {

            setFont(UniqueFont.AVERIA_SERIF_LIBRE.createUniqueFont(FontType.BOLD, 14));

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (getModel().isRollover()) {
                    setBackground(rollOverColor);
                    setForeground(Color.BLACK);
                    try {

                        setFont(UniqueFont.INDIE_FLOWER.createUniqueFont(FontType.BOLD, 14));

                    } catch (IOException | FontFormatException e) {
                        e.printStackTrace();
                    }

                } else if (!isYellow) {
                    setBackground(normalColor);
                    setForeground(Color.WHITE);
                    try {

                        setFont(UniqueFont.AVERIA_SERIF_LIBRE.createUniqueFont(FontType.BOLD, 14));

                    } catch (IOException | FontFormatException e) {
                        e.printStackTrace();
                    }

                } else {
                    setBackground(selectedCountryColor);
                    setForeground(Color.BLACK);
                    try {

                        setFont(UniqueFont.PERMANENT_MARKER.createUniqueFont(FontType.PLAIN, 14));

                    } catch (IOException | FontFormatException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isYellow) {
                    setBackground(normalColor);
                } else {
                    setBackground(selectedCountryColor);
                }
                isYellow = !isYellow;
                revalidate();
                repaint();
            }
        });
    }

}