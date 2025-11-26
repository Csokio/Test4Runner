package org.example.pages.uatpl.logout;

import org.bouncycastle.oer.its.ieee1609dot2.basetypes.SequenceOfUint8;
import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Catalogue extends AbstractGetPaddingSupporter implements SaveImage {


    protected final WebDriver driver;

    public Catalogue(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://shell247b-pl.lod.club/";


    private final By CATALOGUE_DIV = By.xpath("//div[@class=\"container container-vertical-mobile p0-p24\"]");
    private final By CATALOGUE_TITlE = By.xpath("//div[@class=\"catalogue-title\"]");
    protected final By CATALOGUE_CARDS = By.xpath("(//div[@class=\"items-container\"])[1]/app-general-card");
    private final By CATALOGUE_CARD_TITLES = By.xpath("//div[@class=\"custom-card-title bold\"]");
    private final By CATALOGUE_CARD_TEXTS = By.xpath("//div[@class=\"custom-card-title bold\"]/parent::*");
    private final By CATALOGUE_CARD_DESCRIPTIONS = By.xpath("//div[@class=\"custom-card-desc\"]");
    private final By CATALOGUE_IMAGES = By.xpath("(//div[@class=\"items-container\"])[1]//img");
    private final By CATALOGUE_BUTTON = By.xpath("(//div[@class=\"items-container\"])[1]/following::button");

    private String imageURL;

    //private String[] paddingStringArray = new String[160];
    private final String[] cssPaddings = {"padding-top", "padding-right", "padding-bottom", "padding-left"};
    private final String[] cssMargins = {"margin-top", "margin-right", "margin-bottom", "margin-left"};
    private AtomicInteger counter = new AtomicInteger(0);

    protected String getImageURL(){
        return this.imageURL;
    }
    public String getUrl(){
        return this.url;
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        String trimmedUrl = getUrl().substring(0, url.length()-1);
        IntStream.range(0, driver.findElements(CATALOGUE_IMAGES).size()).forEach(i -> {

                if(i == 1 || i == 2){
                    System.out.println("The trimmed url is: "+trimmedUrl);
                    stringBuilder.append(driver.findElements(CATALOGUE_IMAGES).get(i).getAttribute("src")).append(",");
                    System.out.println(stringBuilder.toString());

                }  else  {
                    stringBuilder.append(driver.findElements(CATALOGUE_IMAGES).get(i).getAttribute("src")).append(",");

                }

            });
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());

        imageURL = stringBuilder.toString();
        return imageURL;
    }

    @Override
    public boolean saveImageToFile() {
        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl";
        List<ReadImageUrl> images = Arrays.asList(
                new ReadImageUrl(imageURL.split(",")[0], basePath),
                new ReadImageUrl(imageURL.split(",")[1], basePath),
                new ReadImageUrl(imageURL.split(",")[2], basePath),
                new ReadImageUrl(imageURL.split(",")[3], basePath)
        );

        List<String> fileNames = Arrays.asList(
                "catalogue_image_jeden.png",
                "catalogue_image_dwa.png",
                "catalogue_image_trzy.png",
                "catalogue_image_cztery.png"
        );

        AtomicBoolean allSuccess = new AtomicBoolean(true);

        IntStream.range(0, images.size()).forEach(i -> {
            if(!images.get(i).writeImage(fileNames.get(i))){
                allSuccess.set(false);
            }
        });

        return allSuccess.get();
    }

    @Override
    public List<String[]> valuesList() {

        By[] byArray = {CATALOGUE_TITlE, CATALOGUE_CARD_TITLES, CATALOGUE_CARD_DESCRIPTIONS, CATALOGUE_BUTTON};
        return Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if (by == CATALOGUE_CARD_TITLES || by == CATALOGUE_CARD_DESCRIPTIONS) {
                            List<WebElement> webElementList = driver.findElements(by).subList(0, 4);

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });

                        } else {
                            WebElement webElement;

                            if (by == CATALOGUE_BUTTON) {
                                webElement = driver.findElement(by).findElement(By.xpath(".//span[2]"));
                            } else {
                                webElement = driver.findElement(by);
                            }

                            String fontFamily = webElement.getCssValue("font-family");
                            String fontSize = webElement.getCssValue("font-size");
                            String color = webElement.getCssValue("color");

                            return Collections.singletonList(new String[]{fontFamily, fontSize, color}).stream();
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Element not found: " + by.toString());

                        return Collections.singletonList(new String[]{"N/A", "N/A", "N/A"}).stream();
                    }
                })
                .collect(Collectors.toList());  // Collect the result into a List<String[]>
    }

    @Override
    public Integer[] getPaddings() {

        By[] xpathElements = new By[]{CATALOGUE_DIV, CATALOGUE_TITlE, CATALOGUE_CARDS, CATALOGUE_CARD_TEXTS, CATALOGUE_CARD_TITLES, CATALOGUE_CARD_DESCRIPTIONS, CATALOGUE_BUTTON};

        List<WebElement> catalogueCardsElement = driver.findElements(CATALOGUE_CARDS);
        List<WebElement> catalogueCardsTitlesElement = driver.findElements(CATALOGUE_CARD_TITLES);


        Queue<By> catalogueElements = new LinkedList<>();
        Arrays.stream(xpathElements).forEach(e -> catalogueElements.offer(e));
        while(catalogueElements.peek() != null ){
            List<WebElement> actualListElement;
            WebElement actualElement;
            if(driver.findElements(catalogueElements.peek()).size() > 1){
                By currentByElement = catalogueElements.poll();
                actualListElement = driver.findElements(currentByElement);
                actualListElement = resizeListToNumber(actualListElement, 4);

                System.out.println(currentByElement);

                if(actualListElement.getFirst().getAttribute("outerHTML").equals(catalogueCardsElement.getFirst().getAttribute("outerHTML")) || currentByElement == CATALOGUE_CARDS){
                    System.out.println("ACTUALELEMENT IS EQUALS TO CATALOGUE CARDS ITS THE 24PX");

                    accumulatePaddingsMarginsOfByList(actualListElement);

                } else if (actualListElement.getFirst().getAttribute("outerHTML").equals(catalogueCardsTitlesElement.getFirst().getAttribute("outerHTML"))) {
                    System.out.println("ACTUALELEMENT IS EQUALS TO CATALOGUE CARD TITLE AROUND 16PX PADDING");

                    accumulatePaddingsMarginsOfByListInnerXpath(actualListElement, "./parent::*");

                }   else {
                    System.out.println("NOT THE CARD INNER PADDING 16PX OR 24PX RIGHT MARGIN");
                    accumulatePaddingsMarginsOfByList(actualListElement);
                }

            } else {
                    actualElement = driver.findElement(catalogueElements.poll());
                    accumulatePaddingsMarginsOfBy(actualElement);
                }




        }

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = {CATALOGUE_DIV, CATALOGUE_TITlE, CATALOGUE_CARDS, CATALOGUE_CARD_TEXTS, CATALOGUE_CARD_TITLES, CATALOGUE_CARD_DESCRIPTIONS, CATALOGUE_BUTTON};
        Arrays.stream(xpathElements).forEach(e -> {
            if(driver.findElements(e).size() > 1){
                for(int i = 0; i < driver.findElements(e).size(); i++){
                    locationsList.add(driver.findElements(e).get(i).getLocation());
                }
            } else {
                locationsList.add(driver.findElement(e).getLocation());
            }
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

    /*protected void accumulatePaddingsMarginsOfBy(WebElement element){
        Arrays.stream(cssPaddings).forEach(p -> {
            System.out.println("The css padding is: " + p + "And the value of it is: " + element.getCssValue(p));
            if(element.getCssValue(p) != null){
                paddingStringArray[counter.getAndIncrement()] = element.getCssValue(p);
            }
        });
        Arrays.stream(cssMargins).forEach(m -> {
            System.out.println("The css margin is: " + m + "And the value of it is: " + element.getCssValue(m));
            if(element.getCssValue(m) != null){
                paddingStringArray[counter.getAndIncrement()] = element.getCssValue(m);
            }
        });
    }

    private void accumulatePaddingsMarginsOfByList(List<WebElement> element){
        Arrays.stream(cssPaddings).forEach(p -> {
            element.stream().filter(e -> e.getCssValue(p) != null)
                    .forEach(e -> {
                        paddingStringArray[counter.getAndIncrement()] = e.getCssValue(p);
                    });
        });
        Arrays.stream(cssMargins).forEach(m -> {
            element.stream().filter(e -> e.getCssValue(m) != null)
                    .forEach(e -> {
                        paddingStringArray[counter.getAndIncrement()] = e.getCssValue(m);
                    });
        });
    }

    private void accumulatePaddingsMarginsOfByListInnerXpath(List<WebElement> element, String xPath){
        Arrays.stream(cssPaddings).forEach(p -> {
            element.stream().forEach(e -> {
                WebElement newE = e.findElement(By.xpath(xPath));
                if(newE.getCssValue(p) != null){
                    paddingStringArray[counter.getAndIncrement()] = newE.getCssValue(p);
                }
            });
        });
        Arrays.stream(cssMargins).forEach(m -> {
            element.stream().forEach(e -> {
                WebElement newE = e.findElement(By.xpath(xPath));
                if(newE.getCssValue(m) != null){
                    paddingStringArray[counter.getAndIncrement()] = newE.getCssValue(m);
                }
            });
        });
    }
    private String[] resizeArrayAndRemoveNulls(String[] originalArray) {
        LinkedList<String> nonNullList = new LinkedList<>();

        for (String element : originalArray) {
            System.out.println(element);
            if (element != null) {
                if(!element.startsWith("0")){
                    nonNullList.add(element);

                }
            }
        }

        return nonNullList.toArray(new String[0]);
    }

    private List<WebElement> resizeListToNumber(List<WebElement> originalList, Integer number) {
        if (originalList.size() > number) {
            return new ArrayList<>(originalList.subList(0, number));
        }
        return new ArrayList<>(originalList);
    }*/

    public CataloguePopup goToCataloguePopup(){
        return new CataloguePopup(this.driver);
    }
}
