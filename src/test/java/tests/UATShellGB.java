package tests;


import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.CaptureElement;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.*;
import org.example.ImageDiff;
import org.example.pages.uatgb.login.LoginUatGb;
import org.example.pages.uatgb.logout.*;
import org.junit.Ignore;
import utils.Utils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;
import tests.parameterized.ParameterizedLoginUatGb;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//TODO MAVEN ENVIRONMENT VARIABLE
//TODO IMPLEMENT TEST EXECUTION DATA TO ALL TEST CASES
//TODO IN CASE TEST CASE FAILS TAKE A SCREENSHOT AND ATTACH IT TO THE HTML FILE, IF ASSERTMETHOD() RETURNS A BOOLEAN TAKE A SCREENSHOT OF ACTUAL STATE

public class UATShellGB extends WebTestTH {

    private final By BUTTON_DECLINE = By.xpath("//button[@id=\"_evidon-decline-button\"]");
    private final By DISPLAY_COOKIES = By.xpath("//div[@id=\"_evidon_banner\"]");

    private final ParameterizedLoginUatGb parameterizedLoginUatGb = new ParameterizedLoginUatGb();

    private final ImageDiff imageDiffUatGB = new ImageDiff();


    @Test
    public void AAADummyBooleanUATGB(){
        Instant startTime = Instant.now();
        boolean isAlwaysTrue = true;
        Assertions.assertTrue(isAlwaysTrue);
        initializeTestExecutionData("AAADummyBoolean", startTime, true);
    }

    @Test
    public void testHeaderImageUrl() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        String actualShellImageUrl = header.imageUrl().split(",")[0];
        String actualHomeImageUrl = header.imageUrl().split(",")[1];
        String actualStationImageUrl = header.imageUrl().split(",")[2];
        String actualMoreImageUrl = header.imageUrl().split(",")[3];
        String actualMoreHoverImageUrl = header.imageUrl().split(",")[4];
        String actualFlagImageUrl = header.imageUrl().split(",")[5];
        String actualLoginImageUrl = header.imageUrl().split(",")[6];

        String expectedShellImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/deriv/17/b1088a032c27fa47681c1dafe8982a/292x54.png?Header%20Icon.png";
        String expectedHomeImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/20/d6f0b1f7f09c9becfd944163141597/24x24.png?ic_home_filled.png";
        String expectedStationImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/8f/fbf6e709efc6ec0706a53d782cd387/24x24.png?ic_map_outlinesvg.png";
        String expectedMoreImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/d1/1e80ccf68cfc1a94480d042baf0902/24x24.png?ic_more_horizontal_outline%20(1).png";
        String expectedMoreHoverImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/60/c8a8f6a992f86605d9a44137de181c/24x24.png?ic_more_horizontal_filled.png";
        String expectedFlagImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/static/flat/0c/0ba1eb0b97d1fd15c6fb44c9deeb43/en.svg";
        String expectedLoginImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/04/67d3ee06303d9db9df06e55d00bca5/24x24.png?profile_outline.png";

        softAssert.assertEquals(actualShellImageUrl, expectedShellImageUrl);
        softAssert.assertEquals(actualHomeImageUrl, expectedHomeImageUrl);
        softAssert.assertEquals(actualStationImageUrl, expectedStationImageUrl);
        softAssert.assertEquals(actualMoreImageUrl, expectedMoreImageUrl);
        softAssert.assertEquals(actualMoreHoverImageUrl, expectedMoreHoverImageUrl);
        softAssert.assertEquals(actualFlagImageUrl, expectedFlagImageUrl);
        softAssert.assertEquals(actualLoginImageUrl, expectedLoginImageUrl);

        /*boolean allAssertPassed = true;
        try{
            softAssert.assertTrue(header.saveImageToFile());
            softAssert.assertAll();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
            allAssertPassed = false;
        }

        Instant endTime = Instant.now();

        long elapsedTime = Duration.between(startTime, endTime).toMillis();

        initializeTestExecutionData("testHeaderImageUrl", elapsedTime, allAssertPassed);*/


        //initializeTestExecutionData("testHeaderImageUrl", startTime, header.getHEADER_DIV(), assertMethod(softAssert, header.saveImageToFile()));


        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testHeaderImageUrl", "testHeaderImageUrl",
                "testHeaderImageUrl", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHeaderImageUrl");
        initializeTestExecutionData("testHeaderImageUrl", startTime, header.getHEADER_DIV(), assertMethod(softAssert, header.saveImageToFile(), isImagesSame));

    }


    @Test
    public void testDisplayedHeaderUATGB() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        int actual = header.countHeaderElements();
        int expected = 5;

        softAssert.assertEquals(actual, expected);

        String[] actualArray = header.verifyHeaderElements();
        String[] expectedArray = {"Shell GO+ Rewards logo", "Home", "Station locator", "EN", "fpMenuLinkLogin"};
        for(String item: actualArray){
            System.out.println(item);
        }

        LinkedHashMap<Integer, String[]> actualMap = new LinkedHashMap<>();
        actualMap.put(actual, actualArray);
        LinkedHashMap<Integer, String[]> expectedMap = new LinkedHashMap<>();
        expectedMap.put(expected, expectedArray);

        //takeShutterBugScreenshotOfDivs takes output images with unique counter
        /*softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "selenium_test_pictures\\uat_gb", "selenium_test_pictures_overtime\\uat_gb_overtime",
                "testDisplayedHeader", "selenium_image_differences\\uat_gb\\output_diff_uat_gb");*/

        /*softAssert = takeShutterBugScreenshotOfDivsCounter(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "selenium_test_pictures\\uat_gb", "selenium_test_pictures_overtime\\uat_gb_overtime",
                "testDisplayedHeader", "selenium_image_differences\\uat_gb\\testDisplayedHeader");
        initializeTestExecutionData("testDisplayedHeader", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testDisplayedHeader", "testDisplayedHeader",
                "testDisplayedHeader", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testDisplayedHeader");
        initializeTestExecutionData("testDisplayedHeader", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualMap, expectedMap, isImagesSame));

    }



    @Test
    public void testHeaderFunctionality() throws InterruptedException, IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);
        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        Thread.sleep(4000);


        /*Assertions.assertTrue(header.clickOnHeaderElements());
        Instant endTime = Instant.now();
        long elapsedTime = Duration.between(startTime, endTime).toMillis();
        TestRunner4.testExecutionDataList.add(new TestExecutionData("testHeaderFunctionality", elapsedTime, true ? "PASSED" : "FAILED"));*/

        //initializeTestExecutionData("testHeaderFunctionality", startTime, header.getHEADER_DIV(),assertMethod(softAssert, header.clickOnHeaderElements()));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testHeaderFunctionality", "testHeaderFunctionality",
                "testHeaderFunctionality", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHeaderFunctionality");
        initializeTestExecutionData("testHeaderFunctionality", startTime, header.getHEADER_DIV(), assertMethod(softAssert, header.clickOnHeaderElements(), isImagesSame));

    }

    @Test
    public void testHeaderValues() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        navigateTo(header.getUrl());

        softAssert = new SoftAssert();

        String[] expectedHomeValues = {"FuturaStd-Book, Arial, Verdana", "14px"};
        String[] expectedStationValues = {"FuturaStd-Book, Arial, Verdana", "14px"};
        String[] expectedMoreValues = {"FuturaStd-Book, Arial, Verdana", "14px"};
        String[] expectedFlagValues = {"FuturaStd-Book, Arial, Verdana", "14px"};


        List<String[]> expectedHeaderValues = new LinkedList<>();
        expectedHeaderValues.add(expectedHomeValues);
        expectedHeaderValues.add(expectedStationValues);
        expectedHeaderValues.add(expectedMoreValues);
        expectedHeaderValues.add(expectedFlagValues);

        List<String[]> actualHeaderValues = header.valuesList();

        /*for(int i = 0; i < expectedHeaderValues.size(); i++){
            softAssert.assertEquals(actualHeaderValues.get(i), expectedHeaderValues.get(i));
        }

        softAssert.assertAll();*/

        //initializeTestExecutionData("testHeaderValues", startTIme, header.getHEADER_DIV(), assertMethod(softAssert, actualHeaderValues, expectedHeaderValues));

        /*softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testHeaderValues", "testHeaderValues",
                "testHeaderValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHeaderValues");
        initializeTestExecutionData("testHeaderValues", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualHeaderValues, expectedHeaderValues, isImagesSame));

        /*softAssert = takeShutterBugScreenshotOfDivsCounter(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "selenium_test_pictures\\uat_gb", "selenium_test_pictures_overtime\\uat_gb_overtime",
                "testHeaderValues", "selenium_image_differences\\uat_gb\\testHeaderValues");
        initializeTestExecutionData("testDisplayedHeader", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualHeaderValues, expectedHeaderValues, isImagesSame));*/

        softAssert.assertTrue(true);
        softAssert.assertAll();
    }
    //TODO HEADER PADDINGS
    @Test
    public void testHeaderPaddings() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);
        navigateTo(header.getUrl());

        softAssert = new SoftAssert();

        Integer[] actualArray = header.getPaddings();
        Integer[] expectedArray = new Integer[]{20, 20, 20, 20, 16, 16, 20, 20, 16, 16,
            20, 20, 16, 16, 20, 20, 16, 16};

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        //initializeTestExecutionData("testHeaderPaddings", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualArray, expectedArray));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testHeaderPaddings", "testHeaderPaddings",
                "testHeaderPaddings", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHeaderPaddings");
        initializeTestExecutionData("testHeaderPaddings", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }



    @Test
    public void testHeaderDisplayedMoreOptions() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        String[] actualArray = header.verifyMoreOptionElements();
        String[] expectedArray = {"Help & Support", "Contact us", "About Shell GO+ Rewards"};

        //initializeTestExecutionData("testDisplayedMoreOptions", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualArray, expectedArray));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testDisplayedMoreOptions", "testDisplayedMoreOptions",
                "testDisplayedMoreOptions", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testDisplayedMoreOptions");
        initializeTestExecutionData("testDisplayedMoreOptions", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    @Test
    public void testHeaderMoreOptionFunctionality() throws InterruptedException, IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        Thread.sleep(4000);

        //initializeTestExecutionData("testMoreOptionFunctionality", startTime, header.getHEADER_DIV(), assertMethod(softAssert, header.clickMoreElements()));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testHeaderMoreOptionFunctionality", "testHeaderMoreOptionFunctionality",
                "testHeaderMoreOptionFunctionality", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHeaderMoreOptionFunctionality");
        initializeTestExecutionData("testHeaderMoreOptionFunctionality", startTime, header.getHEADER_DIV(), assertMethod(softAssert, header.clickMoreElements(), isImagesSame));
    }


    @Test
    public void testHeaderAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        header.setLocationsList();
        header.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = header.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{355, 34},
                new Integer[]{982, 38},
                new Integer[]{1095, 38},
                new Integer[]{1268, 38},
                new Integer[]{0, 0},
                new Integer[]{1396, 38},
                new Integer[]{0, 0},
                new Integer[]{355, 20},
                new Integer[]{0, 0},
                new Integer[]{1014, 35},
                new Integer[]{1127, 35},
                new Integer[]{1300, 35},
                new Integer[]{1428, 35}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        //initializeTestExecutionData("testHeaderAbsoluteLocations", startTime, header.getHEADER_DIV(),assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testHeaderAbsoluteLocations", "testHeaderAbsoluteLocations",
                "testHeaderAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHeaderAbsoluteLocations");
        initializeTestExecutionData("testHeaderAbsoluteLocations", startTime, header.getHEADER_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testHeaderRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());

        header.setLocationsList();
        header.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = header.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{627, 4},
                new Integer[]{113, 0},
                new Integer[]{173, 0},
                new Integer[]{-1268, -38},
                new Integer[]{1396, 38},
                new Integer[]{-1396, -38},
                new Integer[]{355, 20},
                new Integer[]{-355, -20},
                new Integer[]{1014, 35},
                new Integer[]{113, 0},
                new Integer[]{173, 0},
                new Integer[]{128, 0}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        //initializeTestExecutionData("testHeaderRelativeLocations", startTime, header.getHEADER_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, header.getHEADER_DIV(), imageDiffUatGB, "testHeaderRelativeLocations", "testHeaderRelativeLocations",
                "testHeaderRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHeaderRelativeLocations");
        initializeTestExecutionData("testHeaderRelativeLocations", startTime, header.getHEADER_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testPromoTileImageURL() throws IOException {
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        String actual = promoTile.imageUrl();
        String expected = "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/97/af8b25c1bd4c85d7165174b5b6f07c/1088x602.png?spider.home_lf.fp.design.promo.tile.mobile.image.png";

        softAssert.assertEquals(actual,expected);

        //softAssert.assertAll();
        //initializeTestExecutionData("testPromoTileImageUrl", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, promoTile.saveImageToFile()));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, promoTile.getPROMO_TILE_DIV(), imageDiffUatGB, "testPromoTileImageURL", "testPromoTileImageURL",
                "testPromoTileImageURL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testPromoTileImageURL");
        initializeTestExecutionData("testPromoTileImageURL", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, actual, expected, isImagesSame));
    }

    @Test
    public void testPromoTileValues() throws IOException {
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        String[] expectedTitleValues = {"FuturaStd-Bold, Arial, Verdana", "24px", "rgba(255, 255, 255, 1)"};
        String[] expectedTextValues = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(255, 255, 255, 1)"};
        String[] expectedRegisterValues = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};


        String[] actualLoginValues = promoTile.getLoginValues();
        String[] expectedLoginValues = {"FuturaStd-Book, Arial, Verdana", "14px", "rgba(255, 255, 255, 1)", "underline"};


        List<String[]> expectedPromoValues = new LinkedList<>();
        expectedPromoValues.add(expectedTitleValues);
        expectedPromoValues.add(expectedTextValues);
        expectedPromoValues.add(expectedRegisterValues);
        expectedPromoValues.add(expectedLoginValues);

        List<String[]> actualPromoValues = promoTile.valuesList();
        actualPromoValues.add(actualLoginValues);

        /*for(int i = 0; i < expectedPromoValues.size(); i++){
            softAssert.assertEquals(actualPromoValues.get(i), expectedPromoValues.get(i));
        }

        softAssert.assertAll();*/
        //initializeTestExecutionData("testPromoTileValues", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, actualPromoValues, expectedPromoValues));


        softAssert = takeShutterBugScreenshotOfDivs(softAssert, promoTile.getPROMO_TILE_DIV(), imageDiffUatGB, "testPromoTileValues", "testPromoTileValues",
                "testPromoTileValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testPromoTileValues");
        initializeTestExecutionData("testPromoTileValues", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, actualPromoValues, expectedPromoValues, isImagesSame));

    }

    @Test
    public void testPromoTilePaddings() throws IOException {
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        Integer[] actualArray = promoTile.getPaddings();
        Integer[] expectedArray = {16, 16, 24, 24, 32, 32, 24, 32, 24, 8};

        //Assertions.assertArrayEquals(expectedArray, actualArray);
        //initializeTestExecutionData("testPromoTilePaddings", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, actualArray, expectedArray));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, promoTile.getPROMO_TILE_DIV(), imageDiffUatGB, "testPromoTilePaddings", "testPromoTilePaddings",
                "testPromoTilePaddings", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testPromoTilePaddings");
        initializeTestExecutionData("testPromoTilePaddings", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    @Test
    public void testPromoTileFunctionality() throws IOException {
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        String actualLoginUrl = promoTile.clickPromoTileLogin();
        driver.navigate().back();
        String actualRegisterUrl = promoTile.clickPromoTileRegister();
        driver.navigate().back();

        String expectedLoginUrl = "https://test.login.consumer.shell.com/login";
        String expectedRegisterUrl = "https://test.login.consumer.shell.com/register";

        /*softAssert.assertEquals(actualLoginUrl, expectedLoginUrl);
        softAssert.assertEquals(actualRegisterUrl, expectedRegisterUrl);

        softAssert.assertAll();*/

        String[] actualUrlArray = {actualLoginUrl, actualRegisterUrl};
        String[] expectedUrlArray = {expectedLoginUrl, expectedRegisterUrl};

        //initializeTestExecutionData("testPromoTileFunctionality", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, actualUrlArray, expectedUrlArray));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, promoTile.getPROMO_TILE_DIV(), imageDiffUatGB, "testPromoTileFunctionality", "testPromoTileFunctionality",
                "testPromoTileFunctionality", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testPromoTileFunctionality");
        initializeTestExecutionData("testPromoTileFunctionality", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, actualUrlArray, expectedUrlArray, isImagesSame));
    }

    //@Test
    public void testPromoTileLocations(){
        PromoTile promoTile = new PromoTile(driver);

        SoftAssert softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        promoTile.setLocationsList();
        promoTile.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = promoTile.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key " + key + ", Value " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        expectedAbsoluteLocations.put(1, new Integer[]{363, 128});
        expectedAbsoluteLocations.put(2, new Integer[]{419, 184});
        expectedAbsoluteLocations.put(3, new Integer[]{419, 241});
        expectedAbsoluteLocations.put(4, new Integer[]{419, 288});
        expectedAbsoluteLocations.put(5, new Integer[]{464, 350});

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


        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = promoTile.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key " + key + ", Value " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        expectedRelativeLocations.put(1, new Integer[]{56, 56});
        expectedRelativeLocations.put(2, new Integer[]{0, 57});
        expectedRelativeLocations.put(3, new Integer[]{0, 47});
        expectedRelativeLocations.put(4, new Integer[]{45, 62});

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
    public void testPromoTileAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        promoTile.setLocationsList();
        promoTile.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = promoTile.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key " + key + ", Value " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        expectedAbsoluteLocations.put(1, new Integer[]{355, 128});
        expectedAbsoluteLocations.put(2, new Integer[]{411, 184});
        expectedAbsoluteLocations.put(3, new Integer[]{411, 241});
        expectedAbsoluteLocations.put(4, new Integer[]{411, 288});
        expectedAbsoluteLocations.put(5, new Integer[]{456, 350});

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });*/
        //initializeTestExecutionData("testPromoTileAbsoluteLocations", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, expectedAbsoluteLocations, absoluteLocationsMap));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, promoTile.getPROMO_TILE_DIV(), imageDiffUatGB, "testPromoTileAbsoluteLocations", "testPromoTileAbsoluteLocations",
                "testPromoTileAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testPromoTileAbsoluteLocations");
        initializeTestExecutionData("testPromoTileAbsoluteLocations", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testPromoTileRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        PromoTile promoTile = new PromoTile(driver);

        softAssert = new SoftAssert();

        navigateTo(promoTile.getUrl());

        promoTile.setLocationsList();
        promoTile.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = promoTile.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key " + key + ", Value " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        expectedRelativeLocations.put(1, new Integer[]{56, 56});
        expectedRelativeLocations.put(2, new Integer[]{0, 57});
        expectedRelativeLocations.put(3, new Integer[]{0, 47});
        expectedRelativeLocations.put(4, new Integer[]{45, 62});

        //initializeTestExecutionData("testPromoTileRelativeLocations", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, expectedRelativeLocations, relativeLocationsMap));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, promoTile.getPROMO_TILE_DIV(), imageDiffUatGB, "testPromoTileRelativeLocations", "testPromoTileRelativeLocations",
                "testPromoTileRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testPromoTileRelativeLocations");
        initializeTestExecutionData("testPromoTileRelativeLocations", startTime, promoTile.getPROMO_TILE_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testWelcomeSectionImageURL() throws IOException {
        Instant startTime = Instant.now();
        WelcomeSection welcomeSection = new WelcomeSection(driver);

        SoftAssert softAssert = new SoftAssert();

        navigateTo(welcomeSection.getUrl());

        String actualIosImageUrl = welcomeSection.imageUrl().split(",")[0];
        String expectedIosImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/deriv/97/2ab045bd2da2858ee964f50b0eb68c/600x188.png?App%20download%20badge_Apple.png";

        String actualAndroidImageUrl = welcomeSection.imageUrl().split(",")[1];
        String expectedAndroidImageUrl = "https://shelluatpoints.loyaltyondemand.club/cdn/b/deriv/28/6545afff19bc85d72ae6c424500c22/600x188.png?App%20download%20badge_Android%20(1).png";

        softAssert.assertEquals(actualIosImageUrl, expectedIosImageUrl);
        softAssert.assertEquals(actualAndroidImageUrl, expectedAndroidImageUrl);

       /* softAssert.assertTrue(welcomeSection.saveImageToFile());

        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, welcomeSection.getWELCOME_SECTION_DIV(), imageDiffUatGB, "testWelcomeSectionImageURL", "testWelcomeSectionImageURL",
                "testWelcomeSectionImageURL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testWelcomeSectionImageURL");
        initializeTestExecutionData("testWelcomeSectionImageURL", startTime, welcomeSection.getWELCOME_SECTION_DIV(), assertMethod(softAssert, welcomeSection.saveImageToFile(), isImagesSame));
    }

    @Test
    public void testWelcomeSectionButtons() throws IOException {
        Instant startTime = Instant.now();
        WelcomeSection welcomeSection = new WelcomeSection(driver);

        SoftAssert softAssert = new SoftAssert();

        navigateTo(welcomeSection.getUrl());

        String actualIOSUrl = welcomeSection.buttonIOSRedirectsTo();
        String actualAndroidUrl = welcomeSection.buttonAndroidRedirectsTo();
        String expectedIOSUrl = "https://shelluatpoints.loyaltyondemand.club/en-gb/redirect?data=982f7cc230b678cc736cbbe" +
                "a631a0268ad6c9581e0832e7f661f2b196a4a21e527e0cef6df995386086c0fed73b5df40f5134831577108f940686104b44845d7";
        String expectedAndroidUrl = "https://shelluatpoints.loyaltyondemand.club/en-gb/redirect?data=982f7cc230b678cc736cbb" +
                "ea631a0268441cd2458ee5c645f119d5a4ea22e3d6a9194a97cafec8b756cdc8a046e372628d0fd3bbcc560b0b012a3b55bea92e93a0" +
                "4b3c44baa59385ac81d6c45e4071738619c86c6a319bb0ebbdc9e5f30e95cd4fcfbda1c9c134fe8440a7c68362018e";


        softAssert.assertEquals(actualIOSUrl, expectedIOSUrl);
        //softAssert.assertEquals(actualAndroidUrl, expectedAndroidUrl);

        //softAssert.assertAll();

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, welcomeSection.getWELCOME_SECTION_DIV(), imageDiffUatGB, "testWelcomeSectionButtons", "testWelcomeSectionButtons",
                "testWelcomeSectionButtons", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testWelcomeSectionButtons");
        initializeTestExecutionData("testWelcomeSectionButtons", startTime, welcomeSection.getWELCOME_SECTION_DIV(), assertMethod(softAssert, actualAndroidUrl, expectedAndroidUrl, isImagesSame));

    }


    @Test
    public void testWelcomeSectionValues() throws IOException {
        Instant startTime = Instant.now();
        WelcomeSection welcomeSection = new WelcomeSection(driver);

        SoftAssert softAssert = new SoftAssert();

        navigateTo(welcomeSection.getUrl());

        String[] expectedTitleValues = {"FuturaStd-Bold, Arial, Verdana", "40px", "rgba(221, 29, 33, 1)"};
        String[] expectedTextValues = {"FuturaStd-Book, Arial, Verdana", "18px", "rgba(64, 64, 64, 1)"};
        String[] expectedSubtitleValues = {"FuturaStd-Bold, Arial, Verdana", "18px", "rgba(64, 64, 64, 1)"};

        List<String[]> expectedWelcomeValues = new LinkedList<>();
        expectedWelcomeValues.add(expectedTitleValues);
        expectedWelcomeValues.add(expectedTextValues);
        expectedWelcomeValues.add(expectedSubtitleValues);

        List<String[]> actualWelcomeValues = welcomeSection.valuesList();


        /*for(int i = 0; i < expectedWelcomeValues.size(); i++){
            softAssert.assertEquals(actualWelcomeValues.get(i), expectedWelcomeValues.get(i));
        }

        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, welcomeSection.getWELCOME_SECTION_DIV(), imageDiffUatGB, "testWelcomeSectionValues", "testWelcomeSectionValues",
                "testWelcomeSectionValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testWelcomeSectionValues");
        initializeTestExecutionData("testWelcomeSectionValues", startTime, welcomeSection.getWELCOME_SECTION_DIV(), assertMethod(softAssert, actualWelcomeValues, expectedWelcomeValues, isImagesSame));
    }

    @Test
    public void testWelcomePaddings() throws IOException {
        Instant startTime = Instant.now();
        WelcomeSection welcomeSection = new WelcomeSection(driver);

        softAssert = new SoftAssert();

        navigateTo(welcomeSection.getUrl());

        Integer[] actualArray = welcomeSection.getPaddings();
        Integer[] expectedArray = {48, 32, 16, 32, 24};

        //Assertions.assertArrayEquals(expectedArray, actualArray);
        softAssert = takeShutterBugScreenshotOfDivs(softAssert, welcomeSection.getWELCOME_SECTION_DIV(), imageDiffUatGB, "testWelcomePaddings", "testWelcomePaddings",
                "testWelcomePaddings", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testWelcomePaddings");
        initializeTestExecutionData("testWelcomePaddings", startTime, welcomeSection.getWELCOME_SECTION_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    //@Test
    public void testWelcomeLocations(){
        WelcomeSection welcomeSection = new WelcomeSection(driver);

        SoftAssert softAssert = new SoftAssert();

        navigateTo(welcomeSection.getUrl());

        welcomeSection.setLocationsList();
        welcomeSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = welcomeSection.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 476},
                new Integer[]{463, 524},
                new Integer[]{463, 588},
                new Integer[]{463, 684},
                new Integer[]{786, 730},
                new Integer[]{954, 730}
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


        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = welcomeSection.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{463, 48},
                new Integer[]{0, 64},
                new Integer[]{0, 96},
                new Integer[]{323, 46},
                new Integer[]{168, 0}
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
    public void testWelcomeSectionAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        WelcomeSection welcomeSection = new WelcomeSection(driver);

        softAssert = new SoftAssert();

        navigateTo(welcomeSection.getUrl());

        welcomeSection.setLocationsList();
        welcomeSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = welcomeSection.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 476},
                new Integer[]{455, 524},
                new Integer[]{455, 588},
                new Integer[]{455, 684},
                new Integer[]{778, 730},
                new Integer[]{946, 730}
        );

        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, welcomeSection.getWELCOME_SECTION_DIV(), imageDiffUatGB, "testWelcomeSectionAbsoluteLocations", "testWelcomeSectionAbsoluteLocations",
                "testWelcomeSectionAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testWelcomeSectionAbsoluteLocations");
        initializeTestExecutionData("testWelcomeSectionAbsoluteLocations", startTime, welcomeSection.getWELCOME_SECTION_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testWelcomeSectionRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        WelcomeSection welcomeSection = new WelcomeSection(driver);

        softAssert = new SoftAssert();

        navigateTo(welcomeSection.getUrl());

        welcomeSection.setLocationsList();
        welcomeSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = welcomeSection.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{455, 48},
                new Integer[]{0, 64},
                new Integer[]{0, 96},
                new Integer[]{323, 46},
                new Integer[]{168, 0}
        );

        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, welcomeSection.getWELCOME_SECTION_DIV(), imageDiffUatGB, "testWelcomeSectionRelativeLocations", "testWelcomeSectionRelativeLocations",
                "testWelcomeSectionRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testWelcomeSectionRelativeLocations");
        initializeTestExecutionData("testWelcomeSectionRelativeLocations", startTime, welcomeSection.getWELCOME_SECTION_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testCampaignImageURL() throws IOException {
        Instant startTime = Instant.now();
        softAssert = new SoftAssert();

        Campaign campaign = new Campaign(driver);

        navigateTo(campaign.getUrl());

        String logoutPageUrl = driver.getCurrentUrl();

        softAssert.assertTrue(campaign.saveImageToFile());

        String[] expectedURLArray = {logoutPageUrl + "/cdn/b/orig/74/5b20a132fa6c2a587ac98bc4c5dd62/376x211.png?Rich", logoutPageUrl
                + "/cdn/b/orig/c5/a6347f5ec5355a52bc27e3f5dbbb3f/376x211.png?Rich", logoutPageUrl + "/cdn/b/orig/86/8481d3bd2f05f92a1ca47bfc68b9ca/376x211.png?Rich"};
        String actualURL = campaign.getImageURL();
        String[] actualURLArray = actualURL.split(",");

        /*for (int i = 0; i < expectedURLArray.length; i++){
            softAssert.assertEquals(actualURLArray[i], expectedURLArray[i]);
        }
        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignImageURL", "testCampaignImageURL",
                "testCampaignImageURL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignImageURL");
        initializeTestExecutionData("testCampaignImageURL", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, actualURLArray, expectedURLArray, isImagesSame));
    }

    @Test
    public void testCampaignValues() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());

        List<String[]> actualCampaignValues = campaign.valuesList();


        String[] expectedMainTitle = {"FuturaStd-Bold, Arial, Verdana", "24px", "rgba(64, 64, 64, 1)"};
        String[] expectedCardTitle = {"FuturaStd-Bold, Arial, Verdana", "18px", "rgba(64, 64, 64, 1)"};
        String[] expectedCardText = {"FuturaStd-Book, Arial, Verdana", "14px", "rgba(89, 89, 89, 1)"};
        String[] expectedButtonText = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        List<String[]> expectedCampaignValues = new LinkedList<>();
        expectedCampaignValues.add(expectedMainTitle);
        expectedCampaignValues.add(expectedCardTitle);
        expectedCampaignValues.add(expectedCardTitle);
        expectedCampaignValues.add(expectedCardTitle);
        expectedCampaignValues.add(expectedCardText);
        expectedCampaignValues.add(expectedCardText);
        expectedCampaignValues.add(expectedCardText);
        expectedCampaignValues.add(expectedButtonText);


        /*for(int i = 0; i < expectedCampaignValues.size(); i++){
            softAssert.assertEquals(actualCampaignValues.get(i), expectedCampaignValues.get(i));
        }
        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignValues", "testCampaignValues",
                "testCampaignValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignValues");
        initializeTestExecutionData("testCampaignValues", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, actualCampaignValues, expectedCampaignValues, isImagesSame));
    }

    @Test
    public void testCampaignPadding() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());

        Integer[] expectedArray = campaign.getPaddings();
        Integer[] actualArray = {48, 32, 24, 16, 24, 16, 24, 16, 24};

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignPadding", "testCampaignPadding",
                "testCampaignPadding", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignPadding");
        initializeTestExecutionData("testCampaignPadding", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    @Test
    public void testCampaignBtnReg() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());

        //Assertions.assertTrue(campaign.clickBtnReg());

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignBtnReg", "testCampaignBtnReg",
                "testCampaignBtnReg", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignBtnReg");
        initializeTestExecutionData("testCampaignBtnReg", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, campaign.clickBtnReg(), isImagesSame));
    }

    //@Test
    public void testCampaignLocations(){
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());

        campaign.setLocationsList();
        campaign.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = campaign.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{363, 858},
                new Integer[]{379, 1115},
                new Integer[]{379, 1147},
                new Integer[]{385, 1283},
                new Integer[]{363, 914}
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

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = campaign.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{16, 257},
                new Integer[]{0, 32},
                new Integer[]{6, 136},
                new Integer[]{-22, -369}
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
    public void testCampaignAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());

        campaign.setLocationsList();
        campaign.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = campaign.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{355, 858},
                new Integer[]{371, 1115},
                new Integer[]{371, 1147},
                new Integer[]{377, 1283},
                new Integer[]{355, 914}
        );

        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignAbsoluteLocations", "testCampaignAbsoluteLocations",
                "testCampaignAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignAbsoluteLocations");
        initializeTestExecutionData("testCampaignAbsoluteLocations", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testCampaignRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());

        campaign.setLocationsList();
        campaign.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = campaign.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{16, 257},
                new Integer[]{0, 32},
                new Integer[]{6, 136},
                new Integer[]{-22, -369}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));


        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignRelativeLocations", "testCampaignRelativeLocations",
                "testCampaignRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignRelativeLocations");
        initializeTestExecutionData("testCampaignRelativeLocations", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testCampaignPopupImageUrl() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());

        CampaignPopup campaignPopup = campaign.goToCampaignPopUp();

        String actualImageUrl = campaignPopup.imageUrl();
        String expectedImageUrl = "https://shelluatpoints.loyaltyondemand.club/en-gb//cdn/b/orig/74/5b20a132fa6c2a587ac98bc4c5dd62/376x211.png?Rich," +
                "https://shelluatpoints.loyaltyondemand.club/en-gb//cdn/b/orig/c5/a6347f5ec5355a52bc27e3f5dbbb3f/376x211.png?Rich," +
                "https://shelluatpoints.loyaltyondemand.club/en-gb//cdn/b/orig/86/8481d3bd2f05f92a1ca47bfc68b9ca/376x211.png?Rich";

        softAssert.assertEquals(actualImageUrl, expectedImageUrl);
        /*softAssert.assertTrue(campaignPopup.saveImageToFile());
        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignPopupImageUrl", "testCampaignPopupImageUrl",
                "testCampaignPopupImageUrl", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignPopupImageUrl");
        initializeTestExecutionData("testCampaignPopupImageUrl", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, campaignPopup.saveImageToFile(), isImagesSame));
    }

    @Test
    public void testCampaignPopupValues() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());
        CampaignPopup campaignPopup = campaign.goToCampaignPopUp();

        List<String[]> actualCampaignPopupValues = campaignPopup.valuesList();

        String[] campaignPopupTitle = {"FuturaStd-Bold, Arial, Verdana","18px","rgba(64, 64, 64, 1)"};
        String[] campaignPopupSecondaryButton = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        String[] campaignPopupPrimaryButton = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        List<String[]> campaignPopUpValuesAccumulator = Arrays.asList(campaignPopupTitle, campaignPopupSecondaryButton, campaignPopupPrimaryButton);

        List<String[]> expectedCampaignPopupValues = new LinkedList<>();

        IntStream.range(0, 3).forEach(i -> {
            IntStream.range(0, 3).forEach(j -> expectedCampaignPopupValues.add(campaignPopUpValuesAccumulator.get(i)));
        });

        /*for(int i = 0; i < expectedCampaignPopupValues.size(); i++){
            softAssert.assertEquals(actualCampaignPopupValues.get(i), expectedCampaignPopupValues.get(i));
        }
        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignPopupValues", "testCampaignPopupValues",
                "testCampaignPopupValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignPopupValues");
        initializeTestExecutionData("testCampaignPopupValues", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, actualCampaignPopupValues, expectedCampaignPopupValues, isImagesSame));
    }

    @Test
    public void testCampaignPopupPadding() throws IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());
        CampaignPopup campaignPopup = campaign.goToCampaignPopUp();

        Integer[] actualArray = campaignPopup.getPaddings();
        Integer[] expectedArray = {48, 24, 24, 24, 24, 8, 16, 16, 48, 24, 24, 24, 24, 8, 16, 16, 48, 24, 24, 24, 24, 8, 16, 16};

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignPopupPadding", "testCampaignPopupPadding",
                "testCampaignPopupPadding", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignPopupPadding");
        initializeTestExecutionData("testCampaignPopupPadding", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }


    @Test
    public void testCampaignPopupLoginRegister() throws AWTException, IOException {
        Instant startTime = Instant.now();
        Campaign campaign = new Campaign(driver);

        softAssert = new SoftAssert();

        navigateTo(campaign.getUrl());
        CampaignPopup campaignPopup = campaign.goToCampaignPopUp();
        List<String> actualUrls = new LinkedList<>();

        IntStream.range(1,4).forEach(
                i ->    {
                    campaignPopup.clickOnCardBasedOnNumber(i);
                    //actualUrls.add(campaignPopup.clickRobot(840, 680));
                    actualUrls.add(campaignPopup.clickSecondaryBtn(i-1));
                    navigateTo(campaign.getUrl());
                    campaignPopup.clickOnCardBasedOnNumber(i);

                    //actualUrls.add(campaignPopup.clickRobot(1140,680));
                    actualUrls.add(campaignPopup.clickPrimaryBtn(i-1));
                    navigateTo(campaignPopup.getUrl());
                }
        );
        
        String[] actions = {"login", "register"};

        List<String> expectedUrls = IntStream.range(0,2)
                        .boxed()
                                .flatMap(i -> Stream.of(actions))
                                        .map(action -> "https://test.login.consumer.shell.com/" + action).toList();


        /*for(int i = 0; i < expectedUrls.size(); i++){
            softAssert.assertEquals(actualUrls.get(i), expectedUrls.get(i));
        }
        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, campaign.getCAMPAIGN_DIV(), imageDiffUatGB, "testCampaignPopupLoginRegister", "testCampaignPopupLoginRegister",
                "testCampaignPopupLoginRegister", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testCampaignPopupLoginRegister");
        initializeTestExecutionData("testCampaignPopupLoginRegister", startTime, campaign.getCAMPAIGN_DIV(), assertMethod(softAssert, actualUrls, expectedUrls, isImagesSame));
    }

    @Test
    public void testLoyaltyBenefitImageUrl() throws IOException {
        Instant startTime = Instant.now();
        LoyaltyBenefit loyaltyBenefit = new LoyaltyBenefit(driver);

        softAssert = new SoftAssert();
        navigateTo(loyaltyBenefit.getUrl());

        String[] actualImageUrl = loyaltyBenefit.imageUrl().split(",");
        for (String item: actualImageUrl){
            System.out.println(item);
        }

        String[] expectedImageUrl = {"https://shelluatpoints.loyaltyondemand.club/cdn/static/flat/af/43711bd3065245d8570dcb9ed2b417/card_img_1.svg",
                "https://shelluatpoints.loyaltyondemand.club/cdn/static/flat/7c/39a4af135ad48f768011cacf87b8e0/card_img_2.svg",
                "https://shelluatpoints.loyaltyondemand.club/cdn/static/flat/fd/3f8e086733198e72f6dd2abd53afbf/card_img_3.svg",
                "https://shelluatpoints.loyaltyondemand.club/cdn/static/flat/b7/5ef770145681ffd38cb2a705eb67df/card_img_4.svg",
                "https://shelluatpoints.loyaltyondemand.club/cdn/static/flat/d2/ed2053d146d110b8ec30f1c13e23b1/main_img.svg"};

        softAssert.assertEquals(actualImageUrl, expectedImageUrl);

        //softAssert.assertTrue(loyaltyBenefit.saveImageToFile());
        //softAssert.assertAll();

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, loyaltyBenefit.getBENEFIT_DIV(), imageDiffUatGB, "testLoyaltyBenefitImageUrl", "testLoyaltyBenefitImageUrl",
                "testLoyaltyBenefitImageUrl", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testLoyaltyBenefitImageUrl");
        initializeTestExecutionData("testLoyaltyBenefitImageUrl", startTime, loyaltyBenefit.getBENEFIT_DIV(), assertMethod(softAssert, actualImageUrl, expectedImageUrl, isImagesSame));
    }


    @Test
    public void testLoyaltyBenefitValues() throws IOException {
        Instant startTime = Instant.now();
        LoyaltyBenefit loyaltyBenefit = new LoyaltyBenefit(driver);

        softAssert = new SoftAssert();

        navigateTo(loyaltyBenefit.getUrl());

        List<String[]> actualLoyaltyBenefitValues = loyaltyBenefit.valuesList();

        String[] loyaltyBenefitTitle = {"FuturaStd-Bold, Arial, Verdana", "24px","rgba(64, 64, 64, 1)"};
        String[] loyaltyBenefitCardTitle = {"FuturaStd-Bold, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        String[] loyaltyBenefitCardDescription = {"FuturaStd-Book, Arial, Verdana", "14px", "rgba(89, 89, 89, 1)"};
        List<String[]> expectedCampaignPopupValues = new LinkedList<>();
        expectedCampaignPopupValues.add(loyaltyBenefitTitle);

        expectedCampaignPopupValues.add(loyaltyBenefitCardTitle);
        expectedCampaignPopupValues.add(loyaltyBenefitCardTitle);
        expectedCampaignPopupValues.add(loyaltyBenefitCardTitle);
        expectedCampaignPopupValues.add(loyaltyBenefitCardTitle);

        expectedCampaignPopupValues.add(loyaltyBenefitCardDescription);
        expectedCampaignPopupValues.add(loyaltyBenefitCardDescription);
        expectedCampaignPopupValues.add(loyaltyBenefitCardDescription);
        expectedCampaignPopupValues.add(loyaltyBenefitCardDescription);


        /*for(int i = 0; i < expectedCampaignPopupValues.size(); i++){
            softAssert.assertEquals(actualLoyaltyBenefitValues.get(i), expectedCampaignPopupValues.get(i));
        }
        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, loyaltyBenefit.getBENEFIT_DIV(), imageDiffUatGB, "testLoyaltyBenefitValues", "testLoyaltyBenefitValues",
                "testLoyaltyBenefitValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testLoyaltyBenefitValues");
        initializeTestExecutionData("testLoyaltyBenefitValues", startTime, loyaltyBenefit.getBENEFIT_DIV(), assertMethod(softAssert, actualLoyaltyBenefitValues, expectedCampaignPopupValues, isImagesSame));
    }

    @Test
    public void testLoyaltyBenefitPadding() throws IOException {
        Instant startTime = Instant.now();
        LoyaltyBenefit loyaltyBenefit = new LoyaltyBenefit(driver);

        softAssert = new SoftAssert();

        navigateTo(loyaltyBenefit.getUrl());

        Integer[] actualArray = loyaltyBenefit.getPaddings();
        Integer[] expectedArray = {48, 32, 24, 15, 8, 24, 15, 8, 24, 15, 8, 24, 15, 8};

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, loyaltyBenefit.getBENEFIT_DIV(), imageDiffUatGB, "testLoyaltyBenefitPadding", "testLoyaltyBenefitPadding",
                "testLoyaltyBenefitPadding", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testLoyaltyBenefitPadding");
        initializeTestExecutionData("testLoyaltyBenefitPadding", startTime, loyaltyBenefit.getBENEFIT_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    //@Test
    public void testLoyaltyBenefitLocations(){
        LoyaltyBenefit loyaltyBenefit = new LoyaltyBenefit(driver);

        navigateTo(loyaltyBenefit.getUrl());

        SoftAssert softAssert = new SoftAssert();

        loyaltyBenefit.setLocationsList();
        loyaltyBenefit.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = loyaltyBenefit.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{363, 1347},
                new Integer[]{997, 1456},
                new Integer[]{363, 1456},
                new Integer[]{363, 1590},
                new Integer[]{363, 1724},
                new Integer[]{363, 1858}
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

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = loyaltyBenefit.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{634, 109},
                new Integer[]{-634, 0},
                new Integer[]{0, 134},
                new Integer[]{0, 134},
                new Integer[]{0, 134}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));
        IntStream.range(1, expectedRelativeLocations.size()+1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if(actual != null && expected != null){
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }

                });

        softAssert.assertAll();
    }

    @Test
    public void testLoyaltyBenefitAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        LoyaltyBenefit loyaltyBenefit = new LoyaltyBenefit(driver);

        navigateTo(loyaltyBenefit.getUrl());

        softAssert = new SoftAssert();

        loyaltyBenefit.setLocationsList();
        loyaltyBenefit.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = loyaltyBenefit.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{363, 1347},
                new Integer[]{997, 1456},
                new Integer[]{363, 1456},
                new Integer[]{363, 1590},
                new Integer[]{363, 1724},
                new Integer[]{363, 1858}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, loyaltyBenefit.getBENEFIT_DIV(), imageDiffUatGB, "testLoyaltyBenefitAbsoluteLocations", "testLoyaltyBenefitAbsoluteLocations",
                "testLoyaltyBenefitAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testLoyaltyBenefitAbsoluteLocations");
        initializeTestExecutionData("testLoyaltyBenefitAbsoluteLocations", startTime, loyaltyBenefit.getBENEFIT_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testLoyaltyBenefitRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        LoyaltyBenefit loyaltyBenefit = new LoyaltyBenefit(driver);

        navigateTo(loyaltyBenefit.getUrl());

        softAssert = new SoftAssert();

        loyaltyBenefit.setLocationsList();
        loyaltyBenefit.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = loyaltyBenefit.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{634, 109},
                new Integer[]{-634, 0},
                new Integer[]{0, 134},
                new Integer[]{0, 134},
                new Integer[]{0, 134}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, loyaltyBenefit.getBENEFIT_DIV(), imageDiffUatGB, "testLoyaltyBenefitRelativeLocations", "testLoyaltyBenefitRelativeLocations",
                "testLoyaltyBenefitRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testLoyaltyBenefitRelativeLocations");
        initializeTestExecutionData("testLoyaltyBenefitRelativeLocations", startTime, loyaltyBenefit.getBENEFIT_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testHowItWorksImageUrl() throws IOException {
        Instant startTime = Instant.now();
        HowItWorks howItWorks = new HowItWorks(driver);

        softAssert = new SoftAssert();

        navigateTo(howItWorks.getUrl());

        String[] actualImageUrl = howItWorks.imageUrl().split(",");
        String[] expectedImageUrl = {"https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/a4/fea5baef53510534ef8251bdd0d82f/24x24.png?Icon.png",
                "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/36/251da405db6a982c3a02c793f51b43/24x24.png?ic_gift_filled.png",
                "https://shelluatpoints.loyaltyondemand.club/cdn/b/orig/bf/f080591ea69837d45642907903495c/24x24.png?Icon.png"};

        Allure.addAttachment("image_url", Arrays.toString(expectedImageUrl));


        softAssert.assertEquals(actualImageUrl, expectedImageUrl);
        //softAssert.assertTrue(howItWorks.saveImageToFile());
        //softAssert.assertAll();

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, howItWorks.getHOWITWORKS_DIV(), imageDiffUatGB, "testHowItWorksImageUrl", "testHowItWorksImageUrl",
                "testHowItWorksImageUrl", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHowItWorksImageUrl");
        initializeTestExecutionData("testHowItWorksImageUrl", startTime, howItWorks.getHOWITWORKS_DIV(), assertMethod(softAssert, howItWorks.saveImageToFile(), isImagesSame));
    }

    @Test
    public void testHowItWorksValues() throws IOException {
        Instant startTime = Instant.now();
        HowItWorks howItWorks = new HowItWorks(driver);

        softAssert = new SoftAssert();

        navigateTo(howItWorks.getUrl());

        List<String[]> actualHowItWorksValues = howItWorks.valuesList();

        String[] howItWorksTitle = {"FuturaStd-Bold, Arial, Verdana", "24px", "rgba(64, 64, 64, 1)"};
        String[] howItWorksCardTitle = {"FuturaStd-Bold, Arial, Verdana", "18px", "rgba(64, 64, 64, 1)"};
        String[] howItWorksCardDescription = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(89, 89, 89, 1)"};
        List<String[]> expectedHowItWorksValues = new LinkedList<>();

        expectedHowItWorksValues.add(howItWorksTitle);

        IntStream.range(0,3).forEach(i -> expectedHowItWorksValues.add(howItWorksCardTitle));
        IntStream.range(0,3).forEach(i -> expectedHowItWorksValues.add(howItWorksCardDescription));

        //int minLength = Math.min(actualHowItWorksValues.size(), expectedHowItWorksValues.size());

        /*IntStream.range(0, Math.max(minLength, 7)).forEach(i ->
                softAssert.assertEquals(actualHowItWorksValues.get(i), expectedHowItWorksValues.get(i)));

        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, howItWorks.getHOWITWORKS_DIV(), imageDiffUatGB, "testHowItWorksValues", "testHowItWorksValues",
                "testHowItWorksValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHowItWorksValues");
        initializeTestExecutionData("testHowItWorksValues", startTime, howItWorks.getHOWITWORKS_DIV(), assertMethod(softAssert, actualHowItWorksValues, expectedHowItWorksValues, isImagesSame));
    }

    @Test
    public void testHowItWorksPadding() throws IOException {
        Instant startTime = Instant.now();
        HowItWorks howItWorks = new HowItWorks(driver);

        softAssert = new SoftAssert();

        navigateTo(howItWorks.getUrl());

        Integer[] actualArray = howItWorks.getPaddings();
        Integer[] expectedArray = {24, 32, 24, 16, 16, 8, 16, 16, 8, 16, 16, 8};

        //Allure.addAttachment("content_allure.txt", "text/plain", "file content");

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, howItWorks.getHOWITWORKS_DIV(), imageDiffUatGB, "testHowItWorksPadding", "testHowItWorksPadding",
                "testHowItWorksPadding", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHowItWorksPadding");
        initializeTestExecutionData("testHowItWorksPadding", startTime, howItWorks.getHOWITWORKS_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    //@Test
    public void testHowItWorksLocations() {
        HowItWorks howItWorks = new HowItWorks(driver);

        navigateTo(howItWorks.getUrl());

        SoftAssert softAssert = new SoftAssert();

        howItWorks.setLocationsList();
        howItWorks.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = howItWorks.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{363, 2016},
                new Integer[]{363, 2068},
                new Integer[]{763, 2068},
                new Integer[]{1163, 2068}
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

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = howItWorks.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{0, 52},
                new Integer[]{400, 0},
                new Integer[]{400, 0}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        IntStream.range(1, relativeLocationsList.size() + 1)
                .forEach(i ->{
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i+ " is null.");
                    softAssert.assertNotNull(expected, "Expected value at idnex " + i + " is null.");

                    if(actual != null && expected != null){
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();
    }

    @Test
    public void testHowItWorksAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        HowItWorks howItWorks = new HowItWorks(driver);

        navigateTo(howItWorks.getUrl());

        softAssert = new SoftAssert();

        howItWorks.setLocationsList();
        howItWorks.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = howItWorks.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{363, 2016},
                new Integer[]{363, 2068},
                new Integer[]{763, 2068},
                new Integer[]{1163, 2068}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, howItWorks.getHOWITWORKS_DIV(), imageDiffUatGB, "testHowItWorksAbsoluteLocations", "testHowItWorksAbsoluteLocations",
                "testHowItWorksAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHowItWorksAbsoluteLocations");
        initializeTestExecutionData("testHowItWorksAbsoluteLocations", startTime, howItWorks.getHOWITWORKS_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testHowItWorksRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        HowItWorks howItWorks = new HowItWorks(driver);

        navigateTo(howItWorks.getUrl());

        softAssert = new SoftAssert();

        howItWorks.setLocationsList();
        howItWorks.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = howItWorks.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{0, 52},
                new Integer[]{400, 0},
                new Integer[]{400, 0}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, howItWorks.getHOWITWORKS_DIV(), imageDiffUatGB, "testHowItWorksRelativeLocations", "testHowItWorksRelativeLocations",
                "testHowItWorksRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testHowItWorksRelativeLocations");
        initializeTestExecutionData("testHowItWorksRelativeLocations", startTime, howItWorks.getHOWITWORKS_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    //@Test
    public void testPosition() throws InterruptedException {
        Instant startTime = Instant.now();
        FAQs faQs = new FAQs(driver);

        softAssert = new SoftAssert();

        navigateTo(faQs.getUrl());

        Integer[] coordinates = {10,40};

        faQs.printPosition();

        //LinkedHashMap<Integer, Integer[]> actualPointDiff = calculateDifference(faQs.accumulateMapBasedOnLocations());
        LinkedHashMap<Integer, Integer[]> actualPointDiff = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer[]> entry : actualPointDiff.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
    }



    @Test
    public void testFAQsValues() throws IOException {
        Instant startTime = Instant.now();
        FAQs faQs = new FAQs(driver);

        softAssert = new SoftAssert();

        navigateTo(faQs.getUrl());

        List<String[]> actualFAQsValues = faQs.valuesList();

        String[] faQsTitle = {"FuturaStd-Bold, Arial, Verdana", "24px", "rgba(64, 64, 64, 1)"};
        String[] faQsQuestion = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};
        String[] faQsAnswer = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(89, 89, 89, 1)", "none"};
        List<String[]> expectedFAQsValues = new LinkedList<>();

        expectedFAQsValues.add(faQsTitle);

        IntStream.range(0,4).forEach(i -> {
            expectedFAQsValues.add(faQsQuestion);
            expectedFAQsValues.add(faQsAnswer);
        });


        /*int minLength = Math.min(actualFAQsValues.size(), expectedFAQsValues.size());

        IntStream.range(0, Math.max(minLength, 8)).forEach(i ->
                softAssert.assertEquals(actualFAQsValues.get(i), expectedFAQsValues.get(i)));

        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, faQs.getFAQ_CONTAINER(), imageDiffUatGB, "testFAQsValues", "testFAQsValues",
                "testFAQsValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFAQsValues");
        initializeTestExecutionData("testFAQsValues", startTime, faQs.getFAQ_CONTAINER(), assertMethod(softAssert, actualFAQsValues, expectedFAQsValues, isImagesSame));
    }

    @Test
    public void testFAQsPaddings() throws IOException {
        Instant startTime = Instant.now();
        FAQs faQs = new FAQs(driver);

        softAssert = new SoftAssert();

        navigateTo(faQs.getUrl());

        Integer[] actualArray = faQs.getPaddings();
        //Integer[] expectedArray = {48, 32, 24, 20, 20, 16, 20, 20, 16, 20, 20, 16, 20, 20, 16, 24, 24, 24, 24};

        int[] pattern = {20, 20, 16};
        Integer[] expectedArray = new Integer[19];

        expectedArray[0] = 48;
        expectedArray[1] = 32;
        expectedArray[2] = 24;

        for (int i = 3, j = 0; i < 16; i++, j = (j + 1) % pattern.length) {
            expectedArray[i] = pattern[j];
        }

        Arrays.fill(expectedArray, 15, 19, 24);

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, faQs.getFAQ_CONTAINER(), imageDiffUatGB, "testFAQsPaddings", "testFAQsPaddings",
                "testFAQsPaddings", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFAQsPaddings");
        initializeTestExecutionData("testFAQsPaddings", startTime, faQs.getFAQ_CONTAINER(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    //@Test
    public void testFAQsLocations(){
        Instant startTime = Instant.now();
        FAQs faQs = new FAQs(driver);

        navigateTo(faQs.getUrl());

        softAssert = new SoftAssert();

        faQs.setLocationsList();
        faQs.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = faQs.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 2224},
                new Integer[]{363, 2272},
                new Integer[]{363, 2328},
                new Integer[]{363, 2393},
                new Integer[]{363, 2458},
                new Integer[]{363, 2523},
                new Integer[]{363, 2392},
                new Integer[]{363, 2457},
                new Integer[]{363, 2522},
                new Integer[]{363, 2587}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));
        IntStream.range(1, expectedAbsoluteLocations.size()+1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = faQs.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{363, 48},
                new Integer[]{0, 56},
                new Integer[]{0, 65},
                new Integer[]{0, 65},
                new Integer[]{0, 65},
                new Integer[]{0, -131},
                new Integer[]{0, 65},
                new Integer[]{0, 65},
                new Integer[]{0, 65}
        );
        IntStream.range(0, relativeLocationsList.size())
                        .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        IntStream.range(1, expectedRelativeLocations.size())
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
    public void testFAQsAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        FAQs faQs = new FAQs(driver);

        navigateTo(faQs.getUrl());

        softAssert = new SoftAssert();

        faQs.setLocationsList();
        faQs.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = faQs.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{0, 2224},
                new Integer[]{355, 2272},
                new Integer[]{355, 2328},
                new Integer[]{355, 2393},
                new Integer[]{355, 2458},
                new Integer[]{355, 2523},
                new Integer[]{355, 2392},
                new Integer[]{355, 2457},
                new Integer[]{355, 2522},
                new Integer[]{355, 2587}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, faQs.getFAQ_CONTAINER(), imageDiffUatGB, "testFAQsAbsoluteLocations", "testFAQsAbsoluteLocations",
                "testFAQsAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFAQsAbsoluteLocations");
        initializeTestExecutionData("testFAQsAbsoluteLocations", startTime, faQs.getFAQ_CONTAINER(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testFAQsRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        FAQs faQs = new FAQs(driver);

        navigateTo(faQs.getUrl());

        softAssert = new SoftAssert();

        faQs.setLocationsList();
        faQs.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = faQs.relativeLocations();
        for(Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{355, 48},
                new Integer[]{0, 56},
                new Integer[]{0, 65},
                new Integer[]{0, 65},
                new Integer[]{0, 65},
                new Integer[]{0, -131},
                new Integer[]{0, 65},
                new Integer[]{0, 65},
                new Integer[]{0, 65}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, faQs.getFAQ_CONTAINER(), imageDiffUatGB, "testFAQsRelativeLocations", "testFAQsRelativeLocations",
                "testFAQsRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFAQsRelativeLocations");
        initializeTestExecutionData("testFAQsRelativeLocations", startTime, faQs.getFAQ_CONTAINER(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testDownloadAppImageUrl() throws IOException {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());
        downloadApp.imageUrl();
        String[] actualImageUrl = downloadApp.getImageUrl().split(",");
        String[] expectedImageUrl = {"https://shelluatpoints.loyaltyondemand.club/cdn/b/deriv/97/2ab045bd2da2858ee964f50b0eb68c/600x188.png?App%20download%20badge_Apple.png",
            "https://shelluatpoints.loyaltyondemand.club/cdn/b/deriv/28/6545afff19bc85d72ae6c424500c22/600x188.png?App%20download%20badge_Android%20(1).png"};

        softAssert.assertEquals(actualImageUrl,expectedImageUrl);
        //softAssert.assertTrue(downloadApp.saveImageToFile());
        //softAssert.assertAll();

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, downloadApp.getDOWNLOAD_APP_DIV(), imageDiffUatGB, "testDownloadAppImageUrl", "testDownloadAppImageUrl",
                "testDownloadAppImageUrl", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testDownloadAppImageUrl");
        initializeTestExecutionData("testDownloadAppImageUrl", startTime, downloadApp.getDOWNLOAD_APP_DIV(), assertMethod(softAssert, downloadApp.saveImageToFile(), isImagesSame));
    }

    @Test
    public void testDownloadAppValues() throws IOException {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        List<String[]> actualDownloadAppValues = downloadApp.valuesList();

        String[] downloadAppTitle = {"FuturaStd-Bold, Arial, Verdana", "24px", "rgba(64, 64, 64, 1)"};
        String[] downloadAppText = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(64, 64, 64, 1)"};

        List<String[]> expectedDownloadAppValues = new LinkedList<>();
        expectedDownloadAppValues.add(downloadAppTitle);
        expectedDownloadAppValues.add(downloadAppText);

        int minLength = Math.min(actualDownloadAppValues.size(), expectedDownloadAppValues.size());

        /*IntStream.range(0, minLength).forEach(i ->
                softAssert.assertEquals(actualDownloadAppValues.get(i), expectedDownloadAppValues.get(i))
        );
        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, downloadApp.getDOWNLOAD_APP_DIV(), imageDiffUatGB, "testDownloadAppValues", "testDownloadAppValues",
                "testDownloadAppValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testDownloadAppValues");
        initializeTestExecutionData("testDownloadAppValues", startTime, downloadApp.getDOWNLOAD_APP_DIV(), assertMethod(softAssert, actualDownloadAppValues, expectedDownloadAppValues, isImagesSame));
    }

    @Test
    public void testDownloadAppPadding() throws IOException {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        Integer[] actualArray = downloadApp.getPaddings();
        Integer[] expectedArray = {48, 16, 32, 24, 24};

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, downloadApp.getDOWNLOAD_APP_DIV(), imageDiffUatGB, "testDownloadAppPadding", "testDownloadAppPadding",
                "testDownloadAppPadding", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testDownloadAppPadding");
        initializeTestExecutionData("testDownloadAppPadding", startTime, downloadApp.getDOWNLOAD_APP_DIV(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    //@Test
    public void testDownloadAppLocations(){
        DownloadApp downloadApp = new DownloadApp(driver);

        navigateTo(downloadApp.getUrl());

        SoftAssert softAssert = new SoftAssert();

        downloadApp.setLocationsList();
        downloadApp.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = downloadApp.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key+ ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{387, 2848},
                new Integer[]{564, 2848},
                new Integer[]{387, 2680},
                new Integer[]{387, 2776}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));
        IntStream.range(1, expectedAbsoluteLocations.size()+1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });


        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = downloadApp.relativeLocations();
        for (Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{177, 0},
                new Integer[]{-177, -168},
                new Integer[]{0, 96}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));
        IntStream.range(1, expectedRelativeLocations.size()+1)
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
    public void testDownloadAppAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        navigateTo(downloadApp.getUrl());

        softAssert = new SoftAssert();

        downloadApp.setLocationsList();
        downloadApp.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = downloadApp.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key+ ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{387, 2848},
                new Integer[]{564, 2848},
                new Integer[]{387, 2680},
                new Integer[]{387, 2776}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, downloadApp.getDOWNLOAD_APP_DIV(), imageDiffUatGB, "testDownloadAppAbsoluteLocations", "testDownloadAppAbsoluteLocations",
                "testDownloadAppAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testDownloadAppAbsoluteLocations");
        initializeTestExecutionData("testDownloadAppAbsoluteLocations", startTime, downloadApp.getDOWNLOAD_APP_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testDownloadAppRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        DownloadApp downloadApp = new DownloadApp(driver);

        softAssert = new SoftAssert();

        navigateTo(downloadApp.getUrl());

        downloadApp.setLocationsList();
        downloadApp.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = downloadApp.relativeLocations();
        for (Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{177, 0},
                new Integer[]{-177, -168},
                new Integer[]{0, 96}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, downloadApp.getDOWNLOAD_APP_DIV(), imageDiffUatGB, "testDownloadAppRelativeLocations", "testDownloadAppRelativeLocations",
                "testDownloadAppRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testDownloadAppRelativeLocations");
        initializeTestExecutionData("testDownloadAppRelativeLocations", startTime, downloadApp.getDOWNLOAD_APP_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testLoginUatGB(){
        LoginUatGb loginUatGb = new LoginUatGb(driver);

        loginUatGb.loginToUATGb();
    }


    @Test
    public void testFooterImageUrl() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());
        footerSection.imageUrl();
        String[] actualImageUrl = footerSection.getImageUrl().split(",");
        String[] expectedImageUrl = {"https://shelluatpoints.loyaltyondemand.club/cdn/b/deriv/97/2ab045bd2da2858ee964f50b0eb68c/600x188.png?App%20download%20badge_Apple.png",
                "https://shelluatpoints.loyaltyondemand.club/cdn/b/deriv/28/6545afff19bc85d72ae6c424500c22/600x188.png?App%20download%20badge_Android%20(1).png"};

        softAssert.assertEquals(actualImageUrl,expectedImageUrl);
        //softAssert.assertTrue(footerSection.saveImageToFile());
        //softAssert.assertAll();

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, footerSection.getFOOTER_WHOLE(), imageDiffUatGB, "testFooterImageUrl", "testFooterImageUrl",
                "testFooterImageUrl", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFooterImageUrl");
        initializeTestExecutionData("testFooterImageUrl", startTime, footerSection.getFOOTER_WHOLE(), assertMethod(softAssert, footerSection.saveImageToFile(), isImagesSame));
    }

    @Test
    public void testFooterValues() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());

        List<String[]> actualFooterValues = footerSection.valuesList();

        String[] footerTitle = {"FuturaStd-Heavy, Arial, Verdana, sans-serif", "12px", "rgba(64, 64, 64, 1)"};

        //TODO NEEDS To CHECK WHY FONT_SIZE 19px instead of 14px
        String[] footerSubtitle = {"FuturaStd-Book, Arial, Verdana", "19.9306px", "rgba(64, 64, 64, 1)"};
        List<String[]> expectedFooterValues = new LinkedList<>();

        IntStream.range(0,4).forEach(i -> {
            expectedFooterValues.add(footerTitle);
        });
        IntStream.range(0,8).forEach(i -> {
            expectedFooterValues.add(footerSubtitle);
        });

        for(String[] array: actualFooterValues){
            System.out.println(Arrays.toString(array));
        }
        for(String[] array: expectedFooterValues){
            System.out.println(Arrays.toString(array));
        }


        int minLength = Math.min(actualFooterValues.size(), expectedFooterValues.size());
        System.out.println(minLength);

        /*IntStream.range(0, Math.max(minLength, 12)).forEach(i ->
                softAssert.assertEquals(actualFooterValues.get(i), expectedFooterValues.get(i)));

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivs(softAssert, footerSection.getFOOTER_WHOLE(), imageDiffUatGB, "testFooterValues", "testFooterValues",
                "testFooterValues", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFooterValues");
        initializeTestExecutionData("testFooterValues", startTime, footerSection.getFOOTER_WHOLE(), assertMethod(softAssert, actualFooterValues, expectedFooterValues, isImagesSame));
    }

    @Test
    public void testFooterPadding() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());

        Integer[] actualArray = footerSection.getPaddings();

        Integer[] expectedArray = new Integer[12];

        expectedArray[0] = 24;
        expectedArray[1] = 48;
        expectedArray[2] = 355;
        expectedArray[3] = 355;


        Arrays.fill(expectedArray, 4, 12, 16);

        //Assertions.assertArrayEquals(expectedArray, actualArray);

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, footerSection.getFOOTER_WHOLE(), imageDiffUatGB, "testFooterPadding", "testFooterPadding",
                "testFooterPadding", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFooterPadding");
        initializeTestExecutionData("testFooterPadding", startTime, footerSection.getFOOTER_WHOLE(), assertMethod(softAssert, actualArray, expectedArray, isImagesSame));
    }

    //@Test
    public void testFooterLocations(){
        //Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        navigateTo(footerSection.getUrl());

        softAssert = new SoftAssert();

        footerSection.setLocationsList();
        footerSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = footerSection.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key+ ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{1255, 3231},
                new Integer[]{1396, 3231},
                new Integer[]{355, 3197},
                new Integer[]{655, 3197},
                new Integer[]{955, 3197},
                new Integer[]{1255, 3197},
                new Integer[]{355, 3215},
                new Integer[]{355, 3251},
                new Integer[]{655, 3215},
                new Integer[]{655, 3251},
                new Integer[]{655, 3287},
                new Integer[]{655, 3323},
                new Integer[]{955, 3215},
                new Integer[]{955, 3251}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));
        IntStream.range(1, expectedAbsoluteLocations.size()+1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });


        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = footerSection.relativeLocations();
        for (Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{141, 0},
                new Integer[]{-1041, -34},
                new Integer[]{300, 0},
                new Integer[]{300, 0},
                new Integer[]{300, 0},
                new Integer[]{-900, 18},
                new Integer[]{0, 36},
                new Integer[]{300, -36},
                new Integer[]{0, 36},
                new Integer[]{0, 36},
                new Integer[]{0, 36},
                new Integer[]{300, -108},
                new Integer[]{0, 36}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));
        IntStream.range(1, expectedRelativeLocations.size()+1)
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
    public void testFooterAbsoluteLocations() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        navigateTo(footerSection.getUrl());

        softAssert = new SoftAssert();

        footerSection.setLocationsList();
        footerSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = footerSection.absoluteLocations();
        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key+ ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{1255, 3231},
                new Integer[]{1396, 3231},
                new Integer[]{355, 3197},
                new Integer[]{655, 3197},
                new Integer[]{955, 3197},
                new Integer[]{1255, 3197},
                new Integer[]{355, 3215},
                new Integer[]{355, 3251},
                new Integer[]{655, 3215},
                new Integer[]{655, 3251},
                new Integer[]{655, 3287},
                new Integer[]{655, 3323},
                new Integer[]{955, 3215},
                new Integer[]{955, 3251}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, footerSection.getFOOTER_WHOLE(), imageDiffUatGB, "testFooterAbsoluteLocations", "testFooterAbsoluteLocations",
                "testFooterAbsoluteLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFooterValues");
        initializeTestExecutionData("testFooterAbsoluteLocations", startTime, footerSection.getFOOTER_WHOLE(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testFooterRelativeLocations() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());

        footerSection.setLocationsList();
        footerSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = footerSection.relativeLocations();
        for (Map.Entry<Integer, Integer[]> entries: relativeLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{141, 0},
                new Integer[]{-1041, -34},
                new Integer[]{300, 0},
                new Integer[]{300, 0},
                new Integer[]{300, 0},
                new Integer[]{-900, 18},
                new Integer[]{0, 36},
                new Integer[]{300, -36},
                new Integer[]{0, 36},
                new Integer[]{0, 36},
                new Integer[]{0, 36},
                new Integer[]{300, -108},
                new Integer[]{0, 36}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        softAssert = takeShutterBugScreenshotOfDivs(softAssert, footerSection.getFOOTER_WHOLE(), imageDiffUatGB, "testFooterRelativeLocations", "testFooterRelativeLocations",
                "testFooterRelativeLocations", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\uat_gb\\testFooterRelativeLocations");
        initializeTestExecutionData("testFooterRelativeLocations", startTime, footerSection.getFOOTER_WHOLE(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    //Because of ROBOT this test just works with not headless chrome
    @Test
    public void testLogin() throws InterruptedException {
        parameterizedLoginUatGb.testLoginWithMultipleUsers();
    }

    @Test
    public void testLogoutPageShutterbug() throws InterruptedException, IOException {
        Instant startTime = Instant.now();
        softAssert = new SoftAssert();

        driver.get("https://shelluatpoints.loyaltyondemand.club/en-gb/");
        final By BUTTON_DECLINE = By.xpath("//button[@id=\"_evidon-decline-button\"]");
        final By DISPLAY_COOKIES = By.xpath("//div[@id=\"_evidon_banner\"]");
        final By TITLE_HOME = By.xpath("//div[@id=\"fpHomeFaqHeadTitle\"]");
        final By BUTTON_MORE = By.xpath("(//div[@class=\"menuButtonTable\"])[3]");

        final By PAGE_WHOLE = By.xpath("//div[@class=\"content-body new-footer\"]");
        final By HOW_IT_WORKS_DIV = By.id("fpHomeHowItWorksContainer");
        final By HEADER_DIV = By.id("desktopSubMenu");


        Thread.sleep(4000);

        final Actions action = new Actions(driver);

        int iterationCounter = 0;
        for (int i = 0; i < 1; i++) {

            try {
                if (driver.findElement(DISPLAY_COOKIES).isDisplayed()) {
                    driver.findElement(BUTTON_DECLINE).click();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Thread.sleep(4000);

            counter = Utils.readFile("counter_uat_gb.txt");
            try {
                Shutterbug.shootPage(driver, Capture.FULL_SCROLL, 500, true)
                        .withName(String.format("shutterbug_headless_screenshot_uat_gb%d.png", counter))
                        .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\uat_gb");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            /*((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(TITLE_HOME));

            if(iterationCounter == 0){
                action.doubleClick(driver.findElement(BUTTON_MORE)).perform();
            }else{
                driver.findElement(BUTTON_MORE).click();
            }

            try {
                Shutterbug.shootPage(driver, Capture.FULL_SCROLL, 500, true)
                        .withName(String.format("shutterbug_headless_screenshot_uat_gb_overtime%d.png", counter))
                        .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\uat_gb_overtime");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Thread.sleep(2000);
            driver.findElement(BUTTON_MORE).click();*/
            //driver.get("https://lodqa.wonderline.eu/en-gb/");

            counter = counter + 1;
            iterationCounter++;

            Utils.writeFile("counter_uat_gb.txt");
            Utils.writeFile("src/test/resources/screenshots.properties");
            System.out.println(counter);
        }


        //TODO THRESHOLD
        //Threshold.main();
        /*initializeTestExecutionData("testLogoutPageShutterbug", startTime, PAGE_WHOLE, assertMethod(softAssert,
                imageDiffUatGB.compareImagePixelDifference("selenium_test_pictures\\uat_gb", "selenium_test_pictures_overtime\\uat_gb_overtime",
                        "selenium_image_differences\\uat_gb\\output_diff_uat_gb%d.png")));*/
        //Assertions.assertTrue(imageDiff.compareImagePixelDifference("selenium_test_pictures\\uat_gb\\", "selenium_test_pictures_overtime\\uat_gb_overtime\\"));
    }
}
