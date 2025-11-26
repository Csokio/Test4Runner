package org.example.mayuse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DetectMagentaColor {

    private File[] allImages;

    public DetectMagentaColor(File file){
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        allImages = file.listFiles();
        System.out.println(allImages.length);
        Arrays.stream(allImages).forEach(i -> {
           try {
               BufferedImage bufferedImage = ImageIO.read(i);
               if(!containsMagenta(bufferedImage)){
                   if(i.delete()){
                       System.out.println("The " + i.getName() + " file doesn't contain magenta color. File is deleted.");
                   } else {
                       System.out.println(i.getName()  + " file doesn't contain magenta color, can't be deleted.");
                   }
               }

           } catch (IOException e) {
               e.printStackTrace();
           }
        });
    }

    private boolean containsMagenta(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);

                if (color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 255) {
                    return true;
                }
            }
        }
        return false;
    }


}
