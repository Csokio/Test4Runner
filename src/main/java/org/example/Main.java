package org.example;

import org.example.animations.CustomIcon;
import org.example.enums.FontType;
import org.example.enums.UniqueFont;
import org.example.mayuse.DetectMagentaColor;
import org.example.menudialogs.CompareImage;
import org.example.menudialogs.CompareImageDialog;
import org.example.menudialogs.CompareImageDialog2;
import org.example.utils.HighlightImageDiff;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws IOException, FontFormatException {

        System.out.println("Hello world!");

        /*CompareImageDialog2 compareImageDialog2 = new CompareImageDialog2();
        compareImageDialog2.writeImageDialog();*/

        SwingUtilities.invokeLater(() -> {
            try {
                CompareImageDialog2 compareImageDialog2 = new CompareImageDialog2();
                compareImageDialog2.writeImageDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        File directoryOriginal = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures");
        File[] imageFilesOriginal = directoryOriginal.listFiles();
        /*for (File file : imageFilesOriginal) {
            System.out.println(file.getName());
        }*/

        File directoryActual = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime");
        File[] imageFilesActual = directoryActual.listFiles();

        HighlightImageDiff highlightImageDiff = new HighlightImageDiff();
        assert imageFilesOriginal != null;
        assert imageFilesActual != null;

        Font font = UniqueFont.LILITA_ONE.createUniqueFont(FontType.BOLD, 14);
        //highlightImageDiff.writeImageDIff(imageFilesOriginal[0], imageFilesOriginal[1]);

        int minIteration = Math.min(imageFilesOriginal.length, imageFilesActual.length);
        IntStream.range(0, minIteration).forEach(i -> {
            try {
                highlightImageDiff.writeImageDIff(imageFilesOriginal[i], imageFilesActual[i], "selenium_image_differences\\output_diff%d.png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        new DetectMagentaColor(new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences"));

    }
}