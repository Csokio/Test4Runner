package tests;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.CaptureElement;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.github.dockerjava.transport.DockerHttpClient;
import com.google.common.net.MediaType;
import credentials.ApiClient;
import credentials.OAuthClient;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import okhttp3.OkHttpClient;
//import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.io.FileUtils;

import org.example.endpoints.StampCounter;
import org.example.endpoints.qa2pl.ConfigLoader;
import org.example.endpoints.qa2pl.PostGetFinalizeRewardsQA2PL2;
import org.example.endpoints.qa2pl.RewardEngineTester;
import org.example.utils.ImageCropper;
import org.junit.*;
import org.junit.Test;
import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.Request;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import org.example.ImageDiff;
//import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterGroups;
import org.testng.asserts.SoftAssert;
import utils.ResponseToMap;
import utils.ResponseToString;
import utils.Utils;
import org.json.JSONObject;

import utils.executiondata.AbstractAssertion;

import javax.imageio.ImageIO;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;


//TODO Parameterize tests for covering different scenarios for the same steps
//TODO L8 LOGIN before test cases
public class WebTestTH extends AbstractAssertion {

    public static WebDriver driver;
    protected static Integer counter;

    public SoftAssert softAssert = new SoftAssert();
    protected boolean isTestSuiteRun = false;


    private final ImageDiff imageDiff = new ImageDiff();
    //private final Threshold threshold = new Threshold();
    private final By DISPLAY_COOKIES = By.xpath("//div[@id=\"_evidon_banner\"]");
    private final By BUTTON_DECLINE = By.xpath("//button[@id=\"_evidon-decline-button\"]");
    protected boolean isImagesSame = false;

    public static int getCounter(){
        return counter;
    }

    @Before
    public void setup()
    {
        if(driver == null){
            //WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            //WebDriverManager.chromedriver().driverVersion("124.0.6367.157").setup();


            ChromeOptions options = new ChromeOptions();
            options.setAcceptInsecureCerts(true);
            options.addArguments("ignore-certificate-errors");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-extensions");
            //
            //options.addArguments("--headless");
            options.addArguments("--incognito");
            //options.addArguments("--window-size=360,640");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("start-maximized");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--allow-insecure-localhost");
            options.addArguments("--disable-search-engine-choice-screen");


            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

    }


    /*@BeforeClass
    public static void setupChromeDriver(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    }

    @Before
    public void initializeChromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        //options.addArguments("--headless");
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--allow-insecure-localhost");
        options.addArguments("--disable-search-engine-choice-screen");


        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }*/

    /*@After
    public void closeDriver(){
        //driver.quit();
        driver.close();
    }*/

    /*@AfterClass
    public static void quitDriver(){
        if(driver != null){
            driver.close();
        }
    }*/



    @AfterEach
    public void tearDown(){
        driver.close();
    }

    @AfterAll
    public static void shutDown(){
        driver.quit();
    }

    protected void navigateTo(String url){
        System.out.println("navigate TO Method is invoked! The requested url is: " + url);
        driver.navigate().to(url);
        //THIS CANCELKUKI JUST NEEDS WHEN WE SPEC UATGB
        cancelCookies();
    }


    private void cancelCookies(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try{
            /*if(driver.findElement(DISPLAY_COOKIES).isDisplayed()){
                driver.findElement(BUTTON_DECLINE).click();
            }*/
            wait.until(ExpectedConditions.visibilityOfElementLocated(DISPLAY_COOKIES));
            driver.findElement(BUTTON_DECLINE).click();

        }   catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void windowScrollTo(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void waitForWebElement(By xpath, Integer duration){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
    }


    //TODO Could implemented into all test cases which one implement saveimage's getLocation() method. - DONE in AbstractLocationManager Class
    protected LinkedHashMap<Integer, Integer[]> calculateDifference(LinkedHashMap<Integer, Integer[]> mapOfPoints){

        LinkedHashMap<Integer, Integer[]> pointDiffXY = new LinkedHashMap<>();
        IntStream.range(0, mapOfPoints.size()-1).forEach(i ->
                pointDiffXY.put(i+1, new Integer[]{mapOfPoints.get(i+2)[0] - mapOfPoints.get(i+1)[0],
                        mapOfPoints.get(i+2)[1] - mapOfPoints.get(i+1)[1]})
        );

        System.out.println("The size of pointDiff Map in calculateDiff method is: " + pointDiffXY.size());
        return pointDiffXY;
    }

/*    @Test
    public void testWholeScreen() throws Exception{
        driver.get("https://demo.seleniumeasy.com/");
        try {
            Thread.sleep(4000);
            captureWholeScreen("selenium_home_page.png");
        } catch (Exception e)   {
            e.printStackTrace();
        }

    }

    @Test
    public void testHeadlessScreenshot() throws NoSuchElementException{

        driver.get("https://demo.seleniumeasy.com/");
        final By BUTTON_InPUT_FORMS = By.linkText("Input Forms");
            //driver.findElement(BUTTON_InPUT_FORMS).click();
        for (int i = 0; i < 3; i++) {
            counter = Utils.readFile("counter.txt");
            File file1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(file1, new File(String.format("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\headless_screenshot_diff%d.png", counter)));

            } catch (IOException e) {
                e.printStackTrace();
            }

            driver.findElement(BUTTON_InPUT_FORMS).click();
            File file2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(file2, new File(String.format("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\headless_screenshot_diff%d.png", counter)));

            } catch (IOException e) {
                e.printStackTrace();
            }
            driver.get("https://demo.seleniumeasy.com/");

            counter = counter + 1;
            Utils.writeFile("counter.txt");
            Utils.writeFile("src/test/resources/screenshots.properties");
            System.out.println(counter);
        }
    }

    @Test
    public void testSimpleScreenshot() throws NoSuchElementException, InterruptedException {
        counter = Utils.readFile("counter.txt");

        driver.get("https://lodqa-rm.wonderline.eu/en-th");
        Thread.sleep(10000);


        File file1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file1, new File(String.format("C:\\Users\\SőregiKrisztián\\Pictures\\angular_logout_original\\headless_screenshot_angular_logout%d.png", counter)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        counter = counter + 1;
        Utils.writeFile("counter.txt");
        Utils.writeFile("src/test/resources/screenshots.properties");
        System.out.println(counter);

    }

    @Test
    public void testFullPageScreenshot() throws Exception {
        counter = Utils.readFile("counter.txt");

        driver.get("https://lodqa.wonderline.eu/nl-nl/");
        Thread.sleep(10000);

        // Get the total height of the webpage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long pageHeight = (Long) js.executeScript("return Math.max(document.body.scrollHeight, document.body.offsetHeight, " +
                "document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");

        // Set the window size to match the height of the entire webpage
        driver.manage().window().setSize(new Dimension(1366, pageHeight.intValue()));

        // Calculate the viewport height
        Long viewportHeight = (Long) js.executeScript("return window.innerHeight");

        // Calculate the number of screenshots needed
        int numberOfScreenshots = (int) Math.ceil((double) pageHeight / viewportHeight);

        // Create a full page screenshot
        BufferedImage fullPageImage = new BufferedImage(1366, pageHeight.intValue(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = fullPageImage.createGraphics();

        // Take multiple screenshots and stitch them together
        for (int i = 0; i < numberOfScreenshots; i++) {
            // Scroll to the current position
            js.executeScript("window.scrollTo(0, " + (viewportHeight * i) + ");");

            // Take a screenshot of the current viewport
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage screenshotImage = ImageIO.read(screenshot);

            // Draw the current screenshot onto the full page image
            graphics.drawImage(screenshotImage, 0, (int) (viewportHeight * i), null);
        }

        // Save the full page screenshot to a file
        ImageIO.write(fullPageImage, "png", new File(String.format
                ("C:\\Users\\SőregiKrisztián\\Pictures\\angular_logout_original\\headless_screenshot_angular_logout%d.png", counter)));


        counter = counter + 1;
        Utils.writeFile("counter.txt");
        Utils.writeFile("src/test/resources/screenshots.properties");
        System.out.println(counter);

    }*/

    //@Test
    public void testScrollIntoView() throws Exception {
        driver.get("https://lodqa-rm.wonderline.eu/en-th");
        //driver.get("https://demo.seleniumeasy.com/");

        final By FOOTER = By.xpath("//footer");
        final By CAMPAIGN = By.xpath("(//div)[89]");

        Thread.sleep(6000);
        //zoomOut(driver);
        Thread.sleep(3000);

        counter = Utils.readFile("counter.txt");

        File file1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file1, new File(String.format("C:\\Users\\SőregiKrisztián\\Pictures\\angular_logout_original\\headless_screenshot_angular_logout%d.png", counter)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(CAMPAIGN));
        Thread.sleep(6000);

        File file2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file2, new File(String.format("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\headless_screenshot_diff%d.2.png", counter)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread.sleep(6000);

        counter = counter + 1;
        Utils.writeFile("counter.txt");
        Utils.writeFile("src/test/resources/screenshots.properties");
        System.out.println(counter);

    }

    //@Test
    public void testShutterBug() throws InterruptedException, IOException {
        driver.get("https://lodqa.wonderline.eu/en-gb/");
        Thread.sleep(10000);
        shootPage();
        Assertions.assertTrue(true);
    }


    /*private void captureWholeScreen(String fileName) throws Exception {
        // Create a Robot object to capture the screen
        Robot robot = new Robot();

        // Create a Rectangle object to define the area of the screen to capture
        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

        // Use the robot.createScreenCapture() method to capture the screen as a BufferedImage object
        BufferedImage image = robot.createScreenCapture(rectangle);

        // Save the BufferedImage object to a file using the ImageIO.write() method
        File file = new File(fileName);
        ImageIO.write(image, "png", file);

        // Print a message indicating that the screenshot has been saved
        System.out.println("Screenshot saved to " + fileName);
    }*/

    private void zoomOut(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='40%'"); // You can adjust the zoom level here
    }

    public void shootPage() throws IOException {

        Shutterbug.shootPage(driver, Capture.FULL_SCROLL ,500,true).withName("shutterbug_fullscreen7")
                .save("C:\\Users\\SőregiKrisztián\\Pictures");
        //Shutterbug.shootPage(driver, Capture.FULL_SCROLL).save();

    }

    @Test
    public void testTwoPagesWithShutterbug() throws InterruptedException, IOException {
        driver.get("https://shelluatpoints.loyaltyondemand.club/en-gb/");
        final By BUTTON_DECLINE = By.xpath("//button[@id=\"_evidon-decline-button\"]");
        final By DISPLAY_COOKIES = By.xpath("//div[@id=\"_evidon_banner\"]");
        final By TITLE_HOME = By.xpath("//div[@id=\"fpHomeFaqHeadTitle\"]");
        final By BUTTON_MORE = By.xpath("(//div[@class=\"menuButtonTable\"])[3]");
        final By BUTTON_CAMPAIGN = By.id("campaignCardContainer_u67");
        final By BUTTON_CLOSE_CAMPAIGN = By.id("campaign_modal_close_u75");

        Thread.sleep(4000);

        final Actions action = new Actions(driver);

        int iterationCounter = 0;
        for (int i = 0; i < 3; i++) {

            try{
                if(driver.findElement(DISPLAY_COOKIES).isDisplayed()){
                    driver.findElement(BUTTON_DECLINE).click();
                }
            }   catch (Exception e){
                System.out.println(e.getMessage());
            }

            Thread.sleep(4000);

            counter = Utils.readFile("counter.txt");
            try {
                Shutterbug.shootPage(driver, Capture.FULL_SCROLL, 500, true)
                        .withName(String.format("shutterbug_headless_screenshot%d.png", counter))
                        .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures\\");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(TITLE_HOME));

            if(iterationCounter == 0){
                action.doubleClick(driver.findElement(BUTTON_MORE)).perform();
            }else{
                driver.findElement(BUTTON_MORE).click();
            }

            try {
                Shutterbug.shootPage(driver, Capture.FULL_SCROLL, 500, true)
                        .withName(String.format("shutterbug_headless_screenshot_overtime%d.png", counter))
                        .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            Thread.sleep(2000);
            driver.findElement(BUTTON_MORE).click();
            //driver.get("https://lodqa.wonderline.eu/en-gb/");

            counter = counter + 1;
            iterationCounter++;

            Utils.writeFile("counter.txt");
            Utils.writeFile("src/test/resources/screenshots.properties");
            System.out.println(counter);
        }

        //TODO THRESHOLD
        //Threshold.main();
        Assertions.assertTrue(imageDiff.compareImagePixelDifference("selenium_test_pictures\\", "selenium_test_pictures_overtime\\",
                "selenium_image_differences\\output_diff%d.png"));
    }

    @Test
    public void testSiteResponse(){

        Response response = RestAssured.get("https://shelluatpoints.loyaltyondemand.club/en-gb/");

        int statusCode = response.getStatusCode();
        Assertions.assertEquals(200, statusCode);

    }

    @Test
    public void testSingleTranslation(){

        RequestSpecification requestSpecification = given().baseUri("https://lodqa-rm.wonderline.eu/lod-web-service/v1/en-th").contentType(ContentType.JSON)
                .config(RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().setParam("http.connection.timeout", 10000)
                .setParam("http.socket.timeout", 10000)));;
        Response response2 =requestSpecification.when().get("/translation");


        String[] responseLines = response2.getBody().asString().split(",");
        for(String line: responseLines){
            System.out.println(line);
        }

        ResponseToMap.CustomMapResponse customResponse = new ResponseToMap.CustomMapResponse(responseLines);
        Map<String, String> responseMap = customResponse.toMap();

        String mapValue = responseMap.get("\"spider_basket_delivery_fp_delivery_addres_not_found_title\"");

        //System.out.println(responseMap.get("\"spider_basket_delivery_fp_delivery_addres_not_found_title\""));
        System.out.println("-----------------------------------------------------------------------");

        Assertions.assertFalse(mapValue.isEmpty());

    }

    @Test
    public void testAllTranslation(){
        RequestSpecification requestSpecification = given().baseUri("https://lodqa-rm.wonderline.eu/lod-web-service/v1/en-th").contentType(ContentType.JSON);
        Response response2 =requestSpecification.when().get("/translation");
        
        ResponseToString.CustomStringResponse customResponse = new ResponseToString.CustomStringResponse(response2.getBody().asString());
        System.out.println(customResponse);

        Assertions.assertFalse(customResponse.isResponseBodyEmpty());

    }

    @Test
    public void testBalanceInResponse(){
        RequestSpecification requestSpecification = given().baseUri("https://lodqa-rm.wonderline.eu").contentType(ContentType.JSON);
        Response response = requestSpecification.when().get("/ngsw.json?ngsw-cache-bust=0.15304998043058915");

        ResponseToString.CustomStringResponse customResponse = new ResponseToString.CustomStringResponse(response.getBody().asString());
        System.out.println(customResponse);

    }

    @Test
    public void testConsumerTransactions(){

        /*String tokenUrl = "https://lodqa.wonderline.eu/loyalty-ws/v1/api/oauth2/token";
        String clientId = "wl_oa";
        String clientSecret = "a";

        RequestSpecification requestSpecification = given()
                .baseUri("https://lodqa.wonderline.eu/loyalty-ws")
                .auth().oauth2(getOAuthToken())
                //.auth().oauth2(getOAuth2Token2(tokenUrl, clientId, clientSecret))
                .auth().basic("wl","a")
                .header("x-lod-country-code", "GB")
                .header("x-lod-market", "en-GB")
                .header("x-lod-client-id", "Mozilla/5.0 (Linux; Android 8.0.0; SM-G960F Build/R16NW) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/62.0.3202.84 Mobile Safari/537.36")
                .header("x-lod-device-id", "1111")
                .header("x-lod-source-application", "PARTNER")
                .header("consumerUUID", "d7cae42d-7cc2-407b-8fac-a0587c81a9b6")
                .contentType(ContentType.JSON);
        Response response = requestSpecification.when().get("/v1/consumers/transactions");
        ResponseToString.CustomStringResponse customResponse = new ResponseToString.CustomStringResponse(response.getBody().asString());
        System.out.println(customResponse);*/

        int testInt = 300 - 150;

        Assertions.assertEquals(200, /*response.getStatusCode()*/testInt);


    }

    //TODO OAuth authorization

    @Test
    public void testApiClient(){

        try {
            String accessToken = OAuthClient.getAccessToken();
            ApiClient.callApi(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBalanceByUUID(){
        RequestSpecification requestSpecification = given().baseUri("https://lodqa-rm.wonderline.eu/contactUs").contentType(ContentType.JSON);
        Response response2 =requestSpecification.when().get("/data");

        String[] responseLines = response2.getBody().asString().split(",");

        ResponseToMap.CustomMapResponse mapResponse = new ResponseToMap.CustomMapResponse(responseLines);
        //System.out.println(mapResponse.toMap().get());

        System.out.println(response2.getBody().asString());
        ResponseToString.CustomStringResponse stringResponse = new ResponseToString.CustomStringResponse(response2.getBody().asString());
        System.out.println(stringResponse);
    }

    @Test
    public void testBalanceUUID2(){
        RestAssured.baseURI = "https://lodqa-rm.wonderline.eu/lod-web-service/v1/account";

        // Create the request specification
        RequestSpecification requestSpecification = RestAssured.given()
                .contentType(ContentType.JSON)  // Content type of the request
                .accept(ContentType.JSON)       // Expected response type
                .header("Authorization", "Bearer eyJhbGciOiJSUzI1NiJ9.eyJleHBpcmVzSW4iOjM2MDAuMCwibWFya2V0IjoiZW4tVEgiLCJjdXN0b21lcklkIjo1OTkwLCJjb25zZW50cyI6IntcIm5vd" +
                        "GlmaWNhdGlvbnNcIjpmYWxzZSxcImxveWFsdHlPcHRJblwiOmZhbHNlLFwibmV3c2xldHRlcnNcIjp0cnVlLFwiZ29QbHVzTWFya2V0aW5nT3B0SW5cIjpmYWxzZSxcIm1vYmlsZUdvUGx1c01hcmtl" +
                        "dGluZ09wdEluXCI6dHJ1ZSxcIm1vYmlsZU5ld3NsZXR0ZXJzXCI6dHJ1ZSxcInRvc1wiOnRydWUsXCJuYnNPcHRJblwiOnRydWV9IiwiYWNjZXNzVG9rZW4iOiJjbHljcGR1eHcwMnVjMjduejJtZHp" +
                        "3cnd3IiwidXVpZCI6ImI3MWEyNGI2LTA3MjUtNDZhNi1hYTllLTBlNDljZGY4ODU0NyIsInJlZnJlc2hUb2tlbiI6ImV5SmhiR2NpT2lKSVV6STFOaUlzSW5SNWNDSTZJa3BYVkNKOS5leUpsYm1OeW" +
                        "VYQjBaV1JVYjJ0bGJpSTZJbUU1WWpVMlkyWmxORFkwTVdJMk5EVTRNRFJoTm1FM1pqYzVPVFF6WmpVeU9qVXpOVFptWTJGa09XTTVabUV6TkRkbE1UWmxZakZtTVdNM05tSTRZbU5qTkRRNVlqVTRNV" +
                        "FZpWW1NMU9HSXpNREEyTlRjeVpHWXlNak01TVRRd1lUY2lMQ0pwWVhRaU9qRTNNakEwTWpZeU56WjkuX2RncEtLM0UyM2lpTVY5YWxfWnUtUy1MbzNpMVp1bmVhaGJWYUZFWExWMCIsInN1YiI6IntcI" +
                        "m5vdGlmaWNhdGlvbnNcIjpmYWxzZSxcImxveWFsdHlPcHRJblwiOmZhbHNlLFwibmV3c2xldHRlcnNcIjp0cnVlLFwiZ29QbHVzTWFya2V0aW5nT3B0SW5cIjpmYWxzZSxcIm1vYmlsZUdvUGx1c01hc" +
                        "mtldGluZ09wdEluXCI6dHJ1ZSxcIm1vYmlsZU5ld3NsZXR0ZXJzXCI6dHJ1ZSxcInRvc1wiOnRydWUsXCJuYnNPcHRJblwiOnRydWV9IiwiZXhwIjoxNzIwNDI5ODc3fQ.uKOxJpwrWhsSkkyoA8lD5cl" +
                        "Xl7VfeCIR65R92hbz6xOp2SsZMf9rkHK42lAiJ9yAv_oVB5EBD7u0Bfx4tCrVK2gIqgbn6HNro6zWidZGvhlKjQMCEajc1ojn_nKb0GRcXkA6v0tQBHxNJONDLtM7kHL3Uj21ZTlaZKjS1gHU4dSPn8tU3" +
                        "ZyJTK1ZLbbKLWJpdHK74PriCTlo_VDFFrTUuqlBhdOwJnACye_Pbm_aEQbom3ZCYZc6kSuDuNmTg43Xvf6jhMwLhx22fgb0U2D5WO5uDk4fvgk1uw5oWd45lffYGf52tuG9clObH24P3j7qoGnyrAXIx5xyt_hUUOQorQ")
                .header("User-Agent:", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Mobile Safari/537.36")
                .header("X-Lod-Correlation-Id:", "RM:ukw9v")
                .header("X-Lod-Market:", "en-th");

        // Send the GET request
        Response response2 = requestSpecification.get("/trackerBalance");
        System.out.println(response2.getBody().asString());
    }

    private String getOAuthToken(){
        Response response = RestAssured
                .given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "wl_oa")
                .formParam("client_secret", "oa")
                .post("https://lodqa.wonderline.eu/loyalty-ws/v1/api/oauth2/token");

        if (response.getStatusCode() == 200) {
            System.out.println("The aaccesstoken is: " + response.jsonPath().getString("access_token"));
            return response.jsonPath().getString("access_token");
        } else {
            throw new RuntimeException("Failed to get access token, HTTP status code: " + response.getStatusCode());
        }
    }

    private String getOAuth2Token2(String tokenUrl, String clientId, String clientSecret) {

         Response response = given()
                               .param("client_id", clientId)
                               .param("client_secret", clientSecret)
                               .post(tokenUrl);
        String token = response.jsonPath().getString("access_token");
        System.out.println("This is the response toke:   ---->   "  + token);
        return token;
    }

    private String getOAuth2Token3(String tokenUrl, String clientId, String clientSecret) {
        Map<String, String> formData = new HashMap<>();
        formData.put("client_id", clientId);
        formData.put("client_secret", clientSecret);
        formData.put("grant_type", "client_credentials");

        Response response = given()
                .contentType(ContentType.URLENC)
                .formParams(formData)
                .post(tokenUrl);

        if (response.getStatusCode() == 200) {
            String accessToken = response.jsonPath().getString("access_token");
            System.out.println("This is the response token: " + accessToken);
            return accessToken;
        } else {
            System.out.println("Error: Unable to retrieve access token. Status code: " + response.getStatusCode());
            return null;
        }
    }

    @Test
    public void testGetRewardEndPointRest(){
        RestAssured.baseURI = "https://lodqa.wonderline.eu";
        RestAssured.useRelaxedHTTPSValidation();  // SSL hitelesítés relaxálása, ha szükséges

        String requestBody = "{\n" +
                "  \"customerData\": [\n" +
                "    {\n" +
                "      \"customerDataType\": \"LoyaltyCard\",\n" +
                "      \"customerDataValue\": \"70048855626897229\",\n" +
                "      \"loyaltyType\": \"Shell\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"posData\": {\n" +
                "    \"posTimeStamp\": \"2024-09-06T09:45:01+00:00\",\n" +
                "    \"transactionNumber\": \"THSO_000023\"\n" +
                "  },\n" +
                "  \"requestData\": {\n" +
                "    \"requestID\": \"THSO_000023\",\n" +
                "    \"requestType\": \"OfferQuery\",\n" +
                "    \"workstationID\": \"1\"\n" +
                "  },\n" +
                "  \"tenders\": [\n" +
                "    {\n" +
                "      \"tenderID\": 1,\n" +
                "      \"methodOfPaymentID\": \"1\",\n" +
                "      \"methodOfPayment\": \"Cash\",\n" +
                "      \"totalAmount\": \"1200\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"saleItems\": [\n" +
                "    {\n" +
                "      \"additionalProductCode\": \"5016878010435\",\n" +
                "      \"additionalProductInfo\": \"\",\n" +
                "      \"amount\": 1200,\n" +
                "      \"categoryCode\": \"\",\n" +
                "      \"itemID\": 1,\n" +
                "      \"originalAmount\": 1200,\n" +
                "      \"vatRate\": 20,\n" +
                "      \"quantity\": 6,\n" +
                "      \"saleItemType\": \"Sale\",\n" +
                "      \"unitMeasure\": \"EA\",\n" +
                "      \"unitPrice\": 200,\n" +
                "      \"productCode\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"siteData\": {\n" +
                "    \"countryCode\": \"TH\",\n" +
                "    \"siteID\": \"55501\"\n" +
                "  },\n" +
                "  \"totalAmount\": \"1200\"\n" +
                "}";

        // Kérés küldése
        Response response = given()
                .auth().basic("wl", "a")  // Ellenőrizd a felhasználónevet és jelszót
                .header("Content-Type", "application/json")  // Header ellenőrzése
                .body(requestBody)  // JSON kérés törzse
                .when()
                .post("/backend-ws/future/v1/getRewards");

        // Válasz kiírása
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());


    }

    @Test
    public void testGetRewardEndPointJava(){

        String url = "https://lodqa.wonderline.eu/backend-ws/future/v1/getRewards";


        String username = "wl";
        String password = "a";
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        // Kérés testje
        String requestBody = "{\n" +
                "  \"customerData\": [\n" +
                "    {\n" +
                "      \"customerDataType\": \"LoyaltyCard\",\n" +
                "      \"customerDataValue\": \"70048855626897229\",\n" +
                "      \"loyaltyType\": \"Shell\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"posData\": {\n" +
                "    \"posTimeStamp\": \"2024-09-06T09:45:01+00:00\",\n" +
                "    \"transactionNumber\": \"THSO_000023\"\n" +
                "  },\n" +
                "  \"requestData\": {\n" +
                "    \"requestID\": \"THSO_000023\",\n" +
                "    \"requestType\": \"OfferQuery\",\n" +
                "    \"workstationID\": \"1\"\n" +
                "  },\n" +
                "  \"tenders\": [\n" +
                "    {\n" +
                "      \"tenderID\": 1,\n" +
                "      \"methodOfPaymentID\": \"1\",\n" +
                "      \"methodOfPayment\": \"Cash\",\n" +
                "      \"totalAmount\": \"1200\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"saleItems\": [\n" +
                "    {\n" +
                "      \"additionalProductCode\": \"5016878010435\",\n" +
                "      \"additionalProductInfo\": \"\",\n" +
                "      \"amount\": 1200,\n" +
                "      \"categoryCode\": \"\",\n" +
                "      \"itemID\": 1,\n" +
                "      \"originalAmount\": 1200,\n" +
                "      \"vatRate\": 20,\n" +
                "      \"quantity\": 6,\n" +
                "      \"saleItemType\": \"Sale\",\n" +
                "      \"unitMeasure\": \"EA\",\n" +
                "      \"unitPrice\": 200,\n" +
                "      \"productCode\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"siteData\": {\n" +
                "    \"countryCode\": \"TH\",\n" +
                "    \"siteID\": \"55501\"\n" +
                "  },\n" +
                "  \"totalAmount\": \"1200\"\n" +
                "}";

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testIncreaseStampCounter(){

        String getRewardsUrl = "https://lodqa.wonderline.eu/backend-ws/future/v1/getRewards";
        String finalizeRewardsUrl = "https://lodqa.wonderline.eu/backend-ws/future/v1/finalizeRewards";

        String username = "wl";
        String password = "a";
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        String requestBody = "{\n" +
                "  \"customerData\": [\n" +
                "    {\n" +
                "      \"customerDataType\": \"LoyaltyCard\",\n" +
                "      \"customerDataValue\": \"70048855626897229\",\n" +
                "      \"loyaltyType\": \"Shell\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"posData\": {\n" +
                "    \"posTimeStamp\": \"2024-09-06T10:07:01+00:00\",\n" +
                "    \"transactionNumber\": \"THSO_000027\"\n" +
                "  },\n" +
                "  \"requestData\": {\n" +
                "    \"requestID\": \"THSO_000027\",\n" +
                "    \"requestType\": \"OfferQuery\",\n" +
                "    \"workstationID\": \"1\"\n" +
                "  },\n" +
                "  \"tenders\": [\n" +
                "    {\n" +
                "      \"tenderID\": 1,\n" +
                "      \"methodOfPaymentID\": \"1\",\n" +
                "      \"methodOfPayment\": \"Cash\",\n" +
                "      \"totalAmount\": \"1200\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"saleItems\": [\n" +
                "    {\n" +
                "      \"additionalProductCode\": \"5016878010435\",\n" +
                "      \"additionalProductInfo\": \"\",\n" +
                "      \"amount\": 1200,\n" +
                "      \"categoryCode\": \"\",\n" +
                "      \"itemID\": 1,\n" +
                "      \"originalAmount\": 1200,\n" +
                "      \"vatRate\": 20,\n" +
                "      \"quantity\": 6,\n" +
                "      \"saleItemType\": \"Sale\",\n" +
                "      \"unitMeasure\": \"EA\",\n" +
                "      \"unitPrice\": 200,\n" +
                "      \"productCode\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"siteData\": {\n" +
                "    \"countryCode\": \"TH\",\n" +
                "    \"siteID\": \"55501\"\n" +
                "  },\n" +
                "  \"totalAmount\": \"1200\"\n" +
                "}";

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(getRewardsUrl))
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (getResponse.statusCode() == 200) {
                System.out.println("GetRewards Response: " + getResponse.body());

                JSONObject responseObject = new JSONObject(getResponse.body());

                String totalAmount = responseObject.getString("totalAmount");
                String bonusPoints = responseObject
                        .getJSONArray("customerData")
                        .getJSONObject(0)
                        .getString("bonusPoints");

                String transactionNumber = "THSO_000030";

                String finalizeRewardsBody = "{\n" +
                        "    \"metaData\": {\n" +
                        "        \"totalCount\": 0\n" +
                        "    },\n" +
                        "    \"objects\": [\n" +
                        "        {\n" +
                        "            \"requestData\": {\n" +
                        "                \"requestType\": \"RetailTransaction\",\n" +
                        "                \"deliveryMode\": \"SiteDelivery\",\n" +
                        "                \"workstationID\": \"1\",\n" +
                        "                \"requestID\": \"" + transactionNumber + "\",\n" +
                        "                \"batchID\": 0,\n" +
                        "                \"currencyCode\": \"TH\"\n" +
                        "            },\n" +
                        "            \"tenders\": [\n" +
                        "                {\n" +
                        "                    \"methodOfPaymentID\": \"1\",\n" +
                        "                    \"methodOfPayment\": \"Cash\",\n" +
                        "                    \"tenderID\": 1,\n" +
                        "                    \"totalAmount\": \"" + totalAmount + "\"\n" +
                        "                }\n" +
                        "            ],\n" +
                        "            \"siteData\": {\n" +
                        "                \"countryCode\": \"TH\",\n" +
                        "                \"siteID\": \"55501\"\n" +
                        "            },\n" +
                        "            \"posData\": {\n" +
                        "                \"posTimeStamp\": \"2024-09-06T11:37:01+00:00\",\n" +
                        "                \"transactionNumber\": \"" + transactionNumber + "\",\n" +
                        "                \"languageCode\": \"eng\"\n" +
                        "            },\n" +
                        "            \"totalAmount\": \"" + totalAmount + "\",\n" +
                        "            \"saleItems\": [\n" +
                        "                {\n" +
                        "                    \"itemID\": 1,\n" +
                        "                    \"saleItemType\": \"Sale\",\n" +
                        "                    \"amount\": \"" + totalAmount + "\",\n" +
                        "                    \"originalAmount\": \"" + totalAmount + "\",\n" +
                        "                    \"vatRate\": \"20\",\n" +
                        "                    \"unitMeasure\": \"EA\",\n" +
                        "                    \"unitPrice\": \"200\",\n" +
                        "                    \"quantity\": \"6\",\n" +
                        "                    \"additionalProductCode\": \"5016878010435\"\n" +
                        "                }\n" +
                        "            ],\n" +
                        "            \"customerData\": [\n" +
                        "                {\n" +
                        "                    \"customerDataType\": \"LoyaltyCard\",\n" +
                        "                    \"customerDataValue\": \"70048855626897229\",\n" +
                        "                    \"bonusPoints\": \"" + bonusPoints + "\"\n" +
                        "                }\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

                HttpRequest finalizeRequest = HttpRequest.newBuilder()
                        .uri(URI.create(finalizeRewardsUrl))
                        .header("Authorization", "Basic " + encodedAuth)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(finalizeRewardsBody))
                        .build();

                HttpResponse<String> finalizeResponse = client.send(finalizeRequest, HttpResponse.BodyHandlers.ofString());

                System.out.println("FinalizeRewards Response: " + finalizeResponse.statusCode());


            } else {
                System.out.println("Failed to get rewards: " + getResponse.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testIncreaseStampCounter2(){

        StampCounter stampCounter = new StampCounter("2025-01-30T14:20:00+00:00", 40);
        stampCounter.increasePostTimestamp();
        stampCounter.increaseTransactionNumber();


        IntStream.range(0, stampCounter.getIterationNumber()).forEach(i ->{
            stampCounter.setTestDate(stampCounter.getPostTimeStampArray()[i]);
            stampCounter.setTransactionNumber(stampCounter.getTransactionNumberArray()[i]);

            try {
                HttpClient client = HttpClient.newHttpClient();

                System.out.println(stampCounter.getTestDate());

                HttpRequest getRequest = HttpRequest.newBuilder()
                        .uri(URI.create(stampCounter.getGetRewardsUrl()))
                        .header("Authorization", "Basic " + stampCounter.getEncodedAuth())
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(stampCounter.getRequestBody()))
                        .build();

                HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());


                if (getResponse.statusCode() == 200) {
                    System.out.println("GetRewards Response: " + getResponse.body());

                    JSONObject responseObject = new JSONObject(getResponse.body());

                    String totalAmount = responseObject.getString("totalAmount");
                    System.out.println("The totalamount is: " + totalAmount);
                    String bonusPoints = responseObject
                            .getJSONArray("customerData")
                            .getJSONObject(0)
                            .getString("bonusPoints");


                    HttpRequest finalizeRequest = HttpRequest.newBuilder()
                            .uri(URI.create(stampCounter.getFinalizeRewardsUrl()))
                            .header("Authorization", "Basic " + stampCounter.getEncodedAuth())
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(stampCounter.finalizeStampReward(totalAmount, bonusPoints)))
                            .build();

                    HttpResponse<String> finalizeResponse = client.send(finalizeRequest, HttpResponse.BodyHandlers.ofString());

                    System.out.println("FinalizeRewards Response: " + finalizeResponse.statusCode());


                } else {
                    System.out.println("Failed to get rewards: " + getResponse.statusCode());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        stampCounter.updateTransactionNumber();

    }

    //TODO getRewards returns with 400 :S
    @Test
    public void testGetFinalizeRewards(){
        /*PostGetFinalizeRewardsQA2PL2 postGetFinalizeRewardsQA2PL = new PostGetFinalizeRewardsQA2PL2("2025-01-20T12:10:00+00:00", "70043708003024015", 2604, "plqa2kso_022");
        postGetFinalizeRewardsQA2PL.printConfigProperties();*/
        RestAssured.baseURI = ConfigLoader.get("url");

        /*try {
            HttpClient client = HttpClient.newHttpClient();

            // API URL (Használj pontos URL-t, amit a Postmanben láttál)
            String url = "https://lodqa2.wonderline.eu/frontend-ws/v1/api/oauth2/token";

            // Bejelentkezési adatok
            String clientId = "wl_oa";  // Cseréld le a valós kliens azonosítóra
            String clientSecret = "a";
            // Base64 kódolt hitelesítési adatok
            String encodedAuth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

            // HTTP POST kérés összeállítása
            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.noBody()) // Ha kell body, itt adj meg egy JSON stringet
                    .build();

            // Kérés elküldése és válasz fogadása
            HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

            // Válasz kiírása
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "wl")
                .formParam("client_secret", "a")
                .when()
                .post(RestAssured.baseURI + "/frontend-ws/v1/api/oauth2/token");

        ResponseBody body = response.body();
        System.out.println(body.asString());

        if (response.getStatusCode() == 200) {
            System.out.println("✅ Success: Status code is 200");
        } else {
            System.out.println("❌ Error: Unexpected status code: " + response.getStatusCode());
        }

        // Teszt: Van-e válasz törzs (response body)
        if (body.asString() != null && !body.asString().isEmpty()) {
            System.out.println("✅ Success: Response has a body");
        } else {
            System.out.println("❌ Error: Response body is empty");
        }

        // Teszt: Van-e access_token a válaszban
        JsonPath jsonPath = response.jsonPath();
        String feAuthToken = "";
        if (jsonPath.get("access_token") != null) {
            feAuthToken = jsonPath.getString("access_token");
            System.out.println("✅ Success: Access Token received: " + feAuthToken);
        } else {
            System.out.println("❌ Error: No Access Token found in response");
        }

        // (Opció) Auth token eltárolása, ha szükséges egy következő kéréshez
        if (feAuthToken != null) {
            RestAssured.given().header("Authorization", "Bearer " + feAuthToken);
        }
    }

    @Test
    public void testAuthentication() throws IOException {


        /*OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
        okhttp3.Request request = new okhttp3.Request().Builder()
                .url("https://lodqa2.wonderline.eu/frontend-ws/v1/api/oauth2/token")
                .method("POST", body)
                .addHeader("Authorization", "Basic d2xfb2E6YQ==")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        OkHttpClient client = new OkHttpClient().newBuilder().build();*/
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create("grant_type=client_credentials", okhttp3.MediaType.parse("application/x-www-form-urlencoded"));

        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/frontend-ws/v1/api/oauth2/token")
                .post(body)
                .addHeader("Authorization", "Basic d2xfb2E6YQ==")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        okhttp3.Response response = client.newCall(request).execute();

        System.out.println("Response Code: " + response.code());
        System.out.println("Response Body: " + response.body().string());
    }

    @Test
    public void testResetRequestByCard() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = RequestBody.create("",  okhttp3.MediaType.parse("text/plain"));
        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/frontend-ws/v1/customer/restore/70043708006444111")
                .method("POST", body)
                .addHeader("x-lod-device-id", "wl_collection")
                .addHeader("x-lod-client-id", "wl_postman")
                .addHeader("x-lod-source-application", "LOD WEB")
                .addHeader("x-lod-market", "pl-PL")
                .addHeader("x-lod-country-code", "PL")
                .addHeader("Authorization", "Bearer eyJraWQiOiJNRWRRVFZZeWRHUnZiMEp2TVZReVdUUnZWblJYUlRsWlMwWklSSGRWVTNONk1EZGFOa2s1Y0VSd0wyZGpZV0ZxTVhaSVlWaHlPRVpGUzFWUFoySkVPSGRNVkROc1UwWlhObmcwYlRKQ2NXRlJaamxPWWtFOVBRPT0iLCJhbGciOiJSUzI1NiJ9.eyJz" +
                        "dWIiOiJ3bF9vYSIsImF1ZCI6IndsX29hIiwibmJmIjoxNzQ0MTE4NDc3LCJpc3MiOiJodHRwczovL2xvZHFhMi53b25kZXJsaW5lLmV1IiwiZXhwIjoxNzQ0MTIyMDc3LCJpYXQiOjE3NDQxMTg0NzcsImF1dGhvcml0aWVzIjpbIlJPTEVfQVBJX0NPTlNVTUVSIl19.GGS4VgOaypZPibp_ipyCy8kyxu4y8AENH_Gt" +
                        "0pqr3MEWiDvvjuWnOHYuWtJxFgAQKBweQ0JiQ2m3_A2zvDT2oiMq2pL4Rta3QCI7xYTczKXC" +
                        "nQLyhh3d-gDzMpnOkrf4YB0Ac19UQz6ervL6u93_7fpUHdnmVDgd4uYAu0uIy_FE0_joz38uGpABo8roAX8ALqIADp6dUF-malIyoQg1qMdPs_BI9TXbDtKq3JfGGh93diXs1R3qlj8SBnb50_daJ_1kWEyGRPLmY9zxDDampqYZQCE_eSiWAAQ-FkUmI4Th8zgbXPznMR8c7GjrACvOmGHLuR3HnRcn25d7zoeIPA")
                .addHeader("x-lod-reset-key", "4366343793832978")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("Response Code: " + response.code());

    }

    @Test
    public void testGetRewards() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = RequestBody.create("{\r\n    \"customerData\": " +
                "[\r\n        {\r\n            \"customerDataType\": \"LoyaltyCard\",\r\n            " +
                "\"customerDataValue\": \"700437080064441112\",\r\n            \"loyaltyType\": \"Shell\"\r\n " +
                "       }\r\n    ],\r\n    \"posData\": {\r\n        \"posTimeStamp\": \"2025-03-31T14:50:29+02:00\",\r\n" +
                "        \"transactionNumber\": \"385931770\"\r\n    },\r\n    \"requestData\": {\r\n        \"requestID\": \"385931769\",\r\n" +
                "        \"requestType\": \"OfferQuery\",\r\n        \"workstationID\": \"1\"\r\n    },\r\n    \"tenders\": [\r\n        {\r\n            " +
                "\"tenderID\": 1,\r\n            \"methodOfPayment\": \"Cash\",\r\n            \"methodOfPaymentID\": \"1\",\r\n            \"totalAmount\":" +
                " 10.0,\r\n            \"voucherRules\": []\r\n        }\r\n    ],\r\n    \"saleItems\": [\r\n        {\r\n      \"itemID\": 1,\r\n      " +
                "\"saleItemType\": \"Sale\",\r\n      \"categoryCode\": \"\",\r\n      \"productCode\": \"\",\r\n      \"subCategoryCode\": \"\",\r\n      \"" +
                "amount\": 10.0,\r\n      \"originalAmount\": 10.0,\r\n      \"vatRate\": 12.0,\r\n      \"unitMeasure\": \"EA\",\r\n      \"unitPrice\": 10.0,\r\n  " +
                "    \"quantity\": 1.0,\r\n      \"additionalProductCode\": \"8019561020213\",\r\n      \"additionalProductInfo\": \"HOT DOG\",\r\n   " +
                "   \"loyaltyOffers\": [],\r\n      \"priceAdjustments\": []\r\n        }\r\n    ],\r\n    \"siteData\": {\r\n        \"countryCode\": \"PL\",\r\n  " +
                "      \"siteID\": \"3061\"\r\n    },\r\n    \"totalAmount\": 10.0\r\n}", okhttp3.MediaType.parse("text/plain"));
        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/backend-ws/future/v1/getRewards")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Basic d2w6YQ==")
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println("Response code: " + response.code());
        System.out.println("Response body: " + response.body());
        System.out.println(response.message());
        System.out.println(response.headers());
    }

    @Test
    public void testGetFinalizeRewards3(){
        RewardEngineTester.testGetRewards();
        RewardEngineTester.testFinalizeRewards();
    }

    protected SoftAssert takeShutterBugScreenshotOfDivsCounter(SoftAssert softAssert, By byXpath, ImageDiff imageDiff, String originalRoute, String actualRoute, String screenShotName, String savePathIfDiff) throws IOException {
        counter = Utils.readFile("counter.txt");

        try {
            Shutterbug.shootElement(driver, driver.findElement(byXpath), true)
                    .withName(String.format(screenShotName + "%d.png", counter))
                    .save("C:\\Users\\SőregiKrisztián\\Pictures\\" + actualRoute);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Utils.writeFile("counter.txt");
        Utils.writeFile("src/test/resources/screenshots.properties");
        System.out.println(counter);


        isImagesSame = imageDiff.compareImagePixelDifference(originalRoute, actualRoute,
                savePathIfDiff + "%d.png");

        softAssert.assertTrue(isImagesSame, "The images are different!");

        return softAssert;
    }




    protected SoftAssert takeShutterBugScreenshotOfDivs(SoftAssert softAssert, By byXpath, ImageDiff imageDiff, String originalRoute, String actualRoute, String screenShotName, String savePathIfDiff) throws IOException {

        try {
            Shutterbug.shootElement(driver, driver.findElement(byXpath), true)
                    .withName(String.format(screenShotName/* + ".png"*/))
                    .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\uat_gb_overtime\\");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //TODO Just added .png to original and actual routes
        isImagesSame = imageDiff.compareImagePixelDifferenceBoundedRoute(originalRoute + ".png", actualRoute + ".png",
                savePathIfDiff + ".png");

        softAssert.assertTrue(isImagesSame, "The images are different!");

        return softAssert;
    }

    protected SoftAssert takeShutterBugScreenshotOfDivsQA2PL(SoftAssert softAssert, By byXpath, ImageDiff imageDiff, String originalRoute, String actualRoute, String screenShotName, String savePathIfDiff) throws IOException {

        try {
            Thread.sleep(2000);
            Shutterbug.shootElement(driver, driver.findElement(byXpath), true)
                    .withName(String.format(screenShotName/* + ".png"*/))
                    .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime\\");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //TODO Just added .png to original and actual routes
        isImagesSame = imageDiff.compareImagePixelDifferenceBoundedRouteQA2PL(originalRoute + ".png", actualRoute + ".png",
                savePathIfDiff + ".png");

        softAssert.assertTrue(isImagesSame, "The images are different!");

        /*if(driver != null){
            driver.close();
        }*/
        return softAssert;
    }

    protected SoftAssert takeScrollShutterBugScreenshotOfDivsQA2PL(SoftAssert softAssert, By byXpath, ImageDiff imageDiff, CaptureElement capture, String originalRoute, String actualRoute, String screenShotName, String savePathIfDiff) throws IOException {

        try {
            Shutterbug.shootElement(driver, driver.findElement(byXpath), capture,true)
                    .withName(String.format(screenShotName/* + ".png"*/))
                    .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime\\");

                    //it saves the screenshot inside the project, into the resources folder
                    //.save(System.getProperty("user.dir") + "\\src\\main\\resources\\selenium_test_pictures_overtime");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        isImagesSame = imageDiff.compareImagePixelDifferenceBoundedRouteQA2PL(originalRoute + ".png", actualRoute + ".png",
                savePathIfDiff + ".png");

        softAssert.assertTrue(isImagesSame, "The images are different!");

        /*if(driver != null){
            driver.close();
        }*/
        return softAssert;
    }

    //TODO CAN'T SEE CROPPED IMAGE
    protected SoftAssert takeScrollCroppedShutterBugScreenshotOfDivsQA2PL(SoftAssert softAssert, By byXpath, ImageDiff imageDiff, CaptureElement capture, String originalRoute, String actualRoute, String screenShotName, String savePathIfDiff) throws IOException, InterruptedException {

        try {
            // Kép készítése a teljes elemről
            Shutterbug.shootElement(driver, driver.findElement(byXpath), capture, true)
                    .withName(screenShotName)
                    .save("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime\\");
        } catch (Exception e) {
            System.out.println("Error while taking the screenshot: " + e.getMessage());
        }

        // Eredeti fájl és kivágott fájl útvonalai
        File originalImageFile = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime\\" + screenShotName + ".png");
        File croppedImageFile = new File("C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime\\cropped_" + screenShotName + ".png");

        // Kép kivágása
        try {
            ImageCropper.cropImage(originalImageFile, 100, 200, 300, 400, croppedImageFile);
        } catch (IllegalArgumentException e) {
            System.out.println("Error cropping image: " + e.getMessage());
        }

        // Kép összehasonlítása, ha szükséges
        isImagesSame = imageDiff.compareImagePixelDifferenceBoundedRouteQA2PL(originalRoute + ".png", actualRoute + ".png", savePathIfDiff + ".png");

        // Kép különbség ellenőrzése
        softAssert.assertTrue(isImagesSame, "The images are different!");

        Thread.sleep(1500);
        /*if(driver != null) {
            driver.close();
        }*/
        return softAssert;
    }

    protected SoftAssert takeConcatenatedScrollShutterBugScreenshotOfDivsQA2PL(
            SoftAssert softAssert,
            List<WebElement> webElementList,
            ImageDiff imageDiff,
            String originalRoute,
            String actualRoute,
            String screenShotName,
            String savePathIfDiff) throws IOException {

        try {
            // Define the directory and file path for the screenshot
            String screenshotDirectory = "C:\\Users\\SőregiKrisztián\\Pictures\\selenium_test_pictures_overtime\\qa2_pl_overtime\\";
            String fullScreenshotPath = screenshotDirectory + screenShotName + ".png";

            // Find all elements matching the XPath
            List<WebElement> elements = webElementList;
            for(WebElement element: elements){
                System.out.println(element);
            }

            // Initialize bounding box variables
            int x = Integer.MAX_VALUE, y = Integer.MAX_VALUE;
            int width = 0, height = 0;

            // Calculate the bounding box that encompasses all elements
            for (WebElement element : elements) {
                /*JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", element);*/
                int elementX = element.getLocation().getX();
                int elementY = element.getLocation().getY();
                int elementWidth = element.getSize().getWidth();
                int elementHeight = element.getSize().getHeight();

                x = Math.min(x, elementX); // Smallest X coordinate
                y = Math.min(y, elementY); // Smallest Y coordinate
                width = Math.max(width, elementX + elementWidth - x); // Maximum width
                height = Math.max(height, elementY + elementHeight - y); // Maximum height
            }

            // Take a full-page screenshot using Shutterbug and save it
            Shutterbug.shootPage(driver)
                    .withName(screenShotName)
                    .save(screenshotDirectory);

            // Load the full-page screenshot as a BufferedImage
            BufferedImage fullScreenshot = ImageIO.read(new File(fullScreenshotPath));

            // Crop the image using the calculated bounding box
            BufferedImage croppedScreenshot = fullScreenshot.getSubimage(x, y, width, height);

            // Save the cropped screenshot
            ImageIO.write(croppedScreenshot, "png", new File(
                    screenshotDirectory + screenShotName + ".png"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Compare the images using your ImageDiff implementation
        isImagesSame = imageDiff.compareImagePixelDifferenceBoundedRouteQA2PL(
                originalRoute + ".png",
                actualRoute + ".png",
                savePathIfDiff + ".png"
        );

        // Assert whether the images are the same
        softAssert.assertTrue(isImagesSame, "The images are different!");

        // Close the driver if it's not null
        /*if (driver != null) {
            driver.close();
        }*/


        return softAssert;
    }

    public void updateLocationsIfDiffIsOnlyOne(LinkedHashMap<Integer, Integer[]> actualLocationsMap, LinkedHashMap<Integer, Integer[]> expectedLocationsMap) {
        for (Map.Entry<Integer, Integer[]> entry : actualLocationsMap.entrySet()) {
            Integer key = entry.getKey();
            Integer[] absoluteLocation = entry.getValue();
            Integer[] expectedLocation = expectedLocationsMap.get(key);

            for (int j = 0; j < absoluteLocation.length; j++) {
                int diff = absoluteLocation[j] - expectedLocation[j];

                if (diff == 1) {
                    absoluteLocation[j] -= 1;
                } else if (diff == -1) {
                    absoluteLocation[j] += 1;
                }
                if (diff == 2) {
                    absoluteLocation[j] -= 2;
                } else if (diff == -2) {
                    absoluteLocation[j] += 2;
                }
            }
        }
    }

    public static Queue<String> getUuidsFromMuiQA2PL() throws InterruptedException {
        driver.get("https://lodqa2.wonderline.eu/lod-auth/login");
        driver.findElement(By.id("username")).sendKeys("WLPLKSO1");
        driver.findElement(By.id("password")).sendKeys("bbBB77!!");
        driver.findElement(By.xpath("//input[@value=\"Login\"]")).click();

        driver.findElement(By.xpath("//a[@href=\"search\"]")).click();
        driver.findElement(By.xpath("//vaadin-button[@data-ga-label=\"Search\"]")).click();

        Thread.sleep(3000);
        //waitForWebElement(By.xpath("//vaadin-grid-cell-content[@slot='vaadin-grid-cell-content-23'"), 5);

        Integer rowSize = driver.findElements(By.xpath("//vaadin-grid-cell-content")).size();
        String uuidToFormat = "//vaadin-grid-cell-content[@slot='vaadin-grid-cell-content-%d']";

        Queue<String> uuidQueue = new ArrayDeque<>();
        IntStream.iterate(23, i -> i+6)
                .takeWhile(i -> i < rowSize)
                .forEach(i -> {
                    System.out.println("Keresett XPath: " + String.format(uuidToFormat, i));
                    uuidQueue.add(driver.findElement(By.xpath(String.format(uuidToFormat, i))).getText());
                });

        System.out.println(uuidQueue.size());
        Queue<String> tempQueue = new LinkedList<>(uuidQueue);
        while (!tempQueue.isEmpty()) {
            System.out.println(tempQueue.poll());
        }
        return uuidQueue;
    }



    protected String apiAuthentication() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create("grant_type=client_credentials", okhttp3.MediaType.parse("application/x-www-form-urlencoded"));

        Request request = new Request.Builder()
                .url("https://lodqa2.wonderline.eu/frontend-ws/v1/api/oauth2/token")
                .post(body)
                .addHeader("Authorization", "Basic d2xfb2E6YQ==")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        okhttp3.Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        System.out.println("Response Code: " + response.code());
        System.out.println("Response Body: " +responseBody);

        return responseBody;
    }

    protected String matchAuthTokenRegex(String responseBody){

        Pattern pattern = Pattern.compile("\"access_token\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(responseBody);

        if (matcher.find()) {
            String accessToken = matcher.group(1);
            System.out.println("Access Token: " + accessToken);
            return accessToken;
        } else {
            System.out.println("Access token not found.");
            return null;
        }
    }


}
