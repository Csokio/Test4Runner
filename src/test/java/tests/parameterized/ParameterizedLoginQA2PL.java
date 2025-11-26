package tests.parameterized;

import maytestuse.probetestsuite.DummyMain;
import tests.WebTestTH;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import tests.qa2pl.login.QA2PLIN;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
//TODO Change parameters to email and password
@RunWith(Parameterized.class)
public class ParameterizedLoginQA2PL extends WebTestTH {
    protected String countryIdentifier;

    protected String ssoIdentifier;

    protected String url;

    protected static AtomicInteger credInvocationCount  = new AtomicInteger(-1);

    public void setUrl(String url){
        this.url = url;
    }

    private final String password = "aaAA11??";

    int rowCounter = credInvocationCount .incrementAndGet();

    private String[] customerUUID = new String[2];

    public String[] getCustomerUUID(){
        return this.customerUUID;
    }



    //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));;

    public ParameterizedLoginQA2PL(){

    }

    /*public ParameterizedLoginQA2PL( String countryIdentifier, String ssoIdentifier) {
        this.countryIdentifier = countryIdentifier;
        this.ssoIdentifier = ssoIdentifier;
    }*/

    @Parameterized.Parameters
    public static Collection<String[]> emailMobilePasswordsPL() throws InterruptedException {

        HashMap<Integer, String> plMap = new HashMap<>(){{
            put(0, "PL");
        }};

        Queue<String> uuidQueue = getUuidsFromMuiQA2PL();
        System.out.println("The size of the uuidQueue is: " + uuidQueue.size());

        String[][] uuidArray = new String[uuidQueue.size()][2];
        int index = 0;
        while (!uuidQueue.isEmpty()) {
            uuidArray[index][0] = plMap.get(0);
            uuidArray[index][1] = uuidQueue.poll();
            index++;
        }

        System.out.println("The size of the uuidArray is: " + uuidArray.length);
        return Arrays.asList(uuidArray);

        /*return Arrays.asList(new String[][] {
                { plMap.get(0), "6c4bd68f-bef4-3655-92d8-4e65160d7202"},
                { plMap.get(0), "5e4c3796-99f3-3527-9e64-fee00808d6c7"}
        });*/

    }

    @Test
    public void testLoginWithMultipleUsers() throws InterruptedException, IOException {
        Instant startTime = Instant.now();
        softAssert = new SoftAssert();

        boolean isValidCredentials;

        int columnCounter = 1;
        List<String[]> credentialsList = new ArrayList<>();
        Iterator<String[]> iterator = emailMobilePasswordsPL().iterator();

        while (iterator.hasNext()) {
            credentialsList.add(iterator.next());
        }

        System.out.println("The size of the credential list: " + credentialsList.size());

        System.out.println("The size of credentialsList is: " + credentialsList.size());
        // Convert List to a 2D array
        String[][] credentials = credentialsList.toArray(new String[0][0]);
        System.out.println("The size of credentialsList is: " + credentialsList.size());
        String[] uuid = new String[credentials.length];


        // Loop through each row of the credentials
        for (int rowCounter = 0; rowCounter < credentials.length; rowCounter++) {
            if (credentials[rowCounter][0].equals("PL")) {
                System.out.println("PL credential invoked");

                // Create a new array to store the UUIDs

                // Store the first and second UUIDs in the uuid array
                // Assuming the UUIDs are in column 1 of the credentials array
                uuid[rowCounter] = credentials[rowCounter][columnCounter]; // First UUID

                // Now you can use the uuid array as needed
                System.out.println("UUID 1: " + uuid[0]);
                System.out.println("UUID 2: " + uuid[1]);
            }  else {
                //TODO Create its own no such a country exception
                System.out.println("Map country credentials do not match either countries.");
                throw new NoSuchElementException();
            }
        }

        customerUUID = uuid;


        String url = "https://lodqa2.wonderline.eu/pl-pl/";

        By[] loginWebElements = new By[]{
                By.xpath("(//a[@class=\"menuButtonCellLink tabOnMe\"])[2]"),     //login btn in header
                By.id("uuid"),      //uuid field
                By.xpath("//input[@name=\"return\"]")      //return to lod btn
        };

        By[] logoutWebElements = new By[]{
                By.id("loggedin_activator_label"),
                By.id("menu_label_logout")
        };

        Set<String> actualSetOfTitles = LoginToSite(uuid, url, loginWebElements, logoutWebElements, softAssert);
        Set<String> expectedSetOfTitles = new HashSet<>();
        expectedSetOfTitles.add("Program lojalnościowy");

        //isValidCredentials = LoginToSite(new String[]{uuid}, url, loginWebElements, logoutWebElements).equals("Program lojalnościowy");

        Set<String> unionSet = Stream.concat(actualSetOfTitles.stream(), expectedSetOfTitles.stream())
                .collect(Collectors.toSet());

        isValidCredentials = unionSet.contains("Program lojalnościowy");


        //Assertions.assertTrue(isValidCredentials);

        softAssert.assertTrue(isValidCredentials);
        softAssert.assertAll();

        //initializeTestExecutionData("testLoginWithMultipleUsers", startTime, assertMethod(softAssert, true));
    }
    private Set<String> LoginToSite(String[] credentials, String url, By[] webElements, By[] logoutWebElements, SoftAssert softAssert) throws InterruptedException, IOException {
        Set<String> title = new HashSet<>();
        System.out.println("The url is " + url);
        System.out.println("The lenght of credentials is: " + credentials.length);
        int iterationIndex = 0;
        while(iterationIndex < credentials.length){
            driver.get(url);

            for(int i = 0; i < webElements.length; i++){

                if(i == 0){
                    System.out.println("The i-edik webelement is: " + webElements[i]);
                    WebElement profileIcon = driver.findElement(webElements[i]);
                    profileIcon.click();
                    //driver.navigate().back();
                } else if (i == 1) {
                    findElementIfVisible(webElements[i]);
                    driver.findElement(webElements[i]).clear();
                    if(iterationIndex == 0){
                        driver.findElement(webElements[i]).sendKeys(credentials[i-1]);
                    } else {
                        driver.findElement(webElements[i]).sendKeys(credentials[iterationIndex]);
                    }
                } else  {
                    findElementIfVisible(webElements[i]);
                    driver.findElement(webElements[i]).click();
                }
            }

            //TODO IMPLEMENT LOGGED IN TEST SUITE TO RUN HERE
            DummyMain.main(new String[0]);
            /*QA2PLIN qa2PLIN = new QA2PLIN();
            qa2PLIN.runTest(softAssert);*/

            //qa2PLIN.runTest(softAssert, credentials[iterationIndex]);
            //qa2PLIN.runTest(credentials[iterationIndex]);

            //Ez itt jóóó
            /*QA2PLIN qa2PLIN = new QA2PLIN();
            qa2PLIN.runTest(credentials[iterationIndex], softAssert);*/

            title.add(logoutFromSite(logoutWebElements));
            System.out.println("The tile is: " + title);
            iterationIndex++;
        }

        return title;
    }

    private String logoutFromSite(By[] logoutWebElements) throws InterruptedException {
        findElementIfVisible(logoutWebElements[0]);
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(logoutWebElements[0]));
        driver.findElement(logoutWebElements[0]).click();
        //driver.findElement(logoutWebElements[0]).click();

        String title = driver.getTitle();

        Thread.sleep(1000);
        findElementIfVisible(logoutWebElements[1]);
        driver.findElement(logoutWebElements[1]).click();

        Thread.sleep(1000);
        return title;
    }

    private void findElementIfVisible(By xpath){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
    }

    private void clickCopyWithRobot(ArrayList<String> tabs){
        try {
            Robot robot = new Robot();

            Thread.sleep(2000);

            int x = 670;
            int y = 650;
            robot.mouseMove(x, y);

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            driver.switchTo().window(tabs.get(0));
            driver.findElement(By.xpath("//div[@style=\"margin-top: 24px; display: inline-block; text-align: center; width: 100%;\"]")).click();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            Thread.sleep(1000);

        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}

