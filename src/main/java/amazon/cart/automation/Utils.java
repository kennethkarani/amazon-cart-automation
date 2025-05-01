package amazon.cart.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static void pause(int ms){
        try {
            Thread.sleep(ms);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static ChromeOptions baseOptions(){
        //autoinstalls the right chromedriver based on local chrome
        WebDriverManager.chromedriver().setup();
        //dont let websites access my location
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2);
        //builds list of custom options to configure broser inlcuding above
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        return options;

    }

    public static WebDriver getDefaultDriver(){
        ChromeOptions options = baseOptions();
        //fake user profile folder so amazon thinks just a random user not selenium
        options.addArguments("user-data-dir=/tmp/fake-user");
        ////fakes browser identity
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
        // disables internal automation markers
        options.addArguments("--disable-blink-features=AutomationControlled");
        ////removs more fingerprints screaming selenium
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        //boots up chrome browser with all options just set
        WebDriver driver = new ChromeDriver(options);
        ///if you cant find something right away wait 10 seconds before giving up
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriver getIncognitoDriver() {
        ChromeOptions options = baseOptions();
        options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;

    }

    public static void spoofWebDriver(WebDriver driver){
        ((JavascriptExecutor) driver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
    }
}
