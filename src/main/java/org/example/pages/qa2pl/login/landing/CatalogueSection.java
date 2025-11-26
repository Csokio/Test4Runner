package org.example.pages.qa2pl.login.landing;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CatalogueSection extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public CatalogueSection(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";
    private String imageUrl;

    private final By CATALOGUE_CONTAINER = By.id("loggedout_catalogue_placeholder_container");
    private final By CATALOGUE_CARDS = By.xpath("(//div[@class=\"rewardItemWrapper\"][1])[%d]");
    private final By CATALOGUE_DIV = By.id("rewardItemsNew");

    public By getCATALOGUE_DIV(){
        return this.CATALOGUE_DIV;
    }

    public String getUrl(){
        return  this.url;
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        List<WebElement> headerElementsList = driver.findElement(CATALOGUE_CONTAINER).findElements(By.xpath(".//img"));
        WebElement[] headerElementsArray = IntStream.range(0, headerElementsList.size())
                .filter(i -> i == headerElementsList.size()-2 || i == headerElementsList.size()-3)
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
        String[] imageNames = {"qa2_catalogue_delivery.png", "qa2_catalogue_basket.png"};

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

        By[] byArray = {CATALOGUE_CONTAINER, CATALOGUE_CONTAINER, CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS,
                CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(0);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if(by == CATALOGUE_CONTAINER){
                            if(titleRowCounter.get() == 0){
                                WebElement webElement = driver.findElement(by).findElements(By.xpath(".//div")).get(5);
                                String fontFamily = webElement.getCssValue("font-family");
                                System.out.println("The font family is: " + fontFamily);
                                String fontSize = webElement.getCssValue("font-size");
                                System.out.println("The font size is: " + fontSize);
                                String color = webElement.getCssValue("color");
                                System.out.println("The font color is: " + color);

                                titleRowCounter.getAndSet(titleRowCounter.get() + 1);
                                return Stream.of(new String[]{fontFamily, fontSize, color});
                            } else {
                                List<WebElement> webElementList = driver.findElement(by).findElements
                                        (By.xpath(".//div//td")).subList(0, 2);

                                return webElementList.stream().map(actualWebElement -> {
                                    String fontFamily = actualWebElement.getCssValue("font-family");
                                    String fontSize = actualWebElement.getCssValue("font-size");
                                    String color = actualWebElement.getCssValue("color");
                                    return new String[]{fontFamily, fontSize, color};
                                });
                            }

                        } else if (by == CATALOGUE_CARDS){
                            List<WebElement> cleanedWebElementList;
                            List<WebElement> webElementList = driver.findElements(By.xpath(String.format("(//div[@class=\"rewardItemWrapper\"][1])[%d]//div",
                                    titleRowCounter.getAndSet(titleRowCounter.get()+1))));

                            System.out.println("THE SIZE OF THE WEBELEMENT LIST IN CATALOGUE CARDS IS: " + webElementList.size());

                            if(webElementList.size() > 11){
                                cleanedWebElementList = IntStream.range(0, webElementList.size())
                                        .filter(i -> i == 3 || i == 5 || i == 7 || i == 8 || i == 15)
                                        .mapToObj(webElementList::get).toList();
                            } else {
                                cleanedWebElementList = IntStream.range(0, webElementList.size())
                                        .filter(i -> i == 3 || i == 5 || i == 7 || i == 8)
                                        .mapToObj(webElementList::get).toList();
                            }

                            return cleanedWebElementList.stream().map(actualWebElement -> {
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
        List<WebElement> elementsList = driver.findElement(CATALOGUE_CONTAINER).findElements(By.xpath(".//div"));
        WebElement[] elementsArray = elementsList
                .toArray(WebElement[]::new);

        List<WebElement> filteredElementsList = new ArrayList<>(Arrays.stream(elementsArray).toList());

        accumulatePaddingsMarginsOfByList(filteredElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }
    //04578
    @Override
    public void setLocationsList() {

        locationsList.add(driver.findElement(CATALOGUE_CONTAINER).findElements(By.xpath(".//div")).get(5).getLocation());
        locationsList.add(driver.findElement(CATALOGUE_CONTAINER).findElements(By.xpath(".//div//td")).getFirst().getLocation());
        locationsList.add(driver.findElement(CATALOGUE_CONTAINER).findElements(By.xpath(".//div//td")).get(1).getLocation());

        IntStream.range(1, 8).forEach(i -> {
            List<WebElement> webElementList = driver.findElements(By.xpath(String.format("(//div[@class=\"rewardItemWrapper\"][1])[%d]//div", i)));

            List<WebElement> cleanedWebElementList;

            if(webElementList.size() > 11){
                cleanedWebElementList = IntStream.range(0, webElementList.size())
                        .filter(j -> j == 2 || j == 3 || j == 4 || j == 5 || j == 7 || j == 8 || j == 12 || j == 15)
                        .mapToObj(webElementList::get).toList();
            } else {
                cleanedWebElementList = IntStream.range(0, webElementList.size())
                        .filter(j -> j == 2 || j == 3 || j == 4 || j == 5 || j == 7 || j == 8)
                        .mapToObj(webElementList::get).toList();
            }

            WebElement[] elementsArray = cleanedWebElementList.toArray(WebElement[]::new);

            Arrays.stream(elementsArray).forEach(e -> {
                locationsList.add(e.getLocation());
            });
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
