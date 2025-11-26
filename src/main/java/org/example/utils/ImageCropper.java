package org.example.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropper {

    /*public ImageCropper(File originalImageFile, int x, int y, int width, int height, File croppedImageFile) throws IOException {

        BufferedImage originalImage = ImageIO.read(originalImageFile);

        BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);

        ImageIO.write(croppedImage, "jpg", croppedImageFile);

        System.out.println("Kivágott kép mentve: " + croppedImageFile.getAbsolutePath());

    }*/

    public static void cropImage(File originalImageFile, int x, int y, int width, int height, File croppedImageFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(originalImageFile);

        // Ellenőrizzük, hogy a kivágás nem lépi-e túl a képet
        if (x < 0 || y < 0 || x + width > originalImage.getWidth() || y + height > originalImage.getHeight()) {
            throw new IllegalArgumentException("The cropping area is outside the image bounds.");
        }

        // Kép kivágása a megadott koordináták és méretek alapján
        BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);

        // A kivágott kép mentése új fájlba
        ImageIO.write(croppedImage, "jpg", croppedImageFile);

        System.out.println("Kivágott kép mentve: " + croppedImageFile.getAbsolutePath());
    }



}
