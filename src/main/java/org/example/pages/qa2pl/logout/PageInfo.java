package org.example.pages.qa2pl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.Serializable;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PageInfo extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public PageInfo(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/";
    private String imageUrl;
    private WebElement[] scrollElementsArray;

    private final By PAGE_INFO_DIV = By.id("homePageInfoContainer");
    private final By PAGE_INFO_TITLE = By.id("homePageInfoTitle");
    private final By CAROUSEL_IMAGE = By.id("homePageInfoAllItemContainer");

    private final By PAGE_INFO_LINK_1 = By.id("homePageInfoItemLink1");
    private final By PAGE_INFO_LINK_2 = By.id("homePageInfoItemLink2");
    private final By PAGE_INFO_LINK_3 = By.id("homePageInfoItemLink3");


    public String getUrl(){
        return  this.url;
    }


    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        WebElement[] elementsArray = driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//img"))
                .stream()
                .toArray(WebElement[]::new);

        WebElement[] cleanedElementsArray = new WebElement[]{elementsArray[0], elementsArray[2], elementsArray[4]};
        Arrays.stream(cleanedElementsArray).forEach(w -> stringBuilder.append(w.getAttribute("src")).append(","));

        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"page_info_image1.png", "page_info_image2.png", "page_info_image3.png"};

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
        By[] byArray = {PAGE_INFO_TITLE, PAGE_INFO_TITLE, CAROUSEL_IMAGE};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(1);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if(by == PAGE_INFO_TITLE){
                            if(titleRowCounter.get() == 1){
                                WebElement webElement = driver.findElement(by).findElement(By.xpath(".//small/small"));
                                String fontFamily = webElement.getCssValue("font-family");
                                System.out.println("The font family is: " + fontFamily);
                                String fontSize = webElement.getCssValue("font-size");
                                System.out.println("The font size is: " + fontSize);
                                String color = webElement.getCssValue("color");
                                System.out.println("The font color is: " + color);

                                titleRowCounter.getAndSet(titleRowCounter.get() + 1);
                                return Stream.of(new String[]{fontFamily, fontSize, color});
                            } else {
                                WebElement webElement = driver.findElement(by).findElements
                                        (By.xpath(".//small/small/following::br/following-sibling::br")).getFirst();
                                String fontFamily = webElement.getCssValue("font-family");
                                System.out.println("The font family is: " + fontFamily);
                                String fontSize = webElement.getCssValue("font-size");
                                System.out.println("The font size is: " + fontSize);
                                String color = webElement.getCssValue("color");
                                System.out.println("The font color is: " + color);

                                return Stream.of(new String[]{fontFamily, fontSize, color});
                            }

                        } else if (by == CAROUSEL_IMAGE){
                            List<WebElement> webElementList = driver.findElement(by).findElements
                                    (By.xpath(".//div"));

                            System.out.println("The size of the carouselImage list is: " + webElementList.size());

                            webElementList.remove(18);
                            webElementList.remove(16);
                            webElementList.remove(15);
                            webElementList.remove(14);
                            webElementList.remove(11);
                            webElementList.remove(9);
                            webElementList.remove(8);
                            webElementList.remove(7);
                            webElementList.remove(4);
                            webElementList.remove(2);
                            webElementList.remove(1);
                            webElementList.remove(0);

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });
                        } else {
                            return Collections.singletonList(new String[]{"N/A", "N/A", "N/A"}).stream();
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
        WebElement[] elementsArray = driver.findElement(PAGE_INFO_DIV).findElements(By.xpath(".//div"))
                .stream()
                .toArray(WebElement[]::new);

        List<WebElement> elementsList = new ArrayList<>(Arrays.stream(elementsArray).toList());

        accumulatePaddingsMarginsOfBy(driver.findElement(PAGE_INFO_DIV));
        accumulatePaddingsMarginsOfByList(elementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> xpathElementsList = new ArrayList<>();

        scrollElementsArray = driver.findElement(PAGE_INFO_DIV).findElements(By.xpath(".//div")).toArray(WebElement[]::new);
        xpathElementsList.addAll(Arrays.asList(scrollElementsArray));
        System.out.println("The size of the xpatewlementList is:" + xpathElementsList.size());

        xpathElementsList.remove(24);
        xpathElementsList.remove(21);
        xpathElementsList.remove(19);
        xpathElementsList.remove(18);
        xpathElementsList.remove(14);
        xpathElementsList.remove(12);
        xpathElementsList.remove(11);
        xpathElementsList.remove(7);
        xpathElementsList.remove(5);
        xpathElementsList.remove(4);
        xpathElementsList.remove(7);

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

    public Set<String> getOpenedTabsLinks() throws InterruptedException {
        Set<String> openedTabsLinks = new HashSet<>();

       /* List<WebElement> clickableElements = driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//a"));

        System.out.println("clickableElements size: " + clickableElements.size());
        clickableElements.forEach(el -> System.out.println(el.getAccessibleName()));

        List<WebElement> everySecondElementList = IntStream.range(0, clickableElements.size())
                .filter(i -> (i+1) % 2 == 0)
                .mapToObj(clickableElements::get)
                .collect(Collectors.toList());

        System.out.println("clickableElements size: " + everySecondElementList.size());
        everySecondElementList.forEach(el -> System.out.println(el.getAccessibleName()));

        System.out.println(everySecondElementList.size());
        System.out.println(everySecondElementList.get(1).getAccessibleName());*/

        driver.findElement(PAGE_INFO_LINK_1).click();
        openedTabsLinks.add(driver.getCurrentUrl());
        driver.navigate().back();

        driver.findElement(PAGE_INFO_LINK_2).click();
        openedTabsLinks.add(driver.getCurrentUrl());
        driver.navigate().back();

        driver.findElement(PAGE_INFO_LINK_3).click();
        openedTabsLinks.add(driver.getCurrentUrl());

        return openedTabsLinks;
    }
}
