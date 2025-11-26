package org.example.pages.qa2pl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DownloadApp extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public DownloadApp(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/";
    private String imageUrl;
    private WebElement[] scrollElementsArray;

    private final By DOWNLOAD_APP_DIV = By.id("homePageDownloadAppContainer");
    private final By DOWNLOAD_APP_BUTTON = By.id("homePageDownloadAppJoinButtonLink");
    private final By CAROUSEL_IMAGE = By.id("homePageInfoAllItemContainer");

    public String getUrl(){
        return  this.url;
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        WebElement[] elementsArray = driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//img"))
                .stream()
                .toArray(WebElement[]::new);

        Arrays.stream(elementsArray).forEach(w -> stringBuilder.append(w.getAttribute("src")).append(","));

        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"download_app_image1.png", "download_app_image2.png", "download_app_image3.png"};

        IntStream.range(0,imageNames.length).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(imageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_qa2_pl");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {
        By[] byArray = {DOWNLOAD_APP_DIV, DOWNLOAD_APP_DIV, DOWNLOAD_APP_BUTTON};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(1);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if(by == DOWNLOAD_APP_DIV){
                            if(titleRowCounter.get() == 1){
                                List<WebElement> webElementList = driver.findElement(by).findElements
                                        (By.xpath(".//div/font"));

                                titleRowCounter.getAndSet(titleRowCounter.get() + 1);

                                return webElementList.stream().map(actualWebElement -> {
                                    String fontFamily = actualWebElement.getCssValue("font-family");
                                    String fontSize = actualWebElement.getCssValue("font-size");
                                    String color = actualWebElement.getCssValue("color");
                                    return new String[]{fontFamily, fontSize, color};
                                });
                            } else {
                                WebElement webElement = driver.findElement(by).findElements
                                        (By.xpath(".//div")).get(14);
                                String fontFamily = webElement.getCssValue("font-family");
                                System.out.println("The font family is: " + fontFamily);
                                String fontSize = webElement.getCssValue("font-size");
                                System.out.println("The font size is: " + fontSize);
                                String color = webElement.getCssValue("color");
                                System.out.println("The font color is: " + color);

                                return Stream.of(new String[]{fontFamily, fontSize, color});
                            }

                        } else {

                            WebElement webElement = driver.findElement(by);
                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});
                        }

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
        WebElement[] elementsArray = driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//div"))
                .stream()
                .toArray(WebElement[]::new);

        List<WebElement> elementsList = new ArrayList<>(Arrays.stream(elementsArray).toList());

        accumulatePaddingsMarginsOfByList(elementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> xpathElementsList = new ArrayList<>();

        xpathElementsList.add(driver.findElement(DOWNLOAD_APP_DIV));

        WebElement[] scrollElementsArray = driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//div/font"))
                .stream()
                .toArray(WebElement[]::new);

        System.out.println("The size of the scrollelementarray is: " + scrollElementsArray.length);

        xpathElementsList.add(driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//div")).get(8));
        xpathElementsList.add(driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//div")).get(11));
        xpathElementsList.add(driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//div")).get(13));
        xpathElementsList.add(driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//div")).get(14));

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

    public Set<String> getOpenedAppLinks(){
        Set<String> openedTabsLinks = new HashSet<>();

        List<WebElement> clickableElements = driver.findElement(DOWNLOAD_APP_DIV).findElements(By.xpath(".//img"));

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
                    driver.close(); // Close the newly opened tab after collecting the URL
                    driver.switchTo().window(originalTab); // Switch back to the original tab
                }
            }
        }

        return openedTabsLinks;
    }

}

