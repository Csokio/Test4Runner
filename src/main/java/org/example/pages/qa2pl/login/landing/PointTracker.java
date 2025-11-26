package org.example.pages.qa2pl.login.landing;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class PointTracker extends AbstractGetPaddingSupporter implements SaveImage {


    private WebDriver driver;

    public PointTracker(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";
    private String imageUrl;

    private final By POINT_TRACKER_CONTAINER = By.id("pointTrackersContainer");

    public By getPOINT_TRACKER_DIV(){
        return this.POINT_TRACKER_CONTAINER;
    }

    public String getUrl(){
        return  this.url;
    }


    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        List<WebElement> headerElementsList = driver.findElement(POINT_TRACKER_CONTAINER).findElements(By.xpath(".//td"));
        WebElement[] headerElementsArray = IntStream.range(0, headerElementsList.size())
                .filter(i -> i == 2)
                .mapToObj(headerElementsList::get)
                .toArray(WebElement[]::new);

        System.out.println("The size of the headerelementsarray is: " + headerElementsArray.length);

        System.out.println(headerElementsArray[0].getAttribute("style"));

        stringBuilder.append(extractUrl(headerElementsArray[0].getAttribute("style"))).append(",");
        stringBuilder.append(driver.findElement(POINT_TRACKER_CONTAINER).findElements(By.xpath(".//td//img")).getFirst().getAttribute("src"));

        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"qa2_pointtocash_right.png", "qa2_pointtocash_left.png"};

        IntStream.range(0, imageUrls.length).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(imageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_qa2_pl\\login\\landing_page");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();
        List<WebElement> elementsList = driver.findElement(POINT_TRACKER_CONTAINER).findElements(By.xpath(".//div"));
        WebElement[] elementsArray = IntStream.range(0, elementsList.size())
                .filter(i -> i == 8 || i == 10 || i == 11 || i == 14 || i == 17 || i == 19)
                .mapToObj(elementsList::get)
                .toArray(WebElement[]::new);

        IntStream.range(0, elementsArray.length).forEach(i -> {

            WebElement label = elementsArray[i];
            String fontFamily = label.getCssValue("font-family");
            String fontSize = label.getCssValue("font-size");
            String color = label.getCssValue("color");
            resultList.add(new String[]{fontFamily, fontSize, color});

        });

        return resultList;
    }

    @Override
    public Integer[] getPaddings() {
        List<WebElement> elementsList = driver.findElement(POINT_TRACKER_CONTAINER).findElements(By.xpath(".//div"));
        WebElement[] elementsArray = IntStream.range(0, elementsList.size())
                //.filter(i -> i == 0 || i == 2 || i == 3 || i == 4 || i == 5 || i == 14 || i == 26 || i == 29)
                .filter(i -> i == 0 || i == 1 || i == 8 || i == 11 || i == 13 || i == 14 || i == 19 || i == 35)
                .mapToObj(elementsList::get)
                .toArray(WebElement[]::new);

        List<WebElement> filteredElementsList = new ArrayList<>(Arrays.stream(elementsArray).toList());

        accumulatePaddingsMarginsOfByList(filteredElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> elementsList = driver.findElement(POINT_TRACKER_CONTAINER).findElements(By.xpath(".//div"));
        WebElement[] elementsArray = IntStream.range(0, elementsList.size())
                .filter(i -> i == 8 || i == 10 || i == 11 || i ==  12 || i == 14 || i == 15 || i == 17 || i == 19)
                .mapToObj(elementsList::get)
                .toArray(WebElement[]::new);

        Arrays.stream(elementsArray).forEach(e -> {
            locationsList.add(e.getLocation());
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

    private String extractUrl(String text) {
        String regex = "url\\(['\"]?(.*?)['\"]?\\)";

        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

}
