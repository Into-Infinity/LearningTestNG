package DemoTestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssertionsTest {
    private WebDriver driver;
    private final SoftAssert softAssert = new SoftAssert();


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
    public void testSingleCheckbox() {
        driver.findElement(By.linkText("Checkbox Demo")).click();
        driver.findElement(By.id("isAgeSelected")).click();
        String actualMessage = driver.findElement(By.id("txtAge")).getText();

        // Hard assertion - stops executing our test script immediately after the failure.
        // Then move on to the next annotation.
        // It is best to use a Hard Assert if you are verifying only one result.
        Assert.assertEquals(actualMessage, "Checked");
    }

    @Test
    public void testRadioButtons() {
        driver.findElement(By.linkText("Radio Buttons Demo")).click();
        driver.findElement(By.xpath("//input[@value='Other']")).click();
        driver.findElement(By.xpath("//input[@value='5 - 15']")).click();
        driver.findElement(By.xpath("//button[text()='Get values']")).click();
        String actualGender = driver.findElement(By.cssSelector(".genderbutton")).getText();
        String actualAgeGroup = driver.findElement(By.cssSelector(".groupradiobutton")).getText();

        // Soft assertion - continues execution after a failure.
        // It is best to use a Soft Assert if you are verifying more than one result.
        softAssert.assertEquals(actualGender, "Other",
                "\n Actual Gender Is Not Correct \n");
        softAssert.assertTrue(actualAgeGroup.contains("5 - 15"),
                "\n Actual Age Group Is Not Correct \n");
        // If you use softAssert, remember to always add assertAll() at the end.
        softAssert.assertAll("\n Optional Message \n");
    }
}
