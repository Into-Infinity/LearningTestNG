package DemoTestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CrossBrowserTesting {
    public RemoteWebDriver driver;
    private String username = ""; // your username
    private String accessKey = ""; // your access key
    private String hub = "@hub.lambdatest.com/wd/hub"; // LambdaTest Hub URL

    @Parameters(value = {"Browser", "Version", "Platform"})
    @BeforeMethod
    public void setUp(String browser, String version, String platform) {
        MutableCapabilities options;

        if (browser.equalsIgnoreCase("chrome")) {
            options = new ChromeOptions();
        } else if (browser.equalsIgnoreCase("edge")) {
            options = new EdgeOptions();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Selenium 4 no longer supports the deprecated JSON Wire Protocol and requires the W3C WebDriver Protocol.
//        options.setCapability("build", "2.1");
//        options.setCapability("name", "Cross Browser Testing");
//        options.setCapability("browserName", browser);
//        options.setCapability("version", version);
//        options.setCapability("platform", platform);
//        options.setCapability("network", true);
//        options.setCapability("console", true);
//        options.setCapability("visual", true);

        // W3C-compliant parameters:
        // browserName is not needed - it's automatically determined from EdgeOptions or ChromeOptions.
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("build", "2.1");
        ltOptions.put("name", "Cross Browser Testing");
        ltOptions.put("platformName", platform);
        ltOptions.put("browserVersion", version);
        ltOptions.put("network", true);
        ltOptions.put("console", true);
        ltOptions.put("visual", true);

        // Add parameters to Selenium-compatible container
        options.setCapability("lt:options", ltOptions);

        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + hub), options);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        driver.get("https://www.lambdatest.com/selenium-playground/");
    }

    @Test
    public void testDropDowns() {
        driver.findElement(By.linkText("Select Dropdown List")).click();
        WebElement findDropDown = driver.findElement(By.id("select-demo"));
        Select dayDropDown = new Select(findDropDown);
        dayDropDown.selectByVisibleText("Saturday");
    }

    @Test
    public void testDragAndDrop() {
        driver.findElement(By.linkText("Drag and Drop")).click();
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
    }
}
