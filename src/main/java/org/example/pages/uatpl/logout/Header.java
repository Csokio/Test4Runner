package org.example.pages.uatpl.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Header extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;
    private WebDriverWait wait;
    public Header(WebDriver webDriver){
        this.driver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
    }

    private final String url = "https://shell247b-pl.lod.club/";

    public String getUrl(){
        return this.url;
    }

    private final By HEADER_DIV = By.xpath("//header");

    private final By HEADER_LEFT_RIGHT = By.xpath("//mat-toolbar[@class=\"mat-toolbar container center navbar-container mat-toolbar-single-row\"]");
    private final By HEADER_TOP_BOTTOM = By.xpath("//mat-toolbar[@class=\"mat-toolbar container center navbar-container mat-toolbar-single-row\"]/div");
    private final By HEADER_OPTIONS_PADDINGS = By.xpath("//mat-toolbar[@class=\"mat-toolbar container center navbar-container mat-toolbar-single-row\"]/div//button");
    private final By LOGO_SHELL = By.xpath("//header//div[@class='navbar-menu-item logo-container']//img[@alt='logo']");
    private final By LOGO_HOME = By.xpath("//header//img[@alt='logo_hover']");
    private final By LOGO_STATION_LOCATOR = By.xpath("//header//img[@alt='station_locator_logo']");
    private final By LOGO_MORE = By.xpath("//header//img[@alt='more_menu_logo' and @class='hoverHide']");
    private final By LOGO_MORE_HOVER = By.xpath("//header//img[@alt='more_menu_logo' and @class='hoverShow']");
    private final By LOGO_FLAG = By.xpath("//header//img[@alt='Polski_logo']");
    private final By LOGO_LOGIN = By.xpath("//header//img[@alt='logout_logo']");

    //accumulate two header elements, second one contains 5 more, /button to access to them
    private final By HEADER_SPLIT = By.xpath("//header//div[@class=\"navbar-wrapper\"]/div");
    private final By MORE_OPTIONS = By.xpath("//div[@role='menu']//button");

    private final By BUTTON_HOME = By.xpath("(//header//div[@class=\"navbar-wrapper\"]/div)[2]/button[1]");
    private final By BUTTON_STATION = By.xpath("(//header//div[@class=\"navbar-wrapper\"]/div)[2]/button[2]");
    private final By BUTTON_MORE = By.xpath("(//header//div[@class=\"navbar-wrapper\"]/div)[2]/button[3]");
    private final By BUTTON_FLAG = By.xpath("(//header//div[@class=\"navbar-wrapper\"]/div)[2]/button[4]");
    private final By BUTTON_LOG = By.xpath("(//header//div[@class=\"navbar-wrapper\"]/div)[2]/button[5]");

    private List<WebElement> headerRows = new LinkedList<>();
    private List<WebElement> moreRows;
    private final By[] logoArray = {LOGO_SHELL, LOGO_HOME, LOGO_STATION_LOCATOR, LOGO_MORE,
            LOGO_MORE_HOVER, LOGO_FLAG, LOGO_LOGIN};

    private String imageUrl;
    private final String[] paddingNamesArray = {"padding-top", "padding-bottom", "padding-right", "padding-left"};

    @Override
    public String imageUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        /*stringBuilder.append(driver.findElement(LOGO_SHELL).getAttribute("src"));
        stringBuilder.append(",");
        stringBuilder.append(driver.findElement(LOGO_HOME).getAttribute("src"));
        stringBuilder.append(",");
        stringBuilder.append(driver.findElement(LOGO_STATION_LOCATOR).getAttribute("src"));
        stringBuilder.append(",");
        stringBuilder.append(driver.findElement(LOGO_MORE).getAttribute("src"));
        stringBuilder.append(",");
        stringBuilder.append(driver.findElement(LOGO_MORE_HOVER).getAttribute("src"));
        stringBuilder.append(",");
        stringBuilder.append(driver.findElement(LOGO_FLAG).getAttribute("src"));
        stringBuilder.append(",");
        stringBuilder.append(driver.findElement(LOGO_LOGIN).getAttribute("src"));
        stringBuilder.append(",");*/


        Arrays.stream(logoArray).forEach(l -> stringBuilder.append(driver.findElement(l).getAttribute("src")).append(","));
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        imageUrl = stringBuilder.toString();
        return imageUrl;
    }

    @Override
    public boolean saveImageToFile() {
        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_pl";
        List<ReadImageUrl> images = Arrays.asList(
                new ReadImageUrl(imageUrl.split(",")[0], basePath),
                new ReadImageUrl(imageUrl.split(",")[1], basePath),
                new ReadImageUrl(imageUrl.split(",")[2], basePath),
                new ReadImageUrl(imageUrl.split(",")[3], basePath),
                new ReadImageUrl(imageUrl.split(",")[4], basePath),
                new ReadImageUrl(imageUrl.split(",")[5], basePath),
                new ReadImageUrl(imageUrl.split(",")[6], basePath)
        );

        List<String> filenames = Arrays.asList(
                "shellimage.png",
                "homeimage.png",
                "stationimage.png",
                "moreimage.png",
                "morehoverimage.png",
                "flagimage.png",
                "loginimage.png"
        );

        AtomicBoolean allSuccess = new AtomicBoolean(true);

        IntStream.range(0, images.size()).forEach(i -> {
            if(!images.get(i).writeImage(filenames.get(i))){
                allSuccess.set(false);
            }
        });

        return allSuccess.get();
    }

    @Override
    public List<String[]> valuesList() {
        List<String[]> resultList = new LinkedList<>();

        String xpathInsideCard = "./span[2]";

        By[] byArray = {BUTTON_HOME, BUTTON_STATION, BUTTON_MORE, BUTTON_FLAG};
        for(int i = 0; i < byArray.length; i++){
            WebElement webElement = driver.findElement(byArray[i]).findElement(By.xpath(xpathInsideCard));
            String fontFamily = webElement.getCssValue("font-family");
            String fontSize = webElement.getCssValue("font-size");
            String color = webElement.getCssValue("color");

            resultList.add(new String[]{fontFamily, fontSize, color});
        }

        return resultList;
    }

    @Override
    public Integer[] getPaddings() {
        String[] paddingStringArray = new String[19];
        AtomicInteger counter = new AtomicInteger(4);

        paddingStringArray[0] = driver.findElement(HEADER_LEFT_RIGHT).getCssValue("padding-left");
        paddingStringArray[1] = driver.findElement(HEADER_LEFT_RIGHT).getCssValue("padding-right");

        paddingStringArray[2] = driver.findElement(HEADER_TOP_BOTTOM).getCssValue("padding-top");
        paddingStringArray[3] = driver.findElement(HEADER_TOP_BOTTOM).getCssValue("padding-bottom");

        By[] menuOptions = new By[]{HEADER_OPTIONS_PADDINGS, LOGO_HOME, LOGO_STATION_LOCATOR, LOGO_MORE, LOGO_MORE, LOGO_LOGIN};
        Arrays.stream(menuOptions).forEach(e -> {
            if(e.equals(HEADER_OPTIONS_PADDINGS)){
                IntStream.range(0, driver.findElements(HEADER_OPTIONS_PADDINGS).size()).forEach(
                        i -> {
                            paddingStringArray[counter.getAndIncrement()] = driver.findElements(e).get(i).getCssValue("padding-left");
                            paddingStringArray[counter.getAndIncrement()] = driver.findElements(e).get(i).getCssValue("padding-right");
                        }
                );
            } else {
                paddingStringArray[counter.getAndIncrement()] = driver.findElement(e).getCssValue("padding-right");
            }
        });

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{LOGO_SHELL, LOGO_HOME, LOGO_STATION_LOCATOR, LOGO_MORE, LOGO_FLAG, LOGO_LOGIN, BUTTON_HOME,
                BUTTON_STATION, BUTTON_MORE, BUTTON_FLAG};
        Arrays.stream(xpathElements).forEach(e -> locationsList.add(driver.findElement(e).getLocation()));
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

    //Use in testDisplayedHeader
    public int countHeaderElements() {
        headerRows.add(driver.findElements(HEADER_SPLIT).getFirst());

        IntStream.range(0, driver.findElements(HEADER_SPLIT).getLast().findElements(By.xpath("./button")).size()).forEach(
                i -> {
                    headerRows.add(driver.findElements(HEADER_SPLIT).getLast().findElements(By.xpath("./button")).get(i));
                }
        );
        return headerRows.size();
    }

    //Use in testDisplayedHeader
    public String[] verifyHeaderElements() {

        String[] headerElements = new String[6];
        int i = 0;

        for (WebElement row : headerRows) {
            if(i == 0){

                headerElements[i] = row.findElement(By.xpath(".//img")).getAttribute("src");

            } else if (i == headerRows.size()-1){

                headerElements[i] = row.findElement(By.xpath(".//img")).getAttribute("alt");

            } else {
                if (!row.getText().isEmpty()) {

                    headerElements[i] = row.getText();

                }
            }

            i++;
        }

        return headerElements;
    }

    public boolean clickOnHeaderElements() throws InterruptedException {

        final By HEADER_OPTIONS = By.xpath("//header//div[@class=\"navbar-wrapper\"]/div[2]/button");
        headerRows = driver.findElements(HEADER_OPTIONS);

        boolean isValidUrl = true;
        int count = 1;

        for (int i = 0; i < headerRows.size(); i++) {
            headerRows = driver.findElements(HEADER_OPTIONS);

            if (headerRows.get(i).isDisplayed()) {
                headerRows.get(i).click();
                Thread.sleep(3000);
                String currentUrl = driver.getCurrentUrl();
                switch (count) {
                    case 1:
                        if (!currentUrl.equals("https://shell247b-pl.lod.club/")) {
                            isValidUrl = false;
                        }
                        count++;
                        break;
                    case 2, 3, 4:
                        if (!currentUrl.equals("https://shell247b-pl.lod.club/station-locator")) {
                            isValidUrl = false;
                        }
                        count++;
                        break;
                    case 5:
                        if (!currentUrl.equals("https://test.login.consumer.shell.com/login")) {
                            isValidUrl = false;
                        }
                        break;
                    default:
                        return true;
                }
                if (!isValidUrl) {
                    break;
                }

            }
        }
        return isValidUrl;
    }

    public String[] verifyMoreOptionElements() {

        String[] moreElements = new String[3];
        int i = 0;

        driver.findElement(BUTTON_MORE).click();
        driver.findElement(BUTTON_MORE).click();


        moreRows = driver.findElements(MORE_OPTIONS);


        for (WebElement row : moreRows) {
            moreElements[i] = row.getAttribute("innerText");
            i++;
        }

        return moreElements;
    }

    //TODO METHOD DOESN'T CLICK ON MORE BTN
    public boolean clickMoreElements() throws InterruptedException, AWTException {
        //moreRows = driver.findElements(MORE_OPTIONS);
        //final By MORE_MENU_BUTTON = By.xpath("(//header//div[@class=\"navbar-wrapper\"]/div[2]/button)[3]");

        WebElement moreButton;

        boolean isValidUrl = true;
        int count = 0;


        for (int i = 0; i < 3; i++) {

            moreButton = wait.until(ExpectedConditions.elementToBeClickable(BUTTON_MORE));
            //moreButton = driver.findElement(MORE_MENU_BUTTON);

            /*Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(MORE_MENU_BUTTON));
            action.click(driver.findElement(MORE_MENU_BUTTON));*/

            /*JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('aria-expanded', 'true');", moreButton);
            js.executeScript("arguments[0].setAttribute('aria-expanded', 'true');", moreButton);*/

            //moreButton.click();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("arguments[0].scrollIntoView(true);", moreButton);
            js.executeScript("arguments[0].click();", moreButton);

            //moreButton.click();
            //moreButton.click();

            /*Robot robot = new Robot();
            robot.mouseMove(1320, 40);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);*/

            //((JavascriptExecutor) driver).executeScript("document.querySelector('hr').style.display='none';");

            Thread.sleep(2000);

            moreRows = driver.findElements(MORE_OPTIONS);


            moreRows.get(i).click();
            Thread.sleep(3000);
            String currentUrl = driver.getCurrentUrl();
            switch (count) {
                case 0:
                    System.out.println(currentUrl);
                    if (!currentUrl.equals("https://support.shell.nl/hc/nl")) {
                        isValidUrl = false;
                    }
                    break;
                case 1:
                    System.out.println(currentUrl);
                    if (!currentUrl.equals("https://shell247b-pl.lod.club/contact-us")) {
                        isValidUrl = false;
                    }
                    break;
                case 2:
                    System.out.println(currentUrl);
                    if (!currentUrl.equals("https://shell247b-pl.lod.club/how-to-use")) {
                        isValidUrl = false;
                    }
                    break;
                default:
                    return true;
            }
            if (!isValidUrl) {
                break;
            }

            driver.navigate().to(url);
            count++;

        }
        return isValidUrl;
    }

}
