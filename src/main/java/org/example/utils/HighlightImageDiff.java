package org.example.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class HighlightImageDiff {

    private static Integer counterOutput;

    public void writeImageDIff(File fileOriginal, File fileActual, String fileRoute) throws IOException {

        counterOutput = readFile("counter_output.txt");
        ImageIO.write(getDifferenceImage(
                        ImageIO.read(new File(String.valueOf(fileOriginal))),
                        ImageIO.read(new File(String.valueOf(fileActual)))),
                "png",
                new File(String.format("C:\\Users\\SőregiKrisztián\\Pictures\\" + fileRoute , counterOutput)));
        counterOutput = counterOutput + 1;
        writeFile("counter_output.txt");

    }

    public BufferedImage createBufferedImg(File file){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(String.valueOf(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    //STRICT DIFF1
    /*public BufferedImage getDifferenceImage(BufferedImage img1, BufferedImage img2) {
        // convert images to pixel arrays...
        final int w = img1.getWidth(),
                h = img1.getHeight(),
                highlight = Color.MAGENTA.getRGB();
        final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
        final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);
        // compare img1 to img2, pixel by pixel. If different, highlight img1's pixel...
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] != p2[i]) {
                p1[i] = highlight;
            }
        }
        // save img1's pixels to a new BufferedImage, and return it...
        // (May require TYPE_INT_ARGB)
        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        out.setRGB(0, 0, w, h, p1, 0, w);
        return out;
    }*/

    //STRICT DIFF2
    public BufferedImage getDifferenceImage(BufferedImage img1, BufferedImage img2) {
        final int w = Math.min(img1.getWidth(), img2.getWidth());
        final int h = Math.min(img1.getHeight(), img2.getHeight());
        final int highlight = Color.MAGENTA.getRGB();

        final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
        final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            System.out.println("Warning: Images have different dimensions! Cropping to common area.");
        }

        // compare pixel arrays
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] != p2[i]) {
                p1[i] = highlight;
            }
        }

        // Create the output image using the comparison dimensions
        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        out.setRGB(0, 0, w, h, p1, 0, w);
        return out;
    }

    //TOLERATED IMAGE DIFF
    /*public BufferedImage getDifferenceImage(BufferedImage img1, BufferedImage img2) {
        // Define image dimensions
        final int w = img1.getWidth(), h = img1.getHeight();
        final int highlight = Color.MAGENTA.getRGB();

        // Define the tolerance for color difference (0-255)
        final int tolerance = 0; // A tolerance value, you can tweak this

        // Convert images to pixel arrays
        final int[] p1 = img1.getRGB(0, 0, w, h, null, 0, w);
        final int[] p2 = img2.getRGB(0, 0, w, h, null, 0, w);

        // Compare img1 to img2, pixel by pixel. If different, highlight img1's pixel...
        for (int i = 0; i < p1.length; i++) {
            int rgb1 = p1[i];
            int rgb2 = p2[i];

            int red1 = (rgb1 >> 16) & 0xFF;
            int green1 = (rgb1 >> 8) & 0xFF;
            int blue1 = (rgb1) & 0xFF;

            int red2 = (rgb2 >> 16) & 0xFF;
            int green2 = (rgb2 >> 8) & 0xFF;
            int blue2 = (rgb2) & 0xFF;

            // Calculate the color difference for each component (Red, Green, Blue)
            int redDiff = Math.abs(red1 - red2);
            int greenDiff = Math.abs(green1 - green2);
            int blueDiff = Math.abs(blue1 - blue2);

            // If the color difference exceeds the tolerance, mark the pixel as different
            if (redDiff > tolerance || greenDiff > tolerance || blueDiff > tolerance) {
                p1[i] = highlight; // Mark the pixel as different with magenta
            }
        }

        // Save img1's pixels to a new BufferedImage, and return it...
        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        out.setRGB(0, 0, w, h, p1, 0, w);
        return out;
    }*/

    public static int readFile(String fileName){

        List<Integer> counterList = new LinkedList<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextInt()){
                counterList.addLast(scanner.nextInt());
            }
        }   catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return counterList.getLast();
    }

    private void writeFile(String fileName){

        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file, true);
            System.out.println(counterOutput);
            fileWriter.write(String.valueOf(counterOutput) + '\n');
            fileWriter.close();
            System.out.println("File is written");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeImageDIff2(File fileOriginal, File fileActual, String fileRoute) throws IOException {

        //counterOutput = readFile("counter_output.txt");
        BufferedImage diffImage = getDifferenceImage(
                ImageIO.read(fileOriginal),
                ImageIO.read(fileActual)
        );

        ImageIO.write(diffImage, "png", new File(fileRoute));

    }


}
