package org.example.pages.qa2pl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ClubSmart extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public ClubSmart(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/";
    private String imageUrl;
    private WebElement[] scrollElementsArray;

    private final By BENEFITS_UPPER_CONTAINER = By.id("homePageBenefitsUpperContainer");
    private final By BENEFITS_TITLE = By.id("homePageBenefitsTitle");
    private final By CLUB_IMAGE = By.xpath("//div[@class=\"width100 clearfix spacing-60 mobile-spacing-30 center-align mobile-scroll-x\"]");
    private final By CAROUSEL_IMAGE = By.id("homePageBenefitsCarouselContainer");

    public String getUrl(){
        return  this.url;
    }


    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        WebElement[] upElementsArray = driver.findElement(CLUB_IMAGE).findElements(By.xpath(".//img"))
                .stream()
                .toArray(WebElement[]::new);

        WebElement[] downElementsArray = driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//img"))
                .stream()
                .toArray(WebElement[]::new);

        Arrays.stream(upElementsArray).forEach(w -> stringBuilder.append(w.getAttribute("src")).append(","));
        Arrays.stream(downElementsArray).forEach(w -> stringBuilder.append(w.getAttribute("src")).append(","));

        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"benefit_image1.png", "benefit_image2.png", "benefit_image3.png",
                "benefit_carousel_image.png", "benefit_carousel_icon1.png", "benefit_carousel_icon2.png",
                "benefit_carousel_icon3.png"};

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
        By[] byArray = {BENEFITS_TITLE, BENEFITS_TITLE, CAROUSEL_IMAGE, CLUB_IMAGE};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(1);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if(by == BENEFITS_TITLE){
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

                        } else if(by == CAROUSEL_IMAGE){
                            List<WebElement> webElementList = driver.findElement(by).findElements
                                    (By.xpath(".//div"));

                            webElementList.remove(11);
                            webElementList.remove(10);
                            webElementList.remove(8);
                            webElementList.remove(6);
                            webElementList.remove(4);
                            webElementList.remove(3);
                            webElementList.remove(0);

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });
                        } else {
                            List<WebElement> webElementList = driver.findElement(by).findElements
                                    (By.xpath(".//div"));

                            webElementList.remove(4);
                            webElementList.remove(2);
                            webElementList.remove(0);

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                /*JavascriptExecutor js = (JavascriptExecutor) driver;
                                String fontSize = (String) js.executeScript(
                                        "return window.getComputedStyle(arguments[0], null).getPropertyValue('font-size');",
                                        actualWebElement);*/
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });
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
        accumulatePaddingsMarginsOfBy(driver.findElement(BENEFITS_TITLE).findElement(By.xpath("./parent::div")));
        accumulatePaddingsMarginsOfBy(driver.findElement(BENEFITS_TITLE).findElement(By.xpath("./parent::div/parent::div")));
        accumulatePaddingsMarginsOfBy(driver.findElement(CLUB_IMAGE));
        accumulatePaddingsMarginsOfByList(driver.findElement(CLUB_IMAGE).findElements(By.xpath(".//div")));
        accumulatePaddingsMarginsOfBy(driver.findElement(CAROUSEL_IMAGE));
        accumulatePaddingsMarginsOfByList(driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//td")));
        accumulatePaddingsMarginsOfBy(driver.findElement(CAROUSEL_IMAGE).findElement(By.xpath(".//td/div")));
        accumulatePaddingsMarginsOfByList(driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//td/div/div")));
        accumulatePaddingsMarginsOfByList(driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//td/div/div/div")));

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> xpathElementsList = new ArrayList<>();

        scrollElementsArray = driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//td")).toArray(WebElement[]::new);
        xpathElementsList.addAll(Arrays.asList(scrollElementsArray));

        System.out.println("The size of the scrollelementarray is: " + scrollElementsArray.length);

        scrollElementsArray = driver.findElement(CAROUSEL_IMAGE).findElements(By.xpath(".//td/div/div"))
                .stream()
                .toArray(WebElement[]::new);
        Arrays.stream(scrollElementsArray).forEach(w -> xpathElementsList.add(w));

        xpathElementsList.add(driver.findElement(BENEFITS_TITLE));
        xpathElementsList.add(driver.findElement(CLUB_IMAGE));
        scrollElementsArray = driver.findElement(CLUB_IMAGE).findElements(By.xpath("./div"))
                .stream()
                .toArray(WebElement[]::new);
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


}
