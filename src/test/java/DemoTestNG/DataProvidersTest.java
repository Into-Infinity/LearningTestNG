package DemoTestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class DataProvidersTest {
    private WebDriver driver;
    private static final String URL_AJAX_FORM = "https://www.lambdatest.com/selenium-playground/ajax-form-submit-demo";
    private static final String URL_INPUT_FORM = "https://www.lambdatest.com/selenium-playground/input-form-demo";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрытие браузера
        }
    }

    @DataProvider
    public Object[][] ajaxData() {
        return new Object[][]{
                {"Joe Doe", "Tester Joe Doe"},
                {"Jane Doe", "Tester Jane Doe"}
        };
    }

    @Test(dataProvider = "ajaxData")
    public void testAjaxForm(String name, String comment) {
        driver.get(URL_AJAX_FORM);
        driver.findElement(By.id("title")).sendKeys(name);
        driver.findElement(By.id("description")).sendKeys(comment);
        driver.findElement(By.id("btn-submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-control")));

        String actualName = driver.findElement(By.id("title")).getAttribute("value");
        String actualComment = driver.findElement(By.id("description")).getAttribute("value");
        String confirmationMessage = driver.findElement(By.id("submit-control")).getText();

        Assert.assertEquals(actualName, name,
                "\n \"Name field does not match the expected value! \n");
        Assert.assertEquals(actualComment, comment,
                "\n Comment field does not match the expected value! \n");
        Assert.assertEquals(confirmationMessage, "Ajax Request is Processing!",
                "\n Confirmation message is incorrect! \n");
    }

    @Test(dataProviderClass = DataProviderOnly.class, dataProvider = "input-provider")
    public void testInputFields(String name, String email, int inputNumber) {
        driver.get(URL_INPUT_FORM);
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("inputEmail4")).sendKeys(email);

        String actualName = driver.findElement(By.id("name")).getAttribute("value");
        String actualEmail = driver.findElement(By.id("inputEmail4")).getAttribute("value");

        Assert.assertEquals(actualName, name,
                "\n Name field value does not match the expected value! \n");
        Assert.assertEquals(actualEmail, email,
                " \n Email field value does not match the expected value! \n");
    }
}
