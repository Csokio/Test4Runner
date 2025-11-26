package org.example.pages.qa2pl.login.landing;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Header extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public Header(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";
    private String imageUrl;


    public String getUrl(){
        return  this.url;
    }

    private final By HEADER_DIV = By.id("desktopSubMenu");
    private final By HEADER_OPTIONS = By.xpath("//div[@id=\"desktopSubMenu\"]//li");

    public By getHEADER_DIV(){
        return this.HEADER_DIV;
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        List<WebElement> headerElementsList = driver.findElement(HEADER_DIV).findElements(By.xpath(".//img"));
        WebElement[] headerElementsArray = IntStream.range(0, headerElementsList.size())
                        .filter(i -> i != 1 && i != 5 && i != 8)
                                .mapToObj(headerElementsList::get)
                                        .toArray(WebElement[]::new);

        System.out.println("The size of the headerelementsarray is: " + headerElementsArray.length);

        Arrays.stream(headerElementsArray).forEach(w -> stringBuilder.append(w.getAttribute("src")).append(","));
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"qa2_header_landing1.png", "qa2_header_landing2.png", "qa2_header_landing3.png", "qa2_header_landing4.png",
                "qa2_header_landing5.png", "qa2_header_landing6.png", "qa2_header_landing7.png",
                "qa2_header_landing8.png", "qa2_header_landing9.png", "qa2_header_landing10.png"};

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
        List<WebElement> headerElementsList = driver.findElement(HEADER_DIV).findElements(By.xpath(".//label"));
        WebElement[] headerElementsArray = IntStream.range(0, headerElementsList.size())
                .mapToObj(headerElementsList::get)
                .toArray(WebElement[]::new);

        IntStream.range(0, headerElementsArray.length).forEach(i -> {

                WebElement label = headerElementsArray[i];
                String fontFamily = label.getCssValue("font-family");
                String fontSize = label.getCssValue("font-size");
                String color = label.getCssValue("color");
                resultList.add(new String[]{fontFamily, fontSize, color});

            });

        return resultList;
    }

    //There's no significant padding on elements.
    @Override
    public Integer[] getPaddings() {
        accumulatePaddingsMarginsOfByList(driver.findElement(HEADER_DIV).findElements(By.xpath(".//label")));

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> xpathElementsList = new ArrayList<>();

        WebElement[] headerElementsArray = driver.findElements(HEADER_OPTIONS).subList(0,6)
                .stream()
                .toArray(WebElement[]::new);

        Arrays.stream(headerElementsArray).forEach(w -> xpathElementsList.add(w.findElement(By.xpath(".//a"))));
        //xpathElementsList.addAll(driver.findElement(HEADER_OPTIONS).findElements(By.xpath(".//a")));

        Arrays.stream(headerElementsArray).forEach(w -> xpathElementsList.add(w.findElement(By.xpath(".//img"))));
        //xpathElementsList.addAll(driver.findElement(HEADER_OPTIONS).findElements(By.xpath(".//img")));

        System.out.println("The size of the xpathElementsList is: " + xpathElementsList.size());

        driver.findElements(HEADER_OPTIONS).get(5).click();

        headerElementsArray = driver.findElements(HEADER_OPTIONS).subList(6, driver.findElements(HEADER_OPTIONS).size())
                .stream()
                .toArray(WebElement[]::new);
        Arrays.stream(headerElementsArray).forEach(w -> xpathElementsList.add(w.findElement(By.xpath(".//a"))));
        Arrays.stream(headerElementsArray).forEach(w -> xpathElementsList.add(w.findElement(By.xpath(".//img"))));

        System.out.println("The size of the xpathElementsList is: " + xpathElementsList.size());

        WebElement[] xpathElementsArray = xpathElementsList.toArray(new WebElement[0]);
        Arrays.stream(xpathElementsArray).forEach(e -> {
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

    public LinkedHashMap<Integer, LinkedHashMap<String, String[]>> reworkedValuesList(){
        LinkedHashMap<Integer, LinkedHashMap<String, String[]>> resultMap = new LinkedHashMap<>();

        List<String[]> valuesList = new ArrayList<>();
        List<WebElement> headerElementsList = driver.findElement(HEADER_DIV).findElements(By.xpath(".//label"));
        WebElement[] headerElementsArray = IntStream.range(0, headerElementsList.size())
                .mapToObj(headerElementsList::get)
                .toArray(WebElement[]::new);

        IntStream.range(0, headerElementsArray.length).forEach(i -> {
            LinkedHashMap<String, String[]> innerMap = new LinkedHashMap<>();

            WebElement label = headerElementsArray[i];
            String fontFamily = label.getCssValue("font-family");
            String fontSize = label.getCssValue("font-size");
            String color = label.getCssValue("color");
            valuesList.add(new String[]{fontFamily, fontSize, color});
            System.out.println(label.toString());
            innerMap.put(getElementXPath2(label, driver), new String[]{fontFamily, fontSize, color});

            resultMap.put(i, innerMap);
        });

        return resultMap;
    }

    public String[][] reworkedGetPaddings(){
        accumulateTwoDimensionalPaddingsMarginsOfByList(getElementXPath2List(driver.findElement(HEADER_DIV).findElements(By.xpath(".//label")), driver), driver.findElement(HEADER_DIV).findElements(By.xpath(".//label")));

        return resizeArrayAndRemoveNullsTwoDimensional(getTwoDimensionalPaddingStringArray());
    }


}
