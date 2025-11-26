package org.example.mayuse;

import org.example.utils.HighlightImageDiff;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Threshold2 {
    public static void main() {
        try {

            File directoryOriginal = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\beta_test");
            File[] imageFilesOriginal = directoryOriginal.listFiles();
            System.out.println(imageFilesOriginal.length);
            System.out.println(imageFilesOriginal[0]);

            File directoryActual = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\beta_test");
            File[] imageFilesActual = directoryActual.listFiles();
            System.out.println(imageFilesActual.length);
            System.out.println(imageFilesActual[0]);

            // Load the images
            BufferedImage image1 = ImageIO.read(imageFilesActual[0]);
            BufferedImage image2 = ImageIO.read(imageFilesOriginal[0]);

            // Convert the images to grayscale
            BufferedImage grayImage1 = convertToGrayscale(image1);
            BufferedImage grayImage2 = convertToGrayscale(image2);

            // Set the threshold value
            double threshold = 0.5; // You can adjust this value based on your requirement

            // Compare the images using thresholding
            boolean isDifferent = compareWithThreshold(grayImage1, grayImage2, threshold);


            if (isDifferent) {
                System.out.println("Images are different.");
                HighlightImageDiff highlighter = new HighlightImageDiff();
                highlighter.writeImageDIff2(imageFilesOriginal[imageFilesOriginal.length - 1], imageFilesActual[0],
                        "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\output_dif%d.png");
            } else {
                System.out.println("Images are similar.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color color = new Color(image.getRGB(i, j));
                int grayValue = (int) (0.2989 * color.getRed() + 0.5870 * color.getGreen() + 0.1140 * color.getBlue());
                Color grayColor = new Color(grayValue, grayValue, grayValue);
                grayImage.setRGB(i, j, grayColor.getRGB());
            }
        }
        return grayImage;
    }

    public static boolean compareWithThreshold(BufferedImage img1, BufferedImage img2, double threshold) {
        int width = img1.getWidth();
        int height = img1.getHeight();

        int secondWidth = img2.getWidth();
        int secondHeight = img2.getHeight();

        width = Math.min(width, secondWidth);
        height = Math.min(height, secondHeight);

        // Loop through each pixel in the images
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel1 = img1.getRGB(x, y) & 0xFF; // Extract grayscale value from the pixel
                int pixel2 = img2.getRGB(x, y) & 0xFF;

                // Check if the absolute difference between pixel values exceeds the threshold
                if (Math.abs(pixel1 - pixel2) > threshold) {
                    return true; // Images are different
                }
            }
        }
        return false; // Images are similar
    }
}
