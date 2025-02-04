package DemoTestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GeoLocation_SeleniumGrid {
    public WebDriver driver;
    private String username = ""; // your username
    private String accessKey = ""; // your access key
    private String hub = "@hub.lambdatest.com/wd/hub"; // LambdaTest Hub URL

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("build", "3.1");
        ltOptions.put("name", "Test GeoLocation");
        ltOptions.put("geoLocation", "IN");
        ltOptions.put("platformName", "Windows 11");
        ltOptions.put("browserVersion", "132.0");
        ltOptions.put("network", true);
        ltOptions.put("console", true);
        ltOptions.put("visual", true);

        options.setCapability("lt:options", ltOptions);

        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + hub), options);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }

        driver.get("https://where-am-i.org/");
    }

    @Test
    public void testGeoLocation() {
        WebElement address = driver.findElement(By.id("address"));
        System.out.println("Address: " + address.getText());
    }
}
