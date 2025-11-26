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

public class DownloadApp extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;

    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";
    private String imageUrl;

    private final By DOWNLOAD_APP_DIV = By.id("homePageDownloadAppWideContainer");
    private final By DOWNLOAD_APP_IMAGES = By.xpath("//div[@class='lfDownloadAppStoreImages']//img");
    private final By DOWNLOAD_APP_TITLE = By.id("homePageDownloadAppTitle");
    private final By DOWNLOAD_APP_TEXT = By.id("homePageDownloadAppText");

    public DownloadApp(WebDriver driver){
        this.driver = driver;
    }

    public By getDOWNLOAD_APP_DIV(){
        return this.DOWNLOAD_APP_DIV;
    }

    public String getUrl(){
        return this.url;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }


    @Override
    public String imageUrl() {
        StringBuilder strBuilder = new StringBuilder();
        IntStream.range(0, driver.findElements(DOWNLOAD_APP_IMAGES).size()).forEach(
                i -> strBuilder.append(driver.findElements(DOWNLOAD_APP_IMAGES).get(i).getAttribute("src")).append(",")
        );

        strBuilder.setLength(strBuilder.length()-1);
        imageUrl = strBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb";
        List<ReadImageUrl> images = Arrays.asList(
                new ReadImageUrl(imageUrl.split(",")[0], basePath),
                new ReadImageUrl(imageUrl.split(",")[1], basePath)
        );

        List<String> fileNames = Arrays.asList(
                "apple_image.png",
                "android_image.png"
        );

        AtomicBoolean allSuccess = new AtomicBoolean(true);

        IntStream.range(0,images.size()).forEach(i -> {
            if(!images.get(i).writeImage(fileNames.get(i))){
                allSuccess.set(false);
            }
        });

        return allSuccess.get();
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();

        WebElement webElement;
        By[] byArray = {DOWNLOAD_APP_TITLE, DOWNLOAD_APP_TEXT};
        for(int i = 0; i < byArray.length; i++){
            webElement = driver.findElement(byArray[i]);
            String fontFamily = webElement.getCssValue("font-family");
            String fontSize = webElement.getCssValue("font-size");
            String color = webElement.getCssValue("color");
            resultList.add(new String[]{fontFamily, fontSize, color});
        }

        return resultList;
    }

    @Override
    public Integer[] getPaddings() {
        String[] paddingStringArray = new String[5];

        paddingStringArray[0] = driver.findElement(DOWNLOAD_APP_TITLE).getCssValue("padding-top");
        paddingStringArray[1] = driver.findElement(DOWNLOAD_APP_TITLE).getCssValue("padding-bottom");
        paddingStringArray[2] = driver.findElement(DOWNLOAD_APP_TEXT).getCssValue("padding-bottom");
        paddingStringArray[3] = driver.findElements(DOWNLOAD_APP_IMAGES).getFirst().getCssValue("margin-right");
        paddingStringArray[4] = driver.findElements(DOWNLOAD_APP_IMAGES).get(1).getCssValue("margin-right");

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{DOWNLOAD_APP_IMAGES, DOWNLOAD_APP_TITLE, DOWNLOAD_APP_TEXT};
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
