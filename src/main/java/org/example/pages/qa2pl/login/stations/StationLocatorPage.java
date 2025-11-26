package org.example.pages.qa2pl.login.stations;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.Serializable;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class StationLocatorPage extends AbstractGetPaddingSupporter implements SaveImage {


    private WebDriver driver;

    public StationLocatorPage(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";

    private final By STATION_LOCATOR_MENU_OPTION = By.id("new_station_button");
    private final By STATION_LOCATOR_MAIN_TITLE = By.id("back_button_text_content"); // ---> .//h1 to get text
    private final By STATION_LOCATOR_SUBTITLE = By.id("stationContainer"); // ---> ./div/div
    private final By STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL = By.id("app_carousel_container_u9");   // ---> .//td  || .//tbody
    private final By MAP_IFRAME_CONTAINER = By.id("stationLocatorIframeContainer");
    private final By PADDING_ABOVE_FOOTER_LINE = By.id("content_u8");
    private final By DOWNLOAD_APP_POPUP = By.id("app_dialog_container");   // ---> .//h3 popup-title  ||  .//img  application's icons
    private final By DOWNLOAD_POPUP_DIALOG_CLOSE_BUTTON = By.id("app_dialog_close");
    private final By STATION_LOCATOR_DIV = By.id("simple_content");

    public By getSTATION_LOCATOR_DIV(){
        return this.STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL;
    }
    public By getDOWNLOAD_APP_POPUP(){
        return  this.DOWNLOAD_APP_POPUP;
    }

    public String getUrl(){
        return  this.url;
    }

    public String goToStationLocatorPage(){
        driver.findElement(STATION_LOCATOR_MENU_OPTION).click();
        return driver.getCurrentUrl();
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
        By[] byArray = {STATION_LOCATOR_MAIN_TITLE, STATION_LOCATOR_SUBTITLE, STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL, STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(0);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        WebElement webElement = null;
                        //List<WebElement> webElementList = null;
                        if (by == STATION_LOCATOR_MAIN_TITLE) {

                            webElement = driver.findElement(by).findElement(By.xpath(".//h1"));

                        } else if (by == STATION_LOCATOR_SUBTITLE) {

                            webElement = driver.findElement(by).findElement(By.xpath("./div/div"));

                        } else if (by == STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL) {

                            if(titleRowCounter.get() < 1){
                                titleRowCounter.getAndSet(1);
                                webElement = driver.findElement(by).findElements(By.xpath(".//td")).getFirst();
                            } else {
                                webElement = driver.findElement(by).findElements(By.xpath(".//td")).getLast();
                            }

                        } else {
                            return Stream.of(new String[]{"N/A", "N/A", "N/A"});
                        }

                        String fontFamily = webElement.getCssValue("font-family");
                        System.out.println("The font family is: " + fontFamily);
                        String fontSize = webElement.getCssValue("font-size");
                        System.out.println("The font size is: " + fontSize);
                        String color = webElement.getCssValue("color");
                        System.out.println("The font color is: " + color);
                        return Stream.of(new String[]{fontFamily, fontSize, color});


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
        List<WebElement> accumulatedElementsList = new LinkedList<>();
        accumulatedElementsList.add(driver.findElement(STATION_LOCATOR_MAIN_TITLE));
        accumulatedElementsList.add(driver.findElement(STATION_LOCATOR_SUBTITLE));
        accumulatedElementsList.add(driver.findElement(STATION_LOCATOR_SUBTITLE).findElement(By.xpath("./div/div")));
        accumulatedElementsList.add(driver.findElement(MAP_IFRAME_CONTAINER));
        accumulatedElementsList.add(driver.findElement(STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL));
        accumulatedElementsList.add(driver.findElement(PADDING_ABOVE_FOOTER_LINE));

        accumulatePaddingsMarginsOfByList(accumulatedElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] byArray = {STATION_LOCATOR_MENU_OPTION, STATION_LOCATOR_MAIN_TITLE, STATION_LOCATOR_SUBTITLE, MAP_IFRAME_CONTAINER, STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL};

        Arrays.stream(byArray).forEach(by -> {
            locationsList.add(driver.findElement(by).getLocation());
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

    public Map<Boolean, Map<Boolean, List<String>>> downloadAppInteraction() throws InterruptedException {
        boolean isLarger = false;
        boolean isPopUpAppIconsLarger = false;
        Map<Boolean, List<String>> popUpDetails = new HashMap<>();
        Map<Boolean, Map<Boolean, List<String>>> finalMap = new HashMap<>();

        List<String> popUpValues = new ArrayList<>();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Map<String, Double> standardDimensions = (Map<String, Double>) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect(); return {width: rect.width, height: rect.height};",
                driver.findElement(STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL).findElement(By.xpath(".//table"))
        );

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL)).perform();
        Thread.sleep(1000);

        Map<String, Double> extendedDimensions = (Map<String, Double>) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect(); return {width: rect.width, height: rect.height};",
                driver.findElement(STATION_LOCATOR_DOWNLOAD_APP_CAROUSEL).findElement(By.xpath(".//table"))
        );

        System.out.println("Original width: " + standardDimensions.get("width"));
        System.out.println("Hovered width: " + extendedDimensions.get("width"));


        if (standardDimensions != null && extendedDimensions != null) {
            isLarger = standardDimensions.get("width") < extendedDimensions.get("width") && standardDimensions.get("height") < extendedDimensions.get("height");

            System.out.println("First dimension's width is: " + standardDimensions.get("width"));
            System.out.println("Second dimension's width is: " + extendedDimensions.get("width"));

            System.out.println("Is the second dimension larger? " + isLarger);
        } else {
            System.out.println("One or both of the Dimension objects are null.");
        }

        actions.click().perform();

        popUpValues.add(driver.findElement(DOWNLOAD_APP_POPUP).findElement(By.xpath(".//h3")).getCssValue("font-family"));
        popUpValues.add(driver.findElement(DOWNLOAD_APP_POPUP).findElement(By.xpath(".//h3")).getCssValue("font-size"));
        popUpValues.add(driver.findElement(DOWNLOAD_APP_POPUP).findElement(By.xpath(".//h3")).getCssValue("color"));
        popUpValues.add(driver.findElement(DOWNLOAD_APP_POPUP).findElement(By.xpath(".//h3")).getCssValue("line-height"));

        Map<String, Double> standardAndroidDimensions = (Map<String, Double>) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect(); return {width: parseFloat(rect.width), height: parseFloat(rect.height)};",
                driver.findElement(DOWNLOAD_APP_POPUP).findElements(By.xpath(".//img")).getFirst()
        );

        Map<String, Double> standardIOSDimensions = (Map<String, Double>) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect(); return {width: parseFloat(rect.width), height: parseFloat(rect.height)};",
                driver.findElement(DOWNLOAD_APP_POPUP).findElements(By.xpath(".//img")).getLast()
        );

        actions.moveToElement(driver.findElement(DOWNLOAD_APP_POPUP).findElements(By.xpath(".//img")).getFirst()).perform();
        Map<String, Double> extendedAndroidDimensions = (Map<String, Double>) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect(); return {width: parseFloat(rect.width), height: parseFloat(rect.height)};",
                driver.findElement(DOWNLOAD_APP_POPUP).findElements(By.xpath(".//img")).getFirst()
        );
        actions.moveToElement(driver.findElement(DOWNLOAD_APP_POPUP).findElements(By.xpath(".//img")).getLast()).perform();
        Map<String, Double> extendedIOSDimensions = (Map<String, Double>) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect(); return {width: parseFloat(rect.width), height: parseFloat(rect.height)};",
                driver.findElement(DOWNLOAD_APP_POPUP).findElements(By.xpath(".//img")).getLast()
        );

        if (standardAndroidDimensions != null && extendedAndroidDimensions != null) {
            isPopUpAppIconsLarger = ((Number) standardAndroidDimensions.get("width")).doubleValue() < ((Number) extendedAndroidDimensions.get("width")).doubleValue()
                    && ((Number) standardAndroidDimensions.get("height")).doubleValue() < ((Number) extendedAndroidDimensions.get("height")).doubleValue();

            System.out.println("First dimension's width is: " + standardAndroidDimensions.get("width"));
            System.out.println("Second dimension's width is: " + extendedAndroidDimensions.get("width"));

            System.out.println("Is the second dimension larger? " + isPopUpAppIconsLarger);
        } else {
            System.out.println("One or both of the Dimension objects are null.");
        }

        if (standardIOSDimensions != null && extendedIOSDimensions != null) {
            isPopUpAppIconsLarger = ((Number) standardIOSDimensions.get("width")).doubleValue() < ((Number) extendedIOSDimensions.get("width")).doubleValue()
                    && ((Number) standardIOSDimensions.get("height")).doubleValue() < ((Number) extendedIOSDimensions.get("height")).doubleValue();

            System.out.println("First dimension's width is: " + standardIOSDimensions.get("width"));
            System.out.println("Second dimension's width is: " + extendedIOSDimensions.get("width"));

            System.out.println("Is the second dimension larger? " + isPopUpAppIconsLarger);
        } else {
            System.out.println("One or both of the Dimension objects are null.");
        }

        //driver.findElement(DOWNLOAD_POPUP_DIALOG_CLOSE_BUTTON).click();
        popUpDetails.put(isPopUpAppIconsLarger, popUpValues);

        if(!driver.findElement(DOWNLOAD_APP_POPUP).isDisplayed()){
            isLarger = false;
        }
        if(!isPopUpAppIconsLarger){
            isLarger = false;
        }
        finalMap.put(isLarger, popUpDetails);
        return finalMap;
    }



}
