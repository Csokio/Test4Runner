package org.example.pages.qa2pl.login.landing;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TransactionSection extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public TransactionSection(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";
    private String imageUrl;

    private final By TRANSACTION_CONTAINER = By.id("toggleContainer");
    private final By TRANSACTION_TITLE = By.id("toggle_table_title");

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
        By[] byArray = {TRANSACTION_CONTAINER, TRANSACTION_CONTAINER, TRANSACTION_CONTAINER, TRANSACTION_CONTAINER, TRANSACTION_TITLE};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(0);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if (by == TRANSACTION_CONTAINER) {
                            if (titleRowCounter.get() == 0) {

                                List<WebElement> cleanedWebElementList;
                                List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//table//div"));

                                cleanedWebElementList = IntStream.range(0, webElementList.size())
                                        .filter(i -> i == 13 || i == 15 || i == 18 || i == 21)
                                        .mapToObj(webElementList::get).toList();

                                titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                                return cleanedWebElementList.stream().map(actualWebElement -> {
                                    String fontFamily = actualWebElement.getCssValue("font-family");
                                    String fontSize = actualWebElement.getCssValue("font-size");
                                    String color = actualWebElement.getCssValue("color");
                                    return new String[]{fontFamily, fontSize, color};
                                });

                            } else if (titleRowCounter.get() == 1) {

                                List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//table//td")).subList(2, 7);
                                System.out.println("THE SIZE OF THE WEBELEMENT LIST IN CATALOGUE CARDS IS: " + webElementList.size());

                                titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                                return webElementList.stream().map(actualWebElement -> {
                                    String fontFamily = actualWebElement.getCssValue("font-family");
                                    String fontSize = actualWebElement.getCssValue("font-size");
                                    String color = actualWebElement.getCssValue("color");
                                    return new String[]{fontFamily, fontSize, color};
                                });

                            } else if (titleRowCounter.get() == 2) {

                                WebElement webElement = driver.findElement(by).findElement(By.xpath(".//a"));

                                System.out.println("WEBELEMENT 'A' IS FOUND");

                                String fontFamily = webElement.getCssValue("font-family");
                                System.out.println("The font family is: " + fontFamily);
                                String fontSize = webElement.getCssValue("font-size");
                                System.out.println("The font size is: " + fontSize);
                                String color = webElement.getCssValue("color");
                                System.out.println("The font color is: " + color);

                                titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                                return Stream.of(new String[]{fontFamily, fontSize, color});

                            } else {

                                WebElement webElement = driver.findElement(by).findElements(By.xpath(".//table//td//span")).get(3);
                                String fontFamily = webElement.getCssValue("font-family");
                                System.out.println("The font family is: " + fontFamily);
                                String fontSize = webElement.getCssValue("font-size");
                                System.out.println("The font size is: " + fontSize);
                                String color = webElement.getCssValue("color");
                                System.out.println("The font color is: " + color);

                                titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                                return Stream.of(new String[]{fontFamily, fontSize, color});

                            }
                        } else if (by == TRANSACTION_TITLE) {

                            WebElement webElement = driver.findElement(by);
                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});

                        } else {
                            return Stream.of(new String[]{"N/A", "N/A", "N/A"});
                        }

                    } catch (NoSuchElementException e) {
                        System.out.println("Element not found: " + by.toString());
                        return Stream.of(new String[]{"N/A", "N/A", "N/A"});
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
        List<WebElement> elementsList = driver.findElement(TRANSACTION_CONTAINER).findElements(By.xpath(".//div"));
        accumulatePaddingsMarginsOfByList(elementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {

        locationsList.add(driver.findElement(TRANSACTION_TITLE).getLocation());

        List<WebElement> webElementList = driver.findElement(TRANSACTION_CONTAINER).findElements(By.xpath(".//table//div"));

        List<WebElement> cleanedWebElementList;
        cleanedWebElementList = IntStream.range(0, webElementList.size())
                .filter(i -> i == 13 || i == 15 || i == 18 || i == 21)
                .mapToObj(webElementList::get).toList();

        WebElement[] elementsArray = cleanedWebElementList.toArray(WebElement[]::new);

        Arrays.stream(elementsArray).forEach(e -> {
            locationsList.add(e.getLocation());
        });

        webElementList = driver.findElement(TRANSACTION_CONTAINER).findElements(By.xpath(".//table//td")).subList(2, 7);
        elementsArray = webElementList.toArray(WebElement[]::new);

        Arrays.stream(elementsArray).forEach(e -> {
            locationsList.add(e.getLocation());
        });

        locationsList.add(driver.findElement(TRANSACTION_CONTAINER).findElement(By.xpath(".//a")).getLocation());
        locationsList.add(driver.findElement(TRANSACTION_CONTAINER).findElements(By.xpath(".//table//td//span")).get(3).getLocation());
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
