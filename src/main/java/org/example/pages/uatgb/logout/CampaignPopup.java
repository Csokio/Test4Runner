package org.example.pages.uatgb.logout;

import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CampaignPopup extends Campaign {

    private final By CAMPAIGN_POPUP_CONTAINER = By.id("campaignPreContainer_u37");
    private final By CAMPAIGN_POPUP_IMAGE = By.id("campaignPopupImageContainer");
    private final By CAMPAIGN_POPUP_TITLE = By.xpath("(//div[@id=\"campaignPopupContentContainer\"]/div)[1]");
    private final By CAMPAIGN_POPUP_BUTTON_CONTAINER = By.id("campaignPopupButtonContainer");
    private final By CAMPAIGN_POPUP_SECONDARY_BUTTON = By.id("secondaryButtonTitle_u60");
    private final By CAMPAIGN_POPUP_PRIMARY_BUTTON = By.id("primaryButtonTitle_u65");


    private final By CAMPAIGN_POPUP_CONTAINERS = By.xpath("//div[@class=\"campaignPreContainer\"]");
    private final By CAMPAIGN_POPUP_TITLES = By.xpath("//div[@class=\"campaignPopupTitle futura-bold mobile-width100\"]");
    private final By CAMPAIGN_POPUP_IMAGES = By.xpath("//div[@class=\"campaignPopupImageContainer\"]");
    private final By CAMPAIGN_POPUP_BUTTONS_CONTAINERS = By.xpath("//div[@class=\"campaignPopupButtonContainer\"]");
    private final By CAMPAIGN_POPUP_SECONDARY_BUTTONS = By.xpath("//div[@class=\"campaignPopupSingInFpButton fpCommonSecondary fpCommonButtonClass rippleContainer\"]/span/div");
    private final By CAMPAIGN_POPUP_PRIMARY_BUTTONS = By.xpath("//div[@class=\"campaignPopupRegFpButton fpCommonButtonClass rippleContainer\"]/span/div");
    protected CampaignPopup(WebDriver driver) {
        super(driver);
    }

    private String imageUrl;

    @Override
    public String imageUrl(){
        StringBuilder stringBuilder = new StringBuilder();

        String regex = "/c.*\\).*g";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        int maxRange = driver.findElements(CAMPAIGN_POPUP_IMAGES).size();

        IntStream.range(0,maxRange).forEach(
            i -> {
                Matcher matcher = pattern.matcher(driver.findElements(CAMPAIGN_POPUP_IMAGES).get(i).getAttribute("style"));
                if (matcher.find()) {
                    stringBuilder.append((driver.getCurrentUrl() + matcher.group()).split(" ")[0]).append(",");
                    System.out.println(imageUrl);
                }
        });

        stringBuilder.setLength(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        System.out.println(imageUrl);
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile(){
        AtomicBoolean isImagesSaved = new AtomicBoolean(false);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"shell_campaign_popup_image1.png", "shell_campaign_popup_image2.png", "shell_campaign_popup_image3.png"};

        IntStream.range(0,4).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(imageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
            isImagesSaved.set(readImageUrl.writeImage(imageNames[i]));
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();
        By[] byArray = {CAMPAIGN_POPUP_TITLES, CAMPAIGN_POPUP_SECONDARY_BUTTONS, CAMPAIGN_POPUP_PRIMARY_BUTTONS};

        IntStream.range(0,3).forEach(i ->{
            for(int j = 0; j < byArray.length; j++){
                WebElement webElement = driver.findElements(byArray[i]).get(j);
                String fontFamily = webElement.getCssValue("font-family");
                String fontSize = webElement.getCssValue("font-size");
                String color = webElement.getCssValue("color");
                resultList.add(new String[]{fontFamily, fontSize, color});
            }
        });

        for(String[] item: resultList){
            System.out.println(Arrays.toString(item));
        }

        return resultList;
    }


    @Override
    public Integer[] getPaddings(){
        String[] paddingStringArray = new String[24];

        IntStream.range(0,3).forEach(i -> {
            int counter = i * 8;

            System.out.println(counter);
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_CONTAINERS).get(i).getCssValue("padding-top");
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_CONTAINERS).get(i).getCssValue("padding-right");
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_CONTAINERS).get(i).getCssValue("padding-bottom");
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_CONTAINERS).get(i).getCssValue("padding-left");
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_IMAGES).get(i).getCssValue("margin-right");
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_TITLES).get(i).getCssValue("padding-bottom");
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_BUTTONS_CONTAINERS).get(i).getCssValue("padding-top");
            paddingStringArray[counter++] = driver.findElements(CAMPAIGN_POPUP_BUTTONS_CONTAINERS).get(i).getCssValue("padding-bottom");

        });

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    public void clickOnCardBasedOnNumber(int number){
        driver.findElements(CAMPAIGN_CARDS).get(number-1).click();
    }

    public String clickRobot(int xCoordinate, int yCoordinate) throws AWTException {

        Robot robot = new Robot();
        robot.mouseMove(xCoordinate, yCoordinate);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        System.out.println(driver.getCurrentUrl());
        String str = driver.getCurrentUrl().split("/?redirect")[0];

        return str.chars()
                .limit(str.length())
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    public String clickSecondaryBtn(int index){

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(CAMPAIGN_POPUP_SECONDARY_BUTTONS).get(index)).perform();
        action.click().perform();

        String str = driver.getCurrentUrl().split("/?redirect")[0];

        //This return gives the same result as "return str" would
        return str.chars()
                .limit(str.length())
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    public String clickPrimaryBtn(int index){

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(CAMPAIGN_POPUP_PRIMARY_BUTTONS).get(index)).perform();
        action.click().perform();

        String str = driver.getCurrentUrl().split("/?redirect")[0];

        //This return gives the same result as "return str" would
        return str.chars()
                .limit(str.length())
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }
}
