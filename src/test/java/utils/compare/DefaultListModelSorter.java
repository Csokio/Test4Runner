package utils.compare;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefaultListModelSorter {
    public DefaultListModel<String> sort(DefaultListModel<String> model, Comparator<String> comparator) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            list.add(model.getElementAt(i));
        }

        Collections.sort(list, comparator);

        model.clear();
        for (String element : list) {
            model.addElement(element);
        }
        return model;
    }
}