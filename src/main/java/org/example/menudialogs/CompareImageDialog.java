package org.example.menudialogs;

import org.example.enums.FontType;
import org.example.enums.UniqueFont;

import javax.swing.*;
//import org.junit.runner.notification.Failure;

import javax.imageio.ImageIO;
import javax.swing.*;
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

public class CompareImageDialog extends JFrame {

    private static BufferedImage[] allImages = new BufferedImage[5];
    private JPanel imagePanel = new JPanel(); // Panel a képeknek
    private JPanel textPanel = new JPanel(); // Panel a szövegnek

    public CompareImageDialog() throws IOException, FontFormatException {

        writeImageDialog();

        setTitle("Image report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea(20, 40);
        textArea.setPreferredSize(new Dimension(1600, 600));
        textArea.setBackground(new Color(242, 255, 112, 64));
        textArea.setForeground(Color.BLUE);
        textArea.setFont(UniqueFont.SALSA.createUniqueFont(FontType.PLAIN, 12));
        /*textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.revalidate();
        textArea.repaint();*/
        JScrollPane textScrollPane = new JScrollPane(textArea);
        JScrollPane imageScrollPane = new JScrollPane(imagePanel); // JScrollPane a képeknek

        // Tesztjelentés beolvasása a fájlból
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\imagereport.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        showImage();

        //textScrollPane.setViewportView(textPanel);
        //imageScrollPane.setViewportView(imagePanel);

        // BorderLayout használata a layout-hoz
        setLayout(new BorderLayout());

        // A JScrollPane-eket a JFrame-hez adjuk hozzá
        add(textScrollPane, BorderLayout.NORTH);
        add(imageScrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Ablak középre helyezése
        setVisible(true);
    }



    public void writeImageDialog(){

        CompareImage compareImage = new CompareImage();

        try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\imagereport.txt")) {
            writer.write(compareImage.compareImages());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO SAMEIMAGE.PROPERTIES
    private void showImage() {

        //decodeAndSaveImages2("images_copy.properties", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_same_pics");

        try {
            File path = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_same_pics\\qa2_pl");
            File[] allFiles = path.listFiles();

            JLabel[] label = new JLabel[allFiles.length];

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
            // Kép beállítása a JLabel-ben
            imagePanel.setBackground(Color.WHITE);

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