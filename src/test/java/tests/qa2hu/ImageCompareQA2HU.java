package tests.qa2hu;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.CaptureElement;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.example.ImageDiff;
import org.example.mayuse.ImageMerger;
import org.example.pages.qa2pl.login.landing.Header;
import org.example.utils.HighlightImageDiff;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import tests.WebTestTH;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

//TODO added comment from file

public class ImageCompareQA2HU extends WebTestTH {

    private final String qa2huUrl = "https://lodqa2.wonderline.eu/hu-hu";
    private final ImageDiff imageDiffQA2HU = new ImageDiff();
    private String postTimeStamp = "";

    private String[] screenShotImages;
    private List<String> screenShotList = new ArrayList<>();
    private ImageMerger imageMerger;

    protected boolean uniqueLoginQA2HUIN(){
        /*
            driver.findElement(By.id("uuid")).clear();
            driver.findElement(By.id("uuid")).sendKeys("6c4bd68f-bef4-3655-92d8-4e65160d7202");
            driver.findElement(By.xpath("//input[@name=\"return\"]")).click();*/
        //if (!isTestSuiteRun) {
        if(!isTestSuiteRun){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Bejelentkezés")));
                driver.findElement(By.linkText("Bejelentkezés")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"uuid\"]")));
                //WebElement uuidField = driver.findElement(By.id("uuid"));
                WebElement uuidField = driver.findElement(By.xpath("//input[@id=\"uuid\"]"));
                uuidField.clear();
                uuidField.sendKeys("32e1317a-83e2-4fbc-b9a3-3f70b31b587a");

                driver.findElement(By.xpath("//input[@name=\"return\"]")).click();
                return true;
            } catch (TimeoutException e) {
                //System.out.println("UUID field was not found within the timeout: " + e.getMessage());
                return false;
            } catch (Exception e) {
                //System.out.println("An unexpected error occurred: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    protected void uniqueLogout(){
        if(!isTestSuiteRun){
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id=\"loggedin_cell\"]")));
                //WebElement uuidField = driver.findElement(By.id("uuid"));
                driver.findElement(By.xpath("//li[@id=\"loggedin_cell\"]")).click();
                driver.findElement(By.xpath("//li[@id=\"menuLogoutElement\"]")).click();

            } catch (TimeoutException e) {
                System.out.println("UUID field was not found within the timeout: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }

    }

    @Test
    public void testLoginQA2HU(){

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);
        uniqueLoginQA2HUIN();

    }

    @Test
    public void testApiHU() throws IOException {

        String responseBody = apiAuthentication();

        System.out.println(responseBody);

        String authToken = matchAuthTokenRegex(responseBody);

        setPostTimeStamp();

        resetRequestByCardQA2HU(authToken);

    }





    private void resetRequestByCardQA2HU(String authToken) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = RequestBody.create("",  okhttp3.MediaType.parse("text/plain"));
        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/frontend-ws/v1/customer/restore/70043480057093912")
                .method("POST", body)
                .addHeader("x-lod-device-id", "wl_collection")
                .addHeader("x-lod-client-id", "wl_postman")
                .addHeader("x-lod-source-application", "LOD WEB")
                .addHeader("x-lod-market", "hu-HU")
                .addHeader("x-lod-country-code", "HU")
                .addHeader("Authorization", "Bearer " + authToken)
                .addHeader("x-lod-reset-key", "4366343793832978")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("Response Code: " + response.code());

    }

    @Test
    @Order(1)
    public void testLogoutMainPageScreenshotCompareQA2HU() throws IOException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);
        //driver.findElement(By.linkText("Bejelentkezés")).click();

        if (!isTestSuiteRun) {
            uniqueLogout();
        }
        navigateTo(qa2huUrl);

        //takeScrollShutterBugScreenshotOfDivsQA2PL()

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testLogoutMainPageScreenshotCompareQA2HU", "testLogoutMainPageScreenshotCompareQA2HU",
                "testLogoutMainPageScreenshotCompareQA2HU", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testLogoutMainPageScreenshotCompareQA2HU");
        initializeTestExecutionData("testLogoutMainPageScreenshotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }


    @Test
    public void testContactUsPageScreenshotCompareQA2HU() throws IOException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);

        driver.findElement(By.id("footerItem_u34")).click();

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testContactUsPageScreenshotCompareQA2HU", "testContactUsPageScreenshotCompareQA2HU",
                "testContactUsPageScreenshotCompareQA2HU", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testContactUsPageScreenshotCompareQA2HU");
        initializeTestExecutionData("testContactUsPageScreenshotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    @Test
    public void testTermsAndConsScreenshotCompareQA2HU() throws IOException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);
        uniqueLogout();
        navigateTo(qa2huUrl);

        driver.findElement(By.id("footerItem_u46")).click();

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testTermsAndConsScreenshotCompareQA2HU", "testTermsAndConsScreenshotCompareQA2HU",
                "testTermsAndConsScreenshotCompareQA2HU", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testTermsAndConsScreenshotCompareQA2HU");
        initializeTestExecutionData("testTermsAndConsScreenshotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    @Test
    public void testStationLocatorScreenshotCompareQA2HU() throws IOException, InterruptedException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);
        uniqueLogout();
        navigateTo(qa2huUrl);

        driver.findElement(By.id("menuLinkStations")).click();
        Thread.sleep(5000);


        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testStationLocatorScreenshotCompareQA2HU", "testStationLocatorScreenshotCompareQA2HU",
                "testStationLocatorScreenshotCompareQA2HU", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testStationLocatorScreenshotCompareQA2HU");
        initializeTestExecutionData("testStationLocatorScreenshotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    @Test
    public void testFAQsScreenshotCompareQA2HU() throws IOException, InterruptedException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);
        uniqueLogout();
        navigateTo(qa2huUrl);


        driver.findElement(By.id("menu_label_faq")).click();
        List<WebElement> faqsList = driver.findElements(By.xpath("//div[@class=\"collapsible\"]"));

        IntStream.range(0, driver.findElements(By.xpath("//div[@class=\"collapsible\"]")).size()).forEach( i -> {
            faqsList.get(i).click();
        });

        Thread.sleep(2000);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.VIEWPORT, "testFAQsScreenshotCompareQA2HU", "testFAQsScreenshotCompareQA2HU",
                "testFAQsScreenshotCompareQA2HU", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testFAQsScreenshotCompareQA2HU");
        initializeTestExecutionData("testFAQsScreenshotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    @Test
    @Order(2)
    public void testLandingPageScreenshotCompareQA2HU() throws IOException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);

        uniqueLoginQA2HUIN();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.textToBe(By.id("pointTrackersSeparatedRightCellCounterFullWidth"), "10"));

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testLandingPageScreenshotCompareQA2HU", "testLandingPageScreenshotCompareQA2HU",
                "testLandingPageScreenshotCompareQA2HU", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testLandingPageScreenshotCompareQA2HU");
        initializeTestExecutionData("testLandingPageScreenshotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    @Test
    public void testHowToUseScreenshotCompareQA2HU() throws IOException, InterruptedException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);

        uniqueLoginQA2HUIN();

        driver.findElement(By.id("menu_label_go_plus")).click();
        Thread.sleep(5000);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.VIEWPORT, "testHowToUseScreenshotCompareQA2HU", "testHowToUseScreenshotCompareQA2HU",
                "testHowToUseScreenshotCompareQA2HU", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testHowToUseScreenshotCompareQA2HU");
        initializeTestExecutionData("testHowToUseScreenshotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    @Test
    public void testBasketScreenShotCompareQA2HU() throws IOException, InterruptedException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);

        /*if(uniqueLoginQA2HUIN()){
            driver.findElement(By.id("new_basket_button")).click();
        }*/
        uniqueLoginQA2HUIN();
        driver.findElement(By.id("new_basket_button")).click();




        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testBasketScreenShotCompareQA2HU", "testBasketScreenShotCompareQA2HU",
                            "testBasketScreenShotCompareQA2HU", "C\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testBasketScreenShotCompareQA2HU");
        initializeTestExecutionData("testBasketScreenShotCompareQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));

    }




    @Test
    public void testCheckoutFlowQA2HU() throws InterruptedException, IOException {

        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);

        uniqueLoginQA2HUIN();

        driver.findElement(By.id("new_basket_button")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("rewardItemAddToBasketButtonContainer"), 2));

        List<WebElement> catalogItemsBasketBtnList = driver.findElements(By.xpath("//div[@class=\"button rewardItemBasketButton\"]"));


        //add catalog items to basket
        /*catalogItemsBasketBtnList.get(0).click();
        catalogItemsBasketBtnList.get(1).click();
        catalogItemsBasketBtnList.get(3).click();*/

        catalogItemsBasketBtnList = threadSleepAfterBasketInteraction(catalogItemsBasketBtnList, 0, 3000, "//div[@class=\"button rewardItemBasketButton\"]", true);
        //driver.findElement(By.id("back_button_link")).click();
        System.out.println(catalogItemsBasketBtnList.size());
        catalogItemsBasketBtnList = threadSleepAfterBasketInteraction(catalogItemsBasketBtnList, 1, 3000, "//div[@class=\"button rewardItemBasketButton\"]", false);
        threadSleepAfterBasketInteraction(catalogItemsBasketBtnList, 3, 3000, "//div[@class=\"button rewardItemBasketButton\"]", false);
        List<WebElement> catalogItemsPlusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy plus\"]"));
        List<WebElement> catalogItemsMinusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy with-border minus\"]"));

        catalogItemsPlusBtnList.get(0).click();
        catalogItemsPlusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy plus\"]"));
        catalogItemsPlusBtnList.get(0).click();
        catalogItemsPlusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy plus\"]"));
        catalogItemsMinusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy with-border minus\"]"));
        catalogItemsMinusBtnList.get(0).click();

        catalogItemsPlusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy plus\"]"));
        catalogItemsPlusBtnList.get(3).click();
        catalogItemsPlusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy plus\"]"));
        catalogItemsPlusBtnList.get(3).click();

        screenShotList.add(takeScreenShotForMerging(By.xpath("//div[@class=\"content-body new-footer\"]"), CaptureElement.FULL_SCROLL, "basket1"));

        WebElement basketBtn = driver.findElement(By.xpath("//div[@class=\"catalogueInfoButton basketInfoButton basket-catalogue float-right\"]"));
        basketBtn.click();

        screenShotList.add(takeScreenShotForMerging(By.xpath("//div[@class=\"content-body new-footer\"]"), CaptureElement.FULL_SCROLL, "basket2"));

        driver.findElements(By.linkText("Törlés")).getLast().click();

        Thread.sleep(8000);

        catalogItemsPlusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy plus\"]"));
        catalogItemsMinusBtnList = driver.findElements(By.xpath("//td[@class=\"width25 center-align futura-14 futura-heavy with-border minus\"]"));

        screenShotList.add(takeScreenShotForMerging(By.xpath("//div[@class=\"content-body new-footer\"]"), CaptureElement.FULL_SCROLL, "basket3"));

        catalogItemsMinusBtnList.get(0).click();
        catalogItemsMinusBtnList.get(1).click();
        catalogItemsPlusBtnList.get(0).click();

        screenShotList.add(takeScreenShotForMerging(By.xpath("//div[@class=\"content-body new-footer\"]"), CaptureElement.FULL_SCROLL, "basket4"));

        driver.findElements(By.linkText("Törlés")).getLast().click();
        Thread.sleep(8000);
        driver.findElements(By.linkText("Törlés")).getLast().click();
        Thread.sleep(8000);

        screenShotList.add(takeScreenShotForMerging(By.xpath("//div[@class=\"content-body new-footer\"]"), CaptureElement.FULL_SCROLL, "basket5"));
        Thread.sleep(3000);

        screenShotImages = screenShotList.toArray(new String[0]);
        imageMerger = new ImageMerger(screenShotImages, "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_hu_overtime\\", "merged_testCheckoutFlowQA2HU.png");
        imageMerger.mergeImages();

        assertMergedImages(imageDiffQA2HU, "merged_testCheckoutFlowQA2HU", "merged_testCheckoutFlowQA2HU",
                "C\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\merged_testCheckoutFlowQA2HU");
    }

    @Test
    public void testCatalogSiteQA2HU() throws IOException {
        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);

        uniqueLoginQA2HUIN();

        driver.findElement(By.id("new_basket_button")).click();
        //driver.findElement(By.xpath("//a[@href=\"/catalog\"]")).click();
        driver.navigate().to("https://lodqa2.wonderline.eu/hu-hu/catalog");

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testCatalogSiteQA2HU", "testCatalogSiteQA2HU",
                "testCatalogSiteQA2HU", "C\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testCatalogSiteQA2HU");
        initializeTestExecutionData("testCatalogSiteQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    @Test
    public void testTransactionSiteQA2HU() throws IOException {
        Instant startTime = Instant.now();

        softAssert = new SoftAssert();

        navigateTo(qa2huUrl);

        uniqueLoginQA2HUIN();

        driver.findElement(By.xpath("//table[@id=\"transaction_table\"]//following-sibling::a[@href=\"/hu-hu/account/transactions\"]")).click();

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2HU(softAssert, By.xpath("//div[@class=\"content-body new-footer\"]"), imageDiffQA2HU, CaptureElement.FULL_SCROLL, "testTransactionSiteQA2HU", "testTransactionSiteQA2HU",
                "testTransactionSiteQA2HU", "C\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_hu\\testTransactionSiteQA2HU");
        initializeTestExecutionData("testTransactionSiteQA2HU", startTime, By.xpath("//div[@class=\"content-body new-footer\"]"), assertMethod(softAssert, true, isImagesSame));
    }

    //TODO TRX site && Catalog site && !Promo! site && Basket flow

    private List<WebElement> threadSleepAfterBasketInteraction(List<WebElement> webelementList, Integer indexOfInteraction, Integer sleepTime, String updateXpathList, Boolean isFirstAddedItem) throws InterruptedException {
        System.out.println("The size of the webelementList is: " + webelementList.size());
        webelementList.get(indexOfInteraction).click();
        if(isFirstAddedItem){
            driver.findElement(By.id("basketSummaryCatalogButton")).click();
        }
        Thread.sleep(sleepTime);
        return driver.findElements(By.xpath(updateXpathList));
    }

    protected SoftAssert takeScrollShutterBugScreenshotOfDivsQA2HU(SoftAssert softAssert, By byXpath, ImageDiff imageDiff, CaptureElement capture, String originalRoute, String actualRoute, String screenShotName, String savePathIfDiff) throws IOException {

        try {
            Shutterbug.shootElement(driver, driver.findElement(byXpath), capture,true)
                    .withName(String.format(screenShotName/* + ".png"*/))
                    .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_hu_overtime\\");

            //it saves the screenshot inside the project, into the resources folder
            //.save(System.getProperty("user.dir") + "\\src\\main\\resources\\selenium_test_pictures_overtime");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        isImagesSame = imageDiff.compareImagePixelDifferenceBoundedRouteQA2HU(originalRoute + ".png", actualRoute + ".png",
                savePathIfDiff + ".png");

        softAssert.assertTrue(isImagesSame, "The images are different!");

        /*if(driver != null){
            driver.close();
        }*/
        return softAssert;
    }

    protected String takeScreenShotForMerging(By byXpath, CaptureElement capture, String screenShotName) {

        try {
            Shutterbug.shootElement(driver, driver.findElement(byXpath), capture,true)
                    .withName(String.format(screenShotName/* + ".png"*/))
                    .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_hu_overtime\\to_merge\\");

            return "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_hu_overtime\\to_merge\\" + screenShotName;
            //it saves the screenshot inside the project, into the resources folder
            //.save(System.getProperty("user.dir") + "\\src\\main\\resources\\selenium_test_pictures_overtime");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

    }

    protected SoftAssert assertMergedImages(ImageDiff imageDiff, String originalRoute, String actualRoute, String savePathIfDiff) throws IOException {
        isImagesSame = imageDiff.compareImagePixelDifferenceBoundedRouteQA2HU(originalRoute + ".png", actualRoute + ".png",
                savePathIfDiff + ".png");

        softAssert.assertTrue(isImagesSame, "The images are different!");

        return softAssert;
    }

    private void setPostTimeStamp(){
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String formatted = now.format(formatter);
        this.postTimeStamp = formatted;
        System.out.println(formatted);
    }


}
