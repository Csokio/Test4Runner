package tests.qa2pl.login.subpages;

import org.example.pages.qa2pl.login.basket.CatalogPage;
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

public class QA2PLCatalog extends WebTestTH {


    CatalogPage catalogPage;
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

        catalogPage = new CatalogPage(driver);

        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();

        if (!isTestSuiteRun) {
            uniqueLoginQA2PLIN();
        }

        catalogPage.goToCatalogPage();
    }

    @Test
    public void testGoToCatalogPageQA2PL(){

        initializeSite();

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/catalog";

        softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();
    }

    @Test
    public void testSaveImageCatalogPageQA2PL(){

        initializeSite();

        String[] expectedImageUrl = {
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg"
        };

        String[] actualImageUrl = catalogPage.imageUrl().split(",");

        System.out.println("The size of the actualImageUrl is: "  +  actualImageUrl.length);
        for(String imageUrl: actualImageUrl){
            System.out.println(imageUrl);
        }

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(catalogPage.saveImageToFile());
    }

    @Test
    public void testCatalogPageValuesQA2PL(){

        initializeSite();

        List<String[]> actualValues = catalogPage.valuesList();

        System.out.println("The size of the Basket page's valuesList is: " + actualValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            System.out.println(Arrays.toString(actualValues.get(i)));
        });

        List<String[]> expectedValues = new LinkedList<>();
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "21.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "17.9167px", "rgba(255, 255, 255, 1)"});

        IntStream.range(0, 2).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"}));

        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "20px", "rgba(166, 166, 166, 1)"});
        expectedValues.add(new String[]{"Arial", "17.82px", "rgba(166, 166, 166, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "20px", "rgba(64, 64, 64, 1)"});

        if(actualValues.size() > 40){
            IntStream.range(0, 4).forEach(i -> expectedValues.addAll(List.of(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                    new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9167px", "rgba(255, 255, 255, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"})));
            IntStream.range(0, 4).forEach(i -> expectedValues.addAll(List.of(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                    new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9167px", "rgba(255, 255, 255, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"})));
        } else {
            IntStream.range(0, 8).forEach(i -> expectedValues.addAll(List.of(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"},
                    new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9167px", "rgba(255, 255, 255, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"},
                    new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"})));
        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testCatalogPagePaddingsQA2PL(){

        initializeSite();

        Integer[] actualPaddings = catalogPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                15, 15, 20, 7, 20, 3, 20, 5, 29, 29, 29, 29, 21, 2, 8, 8, 8, 8, 7, 4, 8, 8,
                20, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7,
                10, 5, 5, 7, 10, 5, 5, 7, 10, 59, 59, 15, 25, 15, 25, 15, 20, 7, 15, 3, 20, 15,
                15, 15, 22, 15, 15, 22, 15, 9, 24, 8, 8, 15, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20,
                5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15,
                20, 5, 5, 7, 15, 20, 15, 15, 7, 22, 3, 20, 22, 8, 8, 20, 5, 5, 7, 33, 5, 5, 7,
                33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33,
                15, 15, 15, 7, 10, 140, 3, 20, 15, 15, 17, 36, 15, 15, 36, 15, 15, 155, 8, 8, 15,
                5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5,
                5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 36, 15,
                15, 15, 10, 5, 5, 50, -10, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, 8, 5, -10, 115,
                35, 115, 20, 35, 20, 5, 5, 1, 115, 15, 303, 147, -41, 8, 8, 8, 8, 20, 8, 20, 8, 20,
                20, 10, 10, 10, 5, 5, 29, 8, 8, 8, 8, 8, 8, 8, 8, 115, 5, 5, 115, 273, 71, 98, 98,
                -9, 5, -9, 5, 5, 115, 303, 147, -10, -20, 8, 8, 8, 8, 8, 20, 8, 20, 8, 20, 8, 20
        };

        System.out.println(expectedPaddings.length);

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testCatalogPageAbsoluteLocationsQA2Pl(){

        initializeSite();

        catalogPage.setLocationsList();
        catalogPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = catalogPage.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{146, 93},
                new Integer[]{175, 184},
                new Integer[]{1487, 164},
                new Integer[]{1093, 164},
                new Integer[]{1299, 164},
                new Integer[]{156, 286},
                new Integer[]{551, 305},
                new Integer[]{1110, 280},
                new Integer[]{230, 528},
                new Integer[]{205, 562},
                new Integer[]{220, 782},
                new Integer[]{205, 837},
                new Integer[]{610, 528},
                new Integer[]{585, 562},
                new Integer[]{600, 782},
                new Integer[]{585, 837},
                new Integer[]{991, 528},
                new Integer[]{966, 562},
                new Integer[]{981, 782},
                new Integer[]{966, 837},
                new Integer[]{1371, 528},
                new Integer[]{1346, 562},
                new Integer[]{1361, 782},
                new Integer[]{1346, 837},
                new Integer[]{228, 993},
                new Integer[]{203, 1027},
                new Integer[]{218, 1247},
                new Integer[]{203, 1302},
                new Integer[]{333, 1376},
                new Integer[]{608, 993},
                new Integer[]{583, 1027},
                new Integer[]{598, 1247},
                new Integer[]{583, 1302},
                new Integer[]{714, 1376},
                new Integer[]{989, 993},
                new Integer[]{964, 1027},
                new Integer[]{979, 1247},
                new Integer[]{964, 1302},
                new Integer[]{1094, 1376},
                new Integer[]{1369, 993},
                new Integer[]{1344, 1027},
                new Integer[]{1359, 1247},
                new Integer[]{1344, 1302},
                new Integer[]{1474, 1376}
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
    public void testCatalogPageRelativeLocationsQA2PL(){

        initializeSite();

        catalogPage.setLocationsList();
        catalogPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = catalogPage.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{29, 91},
                new Integer[]{1312, -20},
                new Integer[]{-394, 0},
                new Integer[]{206, 0},
                new Integer[]{-1143, 122},
                new Integer[]{395, 19},
                new Integer[]{559, -25},
                new Integer[]{-880, 248},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{405, -309},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{406, -309},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{405, -309},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{-1118, 156},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{130, 74},
                new Integer[]{275, -383},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{131, 74},
                new Integer[]{275, -383},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{130, 74},
                new Integer[]{275, -383},
                new Integer[]{-25, 34},
                new Integer[]{15, 220},
                new Integer[]{-15, 55},
                new Integer[]{130, 74}

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
    public void testInteractiveCatalogPageElements() throws InterruptedException {

        initializeSite();

        HashMap<String, HashMap<Boolean, HashMap<Boolean, Integer>>> actualMap = catalogPage.interactiveCatalogPageElements();
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
        softAssert.assertAll();
    }

    @Test
    public void testCatalogPagePopupValuesQA2PL(){

        initializeSite();

        List<String[]> actualValues = catalogPage.getPopUpValues();

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


        System.out.println("The size of the actual values is: " + actualValues.size());

        System.out.println("The size of the expected values is: " + expectedValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }


    @Test
    public void testCatalogPagePopUpPaddingsQA2PL() {
        initializeSite();

        Integer[] actualPaddings = catalogPage.getPopUpPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29,
                5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29,
                15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5,
                15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5,
                15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5,
                -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15,
                29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31
        };

        System.out.println(expectedPaddings.length);

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testFilteredCategorySize() throws InterruptedException {
        initializeSite();

        Integer actualNumberOfFilteredCategory = catalogPage.categoryFilterOption();
        Integer expectedNumberOfFilteredCategory = 3;

        Assertions.assertEquals(expectedNumberOfFilteredCategory, actualNumberOfFilteredCategory);
    }

    @Test
    public void testSearchedCategorySize() throws InterruptedException {
        initializeSite();

        Integer actualNumberOfSearchedCategory = catalogPage.searchFilterOption();
        Integer expectedNumberOfSearchedCategory = 1;

        Assertions.assertEquals(expectedNumberOfSearchedCategory, actualNumberOfSearchedCategory);
    }

    @Test
    public void testFilterPointsValue() throws InterruptedException {
        initializeSite();

        Integer actualNumberOfFilteredCategory = catalogPage.filterPointsValue();
        Integer expectedNumberOfFilteredCategory = 2;

        Assertions.assertEquals(expectedNumberOfFilteredCategory, actualNumberOfFilteredCategory);
    }
}
