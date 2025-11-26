package org.example;

import org.example.menu.WindowMenu;
import org.example.utils.HighlightImageDiff;
import org.example.utils.ImageNameComparator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;


public class ImageDiff {


    public void run() throws Exception {

        System.out.println("Hello and welcome!");

        //float imageComparePercent = compareImage(new File("C:\\Users\\SőregiKrisztián\\Pictures\\headless_screenshot.png"), new File("C:\\Users\\SőregiKrisztián\\Pictures\\headless_screenshot_diff.png"));

        File directoryOriginal = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures");
        File[] imageFilesOriginal = directoryOriginal.listFiles();

        File directoryActual = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime");
        File[] imageFilesActual = directoryActual.listFiles();


        for (int i = 0; i < imageFilesOriginal.length; i++){
            String imageComparePercent = compareImage2(new File(String.valueOf(imageFilesOriginal[i])),
                    new File(String.valueOf(imageFilesActual[i])));
            System.out.println(imageComparePercent);
            if (!imageComparePercent.equals("0")){
                HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
                //BufferedImage image1 = highlightImageDiff.createBufferedImg(imageFilesOriginal[i]);
                //BufferedImage image2 = highlightImageDiff.createBufferedImg(imageFilesActual[i]);
                highlightImageDiff.writeImageDIff(imageFilesOriginal[i], imageFilesActual[i], "selenium_image_differences\\");
            }
        }

        /*double imageDiffPercent = compareImage2(imageFilesOriginal[0], imageFilesActual[2]);
        if(imageDiffPercent != 0){
            HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
            highlightImageDiff.writeImageDIff(imageFilesOriginal[0], imageFilesActual[2]);
        }*/

        //System.out.println(imageDiffPercent);
        WindowMenu windowMenu = new WindowMenu();
        windowMenu.run();
        System.out.println("End of main method");


    }

    public boolean compareImagePixelDifference(String originalRoute, String actualRoute, String fileRoute) throws IOException {
        System.out.println("Hello and welcome!");
        boolean isImageTheSame = true;


        File directoryOriginal = new File("C:\\Users\\SőregiKrisztián\\Pictures\\" + originalRoute);
        File[] imageFilesOriginal = directoryOriginal.listFiles();

        File directoryActual = new File("C:\\Users\\SőregiKrisztián\\Pictures\\" + actualRoute);
        File[] imageFilesActual = directoryActual.listFiles();

        File[] imageFilesActualSorted = Arrays.stream(imageFilesActual).sorted(new ImageNameComparator()).toArray(File[]::new);
        File[] imageFilesOriginalSorted = Arrays.stream(imageFilesOriginal).sorted(new ImageNameComparator()).toArray(File[]::new);

        for (int i = 0; i < imageFilesOriginalSorted.length; i++){
            String imageComparePercent = compareImage2(new File(String.valueOf(imageFilesOriginalSorted[i])),
                    new File(String.valueOf(imageFilesActualSorted[i])));
            if (!imageComparePercent.equals("0")){
                HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
                //BufferedImage image1 = highlightImageDiff.createBufferedImg(imageFilesOriginal[i]);
                //BufferedImage image2 = highlightImageDiff.createBufferedImg(imageFilesActual[i]);
                highlightImageDiff.writeImageDIff(imageFilesOriginalSorted[i], imageFilesActualSorted[i], fileRoute);
                isImageTheSame = false;
            }
        }

        System.out.println("End of main method");
        return isImageTheSame;
    }


    private static float compareImage(File fileA, File fileB) {

        float percentage = 0;
        try {
            // take buffer data from both image files //
            BufferedImage biA = ImageIO.read(fileA);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(fileB);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            int count = 0;
            // compare data-buffer objects //
            if (sizeA == sizeB) {

                for (int i = 0; i < sizeA; i++) {

                    if (dbA.getElem(i) == dbB.getElem(i)) {
                        count = count + 1;
                    }

                }
                percentage = (float) (count * 100) / sizeA;
            } else {
                System.out.println("Both the images are not of same size");
            }

        } catch (Exception e) {
            System.out.println("Failed to compare image files ...");
        }
        return percentage;
    }

    private static String compareImage2(File fileA, File fileB) throws IOException {
        BufferedImage imgA = null;
        BufferedImage imgB = null;

        imgA = ImageIO.read(fileA);
        imgB = ImageIO.read(fileB);

        if (imgA == null) {
            throw new IOException("Could not read image from file: " + fileA.getAbsolutePath());
        }

        int width1 = imgA.getWidth();
        int width2 = imgB.getWidth();
        int height1 = imgA.getHeight();
        int height2 = imgB.getHeight();

        imgB = resizeImage(imgB, width1, height1);


        /*if ((width1 != width2) || (height1 != height2)) {

            System.out.println("Error: Images dimensions"
                    + " mismatch");
            return 0;
        } else {*/

            long difference = 0;

            // treating images likely 2D matrix

            // Outer loop for rows(height)
            for (int y = 0; y < height1; y++) {

                // Inner loop for columns(width)
                for (int x = 0; x < width1; x++) {

                    int rgbA = imgA.getRGB(x, y);
                    int rgbB = imgB.getRGB(x, y);
                    int redA = (rgbA >> 16) & 0xff;
                    int greenA = (rgbA >> 8) & 0xff;
                    int blueA = (rgbA) & 0xff;
                    int redB = (rgbB >> 16) & 0xff;
                    int greenB = (rgbB >> 8) & 0xff;
                    int blueB = (rgbB) & 0xff;

                    difference += Math.abs(redA - redB);
                    difference += Math.abs(greenA - greenB);
                    difference += Math.abs(blueA - blueB);
                }
            }

            // Total number of red pixels = width * height
            // Total number of blue pixels = width * height
            // Total number of green pixels = width * height
            // So total number of pixels = width * height *
            // 3
            double total_pixels = width1 * height1 * 3;

            // Normalizing the value of different pixels
            // for accuracy

            // Note: Average pixels per color component
            double avg_different_pixels
                    = difference / total_pixels;

            // There are 255 values of pixels in total
            double percentage
                    = (avg_different_pixels / 255) * 1000;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            // Lastly print the difference percentage
            System.out.println(STR."Difference Percentage --> \{decimalFormat.format(percentage)}" + "°%");
            return decimalFormat.format(percentage);

    }
    private static String compareImageResized(File fileA, File fileB) throws IOException {
        BufferedImage imgA = null;
        BufferedImage imgB = null;

        imgA = ImageIO.read(fileA);
        imgB = ImageIO.read(fileB);


        int width1 = imgA.getWidth();
        int width2 = imgB.getWidth();
        int height1 = imgA.getHeight();
        int height2 = imgB.getHeight();

        imgB = resizeImage(imgB, width1, height1);
        /*if ((width1 != width2) || (height1 != height2)) {

            System.out.println("Error: Images dimensions"
                    + " mismatch");
            return 0;
        } else {*/

        long difference = 0;

        // treating images likely 2D matrix

        // Outer loop for rows(height)
        for (int y = 0; y < height1; y++) {

            // Inner loop for columns(width)
            for (int x = 0; x < width1; x++) {

                int rgbA = imgA.getRGB(x, y);
                int rgbB = imgB.getRGB(x, y);
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;

                difference += Math.abs(redA - redB);
                difference += Math.abs(greenA - greenB);
                difference += Math.abs(blueA - blueB);
            }
        }

        // Total number of red pixels = width * height
        // Total number of blue pixels = width * height
        // Total number of green pixels = width * height
        // So total number of pixels = width * height *
        // 3
        double total_pixels = width1 * height1 * 3;

        // Normalizing the value of different pixels
        // for accuracy

        // Note: Average pixels per color component
        double avg_different_pixels
                = difference / total_pixels;

        // There are 255 values of pixels in total
        double percentage
                = (avg_different_pixels / 255) * 1000;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Lastly print the difference percentage
        System.out.println(STR."Difference Percentage --> \{decimalFormat.format(percentage)}" + "°%");
        return decimalFormat.format(percentage);

    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }


    public boolean compareImagePixelDifferenceBoundedRouteO(String originalRoute, String actualRoute, String fileRoute) throws IOException {
        System.out.println("Hello and welcome!");
        boolean isImageTheSame = true;

        String originalAbsolutePath =  "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\uat_gb\\";
        String actualAbsolutePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\uat_gb_overtime\\";

        File imageOriginal = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\uat_gb\\" + originalRoute);
        if(imageOriginal == null){
            System.out.println("IMAGEORIGINAL FILE DOES NOT EXIST");
        }
        File imageActual = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\uat_gb_overtime\\" + actualRoute);
        if(imageActual == null){
            System.out.println("IMAGEACTUAL FILE DOES NOT EXIST");
        }

        System.out.println("THE IMAGE ORIGINAL IS: " + imageOriginal.getAbsolutePath());
        System.out.println("THE IMAGE ACTUAL IS: " + imageActual.getAbsolutePath());


        String imageComparePercent = compareImage2(imageOriginal, imageActual);
        if (!imageComparePercent.equals("0")){
            HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
            //BufferedImage image1 = highlightImageDiff.createBufferedImg(imageFilesOriginal[i]);
            //BufferedImage image2 = highlightImageDiff.createBufferedImg(imageFilesActual[i]);
            highlightImageDiff.writeImageDIff2(imageOriginal, imageActual, fileRoute);
            isImageTheSame = false;
        }

        System.out.println("End of main method");
        return isImageTheSame;
    }

    public boolean compareImagePixelDifferenceBoundedRoute(String originalRoute, String actualRoute, String fileRoute) throws IOException {
        System.out.println("Hello and welcome!");
        boolean isImageTheSame = true;

        String originalAbsolutePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\uat_gb\\";
        String actualAbsolutePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\uat_gb_overtime\\";

        // Construct the file objects
        File imageOriginal = new File(originalAbsolutePath + originalRoute);
        if (!imageOriginal.exists()) {
            System.out.println("IMAGE ORIGINAL FILE DOES NOT EXIST");
            return false;
        }
        if (!imageOriginal.canRead()) {
            System.out.println("Cannot read the original image file.");
            return false;
        }

        File imageActual = new File(actualAbsolutePath + actualRoute);
        if (!imageActual.exists()) {
            System.out.println("IMAGE ACTUAL FILE DOES NOT EXIST");
            return false;
        }
        if (!imageActual.canRead()) {
            System.out.println("Cannot read the actual image file.");
            return false;
        }

        // Read the images
        BufferedImage originalImage = ImageIO.read(imageOriginal);
        if (originalImage == null) {
            System.out.println("Failed to read the original image.");
            return false;
        }

        BufferedImage actualImage = ImageIO.read(imageActual);
        if (actualImage == null) {
            System.out.println("Failed to read the actual image.");
            return false;
        }

        // Check if images have the same dimensions
        if (originalImage.getWidth() != actualImage.getWidth() || originalImage.getHeight() != actualImage.getHeight()) {
            System.out.println("Images have different dimensions.");
            return false;
        }

        // Compare the images and highlight differences
        String imageComparePercent = compareImage2(imageOriginal, imageActual);
        if (!imageComparePercent.equals("0")) {
            HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
            highlightImageDiff.writeImageDIff2(imageOriginal, imageActual, fileRoute);
            isImageTheSame = false;
        }

        System.out.println("End of main method");
        return isImageTheSame;
    }

    public boolean compareImagePixelDifferenceBoundedRouteQA2PL(String originalRoute, String actualRoute, String fileRoute) throws IOException {
        System.out.println("Hello and welcome!");
        boolean isImageTheSame = true;

        String originalAbsolutePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\qa2_pl\\";
        String actualAbsolutePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime\\";

        // Construct the file objects
        File imageOriginal = new File(originalAbsolutePath + originalRoute);
        if (!imageOriginal.exists()) {
            System.out.println("IMAGE ORIGINAL FILE DOES NOT EXIST");
            return false;
        }
        if (!imageOriginal.canRead()) {
            System.out.println("Cannot read the original image file.");
            return false;
        }

        File imageActual = new File(actualAbsolutePath + actualRoute);
        if (!imageActual.exists()) {
            System.out.println("IMAGE ACTUAL FILE DOES NOT EXIST");
            return false;
        }
        if (!imageActual.canRead()) {
            System.out.println("Cannot read the actual image file.");
            return false;
        }

        // Read the images
        BufferedImage originalImage = ImageIO.read(imageOriginal);
        if (originalImage == null) {
            System.out.println("Failed to read the original image.");
            return false;
        }

        BufferedImage actualImage = ImageIO.read(imageActual);
        if (actualImage == null) {
            System.out.println("Failed to read the actual image.");
            return false;
        }

        // Check if images have the same dimensions
        if (originalImage.getWidth() != actualImage.getWidth() || originalImage.getHeight() != actualImage.getHeight()) {
            System.out.println("Images have different dimensions.");
            return false;
        }

        // Compare the images and highlight differences
        String imageComparePercent = compareImage2(imageOriginal, imageActual);
        if (!imageComparePercent.equals("0")) {
            HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
            highlightImageDiff.writeImageDIff2(imageOriginal, imageActual, fileRoute);
            isImageTheSame = false;
        }

        System.out.println("End of main method");
        return isImageTheSame;
    }

    public boolean compareImagePixelDifferenceBoundedRouteQA2HU(String originalRoute, String actualRoute, String fileRoute) throws IOException {
        System.out.println("Hello and welcome!");
        boolean isImageTheSame = true;

        String originalAbsolutePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\qa2_hu\\";
        String actualAbsolutePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_hu_overtime\\";

        // Construct the file objects
        File imageOriginal = new File(originalAbsolutePath + originalRoute);
        if (!imageOriginal.exists()) {
            System.out.println("IMAGE ORIGINAL FILE DOES NOT EXIST");
            return false;
        }
        if (!imageOriginal.canRead()) {
            System.out.println("Cannot read the original image file.");
            return false;
        }

        File imageActual = new File(actualAbsolutePath + actualRoute);
        if (!imageActual.exists()) {
            System.out.println("IMAGE ACTUAL FILE DOES NOT EXIST");
            return false;
        }
        if (!imageActual.canRead()) {
            System.out.println("Cannot read the actual image file.");
            return false;
        }

        // Read the images
        BufferedImage originalImage = ImageIO.read(imageOriginal);
        if (originalImage == null) {
            System.out.println("Failed to read the original image.");
            return false;
        }

        BufferedImage actualImage = ImageIO.read(imageActual);
        if (actualImage == null) {
            System.out.println("Failed to read the actual image.");
            return false;
        }

        // Check if images have the same dimensions
        /*if (originalImage.getWidth() != actualImage.getWidth() || originalImage.getHeight() != actualImage.getHeight()) {
            System.out.println("Images have different dimensions.");
            return false;
        }*/

        // Compare the images and highlight differences
        String imageComparePercent = compareImage2(imageOriginal, imageActual);
        if (!imageComparePercent.equals("0")) {
            HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
            highlightImageDiff.writeImageDIff2(imageOriginal, imageActual, fileRoute);
            isImageTheSame = false;
        }

        System.out.println("End of main method");
        return isImageTheSame;
    }

}