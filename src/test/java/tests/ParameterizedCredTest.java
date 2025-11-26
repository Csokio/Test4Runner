package tests;

import credentials.ParameterizedCred;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
//TODO Change parameters to email and password
@RunWith(Parameterized.class)
public class ParameterizedCredTest {
    protected String countryIdentifier;

    protected String inputMobile;
    protected String inputPassword;
    protected ParameterizedCred parameterizedCred;

    protected WebDriver webDriver;

    protected String url;

    public static AtomicInteger validateInvocationCount = new AtomicInteger(-1);

    public void setUrl(String url){
        this.url = url;
    }

    private final String password = "aaAA11??";


    @Before
    public void initialize() {
        //countryIdentifier.put(0, "TH");
        //countryIdentifier.put(1, "NL");

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        //options.addArguments("--headless");
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-search-engine-choice-screen");

        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        parameterizedCred = new ParameterizedCred(webDriver);
    }

    public ParameterizedCredTest( String countryIdentifier, String inputMobile, String inputPassword) {
        this.countryIdentifier = countryIdentifier;
        this.inputMobile = inputMobile;
        this.inputPassword = inputPassword;

        webDriver = WebTestTH.driver;
    }

    @Parameterized.Parameters
    public static Collection<String[]> emailMobilePasswords() {

        HashMap<Integer, String> thMap = new HashMap<>(){{
            put(0, "TH");
        }};
        HashMap<Integer, String> nlMap = new HashMap<>(){{
            put(1, "NL");
        }};


        return Arrays.asList(new String[][] {
                { thMap.get(0), "333001003", "aaAA11??"},
                { thMap.get(0), "333001006", "aaAA11??"},
                { thMap.get(0), "333001009", "aaAA11??"},
                { nlMap.get(1), "tesztelok+nl12@office.wonderline.eu", "aaAA11??24"},
                { nlMap.get(1), "tesztelok+nl16@office.wonderline.eu" , "aaAA11??"},
                { nlMap.get(1), "tesztelok+nl19@office.wonderline.eu" , "aaAA11??"}
        });

        /*List<String[]> credentialsList = Arrays.asList(
                new String[][]{
                        {thMap.get(0), "333001003", "aaAA11??"},
                        {thMap.get(0), "333001006", "aaAA11??"},
                        {thMap.get(0), "333001009", "aaAA11??"},
                        {nlMap.get(1), "tesztelok+nl12@office.wonderline.eu", "aaAA11??"},
                        {nlMap.get(1), "tesztelok+nl16@office.wonderline.eu", "aaAA11??"},
                        {nlMap.get(1), "tesztelok+nl19@office.wonderline.eu", "aaAA11??"}
                });

        return credentialsList;*/

    }

    @Test
    public void testEmailPasswordChecker() throws InterruptedException {

        Assertions.assertTrue(parameterizedCred.validate("NL"));

        /*System.out.println("Parameterized Number is : " + inputNumber);
        assertEquals(expectedResult, primeNumberChecker.validate(inputNumber));*/
    }
}

