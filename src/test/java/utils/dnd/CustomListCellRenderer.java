package utils.dnd;

import javax.swing.*;
import java.awt.*;

public class CustomListCellRenderer extends DefaultListCellRenderer {
    private final Font font;
    private Color color = null;



    public CustomListCellRenderer(Font font, int testCaseColorIndicator) {
        this.font = font;
        if(testCaseColorIndicator == 1){
            this.color = Color.BLUE;
        } else if (testCaseColorIndicator == 2) {
            this.color = Color.RED;
        }

    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
}
