package utils;

import tests.WebTestTH;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Utils {


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

    public static void writeFile(String fileName){

        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file, true);
            System.out.println(WebTestTH.getCounter());
            fileWriter.write(String.valueOf(WebTestTH.getCounter()) + '\n');
            fileWriter.close();
            System.out.println("File is written");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<String> readImageNamesFromFolder(String folderPath){

        List<String> imageNameList = new ArrayList<>();

        try {
            File folder = new File(folderPath);
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        imageNameList.add(file.getName());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return imageNameList;
    }

    public static void deleteImagesInFolder(String folderPath){
        try {
            File folder = new File(folderPath);
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            System.out.println(file.getName() + " sikeresen törölve.");
                        } else {
                            System.out.println("Nem sikerült törölni: " + file.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
