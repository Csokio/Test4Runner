package org.example.pages.qa2pl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.*;

import java.io.Serializable;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PromoAndScroll extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public PromoAndScroll(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/";
    private String imageUrl;


    public String getUrl(){
        return  this.url;
    }

    private final By HEADER = By.xpath("//div[@id=\"page-wrapper-id\"]");
    private final By SCROLL_CONTAINER = By.xpath("//div[@class=\"width100 clearfix future-scroll fixed\"]");

    private final By PROMO_DIV = By.id("content_u23");
    private final By SCROLL_DIV = By.id("homePageScrollContainer");
    private final By PROMO_IMAGE = By.xpath("//div[@style=\"background-image: url('/pl-pl/" +
            "cdn/b/deriv/f4/4c280e23a9f8119c658993ec8cf36c/1280x373.jpg?food image for LOD PL.jpg\t');\"]");
    private final By PROMO_TITLE = By.id("message_u24");
    private final By PROGRESS_BAR = By.xpath("//progress");
    private final By SCROLL_BENEFITS = By.id("homePageScrollBenefits");
    private final By SCROLL_INFO = By.id("homePageScrollInfo");
    private final By SCROLL_DOWNLOAD = By.id("homePageScrollDownloadApp");


    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(driver.findElement(PROMO_IMAGE).getAttribute("style"));
        int startSplit = stringBuilder.indexOf("/pl");
        int endSplit = stringBuilder.indexOf("1280x373.jpg");
        String temporaryImageUrl = stringBuilder.substring(startSplit, endSplit+12).toString();

        imageUrl = temporaryImageUrl.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);

        String[] imageNames = {"dynamic_background_qa2.png"};

        String baseUrl = "https://lodqa2.wonderline.eu/";
        IntStream.range(0,1).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(baseUrl + imageUrl, "C:\\Users\\SőregiKrisztián\\Pictures\\shell_qa2_pl");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {
        By[] byArray = {PROMO_TITLE, SCROLL_DIV};
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if(by == PROMO_TITLE){
                            WebElement webElement = driver.findElement(by).findElement(By.xpath(".//a[1]"));
                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});
                        } else {
                            List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//td"));

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                String fontSize = (String) js.executeScript(
                                        "return window.getComputedStyle(arguments[0], null).getPropertyValue('font-size');",
                                        actualWebElement);

                                //String fontSize = actualWebElement.getCssValue("font-size");
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
        WebElement[] scrollElementsArray = driver.findElements(SCROLL_DIV)
                .stream()
                .toArray(WebElement[]::new);

        List<WebElement> scrollElementsList = new ArrayList<>(Arrays.stream(scrollElementsArray).toList());

        List<WebElement> scrollElementsTempList = new ArrayList<>();
        scrollElementsTempList.add(driver.findElement(PROMO_DIV));
        scrollElementsTempList.add(driver.findElement(PROMO_TITLE));

        scrollElementsList.addAll(scrollElementsTempList);

        /*scrollElementsList.addFirst(driver.findElement(PROMO_DIV));
        scrollElementsList.addFirst(driver.findElement(PROMO_TITLE));*/

        IntStream.range(0, scrollElementsList.size()).forEach(i -> {

                accumulatePaddingsMarginsOfBy(scrollElementsList.get(i));

        });

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> xpathElementsList = new ArrayList<>();

        WebElement[] scrollElementsArray = driver.findElement(SCROLL_DIV).findElements(By.xpath(".//td"))
                .stream()
                .toArray(WebElement[]::new);

        System.out.println("The size of the scrollelementarray is: " + scrollElementsArray.length);

        xpathElementsList.add(driver.findElement(PROMO_DIV));
        xpathElementsList.add(driver.findElement(PROMO_TITLE));

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


    public boolean scrollPromoBar() throws InterruptedException {
        AtomicBoolean allSuccess = new AtomicBoolean(true);

        allSuccess.set(scrolling("0", "540", "rgba(0, 0, 0, 0)", SCROLL_BENEFITS));
        allSuccess.set(scrolling("0", "150", "rgba(247, 209, 23, 1)", SCROLL_BENEFITS));

        allSuccess.set(scrolling("0", "1360", "rgba(0, 0, 0, 0)", SCROLL_INFO));
        allSuccess.set(scrolling("0", "60", "rgba(247, 209, 23, 1)", SCROLL_INFO));

        allSuccess.set(scrolling("0", "900", "rgba(0, 0, 0, 0)", SCROLL_DOWNLOAD));
        allSuccess.set(scrolling("0", "160", "rgba(247, 209, 23, 1)", SCROLL_DOWNLOAD));

        return allSuccess.get();
    }

    private boolean scrolling(String scrollWidth, String scrollHeight, String expectedCssValue, By element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript("window.scrollBy(" + scrollWidth +"," + scrollHeight+ ")", "");
        String cssValue = driver.findElement(element).getCssValue("background-color");
        System.out.println("There should be " + expectedCssValue + ": "+ cssValue);
        Thread.sleep(1000);
        return cssValue.equals(expectedCssValue);
    }

}
