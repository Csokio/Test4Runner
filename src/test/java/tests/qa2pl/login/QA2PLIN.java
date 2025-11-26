package tests.qa2pl.login;

import com.assertthat.selenium_shutterbug.core.CaptureElement;
import jdk.jfr.Description;
import org.example.ImageDiff;
import org.example.pages.qa2pl.login.basket.BasketPage;
import org.example.pages.qa2pl.login.basket.CatalogPage;
import org.example.pages.qa2pl.login.faqs.FAQsPage;
import org.example.pages.qa2pl.login.landing.*;
import org.example.pages.qa2pl.login.stations.StationLocatorPage;
import org.example.pages.qa2pl.login.transactions.TransactionPage;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import tests.WebTestTH;
import tests.parameterized.ParameterizedLoginQA2PL;
import tests.qa2pl.login.parallel_logged_in.ParallelQA2PLBasket;
import tests.qa2pl.login.subpages.QA2PLBasket;
import tests.qa2pl.login.subpages.QA2PLContactUs;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static tests.parameterized.ParameterizedLoginQA2PL.emailMobilePasswordsPL;

public class QA2PLIN extends WebTestTH {

    private final ParameterizedLoginQA2PL parameterizedLoginQA2PL = new ParameterizedLoginQA2PL();
    private final ImageDiff imageDiffQA2PL = new ImageDiff();

    //protected SoftAssert softAssert = new SoftAssert();
    //private final QA2PLContactUs qa2PLContactUs = new QA2PLContactUs();

    protected WebTestTH webTestTH = new WebTestTH();

    Queue<String> uuidCredentials = new ArrayDeque<>(Arrays.asList(
            "5e4c3796-99f3-3527-9e64-fee00808d6c7",
            "6c4bd68f-bef4-3655-92d8-4e65160d7202"
    ));

    //RUNTEST MULTIPLE LOGIN METHOD'S BEST USE CASE FOR BEING A TEST SUITE FOR API TESTS
    public void runTest(SoftAssert softAssert) throws IOException, InterruptedException {
        //this.softAssert = new SoftAssert();

        // pointCompensationThroughMUI();
        testHeaderValuesQA2PL();
        testHeaderImageUrlQA2PL();
        testHeaderAbsoluteLocationsQA2PL();

        //testBuy();
        //this.softAssert.assertAll();
        //This way every test methods can run and if there's any fault exception occurs only after parameterized test ran
    }


    public void runTest(SoftAssert softAssert, String uuid) throws IOException, InterruptedException {
        //this.softAssert = new SoftAssert();

        //simplePointCompensationThroughMUI(uuid);
        testHeaderValuesQA2PL();
        testHeaderImageUrlQA2PL();
        testHeaderAbsoluteLocationsQA2PL();
        //testBuy();
        //this.softAssert.assertAll();
        //This way every test methods can run and if there's any fault exception occurs only after parameterized test ran
    }

    // if no softAssert in method parameter testMultipleLogin/testLogin throws immediately exception if an assertion fails. -- unfortunately in this case also
    public void runTest(String uuid, SoftAssert softAssert) throws IOException, InterruptedException {
            QA2PLContactUs qa2PLContactUs = new QA2PLContactUs(softAssert);
            //QA2PLBasket qa2PLBasket = new QA2PLBasket(softAssert);
            ParallelQA2PLBasket parallelQA2PLBasket = new ParallelQA2PLBasket(softAssert);
            qa2PLContactUs.testContactUsPaddingsQA2PL();
            qa2PLContactUs.testGoToContactUsPageQA2PL();
            qa2PLContactUs.testSubmitBtnInvalidEmailAndCaptchaForm();
            parallelQA2PLBasket.testInteractiveBasketPageElementsQA2PL();

    }




    @Test
    public void testGetUuidsFromMuiQA2PL() throws InterruptedException {
        getUuidsFromMuiQA2PL();
    }

    @Test
    public void testLogin() throws InterruptedException, IOException, RuntimeException {
        isTestSuiteRun = true;
        parameterizedLoginQA2PL.testLoginWithMultipleUsers();
        isTestSuiteRun = false;
    }


    @Test
    public void AAADummyBoolean(){
        Instant startTime = Instant.now();
        boolean isAlwaysTrue = true;
        Assertions.assertTrue(isAlwaysTrue);
        initializeTestExecutionData("AAADummyBoolean", startTime, true);
    }

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
                uuidField.sendKeys("6c4bd68f-bef4-3655-92d8-4e65160d7202");
                //uuidField.sendKeys(uuidCredentials.poll());

                driver.findElement(By.xpath("//input[@name=\"return\"]")).click();
            } catch (TimeoutException e) {
                System.out.println("UUID field was not found within the timeout: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    protected void uniqueTestBuyLoginQA2PLIN(){
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
                //uuidField.sendKeys(uuidCredentials.poll());

                driver.findElement(By.xpath("//input[@name=\"return\"]")).click();
            } catch (TimeoutException e) {
                System.out.println("UUID field was not found within the timeout: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    //@Test
    private void pointCompensationThroughMUI(String compensatedPoints) throws InterruptedException {
        Collection<String[]> data = emailMobilePasswordsPL();

        List<String> uuids = data.stream()
                .map(array -> array[1])
                .collect(Collectors.toList());

        uuids.forEach(System.out::println);

        IntStream.range(0, uuids.size()).forEach(i -> {
            navigateTo("https://lodqa2.wonderline.eu/lod-auth/login");
            driver.findElement(By.id("username")).sendKeys("WLPLKSO1");
            driver.findElement(By.id("password")).sendKeys("bbBB66!!");
            driver.findElement(By.xpath("//input[@value=\"Login\"]")).click();

            driver.findElement(By.xpath("//a[@href=\"search\"]")).click();
            driver.findElement(By.id("input-vaadin-text-field-57")).sendKeys(uuids.get(i));
            driver.findElement(By.xpath("//vaadin-button[@aria-label=\"Search\"]")).click();
            driver.findElement(By.xpath("//vaadin-grid-cell-content[@slot=\"vaadin-grid-cell-content-23\"]")).click();

           driver.findElement(By.xpath("//*[@id=\"main-root\"]/div/div[2]/div[1]/div[2]/div[2]/vaadin-tabs"));

            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }

            driver.findElement(By.xpath("//*[@id=\"main-root\"]/div/div[2]/div[1]/div[2]/div[2]/vaadin-tabs/vaadin-tab[7]")).click();
            driver.findElement(By.xpath("//*[@id=\"main-root\"]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/vaadin-horizontal-layout/vaadin-button")).click();

            driver.findElement(By.xpath("//*[@id=\"input-vaadin-combo-box-153\"]")).sendKeys("Point Based");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            driver.findElement(By.xpath("//*[@id=\"input-vaadin-combo-box-155\"]")).clear();
            driver.findElement(By.xpath("//*[@id=\"input-vaadin-combo-box-155\"]")).sendKeys("Compensation points by Loyalty CSC");
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            driver.findElement(By.xpath("//*[@id=\"input-vaadin-integer-field-156\"]")).sendKeys(compensatedPoints);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            driver.findElement(By.xpath("//*[@id=\"overlay\"]/div/div[2]/vaadin-button[1]")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            /*wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//vaadin-button[@aria-label='Save']")));
            driver.findElement(By.xpath("//vaadin-button[@aria-label='Save']")).click();*/

            WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//vaadin-button[@aria-label=\"Yes\"]")));


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//vaadin-button[@aria-label=\"Yes\"]")));
            driver.findElement(By.xpath("//vaadin-button[@aria-label=\"Yes\"]")).click();
            /*robot.mouseMove(895, 650);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);*/

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private void singlePointCompensationThroughMUI(String uuid){

        navigateTo("https://lodqa2.wonderline.eu/lod-auth/login");
        driver.findElement(By.id("username")).sendKeys("WLPLKSO1");
        driver.findElement(By.id("password")).sendKeys("bbBB66!!");
        driver.findElement(By.xpath("//input[@value=\"Login\"]")).click();

        driver.findElement(By.xpath("//a[@href=\"search\"]")).click();
        driver.findElement(By.id("input-vaadin-text-field-57")).sendKeys(uuid);
        driver.findElement(By.xpath("//vaadin-button[@aria-label=\"Search\"]")).click();
        driver.findElement(By.xpath("//vaadin-grid-cell-content[@slot=\"vaadin-grid-cell-content-23\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"main-root\"]/div/div[2]/div[1]/div[2]/div[2]/vaadin-tabs"));

        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id=\"main-root\"]/div/div[2]/div[1]/div[2]/div[2]/vaadin-tabs/vaadin-tab[7]")).click();
        driver.findElement(By.xpath("//*[@id=\"main-root\"]/div/div[2]/div[2]/div/div[1]/div/div[2]/div/vaadin-horizontal-layout/vaadin-button")).click();

        driver.findElement(By.xpath("//*[@id=\"input-vaadin-combo-box-153\"]")).sendKeys("Point Based");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id=\"input-vaadin-combo-box-155\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"input-vaadin-combo-box-155\"]")).sendKeys("Compensation points by Loyalty CSC");
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.findElement(By.xpath("//*[@id=\"input-vaadin-integer-field-156\"]")).sendKeys("20");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id=\"overlay\"]/div/div[2]/vaadin-button[1]")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        /*wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//vaadin-button[@aria-label='Save']")));
        driver.findElement(By.xpath("//vaadin-button[@aria-label='Save']")).click();*/

        /*WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//vaadin-button[@aria-label='Save']")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//vaadin-button[@aria-label='Save']")));
        System.out.println("Save btn's location:" + driver.findElement(By.xpath("//vaadin-button[@aria-label='Save']")));*/

        WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//vaadin-button[@aria-label=\"Yes\"]")));


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//vaadin-button[@aria-label=\"Yes\"]")));
        driver.findElement(By.xpath("//vaadin-button[@aria-label=\"Yes\"]")).click();

       /* robot.mouseMove(895, 650);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);*/

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void checkUserPoints() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement pointsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("pointTrackersSeparatedRightCellCounterFullWidth")
            ));

            String pointsText = pointsElement.getText();
            int points = Integer.parseInt(pointsText.replaceAll("[^0-9]", ""));

            if (points < 10) {
                System.out.println("User has less than 10 points, actually: " + points + " points");
                singlePointCompensationThroughMUI("6c4bd68f-bef4-3655-92d8-4e65160d7202");
            } else {
                System.out.println("User has enough points: " + points + " points");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while checking user points: " + e.getMessage());
        }
    }
    @Test
    public void testBuyQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        //pointCompensationThroughMUI();

        Header header = new Header(driver);

        //softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        if(!isTestSuiteRun){
            uniqueTestBuyLoginQA2PLIN();
        }

        Thread.sleep(5000);
        checkUserPoints();

        /*String pointsText = driver.findElement(By.id("pointTrackersSeparatedRightCellCounterFullWidth")).getText();
        int points = Integer.parseInt(pointsText.replaceAll("^[0-9]", ""));
        if(points < 20){
            System.out.println("User has less than 20 points, actually: " + points + " points");
            //singlePointCompensationThroughMUI("6c4bd68f-bef4-3655-92d8-4e65160d7202");
        }*/

        navigateTo(header.getUrl());

        driver.findElement(By.id("rewardItemAddToBasketButton_u157")).click();
        driver.findElement(By.xpath("(//td[@class=\"width25 center-align futura-14 futura-heavy plus\"])[3]")).click();

        Thread.sleep(2000);
        driver.findElement(By.id("menu_label_basket_2")).click();
        driver.findElement(By.id("basketSummaryRedeemButton")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("basketSummaryRedeemButton")).click();
        Thread.sleep(2000);

        Integer actualValue = Integer.parseInt(driver.findElement(By.id("basketSummaryBalanceValue")).getText());
        Integer expectedValue = actualValue - 10;

        driver.findElement(By.id("basketSummaryRedeemButton")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("basketConfirmDialogSubmitButton")).click();

        actualValue = Integer.parseInt(driver.findElement(By.id("basketSummaryBalanceValue")).getText());
        //Integer expectedValue = 0;

        //TODO id="loggedin_activator_label" GETS INTERCEPTED BY POPUP IN THE END OF THE TRANSACTION
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class=\"modal-popup basketCompleteModal\"]"))));
        //softAssert.assertEquals(actualValue, expectedValue);

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, By.id("simple_content"), imageDiffQA2PL, "testBuyQA2PL", "testBuyQA2PL",
                "testBuyQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBuyQA2PL");
        initializeTestExecutionData("testBuyQA2PL", startTime, By.id("simple_content"), assertMethod(softAssert, actualValue, expectedValue, isImagesSame));
    }


    @Test
    public void testHeaderImageUrlQA2PL() throws IOException {

        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        if (!isTestSuiteRun) {
            uniqueLoginQA2PLIN();
        }

        String[] expectedImageUrl = {
                "https://shell247b-pl.lod.club/cdn/b/deriv/6f/3f29fe5ad85360c7b3e2908f5b8735/231x55.png?shell_logo_1.png1",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/f3/8c0298015f8d05058d5a4928eff2bd/84x84.png?StationLocator.png",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/6a/22d0a761b71d80e8820e0808d43f4e/85x84.png?85x84.png",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/6b/b959dedba70ecfa476e8854db3527f/20x16.png?Artboard%2012-50.jpg.png",
                "https://lodqa2.wonderline.eu/cdn/static/flat/d5/1c963b255b1bc27447c0ec9424727a/help_icon.png",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/a1/64c5302e43efa6e0ff965887d9bb2b/212x212.jpg?Artboard%2016-50.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/35/edb82da41b8dd0226e564035e2d5f0/20x21.jpg?Artboard%2018-50.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/6b/b959dedba70ecfa476e8854db3527f/20x16.jpg?Artboard%2012-50.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/c2/cc78567e00e97252b40e36e2c32402/20x20.jpg?Artboard%2013-50.jpg",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/93/30dd8fb2bd804793daf2b6719085a5/20x22.jpg?Artboard%2019-50.jpg"
        };
        String[] actualImageUrl = header.imageUrl().split(",");

        System.out.println("The size of the actualImageUrl is: " + actualImageUrl.length);
        for (String imageUrl : actualImageUrl) {
            System.out.println(imageUrl);
        }

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        //softAssert.assertTrue(header.saveImageToFile());

        //softAssert.assertAll();


        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, header.getHEADER_DIV(), imageDiffQA2PL, "testHeaderImageUrlQA2PL", "testHeaderImageUrlQA2PL",
                "testHeaderImageUrlQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testHeaderImageUrlQA2PL");
        initializeTestExecutionData("testHeaderImageUrlQA2PL", startTime, header.getHEADER_DIV(), assertMethod(softAssert, header.saveImageToFile(), isImagesSame));


    }

    /*@Test
    public void testHeaderImageUrlQA2PL2() throws IOException {
        while (uuidCredentials.peek() != null) {
            System.out.println("Testing with UUID: " + uuidCredentials.peek());

            try {
                Instant startTime = Instant.now();
                Header header = new Header(driver);

                // Initialize SoftAssert for each user
                softAssert = new SoftAssert();

                navigateTo(header.getUrl()); // Navigate to the test URL

                // Perform login if required
                if (!isTestSuiteRun) {
                    uniqueLoginQA2PLIN(); // Pass the UUID to login
                }

                String[] expectedImageUrl = {
                        "https://shell247b-pl.lod.club/cdn/b/deriv/6f/3f29fe5ad85360c7b3e2908f5b8735/231x55.png?shell_logo_1.png",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/f3/8c0298015f8d05058d5a4928eff2bd/84x84.png?StationLocator.png",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/6a/22d0a761b71d80e8820e0808d43f4e/85x84.png?85x84.png",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/6b/b959dedba70ecfa476e8854db3527f/20x16.png?Artboard%2012-50.jpg.png",
                        "https://lodqa2.wonderline.eu/cdn/static/flat/d5/1c963b255b1bc27447c0ec9424727a/help_icon.png",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/a1/64c5302e43efa6e0ff965887d9bb2b/212x212.jpg?Artboard%2016-50.jpg",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/35/edb82da41b8dd0226e564035e2d5f0/20x21.jpg?Artboard%2018-50.jpg",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/6b/b959dedba70ecfa476e8854db3527f/20x16.jpg?Artboard%2012-50.jpg",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/c2/cc78567e00e97252b40e36e2c32402/20x20.jpg?Artboard%2013-50.jpg",
                        "https://lodqa2.wonderline.eu/cdn/b/deriv/93/30dd8fb2bd804793daf2b6719085a5/20x22.jpg?Artboard%2019-50.jpg"
                };
                String[] actualImageUrl = header.imageUrl().split(",");

                System.out.println("The size of the actualImageUrl is: " + actualImageUrl.length);
                for (String imageUrl : actualImageUrl) {
                    System.out.println(imageUrl);
                }

                // Validate image URLs
                IntStream.range(0, expectedImageUrl.length).forEach(i ->
                        softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i],
                                "Mismatch in image URL at index " + i)
                );

                // Take screenshots (if applicable)
                softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, header.getHEADER_DIV(), imageDiffQA2PL,
                        "testHeaderImageUrlQA2PL", "testHeaderImageUrlQA2PL",
                        "testHeaderImageUrlQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testHeaderImageUrlQA2PL");

                // Finalize test execution data
                initializeTestExecutionData("testHeaderImageUrlQA2PL", startTime, header.getHEADER_DIV(),
                        assertMethod(softAssert, header.saveImageToFile(), isImagesSame));

            } catch (Exception e) {
                System.err.println("Test failed for UUID: " + uuidCredentials.peek());
                e.printStackTrace();
            } finally {
                // Ensure assertions are run after each iteration
                softAssert.assertAll();
            }
        }
    }*/

    @Test
    public void testHeaderValuesQA2PL() throws IOException {

        Instant startTime = Instant.now();
        Header header = new Header(driver);

        //softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        List<String[]> actualValues = header.valuesList();
        String[] headerValues = {"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"};

        List<String[]> expectedValues = new LinkedList<>();
       IntStream.range(0,9).forEach(i -> expectedValues.add(headerValues));
        for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/


        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, header.getHEADER_DIV(), imageDiffQA2PL, "testHeaderValuesQA2PL", "testHeaderValuesQA2PL",
                "testHeaderValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testHeaderValuesQA2PL");
        initializeTestExecutionData("testHeaderValuesQA2PL", startTime, header.getHEADER_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testReworkedHeaderValuesQA2PL(){
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        LinkedHashMap<Integer, LinkedHashMap<String, String[]>> actualMap = header.reworkedValuesList();
        IntStream.range(0, actualMap.size()).forEach(i -> {
            LinkedHashMap<String, String[]> stringLinkedHashMap = actualMap.get(i);
            Map.Entry<String, String[]> entry = stringLinkedHashMap.firstEntry();
            System.out.println(entry.getKey());
            System.out.println(Arrays.toString(entry.getValue()));
        });

        LinkedHashMap<Integer, LinkedHashMap<String, String[]>> expectedMap = new LinkedHashMap<>();
        //expectedMap.put(0, (LinkedHashMap<String, String[]>) new LinkedHashMap<>().put("id(\"menu_label_stations\")", new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"}));
        Queue<String> innerMapKey = new ArrayDeque<>(List.of("id(\"menu_label_stations\")", "id(\"menu_label_go_plus\")", "id(\"menu_label_basket_2\")", "id(\"menu_label_faq\")", "id(\"loggedin_activator_label\")",
                "id(\"menu_label_overview\")", "id(\"menu_label_basket\")", "id(\"menu_label_contact_us\")", "id(\"menu_label_logout\")"));
        /*IntStream.range(0,9).forEach(i -> {
            expectedMap.put(i, (LinkedHashMap<String, String[]>) new LinkedHashMap<>().put(innerMapKey.poll(), new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"}));
        });*/
        IntStream.range(0, 9).forEach(i -> {
            LinkedHashMap<String, String[]> innerMap = new LinkedHashMap<>();
            innerMap.put(innerMapKey.poll(), new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(64, 64, 64, 1)"});
            expectedMap.put(i, innerMap);
        });


        for(int i = 0; i < actualMap.size(); i++){
            LinkedHashMap<String, String[]> actualInnerMap = actualMap.get(i);
            LinkedHashMap<String, String[]> expectedInnerMap = expectedMap.get(i);

            Map.Entry<String, String[]> actualEntry = actualInnerMap.firstEntry();
            Map.Entry<String, String[]> expectedEntry = expectedInnerMap.firstEntry();

            /*softAssert.assertTrue(actualEntry.getKey().equals(expectedEntry.getKey()));
            softAssert.assertEquals(actualEntry.getValue(), expectedEntry.getValue());*/

            boolean keyMatches = actualEntry.getKey().equals(expectedEntry.getKey());
            if (!keyMatches) {
                System.out.println("Key mismatch at index " + i);
                System.out.println("Actual Key: " + actualEntry.getKey());
                System.out.println("Expected Key: " + expectedEntry.getKey());
            }

            boolean valuesMatch = Arrays.equals(actualEntry.getValue(), expectedEntry.getValue());
            if (!valuesMatch) {
                System.out.println("Values mismatch at index " + i);
                System.out.println("Actual Values: " + Arrays.toString(actualEntry.getValue()));
                System.out.println("Expected Values: " + Arrays.toString(expectedEntry.getValue()));
            }

            softAssert.assertTrue(keyMatches);
            softAssert.assertEquals(actualEntry.getValue(), expectedEntry.getValue());
        }
        softAssert.assertAll();
    }

    @Test
    public void testReworkedHeaderPaddingQA2PL(){
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String[][] actualValues = header.reworkedGetPaddings();
        for(int i = 0; i < actualValues.length; i++){
            for(int j = 0; j < actualValues[i].length; j++){
                System.out.println(actualValues[i][j]);
            }
        }
        System.out.println("The size of the actual reworked paddings are: " + actualValues.length);

        Queue<String> labelQueue = new ArrayDeque<>(List.of("id(\"menu_label_stations\")", "id(\"menu_label_go_plus\")", "id(\"menu_label_basket_2\")", "id(\"menu_label_faq\")", "id(\"loggedin_activator_label\")",
                "id(\"menu_label_overview\")", "id(\"menu_label_basket\")", "id(\"menu_label_contact_us\")", "id(\"menu_label_logout\")"));
        Queue<String> labelQueueMargin = new ArrayDeque<>();

        String[][] expectedValues = new String[72][2];
        IntStream.iterate(0, i -> i + 4)
                .takeWhile(i -> i < 72)
                .forEach(i -> {
                    if (i < 36) {
                        String label = labelQueue.poll();
                        if (i > 19 && i < 32) {
                            labelQueueMargin.add(label);
                            expectedValues[i][0] = label;
                            expectedValues[i + 1][0] = label;
                            expectedValues[i + 2][0] = label;
                            expectedValues[i + 3][0] = label;
                            expectedValues[i][1] = "padding-top: 2px";
                            expectedValues[i + 1][1] = "padding-right: 0px";
                            expectedValues[i + 2][1] = "padding-bottom: 0px";
                            expectedValues[i + 3][1] = "padding-left: 30px";
                        } else {
                            labelQueueMargin.add(label);
                            expectedValues[i][0] = label;
                            expectedValues[i + 1][0] = label;
                            expectedValues[i + 2][0] = label;
                            expectedValues[i + 3][0] = label;
                            expectedValues[i][1] = "padding-top: 2px";
                            expectedValues[i + 1][1] = "padding-right: 0px";
                            expectedValues[i + 2][1] = "padding-bottom: 0px";
                            expectedValues[i + 3][1] = "padding-left: 0px";
                        }
                    }  else {
                        String label = labelQueueMargin.poll();
                        expectedValues[i][0] = label;
                        expectedValues[i+1][0] = label;
                        expectedValues[i+2][0] = label;
                        expectedValues[i+3][0] = label;
                        expectedValues[i][1] = "margin-top: 0px";
                        expectedValues[i+1][1] = "margin-right: 0px";
                        expectedValues[i+2][1] = "margin-bottom: 0px";
                        expectedValues[i+3][1] = "margin-left: 0px";
                    }
        });
        System.out.println("The size of the expected reworked paddings are: " + expectedValues.length);

        System.out.println("Expected values paddings: ");
        for(int i = 0; i < expectedValues.length; i++){
            for(int j = 0; j < expectedValues[i].length; j++){
                System.out.println(expectedValues[i][j]);
            }
        }

        Assertions.assertArrayEquals(expectedValues, actualValues);
    }

    @Test
    public void testHeaderAbsoluteLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

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
                new Integer[]{162, 17},
                new Integer[]{768, 0},
                new Integer[]{959, 0},
                new Integer[]{1150, 0},
                new Integer[]{1341, 0},
                new Integer[]{1532, 0},
                new Integer[]{162, 24},
                new Integer[]{805, 25},
                new Integer[]{997, 25},
                new Integer[]{1194, 25},
                new Integer[]{1401, 25},
                new Integer[]{1571, 25},
                new Integer[]{1488, 84},
                new Integer[]{1488, 134},
                new Integer[]{1488, 184},
                new Integer[]{1488, 234},
                new Integer[]{1513, 99},
                new Integer[]{1513, 149},
                new Integer[]{1513, 199},
                new Integer[]{1513, 249}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

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

        //softAssert.assertAll();

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, header.getHEADER_DIV(), imageDiffQA2PL, "testHeaderAbsoluteLocationsQA2PL", "testHeaderAbsoluteLocationsQA2PL",
                "testHeaderAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testHeaderAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testHeaderAbsoluteLocationsQA2PL", startTime, header.getHEADER_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testHeaderRelativeLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        Header header = new Header(driver);

        softAssert = new SoftAssert();

        navigateTo(header.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

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
                new Integer[]{606, -17},
                new Integer[]{191, 0},
                new Integer[]{191, 0},
                new Integer[]{191, 0},
                new Integer[]{191, 0},
                new Integer[]{-1370, 24},
                new Integer[]{643, 1},
                new Integer[]{192, 0},
                new Integer[]{197, 0},
                new Integer[]{207, 0},
                new Integer[]{170, 0},
                new Integer[]{-83, 59},
                new Integer[]{0, 50},
                new Integer[]{0, 50},
                new Integer[]{0, 50},
                new Integer[]{25, -135},
                new Integer[]{0, 50},
                new Integer[]{0, 50},
                new Integer[]{0, 50}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

       /* IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, header.getHEADER_DIV(), imageDiffQA2PL, "testHeaderRelativeLocationsQA2PL", "testHeaderRelativeLocationsQA2PL",
                "testHeaderRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testHeaderRelativeLocationsQA2PL");
        initializeTestExecutionData("testHeaderRelativeLocationsQA2PL", startTime, header.getHEADER_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }


    @Test
    public void testPointTrackerImageUrlQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        PointTracker pointTracker = new PointTracker(driver);

        softAssert = new SoftAssert();

        navigateTo(pointTracker.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Thread.sleep(4000);

        String[] expectedImageUrl = {
                "https://lodqa2.wonderline.eu/cdn/b/deriv/c0/7dde24debbd0b281f061b439fff1aa/1171x360.png?points_to_cash_bg.png",
                "https://lodqa2.wonderline.eu/cdn/b/deriv/a5/cd5dec4ef33b646f565665d0a60b1c/18x12.jpg?money.jpg"
        };
        String[] actualImageUrl = pointTracker.imageUrl().split(",");

        System.out.println("The size of the actualImageUrl is: "  +  actualImageUrl.length);
        /*for(String imageUrl: actualImageUrl){
            System.out.println(imageUrl);
        }*/

        /*IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        softAssert.assertTrue(pointTracker.saveImageToFile());

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, pointTracker.getPOINT_TRACKER_DIV(), imageDiffQA2PL, "testPointTrackerImageUrlQA2PL", "testPointTrackerImageUrlQA2PL",
                "testPointTrackerImageUrlQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPointTrackerImageUrlQA2PL");
        initializeTestExecutionData("testPointTrackerImageUrlQA2PL", startTime, pointTracker.getPOINT_TRACKER_DIV(), assertMethod(softAssert, actualImageUrl, expectedImageUrl, isImagesSame));
    }

    @Test
    public void testPointTrackerValuesQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        PointTracker pointTracker = new PointTracker(driver);

        softAssert = new SoftAssert();

        navigateTo(pointTracker.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Thread.sleep(4000);

        List<String[]> actualValues = pointTracker.valuesList();
        List<String[]> expectedValues = new ArrayList<>();

        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "27.889px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "13.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "52px", "rgba(255, 255, 255, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(255, 255, 255, 1)"});

        /*for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }*/

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, pointTracker.getPOINT_TRACKER_DIV(), imageDiffQA2PL, "testPointTrackerValuesQA2PL", "testPointTrackerValuesQA2PL",
                "testPointTrackerValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPointTrackerValuesQA2PL");
        initializeTestExecutionData("testPointTrackerValuesQA2PL", startTime, pointTracker.getPOINT_TRACKER_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testPointTrackerPaddingsQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        PointTracker pointTracker = new PointTracker(driver);

        softAssert = new SoftAssert();

        navigateTo(pointTracker.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Thread.sleep(1000);

        Integer[] actualPaddings = pointTracker.getPaddings();
        Integer[] expectedPaddings = new Integer[]{29, 20, 5, 29, 15, 20, 15, 15, 9, 147, 19, 147};

        for (Integer padding : actualPaddings) {
            System.out.println(padding);
        }

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, pointTracker.getPOINT_TRACKER_DIV(), imageDiffQA2PL, "testPointTrackerPaddingsQA2PL", "testPointTrackerPaddingsQA2PL",
                "testPointTrackerPaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPointTrackerPaddingsQA2PL");
        initializeTestExecutionData("testPointTrackerPaddingsQA2PL", startTime, pointTracker.getPOINT_TRACKER_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testPointTrackerAbsoluteLocationsQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        PointTracker pointTracker = new PointTracker(driver);

        softAssert = new SoftAssert();

        navigateTo(pointTracker.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Thread.sleep(4000);

        pointTracker.setLocationsList();
        pointTracker.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = pointTracker.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{149, 216},
                new Integer[]{149, 270},
                new Integer[]{149, 318},
                new Integer[]{149, 438},
                new Integer[]{186, 458},
                new Integer[]{574, 121},
                new Integer[]{574, 256},
                new Integer[]{574, 320}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, pointTracker.getPOINT_TRACKER_DIV(), imageDiffQA2PL, "testPointTrackerAbsoluteLocationsQA2PL", "testPointTrackerAbsoluteLocationsQA2PL",
                "testPointTrackerAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPointTrackerAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testPointTrackerAbsoluteLocationsQA2PL", startTime, pointTracker.getPOINT_TRACKER_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testPointTrackerRelativeLocationsQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();
        PointTracker pointTracker = new PointTracker(driver);

        softAssert = new SoftAssert();

        navigateTo(pointTracker.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Thread.sleep(4000);

        pointTracker.setLocationsList();
        pointTracker.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = pointTracker.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{0, 54},
                new Integer[]{0, 48},
                new Integer[]{0, 120},
                new Integer[]{37, 20},
                new Integer[]{388, -337},
                new Integer[]{0, 135},
                new Integer[]{0, 64}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, pointTracker.getPOINT_TRACKER_DIV(), imageDiffQA2PL, "testPointTrackerRelativeLocationsQA2PL", "testPointTrackerRelativeLocationsQA2PL",
                "testPointTrackerRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPointTrackerRelativeLocationsQA2PL");
        initializeTestExecutionData("testPointTrackerRelativeLocationsQA2PL", startTime, pointTracker.getPOINT_TRACKER_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testPartnerOfferValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();
        PartnerOffer partnerOffer = new PartnerOffer(driver);

        softAssert = new SoftAssert();

        navigateTo(partnerOffer.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        List<String[]> actualValues = partnerOffer.valuesList();
        List<String[]> expectedValues = new ArrayList<>();

        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"});

        for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, partnerOffer.getPARTNER_OFFER_DIV(), imageDiffQA2PL, "testPartnerOfferValuesQA2PL", "testPartnerOfferValuesQA2PL",
                "testPartnerOfferValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPartnerOfferValuesQA2PL");
        initializeTestExecutionData("testPartnerOfferValuesQA2PL", startTime, partnerOffer.getPARTNER_OFFER_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testPartnerOfferPaddingsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        PartnerOffer partnerOffer = new PartnerOffer(driver);

        softAssert = new SoftAssert();

        navigateTo(partnerOffer.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Integer[] actualPaddings = partnerOffer.getPaddings();
        Integer[] expectedPaddings = new Integer[]{47, 5, 15, 3, 15, 20, 15, 3, 15, 21, 3, 147, 47, 14, 147, -2};

        for (Integer padding : actualPaddings) {
            System.out.println(padding);
        }

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, partnerOffer.getPARTNER_OFFER_DIV(), imageDiffQA2PL, "testPartnerOfferPaddingsQA2PL", "testPartnerOfferPaddingsQA2PL",
                "testPartnerOfferPaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPartnerOfferPaddingsQA2PL");
        initializeTestExecutionData("testPartnerOfferPaddingsQA2PL", startTime, partnerOffer.getPARTNER_OFFER_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testPartnerOfferAbsoluteLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        PartnerOffer partnerOffer = new PartnerOffer(driver);

        softAssert = new SoftAssert();

        navigateTo(partnerOffer.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        partnerOffer.setLocationsList();
        partnerOffer.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = partnerOffer.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{147, 607},
                new Integer[]{147, 654},
                new Integer[]{147, 755},
                new Integer[]{150, 758},
                new Integer[]{150, 758},
                new Integer[]{166, 1002},
                new Integer[]{166, 1002},
                new Integer[]{166, 1021}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, partnerOffer.getPARTNER_OFFER_DIV(), imageDiffQA2PL, "testPartnerOfferAbsoluteLocationsQA2PL", "testPartnerOfferAbsoluteLocationsQA2PL",
                "testPartnerOfferAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPartnerOfferAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testPartnerOfferAbsoluteLocationsQA2PL", startTime, partnerOffer.getPARTNER_OFFER_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testPartnerOfferRelativeLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        PartnerOffer partnerOffer = new PartnerOffer(driver);

        softAssert = new SoftAssert();

        navigateTo(partnerOffer.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        partnerOffer.setLocationsList();
        partnerOffer.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = partnerOffer.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{0, 47},
                new Integer[]{0, 101},
                new Integer[]{3, 3},
                new Integer[]{0, 0},
                new Integer[]{16, 244},
                new Integer[]{0, 0},
                new Integer[]{0, 19}
        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, partnerOffer.getPARTNER_OFFER_DIV(), imageDiffQA2PL, "testPartnerOfferRelativeLocationsQA2PL", "testPartnerOfferRelativeLocationsQA2PL",
                "testPartnerOfferRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testPartnerOfferRelativeLocationsQA2PL");
        initializeTestExecutionData("testPartnerOfferRelativeLocationsQA2PL", startTime, partnerOffer.getPARTNER_OFFER_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testCatalogueSectionImageUrlQA2PL() throws IOException {

        Instant startTime = Instant.now();
        CatalogueSection catalogueSection = new CatalogueSection(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogueSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String[] expectedImageUrl = {

                "https://lodqa2.wonderline.eu/cdn/b/deriv/98/ab7a9019eee296ce8cc42c8d371542/40x40.jpg?spider.spend_points.delivery.icon.url.home.40.png.jpg",
                "https://lodqa2.wonderline.eu/cdn/static/flat/6b/b3e37d20730bea4e6949f5f60c26b4/basket.png"
        };
        String[] actualImageUrl = catalogueSection.imageUrl().split(",");

        System.out.println("The size of the actualImageUrl is: "  +  actualImageUrl.length);
        for(String imageUrl: actualImageUrl){
            System.out.println(imageUrl);
        }

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        /*softAssert.assertTrue(catalogueSection.saveImageToFile());

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, catalogueSection.getCATALOGUE_DIV(), imageDiffQA2PL, "testCatalogueSectionImageUrlQA2PL", "testCatalogueSectionImageUrlQA2PL",
                "testCatalogueSectionImageUrlQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogueSectionImageUrlQA2PL");
        initializeTestExecutionData("testCatalogueSectionImageUrlQA2PL", startTime, catalogueSection.getCATALOGUE_DIV(), assertMethod(softAssert, catalogueSection.saveImageToFile(), isImagesSame));
    }

    @Test
    public void testCatalogueSectionValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();
        CatalogueSection catalogueSection = new CatalogueSection(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogueSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        List<String[]> actualValues = catalogueSection.valuesList();
        List<String[]> expectedValues = new ArrayList<>();

        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "27.8612px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.889px", "rgba(64, 64, 64, 1)"});

        IntStream.range(0,4).forEach(i -> {
            expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(51, 51, 51, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"});
        });

        IntStream.range(0,3).forEach(i -> {
            expectedValues.add(new String[]{"FuturaStd-Heavy, Arial, Verdana", "15.9445px", "rgba(235, 135, 5, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(51, 51, 51, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9722px", "rgba(64, 64, 64, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15.9722px", "rgba(89, 89, 89, 1)"});
            expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9584px", "rgba(64, 64, 64, 1)"});
        });

        /*for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }*/

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });*/

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, catalogueSection.getCATALOGUE_DIV(), imageDiffQA2PL, "testCatalogueSectionValuesQA2PL", "testCatalogueSectionValuesQA2PL",
                "testCatalogueSectionValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogueSectionValuesQA2PL");
        initializeTestExecutionData("testCatalogueSectionValuesQA2PL", startTime, catalogueSection.getCATALOGUE_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testCatalogueSectionPaddingsQA2PL() throws IOException {

        Instant startTime = Instant.now();
        CatalogueSection catalogueSection = new CatalogueSection(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogueSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Integer[] actualPaddings = catalogueSection.getPaddings();
        Integer[] expectedPaddings = new Integer[] {
                29, 14, 18, 7, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5,
                5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 7, 5, 5, 7, 15, 20, 5, 5, 7, 15,
                20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15,
                20, 5, 5, 7, 15, 20, 7, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5,
                7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 7, 5, 5, 7, 15, 5, 20,
                5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7,
                15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 12, 21, 20, 36, 7,
                5, 7, 5, 7, 5, 7, 5, 7, 5, 7, 5, 7, 5, 7, 198, 20, 9, 47, 47, 47, 47,
                19, 47, 19, 47, 19, 7, 20, 29, 7, 7, 7, 7, 7, 7, 7, 7, 15, -2, 20, 19, 19, 19
        };

        for (Integer padding : actualPaddings) {
            System.out.print(padding + ", ");
        }

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, catalogueSection.getCATALOGUE_DIV(), imageDiffQA2PL, "testCatalogueSectionPaddingsQA2PL", "testCatalogueSectionPaddingsQA2PL",
                "testCatalogueSectionPaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogueSectionPaddingsQA2PL");
        initializeTestExecutionData("testCatalogueSectionPaddingsQA2PL", startTime, catalogueSection.getCATALOGUE_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));

    }

    @Test
    public void testCatalogueSectionAbsoluteLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        CatalogueSection catalogueSection = new CatalogueSection(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogueSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        catalogueSection.setLocationsList();
        catalogueSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = catalogueSection.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{147, 1170},
                new Integer[]{1409, 1170},
                new Integer[]{1597, 1170},
                new Integer[]{147, 1248},
                new Integer[]{172, 1248},
                new Integer[]{147, 1282},
                new Integer[]{147, 1238},
                new Integer[]{162, 1502},
                new Integer[]{147, 1557},
                new Integer[]{556, 1248},
                new Integer[]{581, 1248},
                new Integer[]{556, 1282},
                new Integer[]{556, 1238},
                new Integer[]{571, 1502},
                new Integer[]{556, 1557},
                new Integer[]{965, 1248},
                new Integer[]{990, 1248},
                new Integer[]{965, 1282},
                new Integer[]{965, 1238},
                new Integer[]{980, 1502},
                new Integer[]{965, 1557},
                new Integer[]{1374, 1248},
                new Integer[]{1399, 1248},
                new Integer[]{1374, 1282},
                new Integer[]{1374, 1238},
                new Integer[]{1389, 1502},
                new Integer[]{1374, 1557},
                new Integer[]{147, 1713},
                new Integer[]{172, 1713},
                new Integer[]{147, 1747},
                new Integer[]{147, 1703},
                new Integer[]{162, 1967},
                new Integer[]{147, 2022},
                new Integer[]{167, 2081},
                new Integer[]{277, 2096},
                new Integer[]{556, 1713},
                new Integer[]{581, 1713},
                new Integer[]{556, 1747},
                new Integer[]{556, 1703},
                new Integer[]{571, 1967},
                new Integer[]{556, 2022},
                new Integer[]{576, 2081},
                new Integer[]{686, 2096},
                new Integer[]{965, 1713},
                new Integer[]{990, 1713},
                new Integer[]{965, 1747},
                new Integer[]{965, 1703},
                new Integer[]{980, 1967},
                new Integer[]{965, 2022},
                new Integer[]{985, 2081},
                new Integer[]{1095, 2096}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, catalogueSection.getCATALOGUE_DIV(), imageDiffQA2PL, "testCatalogueSectionAbsoluteLocationsQA2PL", "testCatalogueSectionAbsoluteLocationsQA2PL",
                "testCatalogueSectionAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogueSectionAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testCatalogueSectionAbsoluteLocationsQA2PL", startTime, catalogueSection.getCATALOGUE_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testCatalogueSectionRelativeLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        CatalogueSection catalogueSection = new CatalogueSection(driver);

        softAssert = new SoftAssert();

        navigateTo(catalogueSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        catalogueSection.setLocationsList();
        catalogueSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = catalogueSection.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{1262, 0},
                new Integer[]{188, 0},
                new Integer[]{-1450, 78},
                new Integer[]{25, 0},
                new Integer[]{-25, 34},
                new Integer[]{0, -44},
                new Integer[]{15, 264},
                new Integer[]{-15, 55},
                new Integer[]{409, -309},
                new Integer[]{25, 0},
                new Integer[]{-25, 34},
                new Integer[]{0, -44},
                new Integer[]{15, 264},
                new Integer[]{-15, 55},
                new Integer[]{409, -309},
                new Integer[]{25, 0},
                new Integer[]{-25, 34},
                new Integer[]{0, -44},
                new Integer[]{15, 264},
                new Integer[]{-15, 55},
                new Integer[]{409, -309},
                new Integer[]{25, 0},
                new Integer[]{-25, 34},
                new Integer[]{0, -44},
                new Integer[]{15, 264},
                new Integer[]{-15, 55},
                new Integer[]{-1227, 156},
                new Integer[]{25, 0},
                new Integer[]{-25, 34},
                new Integer[]{0, -44},
                new Integer[]{15, 264},
                new Integer[]{-15, 55},
                new Integer[]{20, 59},
                new Integer[]{110, 15},
                new Integer[]{279, -383},
                new Integer[]{25, 0},
                new Integer[]{-25, 34},
                new Integer[]{0, -44},
                new Integer[]{15, 264},
                new Integer[]{-15, 55},
                new Integer[]{20, 59},
                new Integer[]{110, 15},
                new Integer[]{279, -383},
                new Integer[]{25, 0},
                new Integer[]{-25, 34},
                new Integer[]{0, -44},
                new Integer[]{15, 264},
                new Integer[]{-15, 55},
                new Integer[]{20, 59},
                new Integer[]{110, 15}

        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, catalogueSection.getCATALOGUE_DIV(), imageDiffQA2PL, "testCatalogueSectionRelativeLocationsQA2PL", "testCatalogueSectionRelativeLocationsQA2PL",
                "testCatalogueSectionRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogueSectionRelativeLocationsQA2PL");
        initializeTestExecutionData("testCatalogueSectionRelativeLocationsQA2PL", startTime, catalogueSection.getCATALOGUE_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testTransactionSectionValuesQA2PL(){
        Instant startTime = Instant.now();

        TransactionSection transactionSection = new TransactionSection(driver);

        navigateTo(transactionSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        softAssert = new SoftAssert();

        List<String[]> actualValues = transactionSection.valuesList();
        String[] transactionValuesUno = {"FuturaStd-Book, Arial, Verdana", "15.9445px", "rgba(64, 64, 64, 1)"};
        String[] transactionValuesDuo = {"FuturaStd-Heavy, Arial, Verdana", "17.9167px", "rgba(64, 64, 64, 1)"};
        String[] transactionValuesTrio = {"FuturaStd-Book, Arial, Verdana", "17.9167px", "rgba(64, 64, 64, 1)"};


        List<String[]> expectedValues = new LinkedList<>();
        IntStream.range(0,2).forEach(i -> expectedValues.add(transactionValuesUno));

        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9167px", "rgba(89, 89, 89, 1)"});
        expectedValues.add(transactionValuesDuo);

        IntStream.range(0,5).forEach(i -> expectedValues.add(transactionValuesTrio));

        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "11.9167px", "rgba(0, 60, 136, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "17.9167px", "rgba(0, 132, 67, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "21.9167px", "rgba(64, 64, 64, 1)"});

        for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }

        for(String[] item: expectedValues){
            System.out.println(Arrays.toString(item));
        }

        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();
        //TODO can't take screenshot cause content dynamically changing
    }

    @Test
    public void testTransactionSectionPaddingsQA2PL(){
        Instant startTime = Instant.now();

        TransactionSection transactionSection = new TransactionSection(driver);

        navigateTo(transactionSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        softAssert = new SoftAssert();

        Integer[] actualPaddings = transactionSection.getPaddings();
        Integer[] expectedPaddings = new Integer[] {
                49, 24, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29,
                29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 19, 15, 15, 15, 15, 15, 15,
                15, 15, 15, 15, 20, 21, 20, -3, 2, 14, -3, 2, -3, 2, -3, 2, -3, 2, 20, 15, 20, -2, 20, 10
        };

        for (Integer padding : actualPaddings) {
            System.out.print(padding + ", ");
        }

        Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
    }

    @Test
    public void testTransactionSectionAbsoluteLocationsQAPL(){

        Instant startTime = Instant.now();
        TransactionSection transactionSection = new TransactionSection(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionSection.setLocationsList();
        transactionSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = transactionSection.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{147, 2284},
                new Integer[]{258, 2454},
                new Integer[]{637, 2454},
                new Integer[]{914, 2454},
                new Integer[]{1110, 2454},
                new Integer[]{274, 2354},
                new Integer[]{656, 2354},
                new Integer[]{927, 2354},
                new Integer[]{1118, 2354},
                new Integer[]{1389, 2354},
                new Integer[]{0, 0},
                new Integer[]{1399, 2489}

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
    public void testTransactionSectionRelativeLocationsQA2PL(){
        Instant startTime = Instant.now();
        TransactionSection transactionSection = new TransactionSection(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionSection.setLocationsList();
        transactionSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = transactionSection.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{111, 170},
                new Integer[]{379, 0},
                new Integer[]{277, 0},
                new Integer[]{196, 0},
                new Integer[]{-836, -100},
                new Integer[]{382, 0},
                new Integer[]{271, 0},
                new Integer[]{191, 0},
                new Integer[]{271, 0},
                new Integer[]{-1389, -2354},
                new Integer[]{1399, 2489}
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
    public void testFooterSectionValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        footerSection.setLocationsList();
        footerSection.createMapFromLocationsList();

        List<String[]> actualValues = footerSection.valuesList();
        String[] footerTitles = {"FuturaStd-Heavy, Arial, Verdana, sans-serif", "19.9445px", "rgba(221, 29, 33, 1)"};
        String[] footerSubTitles = {"FuturaStd-Book, Arial, Verdana", "19.9306px", "rgba(64, 64, 64, 1)"};
        String[] footerSocial = {"FuturaStd-Book, Arial, Verdana", "17.9445px", "rgba(64, 64, 64, 1)"};
        String[] footerCookie = {"FuturaStd-Book, Arial, Verdana", "16px", "rgba(51, 51, 51, 1)"};


        List<String[]> expectedValues = new LinkedList<>();
        IntStream.range(0,4).forEach(i -> expectedValues.add(footerTitles));
        IntStream.range(0,10).forEach(i -> expectedValues.add(footerSubTitles));
        expectedValues.add(footerSocial);
        expectedValues.add(footerCookie);

        /*for(String[] item: actualValues){
            System.out.println(Arrays.toString(item));
        }

        for(String[] item: expectedValues){
            System.out.println(Arrays.toString(item));
        }*/

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, footerSection.getFOOTER_DIV(), imageDiffQA2PL, "testFooterSectionValuesQA2PL", "testFooterSectionValuesQA2PL",
                "testFooterSectionValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFooterSectionValuesQA2PL");
        initializeTestExecutionData("testFooterSectionValuesQA2PL", startTime, footerSection.getFOOTER_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testFooterSectionQA2PL() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        navigateTo(footerSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        softAssert = new SoftAssert();

        Integer[] actualPaddings = footerSection.getPaddings();
        Integer[] expectedPaddings = {49, 16, 49, 76, 29, 10, 15, 15, 15, 15, 10,
            20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 10, 10};

        /*for(Integer actual: actualPaddings){
            System.out.println(actual);
        }*/

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, footerSection.getFOOTER_DIV(), imageDiffQA2PL, "testFooterSectionQA2PL", "testFooterSectionQA2PL",
                "testFooterSectionQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFooterSectionQA2PL");
        initializeTestExecutionData("testFooterSectionQA2PL", startTime, footerSection.getFOOTER_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testFooterSectionAbsoluteLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        footerSection.setLocationsList();
        footerSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> absoluteLocationsMap = footerSection.absoluteLocations();

        for(Map.Entry<Integer, Integer[]> entries: absoluteLocationsMap.entrySet()){
            Integer key = entries.getKey();
            Integer[] value = entries.getValue();
            System.out.println("Key: " + key  + ", Value: " + Arrays.toString(value));
        }

        LinkedHashMap<Integer, Integer[]> expectedAbsoluteLocations = new LinkedHashMap<>();
        List<Integer[]> absoluteLocationsList = Arrays.asList(
                new Integer[]{286, 3118},
                new Integer[]{702, 3118},
                new Integer[]{1166, 3118},
                new Integer[]{1523, 3118},
                new Integer[]{1523, 3167},
                new Integer[]{286, 3157},
                new Integer[]{286, 3200},
                new Integer[]{286, 3244},
                new Integer[]{702, 3157},
                new Integer[]{702, 3200},
                new Integer[]{702, 3244},
                new Integer[]{702, 3288},
                new Integer[]{1166, 3157},
                new Integer[]{1166, 3200},
                new Integer[]{1166, 3244},
                new Integer[]{1523, 3167},
                new Integer[]{0, 3412}
        );
        IntStream.range(0, absoluteLocationsList.size())
                .forEach(i -> expectedAbsoluteLocations.put(i+1, absoluteLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, footerSection.getFOOTER_DIV(), imageDiffQA2PL, "testFooterSectionAbsoluteLocationsQA2PL", "testFooterSectionAbsoluteLocationsQA2PL",
                "testFooterSectionAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFooterSectionAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testFooterSectionAbsoluteLocationsQA2PL", startTime, footerSection.getFOOTER_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testFooterSectionRelativeLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();
        FooterSection footerSection = new FooterSection(driver);

        softAssert = new SoftAssert();

        navigateTo(footerSection.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        footerSection.setLocationsList();
        footerSection.createMapFromLocationsList();

        LinkedHashMap<Integer, Integer[]> relativeLocationsMap = footerSection.relativeLocations();

        for (Map.Entry<Integer, Integer[]> entry : relativeLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + Arrays.toString(value));
        }
        LinkedHashMap<Integer, Integer[]> expectedRelativeLocations = new LinkedHashMap<>();
        List<Integer[]> relativeLocationsList = Arrays.asList(
                new Integer[]{416, 0},
                new Integer[]{464, 0},
                new Integer[]{357, 0},
                new Integer[]{0, 49},
                new Integer[]{-1237, -10},
                new Integer[]{0, 43},
                new Integer[]{0, 44},
                new Integer[]{416, -87},
                new Integer[]{0, 43},
                new Integer[]{0, 44},
                new Integer[]{0, 44},
                new Integer[]{464, -131},
                new Integer[]{0, 43},
                new Integer[]{0, 44},
                new Integer[]{357, -77},
                new Integer[]{-1523, 245}

        );
        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/
        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, footerSection.getFOOTER_DIV(), imageDiffQA2PL, "testFooterSectionRelativeLocationsQA2PL", "testFooterSectionRelativeLocationsQA2PL",
                "testFooterSectionRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFooterSectionRelativeLocationsQA2PL");
        initializeTestExecutionData("testFooterSectionRelativeLocationsQA2PL", startTime, footerSection.getFOOTER_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testLandingHeaderFuncQA2PL(){
        Instant startTime = Instant.now();

        InteractiveLandingElements interactiveElements = new InteractiveLandingElements(driver);

        softAssert = new SoftAssert();

        navigateTo(interactiveElements.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        Queue<String> actualUrls = interactiveElements.clickOnHeaderElements();
        Queue<String> expectedUrls = new LinkedList<>();
        expectedUrls.offer("https://lodqa2.wonderline.eu/pl-pl/station-locator");
        expectedUrls.offer("https://lodqa2.wonderline.eu/pl-pl/how-to-use");
        expectedUrls.offer("https://lodqa2.wonderline.eu/pl-pl/basket");
        expectedUrls.offer("https://lodqa2.wonderline.eu/pl-pl/faq");
        expectedUrls.offer("https://lodqa2.wonderline.eu/pl-pl/basket");
        expectedUrls.offer("https://lodqa2.wonderline.eu/pl-pl/contact-us");

        actualUrls.stream().forEach(System.out::println);

        int i = 0;
        for (String expectedUrl : expectedUrls) {
            String actualUrl = actualUrls.poll();
            softAssert.assertEquals(actualUrl, expectedUrl, "Mismatch at index " + i);
            i++;
        }

        softAssert.assertAll();
    }

    @Test
    public void testLandingPointTrackerPopupQA2PL(){
        Instant startTime = Instant.now();

        InteractiveLandingElements interactiveElements = new InteractiveLandingElements(driver);

        softAssert = new SoftAssert();

        navigateTo(interactiveElements.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        HashMap<List<Serializable>, Integer[]> actualValuePaddingMap = interactiveElements.pointTrackerPopup();
        for (Map.Entry<List<Serializable>, Integer[]> entry : actualValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] value = entry.getValue();

            System.out.println("ActualKey: " + key + ", ActualValue: " + Arrays.toString(value));
        }

        HashMap<List<Serializable>, Integer[]> expectedValuePaddingMap = new HashMap<>();

        List<Serializable> keyExpected = new ArrayList<>();
        keyExpected.add("rgba(64, 64, 64, 1), 21.9167px, FuturaStd-Book, Arial, Verdana, rgba(64, 64, 64, 1), 23.9167px, FuturaStd-Bold, Arial, Verdana");
        //keyExpected.add("rgba(64, 64, 64, 1), 19.523px, FuturaStd-Book, Arial, Verdana, rgba(64, 64, 64, 1), 21.523px, FuturaStd-Bold, Arial, Verdana");


        Integer[] value = {20, 20, 20, 20, 29, 20, 20, 20, 20};
        expectedValuePaddingMap.put(keyExpected, value);

        for (Map.Entry<List<Serializable>, Integer[]> entry : expectedValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] expectedValue = entry.getValue();

            System.out.println("ExpectedKey: " + key + ", ExpectedValue: " + Arrays.toString(expectedValue));
        }

        String expectedKeyString = keyExpected.toString();
        String actualKeyString = actualValuePaddingMap.keySet().iterator().next().toString();

        softAssert.assertTrue(expectedKeyString.equals(actualKeyString), "Key assertion failed");
        if (!expectedKeyString.equals(actualKeyString)) {
            System.out.println("expectedkeystring does not equal with actualkeystring");
            for (int i = 0; i < Math.min(expectedKeyString.length(), actualKeyString.length()); i++) {
                if (expectedKeyString.charAt(i) != actualKeyString.charAt(i)) {
                    System.out.println("Difference at index " + i + ":");
                    System.out.println("Expected char: " + expectedKeyString.charAt(i));
                    System.out.println("Actual char: " + actualKeyString.charAt(i));
                }
            }
        }

        softAssert.assertEquals(actualValuePaddingMap.get(actualValuePaddingMap.keySet().iterator().next()), value);

        softAssert.assertAll();
    }


    @Test
    public void testLandingOfferPopupQA2PL(){
        Instant startTime = Instant.now();

        InteractiveLandingElements interactiveElements = new InteractiveLandingElements(driver);

        softAssert = new SoftAssert();

        navigateTo(interactiveElements.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        HashMap<List<Serializable>, Integer[]> actualValuePaddingMap = interactiveElements.offerPopup();
        for (Map.Entry<List<Serializable>, Integer[]> entry : actualValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] value = entry.getValue();

            System.out.println("ActualKey: " + key + ", ActualValue: " + Arrays.toString(value));
        }

        HashMap<List<Serializable>, Integer[]> expectedValuePaddingMap = new HashMap<>();

        List<Serializable> keyExpected = new ArrayList<>();
        keyExpected.add("rgba(64, 64, 64, 1), 15.9445px, FuturaStd-Book, Arial, Verdana, rgba(64, 64, 64, 1), 15.9445px, " +
                "FuturaStd-Book, Arial, Verdana, rgba(64, 64, 64, 1), 15.9445px, FuturaStd-Bold, Arial, Verdana");

        Integer[] value = {19, 20, 20, 20, 20, 15, 15, 22, 15};
        expectedValuePaddingMap.put(keyExpected, value);

        for (Map.Entry<List<Serializable>, Integer[]> entry : expectedValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] expectedValue = entry.getValue();

            System.out.println("ExpectedKey: " + key + ", ExpectedValue: " + Arrays.toString(expectedValue));
        }

        String expectedKeyString = keyExpected.toString();
        String actualKeyString = actualValuePaddingMap.keySet().iterator().next().toString();

        softAssert.assertTrue(expectedKeyString.equals(actualKeyString), "Key assertion failed");
        if (!expectedKeyString.equals(actualKeyString)) {
            System.out.println("expectedkeystring does not equal with actualkeystring");
            for (int i = 0; i < Math.min(expectedKeyString.length(), actualKeyString.length()); i++) {
                if (expectedKeyString.charAt(i) != actualKeyString.charAt(i)) {
                    System.out.println("Difference at index " + i + ":");
                    System.out.println("Expected char: " + expectedKeyString.charAt(i));
                    System.out.println("Actual char: " + actualKeyString.charAt(i));
                }
            }
        }

        softAssert.assertEquals(actualValuePaddingMap.get(actualValuePaddingMap.keySet().iterator().next()), value);

        softAssert.assertAll();
    }

    @Test
    public void testLandingCataloguePopupQA2PL(){
        Instant startTime = Instant.now();

        InteractiveLandingElements interactiveElements = new InteractiveLandingElements(driver);

        softAssert = new SoftAssert();

        navigateTo(interactiveElements.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        HashMap<List<Serializable>, Integer[]> actualValuePaddingMap = interactiveElements.cataloguePopup();
        for (Map.Entry<List<Serializable>, Integer[]> entry : actualValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] value = entry.getValue();

            System.out.print("ActualKey: ");
            for (Serializable item : key) {
                if (item instanceof String[]) {
                    System.out.print(Arrays.toString((String[]) item) + ", ");
                } else {
                    // Handle other Serializable types
                    System.out.print(item + ", ");
                }
            }

            System.out.println();
            System.out.println("ActualValue: " + Arrays.toString(value));
        }


        HashMap<List<Serializable>, Integer[]> expectedValuePaddingMap = new HashMap<>();

        List<Serializable> keyExpected = new ArrayList<>();

        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Bold, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Heavy, Arial, Verdana, 17.9584px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Heavy, Arial, Verdana, 15.9445px, rgba(235, 135, 5, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 13.9445px, rgba(64, 64, 64, 1)"});


        Integer[] value = {3, 15, 3, 29, 5, 5, 29, 6, 15, 6, 5, 5, 3, 15, 3, 5, 5, 6, 15, 6, 5, 5, -15, 31};

        List<Integer> extendedValues = Stream.generate(() -> Arrays.stream(value))
                .limit(7)
                .flatMap(stream -> stream)
                .collect(Collectors.toList());

        Integer[] extendedValuesArray = extendedValues.toArray(new Integer[0]);
        System.out.println(Arrays.toString(extendedValuesArray));

        expectedValuePaddingMap.put(keyExpected, extendedValuesArray);

        for (Map.Entry<List<Serializable>, Integer[]> entry : expectedValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] expectedValue = entry.getValue();

            for (Serializable item : key) {
                if (item instanceof String[]) {
                    System.out.print(Arrays.toString((String[]) item) + ", ");
                }
            }
            System.out.println();
            System.out.println("ActualValue: " + Arrays.toString(expectedValue));
        }

        List<String> extractedStringsExpected = new ArrayList<>();
        for (Serializable item : keyExpected) {
            if (item instanceof String[]) {
                extractedStringsExpected.addAll(Arrays.asList((String[]) item));
            }
        }

        String[] expectedArrayResult = extractedStringsExpected.toArray(new String[0]);
        System.out.println("The size of the expectedArray is: " + expectedArrayResult.length);

        List<String> extractedStringsActual = new ArrayList<>();

        for (Serializable item : keyExpected) {
            if (item instanceof String[]) {
                String[] stringArray = (String[]) item;
                for (String str : stringArray) {
                    extractedStringsActual.add(str);
                }
            }
        }

        String[] actualArrayResult = extractedStringsActual.toArray(new String[0]);

        System.out.println("The size of the actualArray is: " + actualArrayResult.length);

        IntStream.range(0, actualArrayResult.length).forEach(index -> {
            softAssert.assertEquals(actualArrayResult[index], expectedArrayResult[index],
                    "Mismatch at index " + index + ": expected " + expectedArrayResult[index] + " but got " + actualArrayResult[index]);
        });

        softAssert.assertAll();
    }

    @Test
    public void testLandingTransactionPopupQA2PL(){
        Instant startTime = Instant.now();

        InteractiveLandingElements interactiveElements = new InteractiveLandingElements(driver);

        softAssert = new SoftAssert();

        navigateTo(interactiveElements.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        HashMap<List<Serializable>, Integer[]> actualValuePaddingMap = interactiveElements.interactTransactions();
        for (Map.Entry<List<Serializable>, Integer[]> entry : actualValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] value = entry.getValue();

            System.out.print("ActualKey: ");
            for (Serializable item : key) {
                if (item instanceof String[]) {
                    System.out.print(Arrays.toString((String[]) item) + ", ");
                } else {
                    // Handle other Serializable types
                    System.out.print(item + ", ");
                }
            }

            System.out.println();
            System.out.println("ActualValue: " + Arrays.toString(value));
        }

        HashMap<List<Serializable>, Integer[]> expectedValuePaddingMap = new HashMap<>();

        List<Serializable> keyExpected = new ArrayList<>();

        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 18px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 18px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 18px, rgba(64, 64, 64, 1)"});

        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});

        keyExpected.add(new String[]{"FuturaStd-Heavy, Arial, Verdana, 17.9167px, rgba(64, 64, 64, 1)"});

        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});

        keyExpected.add(new String[]{"FuturaStd-Heavy, Arial, Verdana, 17.9167px, rgba(64, 64, 64, 1)"});

        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});

        keyExpected.add(new String[]{"FuturaStd-Heavy, Arial, Verdana, 17.9167px, rgba(64, 64, 64, 1)"});

        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});
        keyExpected.add(new String[]{"FuturaStd-Book, Arial, Verdana, 15.9445px, rgba(64, 64, 64, 1)"});


        Integer[] value = {18, 18, 18, 18, 18, 18, 29, 29, 29, 29, 29};

        expectedValuePaddingMap.put(keyExpected, value);

        for (Map.Entry<List<Serializable>, Integer[]> entry : expectedValuePaddingMap.entrySet()) {
            List<Serializable> key = entry.getKey();
            Integer[] expectedValue = entry.getValue();

            for (Serializable item : key) {
                if (item instanceof String[]) {
                    System.out.print(Arrays.toString((String[]) item) + ", ");
                }
            }
            System.out.println();
            System.out.println("ActualValue: " + Arrays.toString(expectedValue));
        }

        List<String> extractedStringsExpected = new ArrayList<>();
        for (Serializable item : keyExpected) {
            if (item instanceof String[]) {
                extractedStringsExpected.addAll(Arrays.asList((String[]) item));
            }
        }

        String[] expectedArrayResult = extractedStringsExpected.toArray(new String[0]);
        System.out.println("The size of the expectedArray is: " + expectedArrayResult.length);

        List<String> extractedStringsActual = new ArrayList<>();

        for (Serializable item : keyExpected) {
            if (item instanceof String[]) {
                String[] stringArray = (String[]) item;
                for (String str : stringArray) {
                    extractedStringsActual.add(str);
                }
            }
        }

        String[] actualArrayResult = extractedStringsActual.toArray(new String[0]);

        System.out.println("The size of the actualArray is: " + actualArrayResult.length);

        IntStream.range(0, actualArrayResult.length).forEach(index -> {
            softAssert.assertEquals(actualArrayResult[index], expectedArrayResult[index],
                    "Mismatch at index " + index + ": expected " + expectedArrayResult[index] + " but got " + actualArrayResult[index]);
        });



        String transactionPageActualUrl = interactiveElements.gotToTransactionPage();
        String transactionPageExpectedUrl = "https://lodqa2.wonderline.eu/pl-pl/account/transactions";

        softAssert.assertEquals(transactionPageActualUrl, transactionPageExpectedUrl);


        softAssert.assertAll();
    }


    //STATION LOCATOR ------------>>>
    //STATION LOCATOR ------------>>>
    //STATION LOCATOR ------------>>>
    //STATION LOCATOR ------------>>>
    //STATION LOCATOR ------------>>>
    //STATION LOCATOR ------------>>>


    /*private StationLocatorPage stationLocatorPage;
    private void initializeStationLocatorSite(){

        stationLocatorPage = new StationLocatorPage(driver);

        navigateTo(stationLocatorPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        stationLocatorPage.goToStationLocatorPage();
    }*/

    @Test
    public void testStationLocatorPageURLQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();

        StationLocatorPage stationLocatorPage = new StationLocatorPage(driver);
        navigateTo(stationLocatorPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String actualUrl = stationLocatorPage.goToStationLocatorPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/station-locator";
        Thread.sleep(4000);

        /*softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, stationLocatorPage.getSTATION_LOCATOR_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testStationLocatorPageURLQA2PL", "testStationLocatorPageURLQA2PL",
                "testStationLocatorPageURLQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testStationLocatorPageURLQA2PL");
        initializeTestExecutionData("testStationLocatorPageURLQA2PL", startTime, stationLocatorPage.getSTATION_LOCATOR_DIV(), assertMethod(softAssert, actualUrl, expectedUrl, isImagesSame));
    }


    @Test
    public void testStationValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeStationLocatorSite();
        StationLocatorPage stationLocatorPage = new StationLocatorPage(driver);
        softAssert = new SoftAssert();

        navigateTo(stationLocatorPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        stationLocatorPage.goToStationLocatorPage();

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

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/
        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, stationLocatorPage.getSTATION_LOCATOR_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testStationValuesQA2PL", "testStationValuesQA2PL",
                "testStationValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testStationValuesQA2PL");
        initializeTestExecutionData("testStationValuesQA2PL", startTime, stationLocatorPage.getSTATION_LOCATOR_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testStationPaddingsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeStationLocatorSite();
        StationLocatorPage stationLocatorPage = new StationLocatorPage(driver);
        softAssert = new SoftAssert();

        navigateTo(stationLocatorPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        stationLocatorPage.goToStationLocatorPage();

        Integer[] actualPaddings = stationLocatorPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(System.out::println);

        Integer[] expectedPaddings = {
                10, 20, 24, 20, 10, 15, 15, 15, 10, 20, 24, 20, 20, 141,
                71, 15, 15, 15, 145, 145, 147, 145, 145, 147
        };

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, stationLocatorPage.getSTATION_LOCATOR_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testStationPaddingsQA2PL", "testStationPaddingsQA2PL",
                "testStationPaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testStationPaddingsQA2PL");
        initializeTestExecutionData("testStationPaddingsQA2PL", startTime, stationLocatorPage.getSTATION_LOCATOR_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testStationAbsoluteLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeStationLocatorSite();
        StationLocatorPage stationLocatorPage = new StationLocatorPage(driver);
        softAssert = new SoftAssert();

        navigateTo(stationLocatorPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        stationLocatorPage.goToStationLocatorPage();

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

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, stationLocatorPage.getSTATION_LOCATOR_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testStationAbsoluteLocationsQA2PL", "testStationAbsoluteLocationsQA2PL",
                "testStationAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testStationAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testStationAbsoluteLocationsQA2PL", startTime, stationLocatorPage.getSTATION_LOCATOR_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testStationRelativeLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeStationLocatorSite();
        StationLocatorPage stationLocatorPage = new StationLocatorPage(driver);
        softAssert = new SoftAssert();

        navigateTo(stationLocatorPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        stationLocatorPage.goToStationLocatorPage();

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

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, stationLocatorPage.getSTATION_LOCATOR_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testStationRelativeLocationsQA2PL", "testStationRelativeLocationsQA2PL",
                "testStationRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testStationRelativeLocationsQA2PL");
        initializeTestExecutionData("testStationRelativeLocationsQA2PL", startTime, stationLocatorPage.getSTATION_LOCATOR_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testStationAppPopupQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        //initializeStationLocatorSite();
        StationLocatorPage stationLocatorPage = new StationLocatorPage(driver);
        softAssert = new SoftAssert();

        navigateTo(stationLocatorPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        stationLocatorPage.goToStationLocatorPage();

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
        /*softAssert.assertTrue(isInteract);
        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, stationLocatorPage.getDOWNLOAD_APP_POPUP(), imageDiffQA2PL, "testStationAppPopupQA2PL", "testStationAppPopupQA2PL",
                "testStationAppPopupQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testStationAppPopupQA2PL");
        initializeTestExecutionData("testStationAppPopupQA2PL", startTime, stationLocatorPage.getDOWNLOAD_APP_POPUP(), assertMethod(softAssert, isInteract, isImagesSame));

    }


    //TRANSACTION ---------->>>
    //TRANSACTION ---------->>>
    //TRANSACTION ---------->>>
    //TRANSACTION ---------->>>
    //TRANSACTION ---------->>>
    //TRANSACTION ---------->>>
    //TRANSACTION ---------->>>

    //private TransactionPage transactionPage;

    @Test
    public void testTransactionPageURLQA2PL() throws IOException {
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);
        navigateTo(transactionPage.getUrl());

        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String actualUrl = transactionPage.goToTransactionPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/account/transactions";

        /*softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();*/

        softAssert = takeConcatenatedScrollShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getConcatenatedWebElements(), imageDiffQA2PL, "testTransactionPageURLQA2PL", "testTransactionPageURLQA2PL",
                "testTransactionPageURLQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionPageURLQA2PL");
        initializeTestExecutionData("testTransactionPageURLQA2PL", startTime, transactionPage.getCONCATENATED_ELEMENTS(), assertMethod(softAssert, actualUrl, expectedUrl, isImagesSame));
    }

    @Test
    public void testTransactionFilterQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        boolean isPassed = transactionPage.transactionFilter();
        //softAssert.assertAll();
        softAssert = takeConcatenatedScrollShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getConcatenatedWebElements(), imageDiffQA2PL, "testTransactionFilterQA2PL", "testTransactionFilterQA2PL",
                "testTransactionFilterQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionFilterQA2PL");
        initializeTestExecutionData("testTransactionFilterQA2PL", startTime, transactionPage.getCONCATENATED_ELEMENTS(), assertMethod(softAssert, isPassed, isImagesSame));

    }

    @Test
    public void testTransactionFilterVisualisation() throws IOException, InterruptedException {
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        boolean isPassed = transactionPage.transactionFilterVisualisation();

        /*softAssert = takeScrollCroppedShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getTRANSACTION_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testTransactionFilterVisualisation", "testTransactionFilterVisualisation",
                "testTransactionFilterVisualisation", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionFilterVisualisation");
        initializeTestExecutionData("testTransactionFilterVisualisation", startTime, transactionPage.getTRANSACTION_DIV(), assertMethod(softAssert, isPassed, isImagesSame));*/

        softAssert = takeConcatenatedScrollShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getConcatenatedWebElements(), imageDiffQA2PL, "testTransactionFilterVisualisation", "testTransactionFilterVisualisation",
                "testTransactionFilterVisualisation", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionFilterVisualisation");
        initializeTestExecutionData("testTransactionFilterVisualisation", startTime, transactionPage.getCONCATENATED_ELEMENTS(), assertMethod(softAssert, isPassed, isImagesSame));

    }

    @Test
    public void testDownloadPdfQA2PL() throws IOException {
        Instant startTime = Instant.now();

        TransactionPage transactionPage = new TransactionPage(driver);
        navigateTo(transactionPage.getUrl());

        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        transactionPage.goToTransactionPage();
        /*softAssert.assertEquals(transactionPage.clickDownloadPdf(), "https://lodqa2.wonderline.eu/pl-pl/account/transactions/pdf");
        softAssert.assertAll();*/

        boolean isPassed = transactionPage.clickDownloadPdf();

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getDOWNLOAD_PDF_BUTTON(), imageDiffQA2PL, "testDownloadPdfQA2PL", "testDownloadPdfQA2PL",
                "testDownloadPdfQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testDownloadPdfQA2PL");
        initializeTestExecutionData("testDownloadPdfQA2PL", startTime, transactionPage.getDOWNLOAD_PDF_BUTTON(), assertMethod(softAssert, isPassed, isImagesSame));

    }

    @Test
    public void testTransactionPageValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();
        TransactionPage transactionPage = new TransactionPage(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionPage.getUrl());
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
        IntStream.range(0,4).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "19.9306px", "rgba(64, 64, 64, 1)"}));
        expectedValues.add(new String[]{"FuturaStd-Bold, Arial, Verdana", "23.889px", "rgba(51, 51, 51, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "20px", "rgba(89, 89, 89, 1)"});
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
        IntStream.range(0,5).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "17.9167px", "rgba(64, 64, 64, 1)"}));

        //expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "23.9167px", "rgba(64, 64, 64, 1)"});
        //IntStream.range(0,9).forEach(i -> expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"}));

        /*expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});
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
        expectedValues.add(new String[]{"FuturaStd-Book, Arial, Verdana", "15px", "rgba(64, 64, 64, 1)"});*/

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getTRANSACTION_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL,"testTransactionPageValuesQA2PL", "testTransactionPageValuesQA2PL",
                "testTransactionPageValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionPageValuesQA2PL");
        initializeTestExecutionData("testTransactionPageValuesQA2PL", startTime, transactionPage.getTRANSACTION_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testTransactionPagePaddings() throws IOException {
        Instant startTime = Instant.now();
        TransactionPage transactionPage = new TransactionPage(driver);

        softAssert = new SoftAssert();

        navigateTo(transactionPage.getUrl());
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
            System.out.print(padding + ", ");
        }

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getTRANSACTION_DIV(), imageDiffQA2PL,"testTransactionPagePaddings", "testTransactionPagePaddings",
                "testTransactionPagePaddings", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionPagePaddings");
        initializeTestExecutionData("testTransactionPagePaddings", startTime, transactionPage.getTRANSACTION_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testTransactionPageAbsoluteLocationsQA2PL() throws IOException {
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

        //updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getTRANSACTION_DIV(), imageDiffQA2PL,"testTransactionPageAbsoluteLocationsQA2PL", "testTransactionPageAbsoluteLocationsQA2PL",
                "testTransactionPageAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionPageAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testTransactionPageAbsoluteLocationsQA2PL", startTime, transactionPage.getTRANSACTION_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testTransactionPageRelativeLocationsQA2PL() throws IOException {
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

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, transactionPage.getTRANSACTION_DIV(), imageDiffQA2PL,"testTransactionPageRelativeLocationsQA2PL", "testTransactionPageRelativeLocationsQA2PL",
                "testTransactionPageRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testTransactionPageRelativeLocationsQA2PL");
        initializeTestExecutionData("testTransactionPageRelativeLocationsQA2PL", startTime, transactionPage.getTRANSACTION_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    //FAQS ------------->
    //FAQS ------------->
    //FAQS ------------->
    //FAQS ------------->
    //FAQS ------------->
    //FAQS ------------->
    //FAQS ------------->

    /*private FAQsPage faQsPage;
    private void initializeFAQsSite(){

        faQsPage = new FAQsPage(driver);

        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        faQsPage.goToFAQsPage();
    }*/

    @Test
    public void testFAQsPageURLQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();

        FAQsPage faQsPage = new FAQsPage(driver);
        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String actualUrl = faQsPage.goToFAQsPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/faq";
        Thread.sleep(3000);

        /*softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, faQsPage.getWHOLE_FAQS_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFAQsPageURLQA2PL", "testFAQsPageURLQA2PL",
                "testFAQsPageURLQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFAQsPageURLQA2PL");
        initializeTestExecutionData("testFAQsPageURLQA2PL", startTime, faQsPage.getWHOLE_FAQS_DIV(), assertMethod(softAssert, actualUrl, expectedUrl, isImagesSame));
    }


    @Test
    public void testFAQsValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeFAQsSite();
        FAQsPage faQsPage = new FAQsPage(driver);
        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        faQsPage.goToFAQsPage();

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

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/
        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, faQsPage.getWHOLE_FAQS_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFAQsValuesQA2PL", "testFAQsValuesQA2PL",
                "testFAQsValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFAQsValuesQA2PL");
        initializeTestExecutionData("testFAQsValuesQA2PL", startTime, faQsPage.getWHOLE_FAQS_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testFAQsPaddingsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeFAQsSite();
        FAQsPage faQsPage = new FAQsPage(driver);
        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        faQsPage.goToFAQsPage();

        Integer[] actualPaddings = faQsPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                /*10, 1, 30, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 10, 2, 45, 45, 45, 45, 45, 45,
                45, 45, 45, 45, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
                5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 40, 40, 40, 40,
                40, 40, 40, 40, 40, 40, 10, 10, 1, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
                30, 54, 30, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 12, 12, 12, 12,
                12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 40,
                40, 40, 40, 40, 40, 40, 40, 40, 40, 70, 70, 70, 70, 70, 10, 60, 57, 60, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, 60*/
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

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, faQsPage.getWHOLE_FAQS_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFAQsPaddingsQA2PL", "testFAQsPaddingsQA2PL",
                "testFAQsPaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFAQsPaddingsQA2PL");
        initializeTestExecutionData("testFAQsPaddingsQA2PL", startTime, faQsPage.getWHOLE_FAQS_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testFAQsAbsoluteLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeFAQsSite();
        FAQsPage faQsPage = new FAQsPage(driver);
        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        faQsPage.goToFAQsPage();

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

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, faQsPage.getWHOLE_FAQS_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFAQsAbsoluteLocationsQA2PL", "testFAQsAbsoluteLocationsQA2PL",
                "testFAQsAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFAQsAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testFAQsAbsoluteLocationsQA2PL", startTime, faQsPage.getWHOLE_FAQS_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testFAQsRelativeLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeFAQsSite();
        FAQsPage faQsPage = new FAQsPage(driver);
        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        faQsPage.goToFAQsPage();

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
                new Integer[]{0, 89},
                new Integer[]{0, 89}
        );

        IntStream.range(0, relativeLocationsList.size())
                .forEach(i -> expectedRelativeLocations.put(i+1, relativeLocationsList.get(i)));

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, faQsPage.getWHOLE_FAQS_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFAQsRelativeLocationsQA2PL", "testFAQsRelativeLocationsQA2PL",
                "testFAQsRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFAQsRelativeLocationsQA2PL");
        initializeTestExecutionData("testFAQsRelativeLocationsQA2PL", startTime, faQsPage.getWHOLE_FAQS_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }


    @Test
    public void testFAQsInteractiveElements() throws IOException, InterruptedException {
        Instant startTime = Instant.now();

        //initializeFAQsSite();
        FAQsPage faQsPage = new FAQsPage(driver);
        navigateTo(faQsPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        faQsPage.goToFAQsPage();

        boolean isPassed = faQsPage.interactiveFAQsElements();

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, faQsPage.getWHOLE_FAQS_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFAQsInteractiveElements", "testFAQsInteractiveElements",
                "testFAQsInteractiveElements", "C:\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFAQsInteractiveElements");
        initializeTestExecutionData("testFAQsInteractiveElements", startTime, faQsPage.getWHOLE_FAQS_DIV(), assertMethod(softAssert, isPassed, isImagesSame));
    }

    // -----> BASKET PAGE
    // -----> BASKET PAGE
    // -----> BASKET PAGE
    // -----> BASKET PAGE
    // -----> BASKET PAGE
    // -----> BASKET PAGE
    // -----> BASKET PAGE

    private BasketPage basketPage;
    /*private void initializeBasketSite(){

        basketPage = new BasketPage(driver);

        navigateTo(basketPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        basketPage.goToBasketPage();
    }*/

    @Test
    public void testBasketPageURLQA2PL() throws /*IOException, InterruptedException*/Exception {
        Instant startTime = Instant.now();

        /*basketPage = new BasketPage(driver);
        navigateTo(basketPage.getUrl());
        softAssert = new SoftAssert();*/

        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }

        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String actualUrl = basketPage.goToBasketPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/basket";
        Thread.sleep(3000);

        /*softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testBasketPageURLQA2PL", "testBasketPageURLQA2PL",
                "testBasketPageURLQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPageURLQA2PL");
        initializeTestExecutionData("testBasketPageURLQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, actualUrl, expectedUrl, isImagesSame));
    }


    @Test
    public void testBasketValuesQA2PL() throws /*IOException*/Exception {
        Instant startTime = Instant.now();

        //initializeBasketSite();

        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }
        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        basketPage.goToBasketPage();

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

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/


        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testBasketValuesQA2PL", "testBasketValuesQA2PL",
                "testBasketValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketValuesQA2PL");
        initializeTestExecutionData("testBasketValuesQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testBasketPagePaddingsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeBasketSite();

        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }
        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        basketPage.goToBasketPage();

        Integer[] actualPaddings = basketPage.getPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                14, 14, 14, 29, 14, 29, 18, 7, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 5, 5, 7, 10, 15,
                7, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20, 5, 5, 7, 15, 20
                , 21, 7, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 5, 5, 7, 33, 15, 7, 5, 5, 7, 15, 5, 20
                , 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5, 20, 5, 5, 7, 15, 5,
                20, 20, 21, 20, 36, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 9, 5, 20, 130,
                24, 9, 9, 9, 9, 20, 9, 20, 9, 20, 20, 14, 20, 29, 9, 9, 9, 9, 9, 9, 9, 9, 91, -2, 20, -24, 9, 9, 9, 9, 9, 20, 9, 20, 9, 20, 9, 20
        };

        System.out.println(expectedPaddings.length);


        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testBasketPagePaddingsQA2PL", "testBasketPagePaddingsQA2PL",
                "testBasketPagePaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPagePaddingsQA2PL");
        initializeTestExecutionData("testBasketPagePaddingsQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testBasketPageAbsoluteLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeBasketSite();
        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }
        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        basketPage.goToBasketPage();

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

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testBasketPageAbsoluteLocationsQA2PL", "testBasketPageAbsoluteLocationsQA2PL",
                "testBasketPageAbsoluteLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPageAbsoluteLocationsQA2PL");
        initializeTestExecutionData("testBasketPageAbsoluteLocationsQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testBasketPageRelativeLocationsQA2PL() throws IOException {

        Instant startTime = Instant.now();

        //initializeBasketSite();
        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }
        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        basketPage.goToBasketPage();

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

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testBasketPageRelativeLocationsQA2PL", "testBasketPageRelativeLocationsQA2PL",
                "testBasketPageRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPageRelativeLocationsQA2PL");
        initializeTestExecutionData("testBasketPageRelativeLocationsQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));


        /*softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, "testBasketPageRelativeLocationsQA2PL", "testBasketPageRelativeLocationsQA2PL",
                "testBasketPageRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPageRelativeLocationsQA2PL");
        initializeTestExecutionData("testBasketPageRelativeLocationsQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));*/
    }

    @Test
    public void testInteractiveBasketPageElementsQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        //initializeBasketSite();

        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }
        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        basketPage.goToBasketPage();

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

        /*softAssert.assertTrue(assertionBoolean);
        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testInteractiveBasketPageElementsQA2PL", "testInteractiveBasketPageElementsQA2PL",
                "testInteractiveBasketPageElementsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testInteractiveBasketPageElementsQA2PL");
        initializeTestExecutionData("testInteractiveBasketPageElementsQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, assertionBoolean, isImagesSame));
    }

    @Test
    public void testBasketPagePopUpValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeBasketSite();
        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }
        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        basketPage.goToBasketPage();

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

        /*System.out.println("The size of the expected values is: " + expectedValues.size());
        IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testBasketPagePopUpValuesQA2PL", "testBasketPagePopUpValuesQA2PL",
                "testBasketPagePopUpValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPagePopUpValuesQA2PL");
        initializeTestExecutionData("testBasketPagePopUpValuesQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));

        /*softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testBasketPagePopUpValuesQA2PL", "testBasketPagePopUpValuesQA2PL",
                "testBasketPagePopUpValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPagePopUpValuesQA2PL");
        initializeTestExecutionData("testBasketPagePopUpValuesQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));*/
    }

    @Test
    public void testBasketPagePopUpPaddingsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeBasketSite();
        if (basketPage == null) {
            basketPage = new BasketPage(driver);
        }
        softAssert = new SoftAssert();
        navigateTo(basketPage.getUrl());
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        basketPage.goToBasketPage();

        Integer[] actualPaddings = basketPage.getPopUpPaddings();
        Arrays.stream(actualPaddings).forEach(p -> System.out.print(p + ", "));

        System.out.println(actualPaddings.length);

        Integer[] expectedPaddings = {
                15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5,
                5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15,
                5, 5, 15, 5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15,
                5, 5, 15, 5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15,
                5, 5, -15, 31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15,
                31, 15, 29, 5, 5, 29, 15, 5, 5, 15, 5, 5, 15, 5, 5, -15, 31
        };

        System.out.println(expectedPaddings.length);

        softAssert = takeShutterBugScreenshotOfDivsQA2PL(softAssert, basketPage.getWHOLE_BASKET_DIV(), imageDiffQA2PL, "testBasketPagePopUpPaddingsQA2PL", "testBasketPagePopUpPaddingsQA2PL",
                "testBasketPagePopUpPaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testBasketPagePopUpPaddingsQA2PL");
        initializeTestExecutionData("testBasketPagePopUpPaddingsQA2PL", startTime, basketPage.getWHOLE_BASKET_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }


    //   ------------->  CATALOG PAGE
    //   ------------->  CATALOG PAGE
    //   ------------->  CATALOG PAGE
    //   ------------->  CATALOG PAGE
    //   ------------->  CATALOG PAGE
    //   ------------->  CATALOG PAGE
    //   ------------->  CATALOG PAGE


    private CatalogPage catalogPage;
    private void initializeCatalogSite(){

        catalogPage = new CatalogPage(driver);

        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        catalogPage.goToCatalogPage();
    }

    @Test
    public void testCatalogPageURLQA2PL() throws IOException, InterruptedException {
        Instant startTime = Instant.now();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();

        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }

        String actualUrl = catalogPage.goToCatalogPage();
        String expectedUrl = "https://lodqa2.wonderline.eu/pl-pl/catalog";
        Thread.sleep(3000);

        /*softAssert.assertEquals(actualUrl, expectedUrl);
        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getWHOLE_CATALOG_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testCatalogPageURLQA2PL", "testCatalogPageURLQA2PL",
                "testCatalogPageURLQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogPageURLQA2PL");
        initializeTestExecutionData("testCatalogPageURLQA2PL", startTime, catalogPage.getWHOLE_CATALOG_DIV(), assertMethod(softAssert, actualUrl, expectedUrl, isImagesSame));
    }

    @Test
    public void testSaveImageCatalogPageQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

        String[] expectedImageUrl = {
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
                "background-image: url(\"https://lodqa2.wonderline.eu/cdn/b/deriv/33/45ae050da94dffc7536126b052e8c3/378x350.jpg?spider.reward.default.product.image.png.jpg\");",
        };

        String[] actualImageUrl = catalogPage.imageUrl().split(",");

        System.out.println("The size of the actualImageUrl is: "  +  actualImageUrl.length);
        for(String imageUrl: actualImageUrl){
            System.out.println(imageUrl);
        }

        IntStream.range(0, expectedImageUrl.length).forEach(i ->
                softAssert.assertEquals(actualImageUrl[i], expectedImageUrl[i])
        );

        //softAssert.assertTrue(catalogPage.saveImageToFile());

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getWHOLE_CATALOG_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testSaveImageCatalogPageQA2PL", "testSaveImageCatalogPageQA2PL",
                "testSaveImageCatalogPageQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testSaveImageCatalogPageQA2PL");
        initializeTestExecutionData("testSaveImageCatalogPageQA2PL", startTime, catalogPage.getWHOLE_CATALOG_DIV(), assertMethod(softAssert, catalogPage.saveImageToFile(), isImagesSame));
    }

    @Test
    public void testCatalogPageValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

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

        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getWHOLE_CATALOG_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testCatalogPageValuesQA2PL", "testCatalogPageValuesQA2PL",
                "testCatalogPageValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogPageValuesQA2PL");
        initializeTestExecutionData("testCatalogPageValuesQA2PL", startTime, catalogPage.getWHOLE_CATALOG_DIV(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }

    @Test
    public void testCatalogPagePaddingsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

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

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getWHOLE_CATALOG_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testCatalogPagePaddingsQA2PL", "testCatalogPagePaddingsQA2PL",
                "testCatalogPagePaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogPagePaddingsQA2PL");
        initializeTestExecutionData("testCatalogPagePaddingsQA2PL", startTime, catalogPage.getWHOLE_CATALOG_DIV(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testCatalogPageAbsoluteLocationsQA2Pl() throws IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

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

        updateLocationsIfDiffIsOnlyOne(absoluteLocationsMap, expectedAbsoluteLocations);

        /*IntStream.range(1, expectedAbsoluteLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = absoluteLocationsMap.get(i);
                    Integer[] expected = expectedAbsoluteLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getWHOLE_CATALOG_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testCatalogPageAbsoluteLocationsQA2Pl", "testCatalogPageAbsoluteLocationsQA2Pl",
                "testCatalogPageAbsoluteLocationsQA2Pl", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogPageAbsoluteLocationsQA2Pl");
        initializeTestExecutionData("testCatalogPageAbsoluteLocationsQA2Pl", startTime, catalogPage.getWHOLE_CATALOG_DIV(), assertMethod(softAssert, absoluteLocationsMap, expectedAbsoluteLocations, isImagesSame));
    }

    @Test
    public void testCatalogPageRelativeLocationsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

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

        updateLocationsIfDiffIsOnlyOne(relativeLocationsMap, expectedRelativeLocations);

        /*IntStream.range(1, expectedRelativeLocations.size() + 1)
                .forEach(i -> {
                    Integer[] actual = relativeLocationsMap.get(i);
                    Integer[] expected = expectedRelativeLocations.get(i);

                    softAssert.assertNotNull(actual, "Actual value at index " + i + " is null.");
                    softAssert.assertNotNull(expected, "Expected value at index " + i + " is null.");

                    if (actual != null && expected != null) {
                        softAssert.assertEquals(actual, expected, "Values at index " + i + " do not match.");
                    }
                });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getWHOLE_CATALOG_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testCatalogPageRelativeLocationsQA2PL", "testCatalogPageRelativeLocationsQA2PL",
                "testCatalogPageRelativeLocationsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogPageRelativeLocationsQA2PL");
        initializeTestExecutionData("testCatalogPageRelativeLocationsQA2PL", startTime, catalogPage.getWHOLE_CATALOG_DIV(), assertMethod(softAssert, relativeLocationsMap, expectedRelativeLocations, isImagesSame));
    }

    @Test
    public void testInteractiveCatalogPageElementsQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

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

        /*softAssert.assertTrue(assertionBoolean);
        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getWHOLE_CATALOG_DIV(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testInteractiveCatalogPageElementsQA2PL", "testInteractiveCatalogPageElementsQA2PL",
                "testInteractiveCatalogPageElementsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testInteractiveCatalogPageElementsQA2PL");
        initializeTestExecutionData("testInteractiveCatalogPageElementsQA2PL", startTime, catalogPage.getWHOLE_CATALOG_DIV(), assertMethod(softAssert, assertionBoolean, isImagesSame));
    }

    @Test
    public void testCatalogPagePopupValuesQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

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
        /*IntStream.range(0, actualValues.size()).forEach(i -> {
            softAssert.assertEquals(actualValues.get(i), expectedValues.get(i));
        });

        softAssert.assertAll();*/

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getCATALOG_REWARDS_ITEMS(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testCatalogPagePopupValuesQA2PL", "testCatalogPagePopupValuesQA2PL",
                "testCatalogPagePopupValuesQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogPagePopupValuesQA2PL");
        initializeTestExecutionData("testCatalogPagePopupValuesQA2PL", startTime, catalogPage.getCATALOG_REWARDS_ITEMS(), assertMethod(softAssert, actualValues, expectedValues, isImagesSame));
    }


    @Test
    public void testCatalogPagePopUpPaddingsQA2PL() throws IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

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

        //Assertions.assertArrayEquals(expectedPaddings, actualPaddings);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getCATALOG_REWARDS_ITEMS(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testCatalogPagePopUpPaddingsQA2PL", "testCatalogPagePopUpPaddingsQA2PL",
                "testCatalogPagePopUpPaddingsQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testCatalogPagePopUpPaddingsQA2PL");
        initializeTestExecutionData("testCatalogPagePopUpPaddingsQA2PL", startTime, catalogPage.getCATALOG_REWARDS_ITEMS(), assertMethod(softAssert, actualPaddings, expectedPaddings, isImagesSame));
    }

    @Test
    public void testFilteredCategorySizeQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

        Integer actualNumberOfFilteredCategory = catalogPage.categoryFilterOption();
        Integer expectedNumberOfFilteredCategory = 3;

        //Assertions.assertEquals(expectedNumberOfFilteredCategory, actualNumberOfFilteredCategory);


        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getCATALOG_REWARDS_ITEMS(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFilteredCategorySizeQA2PL", "testFilteredCategorySizeQA2PL",
                "testFilteredCategorySizeQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFilteredCategorySizeQA2PL");
        initializeTestExecutionData("testFilteredCategorySizeQA2PL", startTime, catalogPage.getCATALOG_REWARDS_ITEMS(), assertMethod(softAssert, actualNumberOfFilteredCategory, expectedNumberOfFilteredCategory, isImagesSame));
    }

    @Test
    public void testSearchedCategorySizeQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

        Integer actualNumberOfSearchedCategory = catalogPage.searchFilterOption();
        Integer expectedNumberOfSearchedCategory = 1;

        //Assertions.assertEquals(expectedNumberOfSearchedCategory, actualNumberOfSearchedCategory);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getCATALOG_REWARDS_ITEMS(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testSearchedCategorySizeQA2PL", "testSearchedCategorySizeQA2PL",
                "testSearchedCategorySizeQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testSearchedCategorySizeQA2PL");
        initializeTestExecutionData("testSearchedCategorySizeQA2PL", startTime, catalogPage.getCATALOG_REWARDS_ITEMS(), assertMethod(softAssert, actualNumberOfSearchedCategory, expectedNumberOfSearchedCategory, isImagesSame));
    }

    @Test
    public void testFilterPointsValueQA2PL() throws InterruptedException, IOException {
        Instant startTime = Instant.now();

        //initializeCatalogSite();

        CatalogPage catalogPage = new CatalogPage(driver);
        navigateTo(catalogPage.getUrl());
        softAssert = new SoftAssert();
        if(!isTestSuiteRun){
            uniqueLoginQA2PLIN();
        }
        catalogPage.goToCatalogPage();

        Integer actualNumberOfFilteredCategory = catalogPage.filterPointsValue();
        Integer expectedNumberOfFilteredCategory = 2;

        //Assertions.assertEquals(expectedNumberOfFilteredCategory, actualNumberOfFilteredCategory);

        softAssert = takeScrollShutterBugScreenshotOfDivsQA2PL(softAssert, catalogPage.getCATALOG_REWARDS_ITEMS(), imageDiffQA2PL, CaptureElement.FULL_SCROLL, "testFilterPointsValueQA2PL", "testFilterPointsValueQA2PL",
                "testFilterPointsValueQA2PL", "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_image_differences\\qa2_pl\\testFilterPointsValueQA2PL");
        initializeTestExecutionData("testFilterPointsValueQA2PL", startTime, catalogPage.getCATALOG_REWARDS_ITEMS(), assertMethod(softAssert, actualNumberOfFilteredCategory, expectedNumberOfFilteredCategory, isImagesSame));
    }

}
