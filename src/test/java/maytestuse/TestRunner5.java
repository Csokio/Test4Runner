package maytestuse;


import tests.WebTestTH;
import utils.dnd.ChooseDropActionDemo2;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.UATShellGB;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.example.menu.WindowMenu;
import utils.CircleDiagram;
import utils.executiondata.RuntimeEstimator;
import utils.executiondata.TestExecutionData;

//TODO SPLIT TEST CLASS INTO REQUESTS
public class TestRunner5 {
    private static Integer passedTests = 0;
    private static Integer failedTests = 0;

    public static Integer totalRuntime = 0;
    public static List<TestExecutionData> testExecutionDataList = new ArrayList<>();
    private static Set<String> addedModel = new HashSet<>();


    public static void main(String[] args) throws IOException, FontFormatException {
        String[] choices = {"GB", "TH", "Far Far Away"};

        JDialog.setDefaultLookAndFeelDecorated(true);
        String selectedCountry = (String) JOptionPane.showInputDialog(null, "Please select a country:",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[0]);

        ChooseDropActionDemo2 chooseDropActionDemo2 = new ChooseDropActionDemo2(addedModel);

        SwingUtilities.invokeLater(() -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            chooseDropActionDemo2.setVisible(true);
        });

        // Use a SwingWorker to handle background processing
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            private Result result;

            @Override
            protected Void doInBackground() throws Exception {

                result = new Result();

                // Wait for ChooseDropActionDemo2 window to close
                while (!chooseDropActionDemo2.isClosed()) {
                    Thread.sleep(1000); // Wait 1 second
                }

                // Now get the moved items after the window is closed
                String[] testMethodsToRun = chooseDropActionDemo2.getMovedItems();

                System.out.println(Arrays.toString(testMethodsToRun));

                long startTime = System.currentTimeMillis();
                JUnitCore junit = new JUnitCore();

                Class testClass = UATShellGB.class;

                if (selectedCountry.equals("GB")) {
                    for (String methodToRun : testMethodsToRun) {
                        Method[] methods = testClass.getDeclaredMethods();
                        for (Method method : methods) {
                            if ((method.toString()).contains(methodToRun)) {
                                System.out.println(method);
                                System.out.println("Method get name: " + method.getName());
                                if (method.isAnnotationPresent(org.junit.Test.class)) {
                                    Request request = Request.method(testClass, method.getName());
                                    Result methodResult = junit.run(request);

                                    System.out.println(methodResult.getRunCount());
                                    System.out.println(methodResult.getRunTime());

                                    result = mergeResults(result, methodResult);
                                    System.out.println(result.getRunCount());
                                    System.out.println(result.getRunTime());

                                    manageRunCount(methodResult);

                                    /*testExecutionDataList.add(new TestExecutionData(
                                            method.getName(),
                                            result.getRunTime(), // Set actual execution time if available
                                            methodResult.wasSuccessful() ? "PASSED" : "FAILED"
                                    ));*/
                                }
                            }
                        }
                    }
                } else {
                    result = JUnitCore.runClasses(WebTestTH.class);
                    passedTests = result.getRunCount() - result.getFailureCount();
                    failedTests = result.getFailureCount();
                }
                long endTime = System.currentTimeMillis();
                totalRuntime = Math.toIntExact((endTime - startTime) / 1000);

                try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.txt")) {
                    writer.write("Tesztek eredményei:\n");
                    writer.write("Sikeres tesztek: " + passedTests + "\n");
                    writer.write("Sikertelen tesztek: " + failedTests + "\n\n");
                    writer.write("Tesztfutatás hossza: " + totalRuntime + " sec" + "\n\n");

                    if (!result.wasSuccessful()) {
                        for (Failure failure : result.getFailures()) {
                            writer.write("Sikertelen teszt: " + failure.getDescription().getDisplayName() + "\n\n");

                            /*testExecutionDataList.add(new TestExecutionData(
                                    failure.getDescription().getDisplayName(),
                                    0, // Set actual execution time
                                    "FAILED"
                            ));*/
                        }
                    }
                    /*for (String testName : getPassedTests(result)) {
                        testExecutionDataList.add(new TestExecutionData(
                                testName,
                                0, // Set actual execution time
                                "PASSED"
                        ));
                    }*/
                    try {
                        writeTestExecutionDataToFile(testExecutionDataList, "execution_data.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.html")) {
                    writer.write("<!DOCTYPE html>\n");
                    writer.write("<html>\n");
                    writer.write("<head>\n");
                    writer.write("<title>Tesztreport</title>\n");
                    writer.write("</head>\n");
                    writer.write("<body>\n");

                    writer.write("<h1>Tesztek eredményei:</h1>\n");
                    writer.write("<p>Sikeres tesztek: " + passedTests + "</p>\n");
                    writer.write("<p>Sikertelen tesztek: " + failedTests + "</p>\n");

                    if (!result.wasSuccessful()) {
                        for (Failure failure : result.getFailures()) {
                            String messageId = generateUniqueId();
                            String traceId = generateUniqueId();

                            writeToFile("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\" + messageId + ".txt", failure.getMessage());
                            writeToFile("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\" + traceId + ".txt", failure.getTrace());

                            writer.write("<h2 class='red-text'>Sikertelen teszt: " + failure.getDescription().getDisplayName() + "</h2>\n");
                            writer.write("<p>Hiba üzenet: <a href=\"" + messageId + ".txt\" target=\"_blank\">Megtekintés</a></p>\n");
                            writer.write("<p>Hiba stack trace: <a href=\"" + traceId + ".txt\" target=\"_blank\">Megtekintés</a></p>\n\n");
                        }
                    }

                    writer.write("</body>\n");
                    writer.write("</html>\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*passedTests = result.getRunCount() - result.getFailureCount();
                failedTests = result.getFailureCount();*/

                return null;
            }

            @Override
            protected void done() {
                // Update UI or perform any necessary post-processing
                try {
                    RuntimeEstimator runtimeEstimator = new RuntimeEstimator();
                    runtimeEstimator.run();
                    get(); // Ensure background task completed successfully
                    WindowMenu windowMenu = new WindowMenu();
                    windowMenu.showAlertMessage(failedTests);
                    windowMenu.run();
                    CircleDiagram circleDiagram = new CircleDiagram(passedTests, failedTests, windowMenu.getMenuBarWidth());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException | FontFormatException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        worker.execute(); // Start the SwingWorker
    }

    private static Result mergeResults(Result result1, Result result2) {
        // Merge two JUnit results
        for (Failure failure : result2.getFailures()) {
            result1.getFailures().add(failure);
        }

        return result1;
    }

    private static void manageRunCount(Result methodResult){
        if(methodResult.getFailureCount() == 1){
            failedTests++;
        }   else  {
            passedTests++;
        }
    }

    private static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private static void writeToFile(String path, String message) throws IOException {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(message);
        }
    }
    private static List<String> getPassedTests(Result result) {
        List<String> passedTests = new ArrayList<>();
        result.getFailures().forEach(failure -> {
            String testName = failure.getDescription().getDisplayName();
            if (!passedTests.contains(testName)) {
                passedTests.add(testName);
            }
        });
        return passedTests;
    }

    private static void writeTestExecutionDataToFile(List<TestExecutionData> data, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (TestExecutionData testData : data) {
                testData.setTotalRuntime(totalRuntime);
                writer.write(testData.getTestName() + "," + testData.getExecutionTime() + "," + testData.getStatus() + "," + testData.getTotalRuntime() + "\n");
            }
        }
    }

}
