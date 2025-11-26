package org.example.pages.uatgb.logout;


import org.example.interfaces.SaveImage;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class WelcomeSection extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;

    public WelcomeSection(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";

    public String getUrl(){
        return this.url;
    }

    private final By WELCOME_SECTION_DIV = By.id("fpHomeWelcomeContainer");
    private final By WELCOME_SECTION = By.xpath("//div[@id=\"fpHomeWelcomeTitle\"]/parent::div/parent::div/parent::div");
    private final By WELCOME_TITLE = By.id("fpHomeWelcomeTitle");
    private final By WELCOME_TEXT = By.id("fpHomeWelcomeText");
    private final By WELCOME_SUBTITLE = By.id("fpHomeWelcomeButtonContainerTitle");
    private final By BUTTON_IOS = By.id("fpHomeWelcomeIosButton");
    private final By BUTTON_ANDROID = By.id("fpHomeWelcomeAndroidButton");
    private String imageURL;

    public By getWELCOME_SECTION_DIV(){
        return this.WELCOME_SECTION_DIV;
    }

    @Override
    public String imageUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(driver.findElement(BUTTON_IOS).findElement(By.xpath("./../img")).getAttribute("src"));
        builder.append(",");
        builder.append(driver.findElement(BUTTON_ANDROID).findElement(By.xpath("./../img")).getAttribute("src"));
        imageURL = builder.toString();
        return imageURL;
    }

    @Override
    public boolean saveImageToFile() {
        ReadImageUrl readIosImage = new ReadImageUrl(imageURL.split(",")[0], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        if(!readIosImage.writeImage("iosapp.png")){
            return false;
        }
        ReadImageUrl readAndroidImage = new ReadImageUrl(imageURL.split(",")[1], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        return readAndroidImage.writeImage("androidapp.png");
    }

    @Override
    public List<String[]> valuesList(){

        List<String[]> resultList = new LinkedList<>();

        By[] byArray = {WELCOME_TITLE, WELCOME_TEXT, WELCOME_SUBTITLE};
        for(int i = 0; i < byArray.length; i++){
            WebElement webElement = driver.findElement(byArray[i]);
            String fontFamily = webElement.getCssValue("font-family");
            String fontSize = webElement.getCssValue("font-size");
            String color = webElement.getCssValue("color");

            resultList.add(new String[]{fontFamily, fontSize, color});
        }

        return resultList;
    }

    @Override
    public Integer[] getPaddings(){
        String[] paddingStringArray = new String[5];

        paddingStringArray[0] = driver.findElement(WELCOME_SECTION).getCssValue("padding-top");
        paddingStringArray[1] = driver.findElement(WELCOME_SECTION).getCssValue("padding-bottom");

        int counter = 2;

        By[] byArray = {WELCOME_TITLE, WELCOME_TEXT, WELCOME_SUBTITLE};
        for(By by: byArray){
            paddingStringArray[counter++] = driver.findElement(by).getCssValue("padding-bottom");
        }

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{WELCOME_SECTION, WELCOME_TITLE, WELCOME_TEXT, WELCOME_SUBTITLE, BUTTON_IOS, BUTTON_ANDROID};
        Arrays.stream(xpathElements).forEach(e -> locationsList.add(driver.findElement(e).getLocation()));
    }

    @Override
    public void createMapFromLocationsList() {
        accumulateMapBasedOnLocations();
    }

    @Override
    public LinkedHashMap<Integer, Integer[]> absoluteLocations() {
        return getMapOfPoints();
    }

    @Override
    public LinkedHashMap<Integer, Integer[]> relativeLocations() {
        return calculateDifference(getMapOfPoints());
    }

    public String buttonIOSRedirectsTo(){
        WebElement buttonHref = driver.findElement(BUTTON_IOS).findElement(By.xpath("./.."));
        return buttonHref.getAttribute("href");
    }

    public String buttonAndroidRedirectsTo(){
        WebElement buttonHref = driver.findElement(BUTTON_ANDROID).findElement(By.xpath("./.."));
        return buttonHref.getAttribute("href");
    }



}
