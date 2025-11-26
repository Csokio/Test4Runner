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


public class PromoTile extends AbstractLocationManager implements SaveImage {


    private WebDriver driver;

    public PromoTile(WebDriver driver) {
        this.driver = driver;
    }

    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";

    public String getUrl() {
        return this.url;
    }

    private final By PROMO_TILE_DIV = By.id("content_u19");
    private final By IMAGE_PROMO_TILE = By.xpath("//div[@class=\"fpPromoTileBackgroundDiv desktop-only\"]");
    private final By MESSAGE_TITLE = By.id("fpPromoTileMessageTitle");
    private final By MESSAGE_TEXT = By.id("fpPromoTileMessageText");
    private final By BUTTON_LOGIN = By.id("fpPromoTileMessageLoginLink");
    private final By BUTTON_REGISTER = By.id("commonButtonTitle");
    private String imageURL;

    public By getPROMO_TILE_DIV(){
        return this.PROMO_TILE_DIV;
    }

    @Override
    public String imageUrl() {
        imageURL = driver.findElement(IMAGE_PROMO_TILE).findElement(By.xpath("./following-sibling::div/img")).getAttribute("src");

        return imageURL;
    }

    @Override
    public boolean saveImageToFile(){
        ReadImageUrl readImageUrl = new ReadImageUrl(imageURL, "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        return readImageUrl.writeImage("promo_tile_image.png");
    }

    @Override
    public List<String[]> valuesList(){

        List<String[]> resultList = new LinkedList<>();

        By[] byArray = {MESSAGE_TITLE, MESSAGE_TEXT, BUTTON_REGISTER};
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
        String[] paddingStringArray = new String[10];
        int[] numbersArray = new int[]{1,3,4,7};

        int counter = 2;

        paddingStringArray[0] = driver.findElement(BUTTON_LOGIN).getCssValue("padding-top");
        paddingStringArray[1] = driver.findElement(BUTTON_LOGIN).getCssValue("padding-bottom");


        for(int number: numbersArray){
            WebElement webElement = findMultiWebElementForPaddings(number);
            if(number == 1){
                paddingStringArray[counter++] = webElement.getCssValue("padding-top");
                paddingStringArray[counter++] = webElement.getCssValue("padding-bottom");
            } else if (number == 7) {
                paddingStringArray[counter++] = webElement.getCssValue("padding-top");
            } else if (number == 3) {
                paddingStringArray[counter++] = webElement.getCssValue("padding-top");
                paddingStringArray[counter++] = webElement.getCssValue("padding-right");
                paddingStringArray[counter++] = webElement.getCssValue("padding-bottom");
                paddingStringArray[counter++] = webElement.getCssValue("padding-left");
            } else {
                paddingStringArray[counter++] = webElement.getCssValue("padding");
            }
        }

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{IMAGE_PROMO_TILE, MESSAGE_TITLE, MESSAGE_TEXT, BUTTON_LOGIN, BUTTON_REGISTER};
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

    public String[] getLoginValues(){
        WebElement promoLogin = driver.findElement(BUTTON_LOGIN);
        String fontFamily = promoLogin.getCssValue("font-family");
        String fontSize = promoLogin.getCssValue("font-size");
        String color = promoLogin.getCssValue("color");
        String underline = promoLogin.getCssValue("text-decoration-line");

        return new String[]{fontFamily, fontSize, color, underline};
    }


    public String clickPromoTileLogin(){
        driver.findElement(BUTTON_LOGIN).click();
        return driver.getCurrentUrl();
    }

    public String clickPromoTileRegister(){
        driver.findElement(BUTTON_REGISTER).click();
        return driver.getCurrentUrl();
    }

    public WebElement findMultiWebElementForPaddings(int number){
        return driver.findElement(By.xpath("(//div[@class=\"fpPromoTileBackgroundDiv desktop-only\"]//parent::div//parent::div)[" + number + "]"));
    }
}
