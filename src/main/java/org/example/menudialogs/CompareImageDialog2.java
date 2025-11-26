package org.example.menudialogs;

import org.example.enums.FontType;
import org.example.enums.UniqueFont;
import org.w3c.dom.Text;

import javax.swing.*;
//import org.junit.runner.notification.Failure;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class CompareImageDialog2 extends JFrame {

    private static BufferedImage[] allImages/* = new BufferedImage[5]*/;
    private JPanel imagePanel; // Panel a képeknek
    private JPanel textPanel = new JPanel(); // Panel a szövegnek
    private JTextArea textArea;

    public CompareImageDialog2() throws IOException, FontFormatException {


        setTitle("Image report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        textArea.setBackground(new Color(242, 255, 112, 64));
        textArea.setForeground(Color.BLUE);


        JScrollPane textScrollPane = new JScrollPane(textArea);

        showImage2();
        JScrollPane imageScrollPane = new JScrollPane(imagePanel);

        //imageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //imageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);



        //textScrollPane.setViewportView(textPanel);
        //imageScrollPane.setViewportView(imagePanel);

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\imagereport.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        writeImageDialog();

        add(textScrollPane, BorderLayout.NORTH);
        add(imageScrollPane, BorderLayout.CENTER);

        pack();
        //setLocationRelativeTo(null);
        setVisible(true);
    }



    public void writeImageDialog(){

        CompareImage2 compareImage2 = new CompareImage2();

        try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\imagereport.txt")) {
            writer.write(compareImage2.compareImages());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO SAMEIMAGE.PROPERTIES - OPTIONAL
    private void showImage() {

        //decodeAndSaveImages2("images_copy.properties", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_same_pics");

        try {
            File path = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_same_pics\\qa2_pl");
            File[] allFiles = path.listFiles();
            System.out.println("The length of the same all file is:" + allFiles.length);

            JLabel[] label = new JLabel[allFiles.length];
            allImages = new BufferedImage[allFiles.length];
            for(int i = 0; i < allFiles.length; i++){
                try{
                    allImages[i] = ImageIO.read(allFiles[i]);
                    int actualWidth = allImages[i].getWidth();
                    int actualHeight = allImages[i].getHeight();

                    BiPredicate<Integer, Integer> isBigImage = (w, h) -> w > 500 && h > 1000;

                    Function<Integer, Integer> halveNumber = number -> isBigImage.test(actualWidth, actualHeight) ? number / 2 : number;

                    int newWidth = halveNumber.apply(actualWidth);
                    int newHeight = halveNumber.apply(actualHeight);

                    label[i] = new JLabel();
                    ImageIcon icon = new ImageIcon(allImages[i].getScaledInstance((int) Math.round(newWidth * 0.3), (int) Math.round(newHeight * 0.3), Image.SCALE_SMOOTH));
                    label[i].setIcon(icon);
                    imagePanel.add(label[i]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            imagePanel.setBackground(Color.WHITE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showImage2() {
        try {
            File path = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_same_pics\\qa2_pl");
            File[] allFiles = path.listFiles();
            if (allFiles == null) {
                throw new RuntimeException("No files found in the specified directory.");
            }

            System.out.println("The length of the same all file is:" + allFiles.length);

            //imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
            //imagePanel.setLayout(new GridLayout(10, 3, 5, 5));

            imagePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                }
            };
            imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

            JLabel[] label = new JLabel[allFiles.length];
            allImages = new BufferedImage[allFiles.length];

            int totalHeight = 0; // Calculate total height for preferred size
            Border yellowBorder = BorderFactory.createLineBorder(Color.YELLOW, 2,true);

            for (int i = 0; i < allFiles.length; i++) {
                try {
                    allImages[i] = ImageIO.read(allFiles[i]);
                    int actualWidth = allImages[i].getWidth();
                    int actualHeight = allImages[i].getHeight();

                    BiPredicate<Integer, Integer> isBigImage = (w, h) -> w > 500 && h > 1000;

                    Function<Integer, Integer> halveNumber = number -> isBigImage.test(actualWidth, actualHeight) ? number / 2 : number;

                    int newWidth = halveNumber.apply(actualWidth);
                    int newHeight = halveNumber.apply(actualHeight);

                    label[i] = new JLabel();
                    ImageIcon icon = new ImageIcon(allImages[i].getScaledInstance((int) Math.round(newWidth * 0.3), (int) Math.round(newHeight * 0.3), Image.SCALE_SMOOTH));
                    label[i].setIcon(icon);
                    label[i].setBorder(yellowBorder);

                    imagePanel.add(label[i]);

                    totalHeight += (int) Math.round(newHeight * 0.12); // Accumulate the total height
                    totalHeight += 10; // Add some spacing between images
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            int panelWidth = 1600;
            int panelHeight = totalHeight;
            imagePanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
            imagePanel.setBackground(Color.WHITE);

            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);

            Border titledBorder = BorderFactory.createTitledBorder(
                    redBorder, "Same Images", TitledBorder.CENTER, TitledBorder.TOP
            );
            TitledBorder customTitledBorder = (TitledBorder) titledBorder;

            //customTitledBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
            customTitledBorder.setTitleFont(UniqueFont.AVERIA_SERIF_LIBRE.createUniqueFont(FontType.BOLD, 16));
            customTitledBorder.setTitleColor(Color.BLUE);


            imagePanel.setBorder(customTitledBorder);
            imagePanel.setBackground(new Color(188, 140, 165, 44));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method is replaced by transferPicturesFromFolderToAnotherFolder in CompareImage class
    private String decodeAndSaveImages2(String filePath, String outputDirectory) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + filePath)) {
            properties.load(fileInputStream);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                String fileName = (String) entry.getKey();
                String base64Image = (String) entry.getValue();

                byte[] imageBytes = Base64.getDecoder().decode(base64Image);

                File outputFile = new File(outputDirectory);
                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile + "/" + fileName)) {
                    fileOutputStream.write(imageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return outputFile.getAbsolutePath();
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a(z) " + filePath + " fájl beolvasása során.");
            e.printStackTrace();
        }
        return null;
    }


}