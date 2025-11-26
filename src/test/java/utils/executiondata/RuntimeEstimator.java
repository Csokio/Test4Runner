package utils.executiondata;

import org.openqa.selenium.NoSuchElementException;
import runner.TestRunner4;

import java.io.IOException;
import java.util.*;
import java.io.*;

public class RuntimeEstimator {

    public List<TestExecutionData> readTestExecutionData(String filePath) throws IOException {
        List<TestExecutionData> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String testName = parts[0];
                    long executionTime = Long.parseLong(parts[1]);
                    String status = parts[2];
                    //int totalRuntime = Integer.parseInt(parts[3]);
                    //data.add(new TestExecutionData(testName, executionTime, status));

                    TestExecutionData testExecutionData = new TestExecutionData(testName, executionTime, status);
                    testExecutionData.setTotalRuntime(TestRunner4.totalRuntime);
                    data.add(testExecutionData);
                }
            }
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return data;
    }

    public long estimateAverageRuntime(List<TestExecutionData> dataList) {
        Map<String, List<TestExecutionData>> groupedByTestName = new HashMap<>();
        for (TestExecutionData data : dataList) {
            groupedByTestName
                    .computeIfAbsent(data.getTestName(), k -> new ArrayList<>())
                    .add(data);
        }

        long totalExecutionTime = 0;
        int totalTests = 0;

        for (Map.Entry<String, List<TestExecutionData>> entry : groupedByTestName.entrySet()) {
            List<TestExecutionData> testExecutions = entry.getValue();
            long averageTime = (long) testExecutions.stream()
                    .mapToLong(TestExecutionData::getExecutionTime)
                    .average()
                    .orElse(0);

            totalExecutionTime += averageTime;
            totalTests++;
        }

        return totalTests > 0 ? totalExecutionTime / totalTests : 0;
    }

    public long estimateTotalRuntime(List<TestExecutionData> dataList) {
        Map<String, List<TestExecutionData>> groupedByTestName = new HashMap<>();
        for (TestExecutionData data : dataList) {
            groupedByTestName
                    .computeIfAbsent(data.getTestName(), k -> new ArrayList<>())
                    .add(data);
        }

        long totalExecutionTime = 0;

        for (Map.Entry<String, List<TestExecutionData>> entry : groupedByTestName.entrySet()) {
            List<TestExecutionData> testExecutions = entry.getValue();
            long executionTime = (long) testExecutions.stream()
                    .mapToLong(TestExecutionData::getExecutionTime).sum();

            totalExecutionTime += executionTime;
        }

        return totalExecutionTime;
    }

    public static void run() throws IOException {
        RuntimeEstimator estimator = new RuntimeEstimator();
        List<TestExecutionData> data = estimator.readTestExecutionData("execution_data.txt");
        long estimatedRuntime = estimator.estimateAverageRuntime(data);
        long estimatedTotalRunTime = estimator.estimateTotalRuntime(data);
        System.out.println("\u001B[0;102;41mEstimated Average Runtime of Test Cases: " + estimatedRuntime + " ms\u001B[0m");
        System.out.println("\u001B[0;103;42mEstimated Total Runtime of Test Cases: " + estimatedTotalRunTime + " ms\u001B[0m");
        System.out.println("\u001B[0;104;43mEstimated WAT Runtime: " + getRuntimeOfWat(data) + " s\u001B[0m");
    }

    public int castLongToInt(long longNumber){
        if (longNumber >= Integer.MIN_VALUE && longNumber <= Integer.MAX_VALUE) {
            int intNumber = (int) longNumber / 1000;
            System.out.println("Converted int value: " + intNumber);
            return intNumber;
        } else {
            System.out.println("The long number cannot be safely cast to int due to out of range.");
            return 0;
        }
    }

    public static int getRuntimeOfWat(List<TestExecutionData> data){
        return data.getFirst().getTotalRuntime();
    }
}
