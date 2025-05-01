package amazon.cart.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static amazon.cart.automation.Utils.pause;
import static amazon.cart.automation.Utils.getDefaultDriver;

public class Amazon {
    public static void main(String args[]) throws InterruptedException {
        WebDriver driver = getDefaultDriver();
        driver.get("https://www.amazon.ca");

        // Stealth JS injection
        //when websites ask if im a bot. say no.
        ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
        );

        // wait one sec. mimic human hesitation
        pause(1000);
        // scroll page down 200 pixels. No scroll = suspicious
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 200)");

        // Wait to appear human. lazy human browsing
        pause(3000);

        ///scroll up 200 pixels
        //((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -200)");
        /// scroll to the top of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");

        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.clear();
        searchBox.sendKeys("Protein");
        pause(1000);
        driver.findElement(By.id("nav-search-submit-button")).click();
        pause(1000);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
        // driver.quit(); // only if you're done
    }

    public static WebDriver setUpDriver(){
        //autoinstalls the right chromedriver based on local chrome
        WebDriverManager.chromedriver().setup();

        //dont let websites access my location
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2);

        //builds list of custom options to configure broser inlcuding above
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        //fake user profile folder so amazon things just a random user not selenium
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
        return driver;
    }

}