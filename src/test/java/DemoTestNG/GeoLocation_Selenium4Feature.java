package DemoTestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @Test
    public void mockGeoLocation_executeCDPCommand() {
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", 25.1972);
        coordinates.put("longitude", 55.2744);
        coordinates.put("accuracy", 1);

        // Using Chrome DevTools Protocol (CDP) to mock geolocation
        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);

        driver.get("https://where-am-i.org/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
