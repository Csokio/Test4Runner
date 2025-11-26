package org.example.pages.uatgb.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.example.utils.ReadImageUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Header extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
    }

    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";

    public String getUrl() {
        return this.url;
    }

    private final By HEADER_DIV = By.id("desktopSubMenu");
    private final By LOGO_SHELL = By.xpath("//a[@id=\"logoCellLink\"]/img");
    private final By LOGO_HOME = By.id("menu_label_home_icon_hover");
    private final By LOGO_STATION_LOCATOR = By.id("station_cell_image");
    private final By LOGO_MORE = By.id("fp_more_icon");
    private final By LOGO_MORE_HOVER = By.id("fp_more_icon_hover");
    private final By LOGO_FLAG = By.id("fp_site_selector_icon");
    private final By LOGO_LOGIN = By.id("fpMenuLoginButtonHoverShow");

    private final By HEADER = By.id("new_menu_buttons");
    private final By MORE = By.id("fpMenuMoreList");

    private final By BUTTON_HOME = By.xpath("(//div[@class=\"menuButtonTable\"])[1]//label");
    private final By BUTTON_STATION = By.xpath("(//div[@class=\"menuButtonTable\"])[2]//label");
    private final By BUTTON_MORE = By.xpath("(//div[@class=\"menuButtonTable\"])[3]//label");
    //xpath("(//div[@class=\"menuButtonTable\"])[4/5/6]//label 4,5,6 are the more options"

    private final By BUTTON_FLAG = By.xpath("(//div[@class=\"menuButtonTable\"])[4]//label");


    private List<WebElement> headerRows;
    private List<WebElement> moreRows;

    private String imageUrl;
    private final String[] paddingNamesArray = {"padding-top", "padding-bottom", "padding-right", "padding-left"};

    public By getHEADER_DIV(){
        return this.HEADER_DIV;
    }

    @Override
    public String imageUrl() {
        StringBuilder builder = new StringBuilder();
        builder.append(driver.findElement(LOGO_SHELL).getAttribute("src"));
        builder.append(",");
        builder.append(driver.findElement(LOGO_HOME).getAttribute("src"));
        builder.append(",");
        builder.append(driver.findElement(LOGO_STATION_LOCATOR).getAttribute("src"));
        builder.append(",");
        builder.append(driver.findElement(LOGO_MORE).getAttribute("src"));
        builder.append(",");
        builder.append(driver.findElement(LOGO_MORE_HOVER).getAttribute("src"));
        builder.append(",");
        builder.append(driver.findElement(LOGO_FLAG).getAttribute("src"));
        builder.append(",");
        builder.append(driver.findElement(LOGO_LOGIN).getAttribute("src"));

        imageUrl = builder.toString();
        return imageUrl;
    }

    public boolean saveImageToFile2() {
        ReadImageUrl readShellImage = new ReadImageUrl(imageUrl.split(",")[0], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        ReadImageUrl readHomeImage = new ReadImageUrl(imageUrl.split(",")[1], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        ReadImageUrl readStationImage = new ReadImageUrl(imageUrl.split(",")[2], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        ReadImageUrl readMoreImage = new ReadImageUrl(imageUrl.split(",")[3], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        ReadImageUrl readMoreHoverImage = new ReadImageUrl(imageUrl.split(",")[4], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        ReadImageUrl readFlagImage = new ReadImageUrl(imageUrl.split(",")[5], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        ReadImageUrl readLoginImage = new ReadImageUrl(imageUrl.split(",")[6], "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb");
        readHomeImage.writeImage("homeimage.png");
        readStationImage.writeImage("stationimage.png");
        readMoreImage.writeImage("moreimage.png");
        readMoreHoverImage.writeImage("morehoverimage.png");
        readFlagImage.writeImage("flagimage.png");
        readShellImage.writeImage("shellimage.png");
        readLoginImage.writeImage("loginimage.png");
         return true;
    }

    @Override
    public boolean saveImageToFile() {
        String basePath = "C:\\Users\\SőregiKrisztián\\Pictures\\shell_uat_gb";
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
            if (!images.get(i).writeImage(filenames.get(i))) {
                allSuccess.set(false);
            }
        });

        return allSuccess.get();
    }

    @Override
    public List<String[]> valuesList(){

        List<String[]> resultList = new LinkedList<>();

        By[] byArray = {BUTTON_HOME, BUTTON_STATION, BUTTON_MORE, BUTTON_FLAG};
        for(int i = 0; i < byArray.length; i++){
            WebElement webElement = driver.findElement(byArray[i]);
            String fontFamily = webElement.getCssValue("font-family");
            String fontSize = webElement.getCssValue("font-size");
            //TODO COLOR
            String color = webElement.getCssValue("color");

            resultList.add(new String[]{fontFamily, fontSize});
        }

        return resultList;
    }

    @Override
    public Integer[] getPaddings(){
        String[] paddingStringArray = new String[18];
        int counter = 2;

        paddingStringArray[0] = driver.findElement(HEADER).findElement(By.xpath("./parent::div")).getCssValue("padding-top");

        paddingStringArray[1] = driver.findElement(HEADER).findElement(By.xpath("./parent::div")).getCssValue("padding-bottom");

        By[] menuOptions = new By[]{BUTTON_MORE, BUTTON_STATION, BUTTON_FLAG, BUTTON_HOME};
        for(int i = 0; i < menuOptions.length; i++){
            WebElement temporaryElement = driver.findElement(menuOptions[i]).findElement(By.xpath("./parent::div"));
            for(int j = 0; j < paddingNamesArray.length; j++){
                paddingStringArray[counter] = temporaryElement.getCssValue(paddingNamesArray[j]);
                counter++;
            }
        }

        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        By[] xpathElements = new By[]{LOGO_SHELL, LOGO_HOME, LOGO_STATION_LOCATOR, LOGO_MORE, LOGO_MORE_HOVER, LOGO_FLAG, LOGO_LOGIN, HEADER, MORE, BUTTON_HOME,
                BUTTON_STATION, BUTTON_MORE, BUTTON_FLAG};
        Arrays.stream(xpathElements).forEach(e -> locationsList.add(driver.findElement(e).getLocation()));
    }
    @Override
    public void createMapFromLocationsList(){
        accumulateMapBasedOnLocations();
    }
    @Override
    public LinkedHashMap<Integer, Integer[]> absoluteLocations() {
        return getMapOfPoints();
    }

    @Override
    public LinkedHashMap<Integer, Integer[]> relativeLocations(){
        return calculateDifference(getMapOfPoints());
    }

    public int countHeaderElements() {
        headerRows = driver.findElement(HEADER).findElements(By.xpath("./li"));
        return headerRows.size();

    }

    public String[] verifyHeaderElements() {

        String[] headerElements = new String[6];
        int i = 0;

        headerRows = driver.findElement(HEADER).findElements(By.xpath("./li"));


        for (WebElement row : headerRows) {
            if (!row.getText().equals("")) {
                headerElements[i] = row.getText();
            } else if (row.getAttribute("id").equals("logo_cell")) {
                headerElements[i] = row.findElement(By.xpath("./div/a")).getAttribute("title");
            } else if (row.getAttribute("id").equals("fp_login_cell")) {
                headerElements[i] = row.findElement(By.xpath("./div/a")).getAttribute("id");
            }
            i++;
        }

        return headerElements;
    }


    public boolean clickOnHeaderElements() throws InterruptedException {

        headerRows = driver.findElement(HEADER).findElements(By.xpath("./li"));

        boolean isValidUrl = true;
        int count = 0;

        for (int i = 0; i < headerRows.size(); i++) {
            headerRows = driver.findElement(HEADER).findElements(By.xpath("./li"));

            if (headerRows.get(i).isDisplayed()) {
                headerRows.get(i).click();
                Thread.sleep(3000);
                String currentUrl = driver.getCurrentUrl();
                switch (count) {
                    case 0, 1:
                        if (!currentUrl.equals("https://shelluatpoints.loyaltyondemand.club/en-gb/")) {
                            isValidUrl = false;
                        }
                        count++;
                        break;
                    case 2, 3, 4:
                        if (!currentUrl.equals("https://shelluatpoints.loyaltyondemand.club/en-gb/station-locator")) {
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

        moreRows = driver.findElement(MORE).findElements(By.xpath("./li//label"));


        for (WebElement row : moreRows) {
            moreElements[i] = row.getAttribute("innerText");
            i++;
        }

        return moreElements;
    }



    public boolean clickMoreElements() throws InterruptedException {


        moreRows = driver.findElement(MORE).findElements(By.xpath("./li/div"));

        boolean isValidUrl = true;
        int count = 0;


        for (int i = 0; i < moreRows.size(); i++) {

            driver.findElement(BUTTON_MORE).click();
            driver.findElement(BUTTON_MORE).click();

            moreRows = driver.findElement(MORE).findElements(By.xpath("./li/div"));


            moreRows.get(i).click();
            Thread.sleep(3000);
            String currentUrl = driver.getCurrentUrl();
            switch (count) {
                case 0, 2:
                    System.out.println(currentUrl);
                    if (!currentUrl.equals("https://support.shell.com/hc/en-gb")) {
                        isValidUrl = false;
                    }
                    break;
                case 1:
                    System.out.println(currentUrl);
                    if (!currentUrl.equals("https://shelluatpoints.loyaltyondemand.club/en-gb/contact-us")) {
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
