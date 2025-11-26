package tests.qa2pl.login.subpages;

import com.sun.jna.StringArray;
import org.example.pages.qa2pl.login.basket.CatalogPage;
import org.example.pages.qa2pl.login.contact.ContactUsPage;
import org.example.utils.StringArrayKey;
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

public class QA2PLContactUs extends WebTestTH {

    ContactUsPage contactUsPage;
    //protected boolean isTestSuiteRun = false;

    SoftAssert softAssert;

    public QA2PLContactUs(){

    }

    public QA2PLContactUs(SoftAssert softAssert){
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

        contactUsPage = new ContactUsPage(driver);
        //softAssert = new SoftAssert();

        navigateTo(contactUsPage.getUrl());

        if (!isTestSuiteRun) {
            uniqueLoginQA2PLIN();
        }

        contactUsPage.goToContactUsPage();
    }

    @Test
    public void testGoToContactUsPageQA2PL(){

        initializeSite();

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/contact-us";

        softAssert.assertEquals(actualUrl, expectedUrl);
        //softAssert.assertAll();
    }

    @Test
    public void testSaveImageCatalogPageQA2PL(){

        initializeSite();

        String[] expectedImageUrl = {
                "https://lodqa2.wonderline.eu/cdn//b/deriv/e8/c873bbdd81ccc3b57c293d6bcc24ff/40x40.png?info square@2x.png",
                "https://lodqa2.wonderline.eu/cdn//b/orig/7e/3e3f3ca75e17f8d604f4f7b37f0c79/42x42.png?telefon call@2x.png",
                "https://lodqa2.wonderline.eu/cdn//b/deriv/db/59f8e6691048ae03155510533f5bb/26x26.png?contactus_email.png"
        };

        String[] actualImageUrl = contactUsPage.imageUrl().split(",");

        System.out.println("The size of the actualImageUrl is: "  +  actualImageUrl.length);
        for(String imageUrl: actualImageUrl){
            System.out.println(imageUrl);
        }

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(contactUsPage.saveImageToFile());
    }

    @Test
    public void testContactUsValuesQA2PL(){

        initializeSite();

        List<String[]> actualValues = contactUsPage.valuesList();

        System.out.println("The size of the Contact us page's valuesList is: " + actualValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            System.out.println(Arrays.toString(actualValues.get(i)));
        });

        List<String[]> expectedValues = new LinkedList<>();

        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "21.9167px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "12px", "rgba(255, 255, 255, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"});
        IntStream.range(0, 2).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"}));
        IntStream.range(0, 5).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "29.8474px", "rgba(255, 255, 255, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"});
        IntStream.range(0, 3).forEach(i -> expectedValues.addAll(List.of(
                new String[]{"FuturaStd-Heavy, Arial, Verdana", "17.9167px", "rgba(64, 64, 64, 1)"},
                new String[]{"FuturaStd-Book, Arial, Verdana", "17.9167px", "rgba(64, 64, 64, 1)"},
                new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"})));

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
    }

    @Test
    public void testContactUsPaddingsQA2PL(){

        initializeSite();

        Integer[] actualPaddings = contactUsPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                1, 29, 50, 30, 50, 30, 14, 50, 30, 1, 1, 1, 1, 20, 10, 15, 15, 15, 2,
                2, 2, 6, 20, 10, 50, 50, 50, 1, 1, 1, 1, 20, 10, 15, 15, 15, 15, 43,
                43, 43, 20, 71, 50, 50, 50, 50, 10, 10, 10, 50, 50, 50, 50, 50, 50,
                50, 50, 10, 10, 10, 10, 10, 17, 159, 38, 38, 159, 159, 159, 159, 159,
                159, 159
        };

        System.out.println(expectedPaddings.length);
        softAssert.assertEquals(actualPaddings, expectedPaddings);
        //softAssert.assertAll();
        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testContactUsAbsoluteLocationsQA2PL(){
        initializeSite();

        contactUsPage.setLocationsList();
        contactUsPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = contactUsPage.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{74, 70},
                new Integer[]{147, 164},
                new Integer[]{1279, 970},
                new Integer[]{1268, 12},
                new Integer[]{1686, 25},
                new Integer[]{0, 0},
                new Integer[]{0, 0},
                new Integer[]{147, 0},
                new Integer[]{768, -2},
                new Integer[]{959, -2},
                new Integer[]{1341, -2},
                new Integer[]{1532, -2},
                new Integer[]{1532, -2},
                new Integer[]{1279, 970},
                new Integer[]{147, 1202},
                new Integer[]{372, 1344},
                new Integer[]{321, 1366},
                new Integer[]{306, 1473},
                new Integer[]{911, 1344},
                new Integer[]{758, 1366},
                new Integer[]{743, 1473},
                new Integer[]{1353, 1344},
                new Integer[]{1195, 1366},
                new Integer[]{1180, 1473}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

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
    public void testContactUsRelativeLocationsQA2PL(){
        initializeSite();

        contactUsPage.setLocationsList();
        contactUsPage.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = contactUsPage.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{73, 94},
                new Integer[]{1132, 806},
                new Integer[]{-11, -958},
                new Integer[]{418, 13},
                new Integer[]{-1686, -25},
                new Integer[]{0, 0},
                new Integer[]{147, 0},
                new Integer[]{621, -2},
                new Integer[]{191, 0},
                new Integer[]{382, 0},
                new Integer[]{191, 0},
                new Integer[]{0, 0},
                new Integer[]{-253, 972},
                new Integer[]{-1132, 232},
                new Integer[]{225, 142},
                new Integer[]{-51, 22},
                new Integer[]{-15, 107},
                new Integer[]{605, -129},
                new Integer[]{-153, 22},
                new Integer[]{-15, 107},
                new Integer[]{610, -129},
                new Integer[]{-158, 22},
                new Integer[]{-15, 107}
        );

        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

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
    public void testSubmitBtnOnlyPrefilledFrom(){
        initializeSite();

        HashMap<StringArrayKey, String[]> actualMap = contactUsPage.clickSubmitBtnOnlyPreFilledForm();

        for (Map.Entry<StringArrayKey, String[]> entry : actualMap.entrySet()) {
            StringArrayKey key = entry.getKey();
            String[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        HashMap<StringArrayKey, String[]> expectedMap = new LinkedHashMap<>();
        expectedMap.put(new StringArrayKey(new String[]{"1", "3px solid rgb(221, 29, 33)"}), new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "700", "rgba(221, 29, 33, 1)"});
        expectedMap.put(new StringArrayKey(new String[]{"2", "3px solid rgb(221, 29, 33)"}), new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "700", "rgba(221, 29, 33, 1)"});
        expectedMap.put(new StringArrayKey(new String[]{"3", "3px solid rgb(221, 29, 33)"}), new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "700", "rgba(221, 29, 33, 1)"});

        IntStream.range(1, expectedMap.size() + 1)
                .forEach(i -> {
                    String[] actual = actualMap.get(new StringArrayKey(new String[]{String.valueOf(i), "3px solid rgb(221, 29, 33)"}));
                    String[] expected = expectedMap.get(new StringArrayKey(new String[]{String.valueOf(i), "3px solid rgb(221, 29, 33)"}));

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testSubmitBtnInvalidEmailAndCaptchaForm(){
        initializeSite();

        HashMap<StringArrayKey, String[]> actualMap = contactUsPage.clickSubmitBtnInvalidEmailAndCaptcha();

        for (Map.Entry<StringArrayKey, String[]> entry : actualMap.entrySet()) {
            StringArrayKey key = entry.getKey();
            String[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        HashMap<StringArrayKey, String[]> expectedMap = new LinkedHashMap<>();
        expectedMap.put(new StringArrayKey(new String[]{"1", "3px solid rgb(221, 29, 33)"}), new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "700", "rgba(221, 29, 33, 1)"});
        expectedMap.put(new StringArrayKey(new String[]{"2", "3px solid rgb(221, 29, 33)"}), new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "700", "rgba(221, 29, 33, 1)"});
        expectedMap.put(new StringArrayKey(new String[]{"3", "1px solid rgb(166, 166, 166)"}), new String[]{"FuturaStd-Book, Arial, Verdana", "21.9167px", "400", "rgba(64, 64, 64, 1)"});
        expectedMap.put(new StringArrayKey(new String[]{"4", "1px solid rgb(166, 166, 166)"}), new String[]{"FuturaStd-Book, Arial, Verdana", "21.9167px", "400", "rgba(64, 64, 64, 1)"});

        IntStream.range(1, expectedMap.size() + 1)
                .forEach(i -> {
                    String[] actual;
                    String[] expected;
                    if(i == 1 || i == 2){
                        actual = actualMap.get(new StringArrayKey(new String[]{String.valueOf(i), "3px solid rgb(221, 29, 33)"}));
                        expected = expectedMap.get(new StringArrayKey(new String[]{String.valueOf(i), "3px solid rgb(221, 29, 33)"}));
                    } else if (i == 3 || i == 4) {
                        actual = actualMap.get(new StringArrayKey(new String[]{String.valueOf(i), "1px solid rgb(166, 166, 166)"}));
                        expected = expectedMap.get(new StringArrayKey(new String[]{String.valueOf(i), "1px solid rgb(166, 166, 166)"}));
                    } else {
                        actual = null;
                        expected = null;
                    }

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        //softAssert.assertAll();
    }

}
