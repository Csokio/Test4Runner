package maytestuse;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TestCaseSelector2 extends JFrame {
    private JList<String> availableTestsList;
    private DefaultListModel<String> availableTestsModel;
    private JList<String> selectedTestsList;
    private DefaultListModel<String> selectedTestsModel;
    private JButton runTestsButton;

    public TestCaseSelector2() {
        setTitle("Test Case Selector");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Available tests list
        availableTestsModel = new DefaultListModel<>();
        availableTestsModel.addElement("Test Case 1");
        availableTestsModel.addElement("Test Case 2");
        availableTestsModel.addElement("Test Case 3");
        availableTestsList = new JList<>(availableTestsModel);
        availableTestsList.setDragEnabled(true);
        availableTestsList.setTransferHandler(new ListTransferHandler());

        // Selected tests list
        selectedTestsModel = new DefaultListModel<>();
        selectedTestsList = new JList<>(selectedTestsModel);
        selectedTestsList.setDropMode(DropMode.INSERT);
        selectedTestsList.setTransferHandler(new ListTransferHandler());

        // Run tests button
        runTestsButton = new JButton("Run Tests");
        runTestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runSelectedTests();
            }
        });

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(availableTestsList), BorderLayout.WEST);
        add(new JScrollPane(selectedTestsList), BorderLayout.CENTER);
        add(runTestsButton, BorderLayout.SOUTH);
    }

    private void runSelectedTests() {
        List<String> selectedTests = new ArrayList<>();
        for (int i = 0; i < selectedTestsModel.getSize(); i++) {
            selectedTests.add(selectedTestsModel.getElementAt(i));
        }

        // Run the selected tests using Selenium
        for (String test : selectedTests) {
            System.out.println("Running: " + test);
            // Here you can integrate with your Selenium test runner
            // For example: SeleniumTestRunner.runTest(test);
        }
    }

    private class ListTransferHandler extends TransferHandler {
        private int[] indices = null;
        private int addIndex = -1; // Location where items were added
        private int addCount = 0;  // Number of items added.

        @Override
        protected Transferable createTransferable(JComponent c) {
            JList<String> list = (JList<String>) c;
            indices = list.getSelectedIndices();
            List<String> values = list.getSelectedValuesList();
            return new StringSelection(String.join("\n", values));
        }

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            Transferable transferable = support.getTransferable();
            String data;
            try {
                data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                return false;
            }

            JList.DropLocation dropLocation = (JList.DropLocation) support.getDropLocation();
            int index = dropLocation.getIndex();
            DefaultListModel<String> listModel = (DefaultListModel<String>) ((JList<?>) support.getComponent()).getModel();

            String[] values = data.split("\n");
            addIndex = index;
            addCount = values.length;
            for (int i = 0; i < values.length; i++) {
                listModel.add(index++, values[i]);
            }

            return true;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE && indices != null) {
                JList<String> list = (JList<String>) source;
                DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();

                if (addCount > 0) {
                    for (int i = 0; i < indices.length; i++) {
                        if (indices[i] > addIndex) {
                            indices[i] += addCount;
                        }
                    }
                }
                for (int i = indices.length - 1; i >= 0; i--) {
                    model.remove(indices[i]);
                }
            }
            indices = null;
            addCount = 0;
            addIndex = -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestCaseSelector2().setVisible(true);
            }
        });
    }
}