package runner;

import maytestuse.ChooseDropActionDemoFailed;
import org.example.enums.FontType;
import org.example.enums.UniqueFont;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import tests.WebTestTH;
import utils.FailedTestManager;
import utils.buttons.MyConfirmButton;
import utils.buttons.MyCustomButton;
import utils.buttons.MyPrevFailedTestCasesButton;
import utils.dnd.ChooseDropActionDemo2;
import utils.executiondata.TestExecutionData;
import utils.Utils;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tests.UATShellGB;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.RasterFormatException;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.example.menu.WindowMenu;
import utils.CircleDiagram;

import static tests.WebTestTH.driver;

//import static sun.tools.jconsole.inspector.XDataViewer.dispose;

//TODO SPLIT TEST CLASS INTO REQUESTS
//TODO TOGGLE BUTTON FOR CHOOSING IN CASE LOGGED IN TEST CASES MAY USE PARAMETERIZED CLASS FOR TESTING MULTIPLE USER
//TODO IN CASE THERE ISN'T ANY SUCCEED TESTCASE HTML REPOST SHOWS THE SUCCEED TEST CASES OF THE PREVIOUS RUN
//TODO ACTUALLY IT DOESN'T DELETE THE SUCCEEDED FAILED TEST FROM FAILED TESTS.TXT  --- PIPA
//TODO DELETE THE CONTENT OF IMAGE DIFFERENCES IN UAT GB FOLDER AT EACH START OF TESTRUNNER4

//TODO IF ONLY FAILED TEST THE PROGRAM QUITS AT CERTAIN POINT
//TODO TRANSFORM SVG TO PNG AFTER ITS SAVED TO THE FOLDER

//TODO AT COUNTRY SELECTOR IMPLEMENT AN OPTION TO RUN PREVIOUSLY SELECTED TEST CASES
//TODO AT COUNTRY SELECTOR IMPLEMENT AN OPTION TO RUN PREVIOUSLY FAILED TEST CASES   --- DONE  - MAY REWORK GUI OF CHOOSEDROPACTIONDEMOFAILED
//TODO WHEN CLICK ON PREVIOUSLY FAILED TEST CASES OR RUN TEST CASES WITHOUT ANY COUNTRY CHOSEN PROGRAM QUITS EXIT CODE 1
//TODO AT COUNTRY SELECTOR ADAPT MOBILE DESKTOP LOGIN LOGOUT OPTIONS TO COUNTRY
//TODO MAY USE FLAGS AT INSTEAD OF NAMES AT MAIN COUNTRY OPTIONS
//TODO FAILED IMAGES ONLY COPIES IMAGES FROM ONE DIRECTORY INTO THE HTML REPORT, SHOULD UPGRADE
//          THIS ONE SO EVERY SELECTED COUNTRY'S FAILED IMAGES VIEWABLE IN THE HTML REPORT
//TODO IMPLEMENT METHODS WHICH ARE AUTOMATICALY SEARCH FOR PADDINGS VALUES IN THE DOM OBJECT
//TODO ADD FILES TO TEST REPORTS

//TODO REWORKED VALUESLIST METHOD WITH ADDITIONAL XPATH IMPLEMENTED IN QA2PLiN -> testReworkedHeaderValuesQA2PL   ---  DONE
//TODO REWORKED GETPADDING METHOD WITH ADDITIONAL XPATH IMPLEMENTED IN QA2PLiN -> getReworkedHeaderPaddingQA2PL   ---  DONE
//TODO CONFIRM BUTTON SHOULD POPUP A WARNING MESSAGE - TO SELECT A COUNTRY - IN CASE ITS CLICKED WITHOUT ANY SELECTED COUNTRY

//TODO IMAGE COMPARE WITH ANGULAR SITE

public class TestRunner4 {
    private static WindowMenu windowMenu;
    private static Integer passedTests = 0;
    private static Integer failedTests = 0;
    public static Integer totalRuntime = 0;
    public static List<TestExecutionData> testExecutionDataList = new ArrayList<>();

    private static DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
    private static Set<String> addedModel = new HashSet<>();
    private static Set<String> runMethodNames = new HashSet<>();
    private static Set<String> methodsWithFailedImages = new HashSet<>();

    private static String selectedCountry;
    private static boolean isGreen = true;

    private static final Class testClass = UATShellGB.class;
    private static final Class testTHClass = WebTestTH.class;

    private static String[] everyTestMethodsToRun;

    public static Set<String> allMethods = new HashSet<>();
    public static String allMethodString = new String();

    private static Queue<String> failedTestCases = new ArrayDeque<>();
    public static AtomicBoolean prevFailedTestActionInvoked = new AtomicBoolean(false);



    public static void main(String[] args) throws IOException, FontFormatException {
        /*String[] choices = {"GB", "TH", "Far Far Away"};

        //TODO MAY IMPLEMENT COMBOBOX HERE FOR CHOOSING MULTIPLE COUNTRIES
        JDialog.setDefaultLookAndFeelDecorated(true);
        String selectedCountry = (String) JOptionPane.showInputDialog(null, "Please select a country:",
                "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,
                choices, choices[0]);


        ChooseDropActionDemo2 chooseDropActionDemo2 = new ChooseDropActionDemo2();

        SwingUtilities.invokeLater(() -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            chooseDropActionDemo2.setVisible(true);
        });*/

        String[] possibleChoices = {"GB", "TH", "Far Far Away"};


        JDialog.setDefaultLookAndFeelDecorated(true);

        JPanel mainPanel = new JPanel();
        JPanel topPAnel = new JPanel();
        JPanel bottomPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        topPAnel.setLayout(new BoxLayout(topPAnel, BoxLayout.X_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel("Select Test Classes To Run:");
        /*label.setOpaque(true);
        label.setBackground(Color.RED);
        label.setBorder(new BevelBorder(3));*/

        try {

            label.setFont(UniqueFont.MONOFETT.createUniqueFont(FontType.PLAIN, 24));

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        topPAnel.add(label);

        JPanel btnPanel = new JPanel();


        MyCustomButton btnGB = new MyCustomButton("GB");
        MyCustomButton btnTH = new MyCustomButton("TH");
        MyCustomButton btnPLIn = new MyCustomButton("PLIN");
        MyCustomButton btnPLOut = new MyCustomButton("PLOUT");
        MyCustomButton btnHUi = new MyCustomButton("HUi");
        MyCustomButton btnFAR = new MyCustomButton("Far Far Away");


        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JComboBox<String> comboBox = new JComboBox<>(model);
        comboBox.setRenderer(new CustomComboBoxRenderer());

        uniqueCountryAddJButton(btnGB, comboBox);
        uniqueCountryAddJButton(btnTH, comboBox);
        uniqueCountryAddJButton(btnPLIn, comboBox);
        uniqueCountryAddJButton(btnPLOut, comboBox);
        uniqueCountryAddJButton(btnHUi, comboBox);
        uniqueCountryAddJButton(btnFAR, comboBox);


        btnPanel.add(btnGB);
        btnPanel.add(btnTH);
        btnPanel.add(btnPLIn);
        btnPanel.add(btnPLOut);
        btnPanel.add(btnHUi);
        btnPanel.add(btnFAR);

        topPAnel.add(btnPanel);

        JDialog dialog = new JDialog((JFrame) null, "The Choice of a Lifetime", true);

        MyConfirmButton btnConfirm = new MyConfirmButton("Confirm",18, dialog);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 110, 10));

        btnConfirm.setPreferredSize(new Dimension(140,30));
        comboBox.setPreferredSize(new Dimension(140, 30));

        comboBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    final String fontName = comboBox.getSelectedItem().toString();
                    File fontFile = new File("src/main/resources/font/FugazOne-Regular.ttf");

                    try {
                        Font fugazOne = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 14);
                        comboBox.setFont(fugazOne);
                        comboBox.setBackground(new Color(86, 94, 145));
                        comboBox.setForeground(Color.WHITE);
                    } catch (FontFormatException | IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

        bottomPanel.add(btnConfirm);
        bottomPanel.add(comboBox);

        //TODO IMPLEMENT HERE PREVIOUSLY FAILED TEST CASES BUTTON
        MyPrevFailedTestCasesButton btnPrevFailed = new MyPrevFailedTestCasesButton("Failed Test Cases", 14, dialog);
        btnPrevFailed.setPreferredSize(new Dimension(180, 25));
        bottomPanel.add(btnPrevFailed);

        mainPanel.add(topPAnel);
        mainPanel.add(bottomPanel);

        // Create and set up the dialog
        dialog.setLayout(new GridLayout(1,1));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getContentPane().add(mainPanel);
        dialog.pack();
        dialog.setVisible(true);


        while (addedModel.isEmpty()) {
            /*showPopup("Please select at least one country to run", "None of the countries is selected");
            dialog.setVisible(true);*/
            showWarningDialog("Please select at least one country to run");
            dialog.setVisible(true);
        }

        System.out.println(addedModel.stream().iterator().next());


        ChooseDropActionDemo2 chooseDropActionDemo2 = new ChooseDropActionDemo2(addedModel);
        FailedTestManager failedTestManager = new FailedTestManager();
        List<String> prevFailedTestList = failedTestManager.readFailedTest("previously_failed_test_cases.txt");
        IntStream.range(0, prevFailedTestList.size()).forEach(i -> {
            failedTestCases.add(prevFailedTestList.get(i));
        });
        ChooseDropActionDemoFailed chooseDropActionDemoFailed = new ChooseDropActionDemoFailed(failedTestCases);

        /*btnConfirm.addActionListener(e -> {
            System.out.println("Confirm button clicked");
            dialog.dispose(); // Close the current dialog
            SwingUtilities.invokeLater(() -> {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                chooseDropActionDemo2.setVisible(true);
            });
        });*/

        SwingUtilities.invokeLater(() -> {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            /*if(!prevFailedTestActionInvoked.get()){
                chooseDropActionDemo2.setVisible(true);
            } else {
                chooseDropActionDemoFailed.setVisible(true);
            }*/

            if(!prevFailedTestActionInvoked.get()){
                System.out.println("Prev Failed Boolean value is: " + prevFailedTestActionInvoked.get());
                System.out.println("chooseDropActionDemo2.setVisible(true);");
                chooseDropActionDemo2.setVisible(true);
                chooseDropActionDemoFailed.setVisible(false);
            } else {
                System.out.println("Prev Failed Boolean value is: " + prevFailedTestActionInvoked.get());
                System.out.println("chooseDropActionDemoFailed.setVisible(true);");
                chooseDropActionDemoFailed.setVisible(true);
                chooseDropActionDemo2.setVisible(false);
            }

        });


        // Use a SwingWorker to handle background processing
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            private Result result;


            @Override
            protected Void doInBackground() throws Exception {


                result = new Result();

                String[] testMethodsToRun;
                if(!prevFailedTestActionInvoked.get()){
                    while (!chooseDropActionDemo2.isClosed()) {
                        Thread.sleep(1000);
                    }
                     testMethodsToRun = chooseDropActionDemo2.getMovedItems();
                } else {
                    while (!chooseDropActionDemoFailed.isClosed()) {
                        Thread.sleep(1000);
                    }
                    testMethodsToRun = chooseDropActionDemoFailed.getMovedItems();
                }

                System.out.println(Arrays.toString(testMethodsToRun));

                long startTime = System.currentTimeMillis();
                JUnitCore junit = new JUnitCore();

                //TODO DELETE ALWAYS THE RIGHT DIRECTORY
                Utils.deleteImagesInFolder("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb");

                Map<String, Class> stringClassMap;
                if(!prevFailedTestActionInvoked.get()){
                    stringClassMap = chooseDropActionDemo2.getMapCountryClass();
                } else {
                    stringClassMap = chooseDropActionDemoFailed.getMapCountryClass();
                }

                for(Map.Entry<String, Class> entry : stringClassMap.entrySet()) {
                    if (addedModel.contains(entry.getKey())) {
                        for (String methodToRun : testMethodsToRun) {
                            Method[] methods = entry.getValue().getDeclaredMethods();
                            for (Method method : methods) {
                                if ((method.toString()).contains(methodToRun)) {
                                    System.out.println(method);
                                    System.out.println("Method get name: " + method.getName());
                                    allMethods.add(method.getName());
                                    System.out.println("the allmethods are during iteration: " + allMethods);
                                    allMethodString = allMethodString + method.getName();
                                    System.out.println(allMethodString);
                                    if (method.isAnnotationPresent(org.junit.Test.class)) {
                                        System.out.println("Annotation is present");
                                        Request request = Request.method(entry.getValue(), method.getName());
                                        System.out.println(entry.getValue());
                                        System.out.println(method.getName());

                                        runMethodNames.add(method.getName());

                                        Result methodResult; //= junit.run(request);

                                        try {
                                            methodResult = junit.run(request);
                                            System.out.println(methodResult.getRunCount());
                                            System.out.println(methodResult.getRunTime());

                                            result = mergeResults(result, methodResult);
                                            System.out.println(result.getRunCount());
                                            System.out.println(result.getRunTime());

                                            manageRunCount(methodResult);
                                        } catch (Exception e) {
                                            System.err.println("Hiba a Request.method vagy junit.run során: " + method.getName());
                                            e.printStackTrace();
                                        }

                                        /*System.out.println(methodResult.getRunCount());
                                        System.out.println(methodResult.getRunTime());

                                        result = mergeResults(result, methodResult);
                                        System.out.println(result.getRunCount());
                                        System.out.println(result.getRunTime());

                                        manageRunCount(methodResult);*/
                                    }
                                }
                            }
                        }
                    }
                }


                System.out.println("----------------Debugging allmethdods: ------------------------");
                Stream.of(allMethods).forEach(System.out::println);


                /*if (addedModel.contains("GB")) {
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

                                /*}
                            }
                        }
                    }
                }
                if (addedModel.contains("TH")){
                    for (String methodToRun : testMethodsToRun) {
                        Method[] methods = testTHClass.getDeclaredMethods();

                        for (Method method : methods) {
                            if ((method.toString()).contains(methodToRun)) {
                                System.out.println(method);
                                System.out.println("Method get name: " + method.getName());
                                if (method.isAnnotationPresent(org.junit.Test.class)) {
                                    Request request = Request.method(testTHClass, method.getName());

                                    Result methodResult = junit.run(request);

                                    System.out.println(methodResult.getRunCount());
                                    System.out.println(methodResult.getRunTime());

                                    result = mergeResults(result, methodResult);
                                    System.out.println(result.getRunCount());
                                    System.out.println(result.getRunTime());

                                    manageRunCount(methodResult);
                                }
                            }
                        }
                    }
                }*/
                /*else {
                    result = JUnitCore.runClasses(tests.WebTestTH.class);
                    passedTests = result.getRunCount() - result.getFailureCount();
                    failedTests = result.getFailureCount();
                }*/


                //runSelectedMethodsOfSelectedCountries(chooseDropActionDemo2, chooseDropActionDemo2.getQueueTestClass(), chooseDropActionDemo2.getMapCountryClass(), junit, result, possibleChoices);



                long endTime = System.currentTimeMillis();
                totalRuntime = Math.toIntExact((endTime - startTime) / 1000);



                try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.txt")) {
                    writer.write("Tesztek eredményei:\n");
                    writer.write("Sikeres tesztek: " + passedTests + "\n");
                    writer.write("Sikertelen tesztek: " + failedTests + "\n\n");
                    writer.write("Tesztfutatás hossza: " + totalRuntime + " sec" + "\n\n");

                    System.out.println("The elements in the all method are: ");
                    Stream.of(allMethods).forEach(System.out::println);
                    System.out.println(allMethodString);
                    System.out.println("---------------------------------------------------");

                    //TODO DELETE SUCCEED TEST CASES FROM FAILEDTESTS.TXT --- PIPPPAA --- AGAIN BAD --- AGAIN PIPPAAA
                    Set<String> temporaryAllMethods = new HashSet<>();

                    /*if (!result.wasSuccessful()) {
                        for (Failure failure : result.getFailures()) {
                            writer.write("Sikertelen teszt: " + failure.getDescription().getDisplayName() + "\n\n");
                            writeStringToTxt(failure.getDescription().getDisplayName(), "failedtests.txt");
                            /*if(allMethods.contains(failure.getDescription().getDisplayName().split("\\(")[0])){
                                allMethods.remove(failure.getDescription().getDisplayName().split("\\(")[0]);
                            }*/
                            /*if(allMethods.contains(failure.getDescription().getDisplayName().split("\\(")[0])){
                                allMethods.remove(failure.getDescription().getDisplayName().split("\\(")[0]);
                            }
                            for (String method : allMethods) {
                                System.out.println("----------------------------: " + method);
                                char[] dollarSymbol = method.toCharArray();
                                for(char symbol: dollarSymbol){
                                    if(symbol == '$'){
                                        temporaryAllMethods.add(method.split("\\$")[1]);
                                    } else {
                                        temporaryAllMethods.add(method);
                                    }
                                }
                            }
                            allMethods = temporaryAllMethods;
                            if(allMethods.contains(failure.getDescription().getDisplayName().split("\\(")[0])){
                                allMethods.remove(failure.getDescription().getDisplayName().split("\\(")[0]);
                            }
                        }

                        if(allMethods.isEmpty()){
                            allMethods.add("No succeed test cases");
                        }*/
                        //Set<String> allFailedTests = readTxtToStringSet("failedtests.txt");

                        //Set<String> allFailedTests = readTxtToStringSet("failedtests.txt");


                    if (!result.wasSuccessful()) {
                        for (Failure failure : result.getFailures()) {
                            writer.write("Sikertelen teszt: " + failure.getDescription().getDisplayName() + "\n\n");
                            writeStringToTxt(failure.getDescription().getDisplayName(), "failedtests.txt");

                            String failedMethod = failure.getDescription().getDisplayName().split("\\(")[0];
                            allMethods.remove(failedMethod);  // Remove the failed method first

                            // Create a copy of allMethods to iterate over
                            List<String> methodsCopy = new ArrayList<>(allMethods);
                            temporaryAllMethods.clear();  // Clear the temporary list

                            for (String method : methodsCopy) {
                                char[] dollarSymbol = method.toCharArray();
                                boolean containsDollar = false;
                                for (char symbol : dollarSymbol) {
                                    if (symbol == '$') {
                                        temporaryAllMethods.add(method.split("\\$")[1]);
                                        containsDollar = true;
                                        break;
                                    }
                                }
                                if (!containsDollar) {
                                    temporaryAllMethods.add(method);
                                }
                            }

                            allMethods = new HashSet<>(temporaryAllMethods);  // Assign the modified list back to allMethods

                            // Finally, remove the failed method again, if it was added during processing
                            allMethods.remove(failedMethod);


                        }
                    }


                    //QUASTIONABLE ELSE PATH
                        //else {
                    System.out.println("The elements in the all method are: ");
                    Stream.of(allMethods).forEach(System.out::println);
                    System.out.println("---------------------------------------------------");

                    deleteLambdaString(allMethods);
                    System.out.println("The elements in the all method without lambda start are: ");
                    Stream.of(allMethods).forEach(System.out::println);

                    System.out.println("---------------------------------------------------");

                    System.out.println("The list of remaining methods after removing failures: ");
                    allMethods.forEach(System.out::println);
                    deleteEveryStringOccurrence(allMethods, "failedtests.txt");


                    //}


                    /*if (!result.wasSuccessful()) {
                        for (Failure failure : result.getFailures()) {
                            writer.write("Sikertelen teszt: " + failure.getDescription().getDisplayName() + "\n\n");
                            Set<String> allFailedTests = readTxtToStringSet("failedtests.txt");
                            if(allFailedTests.contains(failure.getDescription().getDisplayName())){
                                writeStringToTxt(failure.getDescription().getDisplayName(), "failedtests.txt");

                            }
                        }
                        Set<String> allFailedTests = readTxtToStringSet("failedtests.txt");
                        System.out.println("The failed tets are: ");
                        Stream.of(allFailedTests).forEach(System.out::println);
                        allMethods.remove(allFailedTests);
                        System.out.println("The list of every methdos derived by failures: ");
                        Stream.of(allMethods).forEach(System.out::println);
                        for (String singleMethod : allMethods) {
                            if (allFailedTests.contains(singleMethod)) {
                                deleteEveryStringOccurrence(singleMethod, "failedtests.txt");
                            }
                        }*/

                        /*testExecutionDataList.add(new TestExecutionData(
                                failure.getDescription().getDisplayName(),
                                0, // Set actual execution time
                                "FAILED"
                        ));*/


                /*for (String testName : getPassedTests(result)) {
                    testExecutionDataList.add(new TestExecutionData(
                            testName,
                            0, // Set actual execution time
                            "PASSED"
                    ));
                }*/
                    try {
                        System.out.println("The size of the test execution datalist is: " + testExecutionDataList.size());
                        writeTestExecutionDataToFile(testExecutionDataList, "execution_data.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }



                try (FileWriter writer = new FileWriter("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\testreport.html")) {
                    writer.write("<!DOCTYPE html>\n");
                    writer.write("<html lang=\"en\">\n");
                    writer.write("<head>\n");
                    writer.write("    <meta charset=\"UTF-8\">\n");
                    writer.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
                    writer.write("    <title>Tesztreport</title>\n");
                    writer.write("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
                    writer.write("    <style>\n");

                    writer.write("        .card {\n");
                    writer.write("            cursor: pointer;\n");
                    writer.write("            transition: box-shadow 0.3s ease-in-out;\n");
                    writer.write("            overflow: hidden;\n");
                    writer.write("        }\n");
                    writer.write("        .card:hover {\n");
                    writer.write("            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);\n");
                    writer.write("        }\n");
                    writer.write("        .red-rectangle {\n");
                    writer.write("            width: 100%;\n");
                    writer.write("            height: 70px; \n");
                    writer.write("            background-color: #e36659;\n");
                    writer.write("        }\n");
                    writer.write("        .green-rectangle {\n");
                    writer.write("            width: 100%;\n");
                    writer.write("            height: 70px; \n");
                    writer.write("            background-color: #2c8c27;\n");
                    writer.write("        }\n");
                    writer.write("        .card-title-container {\n");
                    writer.write("            position: relative;\n");
                    writer.write("            z-index: 1;\n");
                    // writer.write("            margin-top: -50px; /* Felhozom a címet, hogy átfedje a piros téglalapot */\n");
                    //writer.write("            padding-top: 50px; /* Kiegyenlítem a margin-t, hogy ne lógjon ki a szöveg */\n");
                    writer.write("        }\n");
                    writer.write("        .custom-popover-button {\n");
                    //writer.write("            width: 200px; \n");
                    //writer.write("            height: 30px; \n");
                    writer.write("            margin-bottom: 16px;\n");
                    writer.write("            padding: 6px 12px;\n");
                    writer.write("            display: inline-flex;\n"); // Flexbox használata
                    writer.write("            align-items: center;\n"); // Vertikális középre igazítás
                    writer.write("            justify-content: center;\n"); // Horizontális középre igazítás
                    writer.write("            text-align: center;\n"); // Biztosítsuk, hogy a szöveg középre legyen igazítva
                    writer.write("        }\n");
                    writer.write("        .popover-body {\n");
                    writer.write("            text-align: center; /* Középre igazítja a szöveget */\n");
                    writer.write("        }\n");
                    writer.write("        .popover-header {\n");
                    writer.write("            text-align: center; /* Középre igazítja a címet */\n");
                    writer.write("        }\n");
                    writer.write("        .font-weight-bold {\n");
                    writer.write("            font-weight: bold;}\n");
                    /*writer.write("        .custom-border-success {\n");
                    writer.write("            display: inline-block;\n");
                    writer.write("            border: 2px solid #73de90; \n");
                    writer.write("            padding: 10px; \n");
                    writer.write("            border-radius: 5px;}\n");
                    writer.write("        .custom-border-failed {\n");
                    writer.write("            display: inline-block;\n");
                    writer.write("            border: 2px solid #e34934;\n");
                    writer.write("            padding: 10px;\n");
                    writer.write("            border-radius: 5px;}\n");*/
                    writer.write("        .custom-border-success, .custom-border-failed {\n");
                    writer.write("            text-align: center; /* Középre igazítja a címet */\n");
                    writer.write("            border: 2px solid transparent; /* Alapértelmezett, átlátszó keret */\n");
                    writer.write("            padding: 10px; /* Térköz a keret és a szöveg között */\n");
                    writer.write("            border-radius: 5px; /* Kerekített sarkok */\n");
                    writer.write("            display: flex; /* Inline blokk elem, csak a tartalom szélességét veszi figyelembe */\n");
                    writer.write("            margin-bottom: 10px; /* Térköz a szövegek között */\n");
                    writer.write("            margin-right: 48px; /* Térköz a szövegek között */\n");
                    writer.write("            margin-top: 16px; /* Térköz a szövegek között */\n");
                    writer.write("            max-width: 30%; /* Maximális szélesség a teljes konténerhez viszonyítva */\n");

                    writer.write("        }\n");
                    writer.write("        .custom-border-success {\n");
                    writer.write("            border-color: #28a745;\n");
                    writer.write("        }\n");
                    writer.write("        .custom-border-failed {\n");
                    writer.write("            border-color: #dc3545;\n");
                    writer.write("        }\n");
                    writer.write("        .min-vh-50 {\n");
                    writer.write("            min-height: 50vh;\n");
                    writer.write("        }\n");
                    writer.write("        .bg-translucent-red {\n");
                    writer.write("            background-color: rgba(255, 0, 0, 0.05) !important;\n");
                    writer.write("        }\n");
                    writer.write("        .bg-translucent-grey {\n");
                    writer.write("            background-color: rgba(254, 121, 104, 0.05) !important;\n");
                    writer.write("        }\n");


                    writer.write("        .padding-right-16 {\n");
                    writer.write("            padding-right: 16px;\n");
                    writer.write("        }\n");


                    /*writer.write("          .bg-success {\n");
                    writer.write("                --bs-bg-opacity: 1;\n");
                    writer.write("                background-color: rgba(var(--bs-success-rgb), var(--bs-bg-opacity)) !important;\n");
                writer.write("                }\n");*/
                    writer.write("    </style>\n");
                    writer.write("</head>\n");
                    writer.write("<body>\n");


                    writer.write("<div class=\"container-fluid d-flex justify-content-center align-items-center min-vh-50\">\n");

                    writer.write("    <div class=\"container bg-translucent-grey\">\n");
                    writer.write("        <h1 class=\"text-center\">Tesztek eredményei:</h1>\n");


                    //writer.write("        <p class=\"text-center custom-border-success d-flex justify-content-center\"><b>Sikeres tesztek: </b>" + passedTests + "</p>\n");
                    //writer.write("        <p class=\"text-center custom-border-failed d-flex justify-content-center\"><b>Sikertelen tesztek: </b>" + failedTests + "</p>\n");

                    writer.write("        <div class=\"container mt-4\">\n");
                    writer.write("            <div class=\"row justify-content-center\">\n");
                    writer.write("                <div class=\"col-md-6\">\n");
                    writer.write("                      <p class=\" custom-border-success bg-success p-2 text-dark bg-opacity-25\" ><b style=\"padding-right: 8px\">Sikeres tesztek:</b>" +  passedTests + "</p>\n");
                    writer.write("                      <p class=\"custom-border-failed bg-danger p-2 text-dark bg-opacity-25\"><b style=\"padding-right: 8px\">Sikertelen tesztek:</b>" +  failedTests + "</p>\n");
                    writer.write("                    <button class=\"btn btn-success mb-3\" type=\"button\" data-bs-toggle=\"offcanvas\" data-bs-target=\"#offcanvasRight\" aria-controls=\"offcanvasRight\">Click to view succeed Test Cases</button>\n");
                    writer.write("                </div>\n");
                    writer.write("            </div>\n");
                    writer.write("        </div>\n");


                    writer.write("        <div class=\"offcanvas offcanvas-end\" tabindex=\"-1\" id=\"offcanvasRight\" aria-labelledby=\"offcanvasRightLabel\">\n");
                    writer.write("            <div class=\"offcanvas-header\">\n");
                    writer.write("                <h5 class=\"offcanvas-title\" id=\"offcanvasRightLabel\">PASSED TESTS</h5>\n");
                    writer.write("                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"offcanvas\" aria-label=\"Close\"></button>\n");
                    writer.write("            </div>\n");
                    writer.write("            <div class=\"offcanvas-body bg-success p-2 text-white bg-opacity-25\"\n");
                    System.out.println("The size of the allmethods is :---------------------------------------------------------------------: " + allMethods.size());
                    for(String testCase: allMethods){
                        testCase = trimTooLongTestCase(testCase);

                        writer.write("                <p>---------------------------------------------------------</p>\n");
                        writer.write("        <div class=\"container mt-4\">\n");
                        writer.write("            <div class=\"row justify-content-center\">\n");
                        writer.write("                <div class=\"col-10\">\n");
                        writer.write("                    <div class=\"card mb-4\">\n");
                        writer.write("                        <div class=\"green-rectangle text-center\" style=\"display: flex; justify-content: center; align-items: center; height: 100%;\"><h4 style=\"color: white;\">" + testCase + "</h4></div>\n");
                        writer.write("                        <div class=\"card-body bg-translucent-red\">\n");
                        writer.write("                            <div class=\"card-title-container\">\n");
                        writer.write("                                <h5 class=\"card-title text-center\">Sikeres Teszt<span class=\"badge bg-success\" style=\"margin-left: 6px\">Success</span></h5>\n");
                        writer.write("                            </div>\n");
                        //writer.write("                            <p class=\"card-text text-left\">" + testCase + "</p>\n");
                        //writer.write("                                <b>Megtekintés</b></a></p>\n");
                        writer.write("                        </div>\n");
                        writer.write("                    </div>\n");
                        writer.write("                </div>\n");
                        writer.write("            </div>\n");
                        writer.write("        </div>\n");
                    }
                    writer.write("                <!-- Ide jön az offcanvas tartalma -->\n");
                    writer.write("            </div>\n");
                    writer.write("        </div>\n");
                    String accordionId = generateUniqueId();

                    //TODO ADD ALWAYS THE FOLDER FOR DIFFERENT IMAGES
                    List<String> failedImageDiffList = Utils.readImageNamesFromFolder("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl");
                    failedImageDiffList.addAll(Utils.readImageNamesFromFolder("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb"));
                    failedImageDiffList.addAll(Utils.readImageNamesFromFolder("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu"));
                    for(String imageName: failedImageDiffList){
                        System.out.println("FAILED IMAGE NAME IN IMAGE DIFF FOLDER IS: " + imageName);
                    }

                    for (Failure failure : result.getFailures()) {

                        if(failure.getMessage().contains("images are different")){
                            methodsWithFailedImages.add(failure.getDescription().getMethodName());
                        }

                        failedTestCases.add(String.valueOf(failure.getDescription()));
                        String messageId = generateUniqueId();
                        String traceId = generateUniqueId();
                        String testHeader = failure.getTestHeader().split("\\(")[0];
                        System.out.println("THE VALUE OF THE TEST HEADER IS: " + testHeader);

                        writeToFile("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\" + messageId + ".txt", failure.getMessage());
                        writeToFile("C:\\Users\\SőregiKrisztián\\Pictures\\test_report\\" + traceId + ".txt", failure.getTrace());

                        writer.write("        <div class=\"container mt-4\">\n");
                        writer.write("            <div class=\"row justify-content-center\">\n");
                        writer.write("                <div class=\"col-md-6\">\n");
                        writer.write("                    <div class=\"card mb-4\">\n");
                        writer.write("                        <div class=\"red-rectangle\"></div>\n");
                        writer.write("                        <div class=\"card-body bg-translucent-red\">\n");
                        writer.write("                            <div class=\"card-title-container\">\n");
                        writer.write("                                <h5 class=\"card-title text-center\">Sikertelen teszt #" + failure.getDescription() + "<span class=\"badge bg-danger\">Failed</span></h5>\n");
                        writer.write("                            </div>\n");
                        writer.write("                            <button type=\"button\" class=\"btn btn-md btn-danger custom-popover-button\" data-bs-toggle=\"popover\"\n");
                        writer.write("                                data-bs-title=\"Hibaüzenet\" data-bs-html=\"true\" data-bs-content=\"" + failure.getMessage().replace("\n", "<br>") + "\">\n");
                        writer.write("                                Click to view error message\n");
                        writer.write("                            </button>\n");
                        writer.write("                            <p class=\"card-text text-left\">Hiba stack trace: <a href=\"" + traceId + ".txt\" target=\"_blank\" class=\"link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover\">\n");
                        writer.write("                                <b>Megtekintés</b></a></p>\n");
                       /* writer.write("                            <div class=\"accordion\" id=\"imageAccordion\">\n");
                        writer.write("                                <div class=\"accordion-item\">\n");
                        writer.write("                                    <h2 class=\"accordion-header\" id=\"headingImage\">\n");
                        writer.write("                                        <button class=\"accordion-button collapsed\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#collapseImage\" aria-expanded=\"false\" aria-controls=\"collapseImage\">\n");
                        writer.write("                                            View Image\n");
                        writer.write("                                        </button>\n");
                        writer.write("                                    </h2>\n");
                        writer.write("                                    <div id=\"collapseImage\" class=\"accordion-collapse collapse\" aria-labelledby=\"headingImage\" data-bs-parent=\"#imageAccordion\">\n");
                        writer.write("                                        <div class=\"accordion-body\">\n");
                        writer.write("                                            <img src=\"file:///C:/Users/S%C5%91regiKriszti%C3%A1n/Pictures/selenium_failed_tests/" + testHeader + ".png \" class=\"img-fluid\" alt=\"Descriptive Alt Text\">\n");
                        writer.write("                                        </div>\n");
                        writer.write("                                    </div>\n");
                        writer.write("                                </div>\n");*/
                        writer.write("                            <div class=\"accordion\" id=\"" + accordionId + "\">\n");
                        writer.write("                                <div class=\"accordion-item\">\n");
                        writer.write("                                    <h2 class=\"accordion-header\" id=\"headingImage-" + messageId + "\">\n");
                        writer.write("                                        <button class=\"accordion-button collapsed\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#collapseImage-" + messageId + "\" aria-expanded=\"false\" aria-controls=\"collapseImage-" + messageId + "\">\n");
                        writer.write("                                            View Image\n");
                        writer.write("                                        </button>\n");
                        writer.write("                                    </h2>\n");
                        writer.write("                                    <div id=\"collapseImage-" + messageId + "\" class=\"accordion-collapse collapse\" aria-labelledby=\"headingImage-" + messageId + "\" data-bs-parent=\"#"+ accordionId + "\">\n");
                        //TODO MUST MANUALLY SELECT THE PATH IN THE IF SECTION
                        writer.write("                                        <div class=\"accordion-body\">\n");
                                                                                    if(failedImageDiffList.stream().anyMatch(
                                                                                            i -> i.startsWith(testHeader) )){

                                                                                        //String imagePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\" + testHeader + ".png";

                                                                                        String imagePath = getFailedCountriesImagePath(testHeader);
                                                                                        String base64Image = encodeImageToBase64(imagePath);
                                                                                        writer.write("<img src=\"data:image/png;base64," + base64Image + "\" class=\"img-fluid\" alt=\"Failed Test Image\">\n");

                                                                                        //writer.write("<img src=\"file:///C:/Users/S%C5%91regiKriszti%C3%A1n/Pictures/selenium_image_differences/qa2_pl/" + testHeader + ".png\" class=\"img-fluid\" alt=\"Descriptive Alt Text\">\n");
                                                                                    }   else {
                                                                                        writer.write("<img src=\"file:///C:/Users/S%C5%91regiKriszti%C3%A1n/Pictures/selenium_failed_tests/" + testHeader + ".png\" class=\"img-fluid\" alt=\"Descriptive Alt Text\">\n");
                                                                                    }
                        writer.write("                                        </div>\n");
                        writer.write("                                    </div>\n");
                        writer.write("                                </div>\n");
                        writer.write("                            </div>\n");
                        writer.write("                        </div>\n");
                        writer.write("                    </div>\n");
                        writer.write("                </div>\n");
                        writer.write("            </div>\n");
                        writer.write("        </div>\n");
                    }

                    writer.write("    </div>\n");
                    writer.write("</div>\n");

                    writer.write("        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n");
                    writer.write("        <script>\n");
                    writer.write("            document.addEventListener('DOMContentLoaded', function () {\n");
                    writer.write("                var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle=\"popover\"]'));\n");
                    writer.write("                var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {\n");
                    writer.write("                    return new bootstrap.Popover(popoverTriggerEl);\n");
                    writer.write("                });\n");
                    writer.write("            });\n");
                    writer.write("        </script>\n");

                    writer.write("</body>\n");
                    writer.write("</html>\n");

                    /*writer.write("        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js\"></script>\n");
                    writer.write("        <script>\n");
                    writer.write("            document.addEventListener('DOMContentLoaded', function () {\n");
                    writer.write("                var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle=\"popover\"]'));\n");
                    writer.write("                var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {\n");
                    writer.write("                    return new bootstrap.Popover(popoverTriggerEl);\n");
                    writer.write("                });\n");
                    writer.write("            });\n");
                    writer.write("        </script>\n");

                    writer.write("    </div>\n"); // container vége
                    writer.write("</div>\n"); // container-fluid vége

                    writer.write("</body>\n");
                    writer.write("</html>\n");*/
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            //TODO THERE'S SOME MISBEHAVIOUR IN THE SET OF ALLMETHODS, MAYBE CAN'T RUN ONLY ONE TEST CASE
            //Once again, this seems like working fine for now.

            //TODO RUNTIMEESTIMATOR BLOCKS TEST CASES TO PASS - ITS OKAY NOW

            @Override
            protected void done() {
                // Update UI or perform any necessary post-processing
                try {
                    get(); // Ensure background task completed successfully
                    //RuntimeEstimator.run();

                    windowMenu = new WindowMenu(addedModel, runMethodNames, methodsWithFailedImages);
                    //windowMenu = new WindowMenu();
                    windowMenu.showAlertMessage(failedTests);

                    /*if (failedTests != null && !failedTests.toString().isEmpty()) {
                        windowMenu.showAlertMessage(failedTests);
                    }*/

                    windowMenu.run();
                    CircleDiagram circleDiagram = new CircleDiagram(passedTests, failedTests, windowMenu.getMenuBarWidth());
                    updateFailedTestCases(failedTestCases);


                    //TODO May should close driver here, but not like that :)
                    /*WebTestTH.driver.quit();
                    WebTestTH.driver.close();*/
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } /*catch (IOException e) {
                throw new RuntimeException(e);
            }*/ catch (IOException | FontFormatException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                } catch (Throwable throwable)
                {
                    System.out.println(throwable.toString());
                    throwable.printStackTrace();
                }
            }
        };


        worker.execute(); // Start the SwingWorker

        //TODO SHUTDOWN MEMORY USE OF CLOSED WINDOWS
        windowMenu.getMenu1().addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {

            }

            @Override
            public void menuDeselected(MenuEvent e) {
                if(worker.isDone()){
                    worker.cancel(true);
                }
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                if(worker.isDone()){
                    worker.cancel(true);
                }
            }
        });


    }


    private static void runSelectedMethodsOfSelectedCountries(ChooseDropActionDemo2 chooseDropActionDemo2, Queue<Class> selectedCountriesQueue, Map<String, Class> selectedCountriesKeyPAir, JUnitCore junit,
                           Result result, String[] possibleChoices){
        everyTestMethodsToRun = chooseDropActionDemo2.getMovedItems();

        System.out.println("METHOD IS INVOKED: " + "runSelectedMethodsOfSelectedCountries");

        String[] selectedCountriesArray = new String[selectedCountriesQueue.size()];
        int index = 0;
        /*for (Class countryClass : selectedCountriesQueue) {
            String className = countryClass.getSimpleName();
            for (String monogram : possibleChoices) {
                if (className.contains(monogram)) {
                    selectedCountriesArray[index++] = className;
                    break;
                }
            }
        }*/
        System.out.println("The size of selected countries queue inside runselected method: " + selectedCountriesQueue.size());
        Class countryClass;
        while((countryClass = selectedCountriesQueue.poll()) != null){
            String className = countryClass.getSimpleName();
            for (String monogram : possibleChoices) {
                if (className.contains(monogram)) {
                    selectedCountriesArray[index++] = className;
                    break;
                }
            }
        }

       for(String selectedCountry: selectedCountriesArray){
          System.out.println(selectedCountry);
        }

        for(String country: selectedCountriesArray){
            selectedCountriesKeyPAir.get(country).getDeclaredMethods();
            for (String methodToRun : everyTestMethodsToRun) {
                Method[] methods = selectedCountriesKeyPAir.get(country).getDeclaredMethods();


                for (Method method : methods) {
                    if ((method.toString()).contains(methodToRun)) {
                        System.out.println(method);
                        System.out.println("Method get name: " + method.getName());
                        if (method.isAnnotationPresent(org.junit.Test.class)) {
                            Request request = Request.method(selectedCountriesKeyPAir.get(country), method.getName());

                            Result methodResult = junit.run(request);

                            System.out.println(methodResult.getRunCount());
                            System.out.println(methodResult.getRunTime());

                            result = mergeResults(result, methodResult);
                            System.out.println(result.getRunCount());
                            System.out.println(result.getRunTime());

                            manageRunCount(methodResult);
                        }
                    }
                }
            }
        }

    }

    private static void uniqueCountryAddJButton(JButton jButton, JComboBox comboBox){
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newOption = jButton.getText();

                /*if (newOption != null && !newOption.isEmpty() && !addedModel.contains(newOption)) {
                    model.addElement(newOption);
                    addedModel.add(newOption);
                    if (model.getSize() >= 1) {
                        comboBox.setSelectedItem(model.getElementAt(model.getSize()-1));
                    }
                }*/

                if (newOption != null && !newOption.isEmpty()) {
                    if (addedModel.contains(newOption)) {
                        model.removeElement(newOption);
                        addedModel.remove(newOption);
                    } else {
                        model.addElement(newOption);
                        addedModel.add(newOption);
                    }
                    if (model.getSize() >= 1) {
                        comboBox.setSelectedItem(model.getElementAt(model.getSize() - 1));
                    }
                }


            }
        });
    }


    private static Result mergeResults (Result result1, Result result2){
        // Merge two JUnit results
        for (Failure failure : result2.getFailures()) {
            result1.getFailures().add(failure);
        }

        return result1;
    }

    private static void manageRunCount (Result methodResult){
        if (methodResult.getFailureCount() == 1) {
            failedTests++;
        } else {
            passedTests++;
        }
    }

    private static String generateUniqueId () {
        return UUID.randomUUID().toString();
    }


    private static void writeToFile (String path, String message) throws IOException {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(message);
        }
    }
    private static List<String> getPassedTests (Result result){
        List<String> passedTests = new ArrayList<>();
        result.getFailures().forEach(failure -> {
            String testName = failure.getDescription().getDisplayName();
            if (!passedTests.contains(testName)) {
                passedTests.add(testName);
            }
        });
        return passedTests;
    }

    private static void writeTestExecutionDataToFile (List < TestExecutionData > data, String filePath) throws
    IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (TestExecutionData testData : data) {
                testData.setTotalRuntime(totalRuntime);
                writer.write(testData.getTestName() + "," + testData.getExecutionTime() + "," + testData.getStatus() + "," + testData.getTotalRuntime() + "\n");
            }
        }
    }

    private static void writeStringToTxt (String text, String fileName){

        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file, true);
            //fileWriter.write(text.split("\\(")[0] + '\n');
            fileWriter.write(text + '\n');

            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Set<String> readTxtToStringSet(String fileName){
        Set<String> testNames = new HashSet<>();
        try{
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                testNames.add(scanner.nextLine().split("\\(" )[0]);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Stream.of(testNames).forEach(System.out::println);
        return testNames;
    }

private static void deleteEveryStringOccurrence(Set<String> methodsToDelete, String fileName) {
    System.out.println("Delete every String Occurence Method is Invoked");
    File inputFile = new File(fileName);
    File tempFile = new File("tempfile.txt");

    try {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim().split("\\(")[0];
            System.out.println("The trimmed line is: " + trimmedLine);
            if (!methodsToDelete.contains(trimmedLine)) {
                writer.write(currentLine + System.lineSeparator());
            }
        }
        writer.close();
        reader.close();

        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename file");
        }   else {
            tempFile.renameTo(inputFile);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private static void deleteLambdaString(Set<String> allMethods){
        allMethods.removeIf(m -> m.startsWith("lambda"));
}

private static String trimTooLongTestCase(String lengthToMeasure){
        StringBuilder stringBuilder = new StringBuilder(lengthToMeasure);
        if(stringBuilder.length() > 22){
            stringBuilder.delete(19, stringBuilder.length());
            stringBuilder.append("...");
        }
        return stringBuilder.toString();
}
    static class CustomComboBoxRenderer extends BasicComboBoxRenderer {


        public CustomComboBoxRenderer() {
            super();

        }



        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            try {
                File fontFile = new File("src/main/resources/font/Monofett-Regular.ttf");
                Font monoFett = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 24);

                setFont(monoFett);

            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }


            return this;
        }
    }

    private static String encodeImageToBase64(String imagePath) {
        try {
            File file = new File(imagePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            System.err.println("Error encoding image: " + imagePath);
            return null;
        }
    }

    private static String getFailedCountriesImagePath(String failedMethodName){
        String imagePath;
        if(failedMethodName.endsWith("GB")){
            imagePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\" + failedMethodName + ".png";
        } else if (failedMethodName.endsWith("QA2PL")){
            imagePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\" + failedMethodName + ".png";
        } else if (failedMethodName.endsWith("QA2HU")){
            imagePath = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\" + failedMethodName + ".png";
        }else {
            imagePath = null;
        }

        return imagePath;
    }


    private static void updateFailedTestCases(Queue<String> previouslyFailedTestCases) {
        String filePath = "previously_failed_test_cases.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            while (!previouslyFailedTestCases.isEmpty()) {
                String testCase = previouslyFailedTestCases.poll();
                writer.write(testCase);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showWarningDialog(String message) {
        JDialog warningDialog = new JDialog((Frame) null, "Warning", true);
        warningDialog.setLayout(new BorderLayout());
        warningDialog.setBackground(Color.RED);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setBackground(Color.YELLOW);
        messagePanel.setPreferredSize(new Dimension(360, 50));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel warningLabel = new JLabel(message);
        warningLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        warningLabel.setForeground(Color.BLACK);
        warningLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton okButton = new JButton("OK");
        okButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        okButton.setFont(new Font("Arial", Font.BOLD, 16));
        okButton.setBackground(Color.BLACK);
        okButton.setForeground(Color.WHITE);
        okButton.setPreferredSize(new Dimension(50, 30));
        okButton.setHorizontalAlignment(SwingConstants.CENTER);
        okButton.setVerticalAlignment(SwingConstants.CENTER);
        //okButton.setMargin(new Insets(0,0,10,0));

        okButton.addActionListener(e -> warningDialog.dispose());

        messagePanel.add(warningLabel);
        buttonPanel.add(okButton);


        warningDialog.add(messagePanel, BorderLayout.NORTH);
        warningDialog.add(buttonPanel, BorderLayout.SOUTH);
        warningDialog.setSize(360, 120);
        warningDialog.setLocationRelativeTo(null);
        warningDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        warningDialog.setVisible(true);
    }


}