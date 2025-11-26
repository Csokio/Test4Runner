package maytestuse;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import tests.UATShellGB;
import utils.dnd.TestNameExtractor;


public class ChooseDropActionDemo3 extends JFrame {

    DefaultListModel<String> from;
    DefaultListModel<String> copy = new DefaultListModel<>();
    DefaultListModel<String> move = new DefaultListModel<>();
    private String[] movedItems;
    private boolean isClosed = false;  // Flag to indicate if the window is closed

    JList<String> dragFrom;

    public ChooseDropActionDemo3() {
        super("ChooseDropActionDemo2");

        // Initialize from list model and populate it
        from = new DefaultListModel<>();
        List<String> testNames = new TestNameExtractor().getTestNames(UATShellGB.class);
        for (String testName : testNames) {
            from.addElement(testName);
        }

        // Set up the GUI components
        initializeGUI();

        // Add window listener to handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isClosed = true;  // Set the flag when the window is closed
                processMoveList();  // Call a method to process and store the 'move' list
            }
        });
    }

    /*private void initializeGUI() {
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

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        getContentPane().setPreferredSize(new Dimension(320, 315));

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to trigger windowClosed event
        setVisible(true);
    }*/

    private void initializeGUI() {
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // Set border on the main panel

        // Panel for the drag list
        JPanel dragPanel = new JPanel();
        dragPanel.setLayout(new BoxLayout(dragPanel, BoxLayout.Y_AXIS));

        dragFrom = new JList<>(from);
        dragFrom.setTransferHandler(new FromTransferHandler());
        dragFrom.setPrototypeCellValue("List Item WWWWWW");
        dragFrom.setDragEnabled(true);
        dragFrom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JLabel dragLabel = new JLabel("Drag from here:");
        dragLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dragPanel.add(dragLabel);

        JScrollPane dragScrollPane = new JScrollPane(dragFrom);
        dragScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        dragPanel.add(dragScrollPane);

        mainPanel.add(dragPanel);

        // Panel for the drop lists and button
        JPanel dropPanel = new JPanel();
        dropPanel.setLayout(new BoxLayout(dropPanel, BoxLayout.Y_AXIS));

        JList<String> moveTo = new JList<>(copy);
        moveTo.setTransferHandler(new ToTransferHandler(TransferHandler.COPY));
        moveTo.setDropMode(DropMode.INSERT);

        JLabel copyLabel = new JLabel("Drop to COPY to here:");
        copyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropPanel.add(copyLabel);

        JScrollPane copyScrollPane = new JScrollPane(moveTo);
        copyScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropPanel.add(copyScrollPane);

        JList<String> copyTo = new JList<>(move);
        copyTo.setTransferHandler(new ToTransferHandler(TransferHandler.MOVE));
        copyTo.setDropMode(DropMode.INSERT);

        JLabel moveLabel = new JLabel("Drop to MOVE to here:");
        moveLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropPanel.add(moveLabel);

        JScrollPane moveScrollPane = new JScrollPane(copyTo);
        moveScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropPanel.add(moveScrollPane);

        // Add the button directly below the last scroll pane
        JToggleButton toggleButton = new JToggleButton("Toggle Button");
        toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropPanel.add(toggleButton);
        dropPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds some space above the button

        mainPanel.add(dropPanel);

        // Set up the frame
        JFrame frame = new JFrame("ChooseDropActionDemo3");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.getContentPane().setPreferredSize(new Dimension(320, 315));
        frame.pack();
        frame.setVisible(true);
    }





    class FromTransferHandler extends TransferHandler {
        public int getSourceActions(JComponent comp) {
            return COPY_OR_MOVE;
        }

        private int index = 0;

        public Transferable createTransferable(JComponent comp) {
            index = dragFrom.getSelectedIndex();
            if (index < 0 || index >= from.getSize()) {
                return null;
            }
            return new StringSelection((String) dragFrom.getSelectedValue());
        }

        public void exportDone(JComponent comp, Transferable trans, int action) {
            if (action != MOVE) {
                return;
            }
            from.removeElementAt(index);
        }
    }

    class ToTransferHandler extends TransferHandler {
        int action;

        public ToTransferHandler(int action) {
            this.action = action;
        }

        public boolean canImport(TransferHandler.TransferSupport support) {
            if (!support.isDrop()) {
                return false;
            }
            if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return false;
            }
            boolean actionSupported = (action & support.getSourceDropActions()) == action;
            if (actionSupported) {
                support.setDropAction(action);
                return true;
            }
            return false;
        }

        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            int index = dl.getIndex();
            String data;
            try {
                data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | java.io.IOException e) {
                return false;
            }
            JList<String> list = (JList<String>) support.getComponent();
            DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
            model.insertElementAt(data, index);
            Rectangle rect = list.getCellBounds(index, index);
            list.scrollRectToVisible(rect);
            list.setSelectedIndex(index);
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