package tests.qa2pl.login.subpages;
import org.example.pages.qa2pl.login.stations.StationLocatorPage;
import org.example.pages.qa2pl.login.transactions.TransactionPage;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import tests.WebTestTH;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

public class QA2PLStation extends WebTestTH {
    StationLocatorPage stationLocatorPage;
    protected boolean isTestSuiteRun = false;
    protected void uniqueLoginQA2PLIN(){
        /*
            driver.findElement(By.id("uuid")).clear();
            driver.findElement(By.id("uuid")).sendKeys("6c4bd68f-bef4-3655-92d8-4e65160d7202");
            driver.findElement(By.xpath("//input[@name=\"return\"]")).click();*/
        if (!isTestSuiteRun) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uuid")));

                WebElement uuidField = driver.findElement(By.id("uuid"));
                uuidField.clear();
                uuidField.sendKeys("5e4c3796-99f3-3527-9e64-fee00808d6c7");

                driver.findElement(By.xpath("//input[@name=\"return\"]")).click();
            } catch (TimeoutException e) {
                System.out.println("UUID field was not found within the timeout: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void initializeSite(){
        Instant startTime = Instant.now();

        stationLocatorPage = new StationLocatorPage(driver);

        navigateTo(stationLocatorPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        stationLocatorPage.goToStationLocatorPage();
    }

    @Test
    public void testStationLocatorPageURLQA2PL(){
        Instant startTime = Instant.now();

        StationLocatorPage stationLocatorPage = new StationLocatorPage(driver);
        navigateTo(stationLocatorPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String actualUrl = stationLocatorPage.goToStationLocatorPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/station-locator";

        softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();
    }


    @Test
    public void testStationValuesQA2PL(){

        initializeSite();

        List<String[]> actualValues = stationLocatorPage.valuesList();

        System.out.println("The size of the Transaction page's valuesList is: " + actualValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            System.out.println(Arrays.toString(actualValues.get(i)));
        });

        List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "16px", "rgba(89, 89, 89, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "29.7918px", "rgba(255, 255, 255, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "29.7918px", "rgba(51, 51, 51, 1)"});

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testStationPaddingsQA2PL(){

        initializeSite();

        Integer[] actualPaddings = stationLocatorPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(System.out::println);

        Integer[] expectedPaddings = {
                10, 20, 24, 20, 10, 15, 15, 15, 10, 20, 24, 20, 20, 141,
                71, 15, 15, 15, 145, 145, 147, 145, 145, 147
        };

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testStationAbsoluteLocationsQA2PL(){

        initializeSite();

        stationLocatorPage.setLocationsList();
        stationLocatorPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = stationLocatorPage.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{768, 0},
                new Integer[]{74, 70},
                new Integer[]{160, 144},
                new Integer[]{160, 251},
                new Integer[]{147, 1019}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testStationRelativeLocationsQA2PL(){

        initializeSite();

        stationLocatorPage.setLocationsList();
        stationLocatorPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = stationLocatorPage.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{-694, 70},
                new Integer[]{86, 74},
                new Integer[]{0, 107},
                new Integer[]{-13, 768}
        );

        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testStationAppPopupQA2PL() throws InterruptedException {

        initializeSite();

        boolean isInteract = false;

        Map<Boolean, Map<Boolean, List<String>>> actualMap = stationLocatorPage.downloadAppInteraction();
        Map<Boolean, List<String>> listBooleanMap = actualMap.get(true);
        if(listBooleanMap == null){
            softAssert.assertTrue(false);
            softAssert.assertAll();
        }

        List<String> actualValuesOfPopup = listBooleanMap.get(true);
        if(actualValuesOfPopup == null){
            softAssert.assertTrue(false);
            softAssert.assertAll();
        }

        String[] popupValuesArray = actualValuesOfPopup.toArray(String[]::new);

        Arrays.stream(popupValuesArray).forEach(System.out::println);

        isInteract = true;
        softAssert.assertTrue(isInteract);
        softAssert.assertAll();
    }

}
