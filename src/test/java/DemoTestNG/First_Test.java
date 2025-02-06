package DemoTestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class First_Test {
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

    @Test(priority = 1)
    public void testTableSortAndSearch() {
        driver.findElement(By.linkText("Table Sort & Search")).click();
        driver.findElement(By.xpath("//div[@id='example_filter']//input[@type='search']"))
                .sendKeys("B. Greer");

        String actualText = driver.findElement(By.xpath("//td[contains(text(), 'B. Greer')]")).getText();
        Assert.assertEquals(actualText, "B. Greer");
    }

    @Test(priority = 2)
    public void testBootstrapDatePicker() {
        driver.findElement(By.linkText("Bootstrap Date Picker")).click();
        driver.findElement(By.id("birthday")).sendKeys("06/07/2008");

        String actualDate = driver.findElement(By.id("birthday")).getAttribute("value");
        Assert.assertEquals(actualDate, "2008-07-06");
    }
}
