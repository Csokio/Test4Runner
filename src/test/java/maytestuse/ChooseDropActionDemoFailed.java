package maytestuse;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;

import tests.WebTestTH;
import tests.qa2pl.login.QA2PLIN;
import tests.qa2pl.logout.QA2PLOUT;
import utils.buttons.MyConfirmButton;
import utils.buttons.MyCustomButtonRollover;
import utils.buttons.MyCustomDnDButton;
import utils.compare.DefaultListModelSorter;
import utils.compare.MethodNameComparator;
import tests.UATShellGB;
import utils.FailedTestManager;
import utils.dnd.CustomListCellRenderer;
import utils.dnd.TestNameExtractor;


//TODO PRIO ERROR IN LINE 146
public class ChooseDropActionDemoFailed extends JFrame {

    DefaultListModel<String> from;
    DefaultListModel<String> fromFailed;
    DefaultListModel<String> copy = new DefaultListModel<>();
    DefaultListModel<String> move = new DefaultListModel<>();
    DefaultListModel<String> moveFailed = new DefaultListModel<>();

    DefaultListModel<String> allTests = new DefaultListModel<>();
    List<String> movedAllSuccessList = new ArrayList<>();
    List<String> movedAllFailedList = new ArrayList<>();
    List<String> movedAllItemsList = new ArrayList<>();
    List<String> allItemsList = new ArrayList<>();
    private String[] movedAllItems;
    private String[] movedItems;
    private String[] movedIFailedItems;
    private List<String> allTestNames;

    private boolean isClosed = false;  // Flag to indicate if the window is closed
    private boolean isRunAllBtnClicked;

    JList<String> dragFrom;
    ///JList<String> moveTo;
    //JList<String> copyTo;

    BorderLayout borderLayout = new BorderLayout();
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel;
    FailedTestManager failedTestManager = new FailedTestManager();

    private Map<String, Class> mapCountryClass;
    private Queue<Class> queueTestClass;
    private Set<String> country = new HashSet<>();

    public Map<String, Class> getMapCountryClass(){
        return this.mapCountryClass;
    }

    public Queue<Class> getQueueTestClass(){
        return this.queueTestClass;
    }

    //TODO CAPABLE DE FAIRE MARCHER N'IMPORTE QUEL TEST CLASSES
    public ChooseDropActionDemoFailed(Queue<String> previouslyFailedTestNames) throws IOException, FontFormatException {
        super("ChooseDropActionDemoFailed");


        queueTestClass = new LinkedList<>();
        mapCountryClass = new HashMap<>();
        mapCountryClass.put("GB", UATShellGB.class);
        mapCountryClass.put("TH", WebTestTH.class);
        mapCountryClass.put("PLIN", QA2PLIN.class);
        mapCountryClass.put("PLOUT", QA2PLOUT.class);


        Set<String> possibleCountries = new HashSet<>();
        possibleCountries.add("GB");
        possibleCountries.add("TH");
        possibleCountries.add("PLIN");
        possibleCountries.add("PLOUT");

        country.add("GB");
        country.add("TH");
        country.add("PLIN");
        country.add("PLOUT");
        country.stream()
                .filter(possibleCountries::contains)
                .map(mapCountryClass::get)
                .forEach(queueTestClass::offer);




        fromFailed = new DefaultListModel<>();
        List<String> failedTests = failedTestManager.getFailedTestcases();
        System.out.print("Previously failed test case: ");
        for(String failedTest: failedTests){
            System.out.print(", " + failedTest);
        }

        int equalIndex = 1;
        int testNameIndex = 1;
        System.out.println("Previously failed test case's size: " + previouslyFailedTestNames.size());
        while(!previouslyFailedTestNames.isEmpty()){
            String prevFailedTestName = previouslyFailedTestNames.poll();
            System.out.println("testName index is: " + testNameIndex++);
            System.out.println("Test name is: " + prevFailedTestName);
            for(String failedTest: failedTests){
                if(prevFailedTestName.equals(failedTest)){
                    System.out.println("Equal index is: " + equalIndex++);
                    System.out.print("  -  Failed test name is: " + failedTest);
                    fromFailed.addElement(prevFailedTestName);
                }
            }
        }



        fromFailed = new DefaultListModelSorter().sort(fromFailed, new MethodNameComparator());
        System.out.println("The size of the from failed is: " + fromFailed.size());



        System.out.println(failedTests.size());
        System.out.println(fromFailed.size());
        //System.out.println("The from list's  size is: " + from.size());
        // Set up the GUI components
        initializeGUI5();

        // Add window listener to handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                isClosed = true;  // Set the flag when the window is closed
                dispose();  // Call a method to process and store the 'move' list
            }
        });
    }



    private void initializeGUI5() throws IOException, FontFormatException {
        mainPanel = new JPanel(cardLayout);

        //TODO IF DOESN'T LEAVE MOVE LIST EMPTY WINDOW MENU CLASS RUNS AFTER SELECTING TEST CASES


        JPanel panelSecond = createPanel(fromFailed, moveFailed,2,"Switch to succeed tests", Color.RED, new Color(222, 117, 117), e -> cardLayout.show(mainPanel, "firstPanel"),
                this::failedProcessMoveList, this::processMoveAllList);


        mainPanel.add(panelSecond, "secondPanel");

        add(mainPanel, BorderLayout.CENTER);

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        getContentPane().setPreferredSize(new Dimension(400, 340));

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

    }


    private JPanel createPanel(DefaultListModel<String> selectableTestCases, DefaultListModel<String> movedTestCases, int testCaseColorIndicator, String buttonText, Color buttonBackgroundColor,
                               Color buttonRolloverColor, ActionListener switchAction, Runnable processAction, Runnable processEveryTest) throws IOException, FontFormatException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        File fontFile = new File("src/main/resources/font/Salsa-Regular.ttf");
        Font salsaFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 14);

        JPanel methodHolderPanel = new JPanel();
        //methodHolderPanel.setLayout(new BoxLayout(methodHolderPanel, BoxLayout.X_AXIS));
        methodHolderPanel.setLayout(new GridLayout(1,2));


        JPanel panelWest = new JPanel();
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));

        //new Color(12, 112, 16);

        // Set up the drag list
        JList<String> dragFrom = new JList<>(selectableTestCases);
        System.out.println("The from list's  size is: " + selectableTestCases.getSize());
        dragFrom.setTransferHandler(new maytestuse.ChooseDropActionDemoFailed.FromTransferHandler());
        dragFrom.setPrototypeCellValue("List Item WWWWWW");
        dragFrom.setDragEnabled(true);
        dragFrom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //dragFrom.setFont(salsaFont);
        dragFrom.setCellRenderer(new CustomListCellRenderer(salsaFont,testCaseColorIndicator));
        dragFrom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Ha dupla kattintás történt
                    int index = dragFrom.locationToIndex(e.getPoint()); // A kattintott elem indexének lekérése
                    if (index != -1) {
                        String selectedItem = dragFrom.getModel().getElementAt(index);
                        runRandomMethod(selectedItem);
                        FlatLafMethodEditorWindow.runFlatLafMethodEditor();
                    }
                }
            }
        });

        JLabel label = new JLabel("Drag from here:");
        label.setAlignmentX(0f);
        panelWest.add(label);


        JScrollPane sp = new JScrollPane(dragFrom);
        sp.setBackground(Color.RED);
        sp.setAlignmentX(0f);

        panelWest.add(sp);

        // Set up the drop lists
        JList<String> moveTo = new JList<>(movedTestCases);
        moveTo.setTransferHandler(new maytestuse.ChooseDropActionDemoFailed.ToTransferHandler(TransferHandler.COPY));
        moveTo.setDropMode(DropMode.INSERT);
        moveTo.setCellRenderer(new CustomListCellRenderer(salsaFont, testCaseColorIndicator));

        JPanel panelEast = new JPanel();
        panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));

        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        label = new JLabel("Drop to MOVE to here:");
        label.setAlignmentX(0f);
        panelEast.add(label);
        sp = new JScrollPane(moveTo);
        sp.setAlignmentX(0f);
        panelEast.add(sp);

        // Add button to switch panels
        //JButton buttonSwitch = new JButton(buttonText);

        MyCustomButtonRollover buttonSwitch = new MyCustomButtonRollover(buttonText, buttonBackgroundColor, buttonRolloverColor);
        buttonSwitch.setBackground(buttonBackgroundColor);


        buttonSwitch.addActionListener(switchAction);
        panelEast.add(buttonSwitch);

        methodHolderPanel.add(panelWest);
        methodHolderPanel.add(panelEast);

        panel.add(methodHolderPanel);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));


        MyCustomDnDButton btnAddAll = new MyCustomDnDButton("Add All");
        MyCustomDnDButton btnAdd = new MyCustomDnDButton("Add");
        MyCustomDnDButton btnRemove = new MyCustomDnDButton("Remove");
        MyCustomDnDButton btnRemoveAll = new MyCustomDnDButton("Remove All");

        btnAddAll.addActionListener(e -> {
            int size = dragFrom.getModel().getSize();
            System.out.println(size);
            for (int i = 0; i < size; i++) {
                movedTestCases.addElement(dragFrom.getModel().getElementAt(i));
            }
            selectableTestCases.clear();
            moveTo.setModel(movedTestCases);
        });

        btnAdd.addActionListener(e -> {
            int removeIndex = 0;
            int size = dragFrom.getModel().getSize();

            if (size >= 1) {
                while (size != 0) {
                    if (dragFrom.getSelectedIndex() != -1) {
                        removeIndex = dragFrom.getSelectedIndex();
                        //movedTestCases.addElement(dragFrom.getSelectedValue());
                        movedTestCases.addElement(dragFrom.getModel().getElementAt(dragFrom.getSelectedIndex()));
                        selectableTestCases.removeElementAt(removeIndex);
                        size--;
                    } else {
                        break;
                    }
                }
            } else {
                removeIndex = dragFrom.getSelectedIndex();
                movedTestCases.addElement(dragFrom.getSelectedValue());
                selectableTestCases.removeElementAt(removeIndex);
            }
            moveTo.setModel(movedTestCases);
        });

        btnRemove.addActionListener(e -> {
            int removeIndex = 0;
            int size = movedTestCases.getSize();

            if (size >= 1) {
                while (size != 0) {
                    if (moveTo.getSelectedIndex() != -1) {
                        removeIndex = moveTo.getSelectedIndex();
                        //movedTestCases.addElement(dragFrom.getSelectedValue());
                        selectableTestCases.addElement(moveTo.getModel().getElementAt(moveTo.getSelectedIndex()));
                        movedTestCases.removeElementAt(removeIndex);
                        size--;
                    } else {
                        break;
                    }
                }
            } else {
                /*removeIndex = moveTo.getSelectedIndex();
                selectableTestCases.addElement(moveTo.getSelectedValue());
                movedTestCases.removeElementAt(removeIndex);*/
                System.out.println("Index is smaller than 1");
            }
            moveTo.setModel(movedTestCases);

        });

        btnRemoveAll.addActionListener(e -> {
            int size = moveTo.getModel().getSize();
            System.out.println(size);
            for (int i = 0; i < size; i++) {
                selectableTestCases.addElement(moveTo.getModel().getElementAt(i));
            }
            movedTestCases.clear();
        });

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnAddAll);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnRemoveAll);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add Run Tests button
        JPanel runTestsPanel = new JPanel();
        runTestsPanel.setLayout(new BoxLayout(runTestsPanel, BoxLayout.X_AXIS));
        //JButton btnRunTests = new JButton("Run Selected Test Cases");
        MyConfirmButton btnRunTests = new MyConfirmButton("Run Selected", 14);
        btnRunTests.addActionListener(e -> {
            if(move.isEmpty() && moveFailed.isEmpty()){
                JOptionPane.showMessageDialog(this, "Select Test Cases to Run",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                isClosed = true;
                processAction.run();
                dispose();
            }
        });
        MyConfirmButton btnRunAllTests = new MyConfirmButton("Run All", 14);
        btnRunAllTests.addActionListener(e -> {
            isRunAllBtnClicked = true;
            isClosed = true;
            processEveryTest.run();
            dispose();
        });



        runTestsPanel.add(btnRunAllTests);
        runTestsPanel.add(Box.createRigidArea(new Dimension(10,0)));
        runTestsPanel.add(btnRunTests);
        runTestsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add button panels to a container panel
        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.Y_AXIS));
        panelSouth.add(buttonPanel);
        panelSouth.add(runTestsPanel);

        panel.add(panelSouth);


        return panel;
    }




    class FromTransferHandler extends TransferHandler {
        public int getSourceActions(JComponent comp) {
            return COPY_OR_MOVE;
        }

        private int[] indices;

        public Transferable createTransferable(JComponent comp) {
            indices = dragFrom.getSelectedIndices();
            List<String> selectedValues = dragFrom.getSelectedValuesList();
            return new maytestuse.StringListTransferableFailed(selectedValues);
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

        public boolean canImport(TransferHandler.TransferSupport support) {
            if (!support.isDrop()) {
                return false;
            }
            if (!support.isDataFlavorSupported(maytestuse.StringListTransferableFailed.STRING_LIST_FLAVOR)) {
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
            List<String> data;
            try {
                data = (List<String>) support.getTransferable().getTransferData(maytestuse.StringListTransferableFailed.STRING_LIST_FLAVOR);
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

    //TODO IN MOVE LISTS CLASS CLASSES SHOULD BE ADJUSTABLE
    private void processMoveList() {
        List<String> testNames = convertToList(move);

        testNames.addAll(convertToList(fromFailed));

        System.out.println("The size of the list of test cases: " + testNames.size());


        movedItems = testNames.stream()
                /*.map(name -> {
                    try {
                        Class<?> clazz = Class.forName("tests.UATShellGB");
                        Method method = clazz.getMethod(name);
                        // A metódus nevének visszaadása
                        return method.getName();
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                        return null;
                    }
                })*/
                .toArray(String[]::new);

    }

    private void processMoveAllList() {

        allTestNames = convertToList(from);
        allTestNames.addAll(convertToList(fromFailed));
        allTestNames.addAll(convertToList(move));
        allTestNames.addAll(convertToList(moveFailed));

        System.out.println("The size of the list of test cases: " + allTestNames.size() + " !!!!!!!!!!!!!!!!!!!!!!!!");


        movedItems = allTestNames.stream()
                /*.map(name -> {
                    try {
                        Class<?> clazz = Class.forName("tests.UATShellGB");
                        Method method = clazz.getMethod(name);
                        // A metódus nevének visszaadása
                        return method.getName();
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                        return null;
                    }
                })*/
                .toArray(String[]::new);
        allItemsList.addAll(Arrays.stream(movedItems).toList());
    }
    private void successProcessMoveList() {
        List<String> testNames = convertToList(move);
        movedItems = testNames.stream()
                /*.map(name -> {
                    try {
                        //Class<?> clazz = Class.forName("tests.UATShellGB");
                        //Class<?> clazz = Class.forName("tests.qa2pl.logout.QA2PLOUT");
                        //Class<?> clazz = Class.forName("tests.WebTestTH");
                        Class<?> clazz = Class.forName("tests.qa2pl.login.QA2PLIN");


                        Method method = clazz.getMethod(name);
                        // A metódus nevének visszaadása
                        return method.getName();
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                        return null;
                    }
                })*/
                .toArray(String[]::new);

        System.out.println("The size of the movedItemslits in success is: " + testNames.size());

        movedAllSuccessList.addAll(Arrays.stream(movedItems)
                .toList());
        if(moveFailed.size() > 0){
            List<String> testNamesFailed = convertToList(moveFailed);
            movedItems = testNamesFailed.stream()
                    /*.map(name -> {
                        try {
                            //Class<?> clazz = Class.forName("tests.UATShellGB");
                            //Class<?> clazz = Class.forName("tests.qa2pl.logout.QA2PLOUT");
                            //Class<?> clazz = Class.forName("tests.WebTestTH");
                            Class<?> clazz = Class.forName("tests.qa2pl.login.QA2PLIN");

                            Method method = clazz.getMethod(name);
                            // A metódus nevének visszaadása
                            return method.getName();
                        } catch (ClassNotFoundException | NoSuchMethodException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })*/
                    .toArray(String[]::new);

            System.out.println("The size of the movedItemslits in failed is: " + testNamesFailed.size());

            movedAllFailedList.addAll(Arrays.stream(movedItems)
                    .toList());
        }
    }
    private void failedProcessMoveList() {
        List<String> testNames = convertToList(moveFailed);
        movedItems = testNames.stream()
                /*.map(name -> {
                    try {
                        //Class<?> clazz = Class.forName("tests.UATShellGB");
                        //Class<?> clazz = Class.forName("tests.qa2pl.logout.QA2PLOUT");
                        //Class<?> clazz = Class.forName("tests.WebTestTH");
                        Class<?> clazz = Class.forName("tests.qa2pl.login.QA2PLIN");

                        Method method = clazz.getMethod(name);
                        // A metódus nevének visszaadása
                        return method.getName();
                    } catch (ClassNotFoundException | NoSuchMethodException e) {
                        e.printStackTrace();
                        return null;
                    }
                })*/
                .toArray(String[]::new);

        System.out.println("The size of the movedItemslits in failed is: " + testNames.size());

        movedAllFailedList.addAll(Arrays.stream(movedItems)
                .toList());
        if(!move.isEmpty()){
            List<String> testNamesSuccess = convertToList(move);
            movedItems = testNamesSuccess.stream()
                    /*.map(name -> {
                        try {
                            //Class<?> clazz = Class.forName("tests.UATShellGB");
                            //Class<?> clazz = Class.forName("tests.qa2pl.logout.QA2PLOUT");
                            //Class<?> clazz = Class.forName("tests.WebTestTH");
                            Class<?> clazz = Class.forName("tests.qa2pl.login.QA2PLIN");

                            Method method = clazz.getMethod(name);
                            // A metódus nevének visszaadása
                            return method.getName();
                        } catch (ClassNotFoundException | NoSuchMethodException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })*/
                    .toArray(String[]::new);

            System.out.println("The size of the movedItemslits in success is: " + testNamesSuccess.size());

            movedAllSuccessList.addAll(Arrays.stream(movedItems)
                    .toList());
        }
    }

    private List<String> convertToList(DefaultListModel<String> model) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            list.add(model.getElementAt(i));
        }
        return list;
    }

    public String[] getMovedItems() {
        if(isRunAllBtnClicked){
            return allItemsList.toArray(new String[0]);
        } else {
            movedAllItemsList.addAll(movedAllSuccessList);
            System.out.println("The size of the movedAllSuccessList is: " + movedAllSuccessList.size());

            movedAllItemsList.addAll(movedAllFailedList);
            //System.out.println("The size of the movedAllFailedList is: " + movedAllFailedList.size());

            System.out.println("The size of the movedALLItemsList is: " + movedAllItemsList.size());
            return movedAllItemsList.toArray(new String[0]);
        }
    }

    public boolean isClosed() {
        return isClosed;
    }
    private void runRandomMethod(String message){
        System.out.println("The following method is doubleclcicked: " + message);
    }
}

class StringListTransferableFailed implements Transferable {
    public static final DataFlavor STRING_LIST_FLAVOR = new DataFlavor(List.class, "List of Strings");
    private final List<String> data;

    public StringListTransferableFailed(List<String> data) {
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