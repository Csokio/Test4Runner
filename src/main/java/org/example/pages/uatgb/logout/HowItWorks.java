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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class HowItWorks extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;

    public HowItWorks(WebDriver driver){
        this.driver = driver;
    }

    private final By HOWITWORKS_DIV = By.id("fpHomeHowItWorksContainer");
    private final By HOWITWORKS_TITLE = By.id("fpHomeHowItWorksTitle");
    private final By HOWITWORKS_CARDS = By.xpath("//div[@class=\"fpHomeHowItWorksCard\"]");

    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";
    private String imageURL;


    public By getHOWITWORKS_DIV(){
        return this.HOWITWORKS_DIV;
    }

    public String getUrl(){
        return this.url;
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < driver.findElements(HOWITWORKS_CARDS).size(); i++){
            stringBuilder.append(driver.findElements(HOWITWORKS_CARDS).get(i).findElement(By.xpath("./div/div[1]/img")).getAttribute("src"));
            if(i < driver.findElements(HOWITWORKS_CARDS).size() - 1 ){
                stringBuilder.append(",");
            }
        }

        imageURL = stringBuilder.toString();
        return imageURL;
    }

    @Override
    public boolean saveImageToFile() {
        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb";
        List<ReadImageUrl> images = Arrays.asList(
                new ReadImageUrl(imageURL.split(",")[0], basePath),
                new ReadImageUrl(imageURL.split(",")[1], basePath),
                new ReadImageUrl(imageURL.split(",")[2], basePath)
        );

        List<String> fileNames = Arrays.asList(
                "star_image.png",
                "cadeau_image.png",
                "text_image.png"
        );

        AtomicBoolean allSuccess = new AtomicBoolean(true);

        IntStream.range(0, images.size()).forEach(i -> {
            if(!images.get(i).writeImage(fileNames.get(i))){
                allSuccess.set(false);
            }
        });

        return allSuccess.get();
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();

        String[] xpathInsideCard = {"//div[@class=\"fpHomeHowItWorksCardTitle futura-bold\"]", "//div[@class=\"fpHomeHowItWorksCardText\"]"};

        WebElement webElement;
        List<WebElement> webElementList;
        By[] byArray = {HOWITWORKS_TITLE, HOWITWORKS_CARDS};
        for(int i = 0; i < byArray.length; i++){
            if(byArray[i] == HOWITWORKS_CARDS){
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
        String[] paddingStringArray = new String[12];

        paddingStringArray[0] = driver.findElement(HOWITWORKS_TITLE).findElement(By.xpath("./ancestor::div[3]")).getCssValue("padding-top");
        paddingStringArray[1] = driver.findElement(HOWITWORKS_TITLE).findElement(By.xpath("./ancestor::div[3]")).getCssValue("padding-bottom");

        paddingStringArray[2] = driver.findElement(HOWITWORKS_TITLE).getCssValue("padding-bottom");

        List<WebElement> webElementList = driver.findElements(HOWITWORKS_CARDS);

        for(int i = 3; i < paddingStringArray.length-2; i += 3){
            for(WebElement element: webElementList){
                paddingStringArray[i] = element.getCssValue("padding");
                paddingStringArray[i+1] = element.findElement(By.xpath("./div/div[2]")).getCssValue("padding-left");
                paddingStringArray[i+2] = element.findElement(By.xpath("//div[@class='fpHomeHowItWorksCardTitle futura-bold']")).getCssValue("padding-bottom");
            }
        }

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{HOWITWORKS_TITLE, HOWITWORKS_CARDS};
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
