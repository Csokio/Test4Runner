package org.example.pages.qa2pl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Header extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public Header(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/";
    private String imageUrl;


    public String getUrl(){
        return  this.url;
    }

    private final By HEADER_DIV = By.xpath("//div[@class=\"header-wrapper width100\"]");
    private final By HEADER_OPTIONS = By.xpath("//div[@class=\"header-wrapper width100\"]//li");

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        WebElement[] headerElementsArray = driver.findElements(HEADER_OPTIONS)
                .stream()
                .toArray(WebElement[]::new);

        Arrays.stream(headerElementsArray).forEach(w -> stringBuilder.append(w.findElement(By.xpath(".//img"))
                .getAttribute("src")).append(","));
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"shell_logo_qa2.png", "station_locator_qa2.png", "faqs_qa2.png", "profile_icon_qa2.png"};

        IntStream.range(0,2).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(imageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_qa2_pl");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();
        WebElement[] headerElementsArray = driver.findElements(HEADER_OPTIONS)
                .stream()
                .toArray(WebElement[]::new);

        List<WebElement> headerElementsList = Arrays.stream(headerElementsArray).toList().subList(1, headerElementsArray.length);

        IntStream.range(0, headerElementsList.size()).forEach(i -> {
            if (i == 0 || i == 1) {
                WebElement label = headerElementsList.get(i).findElement(By.xpath(".//label[@class=\"menuButtonLabel\"]"));
                String fontFamily = label.getCssValue("font-family");
                String fontSize = label.getCssValue("font-size");
                String color = label.getCssValue("color");
                resultList.add(new String[]{fontFamily, fontSize, color});
            } else {
                List<WebElement> labelList = headerElementsList.get(i).findElements(By.xpath(".//a"));
                labelList.forEach(l ->  {
                            String fontFamily = l.getCssValue("font-family");
                            String fontSize = l.getCssValue("font-size");
                            String color = l.getCssValue("color");
                            resultList.add(new String[]{fontFamily, fontSize, color});
                        });
            }
        });
        return resultList;
    }

    @Override
    public Integer[] getPaddings() {

        WebElement[] headerElementsArray = driver.findElements(HEADER_OPTIONS)
                .stream()
                .toArray(WebElement[]::new);

        List<WebElement> headerElementsList = Arrays.stream(headerElementsArray).toList().subList(1, headerElementsArray.length);
        //WebElement label;

        IntStream.range(0, headerElementsList.size()).forEach(i -> {
            WebElement element;
            if (i == 0 || i == 1) {
                element = headerElementsList.get(i).findElement(By.xpath(".//label[@class=\"menuButtonLabel\"]"));
                accumulatePaddingsMarginsOfBy(element);
            } else {
                element = headerElementsList.get(i).findElement(By.xpath(".//div[@class=\"logoutCellLabel\"]/img"));
                accumulatePaddingsMarginsOfBy(element);
                element = headerElementsList.get(i).findElement(By.xpath(".//div[@class=\"logoutCellLabel\"]"));
                accumulatePaddingsMarginsOfBy(element);
            }
        });

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        List<WebElement> xpathElementsList = new ArrayList<>();

        System.out.println(driver.findElements(HEADER_OPTIONS).size());

        WebElement[] headerElementsArray = driver.findElements(HEADER_OPTIONS).subList(0,4)
                .stream()
                .toArray(WebElement[]::new);

        Arrays.stream(headerElementsArray).forEach(w -> xpathElementsList.add(w.findElement(By.xpath(".//a"))));
        //xpathElementsList.addAll(driver.findElement(HEADER_OPTIONS).findElements(By.xpath(".//a")));
        /*xpathElementsList.get(5).click();

        headerElementsArray = driver.findElements(HEADER_OPTIONS).subList(6, driver.findElements(HEADER_OPTIONS).size())
                .stream()
                .toArray(WebElement[]::new);
        Arrays.stream(headerElementsArray).forEach(w -> xpathElementsList.add(w.findElement(By.xpath(".//a"))));*/


        Arrays.stream(headerElementsArray).forEach(w -> xpathElementsList.add(w.findElement(By.xpath(".//img"))));
        //xpathElementsList.addAll(driver.findElement(HEADER_OPTIONS).findElements(By.xpath(".//img")));

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
