package tests.qa2pl.ide_adaptation;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import tests.WebTestTH;

public class IdeTest extends WebTestTH {


    @Test
    public void testOfferActivation() {
        driver.get("https://lodqa2.wonderline.eu/lod-auth/login");

        // Login
        driver.findElement(By.id("username")).sendKeys("WLGBKSO1");
        driver.findElement(By.id("password")).sendKeys("bbBB77!!");
        driver.findElement(By.cssSelector("input:nth-child(1)")).click();

        // Search Customer
        driver.findElement(By.linkText("Search Customer")).click();
        WebElement customerIdField = driver.findElement(By.id("customerID"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='4585'; arguments[0].dispatchEvent(new Event('change'));", customerIdField);
        driver.findElement(By.xpath("//vaadin-button[contains(.,'Search')]")).click();
        driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'2911764648637')]")).click();

        // Navigate to Offers and Activate
        /*driver.findElement(By.xpath("//vaadin-tab[contains(.,'Offers')]")).click();
        driver.findElement(By.xpath("//vaadin-button[contains(.,'Activate')]")).click();
        driver.findElement(By.xpath("//vaadin-button[contains(.,'Yes')]")).click();*/

        // Search Another Customer
        js.executeScript("arguments[0].value='4586'; arguments[0].dispatchEvent(new Event('change'));", driver.findElement(By.id("customerID")));
        driver.findElement(By.xpath("//vaadin-button[contains(.,'Search')]")).click();
        driver.findElement(By.xpath("//vaadin-grid-cell-content[contains(.,'2916855197018')]")).click();
        driver.findElement(By.xpath("//vaadin-tab[contains(.,'Offers')]")).click();
        driver.findElement(By.xpath("//vaadin-button[contains(.,'Activate')]")).click();
    }
}
