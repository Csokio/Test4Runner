package org.example.mayuse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUp extends JFrame implements ActionListener{
    Popup p;

    // constructor
    PopUp()
    {
        // create a frame
        JFrame f = new JFrame("pop");

        // create a label
        JLabel l = new JLabel("This is a popup");

        f.setSize(400, 400);

        PopupFactory pf = new PopupFactory();

        // create a panel
        JPanel p2 = new JPanel();

        // set Background of panel
        p2.setBackground(Color.red);

        p2.add(l);

        // create a popup
        p = pf.getPopup(f, p2, 180, 100);

        // create a button
        JButton b = new JButton("click");

        // add action listener
        b.addActionListener((ActionListener) this);

        // create a panel
        JPanel p1 = new JPanel();

        p1.add(b);
        JOptionPane.showMessageDialog(f, "thank you for using java");

        f.add(p1);
        f.show();
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        p.show();
    }
    // main class
    public static void main(String args[])
    {
        PopUp p = new PopUp();
    }
}
