 package org.example.pages.uatgb.logout;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractLocationManager;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

 public class FAQs extends AbstractLocationManager implements SaveImage {

    private WebDriver driver;
    public FAQs(WebDriver driver){
        this.driver = driver;
    }
    private final String url = "https://shelluatpoints.loyaltyondemand.club/en-gb/";


    private final By FAQ_CONTAINER = By.id("fpHomeFaqContainer");
    private final By FAQ_TITLE = By.id("fpHomeFaqTitle");
    private final By FAQ_QUESTIONS = By.xpath("//div[@class=\"fpHomeFaqHeadTitle\"]");
    private final By FAQ_ANSWERS = By.xpath("//div[@class=\"fpHomeFaqHeadTitle\"]/following::div[3]");

    private final By FAQ_TITLE_RM = By.xpath("//div[@class=\"faq-title\"]");
    private final By FAQ_TITLE_ANG_QA = By.xpath("//div[@class=\"faq-title\"]");
    private final By FAQ_QUESTIONS_1ST = By.xpath("//span[@class=\"mat-content ng-tns-c2622717266-4 mat-content-hide-toggle\"]");
    private final By FAQ_QUESTIONS_2ND = By.xpath("//span[@class=\"mat-content ng-tns-c2622717266-6 mat-content-hide-toggle\"]");
    private final By FAQ_QUESTIONS_1ST_ANG_QA = By.xpath("//app-faq-module[@class=\"ng-star-inserted\"]");
    private final By WELCOME_TITLE = By.id("fpHomeWelcomeTitle");

    private final LinkedList<Point> locationsList = new LinkedList<>();

    public By getFAQ_CONTAINER(){
        return this.FAQ_CONTAINER;
    }

     public String getUrl(){
         return this.url;
     }

     //TODO May use as interface for get locations
    public int getSizeOfLocationList(){
        return this.locationsList.size();
    }
     //TODO May use as interface for get locations
     public Integer[] getLocations(int index){
        return new Integer[]{locationsList.get(index).getX(), locationsList.get(index).getY()};
    }


     public void printPosition() throws InterruptedException {
        System.out.println(driver.findElement(FAQ_TITLE).getLocation());
        locationsList.add(driver.findElement(FAQ_TITLE).getLocation());
        System.out.println(driver.findElements(FAQ_QUESTIONS).get(0).getLocation());
        locationsList.add(driver.findElements(FAQ_QUESTIONS).get(0).getLocation());

        System.out.println(driver.findElement(WELCOME_TITLE).getLocation());
        System.out.println("-------------------------------------------------");


        driver.navigate().to("https://lodqa.wonderline.eu/en-th");
        System.out.println(driver.findElement(FAQ_TITLE_ANG_QA).getLocation());
        locationsList.add(driver.findElement(FAQ_TITLE_ANG_QA).getLocation());
        System.out.println(driver.findElements(FAQ_QUESTIONS_1ST_ANG_QA).get(0).getLocation());
        locationsList.add(driver.findElements(FAQ_QUESTIONS_1ST_ANG_QA).get(0).getLocation());

         //driver.findElement(FAQ_QUESTIONS_1ST).click();

        System.out.println("-------------------------------------------------");
        System.out.println("The size of locationList is: " + getSizeOfLocationList());

        Thread.sleep(2000);

        /*System.out.println(driver.findElement(FAQ_TITLE_RM).getLocation());
        System.out.println(driver.findElement(FAQ_QUESTIONS_2ND).getLocation());*/

    }

    //TODO May use as interface for get locations
     /*public LinkedHashMap<Integer, Integer[]> accumulateMapBasedOnLocations(){
         LinkedHashMap<Integer, Integer[]> temporaryMap = new LinkedHashMap<>();
         int index = 0;
         int  sizeOfLocationList = getSizeOfLocationList();
         if(sizeOfLocationList != 0){
             while (sizeOfLocationList != 0){
                 temporaryMap.put(index+1, getLocations(index++));
                 sizeOfLocationList--;
             }
         }
         System.out.println("The size of temporaryMap in accumulateMapBasedOnLocations method is: " + temporaryMap.size());
         return temporaryMap;
     }*/

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
        List<String[]> resultList = new LinkedList<>();

        //String[] xpathInsideCard = {"//div[@class=\"fpHomeHowItWorksCardTitle futura-bold\"]", "//div[@class=\"fpHomeHowItWorksCardText\"]"};

        WebElement webElement;
        WebElement expandedWebElement;
        //List<WebElement> webElementList;
        By[] byArray = {FAQ_TITLE, FAQ_QUESTIONS};
        System.out.println(byArray.length);
        for(int i = 0; i < byArray.length; i++){
            if(byArray[i] == FAQ_QUESTIONS){
                for(int j = 0; j < driver.findElements(FAQ_QUESTIONS).size(); j++){


                    webElement = driver.findElements(FAQ_QUESTIONS).get(j);
                    String fontFamily = webElement.getCssValue("font-family");
                    String fontSize = webElement.getCssValue("font-size");
                    String color = webElement.getCssValue("color");
                    resultList.add(new String[]{fontFamily, fontSize, color});
                    //webElement.click();


                    expandedWebElement = driver.findElements(By.xpath("//div[@class=\"fpHomeFaqHeadTitle\"]/following::div[3]")).get(j);
                    fontFamily = expandedWebElement.getCssValue("font-family");
                    fontSize = expandedWebElement.getCssValue("font-size");
                    color = expandedWebElement.getCssValue("color");
                    String display = expandedWebElement.getCssValue("display");
                    resultList.add(new String[]{fontFamily, fontSize, color, display});
                    }
                }
            else {
                webElement = driver.findElement(byArray[i]);

                String fontFamily = webElement.getCssValue("font-family");
                String fontSize = webElement.getCssValue("font-size");
                String color = webElement.getCssValue("color");
                resultList.add(new String[]{fontFamily, fontSize, color});
            }
        }
        return resultList;
    }


    @Override
    public Integer[] getPaddings() {
        String[] paddingStrArray = new String[30];

        By[] byArray = {FAQ_CONTAINER, FAQ_TITLE, FAQ_QUESTIONS, FAQ_ANSWERS};
        String padding;
        int counter = 0;

        for(int i = 0; i < byArray.length; i++){
            if(i == 0 || i == 2){
                if(driver.findElements(byArray[i]).size() < 2){
                    System.out.println("top lane:");

                    padding = driver.findElement(byArray[i]).getCssValue("padding-top");
                    System.out.println(padding);
                    paddingStrArray[counter++] = padding;
                    padding = driver.findElement(byArray[i]).getCssValue("padding-bottom");
                    paddingStrArray[counter++] = padding;
                    System.out.println(padding);

                }   else {
                    for(int j = 0; j < driver.findElements(byArray[i]).size(); j++){
                        System.out.println("questions lane:");

                        padding = driver.findElements(byArray[i]).get(j).getCssValue("padding-top");
                        paddingStrArray[counter++] = padding;
                        System.out.println(padding);

                        padding = driver.findElements(byArray[i]).get(j).getCssValue("padding-bottom");
                        paddingStrArray[counter++] = padding;
                        System.out.println(padding);

                        padding = driver.findElements(byArray[i]).get(j).getCssValue("margin-right");
                        paddingStrArray[counter++] = padding;
                        System.out.println(padding);

                    }
                }
            } else {
                if(driver.findElements(byArray[i]).size() < 2 ){
                    System.out.println("title lane:");

                    padding = driver.findElement(byArray[i]).getCssValue("margin-bottom");
                    paddingStrArray[counter++] = padding;
                    System.out.println(padding);

                }   else {
                    for(int j = 0; j < driver.findElements(byArray[i]).size(); j++){
                        System.out.println("answer lane:");

                        padding = driver.findElements(byArray[i]).get(j).getCssValue("padding-bottom");
                        paddingStrArray[counter++] = padding;
                        System.out.println(padding);

                    }
                }
            }
        }
        System.out.println(counter);
        String[] resizedPaddingStrArr = new String[counter];

        IntStream.range(0, counter).forEach(i -> resizedPaddingStrArr[i] = paddingStrArray[i]);

        return new IntegerPattern(resizedPaddingStrArr, resizedPaddingStrArr.length).getMatchedInteger();
    }

     @Override
     public void setLocationsList() {
        By[] xpathElements = new By[]{FAQ_CONTAINER, FAQ_TITLE, FAQ_QUESTIONS, FAQ_ANSWERS};
         Arrays.stream(xpathElements).forEach(e -> {
             if(driver.findElements(e).size() > 1){
                 for(int i = 0; i < driver.findElements(e).size(); i++){
                     if(e == FAQ_ANSWERS){
                         driver.findElements(FAQ_QUESTIONS).get(i).click();
                         locationsList.add(driver.findElements(e).get(i).getLocation());
                         driver.findElements(FAQ_QUESTIONS).get(i).click();
                         try {
                             Thread.sleep(2000);
                         } catch (InterruptedException ex) {
                             throw new RuntimeException(ex);
                         }
                     }  else {
                         locationsList.add(driver.findElements(e).get(i).getLocation());
                     }
                 }
             } else {
                 locationsList.add(driver.findElement(e).getLocation());
             }
         });
     }

     @Override
     public void createMapFromLocationsList() {
        LinkedHashMap<Integer, Integer[]> temporaryMap = new LinkedHashMap<>();
        int keyIndex = 1;
        int mapSize = locationsList.size();
        while (mapSize != 0){
            temporaryMap.put(keyIndex++, getLocations(keyIndex-2));
            mapSize--;
        }
        setMapOfPointsFAQs(temporaryMap);
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
