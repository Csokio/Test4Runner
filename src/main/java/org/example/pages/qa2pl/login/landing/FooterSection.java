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

public class FooterSection extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public FooterSection(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";
    private String imageUrl;

    private final By FOOTER_CONTAINER = By.id("footer_wrapper");
    private final By FOOTER_SUB_OPTIONS = By.xpath("//div[@id=\"footer_wrapper\"]//div/ul/li");
    private final By FOOTER_SOCIAL = By.id("footerSocialIconContainer");
    private final By FOOTER_COOKIE_PREF = By.id("footer_cookie_preferences_container");
    private final By FOOTER_DIV = By.id("footer");

    public By getFOOTER_DIV(){
        return this.FOOTER_DIV;
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

    //TODO
    @Override
    public List<String[]> valuesList() {
        By[] byArray = {FOOTER_CONTAINER, FOOTER_SUB_OPTIONS, FOOTER_SOCIAL, FOOTER_COOKIE_PREF};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(0);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if (by == FOOTER_CONTAINER) {

                            List<WebElement> cleanedWebElementList;
                            List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//div"));

                            cleanedWebElementList = IntStream.range(0, webElementList.size())
                                    .filter(i -> i == 2 || i == 5 || i == 8 || i == 11)
                                    .mapToObj(webElementList::get).toList();

                            titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                            return cleanedWebElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });

                        } else if (by == FOOTER_SUB_OPTIONS) {

                            List<WebElement> webElementList = driver.findElements(by);

                            titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });

                        } else if (by == FOOTER_SOCIAL) {

                            WebElement webElement = driver.findElement(by).findElement(By.xpath(".//a"));

                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                            return Stream.of(new String[]{fontFamily, fontSize, color});


                        } else if (by == FOOTER_COOKIE_PREF) {

                            WebElement webElement = driver.findElement(by).findElement(By.xpath(".//a"));

                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            titleRowCounter.getAndSet(titleRowCounter.get() + 1);

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
        accumulatePaddingsMarginsOfBy(driver.findElement(FOOTER_CONTAINER));
        List<WebElement> elementsList = driver.findElement(FOOTER_CONTAINER).findElements(By.xpath(".//div"));
        elementsList = IntStream.range(0, elementsList.size())
                .filter(i -> i == 2 || i == 5 || i == 8 || i == 11 || i == 13 || i == 14)
                .mapToObj(elementsList::get).toList();
        accumulatePaddingsMarginsOfByList(elementsList);
        elementsList = driver.findElements(FOOTER_SUB_OPTIONS);
        accumulatePaddingsMarginsOfByList(elementsList);
        accumulatePaddingsMarginsOfBy(driver.findElement(FOOTER_SOCIAL));
        accumulatePaddingsMarginsOfBy(driver.findElement(FOOTER_COOKIE_PREF));

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {

        List<WebElement> webElementList = driver.findElement(FOOTER_CONTAINER).findElements(By.xpath(".//div"));

        List<WebElement> cleanedWebElementList;
        cleanedWebElementList = IntStream.range(0, webElementList.size())
                .filter(i -> i == 2 || i == 5 || i == 8 || i == 11 || i == 13)
                .mapToObj(webElementList::get).toList();

        WebElement[] elementsArray = cleanedWebElementList.toArray(WebElement[]::new);

        Arrays.stream(elementsArray).forEach(e -> {
            locationsList.add(e.getLocation());
        });

        cleanedWebElementList = driver.findElements(FOOTER_SUB_OPTIONS);
        elementsArray = cleanedWebElementList.toArray(WebElement[]::new);

        Arrays.stream(elementsArray).forEach(e -> {
            locationsList.add(e.getLocation());
        });

        locationsList.add(driver.findElement(FOOTER_SOCIAL).getLocation());
        locationsList.add(driver.findElement(FOOTER_COOKIE_PREF).getLocation());
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
