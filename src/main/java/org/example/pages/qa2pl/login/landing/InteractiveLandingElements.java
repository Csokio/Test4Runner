package org.example.pages.qa2pl.login.landing;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InteractiveLandingElements extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public InteractiveLandingElements(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";
    private String imageUrl;
    private AtomicReference<Integer> titleRowCounter = new AtomicReference<>(0);


    private final By SHELL_LOGO = By.id("logo_cell_image");
    private final By STATION_LOGO = By.id("menu_label_stations");
    private final By GO_PLUS_LOGO = By.id("menu_label_go_plus");
    private final By BASKET_LOGO = By.id("menu_label_basket_2");
    private final By FAQ_LOGO = By.id("menu_label_faq");
    private final By OPTIONS_LOGO = By.id("loggedin_activator_label");
    private final By SUB_OPTION_BASKET = By.id("menu_label_basket");
    private final By SUB_OPTION_CONTACT = By.id("menu_label_contact_us");

    private final By POINT_TRACKER_POPUP = By.id("pointTrackersSeparatedLeftCellPointsToCashContainerFullWidth");
    private final By POINT_TRACKER_TITLE = By.id("pointsToCashPointTrackerPopupTitle");
    private final By POINT_TRACKER_DESC = By.id("pointsToCashPointTrackerPopupText");
    private final By POINT_TRACKER_CLOSE = By.xpath("//div[@class=\"close-modal close-modal-lfInfo\"]");

    private final By OFFER_POPUP = By.id("offerDataContainer");
    private final By OFFER_POPUP_CONTENT = By.id("popup_content");
    private final By OFFER_POPUP_TITLE = By.id("offerText");
    private final By OFFER_POPUP_DESC = By.id("offerDetailText");
    private final By OFFER_POPUP_SUB_DESC = By.xpath("//div[@id=\"offerDetailText\"]/following::p[4]");
    private final By OFFER_POPUP_CLOSE = By.id("popup_dialog_close_u315");

    private final By CATALOGUE_CARDS = By.xpath("(//div[@class=\"rewardItemWrapper\"][1])");
    private final By CATALOGUE_POPUP_MODEL = By.xpath("//div[@id=\"productPopup\"]");
    private final By CATALOGUE_POPUP_CLOSE = By.xpath("//div[@class=\"close-modal\"]");

    private final By TRANSACTIONS_INFO_POPUP = By.id("toggle_table_title_info");
    private final By TRANSACTION_LINE_CLOSED = By.xpath("//div[@class=\"width100 trx-header-bg\"]");
    private final By TRANSACTION_LINE_OPENED = By.xpath("//div[@class=\"width100 trx-header-bg opened\"]");
    private final By TRANSACTION_DETAILS = By.xpath("//div[@class=\"width100 trxRowDetails\"]");
    private final By TRANSACTION_PAGE_LINK_BUTTON = By.xpath("//a[@class=\"futura-18 lfTrxUnderLink lfFutura16 futura-heavy mobile-spacing-15 lfDarkBlueText\"]");


    public String getUrl(){
        return  this.url;
    }

    public Queue<String> clickOnHeaderElements(){
        Queue<String> siteUrls = new LinkedList<>();
        AtomicReference<Integer> subOptionCounter = new AtomicReference<>(0);

        By[] headerElements = {SHELL_LOGO, STATION_LOGO, GO_PLUS_LOGO, BASKET_LOGO, FAQ_LOGO, OPTIONS_LOGO, OPTIONS_LOGO};

        IntStream.range(1, headerElements.length).forEach(i -> {
            driver.findElement(headerElements[i]).click();
            if(headerElements[i] == OPTIONS_LOGO){
                if(0 == subOptionCounter.getAndSet(1)){
                    driver.findElement(SUB_OPTION_BASKET).click();
                } else {
                    driver.findElement(SUB_OPTION_CONTACT).click();
                }
            }
            boolean isUrlAdded = siteUrls.offer(driver.getCurrentUrl());
            if (!isUrlAdded) {
                System.out.println("Queue full, element not added.");
            }
            driver.findElement(headerElements[0]).click();
        });

        return siteUrls;
    }

    public HashMap<List<Serializable>, Integer[]> pointTrackerPopup(){
        driver.findElement(POINT_TRACKER_POPUP).click();

        By[] byArray = {POINT_TRACKER_TITLE, POINT_TRACKER_DESC};
        List<Serializable> valuesList = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {

                        WebElement webElement = driver.findElement(by);
                        String fontFamily = webElement.getCssValue("font-family");
                        System.out.println("The font family is: " + fontFamily);
                        String fontSize = webElement.getCssValue("font-size");
                        System.out.println("The font size is: " + fontSize);
                        String color = webElement.getCssValue("color");
                        System.out.println("The font color is: " + color);

                        return Stream.of(new String[]{fontFamily, fontSize, color});

                    } catch (NoSuchElementException e) {
                        System.out.println("Element not found: " + by.toString());

                        return Collections.singletonList(new String[]{"N/A", "N/A", "N/A"}).stream();
                    }
                })
                .collect(Collectors.toList()).reversed();  // Collect the result into a List<String[]>

        accumulatePaddingsMarginsOfBy(driver.findElement(POINT_TRACKER_TITLE));
        accumulatePaddingsMarginsOfBy(driver.findElement(POINT_TRACKER_DESC));
        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        Integer[] paddingArray = new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();

        HashMap<List<Serializable>, Integer[]> valuePaddingMap = new HashMap<>();
        valuePaddingMap.put(valuesList, paddingArray);

        driver.findElement(POINT_TRACKER_CLOSE).click();
        return valuePaddingMap;
    }

    public HashMap<List<Serializable>, Integer[]> offerPopup(){
        driver.findElement(OFFER_POPUP).click();

        By[] byArray = {OFFER_POPUP_TITLE, OFFER_POPUP_DESC, OFFER_POPUP_SUB_DESC};
        List<Serializable> valuesList = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        WebElement webElement;

                        if(by != OFFER_POPUP_DESC){
                            webElement = driver.findElements(by).getLast();
                        } else {
                            webElement = driver.findElement(by);
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

                        return Collections.singletonList(new String[]{"N/A", "N/A", "N/A"}).stream();
                    }
                })
                .collect(Collectors.toList()).reversed();  // Collect the result into a List<String[]>


        accumulatePaddingsMarginsOfBy(driver.findElement(OFFER_POPUP_CONTENT));
        for(By byElement: byArray){
            if(byElement != OFFER_POPUP_SUB_DESC){
                accumulatePaddingsMarginsOfBy(driver.findElements(byElement).getLast());
            } else {
                accumulatePaddingsMarginsOfBy(driver.findElement(byElement));
            }
        }
        accumulatePaddingsMarginsOfBy(driver.findElements(By.xpath("//div[@id=\"offerText\"]/parent::*")).getLast());


        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        Integer[] paddingArray = new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();

        HashMap<List<Serializable>, Integer[]> valuePaddingMap = new HashMap<>();
        valuePaddingMap.put(valuesList, paddingArray);

        //driver.findElement(OFFER_POPUP_CLOSE).click();
        return valuePaddingMap;
    }

    /*public HashMap<List<Serializable>, Integer[]> cataloguePopup(){
        //driver.findElement(OFFER_POPUP).click();
        titleRowCounter.set(0);
        By[] byArray = {CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS,
                CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS};

        Stream<Serializable> valuesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        List<WebElement> cleanedWebElementList = new ArrayList<>();
                        List<WebElement> webElementList = driver.findElements(By.xpath(
                                "(//div[@class=\"rewardItemWrapper\"][1])"
                        ));

                        System.out.println("The size of the weblement list is: " + webElementList.size());


                        // Konvertálás Stream<Serializable>-vé  8   9   16   19   20           8   9   16  22  25  26
                        return webElementList.stream().flatMap(actualWebElement -> {

                            actualWebElement.click();


                            List<WebElement> subList = driver.findElement(CATALOGUE_POPUP_MODEL).findElements
                                    (By.xpath(".//div"));
                            if(titleRowCounter.get() < 4){
                                cleanedWebElementList.add(subList.get(7));
                                cleanedWebElementList.add(subList.get(8));
                                cleanedWebElementList.add(subList.get(15));
                                cleanedWebElementList.add(subList.get(18));
                                cleanedWebElementList.add(subList.get(19));
                            }   else {
                                cleanedWebElementList.add(subList.get(7));
                                cleanedWebElementList.add(subList.get(8));
                                cleanedWebElementList.add(subList.get(15));
                                cleanedWebElementList.add(subList.get(21));
                                cleanedWebElementList.add(subList.get(24));
                                cleanedWebElementList.add(subList.get(25));
                            }
                            titleRowCounter.getAndSet(titleRowCounter.get()+1);
                            try {
                                clickRobot(1203, 100);
                            } catch (AWTException e) {
                                throw new RuntimeException(e);
                            }
                            return cleanedWebElementList.stream().map(actualPopUpWebElement -> {
                                String fontFamily = actualPopUpWebElement.getCssValue("font-family");
                                String fontSize = actualPopUpWebElement.getCssValue("font-size");
                                String color = actualPopUpWebElement.getCssValue("color");
                                //driver.findElement(CATALOGUE_POPUP_CLOSE).click();
                                //System.out.println(driver.findElements(By.xpath("//div[@aria-label=\"Close dialog\"]")).get(6).getLocation().getY());

                                //driver.findElements(By.xpath("//div[@aria-label=\"Close dialog\"]")).get(6).click();


                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                return new String[]{fontFamily, fontSize, color};
                            });
                        });

                    } catch (NoSuchElementException e) {
                        System.out.println("Element not found: " + by.toString());
                        return Stream.of("N/A", "N/A", "N/A");
                    }
                });


        List<Serializable> valuesList = valuesStream.collect(Collectors.toList());  // Collect the result into a List<String[]>


        accumulatePaddingsMarginsOfBy(driver.findElement(OFFER_POPUP_CONTENT));
        for(By byElement: byArray){
            if(byElement != OFFER_POPUP_SUB_DESC){
                accumulatePaddingsMarginsOfBy(driver.findElements(byElement).getLast());
            } else {
                accumulatePaddingsMarginsOfBy(driver.findElement(byElement));
            }
        }
        accumulatePaddingsMarginsOfBy(driver.findElements(By.xpath("//div[@id=\"offerText\"]/parent::*")).getLast());


        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        Integer[] paddingArray = new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();

        HashMap<List<Serializable>, Integer[]> valuePaddingMap = new HashMap<>();
        valuePaddingMap.put(valuesList, paddingArray);

        driver.findElement(OFFER_POPUP_CLOSE).click();
        return valuePaddingMap;
    }*/
    public HashMap<List<Serializable>, Integer[]> cataloguePopup() {
        titleRowCounter.set(0);
        /*By[] byArray = {CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS,
                CATALOGUE_CARDS, CATALOGUE_CARDS, CATALOGUE_CARDS};*/
        List<WebElement> webElementList = driver.findElements(By.xpath(
                "(//div[@class=\"rewardItemWrapper\"][1])"
        ));
        WebElement[] webElementArray = webElementList.toArray(new WebElement[0]);

        Stream<Serializable> valuesStream = Stream.empty();

        for (WebElement element : webElementArray) {
            valuesStream = Stream.concat(valuesStream, generateValuesStreamCatPop(element));
            driver.findElements(By.xpath("//div[@aria-label=\"Close dialog\"]")).get(6).click();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        List<Serializable> valuesList = valuesStream.collect(Collectors.toList());

        //accumulatePaddingsMarginsOfBy(driver.findElement(CATALOGUE_POPUP_MODEL));
        /*for (By byElement : byArray) {
            if (byElement != OFFER_POPUP_SUB_DESC) {
                accumulatePaddingsMarginsOfBy(driver.findElements(byElement).getLast());
            } else {
                accumulatePaddingsMarginsOfBy(driver.findElement(byElement));
            }
        }*/
        //accumulatePaddingsMarginsOfByList(driver.findElement(CATALOGUE_POPUP_MODEL).findElements(By.xpath(".//div")));
        //accumulatePaddingsMarginsOfBy(driver.findElements(By.xpath("//div[@id=\"offerText\"]/parent::*")).getLast());

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        Integer[] paddingArray = new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();

        HashMap<List<Serializable>, Integer[]> valuePaddingMap = new HashMap<>();
        valuePaddingMap.put(valuesList, paddingArray);

        return valuePaddingMap;
    }



    private Stream<String[]> generateValuesStreamCatPop(WebElement element) {
        try {
            System.out.println("The value of the titleRowCounter is: " + titleRowCounter.get());
            List<WebElement> cleanedWebElementList = new ArrayList<>();

            element.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cataloguePopupModel = wait.until(ExpectedConditions.visibilityOfElementLocated(CATALOGUE_POPUP_MODEL));

            List<WebElement> subList = cataloguePopupModel.findElements(By.xpath(".//div"));
            accumulatePaddingsMarginsOfByList(subList);

            System.out.println("The size of the sublist is: " + subList.size());

            if (subList.size() >= 27) {
                if (titleRowCounter.get() < 4) {
                    cleanedWebElementList.add(subList.get(7));
                    cleanedWebElementList.add(subList.get(8));
                    cleanedWebElementList.add(subList.get(15));
                    cleanedWebElementList.add(subList.get(18));
                    cleanedWebElementList.add(subList.get(19));
                } else {
                    cleanedWebElementList.add(subList.get(7));
                    cleanedWebElementList.add(subList.get(8));
                    cleanedWebElementList.add(subList.get(15));
                    if (subList.size() > 21) cleanedWebElementList.add(subList.get(21));
                    if (subList.size() > 24) cleanedWebElementList.add(subList.get(24));
                    if (subList.size() > 25) cleanedWebElementList.add(subList.get(25));
                }
            } else {
                System.out.println("The sublist does not have enough elements. Sublist size: " + subList.size());
                cleanedWebElementList.addAll(subList);
            }

            titleRowCounter.set(titleRowCounter.get() + 1);

            return cleanedWebElementList.stream().map(actualPopUpWebElement -> {
                try {
                    String fontFamily = actualPopUpWebElement.getCssValue("font-family");
                    String fontSize = actualPopUpWebElement.getCssValue("font-size");
                    String color = actualPopUpWebElement.getCssValue("color");
                    return new String[]{fontFamily, fontSize, color};
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element, retrying...");
                    // Attempt to re-fetch or skip
                    return null;
                }
            }).filter(Objects::nonNull);

        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + element.toString());
            return null;
        }
    }

    public HashMap<List<Serializable>, Integer[]> interactTransactions(){

        List<WebElement> webElementPopupList = driver.findElement(TRANSACTIONS_INFO_POPUP).findElements(By.xpath(".//p"));

        WebElement[] webElementPopupArray = webElementPopupList.toArray(new WebElement[0]);

        Stream<Serializable> valuesStream = Stream.empty();

        for (WebElement element : webElementPopupArray) {
            valuesStream = Stream.concat(valuesStream, generateValuesStreamTransInfoPop(element));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO TRANSACTION INTERACT ELEMENTS

        driver.findElement(TRANSACTION_LINE_CLOSED).click();

        List<WebElement> webElementTrxList = driver.findElement(TRANSACTION_DETAILS).findElements(By.xpath(".//div"));

        WebElement[] webElementTrxArray = webElementTrxList.toArray(new WebElement[0]);

        for (WebElement element : webElementTrxArray) {
            valuesStream = Stream.concat(valuesStream, generateValuesStreamTransInfoPop(element));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        //TODO END LINE of above code

        List<Serializable> valuesList = valuesStream.collect(Collectors.toList());

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        Integer[] paddingArray = new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();

        HashMap<List<Serializable>, Integer[]> valuePaddingMap = new HashMap<>();
        valuePaddingMap.put(valuesList, paddingArray);

        return valuePaddingMap;
    }

    public String gotToTransactionPage(){
        driver.findElement(TRANSACTION_PAGE_LINK_BUTTON).click();
        return driver.getCurrentUrl();
    }

    private Stream<String> generateValuesStreamTransInfoPop(WebElement element) {
        try {
            System.out.println("The value of the titleRowCounter is: " + titleRowCounter.get());
            List<WebElement> cleanedWebElementList = new ArrayList<>();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement transactionPopupModel = wait.until(ExpectedConditions.visibilityOfElementLocated(TRANSACTIONS_INFO_POPUP));

            //List<WebElement> subList = cataloguePopupModel.findElements(By.xpath(".//div"));
            accumulatePaddingsMarginsOfBy(element);



            String fontFamily = element.getCssValue("font-family");
            System.out.println("The font family is: " + fontFamily);
            String fontSize = element.getCssValue("font-size");
            System.out.println("The font size is: " + fontSize);
            String color = element.getCssValue("color");
            System.out.println("The font color is: " + color);

            return Stream.of(new String[]{fontFamily, fontSize, color});

        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + element.toString());
            return null;
        }
    }
    private void clickRobot(int xCoordinate, int yCoordinate) throws AWTException {

        Robot robot = new Robot();
        robot.mouseMove(xCoordinate, yCoordinate);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

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
        return null;
    }

    @Override
    public Integer[] getPaddings() {
        return new Integer[0];
    }

    @Override
    public void setLocationsList() {

    }

    @Override
    public void createMapFromLocationsList() {

    }

    @Override
    public LinkedHashMap<Integer, Integer[]> absoluteLocations() {
        return null;
    }

    @Override
    public LinkedHashMap<Integer, Integer[]> relativeLocations() {
        return null;
    }




       /*public Stream<Serializable> generateValuesStream(WebElement element) {
            try {
                System.out.println("The value of the titlerow is: " + titleRowCounter.get());
                List<WebElement> cleanedWebElementList = new ArrayList<>();

                element.click();

                List<WebElement> subList = driver.findElement(CATALOGUE_POPUP_MODEL)
                        .findElements(By.xpath(".//div"));

                System.out.println("The size of the sublist is: " + subList.size());

                if (titleRowCounter.get() < 4) {
                    cleanedWebElementList.add(subList.get(7));
                    cleanedWebElementList.add(subList.get(8));
                    cleanedWebElementList.add(subList.get(15));
                    cleanedWebElementList.add(subList.get(18));
                    cleanedWebElementList.add(subList.get(19));
                } else {
                    cleanedWebElementList.add(subList.get(7));
                    cleanedWebElementList.add(subList.get(8));
                    cleanedWebElementList.add(subList.get(15));
                    cleanedWebElementList.add(subList.get(21));
                    cleanedWebElementList.add(subList.get(24));
                    cleanedWebElementList.add(subList.get(25));
                }

                titleRowCounter.set(titleRowCounter.get() + 1);

                return cleanedWebElementList.stream().map(actualPopUpWebElement -> {
                    String fontFamily = actualPopUpWebElement.getCssValue("font-family");
                    String fontSize = actualPopUpWebElement.getCssValue("font-size");
                    String color = actualPopUpWebElement.getCssValue("color");
                    return new String[]{fontFamily, fontSize, color};
                });

            } catch (NoSuchElementException e) {
                System.out.println("Element not found: " + element.toString());
                return Stream.of("N/A", "N/A", "N/A");
            }

    }*/

    /*public Stream<Serializable> generateValuesStream(WebElement element) {
        try {
            System.out.println("The value of the titlerow is: " + titleRowCounter.get());
            List<WebElement> cleanedWebElementList = new ArrayList<>();

            element.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cataloguePopupModel = wait.until(ExpectedConditions.visibilityOfElementLocated(CATALOGUE_POPUP_MODEL));

            List<WebElement> subList = cataloguePopupModel.findElements(By.xpath(".//div"));
            accumulatePaddingsMarginsOfByList(subList);

            System.out.println("The size of the sublist is: " + subList.size());

            if (subList.size() >= 27) {
                if (titleRowCounter.get() < 4) {
                    cleanedWebElementList.add(subList.get(7));
                    cleanedWebElementList.add(subList.get(8));
                    cleanedWebElementList.add(subList.get(15));
                    cleanedWebElementList.add(subList.get(18));
                    cleanedWebElementList.add(subList.get(19));
                } else {
                    cleanedWebElementList.add(subList.get(7));
                    cleanedWebElementList.add(subList.get(8));
                    cleanedWebElementList.add(subList.get(15));
                    // Ellenőrizzük, hogy léteznek-e ezek az indexek
                    if (subList.size() > 21) cleanedWebElementList.add(subList.get(21));
                    if (subList.size() > 24) cleanedWebElementList.add(subList.get(24));
                    if (subList.size() > 25) cleanedWebElementList.add(subList.get(25));
                }
            } else {
                System.out.println("The sublist does not have enough elements. Sublist size: " + subList.size());
                // Ha nem elég hosszú a lista, csak a létező elemeket adjuk hozzá
                cleanedWebElementList.addAll(subList);
            }

            titleRowCounter.set(titleRowCounter.get() + 1);

            return cleanedWebElementList.stream().map(actualPopUpWebElement -> {
                System.out.println("this is the " + titleRowCounter.get() +".th iteration");
                String fontFamily = actualPopUpWebElement.getCssValue("font-family");
                String fontSize = actualPopUpWebElement.getCssValue("font-size");
                String color = actualPopUpWebElement.getCssValue("color");
                return new String[]{fontFamily, fontSize, color};
            });

            //Just the 7th iteration is printed, the prob is that -> we may not return a list of items

        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + element.toString());
            return Stream.of("N/A", "N/A", "N/A");
        }
    }*/
}
