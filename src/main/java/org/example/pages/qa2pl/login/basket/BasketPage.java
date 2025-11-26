package org.example.pages.qa2pl.login.basket;

import io.cucumber.java.sl.In;
import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasketPage extends AbstractGetPaddingSupporter implements SaveImage {

    WebDriver driver;

    public BasketPage(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";

    public String getUrl(){
        return this.url;
    }

    private final By BASKET_WHOLE_DIV = By.id("simple_content");
    private final By BASKET_MENU_OPTION = By.xpath("(//div[@class=\"menuButtonCell\"])[3]");
    private final By BASKET_PAGE_CONTENT = By.xpath("//div[@id=\"empty_cart_container\"]/div");   //  ---> list-size = 2, 0 == title, 1 == cards
    private final By ABOVE_CARDS_TITLE = By.xpath("//div[@id=\"empty_cart_container\"]/div[2]//div[@id=\"basketEmptyCatalogueTitle\"]");
    private final By POINT_INFO_BTN_LABEL = By.id("pointInfoButtonLabel");
    private final By POINT_INFO_BTN_VALUE = By.id("pointInfoButtonValue");
    private final By POINT_INFO_ICON = By.id("point_balance_info");
    private final By REWARD_ITEMS = By.xpath("//div[@id=\"rewardItemsNew\"]/div");
    private final By REWARD_ITEM_CONTENT = By.xpath("//div[@id=\"rewardItemsNew\"]/div[1]//div");  // ---> total of 8 cards
    private final String rewardItemContent = "//div[@id=\"rewardItemsNew\"]/div[%d]//div";
    private final By MENU_OPTION_BASKET_SIZE = By.id("basket_label_basket_size");
    private final By REMOVE_BASKET_QUANTITY_BUTTONS = By.xpath("//td[@id=\"basketItemTableQuantity\"]//td");  // ---> 1st is remove btn
    private final By CARD_POPUP_CONTENTS = By.xpath("//div[@id=\"productPopup\"]//div");
    //private final By CARD_POPUP_CLOSE_BUTTON = By.id("popup_dialog_close");
    private final By CARD_POPUP_CLOSE_BUTTON = By.xpath("//div[@class=\"close-modal\"]");

    private Integer pointBalance;
    public Integer getPointBalance(){
        pointBalance = Integer.parseInt(driver.findElement(POINT_INFO_BTN_VALUE).getText());
        return pointBalance;
    }

    public String goToBasketPage(){
        driver.findElement(BASKET_MENU_OPTION).click();
        return driver.getCurrentUrl();
    }

    public By getWHOLE_BASKET_DIV(){
        return this.BASKET_WHOLE_DIV;
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

        By[] byArray = {BASKET_PAGE_CONTENT, ABOVE_CARDS_TITLE, POINT_INFO_BTN_LABEL, POINT_INFO_BTN_VALUE, REWARD_ITEM_CONTENT,
                REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT,
                REWARD_ITEM_CONTENT};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(1);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if (by == BASKET_PAGE_CONTENT) {

                            List<WebElement> webElementList = driver.findElements(by).getFirst()
                                    .findElements(By.xpath("./div"));

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};

                            });

                        } else if (by == ABOVE_CARDS_TITLE || by == POINT_INFO_BTN_LABEL || by == POINT_INFO_BTN_VALUE) {

                            WebElement webElement = driver.findElement(by);
                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});

                        } else if (by == REWARD_ITEM_CONTENT){
                            List<WebElement> cleanedWebElementList;

                            List<WebElement> webElementList = driver.findElements(By.xpath(String.format(rewardItemContent,
                                    titleRowCounter.getAndSet(titleRowCounter.get()+1))));

                            System.out.println("THE SIZE OF THE WEBELEMENT LIST IN CATALOGUE CARDS IS: " + webElementList.size());

                            if(webElementList.get(5).getText().equals("Available at a Shell station")){
                                cleanedWebElementList = IntStream.range(0, webElementList.size())
                                        .filter(i -> i == 5 || i == 6 || i == 9 || i == 10)
                                        .mapToObj(webElementList::get).toList();
                            } else {
                                cleanedWebElementList = IntStream.range(0, webElementList.size())
                                        .filter(i -> i == 5 || i == 6 || i == 9 || i == 10 || i == 17)
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
        List<WebElement> accumulatedElementsList = new LinkedList<>();
        accumulatedElementsList.addAll(driver.findElements(BASKET_PAGE_CONTENT).getFirst().findElements(By.xpath("./div")));
        accumulatedElementsList.addAll(driver.findElements(BASKET_PAGE_CONTENT).getLast().findElements(By.xpath(".//div")));
        accumulatedElementsList.add(driver.findElement(POINT_INFO_BTN_LABEL));
        accumulatedElementsList.add(driver.findElement(POINT_INFO_ICON));

        accumulatePaddingsMarginsOfByList(accumulatedElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] byArray = {BASKET_PAGE_CONTENT, ABOVE_CARDS_TITLE, POINT_INFO_BTN_LABEL, POINT_INFO_BTN_VALUE, POINT_INFO_ICON};

        Arrays.stream(byArray).forEach(b -> locationsList.add(driver.findElement(b).getLocation()));

        List<WebElement> cleanedWebElementList;
        List<WebElement> webElementList;
        WebElement[] basketCardArray;
        AtomicReference<Integer> locationRowCounter = new AtomicReference<>(1);
        int cardCounter = 0;
        while(cardCounter < 7){
            webElementList = driver.findElements(By.xpath(String.format("//div[@id=\"rewardItemsNew\"]/div[%d]//div",
                    locationRowCounter.getAndSet(locationRowCounter.get()+1))));

            System.out.println("THE SIZE OF THE WEBELEMENT LIST IN CATALOGUE CARDS IS: " + webElementList.size());

            if(webElementList.get(5).getText().equals("Available at a Shell station")){
                cleanedWebElementList = IntStream.range(0, webElementList.size())
                        .filter(i -> i == 5 || i == 6 || i == 9 || i == 10)
                        .mapToObj(webElementList::get).toList();
            } else {
                cleanedWebElementList = IntStream.range(0, webElementList.size())
                        .filter(i -> i == 5 || i == 6 || i == 9 || i == 10 || i == 17)
                        .mapToObj(webElementList::get).toList();
            }
            basketCardArray = cleanedWebElementList.toArray(WebElement[]::new);
            Arrays.stream(basketCardArray).forEach(w -> locationsList.add(w.getLocation()));
            cardCounter++;
        }

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

    public HashMap<String, HashMap<Boolean, HashMap<Boolean, Integer>>> interactiveBasketPageElements() throws InterruptedException {
        HashMap<String, HashMap<Boolean, HashMap<Boolean, Integer>>> resultMap = new HashMap<>();
        HashMap<Boolean ,HashMap<Boolean, Integer>> innerMap = new HashMap<>();
        HashMap<Boolean, Integer> mostInnerMap = new HashMap<>();

        driver.findElements(BASKET_PAGE_CONTENT).getFirst().findElement(By.xpath(".//a")).click();
        String catalogUrl = driver.getCurrentUrl();
        driver.navigate().back();
        Thread.sleep(2000);

        Integer pointBalance = Integer.parseInt(driver.findElement(POINT_INFO_BTN_VALUE).getText());
        boolean isPointBalanceAboveZero = pointBalance > 0;

        mostInnerMap.put(isPointBalanceAboveZero, pointBalance);
        Thread.sleep(2000);


        boolean isBasketCardClicked = isBasketCardClickableAsBoolean();
        System.out.println("Add to basket btn is enabled: " + isBasketCardClicked);
        if(isBasketCardClicked){
            scrollToTop();
            driver.findElements(REMOVE_BASKET_QUANTITY_BUTTONS).getFirst().click();
        }

        innerMap.put(isBasketCardClicked, mostInnerMap);

        resultMap.put(catalogUrl, innerMap);
        return  resultMap;
    }

    private ExpectedCondition<WebElement> isBasketCardClickable(){
        return ExpectedConditions.elementToBeClickable(By.xpath(String.format("//div[@id=\"rewardItemsNew\"]/div[%d]//div/" +
                "parent::div[@class=\"rewardItemAddToBasketButtonContainer\"]/div", 6)));
    }
    private boolean isBasketCardClickableAsBoolean() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(isBasketCardClickable());
            driver.findElement(By.xpath(String.format("//div[@id=\"rewardItemsNew\"]/div[%d]//div/" +
                    "parent::div[@class=\"rewardItemAddToBasketButtonContainer\"]/div", 6))).click();
            Thread.sleep(2000);
            System.out.println("Basket size's: " + Integer.parseInt(driver.findElement(MENU_OPTION_BASKET_SIZE).getText()));
            return Integer.parseInt(driver.findElement(MENU_OPTION_BASKET_SIZE).getText()) > 0;
        } catch (TimeoutException e) {
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }


    public List<String[]> getPopUpValues() {
        List<String[]> stringArrayList = new ArrayList<>();
        By[] byArray = {REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT,
                REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(1);

        for (By by : byArray) {
            try {
                if (titleRowCounter.get() > 1) {
                    driver.findElement(CARD_POPUP_CLOSE_BUTTON).click();
                    Thread.sleep(1000);
                }

                driver.findElements(By.xpath(String.format(rewardItemContent, titleRowCounter.getAndSet(titleRowCounter.get() + 1))))
                        .get(7).click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOfElementLocated(CARD_POPUP_CONTENTS));

                List<WebElement> webElementList = driver.findElements(CARD_POPUP_CONTENTS);
                System.out.println("Popup contents size: " + webElementList.size());

                List<WebElement> cleanedWebElementList = webElementList.stream()
                        .filter(e -> e.isDisplayed())
                        .toList();

                for (WebElement element : cleanedWebElementList) {
                    String fontFamily = element.getCssValue("font-family");
                    String fontSize = element.getCssValue("font-size");
                    String color = element.getCssValue("color");
                    stringArrayList.add(new String[]{fontFamily, fontSize, color});
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element encountered. Skipping...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return stringArrayList;
    }

    public Integer[] getPopUpPaddings(){
        By[] byArray = {REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT,
                REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT, REWARD_ITEM_CONTENT};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(1);

        for (By by : byArray) {
            try {
                if (titleRowCounter.get() > 1) {
                    driver.findElement(CARD_POPUP_CLOSE_BUTTON).click();
                    Thread.sleep(1000);
                }

                driver.findElements(By.xpath(String.format(rewardItemContent, titleRowCounter.getAndSet(titleRowCounter.get() + 1))))
                        .get(7).click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOfElementLocated(CARD_POPUP_CONTENTS));

                List<WebElement> webElementList = driver.findElements(CARD_POPUP_CONTENTS);
                System.out.println("Popup contents size: " + webElementList.size());

                List<WebElement> cleanedWebElementList = webElementList.stream()
                        .filter(e -> e.isDisplayed())
                        .toList();

                accumulatePaddingsMarginsOfByList(cleanedWebElementList);

            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element encountered. Skipping...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

}
