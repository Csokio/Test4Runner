import org.example.menu.WindowMenu;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.UATShellGB;
import tests.WebTestTH;
import utils.CircleDiagram;


import javax.swing.*;
import java.io.*;
import java.util.UUID;

public class TestRunner {

    //TODO Show a dialog asking the user to type in a String: a country code, which determines of which country's test cases we run
    //TODO String inputValue = JOptionPane.showInputDialog("Please input a value");


    public static Integer passedTests;
    public static Integer failedTests;

    private static Result result;

    public static void main(String[] args) throws Exception {

        String[] choices = { "GB", "TH", "Far Far Away"};

        JDialog.setDefaultLookAndFeelDecorated(true);
        String selectedCountry = (String) JOptionPane.showInputDialog(null, "Please select a country:",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                choices, // Array of choices
                choices[0]); // Initial choice


        long startTime = System.currentTimeMillis();
        if(selectedCountry.equals("GB")){
            result = JUnitCore.runClasses(/*tests.WebTestTH.class, */UATShellGB.class);
        }   else {
            result = JUnitCore.runClasses(WebTestTH.class/*, UATShellGB.class*/);

        }
        long endTime = System.currentTimeMillis();
        try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.txt")) {
            writer.write("Tesztek eredményei:\n");
            writer.write("Sikeres tesztek: " + (result.getRunCount() - result.getFailureCount()) + "\n");
            writer.write("Sikertelen tesztek: " + result.getFailureCount() + "\n\n");
            writer.write("Tesztfutatás hossza: " + (endTime-startTime) / 1000 + " sec");


            if (!result.wasSuccessful()) {
                for (Failure failure : result.getFailures()) {
                    writer.write("Sikertelen teszt: " + failure.getDescription().getDisplayName() + "\n\n");
                    //writer.write("Hiba üzenet: " + failure.getMessage() + "\n");
                    //writer.write("Hiba stack trace: " + failure.getTrace() + "\n\n");
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



        /*ImageDiff imageDiff = new ImageDiff();
        imageDiff.run();*/
        WindowMenu windowMenu = new WindowMenu();
        windowMenu.showAlertMessage(failedTests);

        windowMenu.run();
        CircleDiagram circleDiagram = new CircleDiagram(passedTests, failedTests, windowMenu.getMenuBarWidth());

        //TODO read size of directories comparing results of test runner, barchart implementation

    }

    private static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    private static void writeToFile(String s, String message) throws IOException {
        try(FileWriter fileWriter = new FileWriter(s)){
            fileWriter.write(message);
        }

    }


}