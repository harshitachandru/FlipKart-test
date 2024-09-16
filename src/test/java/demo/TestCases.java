package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.time.Duration;



// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Durations;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
    
    @Test
    public void testCase01() throws InterruptedException
    {
        driver.get("https://www.flipkart.com");
        System.out.println("TestCase01 Starts");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']")));
        Wrappers.movetotheelementtoclick(driver, searchbox);
        Wrappers.enterText(searchbox, "Washing Machine");
        Wrappers.pressenter(searchbox);
        Thread.sleep(2000);
        System.out.println("Wait 1");

        WebElement popularitysort = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='zg-M3Z' and text()='Popularity']")));
        popularitysort.click();
        Thread.sleep(2000);
        System.out.println("Wait 2");

        int lowercount = Wrappers.count(driver, By.xpath("//div[@class='XQDdHH']"));

        System.out.println("Items with rating less than or equal to 4 stars : " + lowercount);
        
        System.out.println("TestCase01 Ends");
        
    }

    @Test
    public void testCase02() throws InterruptedException
    {
        System.out.println("TestCase02 Starts");
        driver.get("https://www.flipkart.com");
        Thread.sleep(3000);
        System.out.println("Wait 1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']")));
        Wrappers.movetotheelementtoclick(driver, searchbox);
        Wrappers.enterText(searchbox, "iPhone");
        Wrappers.pressenter(searchbox);
        Thread.sleep(2000);
        System.out.println("Wait 2");

        List<WebElement> titlelocator = Wrappers.getElements(driver, By.xpath("//div[@class='KzDlHZ']"));
        List<WebElement> discountlocator = Wrappers.getElements(driver, By.xpath("//div[@class='UkUFwK']"));
        
        System.out.println("Number of titles: " + titlelocator.size());
        System.out.println("Number of discounts: " + discountlocator.size());

        Wrappers.printtitleanddiscount(titlelocator, discountlocator);
        System.out.println("TestCase02 Ends");
    }

    @Test
    public void testCase03() throws InterruptedException
    {
        System.out.println("TestCase03 Starts");
        driver.get("https://www.flipkart.com");
        Thread.sleep(3000);
        System.out.println("Wait 1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']")));
        Wrappers.movetotheelementtoclick(driver, searchbox);
        Wrappers.enterText(searchbox, "Coffee Mug");
        Wrappers.pressenter(searchbox);
        Thread.sleep(2000);
        System.out.println("Wait 2");

        WebElement fourstartandabove = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, '_6i1qKy') and contains(text(), '4') and contains(text(), 'above')]")));
        fourstartandabove.click();
        Thread.sleep(2000);
        System.out.println("Wait 3");

        By titletext = By.xpath("//a[@class='wjcEIp']");
        By imageURL = By.xpath("//img[@loading='eager']");

        By reviewlocator = By.xpath("//span[@class='Wphh3N']");

        List<Map<String, String>> topReviewedItems = Wrappers.getTopNReviewedItems(driver, reviewlocator, 5, titletext, imageURL);

        Wrappers.printTitlesAndImageURLs(topReviewedItems);
        //Wrappers.printTitlesAndImageURLs(Topreviewitems, titletext, imageURL);

        System.out.println("TestCase03 Ends");
    }



}