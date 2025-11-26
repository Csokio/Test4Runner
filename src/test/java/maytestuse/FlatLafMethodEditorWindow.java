package maytestuse;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.resources.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FlatLafMethodEditorWindow extends JFrame{

    private JTextArea codeArea;
    //private JTextPane codeArea;
    private JButton loadButton, saveButton;
    private String filePath;
    private LookAndFeel previousLookAndFeel;
    private Highlighter.HighlightPainter highlightPainter;
    private List<Integer> highlightPositions = new LinkedList<>();
    private int currentMatchIndex = -1;



    public FlatLafMethodEditorWindow(){
        try {
            previousLookAndFeel = UIManager.getLookAndFeel();
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Method Editor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        codeArea = new JTextArea();
        //codeArea = new JTextPane();
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        codeArea.setBackground(new Color(40,44,52));
        codeArea.setForeground(Color.WHITE);
        codeArea.setCaretColor(Color.WHITE);
        JScrollPane jScrollPane = new JScrollPane(codeArea);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(jScrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(30,30,30));

        loadButton = createStyledButton("Betöltés", new Color(52, 152, 219));
        saveButton = createStyledButton("Save", new Color(46, 204, 113));

        panel.add(loadButton);
        panel.add(saveButton);
        add(panel, BorderLayout.SOUTH);

        loadButton.addActionListener(e -> {
            try {
                loadCode();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        saveButton.addActionListener(e -> {
            try {
                saveCode();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        codeArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                    showSearchDialog();
                }
            }
        });

        highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.DARK_GRAY);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1));
        return button;
    }

    private void loadCode() throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        File defaultDirectory = new File("C:\\Users\\SőregiKrisztián\\IdeaProjects\\watbefore_secondhand_deuxieme\\src\\test\\java\\maytestuse");

        if(defaultDirectory.exists() && defaultDirectory.isDirectory()){
            fileChooser.setCurrentDirectory(defaultDirectory);
        } else {
            showWarningPopUp("Mappa nem található");
        }

        fileChooser.setDialogTitle("Válassz egy fájlt");
        fileChooser.setApproveButtonText("Megnyitás");

        int returnValue = fileChooser.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            filePath = fileChooser.getSelectedFile().getAbsolutePath();

            //JTextArea mellett használható
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                codeArea.setText("");
                while((line = br.readLine()) != null){
                    codeArea.append(line + "\n");
                }
                showSuccessPopUp("Fájl betöltve");

                //JTextPane mellett használható
                /*StyledDocument doc = codeArea.getStyledDocument();
                try {
                    doc.remove(0, doc.getLength());
                    String line;
                    while ((line = br.readLine()) != null) {
                        doc.insertString(doc.getLength(), line + "\n", null);
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }*/
            } catch (IOException e) {
                showErrorPopUp("Nem sikerült betölteni a fájlt");
            }
        }
    }

    private void saveCode() throws IOException {
        if(filePath != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                bw.write(codeArea.getText());
                showSuccessPopUp("Fájl sikeresen mentve");
            } catch (IOException e) {
                showErrorPopUp("Nem sikerült menteni a fájlt");
            }
        } else {
            showWarningPopUp("Először válassz egy fájlt");
        }
    }

    private void showSearchDialog() {
        JDialog searchDialog = new JDialog(this, "Keresés", false);
        searchDialog.setSize(350, 120);
        searchDialog.setLayout(new FlowLayout());
        searchDialog.setLocationRelativeTo(this);

        JLabel searchLabel = new JLabel("Keresett szó:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Keresés");
        JButton nextButton = new JButton("Következő");
        JButton closeButton = new JButton("Mégse");

        nextButton.setEnabled(false); // Alapból nem engedélyezett, amíg nincs találat

        searchDialog.add(searchLabel);
        searchDialog.add(searchField);
        searchDialog.add(searchButton);
        searchDialog.add(nextButton);
        searchDialog.add(closeButton);

        searchButton.addActionListener(e -> {
            highlightText(searchField.getText());
            nextButton.setEnabled(!highlightPositions.isEmpty());
        });

        nextButton.addActionListener(e -> moveToNextMatch());

        closeButton.addActionListener(e -> searchDialog.dispose());

        // Enter megnyomásával keresés
        searchField.addActionListener(e -> {
            highlightText(searchField.getText());
            nextButton.setEnabled(!highlightPositions.isEmpty());
        });

        searchDialog.setVisible(true);
    }



    private void highlightText(String searchText) {
        Highlighter highlighter = codeArea.getHighlighter();
        highlighter.removeAllHighlights();
        highlightPositions.clear();
        currentMatchIndex = -1;

        if (searchText == null || searchText.isEmpty()) {
            return;
        }

        String text = codeArea.getText();
        int index = text.indexOf(searchText);
        while (index >= 0) {
            highlightPositions.add(index);
            index = text.indexOf(searchText, index + searchText.length());
        }

        if (!highlightPositions.isEmpty()) {
            moveToNextMatch();
        }
    }

    /*private void highlightText(String searchText) {
        StyledDocument doc = codeArea.getStyledDocument();
        Style defaultStyle = codeArea.getStyle(StyleContext.DEFAULT_STYLE);
        Style highlightStyle = codeArea.addStyle("Highlight", null);

        StyleConstants.setForeground(highlightStyle, Color.RED); // Keresett szó piros
        StyleConstants.setBold(highlightStyle, true);

        try {
            doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true); // Reset szín
            String text = codeArea.getText().toLowerCase();
            String lowerSearch = searchText.toLowerCase();

            int index = text.indexOf(lowerSearch);
            while (index >= 0) {
                doc.setCharacterAttributes(index, searchText.length(), highlightStyle, false);
                index = text.indexOf(lowerSearch, index + searchText.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void moveToNextMatch() {
        if (highlightPositions.isEmpty()) {
            return;
        }

        currentMatchIndex = (currentMatchIndex + 1) % highlightPositions.size();
        int start = highlightPositions.get(currentMatchIndex);
        int end = start + codeArea.getText().substring(start).indexOf("\n");

        try {
            Highlighter highlighter = codeArea.getHighlighter();
            highlighter.removeAllHighlights();

            for (Integer pos : highlightPositions) {
                highlighter.addHighlight(pos, pos + codeArea.getText().substring(pos).indexOf("\n"),
                        new DefaultHighlighter.DefaultHighlightPainter(Color.DARK_GRAY));
            }

            // Az aktuális találat narancssárga
            highlighter.addHighlight(start, end, new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE));

            codeArea.setCaretPosition(start);
            codeArea.requestFocus();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void showSuccessPopUp(String message) {
        JOptionPane.showMessageDialog(this, message, "Siker", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorPopUp(String message) {
        JOptionPane.showMessageDialog(this, message, "Hiba", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarningPopUp(String message) {
        JOptionPane.showMessageDialog(this, message, "Figyelmeztetés", JOptionPane.WARNING_MESSAGE);
    }

    public static void runFlatLafMethodEditor() {
        SwingUtilities.invokeLater(() -> new FlatLafMethodEditorWindow().setVisible(true));
    }

    @Override
    public void dispose() {
        try {
            UIManager.setLookAndFeel(previousLookAndFeel);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        super.dispose();
    }

}
