package maytestuse;

import tests.UATShellGB;
import utils.dnd.TestNameExtractor;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class ChooseDropActionDemo4 extends JFrame {

    DefaultListModel<String> from;
    DefaultListModel<String> copy = new DefaultListModel<>();
    DefaultListModel<String> move = new DefaultListModel<>();
    private String[] movedItems;
    private boolean isClosed = false;  // Flag to indicate if the window is closed

    JList<String> dragFrom;
    ///JList<String> moveTo;
    //JList<String> copyTo;


    public ChooseDropActionDemo4() {
        super("ChooseDropActionDemo2");

        // Initialize from list model and populate it
        from = new DefaultListModel<>();
        List<String> testNames = new TestNameExtractor().getTestNames(UATShellGB.class);
        for (String testName : testNames) {
            from.addElement(testName);
        }

        // Set up the GUI components
        initializeGUI2();

        // Add window listener to handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isClosed = true;  // Set the flag when the window is closed
                processMoveList();  // Call a method to process and store the 'move' list
            }
        });
    }

    private void initializeGUI2() {
        setLayout(new BorderLayout());

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // Set up the drag list
        dragFrom = new JList<>(from);
        dragFrom.setTransferHandler(new FromTransferHandler());
        dragFrom.setPrototypeCellValue("List Item WWWWWW");
        dragFrom.setDragEnabled(true);
        dragFrom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JLabel label = new JLabel("Drag from here:");
        label.setAlignmentX(0f);
        p.add(label);
        JScrollPane sp = new JScrollPane(dragFrom);
        sp.setAlignmentX(0f);
        p.add(sp);
        add(p, BorderLayout.WEST);

        // Set up the drop lists
        JList<String> moveTo = new JList<>(move);
        moveTo.setTransferHandler(new ToTransferHandler(TransferHandler.COPY));
        moveTo.setDropMode(DropMode.INSERT);


        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        
        label = new JLabel("Drop to MOVE to here:");
        label.setAlignmentX(0f);
        p.add(label);
        sp = new JScrollPane(moveTo);
        sp.setAlignmentX(0f);
        p.add(sp);


        p.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        add(p, BorderLayout.CENTER);

        JButton btnAddAll = new JButton("Add All");
        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        JButton btnRemoveAll = new JButton("Remove All");

        btnAddAll.addActionListener(e -> {
            int size = dragFrom.getModel().getSize();
            for (int i = 0; i < size; i++) {
                move.addElement(dragFrom.getModel().getElementAt(i));
            }
            from.clear();
            moveTo.setModel(move);
        });

        btnAdd.addActionListener(e -> {
            int removeIndex = 0;
            int size = dragFrom.getModel().getSize();

            if (size > 1){
                while (size != 0){
                    if(dragFrom.getSelectedIndex() != -1){
                        removeIndex = dragFrom.getSelectedIndex();
                        move.addElement(dragFrom.getSelectedValue());
                        from.removeElementAt(removeIndex);
                        size--;
                    } else {
                        break;
                    }
                }
            }   else {
                removeIndex = dragFrom.getSelectedIndex();
                move.addElement(dragFrom.getSelectedValue());
                from.removeElementAt(removeIndex);
            }
            moveTo.setModel(move);
        });

        btnRemoveAll.addActionListener(e -> {
            int size = moveTo.getModel().getSize();
            System.out.println(size);
            for (int i = 0; i < size; i++) {
                from.addElement(moveTo.getModel().getElementAt(i));
            }
            move.clear();
        });


        p = new JPanel();


        /*p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(btnAdd);
        p.add(btnAddAll);
        p.add(btnRemove);
        p.add(btnRemoveAll);
        p.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(p, BorderLayout.SOUTH);*/

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JPanel buttonPanelNorth = new JPanel();
        buttonPanelNorth.setLayout(new BoxLayout(buttonPanelNorth, BoxLayout.X_AXIS));
        buttonPanelNorth.add(btnAdd);
        buttonPanelNorth.add(btnAddAll);
        buttonPanelNorth.add(btnRemove);
        buttonPanelNorth.add(btnRemoveAll);
        buttonPanelNorth.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel buttonPanelSouth = new JPanel();
        buttonPanelSouth.setLayout(new BoxLayout(buttonPanelSouth, BoxLayout.X_AXIS));
        JButton btnRunTests = new JButton("Run Selected Test Cases");
        btnRunTests.addActionListener(e -> {
            isClosed = true;
            processMoveList();
            dispose();
        });
        buttonPanelSouth.add(btnRunTests);
        buttonPanelSouth.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        p.add(buttonPanelNorth);
        p.add(buttonPanelSouth);

        add(p, BorderLayout.SOUTH);

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getContentPane().setPreferredSize(new Dimension(320, 315));

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    private void initializeGUI() {
        setLayout(new BorderLayout());

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // Set up the drag list
        dragFrom = new JList<>(from);
        dragFrom.setTransferHandler(new FromTransferHandler());
        dragFrom.setPrototypeCellValue("List Item WWWWWW");
        dragFrom.setDragEnabled(true);
        dragFrom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JLabel label = new JLabel("Drag from here:");
        label.setAlignmentX(0f);
        p.add(label);
        JScrollPane sp = new JScrollPane(dragFrom);
        sp.setAlignmentX(0f);
        p.add(sp);
        add(p, BorderLayout.WEST);

        // Set up the drop lists
        JList<String> moveTo = new JList<>(copy);
        moveTo.setTransferHandler(new ToTransferHandler(TransferHandler.COPY));
        moveTo.setDropMode(DropMode.INSERT);
        JList<String> copyTo = new JList<>(move);
        copyTo.setTransferHandler(new ToTransferHandler(TransferHandler.MOVE));
        copyTo.setDropMode(DropMode.INSERT);

        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        label = new JLabel("Drop to COPY to here:");
        label.setAlignmentX(0f);
        p.add(label);
        sp = new JScrollPane(moveTo);
        sp.setAlignmentX(0f);
        p.add(sp);
        label = new JLabel("Drop to MOVE to here:");
        label.setAlignmentX(0f);
        p.add(label);
        sp = new JScrollPane(copyTo);
        sp.setAlignmentX(0f);
        p.add(sp);
        p.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        add(p, BorderLayout.CENTER);

        JButton btnAddAll = new JButton("Add All");
        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        JButton btnRemoveAll = new JButton("Remove All");

        btnAddAll.addActionListener(e -> {
            int size = dragFrom.getModel().getSize();
            for (int i = 0; i < size; i++) {
                move.addElement(dragFrom.getModel().getElementAt(i));
            }
            from.clear();
            moveTo.setModel(move);
        });

        btnAdd.addActionListener(e -> {
            int removeIndex = 0;
            int size = dragFrom.getModel().getSize();

            if (size > 1){
                while (size != 0){
                    if(dragFrom.getSelectedIndex() != -1){
                        removeIndex = dragFrom.getSelectedIndex();
                        move.addElement(dragFrom.getSelectedValue());
                        from.removeElementAt(removeIndex);
                        size--;
                    } else {
                        break;
                    }
                }
            }   else {
                removeIndex = dragFrom.getSelectedIndex();
                move.addElement(dragFrom.getSelectedValue());
                from.removeElementAt(removeIndex);
            }
            moveTo.setModel(move);
        });


        //TODO IF MOVE LIST IS EMPTY A POP UP WARNING SHOULD APPEAR FOR THE USER, WHICH ONE WARNS THE USER THAT NO TEST CASES ARE SELECTED
        btnRemoveAll.addActionListener(e -> {
            int size = moveTo.getModel().getSize();
            System.out.println(size);
            for (int i = 0; i < size; i++) {
                from.addElement(moveTo.getModel().getElementAt(i));
            }
            move.clear();
        });


        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(btnAdd);
        p.add(btnAddAll);
        p.add(btnRemove);
        p.add(btnRemoveAll);
        p.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(p, BorderLayout.SOUTH);


        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getContentPane().setPreferredSize(new Dimension(320, 315));

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    class FromTransferHandler extends TransferHandler {
        public int getSourceActions(JComponent comp) {
            return COPY_OR_MOVE;
        }

        private int[] indices;

        public Transferable createTransferable(JComponent comp) {
            indices = dragFrom.getSelectedIndices();
            List<String> selectedValues = dragFrom.getSelectedValuesList();
            return new StringListTransferable(selectedValues);
        }

        public void exportDone(JComponent comp, Transferable trans, int action) {
            if (action != MOVE) {
                return;
            }
            // Remove elements from the last index to the first to avoid shifting issues
            Arrays.sort(indices);
            for (int i = indices.length - 1; i >= 0; i--) {
                from.removeElementAt(indices[i]);
            }
        }
    }

    class ToTransferHandler extends TransferHandler {
        int action;

        public ToTransferHandler(int action) {
            this.action = action;
        }

        public boolean canImport(TransferSupport support) {
            if (!support.isDrop()) {
                return false;
            }
            if (!support.isDataFlavorSupported(StringListTransferable.STRING_LIST_FLAVOR)) {
                return false;
            }
            boolean actionSupported = (action & support.getSourceDropActions()) == action;
            if (actionSupported) {
                support.setDropAction(action);
                return true;
            }
            return false;
        }

        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            int index = dl.getIndex();
            List<String> data;
            try {
                data = (List<String>) support.getTransferable().getTransferData(StringListTransferable.STRING_LIST_FLAVOR);
            } catch (UnsupportedFlavorException | java.io.IOException e) {
                return false;
            }
            JList<String> list = (JList<String>) support.getComponent();
            DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
            for (int i = 0; i < data.size(); i++) {
                model.add(index + i, data.get(i));
            }
            Rectangle rect = list.getCellBounds(index, index + data.size() - 1);
            list.scrollRectToVisible(rect);
            list.setSelectedIndices(IntStream.range(index, index + data.size()).toArray());
            list.requestFocusInWindow();
            return true;
        }
    }

    private void processMoveList() {
        List<String> testNames = convertToList(move);
        movedItems = testNames.stream()
                .map(name -> {
                    try {
                        Class<?> clazz = Class.forName("tests.UATShellGB");
                        Method method = clazz.getMethod(name);
                        // A metódus nevének visszaadása
                        return method.getName();
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .toArray(String[]::new);
    }

    private List<String> convertToList(DefaultListModel<String> model) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            list.add(model.getElementAt(i));
        }
        return list;
    }

    public String[] getMovedItems() {
        return movedItems;
    }

    public boolean isClosed() {
        return isClosed;
    }
}

class StringListTransferable implements Transferable {
    public static final DataFlavor STRING_LIST_FLAVOR = new DataFlavor(List.class, "List of Strings");
    private final List<String> data;

    public StringListTransferable(List<String> data) {
        this.data = data;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { STRING_LIST_FLAVOR, DataFlavor.stringFlavor };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return STRING_LIST_FLAVOR.equals(flavor) || DataFlavor.stringFlavor.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (STRING_LIST_FLAVOR.equals(flavor)) {
            return data;
        } else if (DataFlavor.stringFlavor.equals(flavor)) {
            return String.join("\n", data);
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
