package org.example.pages.qa2pl.login.transactions;

import org.example.interfaces.SaveImage;
import org.example.utils.AbstractGetPaddingSupporter;
import org.example.utils.IntegerPattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TransactionPage extends AbstractGetPaddingSupporter implements SaveImage {


    private WebDriver driver;

    public TransactionPage(WebDriver driver){
        this.driver = driver;
    }

    private final String url = "https://lodqa2.wonderline.eu/pl-pl/account/main";

    private final By WHOLE_TRANSACTION_PAGE = By.id("simple_content");
    private final By TRANSACTION_LINK_BUTTON = By.xpath("//a[@class=\"futura-18 lfTrxUnderLink lfFutura16 futura-heavy mobile-spacing-15 lfDarkBlueText\"]");
    private final By TRANSACTION_TABLE_ROWS = By.xpath("//table[@id=\"transaction_table\"]//tr");
    private By TRANSACTION_TYPES = By.xpath("//table[@id=\"transaction_table\"]//tr//div[@id=\"new_transaction_station\"]");
    private final By FILTER_BUTTON = By.xpath("//div[@class=\"trxNewPdfButton trxFilterToggleButton float-left box-sizing\"]");
    private final By FILTER_DROPDOWN_BUTTON = By.xpath("//span[@id=\"categorySelectSpan\"]");
    private final By FILTER_LABELS = By.xpath("//div[@class=\"indent-15\"]");
    private final By FILTER_CHECKBOXES_UNCHECKED = By.xpath("//div[@class=\"lfCheckBox\"]");
    private final By FILTER_CHECKBOXES_CHECKED = By.xpath("//div[@class=\"lfCheckBox checked\"]");
    private final By FILTER_OK_BUTTON = By.xpath("//input[@id=\"categorySelectContainerButton\"]");
    private final By DATE_FROM_TO = By.id("new_filter_trx_from_to_container");
    private final By SECOND_DATE_OF_MONTH = By.xpath("//a[@data-date=\"2\"]");
    private final By CLEAR_FILTER_BUTTON = By.xpath("//input[@id=\"transaction_clear_filter_button\"]");
    private final By SELECTED_CATEGORY_LABEL = By.xpath("//span[@id=\"categorySelectContainerLabel\"]");
    private final By DOWNLOAD_PDF_BUTTON = By.xpath("//a[@id=\"new_filter_new_pdf_link\"]");
    private final By TRANSACTION_PAGE_TITLE = By.id("carousel_title");
    private final By TRANSACTION_TABLE_HEADER = By.id("trxPageHeaderDataTable");
    private final By TRANSACTION_TABLE_DATA = By.id("transaction_parent");
    private final By CONCATENATED_ELEMENTS = By.xpath("//div[@class=\"container no-print\"] |" +
            " //div[@class=\"mobile-display-table no-print container mobile-width100\"] | //div[@class=\"container desktop-only\"]");

    public By getCONCATENATED_ELEMENTS(){
        return this.CONCATENATED_ELEMENTS;
    }

    public List<WebElement> getConcatenatedWebElements(){
        List<WebElement> webElementList = new ArrayList<>();
        webElementList.add(driver.findElement(By.id("back_button_carousel_container")));
        webElementList.add(driver.findElement(By.xpath("//div[@class=\"container no-print\"]")));
        webElementList.add(driver.findElement(By.xpath("//div[@class=\"mobile-display-table no-print container mobile-width100\"]")));
        webElementList.add(driver.findElement(By.xpath("//div[@class=\"container desktop-only\"]")));

        return webElementList;
    }
    public By getTRANSACTION_DIV(){
        return this.WHOLE_TRANSACTION_PAGE;
    }
    public By getDOWNLOAD_PDF_BUTTON(){
        return this.DOWNLOAD_PDF_BUTTON;
    }

    public String getUrl(){
        return  this.url;
    }
    public String goToTransactionPage(){
        driver.findElement(TRANSACTION_LINK_BUTTON).click();
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
        By[] byArray = {TRANSACTION_PAGE_TITLE, FILTER_BUTTON, FILTER_LABELS, TRANSACTION_TABLE_HEADER};
        AtomicReference<Integer> titleRowCounter = new AtomicReference<>(0);
        Stream<Serializable> propertiesStream = Arrays.stream(byArray)
                .flatMap(by -> {
                    try {
                        if (by == FILTER_LABELS) {

                            List<WebElement> webElementList = driver.findElements(by);
                            webElementList.add(driver.findElement(FILTER_OK_BUTTON));

                            driver.findElement(FILTER_BUTTON).click();
                            driver.findElement(FILTER_DROPDOWN_BUTTON).click();
                            driver.findElement(FILTER_CHECKBOXES_UNCHECKED).click();
                            webElementList.add(driver.findElement(CLEAR_FILTER_BUTTON));


                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });

                        } else if (by == TRANSACTION_PAGE_TITLE || by == FILTER_BUTTON) {

                            WebElement webElement = driver.findElement(by);
                            String fontFamily = webElement.getCssValue("font-family");
                            System.out.println("The font family is: " + fontFamily);
                            String fontSize = webElement.getCssValue("font-size");
                            System.out.println("The font size is: " + fontSize);
                            String color = webElement.getCssValue("color");
                            System.out.println("The font color is: " + color);

                            return Stream.of(new String[]{fontFamily, fontSize, color});

                        } else if (by == TRANSACTION_TABLE_HEADER){
                            List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//td"));

                            return webElementList.stream().map(actualWebElement -> {
                                String fontFamily = actualWebElement.getCssValue("font-family");
                                String fontSize = actualWebElement.getCssValue("font-size");
                                String color = actualWebElement.getCssValue("color");
                                return new String[]{fontFamily, fontSize, color};
                            });
                        } else if (by == TRANSACTION_TABLE_DATA){
                            List<WebElement> webElementList = driver.findElement(by).findElements(By.xpath(".//div"));

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
        List<WebElement> accumulatedElementsList = driver.findElement(WHOLE_TRANSACTION_PAGE).findElements(By.xpath(".//td"));
        accumulatedElementsList.addAll(driver.findElement(WHOLE_TRANSACTION_PAGE).findElements(By.xpath(".//span")));
        accumulatedElementsList.addAll(driver.findElement(WHOLE_TRANSACTION_PAGE).findElements(By.xpath(".//div")).subList(36,41));
        accumulatedElementsList.addAll(driver.findElement(WHOLE_TRANSACTION_PAGE).findElements(By.xpath(".//div"))
                .subList(100, driver.findElement(WHOLE_TRANSACTION_PAGE).findElements(By.xpath(".//div")).size()));

        accumulatePaddingsMarginsOfByList(accumulatedElementsList);

        paddingStringArray = resizeArrayAndRemoveNulls(paddingStringArray);
        return new IntegerPattern(paddingStringArray, paddingStringArray.length).getMatchedInteger();
    }

    @Override
    public void setLocationsList() {
        locationsList.add(driver.findElement(TRANSACTION_PAGE_TITLE).getLocation());
        locationsList.add(driver.findElement(DOWNLOAD_PDF_BUTTON).getLocation());
        locationsList.add(driver.findElement(FILTER_BUTTON).getLocation());
        driver.findElement(FILTER_BUTTON).click();
        driver.findElement(FILTER_DROPDOWN_BUTTON).click();

        /*List<WebElement> filterLabelList = driver.findElements(FILTER_LABELS);
        WebElement[] filterLabelArray = filterLabelList.toArray(WebElement[]::new);

        Arrays.stream(filterLabelArray).forEach(e -> {
            locationsList.add(e.getLocation());
        });*/

        /*List<WebElement> filterCheckBoxList = driver.findElements(FILTER_CHECKBOXES_UNCHECKED);
        WebElement[] filterCheckBoxArray = filterCheckBoxList.toArray(WebElement[]::new);

        Arrays.stream(filterCheckBoxArray).forEach(e -> {
            locationsList.add(e.getLocation());
        });*/

        driver.findElement(FILTER_CHECKBOXES_UNCHECKED).click();
        locationsList.add(driver.findElement(CLEAR_FILTER_BUTTON).getLocation());

        List<WebElement> tableRowsList = driver.findElement(TRANSACTION_TABLE_ROWS).findElements(By.xpath("./td/div/div/div/div"));
        List<WebElement> cleanedTableRowList = IntStream.range(0, tableRowsList.size())
                .filter(i -> (i / 4) % 2 == 0)
                .mapToObj(tableRowsList::get)
                .collect(Collectors.toList());
        System.out.println("The size of the tablerow list is: " + tableRowsList.size());

        System.out.println("The size of the cleaned tablerow list is: " + cleanedTableRowList.size());
        WebElement[] tableRowsArray = cleanedTableRowList.toArray(WebElement[]::new);

        Arrays.stream(tableRowsArray).forEach(e -> {
            locationsList.add(e.getLocation());
        });

        driver.findElement(TRANSACTION_TABLE_HEADER)
                .findElements(By.xpath(".//td"))
                .stream()
                .forEach(e -> locationsList.add(e.getLocation()));


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

    public boolean transactionFilter() throws InterruptedException {
        AtomicBoolean isOnlyFilteredLabelsVisible = new AtomicBoolean(true);

        driver.findElement(FILTER_BUTTON).click();
        IntStream.range(0,2).forEach(i -> {
            driver.findElement(FILTER_DROPDOWN_BUTTON).click();
            if(i > 0){
                driver.findElement(FILTER_CHECKBOXES_CHECKED).click();
            }
            System.out.println("The number of the checkboxes: " + driver.findElements(FILTER_CHECKBOXES_UNCHECKED).size());
            driver.findElements(FILTER_CHECKBOXES_UNCHECKED).get(i).click();
            String selectedFilter = driver.findElements(FILTER_LABELS).get(i).getText();
            System.out.println("The selected filter is: " + selectedFilter);
            driver.findElement(FILTER_OK_BUTTON).click();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            TRANSACTION_TYPES = By.xpath("//table[@id=\"transaction_table\"]//tr//div[@id=\"new_transaction_station\"]");
            List<WebElement> transactionTypeRowsList = driver.findElements(TRANSACTION_TYPES);
            IntStream.range(0, transactionTypeRowsList.size()).forEach(j -> {
                System.out.println("Transaction type in table row is: " + transactionTypeRowsList.get(j).getText());
                if(!transactionTypeRowsList.get(j).getText().equalsIgnoreCase(selectedFilter)){
                    isOnlyFilteredLabelsVisible.set(false);
                }
            });
        });

        driver.findElement(CLEAR_FILTER_BUTTON).click();
        if(!driver.findElement(SELECTED_CATEGORY_LABEL).getText().equals("Wszystkie transakcje")){
            isOnlyFilteredLabelsVisible.set(false);
        }

        return isOnlyFilteredLabelsVisible.get();
    }

    public boolean transactionFilterVisualisation(){
        AtomicBoolean isOnlyFilteredLabelsVisible = new AtomicBoolean(true);

        driver.findElement(FILTER_BUTTON).click();
        driver.findElement(FILTER_DROPDOWN_BUTTON).click();
        driver.findElements(FILTER_CHECKBOXES_UNCHECKED).getFirst().click();
        driver.findElement(DATE_FROM_TO).findElements(By.xpath(".//input")).getFirst().click();
        driver.findElement(SECOND_DATE_OF_MONTH).click();
        driver.findElement(FILTER_DROPDOWN_BUTTON).click();
        driver.findElement(DATE_FROM_TO).findElements(By.xpath(".//input")).getLast().click();

        return true;

    }

    public boolean clickDownloadPdf(){
        driver.findElement(DOWNLOAD_PDF_BUTTON).click();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());

        driver.switchTo().window(newTab.get(1));
        String pdfUrl = driver.getCurrentUrl();

        //driver.close();
        return  pdfUrl.equals("https://lodqa2.wonderline.eu/pl-pl/account/transactions/pdf");
    }



}
