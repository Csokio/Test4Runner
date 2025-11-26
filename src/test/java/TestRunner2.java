import maytestuse.ChooseDropActionDemo3;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.UATShellGB;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.example.menu.WindowMenu;
import tests.WebTestTH;
import utils.CircleDiagram;

//TODO TEST RUNNER2 IMPLEMENTS CHOOSE DROP ACTION2
public class TestRunner2 {

    private static Integer passedTests;
    private static Integer failedTests;

    private static Result result;

    public static void main(String[] args) throws IOException {
        String[] choices = {"GB", "TH", "Far Far Away"};

        JDialog.setDefaultLookAndFeelDecorated(true);
        String selectedCountry = (String) JOptionPane.showInputDialog(null, "Please select a country:",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[0]);

        ChooseDropActionDemo3 chooseDropActionDemo3 = new ChooseDropActionDemo3();

        SwingUtilities.invokeLater(() -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            chooseDropActionDemo3.setVisible(true);
        });

        // Use a SwingWorker to handle background processing
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Wait for ChooseDropActionDemo2 window to close
                while (!chooseDropActionDemo3.isClosed()) {
                    Thread.sleep(1000); // Wait 1 second
                }

                // Now get the moved items after the window is closed
                String[] testCasesToRun = chooseDropActionDemo3.getMovedItems();

                System.out.println(Arrays.toString(testCasesToRun));

                long startTime = System.currentTimeMillis();
                if (selectedCountry.equals("GB")) {
                    result = JUnitCore.runClasses(UATShellGB.class);
                } else {
                    result = JUnitCore.runClasses(WebTestTH.class);
                }
                long endTime = System.currentTimeMillis();

                try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.txt")) {
                    writer.write("Tesztek eredményei:\n");
                    writer.write("Sikeres tesztek: " + (result.getRunCount() - result.getFailureCount()) + "\n");
                    writer.write("Sikertelen tesztek: " + result.getFailureCount() + "\n\n");
                    writer.write("Tesztfutatás hossza: " + (endTime - startTime) / 1000 + " sec" + "\n\n");

                    if (!result.wasSuccessful()) {
                        for (Failure failure : result.getFailures()) {
                            writer.write("Sikertelen teszt: " + failure.getDescription().getDisplayName() + "\n\n");
                        }
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
                    writer.write("<p>Sikeres tesztek: " + (result.getRunCount() - result.getFailureCount()) + "</p>\n");
                    writer.write("<p>Sikertelen tesztek: " + result.getFailureCount() + "</p>\n");

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

                passedTests = result.getRunCount() - result.getFailureCount();
                failedTests = result.getFailureCount();

                return null;
            }

            @Override
            protected void done() {
                // Update UI or perform any necessary post-processing
                try {
                    get(); // Ensure background task completed successfully
                    WindowMenu windowMenu = new WindowMenu();
                    windowMenu.showAlertMessage(failedTests);
                    windowMenu.run();
                    CircleDiagram circleDiagram = new CircleDiagram(passedTests, failedTests, windowMenu.getMenuBarWidth());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException | FontFormatException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        worker.execute(); // Start the SwingWorker
    }

    private static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private static void writeToFile(String s, String message) throws IOException {
        try (FileWriter fileWriter = new FileWriter(s)) {
            fileWriter.write(message);
        }
    }
}