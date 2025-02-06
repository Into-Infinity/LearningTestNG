package DemoTestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GeoLocation_SeleniumGrid {
    private WebDriver driver;
    private static final String HUB_URL = "https://%s:%s@hub.lambdatest.com/wd/hub";

    private final String username;
    private final String accessKey;

    public GeoLocation_SeleniumGrid() {
        username = ConfigReader.getProperty("LT_USERNAME");
        accessKey = ConfigReader.getProperty("LT_ACCESS_KEY");

        if (username == null || accessKey == null || username.isEmpty() || accessKey.isEmpty()) {
            throw new IllegalStateException("LT_USERNAME или LT_ACCESS_KEY не заданы в config.properties");
        }
    }

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("build", "4.1");
        ltOptions.put("name", "Test GeoLocation");
        ltOptions.put("location", "IN");
        ltOptions.put("platformName", "Windows 11");
        ltOptions.put("browserVersion", "132.0");
        ltOptions.put("network", true);
        ltOptions.put("console", true);
        ltOptions.put("visual", true);

        options.setCapability("lt:options", ltOptions);

        try {
            driver = new RemoteWebDriver(new URL(String.format(HUB_URL, username, accessKey)), options);
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Error in URL LambdaTest", exception);
        }

        driver.get("https://where-am-i.org/");
    }

    @Test
    public void testGeoLocation() {
        WebElement address = driver.findElement(By.id("address"));
        String actualAddress = address.getText();
        String expectedAddress = "TU Dublin, Dublin 15, Ireland";

        Assert.assertEquals(actualAddress, expectedAddress,
                "GeoLocation does not match expected location!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
