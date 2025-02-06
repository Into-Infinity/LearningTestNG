package DemoTestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParallelTesting2 {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test3_BootstrapAlerts() {
        driver.get("https://www.lambdatest.com/selenium-playground/bootstrap-alert-messages-demo");
        System.out.println(Thread.currentThread().getId() + ": Bootstrap Alert Message Page");
    }

    @Test
    public void test4_DragAndDropRangeSlider() {
        driver.get("https://www.lambdatest.com/selenium-playground/drag-and-drop-demo");
        System.out.println(Thread.currentThread().getId() + ": Drag And Drop Range Slider Page");
    }
}
