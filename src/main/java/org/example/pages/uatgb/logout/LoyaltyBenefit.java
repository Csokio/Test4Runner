package org.example.pages.uatgb.logout;


import org.example.interfaces.SaveImage;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.example.utils.SvgToPngTranscoder;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class LoyaltyBenefit extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;

    public LoyaltyBenefit(WebDriver driver){
        this.driver = driver;
    }

    private final By BENEFIT_DIV = By.id("loyaltyBenefitsContent");
    private final By BENEFIT_TITLE = By.id("loyaltyBenefitsTitle");
    private final By BENEFIT_CARDS = By.xpath("//div[@id='loyaltyBenefitsCards']/div");
    private final By BENEFIT_IMAGE = By.xpath("//img[@id='loyaltyBenefitsImg']");
    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";
    private String imageURL;

    public By getBENEFIT_DIV(){
        return this.BENEFIT_DIV;
    }


    public String getUrl(){
        return this.url;
    }

    @Override
    public String imageUrl() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < driver.findElement(BENEFIT_CARDS).findElements(By.xpath("//img[@class='loyalty-benefits-card-img']")).size(); i++){
            //System.out.println(driver.findElement(BENEFIT_CARDS).findElements(By.xpath("//img[@class='loyalty-benefits-card-img']")).size());
            builder.append(driver.findElement(BENEFIT_CARDS).findElements(By.xpath("//img[@class='loyalty-benefits-card-img']")).get(i).getAttribute("src"));
            //System.out.println(builder.append(driver.findElement(BENEFIT_CARDS).findElements(By.xpath("//img[@class='loyalty-benefits-card-img']")).get(i).getAttribute("src")));
            if(i < driver.findElements(BENEFIT_CARDS).size() - 1 ){
                builder.append(",");
            }
        }
        builder.append(",").append(driver.findElement(BENEFIT_IMAGE).getAttribute("src"));
        imageURL = builder.toString();
        return imageURL;
    }

    @Override
    public boolean saveImageToFile() {

        SvgToPngTranscoder svgToPngTranscoder;

        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb";
        List<ReadImageUrl> images = Arrays.asList(
                new ReadImageUrl(imageURL.split(",")[0], basePath),
                new ReadImageUrl(imageURL.split(",")[1], basePath),
                new ReadImageUrl(imageURL.split(",")[2], basePath),
                new ReadImageUrl(imageURL.split(",")[3], basePath),
                new ReadImageUrl(imageURL.split(",")[4], basePath)
        );

        List<String> filenames = Arrays.asList(
                "loyalty_discount.svg",
                "loyalty_scan.svg",
                "loyalty_spend.svg",
                "loyalty_surprise.svg",
                "loyalty_main.svg"
        );

        AtomicBoolean allSuccess = new AtomicBoolean(true);

        IntStream.range(0, images.size()).forEach(i -> {
            if (!images.get(i).writeImage(filenames.get(i))) {
                allSuccess.set(false);
            }
        });

        for(int i = 0; i < filenames.size(); i++){
            svgToPngTranscoder = new SvgToPngTranscoder(filenames.get(i), filenames.get(i).replace("svg","png"));
        }

        return allSuccess.get();
    }

    //TODO VALUES INSIDE BENEFIT CARDS
    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();

        String[] xpathInsideCard = {"//div[@class=\"loyalty-benefits-card-title futura-bold\"]", "//div[@class=\"loyalty-benefits-card-desc\"]"};

        WebElement webElement;
        List<WebElement> webElementList;
        By[] byArray = {BENEFIT_TITLE, BENEFIT_CARDS};
        for(int i = 0; i < byArray.length; i++){
            if(byArray[i] == BENEFIT_CARDS){
                for(int j = 0; j < xpathInsideCard.length; j++){
                    webElementList = driver.findElement(byArray[i]).findElements(By.xpath(xpathInsideCard[j]));
                    for(WebElement element: webElementList){
                        String fontFamily = element.getCssValue("font-family");
                        String fontSize = element.getCssValue("font-size");
                        String color = element.getCssValue("color");
                        resultList.add(new String[]{fontFamily, fontSize, color});
                    }
                }
            }   else {
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
        String[] paddingStringArray = new String[14];

        paddingStringArray[0] = driver.findElement(BENEFIT_TITLE).getCssValue("padding-top");
        paddingStringArray[1] = driver.findElement(BENEFIT_TITLE).getCssValue("padding-bottom");

        List<WebElement> cardElements = driver.findElements(BENEFIT_CARDS);

        int counter = 2;
        int titleCounter = 0;
        for(WebElement element: cardElements){
            paddingStringArray[counter++] = element.getCssValue("margin-bottom");
            paddingStringArray[counter++] = element.findElement(By.xpath("./div")).getCssValue("padding");
            paddingStringArray[counter++] = element.findElements(By.xpath("//div[@class=\"loyalty-benefits-card-text-container\"]/div")).get(titleCounter+2).getCssValue("margin-bottom");
        }

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{BENEFIT_TITLE, BENEFIT_IMAGE, BENEFIT_CARDS};
        Arrays.stream(xpathElements).forEach(e -> {
            if(driver.findElements(e).size() > 1){
                for(int i = 0; i < driver.findElements(e).size(); i++){
                    locationsList.add(driver.findElements(e).get(i).getLocation());
                }
            } else {
                locationsList.add(driver.findElement(e).getLocation());
            }
        });
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
}
