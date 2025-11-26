package credentials;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.ParameterizedCredTest;

import java.time.Duration;
import java.util.*;

public class ParameterizedCred {


    WebDriver webDriver;
    WebDriverWait wait;

    int rowCounter = ParameterizedCredTest.validateInvocationCount.incrementAndGet();
    public ParameterizedCred(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
    }


    private void findElementIfVisible(By xpath){
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
    }

    private String loginToSite(String[] credentials, String url, By[] webElements, By[] logoutWebElements, String splashChooser) throws InterruptedException {
        String title;
        System.out.println("The url is " + url);
        for(int i = 0; i < webElements.length; i++){
            if(i == 0){
                System.out.println("The i-edik webelement is: " + webElements[i]);
                findElementIfVisible(webElements[i]);
                webDriver.findElement(webElements[i]).click();
                WebElement splashCountries = webDriver.findElement(By.xpath("(//span[@class=\"title\"])["+ splashChooser + "]"));
                splashCountries.click();
                Thread.sleep(4000);
                WebElement profileIcon = webDriver.findElement(webElements[i]);
                profileIcon.click();

            } else if (i == 1) {
                findElementIfVisible(webElements[i]);
                webDriver.findElement(webElements[i]).sendKeys(credentials[i-1]);
            } else if (i == 2) {
                findElementIfVisible(webElements[i]);

                webDriver.findElement(webElements[i]).sendKeys(credentials[i-1]);
            } else {
                findElementIfVisible(webElements[i]);
                Thread.sleep(2000);
                webDriver.findElement(webElements[i]).click();
            }
        }

        title = logoutFromSite(logoutWebElements);
        System.out.println("The tile is: " + title);

        return title;
    }

    private String logoutFromSite(By[] logoutWebElements) throws InterruptedException {
        findElementIfVisible(logoutWebElements[0]);
        Thread.sleep(10000);

        webDriver.findElement(logoutWebElements[0]).click();
        String title = webDriver.getTitle();
        Thread.sleep(4000);
        findElementIfVisible(logoutWebElements[1]);
        webDriver.findElements(logoutWebElements[1]).getLast().click();

        Thread.sleep(4000);
        return title;
    }

    public boolean validate(String countryCode) throws InterruptedException {

        boolean isValidCredentials;

        int columnCounter = 1;
        List<String[]> credentialsList = new ArrayList<>();
        Iterator<String[]> iterator = ParameterizedCredTest.emailMobilePasswords().iterator();

        while (iterator.hasNext()) {
            credentialsList.add(iterator.next());
        }

        System.out.println("The size of the credential list: " + credentialsList.size());

        String[][] credentials = credentialsList.toArray(new String[0][0]);

        //while(rowCounter < credentialsList.size()) {



        System.out.println("The size of credentialsList is: " + credentialsList.size());
        if (credentials[rowCounter][0].equals("TH") && countryCode.equals("TH")) {

            System.out.println("TH credential invoked");

            String email = credentials[rowCounter][columnCounter++];
            String password = credentials[rowCounter][columnCounter];

            String url = "https://lodqa.wonderline.eu/en-th/";
            webDriver.get(url);


            By[] loginWebElements = new By[]{
                    By.xpath("//img[@alt=\"logout_logo\"]"),
                    By.id("signInMobile"),
                    By.id("currentPassword"),
                    By.id("submit_wizard_form")
            };

            By[] logoutWebElements = new By[]{
                    By.xpath("(//button[@mat-ripple-loader-class-name=\"mat-mdc-button-ripple\"])[7]"),
                    By.xpath("//button[@class=\"navbar-menu-item light dropdown-menu mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-unthemed mat-mdc-button-base\"]")
            };

            isValidCredentials = loginToSite(new String[]{email, password}, url, loginWebElements, logoutWebElements, "3").equals("Shell Go+");

        } else if (credentials[rowCounter][0].equals("NL") && countryCode.equals("NL")) {

            System.out.println("NL credential invoked");


            String email = credentials[rowCounter][columnCounter++];
            String password = credentials[rowCounter][columnCounter--];

            String url = "https://lodqa.wonderline.eu/en-nl";
            webDriver.get(url);

            By[] loginWebElements = new By[]{
                    By.xpath("//button[@class=\"navbar-menu-item only-icon light no-hover mdc-button mdc-button--unelevated mat-mdc-unelevated-button" +
                            " mat-unthemed mat-mdc-button-base ng-star-inserted\"]"),
                    By.id("signInEmailAddress"),
                    By.id("currentPassword"),
                    By.id("submit_wizard_form")
            };

            By[] logoutWebElements = new By[]{
                    By.xpath("(//button[@mat-ripple-loader-class-name=\"mat-mdc-button-ripple\"])[7]"),
                    By.xpath("//button[@class=\"navbar-menu-item light dropdown-menu mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-unthemed mat-mdc-button-base\"]")
            };

            isValidCredentials = loginToSite(new String[]{email, password}, url, loginWebElements, logoutWebElements, "1").equals("Loyalty on Demand");

        } else {
            //TODO Create its own no such a country exception
            System.out.println("Map country credentials do not match either countries.");
            throw new NoSuchElementException();
        }

        return isValidCredentials;
    }


}
