package amazon.cart.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static amazon.cart.automation.Utils.pause;
import static amazon.cart.automation.Utils.getDefaultDriver;

public class GmailLogin {
    public static void main(String args[]){
        WebDriver driver = getDefaultDriver();
        driver.get("https://www.google.com/gmail");
        login(driver);
        /// wait until this element appears. I dont care about storing it.
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.UI")));
        pause(3000);
        //driver.quit();
    }

    public static WebDriver setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        return driver;
    }

    public static void login(WebDriver driver){
        try {
            driver.findElement(By.id("identifierId")).sendKeys("slavnmaster");
            driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button")).click();
            driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys("Ecole199");
            driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button")).click();
            try{
                driver.findElement(By.xpath("//*[@id=\"yDmH0d\"]/c-wiz/div/div[3]/div/div[2]/div/div/button"));
            } catch(Exception e) {
                // No passkey prompt, continue
            }
        } catch(Exception e) {
            System.out.println("Login failed or Gmail layout changed");
        }
    }
}
