package org.example.pages.uatgb.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Campaign extends AbstractLocationManager implements SaveImage {

    protected WebDriver driver;

    public Campaign(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";
    private String imageURL;

    private final By CAMPAIGN_DIV = By.id("campaignWideContainer");
    private final By CAMPAIGN_TITLE = By.id("campaignTitle");
    private final By CAMPAIGN_CARD_CONTAINER = By.id("fpOverviewCampaignDataContainer");
    protected final By CAMPAIGN_CARD_TITLES = By.xpath("//div[@class=\"futura-bold campaignCardTitle\"]");
    private final By CAMPAIGN_CARD_TEXTS = By.xpath("//div[@class=\"campaignCardText\"]");
    private final By BUTTON_REGISTER = By.id("commonButtonTitle_u108");
    private final By CAMPAIGN_IMAGE = By.xpath("//div[@class=\"campaignCardBackgroundImageContainer\"]");
    protected final By CAMPAIGN_CARDS = By.xpath("//div[@class=\"campaignCardContainer campaignPopup\"]");


    public By getCAMPAIGN_DIV(){
        return this.CAMPAIGN_DIV;
    }

    public String getUrl(){
        return this.url;
    }
    public String getImageURL(){
        return this.imageURL;
    }


    @Override
    public String imageUrl() {
        StringBuilder finalBuilder = new StringBuilder();
        String regex = "/c.*\\).*g";

        String text;
        boolean found = false;


        int i = 0;
        int regexCount = 0;

        while(i < 3){
            StringBuilder inProgressBuilder = new StringBuilder();

            text = driver.findElements(CAMPAIGN_IMAGE).get(i).getAttribute("style");
            System.out.println(text);

            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);

            if(i == 0) {
                if (matcher.find()){
                    inProgressBuilder.append(driver.getCurrentUrl()).append(matcher.group());
                    finalBuilder.append(inProgressBuilder.toString().split(" ")[0]).append(",");
                    System.out.println(inProgressBuilder);
                    found = true;
                }

            } else if (i == 1 || i == 2) {
                if(matcher.find()){
                    regexCount++;
                    System.out.println(regexCount);

                    inProgressBuilder.append(driver.getCurrentUrl()).append(matcher.group());
                    finalBuilder.append(inProgressBuilder.toString().split(" ")[0]).append(",");
                    found = true;

                }

            }
            i++;
        }
        finalBuilder.setLength(finalBuilder.length() - 1);
        if(!found){
            return null;
        }   else {
            return finalBuilder.toString();
        }
    }

    @Override
    public boolean saveImageToFile() {
        imageURL = imageUrl();
        ReadImageUrl readImageUrl1 = new ReadImageUrl(imageURL.split(",")[0], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        if(!readImageUrl1.writeImage("shell_campaign_image1.png")){
            return false;
        }
        ReadImageUrl readImageUrl2 = new ReadImageUrl(imageURL.split(",")[1], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        if(!readImageUrl2.writeImage("shell_campaign_image2.png")){
            return false;
        }
        ReadImageUrl readImageUrl3 = new ReadImageUrl(imageURL.split(",")[2], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        return readImageUrl3.writeImage("shell_campaign_image3.png");
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();
        WebElement webElement;
        List<WebElement> webElementList;
        By[] byArray = {CAMPAIGN_TITLE, CAMPAIGN_CARD_TITLES, CAMPAIGN_CARD_TEXTS, BUTTON_REGISTER};
        for(int i = 0; i < byArray.length; i++){
            if(byArray[i] == CAMPAIGN_CARD_TITLES || byArray[i] == CAMPAIGN_CARD_TEXTS){
                webElementList = driver.findElements(byArray[i]);
                for(WebElement element: webElementList){
                    String fontFamily = element.getCssValue("font-family");
                    String fontSize = element.getCssValue("font-size");
                    String color = element.getCssValue("color");
                    resultList.add(new String[]{fontFamily, fontSize, color});
                }
            }   else  {
                webElement = driver.findElement(byArray[i]);

                String fontFamily = webElement.getCssValue("font-family");
                String fontSize = webElement.getCssValue("font-size");
                String color = webElement.getCssValue("color");
                resultList.add(new String[]{fontFamily, fontSize, color});
            }
        }
        return resultList;
    }

    @Override
    public Integer[] getPaddings() {
        List<String> paddingList = new ArrayList<>();
        int campaignCardSize = driver.findElements(CAMPAIGN_CARDS).size();

        WebElement campaignTitleElement = driver.findElement(CAMPAIGN_TITLE);
        paddingList.add(campaignTitleElement.findElement(By.xpath("./parent::div/parent::div")).getCssValue("padding-top"));
        paddingList.add(campaignTitleElement.findElement(By.xpath("./parent::div/parent::div")).getCssValue("padding-bottom"));
        paddingList.add(campaignTitleElement.getCssValue("padding-bottom"));

        IntStream.range(0, campaignCardSize).forEach(i -> {
            WebElement campaignCardTitlesElement = driver.findElements(CAMPAIGN_CARD_TITLES).get(i);

            paddingList.add(campaignCardTitlesElement.findElement(By.xpath("./parent::div")).getCssValue("padding"));
            paddingList.add(campaignCardTitlesElement.findElement(By.xpath("./parent::div/parent::div")).getCssValue("margin-right"));
        });

        String[] paddingStringArray = paddingList.toArray(new String[0]);

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{CAMPAIGN_TITLE, CAMPAIGN_CARD_TITLES, CAMPAIGN_CARD_TEXTS, BUTTON_REGISTER, CAMPAIGN_IMAGE};
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

    public boolean clickBtnReg(){
        driver.findElement(BUTTON_REGISTER).click();
        String str = driver.getCurrentUrl();
        return str.equals("https://test.login.consumer.shell.com/register");
    }

    public CampaignPopup goToCampaignPopUp(){
        return new CampaignPopup(this.driver);
    }

}
