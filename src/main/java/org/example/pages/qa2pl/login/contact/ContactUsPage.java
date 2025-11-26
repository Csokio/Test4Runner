package org.example.pages.qa2pl.login.contact;

import com.github.dockerjava.api.model.Link;
import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.example.utils.StringArrayKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ContactUsPage extends AbstractGetPaddingSupporter implements SaveImage {

    private WebDriver driver;

    public ContactUsPage(WebDriver driver){
        this.driver = driver;
    }

    private final By HEADER_MENU_PROFILE_ICON = By.id("loggedin_activator_container");
    private final By HEADER_DROPDOWN_CONTACTUS_OPTION = By.id("menuContactUsElement");
    private final By CONTACT_US_WHOLE_PAGE =By.id("customerServiceMainContainer");
    private final By CONTACT_US_TITLE_PADDING = By.id("back_button_text_content"); // ---> with .//h1 can get title's ccs values.
    private final By CONTACT_US_TEXT_UNDER_TITLE = By.id("complete_form_text");
    private final By CONTACT_US_FORM = By.id("contact_form");  // ---> .//div  indexes: 1(single), 5, 7, 10, 13(//div.sublist(0, 2)),  21(single)   .//textarea[1]  .//span.sublist[0,2]
    private final By CONTACT_US_FORM_SUBMIT_BUTTON = By.id("contact_submit_button");
    private final By CONTACT_US_FOOTER_CONTENT = By.id("contact_footer_content");  // ---> .//img  &&  for values .//div[4, 9, 10, 11, 15, 16, 17, 22, 23, 24]
    private final By SUBMITTED_ERROR_MASSAGES = By.xpath("//div[@class=\"error-message-container no-print\"]");
    private final By SELECT_REASON = By.xpath("//select[@id=\"subject\"]");
    private final By TEXTAREA_DIV = By.id("contact_message");
    private final By CAPTCHA_DIV = By.id("captcha");
    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";

    private String imageUrl;

    public String getUrl(){
        return this.url;
    }
    public String getImageUrl(){
        return this.imageUrl;
    }
    public String goToContactUsPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(HEADER_MENU_PROFILE_ICON)));
        driver.findElement(HEADER_MENU_PROFILE_ICON).click();
        driver.findElement(HEADER_DROPDOWN_CONTACTUS_OPTION).click();
        return driver.getCurrentUrl();
    }

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        List<WebElement> headerElementsList = driver.findElement(CONTACT_US_FOOTER_CONTENT).findElements(By.xpath(".//img"));


                /*.findElements(By.xpath(".//div[@class=\"width100 relative dynamic-background " +
                "rewardItemTitleData dynamic-background reset-transition\"]"));*/

        System.out.println("The size of the headerelementslist is: " + headerElementsList.size());
        WebElement[] headerElementsArray = IntStream.range(0, headerElementsList.size())
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
        String[] imageNames = {"contact_footer_faq_icon.png", "contact_footer_phone_icon.png", "contact_footer_twitter_icon.png"};

        IntStream.range(0, imageUrls.length).forEach(i -> {
            ReadImageUrl readImageUrl = new ReadImageUrl(imageUrls[i], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_qa2_pl\\login\\contactus_page");
            if(!readImageUrl.writeImage(imageNames[i])){
                isImagesSaved.set(false);
            }
        });

        return isImagesSaved.get();
    }

    @Override
    public List<String[]> valuesList() {
        By[] byArray = {CONTACT_US_TITLE_PADDING, CONTACT_US_TEXT_UNDER_TITLE, CONTACT_US_FORM, CONTACT_US_FORM, CONTACT_US_FORM, CONTACT_US_FORM,
            CONTACT_US_FORM_SUBMIT_BUTTON, CONTACT_US_FOOTER_CONTENT};
        AtomicReference<Integer> contactUsFormCounter = new AtomicReference<>(0);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        System.out.println("The value of the contact us atomic counter is: " + contactUsFormCounter.get());
                        if (by == CONTACT_US_TITLE_PADDING || by == CONTACT_US_TEXT_UNDER_TITLE) {
                            WebElement webElement;
                            if (by == CONTACT_US_TITLE_PADDING) {
                                webElement = driver.findElement(by).findElement(By.xpath(".//h1"));
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

                        } else if (by == CONTACT_US_FORM) {
                            WebElement webElement;
                            List<WebElement> webElementList;
                            List<WebElement> cleanedWebElementList;

                            if (contactUsFormCounter.get() == 0) {
                                webElementList = driver.findElement(by).findElements(By.xpath("//span")).subList(0, 2);

                                contactUsFormCounter.getAndSet(contactUsFormCounter.get() + 1);

                                for (WebElement element : webElementList) {
                                    System.out.println("The text of the element is: " + element.getText());
                                }

                                return webElementList.stream().map(actualWebElement -> {
                                    String fontFamily = actualWebElement.getCssValue("font-family");
                                    String fontSize = actualWebElement.getCssValue("font-size");
                                    String color = actualWebElement.getCssValue("color");
                                    return new String[]{fontFamily, fontSize, color};
                                });

                            } else if (contactUsFormCounter.get() == 1 || contactUsFormCounter.get() == 2) {

                                System.out.println("Contact us title and text invoked");

                                if (contactUsFormCounter.get() == 1) {
                                    webElement = driver.findElement(by).findElements(By.xpath(".//div")).get(1);
                                } else {
                                    webElement = driver.findElement(by).findElements(By.xpath(".//div")).get(21);
                                }

                                contactUsFormCounter.getAndSet(contactUsFormCounter.get() + 1);

                                String fontFamily = webElement.getCssValue("font-family");
                                System.out.println("The font family is: " + fontFamily);
                                String fontSize = webElement.getCssValue("font-size");
                                System.out.println("The font size is: " + fontSize);
                                String color = webElement.getCssValue("color");
                                System.out.println("The font color is: " + color);

                                return Stream.of(new String[]{fontFamily, fontSize, color});
                            } else if (contactUsFormCounter.get() == 3) {

                                System.out.println("Contact us middle list's invoked");

                                webElementList = driver.findElement(by).findElements(By.xpath("//label"));
                                cleanedWebElementList = IntStream.range(0, webElementList.size())
                                        .filter(i -> i == 0 || i == 2 || i == 4 || i == 6 || i == 8)
                                        .mapToObj(webElementList::get)
                                        .toList();

                                contactUsFormCounter.getAndSet(contactUsFormCounter.get() + 1);
                                System.out.println("The size of the cleanedWebElementList is: " + cleanedWebElementList.size());

                                return cleanedWebElementList.stream().map(actualWebElement -> {
                                    String fontFamily = actualWebElement.getCssValue("font-family");
                                    String fontSize = actualWebElement.getCssValue("font-size");
                                    String color = actualWebElement.getCssValue("color");
                                    return new String[]{fontFamily, fontSize, color};
                                });
                            } else {
                                if (contactUsFormCounter.get() == 4) {
                                    webElement = driver.findElement(by).findElements(By.xpath(".//textarea")).getFirst();

                                    System.out.println("Textarea webelement invoked");

                                    String fontFamily = webElement.getCssValue("font-family");
                                    System.out.println("The font family is: " + fontFamily);
                                    String fontSize = webElement.getCssValue("font-size");
                                    System.out.println("The font size is: " + fontSize);
                                    String color = webElement.getCssValue("color");
                                    System.out.println("The font color is: " + color);

                                    return Stream.of(new String[]{fontFamily, fontSize, color});
                                }
                            }

                        } else if (by == CONTACT_US_FORM_SUBMIT_BUTTON) {

                            WebElement webElement = driver.findElement(by);

                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});

                        } else if (by == CONTACT_US_FOOTER_CONTENT) {
                            List<WebElement> cleanedWebElementList;

                            List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//div"));
                            cleanedWebElementList = IntStream.range(0, webElementList.size())
                                    .filter(i -> i == 4 || i == 9 || i == 10 || i == 11 || i == 15 || i == 16
                                        || i == 17 || i == 22 || i == 23 || i == 24)
                                    .mapToObj(webElementList::get)
                                    .toList();

                            System.out.println("The size of the cleanedWebElementList is: " + cleanedWebElementList.size());

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
                    return null;
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
        List<WebElement> accumulatedElementsList = driver.findElement(CONTACT_US_WHOLE_PAGE).findElements(By.xpath(".//div"));
        accumulatedElementsList.addAll(driver.findElement(CONTACT_US_WHOLE_PAGE).findElements(By.xpath(".//input")).subList(0,4));
        accumulatedElementsList.add(driver.findElement(CONTACT_US_WHOLE_PAGE).findElements(By.xpath(".//textarea")).getFirst());
        accumulatedElementsList.add(driver.findElement(CONTACT_US_TITLE_PADDING));

        accumulatePaddingsMarginsOfByList(accumulatedElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] byArray = {CONTACT_US_TITLE_PADDING, CONTACT_US_TEXT_UNDER_TITLE, CONTACT_US_FORM_SUBMIT_BUTTON};
        Arrays.stream(byArray).forEach(b -> locationsList.add(driver.findElement(b).getLocation()));

        List<WebElement> webElementList = new ArrayList<>();
        WebElement[] basketCardArray;

        webElementList.addAll(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//span")).subList(0, 2));
        webElementList.add(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//div")).get(1));
        webElementList.add(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//div")).get(4));
        webElementList.add(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//div")).get(7));
        webElementList.add(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//div")).get(10));
        webElementList.add(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//div")).get(13));
        webElementList.add(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//div")).get(21));
        webElementList.addAll(driver.findElement(CONTACT_US_FORM).findElements(By.xpath("//div")).subList(25, 27));
        webElementList.add(driver.findElement(CONTACT_US_FORM_SUBMIT_BUTTON));

        List<WebElement> cleanedFooterElementList;
        List<WebElement> footerElementList = driver.findElement(CONTACT_US_FOOTER_CONTENT).findElements(By.xpath(".//div"));
        cleanedFooterElementList = IntStream.range(0, footerElementList.size())
                .filter(i -> i == 4 || i == 9 || i == 10 || i == 11 || i == 15 || i == 16
                        || i == 17 || i == 22 || i == 23 || i == 24)
                .mapToObj(footerElementList::get)
                .toList();

        webElementList.addAll(cleanedFooterElementList);

        basketCardArray = webElementList.toArray(WebElement[]::new);
        Arrays.stream(basketCardArray).forEach(w -> locationsList.add(w.getLocation()));
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

    public HashMap<StringArrayKey, String[]> clickSubmitBtnOnlyPreFilledForm(){
        HashMap<StringArrayKey, String[]> resultMap = new LinkedHashMap<>();

        List<WebElement> errorFieldList = new ArrayList<>();

        driver.findElement(CONTACT_US_FORM_SUBMIT_BUTTON).click();

        List<WebElement> errorMessageList = driver.findElements(SUBMITTED_ERROR_MASSAGES).subList(1, driver.findElements(SUBMITTED_ERROR_MASSAGES).size());

        errorFieldList.add(driver.findElement(SELECT_REASON));
        errorFieldList.add(driver.findElement(TEXTAREA_DIV));
        errorFieldList.add(driver.findElement(CAPTCHA_DIV));

        IntStream.range(0, errorMessageList.size()).forEach(i -> {
            String fontFamily = errorMessageList.get(i).getCssValue("font-family");
            String fontSize = errorMessageList.get(i).getCssValue("font-size");
            String fontWeight = errorMessageList.get(i).getCssValue("font-weight");
            String color = errorMessageList.get(i).getCssValue("color");

            System.out.println("Current iteration is: " + i);

            resultMap.put(new StringArrayKey(new String[]{String.valueOf(i + 1), errorFieldList.get(i).getCssValue("border")}), new String[]{fontFamily, fontSize, fontWeight, color});
        });

        return resultMap;
    }

    public HashMap<StringArrayKey, String[]> clickSubmitBtnInvalidEmailAndCaptcha(){
        HashMap<StringArrayKey, String[]> resultMap = new LinkedHashMap<>();
        List<WebElement> errorFieldList = new ArrayList<>();

        Select reasonSelect = new Select(driver.findElement(SELECT_REASON));
        reasonSelect.selectByIndex(1);

        driver.findElement(TEXTAREA_DIV).sendKeys("Test");
        driver.findElement(CONTACT_US_FORM).findElements(By.xpath(".//input")).get(2).sendKeys("a@a");

        driver.findElement(CONTACT_US_FORM_SUBMIT_BUTTON).click();

        List<WebElement> errorMessageList = driver.findElements(SUBMITTED_ERROR_MASSAGES).subList(1, driver.findElements(SUBMITTED_ERROR_MASSAGES).size());

        errorFieldList.add(driver.findElement(CONTACT_US_FORM).findElements(By.xpath(".//input")).get(2));
        errorFieldList.add(driver.findElement(CAPTCHA_DIV));
        errorFieldList.add(driver.findElement(SELECT_REASON));
        errorFieldList.add(driver.findElement(TEXTAREA_DIV));

        errorMessageList.addAll(List.of(driver.findElement(CONTACT_US_FORM).findElement(By.xpath("//label[@id=\"subject_text\"]")),
                driver.findElement(CONTACT_US_FORM).findElement(By.xpath("//label[@id=\"message_u11\"]"))));

        IntStream.range(0, errorFieldList.size()).forEach(i -> {

            String fontFamily;
            String fontSize;
            String fontWeight;
            String color;

            if(errorMessageList.get(i) == null){
                fontFamily = "null";
                fontSize = "null";
                fontWeight = "null";
                color = "null";
            } else {
                fontFamily = errorMessageList.get(i).getCssValue("font-family");
                fontSize = errorMessageList.get(i).getCssValue("font-size");
                fontWeight = errorMessageList.get(i).getCssValue("font-weight");
                color = errorMessageList.get(i).getCssValue("color");
            }

            System.out.println("Current iteration is: " + i);

            resultMap.put(new StringArrayKey(new String[]{String.valueOf(i + 1), errorFieldList.get(i).getCssValue("border")}), new String[]{fontFamily, fontSize, fontWeight, color});
        });

        return resultMap;
    }

}
