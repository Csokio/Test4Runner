package org.example.pages.uatgb.login;

import org.example.interfaces.Locations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginUatGb {


    WebDriver driver;
    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";
    private final String emailAddress = "gccgb_token1@yopmail.com";
    private final String urlYopMail = "https://yopmail.com/";


    public LoginUatGb(WebDriver driver){
        this.driver = driver;
    }

    private final By LOGIN_PROFILE_ICON = By.xpath("(//div[@class=\"menuButtonTable\"])[8]");

    private final By LOGIN_EMAIL_FIELD = By.id("signInEmailAddress");

    private final By LOGIN_PASSWORD_FIELD = By.id("currentPassword");

    private final By LOGIN_SIGN_IN_BUTTON = By.id("submit_wizard_form");

    private final By YOP_EMAIL_FIELD = By.id("yp");

    private final By YOP_SEARCH_BUTTON = By.xpath("//button[@title=\"Check Inbox @yopmail.com\"]");
    private final By YOP_VERIFICATION_CODE = By.xpath("//div[@style=\"color: rgba(64, 64, 64, 1); height: 58px; margin: 0 16px; text-align: center; " +
            "font-size: 48px; font-weight: normal; line-height: 58px; letter-spacing: 0\"]");

    private final By LOGIN_VERIFICATION_CODE_PLACEHOLDER = By.xpath("//div[@style=\"margin-top: 24px; display: inline-block; text-align: center; width: 100%;\"]");


    public void loginToUATGb(){
        driver.get(url);
        driver.findElement(LOGIN_PROFILE_ICON).click();
        driver.findElement(LOGIN_EMAIL_FIELD).sendKeys(emailAddress);
        driver.findElement(LOGIN_PASSWORD_FIELD).sendKeys("TestBA01!");
        driver.findElement(LOGIN_SIGN_IN_BUTTON).click();
        driver.get(urlYopMail);
        driver.findElement(YOP_EMAIL_FIELD).sendKeys(emailAddress);
        driver.findElement(YOP_SEARCH_BUTTON).click();
        String verificationCode = driver.findElement(YOP_VERIFICATION_CODE).getText();
        driver.findElement(LOGIN_VERIFICATION_CODE_PLACEHOLDER).sendKeys(verificationCode);
    }

}
