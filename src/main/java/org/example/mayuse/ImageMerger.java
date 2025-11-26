package org.example.mayuse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageMerger {

    private String[] imagePaths;
    private String savePath;
    private String fileName;
    public ImageMerger(String[] imagePaths, String savePath, String fileName){
        this.imagePaths = imagePaths;
        this.savePath = savePath;
        this.fileName = fileName;
    }

    public void mergeImages() throws IOException {

        BufferedImage[] images = new BufferedImage[imagePaths.length];
        int totalWidth = 0;
        int maxHeight = 0;

        Arrays.stream(imagePaths).forEach(System.out::println);

        for (int i = 0; i < imagePaths.length; i++) {
            images[i] = ImageIO.read(new File(imagePaths[i] + ".png"));
            totalWidth += images[i].getWidth();
            maxHeight = Math.max(maxHeight, images[i].getHeight());
        }

        // Create a new image with the combined size (horizontal merge)
        BufferedImage combined = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();

        // Draw each image into the combined image
        int currentX = 0;
        for (BufferedImage image : images) {
            g.drawImage(image, currentX, 0, null);
            currentX += image.getWidth();
        }
        g.dispose();

        // Save the result
        ImageIO.write(combined, "PNG", new File(savePath + fileName));
        System.out.println("Merged image saved as " + fileName +", to path: " +
                (savePath.isEmpty() ? "saved to project" : savePath));
    }
}
