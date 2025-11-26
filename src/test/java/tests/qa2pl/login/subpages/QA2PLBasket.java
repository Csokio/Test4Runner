package tests.qa2pl.login.subpages;

import org.example.pages.qa2pl.login.basket.BasketPage;
import org.example.pages.qa2pl.login.faqs.FAQsPage;
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

public class QA2PLBasket extends WebTestTH {

    private BasketPage basketPage;
    protected boolean isTestSuiteRun = false;

    SoftAssert softAssert;

    /*public QA2PLBasket(){

    }*/

    public QA2PLBasket(SoftAssert softAssert){
        this.softAssert = softAssert;
    }
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
                uuidField.sendKeys("6c4bd68f-bef4-3655-92d8-4e65160d7202");

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

        basketPage = new BasketPage(driver);

        navigateTo(basketPage.getUrl());
        //softAssert = new SoftAssert();

        if (!isTestSuiteRun) {
            uniqueLoginQA2PLIN();
        }

        basketPage.goToBasketPage();
    }

    @Test
    public void testBasketPageURLQA2PL(){

        initializeSite();

        String actualUrl = basketPage.goToBasketPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/basket";

        softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();
    }

    @Test
    public void testBasketPageValuesQA2Pl(){
        initializeSite();

        List<String[]> actualValues = basketPage.valuesList();

        System.out.println("The size of the Basket page's valuesList is: " + actualValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            System.out.println(Arrays.toString(actualValues.get(i)));
        });

        /*List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "59.4449px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "24.8196px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "27.8612px", "rgba(64, 64, 64, 1)"});

        IntStream.range(0, 2).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"}));

        if(actualValues.size() > 40){
            IntStream.range(0, 4).forEach(i -> expectedValues.addAll(List.of(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                    new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9722px", "rgba(255, 255, 255, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"})));
            IntStream.range(0, 4).forEach(i -> expectedValues.addAll(List.of(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                    new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9722px", "rgba(255, 255, 255, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"})));
        } else {
            IntStream.range(0, 8).forEach(i -> expectedValues.addAll(List.of(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                    new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9722px", "rgba(255, 255, 255, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"})));
        }*/

        List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "59.4449px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "24.8196px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "27.8612px", "rgba(64, 64, 64, 1)"});

        IntStream.range(0, 2).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"}));

        if(actualValues.size() < 36){
            IntStream.range(0, 7).forEach(i ->
                    expectedValues.addAll(List.of(
                            new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                            new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9722px", "rgba(255, 255, 255, 1)"},
                            new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                            new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"}
                    )));
        } else {
            IntStream.range(0, 4).forEach(i ->
                    expectedValues.addAll(List.of(
                            new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                            new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9722px", "rgba(255, 255, 255, 1)"},
                            new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                            new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"}
                    )));
            IntStream.range(0, 3).forEach(i ->
                    expectedValues.addAll(List.of(
                            new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                            new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9722px", "rgba(255, 255, 255, 1)"},
                            new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                            new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"},
                            new String[]{"FuturaStd-Book, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"}
                    )));

        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testBasketPagePaddingsQA2PL(){
        initializeSite();

        Integer[] actualPaddings = basketPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                14, 14, 14, 29, 14, 29, 18, 7, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5,
                7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 15,
                7, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7,
                15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 21, 7,
                5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33,
                5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 15, 7, 5, 5,
                7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20,
                5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15,
                5, 20, 5, 5, 7, 15, 5, 20, 20, 21, 20, 36, 9, 5, 9,
                5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 200, 20, 130, 24,
                9, 9, 9, 9, 20, 9, 20, 9, 20, 14, 20, 29, 9, 9, 9, 9,
                9, 9, 9, 9, 91, -2, 20, -24, 9, 9, 9, 9, 9, 20, 9, 20,
                9, 20, 9

        };

        System.out.println(expectedPaddings.length);

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testBasketPageAbsoluteLocationsQA2PL(){
        Instant startTime = Instant.now();

        initializeSite();

        basketPage.setLocationsList();
        basketPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = basketPage.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList;

        if(basketPage.getPointBalance() > 0){
            absoluteLocationsList = Arrays.asList(
                    new Integer[]{0, 144},
                    new Integer[]{121, 434},
                    new Integer[]{1476, 434},
                    new Integer[]{1643, 434},
                    new Integer[]{1672, 434},
                    new Integer[]{193, 513},
                    new Integer[]{168, 547},
                    new Integer[]{183, 767},
                    new Integer[]{168, 822},
                    new Integer[]{575, 513},
                    new Integer[]{550, 547},
                    new Integer[]{565, 767},
                    new Integer[]{550, 822},
                    new Integer[]{957, 513},
                    new Integer[]{932, 547},
                    new Integer[]{947, 767},
                    new Integer[]{932, 822},
                    new Integer[]{1340, 513},
                    new Integer[]{1315, 547},
                    new Integer[]{1330, 767},
                    new Integer[]{1315, 822},
                    new Integer[]{191, 980},
                    new Integer[]{166, 1014},
                    new Integer[]{181, 1234},
                    new Integer[]{166, 1289},
                    new Integer[]{296, 1362},
                    new Integer[]{573, 980},
                    new Integer[]{548, 1014},
                    new Integer[]{563, 1234},
                    new Integer[]{548, 1289},
                    new Integer[]{678, 1362},
                    new Integer[]{955, 980},
                    new Integer[]{930, 1014},
                    new Integer[]{945, 1234},
                    new Integer[]{930, 1289},
                    new Integer[]{1061, 1362}
            );
        } else {
            absoluteLocationsList = Arrays.asList(
                    new Integer[]{0, 144},
                    new Integer[]{121, 434},
                    new Integer[]{1476, 434},
                    new Integer[]{1643, 434},
                    new Integer[]{1661, 434},
                    new Integer[]{193, 513},
                    new Integer[]{168, 547},
                    new Integer[]{183, 767},
                    new Integer[]{168, 822},
                    new Integer[]{575, 513},
                    new Integer[]{550, 547},
                    new Integer[]{565, 767},
                    new Integer[]{550, 822},
                    new Integer[]{957, 513},
                    new Integer[]{932, 547},
                    new Integer[]{947, 767},
                    new Integer[]{932, 822},
                    new Integer[]{1340, 513},
                    new Integer[]{1315, 547},
                    new Integer[]{1330, 767},
                    new Integer[]{1315, 822},
                    new Integer[]{191, 980},
                    new Integer[]{166, 1014},
                    new Integer[]{181, 1234},
                    new Integer[]{166, 1289},
                    new Integer[]{296, 1362},
                    new Integer[]{573, 980},
                    new Integer[]{548, 1014},
                    new Integer[]{563, 1234},
                    new Integer[]{548, 1289},
                    new Integer[]{678, 1362},
                    new Integer[]{955, 980},
                    new Integer[]{930, 1014},
                    new Integer[]{945, 1234},
                    new Integer[]{930, 1289},
                    new Integer[]{1061, 1362}
            );
        }

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
    public void testBasketPageRelativeLocationsQA2PL(){

        Instant startTime = Instant.now();

        initializeSite();

        basketPage.setLocationsList();
        basketPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = basketPage.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{121, 290},
                new Integer[]{1355, 0},
                new Integer[]{167, 0},
                new Integer[]{29, 0},
                new Integer[]{-1479, 79},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{407, -309},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{407, -309},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{408, -309},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},

                new Integer[]{-15, 55},
                new Integer[]{-1124, 158},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{130, 73},
                new Integer[]{277, -382},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{130, 73},
                new Integer[]{277, -382},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{131, 73}
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
    public void testInteractiveBasketPageElementsQA2PL() throws InterruptedException {

        initializeSite();

        HashMap<String, HashMap<Boolean, HashMap<Boolean, Integer>>> actualMap = basketPage.interactiveBasketPageElements();
        HashMap<Boolean, HashMap<Boolean, Integer>> integerBooleanHashMap = actualMap.get("https://lodqa2.wonderline.eu/pl-pl/catalog");

        Integer customerPointBalance;
        boolean isPointBalanceZero;
        if(integerBooleanHashMap.get(true) != null){
            System.out.println("If path invoked");

            HashMap<Boolean, Integer> pointBalanceMapAboveZero = integerBooleanHashMap.get(true);
            customerPointBalance = pointBalanceMapAboveZero.get(true);
            isPointBalanceZero = false;
        } else {
            System.out.println("Else path invoked");

            HashMap<Boolean, Integer> pointBalanceIsZero = integerBooleanHashMap.get(false);
            customerPointBalance = pointBalanceIsZero.get(false);
            if(customerPointBalance == null){
                customerPointBalance = 0;
            }
            isPointBalanceZero = true;
        }

        boolean assertionBoolean = false;
        if(customerPointBalance > 0 && !isPointBalanceZero){
            assertionBoolean = true;
        }
        if(customerPointBalance == 0 && isPointBalanceZero){
            assertionBoolean = true;
        }

        System.out.println("Customer's point balance is: " + customerPointBalance);
        System.out.println("Customer has 0 points: " + isPointBalanceZero);

        softAssert.assertTrue(assertionBoolean);
        //softAssert.assertAll();
    }

    @Test
    public void testBasketPagePopUpValuesQA2PL(){
        initializeSite();

        List<String[]> actualValues = basketPage.getPopUpValues();

        System.out.println("The size of the Basket page's valuesList is: " + actualValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            System.out.println(Arrays.toString(actualValues.get(i)));
        });

        List<String[]> expectedValues = new ArrayList<String[]>();

        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});



        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});


        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});


        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});
        /*expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});*/


        System.out.println("The size of the actual values is: " + actualValues.size());

        System.out.println("The size of the expected values is: " + expectedValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testBasketPagePopUpPaddingsQA2PL(){
        initializeSite();

        Integer[] actualPaddings = basketPage.getPopUpPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = new Integer[]{
                15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5,
                5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15,
                5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15,
                5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15,
                5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15,
                31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31
        };

        System.out.println(expectedPaddings.length);

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }
}
