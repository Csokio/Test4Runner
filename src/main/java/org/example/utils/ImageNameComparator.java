package org.example.utils;

import java.io.File;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageNameComparator implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {

        return Integer.compare(extractNumber(o1.getName()), extractNumber(o2.getName()));
    }

    private int extractNumber(String fileName) {
        /*String numberString = fileName.replaceAll("[^\\d]+$", "");

        return Integer.parseInt(numberString);*/

        Matcher matcher = Pattern.compile("\\d+").matcher(fileName);


        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            return 0;
        }
    }

}
