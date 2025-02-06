package DemoTestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class GeoLocation_Selenium4Feature {
    private ChromeDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private void mockGeoLocation(double latitude, double longitude, int accuracy) {
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", latitude);
        coordinates.put("longitude", longitude);
        coordinates.put("accuracy", accuracy);

        // Using Chrome DevTools Protocol (CDP) to mock geolocation
        ((ChromeDriver) driver).executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
    }

    @Test
    public void mockGeoLocation_executeCDPCommand() {
        mockGeoLocation(25.1972, 55.2744, 1);
        driver.get("https://where-am-i.org/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String actualLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("address"))).getText();

        Assert.assertEquals(actualLocation,
                "Sheikh Mohammed bin Rashid Boulevard 1, Dubai, United Arab Emirates",
                "GeoLocation does not match expected location!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
