package DemoTestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class DynamicWaits {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.lambdatest.com/selenium-playground/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testExplicitWait() {
        driver.findElement(By.linkText("Dynamic Data Loading")).click();
        driver.findElement(By.id("save")).click();

        By image = By.xpath("//div[@id='loading']//img");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(image));

        boolean isImageDisplayed = driver.findElement(image).isDisplayed();
        Assert.assertTrue(isImageDisplayed, "\n Image Is Not Displayed In The AUT \n");
    }

    @Test
    public void testFluentWait() {
        driver.findElement(By.linkText("JQuery Download Progress bars")).click();
        driver.findElement(By.id("downloadButton")).click();

        // Waiting 30 seconds for an element to be present on the page, checking
        // for its presence once every 100 Milliseconds.
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(driver -> {
            WebElement progress = driver.findElement(By.xpath("//div[@id='dialog']//div[@class='progress-label']"));
            String progressBarText = progress.getText();

            if ("Complete!".equals(progressBarText)) {
                System.out.println("Progress Is Complete!");
                return progress;
            } else {
                System.out.println(progressBarText);
                return null;
            }
        });
    }

    @Test
    public void testImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://the-internet.herokuapp.com/dynamic_loading");

        driver.findElement(By.linkText("Example 2: Element rendered after the fact")).click();
        driver.findElement(By.xpath("//div[@id='start']/button")).click();

        By helloWorld = By.xpath("//div[@id='finish']/h4[text()='Hello World!']");
        String actualMessage = driver.findElement(helloWorld).getText();

        Assert.assertEquals(actualMessage, "Hello World!",
                "\n Message Is Not Hello World! \n");
    }
}
