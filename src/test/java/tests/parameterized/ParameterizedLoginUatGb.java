package tests.parameterized;

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

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
//TODO Change parameters to email and password
@RunWith(Parameterized.class)
public class ParameterizedLoginUatGb extends WebTestTH {
    protected String countryIdentifier;

    protected String inputMobile;
    protected String inputPassword;


    protected String url;

    protected static AtomicInteger credInvocationCount  = new AtomicInteger(-1);

    public void setUrl(String url){
        this.url = url;
    }

    private final String password = "aaAA11??";

    int rowCounter = credInvocationCount .incrementAndGet();

    //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));;

    public ParameterizedLoginUatGb(){

    }

    public ParameterizedLoginUatGb( String countryIdentifier, String inputMobile, String inputPassword) {
        this.countryIdentifier = countryIdentifier;
        this.inputMobile = inputMobile;
        this.inputPassword = inputPassword;
    }

    @Parameterized.Parameters
    public static Collection<String[]> emailMobilePasswordsGB() {

        HashMap<Integer, String> gbMap = new HashMap<>(){{
            put(0, "GB");
        }};


        return Arrays.asList(new String[][] {
                //{ gbMap.get(0), "krisztiansoreg+1@gmail.com", "aaAA11??"},
                { gbMap.get(0), "gccgb_token1@yopmail.com", "TestBA01!"}
        });

    }

    @Test
    public void testLoginWithMultipleUsers() throws InterruptedException {
        Instant startTime = Instant.now();
        softAssert = new SoftAssert();

        boolean isValidCredentials;

        int columnCounter = 1;
        List<String[]> credentialsList = new ArrayList<>();
        Iterator<String[]> iterator = emailMobilePasswordsGB().iterator();

        while (iterator.hasNext()) {
            credentialsList.add(iterator.next());
        }

        System.out.println("The size of the credential list: " + credentialsList.size());

        String[][] credentials = credentialsList.toArray(new String[0][0]);



        System.out.println("The size of credentialsList is: " + credentialsList.size());
        if (credentials[rowCounter][0].equals("GB")) {

            System.out.println("GB credential invoked");

            String email = credentials[rowCounter][columnCounter++];
            String password = credentials[rowCounter][columnCounter];

            String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";
            driver.get(url);


            By[] loginWebElements = new By[]{
                    By.id("fp_login_button"),
                    By.id("signInEmailAddress"),
                    By.id("currentPassword"),
                    By.id("submit_wizard_form")
            };

            By[] logoutWebElements = new By[]{
                    By.xpath("//li[@id=\"loggedin_cell\"]"),
                    By.xpath("//label[@id=\"menu_label_logout\"]")
            };

            isValidCredentials = LoginToSite(new String[]{email, password}, url, loginWebElements, logoutWebElements).equals("Shell Go+");

        } else {
            //TODO Create its own no such a country exception
            System.out.println("Map country credentials do not match either countries.");
            throw new NoSuchElementException();
        }

        Assertions.assertTrue(isValidCredentials);
        //initializeTestExecutionData("testLoginWithMultipleUsers", startTime, assertMethod(softAssert, true));
    }
    private String LoginToSite(String[] credentials, String url, By[] webElements, By[] logoutWebElements) throws InterruptedException {
        String title;
        System.out.println("The url is " + url);
        for(int i = 0; i < webElements.length; i++){
            if(i == 0){
                System.out.println("The i-edik webelement is: " + webElements[i]);
                WebElement profileIcon = driver.findElement(webElements[i]);
                profileIcon.click();

            } else if (i == 1) {
                findElementIfVisible(webElements[i]);
                driver.findElement(webElements[i]).sendKeys(credentials[i-1]);
            } else if (i == 2) {
                findElementIfVisible(webElements[i]);

                driver.findElement(webElements[i]).sendKeys(credentials[i-1]);
            } else {
                findElementIfVisible(webElements[i]);
                Thread.sleep(2000);
                driver.findElement(webElements[i]).click();
            }
        }

        ((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank');");

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.get("https://yopmail.com/");

        driver.findElement(By.xpath("//button[@class=\"fc-button fc-cta-consent fc-primary-button\"]")).click();
        driver.findElement(By.id("login")).sendKeys(credentials[0]);
        driver.findElement(By.id("refreshbut")).click();

        clickCopyWithRobot(tabs);

        title = logoutFromSite(logoutWebElements);
        System.out.println("The tile is: " + title);

        return title;
    }

    private String logoutFromSite(By[] logoutWebElements) throws InterruptedException {
        findElementIfVisible(logoutWebElements[0]);
        Thread.sleep(2000);

        driver.findElement(logoutWebElements[0]).click();
        driver.findElement(logoutWebElements[0]).click();

        String title = driver.getTitle();

        Thread.sleep(1000);
        findElementIfVisible(logoutWebElements[1]);
        driver.findElements(logoutWebElements[1]).getLast().click();

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

