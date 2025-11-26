package tests.qa2pl.login.parallel_logged_in;

import org.example.pages.qa2pl.login.basket.BasketPage;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import tests.WebTestTH;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class ParallelQA2PLBasket extends WebTestTH {

    private BasketPage basketPage;
    protected boolean isTestSuiteRun = false;

    SoftAssert softAssert;

    /*public ParallelQA2PLBasket(){

    }*/

    public ParallelQA2PLBasket(SoftAssert softAssert){
        this.softAssert = softAssert;
    }

    protected void uniqueLoginQA2PLIN() {
        /*
            driver.findElement(By.id("uuid")).clear();
            driver.findElement(By.id("uuid")).sendKeys("6c4bd68f-bef4-3655-92d8-4e65160d7202");
            driver.findElement(By.xpath("//input[@name=\"return\"]")).click();*/
        if (!isTestSuiteRun) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uuid")));

                WebElement uuidField = driver.findElement(By.id("uuid"));
                uuidField.clear();
                uuidField.sendKeys("6c4bd68f-bef4-3655-92d8-4e65160d7202");

                driver.findElement(By.xpath("//input[@name=\"return\"]")).click();
            } catch (TimeoutException e) {
                System.out.println("UUID field was not found within the timeout: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void initializeSite() {
        Instant startTime = Instant.now();

        basketPage = new BasketPage(driver);

        navigateTo(basketPage.getUrl());
        //softAssert = new SoftAssert();

        if (!isTestSuiteRun) {
            uniqueLoginQA2PLIN();
        }

        basketPage.goToBasketPage();
    }

    @Test
    public void testInteractiveBasketPageElementsQA2PL() throws InterruptedException {

        initializeSite();

        HashMap<String, HashMap<Boolean, HashMap<Boolean, Integer>>> actualMap = basketPage.interactiveBasketPageElements();
        HashMap<Boolean, HashMap<Boolean, Integer>> integerBooleanHashMap = actualMap.get("https://lodqa2.wonderline.eu/pl-pl/catalog");

        Integer customerPointBalance;
        boolean isPointBalanceZero;
        if(integerBooleanHashMap.get(true) != null){
            System.out.println("If path invoked");

            HashMap<Boolean, Integer> pointBalanceMapAboveZero = integerBooleanHashMap.get(true);
            customerPointBalance = pointBalanceMapAboveZero.get(true);
            isPointBalanceZero = false;
        } else {
            System.out.println("Else path invoked");

            HashMap<Boolean, Integer> pointBalanceIsZero = integerBooleanHashMap.get(false);
            customerPointBalance = pointBalanceIsZero.get(false);
            if(customerPointBalance == null){
                customerPointBalance = 0;
            }
            isPointBalanceZero = true;
        }

        boolean assertionBoolean = false;
        if(customerPointBalance > 0 && !isPointBalanceZero){
            assertionBoolean = true;
        }
        if(customerPointBalance == 0 && isPointBalanceZero){
            assertionBoolean = true;
        }

        System.out.println("Customer's point balance is: " + customerPointBalance);
        System.out.println("Customer has 0 points: " + isPointBalanceZero);

        softAssert.assertTrue(assertionBoolean);
        //softAssert.assertAll();
    }
}
