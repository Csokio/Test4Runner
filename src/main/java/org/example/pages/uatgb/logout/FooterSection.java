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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class FooterSection extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;
    private String imageUrl;
    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";



    private final By FOOTER_DOWNLOAD_APP_IMAGES = By.xpath("//div[@class=\"fpFooterAppImageContainer left-text\"]/a/img");

    private final By FOOTER_COLUMNS = By.xpath("//div[@id=\"footerFirstRow\"]/div[@id=\"footerGroup\" or @id=\"footerGroup_u150\" or @id=\"footerGroup_u162\" or @id=\"footerGroup_u172\"]");

    private final By FOOTER_SUBTITLES = By.xpath("//div[@id=\"footerFirstRow\"]/div[@id=\"footerGroup\" or @id=\"footerGroup_u150\" or @id=\"footerGroup_u162\" or @id=\"footerGroup_u172\"]/ul/li");

    private final By FOOTER_TITLES = By.xpath("//div[@id=\"footerFirstRow\"]/div[@id=\"footerGroup\" or @id=\"footerGroup_u150\" or @id=\"footerGroup_u162\" or @id=\"footerGroup_u172\"]/div[1]");

    private final By FOOTER_WHOLE = By.id("footer");

    private final By FOOTER_CONTENT_WRAPPER = By.xpath("//div[@id=\"footer\"]/div[1]");

    public FooterSection(WebDriver driver){
        this.driver = driver;
    }

    public By getFOOTER_WHOLE(){
        return this.FOOTER_WHOLE;
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
        IntStream.range(0, driver.findElements(FOOTER_DOWNLOAD_APP_IMAGES).size()).forEach(
                i -> strBuilder.append(driver.findElements(FOOTER_DOWNLOAD_APP_IMAGES).get(i).getAttribute("src")).append(",")
        );

        System.out.println(strBuilder.toString());
        strBuilder.setLength(strBuilder.length()-1);
        imageUrl = strBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl";
        System.out.println(imageUrl.split(",")[0]);
        List<ReadImageUrl> images = Arrays.asList(
                new ReadImageUrl(imageUrl.split(",")[0], basePath),
                new ReadImageUrl(imageUrl.split(",")[1], basePath)
        );

        List<String> fileNames = Arrays.asList(
                "apple_footer_image.png",
                "android_footer_image.png"
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

        AtomicReference<WebElement> webElement = new AtomicReference<>(null);
        By[] byArray = {FOOTER_TITLES, FOOTER_SUBTITLES};
        for(int i = 0; i < byArray.length; i++){
            if(byArray[i] == FOOTER_TITLES){
                IntStream.range(0, driver.findElements(FOOTER_TITLES).size())
                        .forEach(j -> {
                            if(driver.findElements(FOOTER_TITLES).get(j) == driver.findElements(FOOTER_TITLES).getLast()){
                                webElement.set(driver.findElements(FOOTER_TITLES).get(j).findElement(By.xpath("following::div[@id=\"fpFooterGroupTitle\"]")));
                            } else {
                                webElement.set(driver.findElements(FOOTER_TITLES).get(j));
                            }

                            String fontFamily = webElement.get().getCssValue("font-family");
                            String fontSize = webElement.get().getCssValue("font-size");
                            String color = webElement.get().getCssValue("color");
                            resultList.add(new String[]{fontFamily, fontSize, color});
                        });
            } else {
                IntStream.range(0, driver.findElements(FOOTER_SUBTITLES).size()-1)
                        .forEach(j -> {
                            webElement.set(driver.findElements(FOOTER_SUBTITLES).get(j));

                            String fontFamily = webElement.get().getCssValue("font-family");
                            String fontSize = webElement.get().getCssValue("font-size");
                            String color = webElement.get().getCssValue("color");
                            resultList.add(new String[]{fontFamily, fontSize, color});
                        });
            }
        }
        for(String[] array: resultList){
            System.out.println(Arrays.toString(array));
        }
        return resultList;
    }

    @Override
    public Integer[] getPaddings() {
        String[] paddingStringArray = new String[12];

        paddingStringArray[0] = driver.findElement(FOOTER_WHOLE).getCssValue("margin-top");
        paddingStringArray[1] = driver.findElement(FOOTER_CONTENT_WRAPPER).getCssValue("padding-top");
        paddingStringArray[2] = driver.findElement(FOOTER_CONTENT_WRAPPER).getCssValue("margin-right");
        paddingStringArray[3] = driver.findElement(FOOTER_CONTENT_WRAPPER).getCssValue("margin-left");


        IntStream.range(0, driver.findElements(FOOTER_SUBTITLES).size()-1)
                .forEach(i -> {
                    int index = 4;
                    paddingStringArray[index+i] = driver.findElements(FOOTER_SUBTITLES).get(i).getCssValue("padding-top");
                });

        Arrays.stream(paddingStringArray).forEach(System.out::println);

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{FOOTER_DOWNLOAD_APP_IMAGES, FOOTER_TITLES, FOOTER_SUBTITLES};
        Arrays.stream(xpathElements).forEach(e -> {
            if(e == FOOTER_TITLES){
                IntStream.range(0, driver.findElements(FOOTER_TITLES).size())
                        .forEach(i -> {
                            if(i == driver.findElements(FOOTER_TITLES).size()-1){
                                locationsList.add(driver.findElements(FOOTER_TITLES).get(i).findElement(By.xpath("following::div[@id=\"fpFooterGroupTitle\"]")).getLocation());
                            } else {
                                locationsList.add(driver.findElements(e).get(i).getLocation());
                            }
                });
            } else if (e == FOOTER_SUBTITLES) {
                IntStream.range(0, driver.findElements(FOOTER_SUBTITLES).size()-1)
                        .forEach(i -> {
                            locationsList.add(driver.findElements(FOOTER_SUBTITLES).get(i).getLocation());
                        });
            } else {
                locationsList.add(driver.findElements(e).get(0).getLocation());
                locationsList.add(driver.findElements(e).get(1).getLocation());
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
