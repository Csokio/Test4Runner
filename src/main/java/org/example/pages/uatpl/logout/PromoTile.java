package org.example.pages.uatpl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PromoTile extends AbstractLocationManager implements SaveImage {

    private final WebDriver driver;

    public PromoTile(WebDriver driver) {
        this.driver = driver;
    }

    private final String url = "https://shell247b-pl.lod.club/";

    public String getUrl(){
        return this.url;
    }


    private final By PROMO_TILE_DIV = By.xpath("//div[@class=\"promoTileWrapper\"]");

    private final By PROMO_TILE_IMAGES = By.xpath("//app-promo-tile[@style=\"--desktop-img-url:" +
            " url(https://shell247b-pl.lod.club/cdn/b/orig/6d/fab897e1c68efdec0355f5cc37aa99/1176x372.jpg);" +
            " --mobile-img-url:" +
            " url(https://shell247b-pl.lod.club/cdn/b/orig/97/af8b25c1bd4c85d7165174b5b6f07c/1088x602.png);\"]");
    private final By PROMO_TILE_INFO = By.xpath("//div[@class=\"promoTileInfo desktopOnly\"]");
    private final By MESSAGE_TITLE = By.xpath("(//div[@class=\"promoTileInfoTitle\"])[1]");
    private final By MESSAGE_TEXT = By.xpath("(//div[@class=\"promoTileInfoContent\"])[1]");
    private final By BUTTON_LOGIN = By.xpath("(//div[@class=\"promoTileInfoLink\"])[1]");
    private final By BUTTON_REGISTER = By.xpath("(//div[@class=\"promoTileInfoButton\"])[1]");
    private String imageUrl;

    public String getImageUrl(){
        return this.imageUrl;
    }
    public By getPROMO_TILE_DIV(){
        return this.PROMO_TILE_DIV;
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder;

        stringBuilder = new StringBuilder(driver.findElement(PROMO_TILE_IMAGES).getAttribute("style"));

        stringBuilder.delete(0, stringBuilder.indexOf("(")+1);
        stringBuilder.delete(stringBuilder.indexOf(")"), stringBuilder.indexOf("(")+1);
        stringBuilder.insert(stringBuilder.indexOf("jpg")+3, ",");
        stringBuilder.delete(stringBuilder.indexOf(")"), stringBuilder.length());

        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl";
        List<ReadImageUrl> images = Arrays.asList(
                new ReadImageUrl(imageUrl.split(",")[0], basePath),
                new ReadImageUrl(imageUrl.split(",")[1], basePath)
        );

        List<String> filenames = Arrays.asList(
                "promotiledesktop.png",
                "promotilemobile.png"
        );

        AtomicBoolean allSuccess = new AtomicBoolean(true);

        IntStream.range(0, images.size()).forEach(i -> {
            if(!images.get(i).writeImage(filenames.get(i))){
                allSuccess.set(false);
            }
        });

        return allSuccess.get();
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();

        WebElement webElement;
        By[] byArray = {MESSAGE_TITLE, MESSAGE_TEXT, BUTTON_LOGIN, BUTTON_REGISTER};
        for(int i = 0; i < byArray.length; i++){
            if(byArray[i] == BUTTON_LOGIN){
                webElement = driver.findElement(byArray[i]).findElement(By.xpath("./a"));
                String fontFamily = webElement.getCssValue("font-family");
                String fontSize = webElement.getCssValue("font-size");
                String color = webElement.getCssValue("color");
                resultList.add(new String[]{fontFamily, fontSize, color});
            } else if (byArray[i] == BUTTON_REGISTER) {
                webElement = driver.findElement(byArray[i]).findElement(By.xpath(".//span[2]"));
                String fontFamily = webElement.getCssValue("font-family");
                String fontSize = webElement.getCssValue("font-size");
                String color = webElement.getCssValue("color");
                resultList.add(new String[]{fontFamily, fontSize, color});
            } else {
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
        AtomicInteger counter = new AtomicInteger(0);

        By[] xpathElements = new By[]{PROMO_TILE_DIV, PROMO_TILE_INFO, MESSAGE_TITLE, MESSAGE_TEXT, BUTTON_LOGIN, BUTTON_REGISTER};
        String[] ccsPaddings = {"padding-top", "padding-right", "padding-bottom", "padding-left"};
        Queue<By> promoElements = new LinkedList<>();
        Arrays.stream(xpathElements).forEach(e -> promoElements.offer(e));
        while(promoElements.peek() != null ){
            By actualElement = promoElements.poll();
            if(actualElement == PROMO_TILE_DIV || actualElement == PROMO_TILE_INFO){
                IntStream.range(0, ccsPaddings.length).forEach( i ->
                    paddingStringArray[counter.getAndIncrement()] = driver.findElement(actualElement).getCssValue(ccsPaddings[i])
                );
            } else if (actualElement == MESSAGE_TITLE) {
                paddingStringArray[counter.getAndIncrement()] = driver.findElement(actualElement).getCssValue("margin-bottom");
            }  else  {
                paddingStringArray[counter.getAndIncrement()] = driver.findElement(actualElement).getCssValue("margin-top");
            }
        }

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{PROMO_TILE_DIV, PROMO_TILE_IMAGES, PROMO_TILE_INFO, MESSAGE_TITLE,
                MESSAGE_TEXT, BUTTON_LOGIN, BUTTON_REGISTER};
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

}
