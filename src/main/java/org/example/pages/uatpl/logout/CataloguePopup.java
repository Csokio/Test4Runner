package org.example.pages.uatpl.logout;

import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CataloguePopup extends Catalogue {

    private final By CATALOGUE_POPUP_X_BUTTON = By.xpath("//div[@class=\"close ng-star-inserted\"]");
    private final By CATALOGUE_POPUP_INNER_PADDINGS = By.xpath("//div[@class=\"catalogue-popup\"]");
    private final By CATALOGUE_POPUP_PRICE = By.xpath("//div[@class=\"catalogue-popup-price\"]");
    private final By CATALOGUE_POPUP_TITLE = By.xpath("//div[@class=\"catalogue-popup-title\"]");
    private final By CATALOGUE_POPUP_DESC = By.xpath("//div[@class=\"catalogue-popup-description\"]");
    private final By CATALOGUE_POPUP_BUTTONS = By.xpath("//div[@class=\"catalogue-popup-button-container ng-star-inserted\"]");
    private By CATALOGUE_POPUP_IMAGE;

    private List<WebElement> cataloguePopupsList;
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    private String imageUrl;
    protected CataloguePopup(WebDriver driver) {
        super(driver);
    }

    @Override
    public String imageUrl(){
        System.out.println("imageurl method is invoked");
        StringBuilder stringBuilder = new StringBuilder();
        cataloguePopupsList = driver.findElements(CATALOGUE_CARDS);
        System.out.println(cataloguePopupsList.size());
        cataloguePopupsList.forEach(System.out::println);

        AtomicInteger iterationCounter = new AtomicInteger(0);
        cataloguePopupsList.forEach(c -> {
            wait.until(ExpectedConditions.elementToBeClickable(c));
            c.click();
            switch (iterationCounter.get()) {
                case 1:
                case 3:
                    CATALOGUE_POPUP_IMAGE = By.xpath("//img[@class='catalogue-popup-img']");
                    break;
                case 0:
                case 2:
                    CATALOGUE_POPUP_IMAGE = By.xpath("//img[@class='catalogue-popup-img darken']");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid iterationCounter: " + iterationCounter);
            }

            stringBuilder.append(driver.findElement(CATALOGUE_POPUP_IMAGE).getAttribute("src")).append(",");

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(CATALOGUE_POPUP_X_BUTTON));
            //driver.findElement(CATALOGUE_POPUP_X_BUTTON).click();
            iterationCounter.getAndIncrement();
        });

        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());

        imageUrl = stringBuilder.toString();
        System.out.println(imageUrl);
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile(){
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"shell_catalogue_popup_image1.png", "shell_catalogue_popup_image2.png", "shell_catalogue_popup_image3.png", "shell_catalogue_popup_image4.png"};

        IntStream.range(0,4).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(imageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {
        cataloguePopupsList = driver.findElements(CATALOGUE_CARDS);

        /*AtomicInteger iterationCounter = new AtomicInteger(0);
        cataloguePopupsList.forEach(c -> {
            wait.until(ExpectedConditions.elementToBeClickable(c));
            c.click();


            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(CATALOGUE_POPUP_X_BUTTON));
            //driver.findElement(CATALOGUE_POPUP_X_BUTTON).click();
            iterationCounter.getAndIncrement();
        });*/

        By[] byArray = {CATALOGUE_POPUP_PRICE, CATALOGUE_POPUP_TITLE, CATALOGUE_POPUP_DESC, CATALOGUE_POPUP_BUTTONS};
        /*return Arrays.stream(byArray)
                .flatMap(by -> {
                    cataloguePopupsList.forEach(c -> {
                        wait.until(ExpectedConditions.elementToBeClickable(c));
                        c.click();

                    try {
                        if (by == CATALOGUE_POPUP_BUTTONS) {
                            List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath("./button"));

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });

                        } else {
                            WebElement webElement;
                            webElement = driver.findElement(by);

                            String fontFamily = webElement.getCssValue("font-family");
                            String fontSize = webElement.getCssValue("font-size");
                            String color = webElement.getCssValue("color");

                            return Collections.singletonList(new String[]{fontFamily, fontSize, color}).stream();
                        }
                    } catch (NoSuchElementException e) {
                        System.out.println("Element not found: " + by.toString());

                        return Collections.singletonList(new String[]{"N/A", "N/A", "N/A"}).stream();
                    }
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(CATALOGUE_POPUP_X_BUTTON));
                        //driver.findElement(CATALOGUE_POPUP_X_BUTTON).click();
                        iterationCounter.getAndIncrement();
                    });
                })
                .collect(Collectors.toList());


        //return resultList;*/

        List<Serializable> resultList = cataloguePopupsList.stream()
                .flatMap(c -> {
                    wait.until(ExpectedConditions.elementToBeClickable(c));
                    c.click();

                    System.out.println("Catalogue popup is invoked.");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Stream<Serializable> propertiesStream = Arrays.stream(byArray).flatMap(by -> {
                        try {
                            if (by == CATALOGUE_POPUP_BUTTONS) {
                                System.out.println("The by element is equal to catalogue popupbtn!!!!!!!!!!!");
                                List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath("./button"));

                                return webElementList.stream().map(actualWebElement -> {
                                    String fontFamily = actualWebElement.getCssValue("font-family");
                                    String fontSize = actualWebElement.getCssValue("font-size");
                                    String color = actualWebElement.getCssValue("color");
                                    return new String[]{fontFamily, fontSize, color};
                                });

                            } else {
                                WebElement webElement = driver.findElement(by);
                                String fontFamily = webElement.getCssValue("font-family");
                                String fontSize = webElement.getCssValue("font-size");
                                String color = webElement.getCssValue("color");
                                return Stream.of(new String[]{fontFamily, fontSize, color});
                            }
                        } catch (NoSuchElementException e) {
                            System.out.println("Element not found: " + by.toString());
                            return Stream.of(new String[]{"N/A", "N/A", "N/A"});
                        }
                    });


                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(CATALOGUE_POPUP_X_BUTTON));
                    /*try {
                        Robot robot = new Robot();
                        robot.mouseMove(40, 200);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    } catch (AWTException e) {
                        throw new RuntimeException(e);
                    }*/


                    return propertiesStream;
                })
                .collect(Collectors.toList());

        List<String[]> stringArrayList = new ArrayList<>();


        //TODO add every 3 part of resultList to an array and that that array to stringArrayList
        /*for (Serializable item : resultList) {
            if (item instanceof String[]) {
                stringArrayList.add((String[]) item);
            } else {
                System.out.println("Item is not a String[]: " + item);
            }
        }*/

        int indexCounter = 0;
        String[] temporaryStringArray = new String[3];

        for (Serializable item : resultList) {
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
        cataloguePopupsList = driver.findElements(CATALOGUE_CARDS);

        By[] xpathElements = new By[]{
                CATALOGUE_POPUP_PRICE,
                CATALOGUE_POPUP_TITLE,
                CATALOGUE_POPUP_DESC,
                CATALOGUE_POPUP_BUTTONS
        };

        cataloguePopupsList.forEach(c -> {
            wait.until(ExpectedConditions.elementToBeClickable(c));
            c.click();
            System.out.println("Catalogue popup is invoked.");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Arrays.stream(xpathElements).forEach(by -> {
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(by));
                    WebElement element = driver.findElement(by);
                    accumulatePaddingsMarginsOfBy(element);
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element encountered, re-locating element: " + by.toString());
                    WebElement element = driver.findElement(by);
                    accumulatePaddingsMarginsOfBy(element);
                } catch (NoSuchElementException e) {
                    System.out.println("Element not found: " + by.toString());
                }
            });

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(CATALOGUE_POPUP_X_BUTTON));
        });

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }



    /*@Override
    public Integer[] getPaddings() {
        //String[] paddingStringArray = new String[24];

        cataloguePopupsList = driver.findElements(CATALOGUE_CARDS);

        By[] xpathElements = new By[]{
                CATALOGUE_POPUP_PRICE,
                CATALOGUE_POPUP_DESC,
                CATALOGUE_POPUP_BUTTONS
        };

        List<Serializable> resultList = cataloguePopupsList.stream()
                .flatMap(c -> {
                    wait.until(ExpectedConditions.elementToBeClickable(c));
                    c.click();

                    System.out.println("Catalogue popup is invoked.");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Stream<Serializable> propertiesStream = Arrays.stream(xpathElements).flatMap(by -> {
                        try {
                            wait.until(ExpectedConditions.presenceOfElementLocated(by));

                            WebElement element = driver.findElement(by);
                            return accumulateAndReturnCssProperties(element);

                        } catch (StaleElementReferenceException e) {
                            System.out.println("Stale element encountered, re-locating element: " + by.toString());
                            WebElement element = driver.findElement(by);
                            return accumulateAndReturnCssProperties(element);
                        } catch (NoSuchElementException e) {
                            System.out.println("Element not found: " + by.toString());
                            return Stream.of("N/A");
                        }
                    });

                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(CATALOGUE_POPUP_X_BUTTON));

                    return propertiesStream;
                })
                .collect(Collectors.toList());

        List<String[]> stringArrayList = new ArrayList<>();

        for (Serializable item : resultList) {
            if (item instanceof String[]) {
                stringArrayList.add((String[]) item);
            } else {
                System.out.println("Item is not a String[]: " + item);
            }
        }

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();

    }*/
}
