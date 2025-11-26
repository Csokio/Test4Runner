package org.example.pages.qa2pl.login.faqs;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class FAQsPage extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public FAQsPage(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";

    private final By FAQS_MENU_OPTION = By.id("new_faq_button");
    private final By FAQS_TITLE = By.id("back_button_text_content");  //  -----> .//h1
    private final By RIGHT_MAIN_TEXTS = By.xpath("//div[@id=\"faq_right_list\"]//div[@class=\"right-text\"]");  // -----> first 10 of the list
    private final By RIGHT_SUB_TEXTS = By.xpath("//div[@id=\"faq_right_list\"]//div[@class=\"faq-content\"]");
    private final By LEFT_TEXTS = By.xpath("//div[@id=\"faq_left_list\"]//div[@class=\"left-text\"]");
    private final By CONTACT_CONTAINER = By.id("faq_contact_container");
    private final By FAQS_SEARCH_BAR = By.id("faq_search");
    private final By WHOLE_FAQS_DIV = By.id("simple_content");

    public By getWHOLE_FAQS_DIV(){
        return this.WHOLE_FAQS_DIV;
    }

    public String getUrl(){
        return this.url;
    }
    public String goToFAQsPage(){
        driver.findElement(FAQS_MENU_OPTION).click();
        return driver.getCurrentUrl();
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
        By[] byArray = {FAQS_TITLE, LEFT_TEXTS, RIGHT_MAIN_TEXTS, RIGHT_SUB_TEXTS, CONTACT_CONTAINER};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(0);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if (by == RIGHT_MAIN_TEXTS) {

                            List<WebElement> webElementList = driver.findElements(by).subList(0, 9);

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });

                        } else if (by == LEFT_TEXTS || by == RIGHT_SUB_TEXTS) {

                            List<WebElement> webElementList = driver.findElements(by);

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });

                        } else if (by == FAQS_TITLE){

                            WebElement webElement = driver.findElement(by);
                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});

                        } else if (by == CONTACT_CONTAINER){
                            List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//span"));

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });
                        } else {
                            return Stream.of(new String[]{"N/A", "N/A", "N/A"});
                        }

                    } catch (NoSuchElementException e) {
                        System.out.println("Element not found: " + by.toString());
                        return Stream.of(new String[]{"N/A", "N/A", "N/A"});
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
        accumulatedElementsList.add(driver.findElement(FAQS_TITLE));
        accumulatedElementsList.add(driver.findElement(FAQS_SEARCH_BAR));
        accumulatedElementsList.add(driver.findElement(FAQS_SEARCH_BAR).findElement(By.xpath("./following::div")));
        accumulatedElementsList.addAll(driver.findElements(RIGHT_MAIN_TEXTS));
        accumulatedElementsList.addAll(driver.findElements(RIGHT_SUB_TEXTS));
        accumulatedElementsList.addAll(driver.findElements(LEFT_TEXTS));
        accumulatedElementsList.add(driver.findElement(CONTACT_CONTAINER).findElement(By.xpath("./span")));
        accumulatedElementsList.add(driver.findElement(CONTACT_CONTAINER).findElement(By.xpath(".//a")));

        accumulatePaddingsMarginsOfByList(accumulatedElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] byArray = {FAQS_TITLE, FAQS_SEARCH_BAR, CONTACT_CONTAINER, RIGHT_SUB_TEXTS,
            By.xpath("//div[@id=\"faq_contact_container\"]/span"),
            By.xpath("//div[@id=\"faq_contact_container\"]//a"),
            By.xpath("//div[@id=\"faq_contact_container\"]//div[@id=\"faq_right_button_default\"]")};

        Arrays.stream(byArray).forEach(by -> {
            locationsList.add(driver.findElement(by).getLocation());
        });

       driver.findElements(LEFT_TEXTS).stream().forEach(e -> locationsList.add(e.getLocation()));
       driver.findElements(RIGHT_MAIN_TEXTS).subList(0,9).stream().forEach(e -> locationsList.add(e.getLocation()));
       //driver.findElements(RIGHT_SUB_TEXTS).stream().forEach(e -> locationsList.add(e.getLocation()));


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

    public boolean interactiveFAQsElements() throws InterruptedException {

        driver.findElements(LEFT_TEXTS).get(3).click();
        driver.findElements(RIGHT_MAIN_TEXTS).subList(35, 42).forEach(e -> e.click());

        driver.findElement(By.xpath("//div[@id=\"faq_contact_container\"]//div[@id=\"faq_right_button_default\"]")).click();

        String leftTextBackgroundColor = driver.findElement(By.xpath("(//div[@id=\"faq_left_list\"]//div[@class=\"left-text\"]/parent::div)[4]")).getCssValue("background-color");
        String defaultContactNumberBackgroundColor = driver.findElement(By.xpath("//div[@id=\"faq_contact_container\"]//div[@id=\"faq_right_button_default\"]")).getCssValue("background-color");
        String clickedContactNumberBackgroundColor = driver.findElement(By.xpath("//div[@id=\"faq_contact_container\"]//div[@id=\"faq_right_button\"]")).getCssValue("background-color");

        System.out.println(leftTextBackgroundColor);
        //System.out.println(contactNumberBackgroundColor);
        Thread.sleep(2000);

        return leftTextBackgroundColor.equals("rgba(0, 143, 194, 1)") && clickedContactNumberBackgroundColor.equals("rgba(255, 255, 255, 1)") && defaultContactNumberBackgroundColor.equals("rgba(0, 60, 136, 1)");
    }

}
