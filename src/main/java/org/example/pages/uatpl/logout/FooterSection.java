package org.example.pages.uatpl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.AbstractLocationManager;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FooterSection extends AbstractGetPaddingSupporter implements SaveImage {

    public FooterSection(WebDriver driver){
        this.driver = driver;
    }

    private WebDriver driver;
    private String imageUrl;
    private final String url = "https://shell247b-pl.lod.club/";

    private final By FOOTER_DOWNLOAD_APP_IMAGES = By.xpath("//figure/a/img");

    private final By FOOTER_COLUMNS = By.xpath("//div[@id=\"footerFirstRow\"]/div[@id=\"footerGroup\" or @id=\"footerGroup_u150\" or @id=\"footerGroup_u162\" or @id=\"footerGroup_u172\"]");

    private final By FOOTER_SUBTITLES = By.xpath("//div[@class=\"item ng-star-inserted\"]//a");

    private final By FOOTER_TITLES = By.xpath("//div[@class=\"footer-title heavy\"]");

    private final By FOOTER_WHOLE = By.id("footer");

    private final By FOOTER_CONTENT_WRAPPER = By.xpath("//div[@id=\"footer\"]/div[1]");

    public String getUrl(){
        return this.url;
    }


    @Override
    public String imageUrl() {
        StringBuilder strBuilder = new StringBuilder();
        IntStream.range(1, driver.findElements(FOOTER_DOWNLOAD_APP_IMAGES).size()+1).forEach(
                i -> {
                    By footerDownloadAppImage = By.xpath(String.format("(//figure/a/img)[%d]", i));
                    strBuilder.append(driver.findElement(footerDownloadAppImage).getAttribute("src")).append(",");
                }
        );

        System.out.println(strBuilder.toString());
        strBuilder.setLength(strBuilder.length()-1);
        imageUrl = strBuilder.toString();
        return imageUrl;
    }


    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"apple_footer_image.png", "android_footer_image.png"};

        IntStream.range(0,2).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(imageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }


    @Override
    public List<String[]> valuesList() {
        By[] byArray = {FOOTER_TITLES, FOOTER_SUBTITLES};
        return Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        List<WebElement> webElementList = driver.findElements(by);

                        return webElementList.stream().map(actualWebElement -> {
                            String fontFamily = actualWebElement.getCssValue("font-family");
                            String fontSize = actualWebElement.getCssValue("font-size");
                            String color = actualWebElement.getCssValue("color");
                            return new String[]{fontFamily, fontSize, color};
                        });


                    } catch (NoSuchElementException e) {
                        System.out.println("Element not found: " + by.toString());

                        return Collections.singletonList(new String[]{"N/A", "N/A", "N/A"}).stream();
                    }
                })
                .collect(Collectors.toList());  // Collect the result into a List<String[]>
    }


    @Override
    public Integer[] getPaddings() {

        return new Integer[0];
    }


    @Override
    public void setLocationsList() {

    }


    @Override
    public void createMapFromLocationsList() {

    }


    @Override
    public LinkedHashMap<Integer, Integer[]> absoluteLocations() {
        return null;
    }


    @Override
    public LinkedHashMap<Integer, Integer[]> relativeLocations() {
        return null;
    }


}
