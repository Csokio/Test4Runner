package org.example.pages.qa2pl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Footer extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public Footer(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/";
    private String imageUrl;
    private WebElement[] scrollElementsArray;

    private final By FOOTER_DIV = By.id("footer");
    private final By FOOTER_TITLES = By.xpath("//div[@class=\"futura-14 footer-group-title desktop-only\"]");
    private final By FOOTER_OPTIONS = By.xpath("//div[@id=\"footer\"]//li");
    private final By FOOTER_TEXTS = By.xpath("//div[@id=\"footer\"]//a");

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
        By[] byArray = {FOOTER_TITLES, FOOTER_TEXTS};
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
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
                });

        List<String[]> stringArrayList = new ArrayList<>();

        int indexCounter = 0;
        String[] temporaryStringArray = new String[3];

        for (Serializable item : propertiesStream.toList()) {
            if(indexCounter == 3){
                temporaryStringArray = new String[3];
                indexCounter = 0;
            }
            if (item instanceof String) {
                temporaryStringArray[indexCounter++] = (String) item;
                //stringArrayList.add(temporaryStringArray);
            } else if (item instanceof  String[]) {
                stringArrayList.add((String[]) item);
            } else {
                System.out.println("Item is not a String[] || String: " + item);
                System.out.println("The class of the item is: " + item.getClass());
            }
            if(indexCounter == 2){
                stringArrayList.add(temporaryStringArray);
            }
        }

        return stringArrayList;
    }

    @Override
    public Integer[] getPaddings() {
        WebElement[] elementsArray = driver.findElements(FOOTER_TITLES)
                .stream()
                .toArray(WebElement[]::new);

        List<WebElement> elementsList = new ArrayList<>(Arrays.stream(elementsArray).toList());

        accumulatePaddingsMarginsOfBy(driver.findElement(FOOTER_DIV));
        accumulatePaddingsMarginsOfBy(driver.findElement(FOOTER_DIV).findElements(By.xpath("./div")).getFirst());
        accumulatePaddingsMarginsOfByList(driver.findElement(FOOTER_DIV).findElements(By.xpath(".//li")));

        accumulatePaddingsMarginsOfByList(elementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> xpathElementsList = new ArrayList<>();

        xpathElementsList.add(driver.findElement(FOOTER_DIV));

        WebElement[] scrollElementsArray = driver.findElements(FOOTER_TITLES)
                .stream()
                .toArray(WebElement[]::new);

        System.out.println("The size of the scrollelementarray is: " + scrollElementsArray.length);

        IntStream.range(0, driver.findElements(FOOTER_TEXTS).size()).forEach(i -> {
                    xpathElementsList.add(driver.findElements(FOOTER_TEXTS).get(i));
                }
        );

        Arrays.stream(scrollElementsArray).forEach(w -> xpathElementsList.add(w));

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

    public Set<String> getOpenedTabsLinks() {
        Set<String> openedTabsLinks = new HashSet<>();

        List<WebElement> clickableElements = driver.findElements(FOOTER_TEXTS);

        for (WebElement element : clickableElements) {
            String originalTab = driver.getWindowHandle();

            // Open the link in a new tab
            Actions action = new Actions(driver);
            action.keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL).perform();

            // Switch to the new tab
            for (String tabHandle : driver.getWindowHandles()) {
                if (!tabHandle.equals(originalTab)) {
                    driver.switchTo().window(tabHandle);
                    openedTabsLinks.add(driver.getCurrentUrl()); // Collect the URL of the opened tab
                    System.out.println("Current url is: " + driver.getCurrentUrl());
                    driver.close(); // Close the newly opened tab after collecting the URL
                    driver.switchTo().window(originalTab); // Switch back to the original tab
                }
            }
        }

        System.out.println(openedTabsLinks.size());
        return openedTabsLinks;
    }

}
