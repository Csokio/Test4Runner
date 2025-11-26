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

public class MyConfirmButton extends JButton {

    private boolean isYellow = false;
    private final Color normalColor = new Color(229, 195, 15);
    private final Color selectedCountryColor = new Color(253, 85, 115); // Assuming a rollover color
    private final Color rollOverColor = new Color(229, 210, 131);

    private final String originalText;

    private JDialog dialog;

    public MyConfirmButton(String text, int fontSize, JDialog dialog) {
        super(text);
        originalText = getText();
        this.dialog = dialog;
        setBackground(normalColor);
        setForeground(Color.BLACK);
        try {

            setFont(UniqueFont.AVERIA_SERIF_LIBRE.createUniqueFont(FontType.BOLD,fontSize));

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

                        setFont(UniqueFont.INDIE_FLOWER.createUniqueFont(FontType.BOLD, fontSize));

                        setText("Let's Go!");

                    } catch (IOException | FontFormatException e) {
                        e.printStackTrace();
                    }

                } else if (!isYellow) {
                    setBackground(normalColor);
                    setForeground(Color.BLACK);
                    try {

                        setFont(UniqueFont.AVERIA_SERIF_LIBRE.createUniqueFont(FontType.BOLD, fontSize));

                        setText(originalText);

                    } catch (IOException | FontFormatException e) {
                        e.printStackTrace();
                    }

                } else {
                    setBackground(selectedCountryColor);
                    setForeground(Color.BLACK);
                    try {

                        setFont(UniqueFont.PERMANENT_MARKER.createUniqueFont(FontType.PLAIN, fontSize));

                        setText(originalText);

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
                System.out.println("Confirm button clicked");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dispose();
            }
        });
    }

    public MyConfirmButton(String text, int fontSize) {
        super(text);
        originalText = getText();
        setBackground(normalColor);
        setForeground(Color.BLACK);
        try {

            setFont(UniqueFont.AVERIA_SERIF_LIBRE.createUniqueFont(FontType.BOLD, fontSize));

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

                        setFont(UniqueFont.INDIE_FLOWER.createUniqueFont(FontType.BOLD, fontSize));

                        setText("Let's Go!");

                    } catch (IOException | FontFormatException e) {
                        e.printStackTrace();
                    }

                } else if (!isYellow) {
                    setBackground(normalColor);
                    setForeground(Color.BLACK);
                    try {

                        setFont(UniqueFont.AVERIA_SERIF_LIBRE.createUniqueFont(FontType.BOLD, fontSize));

                        setText(originalText);

                    } catch (IOException | FontFormatException e) {
                        e.printStackTrace();
                    }

                } else {
                    setBackground(selectedCountryColor);
                    setForeground(Color.BLACK);
                    try {

                        setFont(UniqueFont.PERMANENT_MARKER.createUniqueFont(FontType.PLAIN, fontSize));

                        setText(originalText);

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