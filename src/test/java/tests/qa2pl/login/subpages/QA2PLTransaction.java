package tests.qa2pl.login.subpages;

import org.example.pages.qa2pl.login.landing.CatalogueSection;
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

public class QA2PLTransaction extends WebTestTH {
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

    @Test
    public void testTransactionPageURLQA2PL(){
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);
        navigateTo(transactionPage.getUrl());

        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String actualUrl = transactionPage.goToTransactionPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/account/transactions";

        softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();
    }

    @Test
    public void testTransactionFilterQA2PL() throws InterruptedException {
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);
        navigateTo(transactionPage.getUrl());

        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        softAssert.assertTrue(transactionPage.transactionFilter());
        softAssert.assertAll();
    }

    @Test
    public void testDownloadPdfQA2PL(){
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);
        navigateTo(transactionPage.getUrl());

        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        softAssert.assertEquals(transactionPage.clickDownloadPdf(), "https://lodqa2.wonderline.eu/pl-pl/account/transactions/pdf");
        softAssert.assertAll();
    }

    @Test
    public void testTransactionPageValuesQA2PL(){
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);
        navigateTo(transactionPage.getUrl());

        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        List<String[]> actualValues = transactionPage.valuesList();

        System.out.println("The size of the Transaction page's valuesList is: " + actualValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            System.out.println(Arrays.toString(actualValues.get(i)));
        });

        List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.9306px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.9306px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "20px", "rgba(89, 89, 89, 1)"});

        IntStream.range(0,9).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        IntStream.range(0,3).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(51, 51, 51, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "21.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});

        IntStream.range(0,5).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9167px", "rgba(64, 64, 64, 1)"}));

        IntStream.range(0,3).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});

        IntStream.range(0,3).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});

        IntStream.range(0,3).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});

        IntStream.range(0,3).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});

        IntStream.range(0,3).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(221, 29, 33, 1)"});

        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testTransactionPagePaddings(){
        Instant startTime = Instant.now();
        TransactionPage transactionPage = new TransactionPage(driver);

        navigateTo(transactionPage.getUrl());

        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        transactionPage.goToTransactionPage();

        Integer[] actualPaddings = transactionPage.getPaddings();
        Integer[] expectedPaddings = {
                10, 1, 20, 10, 29, 20, 14, 20, 20, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29,
                29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 49, 10, 15, 15, 15, 20, 20, 20, 20, 20,
                20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 10, 20, 10, 15, 15, 14, 15, 15, 15, 15, 15, 15,
                15, 15, 15, 15, 15, 15, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10
                , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 6, 20, 20, 109, 71, 15, 15, 10,
                10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 1, 20, 15, 10, 15, 10, 15, 11,
                20, 15, 15, 5, -3, 2, 14, -3, 2, -3, 2, -3, 2, -3, 2, 147, 147, 147, 30, 147, 147, 10, 147
        };

        for (Integer padding : actualPaddings) {
            System.out.println(padding);
        }

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testTransactionPageAbsoluteLocationsQA2PL(){
        Instant startTime = Instant.now();
        TransactionPage transactionPage = new TransactionPage(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        transactionPage.setLocationsList();
        transactionPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = transactionPage.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{146, 93},
                new Integer[]{1425, 234},
                new Integer[]{167, 234},
                new Integer[]{0, 0},
                new Integer[]{271, 576},
                new Integer[]{643, 576},
                new Integer[]{915, 576},
                new Integer[]{1107, 576},
                new Integer[]{0, 0},
                new Integer[]{162, 486},
                new Integer[]{271, 486},
                new Integer[]{662, 486},
                new Integer[]{927, 486},
                new Integer[]{1115, 486},
                new Integer[]{1380, 486}
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
    public void testTransactionPageRelativeLocationsQA2PL(){
        Instant startTime = Instant.now();
        TransactionPage transactionPage = new TransactionPage(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        transactionPage.setLocationsList();
        transactionPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = transactionPage.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{1279, 141},
                new Integer[]{-1258, 0},
                new Integer[]{-167, -234},
                new Integer[]{271, 576},
                new Integer[]{372, 0},
                new Integer[]{272, 0},
                new Integer[]{192, 0},
                new Integer[]{-1107, -576},
                new Integer[]{162, 486},
                new Integer[]{109, 0},
                new Integer[]{391, 0},
                new Integer[]{265, 0},
                new Integer[]{188, 0},
                new Integer[]{265, 0}
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

}
