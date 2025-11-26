package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FailedTestManager {

    private List<String> failedTestcases = new ArrayList<>();

    public FailedTestManager(){
        failedTestcases = readFailedTest();
        failedTestcases = deleteDuplicateElements(failedTestcases);
    }

    public List<String> getFailedTestcases(){
        return this.failedTestcases;
    }

    public List<String> readFailedTest(){

        List<String> counterList = new LinkedList<>();

        try {
            File file = new File("failedtests.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                counterList.addLast(scanner.nextLine().split("\\(")[0]);
            }
        }   catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return counterList;
    }

    public List<String> readFailedTest(String filePath){

        List<String> counterList = new LinkedList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                counterList.addLast(scanner.nextLine().split("\\(")[0]);
            }
        }   catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return counterList;
    }
    private List<String> deleteDuplicateElements(List<String> originalList){
        List<String> newList = new ArrayList<>();
        for(int i = 0; i < originalList.size(); i++){
            if(!newList.contains(originalList.get(i))){
                newList.add(originalList.get(i));
            }
        }
        return newList;
    }

}
