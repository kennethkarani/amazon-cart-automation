package amazon.cart.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static amazon.cart.automation.Utils.pause;
import static amazon.cart.automation.Utils.getIncognitoDriver;

public class PriceTracker {
    public static void main(String args[]) {
        WebDriver driver = getIncognitoDriver();
        driver.get("https://www.bestbuy.ca/en-ca/product/open-box-apple-macbook-air-13-6-w-touch-id-fall-2024-space-grey-apple-m3-16gb-ram-256gb-ssd-english/18925109");
        closeCookiePopup(driver);
        pause(200);
        String price = extractPriceFromJson(driver);
        if (price != null) {
            System.out.println("Price: $" + price);
        } else {
            System.out.println("Price not found.");
        }
        pause(2000);
        driver.quit();

    }

    public static String extractPriceFromJson(WebDriver driver){
        WebElement scriptTag = driver.findElement(By.id("product-json-ld"));
        String jsonText = scriptTag.getAttribute("innerHTML");
        // Optional: use regex to extract the price
        Pattern pattern = Pattern.compile("\"price\":\\s*(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(jsonText);
        if (matcher.find()) {
            String price = matcher.group(1);
            return price;
        } else {
            return null;
        }

    }

    public static WebDriver setupDriver(){
        WebDriverManager.chromedriver().setup();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.setExperimentalOption("prefs", prefs);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public static void closeCookiePopup(WebDriver driver) {
        driver.findElement(By.className("onetrust-close-btn-handler")).click();
    }
}
