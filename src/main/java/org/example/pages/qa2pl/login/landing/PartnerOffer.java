package org.example.pages.qa2pl.login.landing;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.IntStream;

public class PartnerOffer extends AbstractGetPaddingSupporter implements SaveImage {


    private WebDriver driver;

    public PartnerOffer(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";
    private String imageUrl;

    private final By PARTNER_OFFER_CONTAINER = By.id("offerWideContainer");

    public By getPARTNER_OFFER_DIV(){
        return this.PARTNER_OFFER_CONTAINER;
    }

    public String getUrl(){
        return  this.url;
    }


    @Override
    public String imageUrl() {
        return null;
    }

    @Override
    public boolean saveImageToFile() {
        return false;
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();
        List<WebElement> elementsList = driver.findElement(PARTNER_OFFER_CONTAINER).findElements(By.xpath(".//div"));
        WebElement[] elementsArray = IntStream.range(0, elementsList.size())
                .filter(i -> i == 3 || i == 4 || i == 28 || i == 29)
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
        List<WebElement> elementsList = driver.findElement(PARTNER_OFFER_CONTAINER).findElements(By.xpath(".//div"));
        WebElement[] elementsArray = IntStream.range(0, elementsList.size())
                .filter(i -> i == 0 || i == 1 || i == 8 || i == 11 || i == 13 || i == 14 || i == 18 || i == 19 || i == 35)
                .mapToObj(elementsList::get)
                .toArray(WebElement[]::new);

        List<WebElement> filteredElementsList = new ArrayList<>(Arrays.stream(elementsArray).toList());

        accumulatePaddingsMarginsOfByList(filteredElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> elementsList = driver.findElement(PARTNER_OFFER_CONTAINER).findElements(By.xpath(".//div"));
        WebElement[] elementsArray = IntStream.range(0, elementsList.size())
                .filter(i -> i == 0 || i == 1 || i == 12 || i == 14 || i == 19 || i == 27 || i == 28 || i == 29)
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
}
