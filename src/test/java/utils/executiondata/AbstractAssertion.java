package utils.executiondata;

import tests.WebTestTH;
import org.example.utils.SectionScreenshot;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;
import runner.TestRunner4;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public abstract class AbstractAssertion {

    private String assertionErrorMessage;

    private SectionScreenshot sectionScreenshot = new SectionScreenshot();

    private boolean isImagesDifferent;

    public boolean getIsImagesDifferent(){
        return isImagesDifferent;
    }

    protected boolean assertMethod(SoftAssert softAssert, Object actual, Object expected, boolean isImagesDifferent){

        if (actual instanceof String && expected instanceof String) {
            String actualStr = (String) actual;
            String expectedStr = (String) expected;


            softAssert.assertEquals(actualStr, expectedStr);
            System.out.println("Assert Equals (String) Invoked");

        } else if (actual instanceof Integer[] && expected instanceof Integer[]) {
            Integer[] actualArr = (Integer[]) actual;
            Integer[] expectedArr = (Integer[]) expected;


            softAssert.assertEquals(actualArr, expectedArr);
            System.out.println("Assert Equals (Integer[]) Invoked");

        } else if (actual instanceof String[] && expected instanceof String[]) {
            String[] actualArr = (String[]) actual;
            String[] expectedArr = (String[]) expected;

            softAssert.assertEquals(actualArr, expectedArr);
            System.out.println("Assert Equals (String[]) Invoked");

        } else if (actual instanceof List<?> && expected instanceof List<?>) {
            List<?> tempListActual = (List<?>) actual;
            List<?> tempListExpected = (List<?>) expected;

            if(!tempListActual.isEmpty() && tempListActual.getFirst() instanceof String[]){
                List<String[]> actualList = (List<String[]>) tempListActual;
                List<String[]> expectedList = (List<String[]>) tempListExpected;
                IntStream.range(0, actualList.size())
                        .forEach(i -> {
                            softAssert.assertEquals(actualList.get(i), expectedList.get(i));
                        });
            }
        } else if (actual instanceof LinkedHashMap<?,?> && expected instanceof LinkedHashMap<?,?>){
            LinkedHashMap<?, ?> tempMapActual = (LinkedHashMap<?, ?>) actual;
            LinkedHashMap<?, ?> tempMapExpected = (LinkedHashMap<?, ?>) expected;

            if (!tempMapActual.isEmpty() && tempMapActual.values().iterator().next() instanceof Integer[]) {

                Map.Entry<?, ?> firstEntry = tempMapActual.entrySet().iterator().next();
                Object firstKey = firstEntry.getKey();
                Object firstValue = firstEntry.getValue();

                boolean safeToCastBasedOnKey = true;

                if(!(firstKey instanceof Integer)){
                    safeToCastBasedOnKey = false;
                    System.out.println("Not safe to cast the first key to Integer");
                }
                if(!(firstValue instanceof Integer[])){
                    safeToCastBasedOnKey = false;
                    System.out.println("Not safe to cast the first value to Integer[]");
                }

                if(safeToCastBasedOnKey){
                    System.out.println("Assert Equals (LinkedHashMap<Integer, Integer[]>) Invoked");

                    LinkedHashMap<Integer, Integer[]> actualMap = (LinkedHashMap<Integer, Integer[]>) tempMapActual;
                    LinkedHashMap<Integer, Integer[]> expectedMap = (LinkedHashMap<Integer, Integer[]>) tempMapExpected;

                    IntStream.range(1, actualMap.size() + 1)
                            .forEach(i -> {
                                Integer[] actualIntegerArr = actualMap.get(i);
                                Integer[] expectedIntegerArr = expectedMap.get(i);

                                softAssert.assertNotNull(actualIntegerArr, "Actual value at index " + i + " is null.");
                                softAssert.assertNotNull(expectedIntegerArr, "Expected value at index " + i + " is null.");

                                if (actualIntegerArr != null && expectedIntegerArr != null) {
                                    softAssert.assertEquals(actualIntegerArr, expectedIntegerArr, "Values at index " + i + " do not match.");
                                }
                            });
                }

            }
        }   else {
            System.out.println("The instances of actual/expected Objects are not recognized by the assertMethod().");
        }
        boolean allAssertPassed = true;
        softAssert.assertTrue(isImagesDifferent);
        try {
            softAssert.assertAll();
        } catch (AssertionError e){
            allAssertPassed = false;
            assertionErrorMessage = e.getMessage();
            System.out.println(e.getMessage());
        }
        System.out.println("The value of the allAssertPassed is: " + allAssertPassed);
        return allAssertPassed;
    }

    protected boolean assertMethod(SoftAssert softAssert, Object actual, Object expected){

        if (actual instanceof String && expected instanceof String) {
            String actualStr = (String) actual;
            String expectedStr = (String) expected;


            softAssert.assertEquals(actualStr, expectedStr);
            System.out.println("Assert Equals (String) Invoked");

        } else if (actual instanceof Integer[] && expected instanceof Integer[]) {
            Integer[] actualArr = (Integer[]) actual;
            Integer[] expectedArr = (Integer[]) expected;


            softAssert.assertEquals(actualArr, expectedArr);
            System.out.println("Assert Equals (Integer[]) Invoked");

        } else if (actual instanceof String[] && expected instanceof String[]) {
            String[] actualArr = (String[]) actual;
            String[] expectedArr = (String[]) expected;

            softAssert.assertEquals(actualArr, expectedArr);
            System.out.println("Assert Equals (String[]) Invoked");

        } else if (actual instanceof List<?> && expected instanceof List<?>) {
            List<?> tempListActual = (List<?>) actual;
            List<?> tempListExpected = (List<?>) expected;

            if(!tempListActual.isEmpty() && tempListActual.getFirst() instanceof String[]){
                List<String[]> actualList = (List<String[]>) tempListActual;
                List<String[]> expectedList = (List<String[]>) tempListExpected;
                IntStream.range(0, actualList.size())
                        .forEach(i -> {
                            softAssert.assertEquals(actualList.get(i), expectedList.get(i));
                        });
            }
        } else if (actual instanceof LinkedHashMap<?,?> && expected instanceof LinkedHashMap<?,?>){
            LinkedHashMap<?, ?> tempMapActual = (LinkedHashMap<?, ?>) actual;
            LinkedHashMap<?, ?> tempMapExpected = (LinkedHashMap<?, ?>) expected;

            if (!tempMapActual.isEmpty() && tempMapActual.values().iterator().next() instanceof Integer[]) {

                Map.Entry<?, ?> firstEntry = tempMapActual.entrySet().iterator().next();
                Object firstKey = firstEntry.getKey();
                Object firstValue = firstEntry.getValue();

                boolean safeToCastBasedOnKey = true;

                if(!(firstKey instanceof Integer)){
                    safeToCastBasedOnKey = false;
                    System.out.println("Not safe to cast the first key to Integer");
                }
                if(!(firstValue instanceof Integer[])){
                    safeToCastBasedOnKey = false;
                    System.out.println("Not safe to cast the first value to Integer[]");
                }

                if(safeToCastBasedOnKey){
                    System.out.println("Assert Equals (LinkedHashMap<Integer, Integer[]>) Invoked");

                    LinkedHashMap<Integer, Integer[]> actualMap = (LinkedHashMap<Integer, Integer[]>) tempMapActual;
                    LinkedHashMap<Integer, Integer[]> expectedMap = (LinkedHashMap<Integer, Integer[]>) tempMapExpected;

                    IntStream.range(1, actualMap.size() + 1)
                            .forEach(i -> {
                                Integer[] actualIntegerArr = actualMap.get(i);
                                Integer[] expectedIntegerArr = expectedMap.get(i);

                                softAssert.assertNotNull(actualIntegerArr, "Actual value at index " + i + " is null.");
                                softAssert.assertNotNull(expectedIntegerArr, "Expected value at index " + i + " is null.");

                                if (actualIntegerArr != null && expectedIntegerArr != null) {
                                    softAssert.assertEquals(actualIntegerArr, expectedIntegerArr, "Values at index " + i + " do not match.");
                                }
                            });
                }

            }
        }   else {
            System.out.println("The instances of actual/expected Objects are not recognized by the assertMethod().");
        }
        boolean allAssertPassed = true;
        try {
            softAssert.assertAll();
        } catch (AssertionError e){
            allAssertPassed = false;
            assertionErrorMessage = e.getMessage();
            System.out.println(e.getMessage());
        }
        System.out.println("The value of the allAssertPassed is: " + allAssertPassed);
        return allAssertPassed;
    }


    protected boolean assertMethod(SoftAssert softAssert, boolean assertTrueOrFalse){
        softAssert.assertTrue(assertTrueOrFalse);
        System.out.println("Assert True Invoked");

        boolean allAssertPassed = true;

        try {
            softAssert.assertAll();
        } catch (AssertionError e){
            allAssertPassed = false;
            assertionErrorMessage = e.getMessage();
            System.out.println(e.getMessage());
        }
        System.out.println("The value of the allAssertPassed is: " + allAssertPassed);
        return allAssertPassed;
    }

    protected boolean assertMethod(SoftAssert softAssert, boolean assertTrueOrFalse, boolean isImagesDifferent){
        System.out.println("Assert True Invoked");

        boolean allAssertPassed = true;
        softAssert.assertTrue(isImagesDifferent);
        softAssert.assertTrue(assertTrueOrFalse);

        try {
            softAssert.assertAll();
        } catch (AssertionError e){
            allAssertPassed = false;
            assertionErrorMessage = e.getMessage();
            System.out.println(e.getMessage());
        }
        System.out.println("The value of the allAssertPassed is: " + allAssertPassed);
        return allAssertPassed;
    }
    //TODO When throw assertion we need to throw the text of Assertion e's getMessage() - DONE

    protected void initializeTestExecutionData(String testName, Instant startTime , By wholeSection, boolean testPassed){
        Instant endTime = Instant.now();
        long testExecutionTime = Duration.between(startTime, endTime).toMillis();
        TestRunner4.testExecutionDataList.add(new TestExecutionData(testName, testExecutionTime, testPassed ? "PASSED" : "FAILED"));
        if (!testPassed) {
            sectionScreenshot.takeDivScreenShot(WebTestTH.driver, wholeSection, testName);
            throw new AssertionError(assertionErrorMessage);
        }
    }

    protected void initializeTestExecutionData(String testName, Instant startTime , By wholeSection, boolean testPassed, boolean isImagesSame){
        Instant endTime = Instant.now();
        isImagesDifferent = true;
        long testExecutionTime = Duration.between(startTime, endTime).toMillis();
        TestRunner4.testExecutionDataList.add(new TestExecutionData(testName, testExecutionTime, testPassed ? "PASSED" : "FAILED"));
        if (!testPassed && isImagesSame) {
            sectionScreenshot.takeDivScreenShot(WebTestTH.driver, wholeSection, testName);
            throw new AssertionError(assertionErrorMessage);
        } else if (!testPassed && !isImagesSame) {
            isImagesDifferent = false;
        }
    }

    protected void initializeTestExecutionData(String testName, Instant startTime, boolean testPassed){
        Instant endTime = Instant.now();
        long testExecutionTime = Duration.between(startTime, endTime).toMillis();
        TestRunner4.testExecutionDataList.add(new TestExecutionData(testName, testExecutionTime, testPassed ? "PASSED" : "FAILED"));
        if (!testPassed) {
            //sectionScreenshot.takeDivScreenShot(WebTestTH.driver, wholeSection, testName);
            throw new AssertionError(assertionErrorMessage);
        }
    }

}
