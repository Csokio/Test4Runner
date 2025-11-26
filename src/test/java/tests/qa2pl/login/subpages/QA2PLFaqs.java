package tests.qa2pl.login.subpages;

import org.example.pages.qa2pl.login.faqs.FAQsPage;
import org.example.pages.qa2pl.login.stations.StationLocatorPage;
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

public class QA2PLFaqs extends WebTestTH {

    FAQsPage faQsPage;
    protected boolean isTestSuiteRun = false;

    protected void uniqueLoginQA2PLIN() {
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

    private void initializeSite() {
        Instant startTime = Instant.now();

        faQsPage = new FAQsPage(driver);

        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if (!isTestSuiteRun) {
            uniqueLoginQA2PLIN();
        }

        faQsPage.goToFAQsPage();
    }

    @Test
    public void testFAQsPageURLQA2PL() {
        initializeSite();

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/faq";

        softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();
    }

    @Test
    public void testFAQsPageValuesQA2PL() {

        initializeSite();

        List<String[]> actualValues = faQsPage.valuesList();

        System.out.println("The size of the Transaction page's valuesList is: " + actualValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            System.out.println(Arrays.toString(actualValues.get(i)));
        });

        List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "16.875px", "rgba(255, 255, 255, 1)"});

        IntStream.range(0, 4).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "16.875px", "rgba(51, 51, 51, 1)"}));
        IntStream.range(0, 9).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "20.625px", "rgba(51, 51, 51, 1)"}));
        IntStream.range(0, 11).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "20.625px", "rgba(64, 64, 64, 1)"}));

        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "22.5px", "rgba(255, 255, 255, 1)"});


        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testFAQsPagePaddingsQA2PL() {

        initializeSite();

        Integer[] actualPaddings = faQsPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                10, 1, 30, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 10, 2, 60, 60, 60, 60,
                60, 60, 60, 60, 60, 60, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
                5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 40, 40, 40,
                40, 40, 40, 40, 40, 40, 40, 10, 10, 1, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                30, 30, 71, 30, 144, 144, 144, 144, 144, 144, 144, 144, 144, 144, 12, 12,
                12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 40, 40,
                40, 40, 40, 40, 40, 40, 40, 40, 70, 70, 70, 70, 70, 10, 60, 63, 60, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, 60
        };

        System.out.println(expectedPaddings.length);

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testFAQsAbsoluteLocationsQA2PL(){

        Instant startTime = Instant.now();

        initializeSite();

        faQsPage.setLocationsList();
        faQsPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = faQsPage.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{74, 70},
                new Integer[]{138, 204},
                new Integer[]{0, 1284},
                new Integer[]{0, 0},
                new Integer[]{155, 1371},
                new Integer[]{470, 1480},
                new Integer[]{975, 1480},
                new Integer[]{173, 399},
                new Integer[]{173, 488},
                new Integer[]{173, 577},
                new Integer[]{173, 666},
                new Integer[]{173, 755},
                new Integer[]{535, 396},
                new Integer[]{535, 485},
                new Integer[]{535, 574},
                new Integer[]{535, 663},
                new Integer[]{535, 752},
                new Integer[]{535, 841},
                new Integer[]{535, 930},
                new Integer[]{535, 1019},
                new Integer[]{535, 1108}
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
    public void testFAQsRelativeLocationsQA2Pl(){
        Instant startTime = Instant.now();

        initializeSite();

        faQsPage.setLocationsList();
        faQsPage.createMapFromLocationsList();


        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = faQsPage.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{64, 134},
                new Integer[]{-138, 1080},
                new Integer[]{0, -1284},
                new Integer[]{155, 1371},
                new Integer[]{315, 109},
                new Integer[]{505, 0},
                new Integer[]{-802, -1081},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{362, -359},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{0, 89},
                new Integer[]{0, 89}
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
    public void testFAQsInteractiveElements() throws InterruptedException {
        Instant startTime = Instant.now();

        initializeSite();

        Assertions.assertTrue(faQsPage.interactiveFAQsElements());
    }


}





