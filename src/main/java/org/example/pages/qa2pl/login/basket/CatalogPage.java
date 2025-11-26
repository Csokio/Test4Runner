package org.example.pages.qa2pl.login.basket;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CatalogPage extends AbstractGetPaddingSupporter implements SaveImage {

    private final By CATALOG_WHOLE_DIV = By.id("simple_content");
    private final By BASKET_MENU_OPTION = By.xpath("(//div[@class=\"menuButtonCell\"])[3]");
    private final By BASKET_PAGE_CONTENT = By.xpath("//div[@id=\"empty_cart_container\"]/div");   //  ---> list-size = 2, 0 == title, 1 == cards
    private final By CATALOG_REWARDS_ALL_CARDS = By.id("rewardItemsNew");
    private final By CATALOG_REWARDS_ITEMS = By.xpath("//div[@id=\"rewardItemsNew\"]/div");
    private final By CATALOG_REWARDS_ITEMS_IMAGES = By.xpath("//div[@id=\"rewardItemsNew\"]/div/" +
            "/div[@class=\"width100 relative dynamic-background rewardItemTitleData dynamic-background reset-transition\"]");
    private final By CATALOG_TITLE = By.id("carousel_title");
    private final By CATALOG_FILTER_TITLE_LABEL = By.id("filterStaticButtonLabel");
    private final By BASKET_INFO_BUTTON_LABEL = By.id("basketInfoButtonLabel");
    private final By POINT_INFO_BTN_LABEL = By.id("pointInfoButtonLabel");
    private final By POINT_INFO_BTN_VALUE = By.id("pointInfoButtonValue");
    private final By CATEGORY_SELECT_CONTAINER_LABEL = By.id("categorySelectContainerLabel");
    private final By CATEGORY_SELECT_CONTAINER_CHECKBOX = By.id("category_21_div");
    private final By CATEGORY_SELECT_CONTAINER_BUTTON = By.id("categorySelectContainerButton");
    private final By CATEGORY_SEARCH_INPUT_FILTER = By.id("searchContainerNewInputFilter");
    private final By CATEGORY_FILTER_POINTS_TEXT = By.id("catalogFilterPointsText");
    private final By MINIMUM_FILTER_POINTS_VALUE = By.id("searchContainerMinInputPoints");
    private final By MAXIMUM_FILTER_POINTS_VALUE = By.id("searchContainerMaxInputPoints");
    private final String rewardItemContent = "//div[@id=\"rewardItemsNew\"]/div[%d]//div";
    private final By BACK_BUTTON_TEXT_CONTENT = By.id("back_button_text_content");
    private final By FILTER_ALL_CONTAINER = By.id("catalogNewFilterAllContainer");
    private final By CATALOG_WHOLE_PAGE = By.id("simple_content");
    private final By MENU_OPTION_BASKET_SIZE = By.id("basket_label_basket_size");
    private final By REMOVE_BASKET_QUANTITY_BUTTONS = By.xpath("//td[@id=\"basketItemTableQuantity\"]//td");  // ---> 1st is remove btn
    private final By CARD_POPUP_CLOSE_BUTTON = By.xpath("//div[@class=\"close-modal\"]");
    private final By CARD_POPUP_CONTENTS = By.xpath("//div[@id=\"productPopup\"]//div");

    private WebDriver driver;
    private String imageUrl;

    public CatalogPage(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";

    public String getUrl(){
        return this.url;
    }

    public By getWHOLE_CATALOG_DIV(){
        return this.CATALOG_WHOLE_DIV;
    }

    public By getCATALOG_REWARDS_ITEMS(){
        return this.CATALOG_REWARDS_ALL_CARDS;
    }

    public String goToCatalogPage(){
        driver.findElement(BASKET_MENU_OPTION).click();
        driver.findElements(BASKET_PAGE_CONTENT).getFirst().findElement(By.xpath(".//a")).click();
        return driver.getCurrentUrl();
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        List<WebElement> headerElementsList = driver.findElements(CATALOG_REWARDS_ITEMS_IMAGES);


                /*.findElements(By.xpath(".//div[@class=\"width100 relative dynamic-background " +
                "rewardItemTitleData dynamic-background reset-transition\"]"));*/

        System.out.println("The size of the headerelementslist is: " + headerElementsList.size());
        WebElement[] headerElementsArray = IntStream.range(0, headerElementsList.size())
                .mapToObj(headerElementsList::get)
                .toArray(WebElement[]::new);

        System.out.println("The size of the headerelementsarray is: " + headerElementsArray.length);

        Arrays.stream(headerElementsArray).forEach(w -> stringBuilder.append(w.getAttribute("style")).append(","));
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        AtomicBoolean isImagesSaved = new AtomicBoolean(true);
        String[] imageUrls = imageUrl.split(",");
        String[] imageNames = {"catalog_item1_qa2.png", "catalog_item2_qa2.png", "catalog_item3_qa2.png", "catalog_item4_qa2.png",
                "catalog_item5_qa2.png", "catalog_item6_qa2.png", "catalog_item7_qa2.png", "catalog_item8_qa2.png"};

        String[] extractedImageUrls;
        List<String> expectedImagesLists = new ArrayList<>(8);
        Arrays.stream(imageUrls).forEach(i -> expectedImagesLists.add(i.substring(23, i.length()-4)));

        extractedImageUrls = expectedImagesLists.toArray(new String[0]);

        IntStream.range(0,8).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(extractedImageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_qa2_pl\\login\\catalog_page");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {

        By[] byArray = {CATALOG_TITLE, CATALOG_FILTER_TITLE_LABEL, BASKET_INFO_BUTTON_LABEL, POINT_INFO_BTN_LABEL, POINT_INFO_BTN_VALUE,
                CATEGORY_SELECT_CONTAINER_LABEL, CATEGORY_SEARCH_INPUT_FILTER, CATEGORY_FILTER_POINTS_TEXT, CATALOG_REWARDS_ITEMS,
                CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS,
                CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(1);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if (by != CATALOG_REWARDS_ITEMS) {

                            WebElement webElement = driver.findElement(by);
                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});

                        } else if (by == CATALOG_REWARDS_ITEMS){
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
        List<WebElement> accumulatedElementsList = driver.findElement(CATALOG_WHOLE_PAGE).findElements(By.xpath(".//div"));

        accumulatePaddingsMarginsOfByList(accumulatedElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] byArray = {CATALOG_TITLE, CATALOG_FILTER_TITLE_LABEL, BASKET_INFO_BUTTON_LABEL, POINT_INFO_BTN_LABEL, POINT_INFO_BTN_VALUE,
                CATEGORY_SELECT_CONTAINER_LABEL, CATEGORY_SEARCH_INPUT_FILTER, CATEGORY_FILTER_POINTS_TEXT};

        Arrays.stream(byArray).forEach(b -> locationsList.add(driver.findElement(b).getLocation()));

        List<WebElement> cleanedWebElementList;
        List<WebElement> webElementList;
        WebElement[] basketCardArray;
        AtomicReference<Integer> locationRowCounter = new AtomicReference<>(1);
        int cardCounter = 0;
        while(cardCounter < 8){
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

    public HashMap<String, HashMap<Boolean, HashMap<Boolean, Integer>>> interactiveCatalogPageElements() throws InterruptedException {
        HashMap<String, HashMap<Boolean, HashMap<Boolean, Integer>>> resultMap = new HashMap<>();
        HashMap<Boolean ,HashMap<Boolean, Integer>> innerMap = new HashMap<>();
        HashMap<Boolean, Integer> mostInnerMap = new HashMap<>();

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
        By[] byArray = {CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS,
                CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS};
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
        By[] byArray = {CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS,
                CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS, CATALOG_REWARDS_ITEMS};
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

    public Integer categoryFilterOption() throws InterruptedException {
        driver.findElement(CATEGORY_SELECT_CONTAINER_LABEL).click();
        driver.findElement(CATEGORY_SELECT_CONTAINER_CHECKBOX).click();
        driver.findElement(CATEGORY_SELECT_CONTAINER_BUTTON).click();

        Thread.sleep(2000);
        return driver.findElements(By.xpath("//div[@id=\"rewardItemsNew\"]/div")).size();
    }

    public Integer searchFilterOption() throws InterruptedException {
        driver.findElement(CATEGORY_SEARCH_INPUT_FILTER).sendKeys("toyota");

        Thread.sleep(2000);
        return driver.findElements(By.xpath("//div[@id=\"rewardItemsNew\"]/div")).size();
    }

    public Integer filterPointsValue() throws InterruptedException {
        driver.findElement(MINIMUM_FILTER_POINTS_VALUE).clear();
        driver.findElement(MINIMUM_FILTER_POINTS_VALUE).sendKeys("2");

        driver.findElement(MAXIMUM_FILTER_POINTS_VALUE).clear();
        driver.findElement(MAXIMUM_FILTER_POINTS_VALUE).sendKeys("8");

        Thread.sleep(2000);
        return driver.findElements(By.xpath("//div[@id=\"rewardItemsNew\"]/div")).size();
    }
}
