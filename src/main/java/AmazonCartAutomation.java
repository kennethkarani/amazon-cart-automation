import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class AmazonCartAutomation {
    public static void main(String args[]) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
       options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        //explict wait- until a sepecific condition true or 10 seconds pass
        // sets default waiting time for finding elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");
        login(driver);
        pause(500);
        // clicking escape key
        //Actions actions = new Actions(driver);
        //actions.sendKeys(Keys.ESCAPE).perform();
        addItems(driver);
        pause(500);
        removeItem(driver);
        checkout(driver);
        pause(500);
        deliveryInfo(driver,"Jey","Uso",20052);
        pause(500);
        finish(driver);
        String confirmation = driver.findElement(By.className("complete-header")).getText();
        if (confirmation.equals("Thank you for your order!")) {
            System.out.println("Test Passed");
        } else {
            System.out.println("Test Failed");
        }
        pause(4000);
        driver.quit();
    }

    public static void login(WebDriver driver) {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    public static void addItems(WebDriver driver) {
        System.out.println("Clicking backpack...");
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        System.out.println("Clicked backpack...");
        System.out.println("Clicking jacket...");
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
        System.out.println("Clicked jacket");
        System.out.println("Clicking Bike Light...");
        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        System.out.println("Clicked Bike Light");
        driver.findElement(By.className("shopping_cart_link")).click();
    }

    public static void removeItem(WebDriver driver) {
        System.out.println("Removing Bike Light...");
        driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
    }

    public static void checkout(WebDriver driver) {
        System.out.println("Checkout");
        driver.findElement(By.id("checkout")).click();
    }

    public static void deliveryInfo(WebDriver driver,String firstName,String lastName,Integer postalCode) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode.toString());
        driver.findElement(By.id("continue")).click();
    }

    public static void finish(WebDriver driver) {
        driver.findElement(By.id("finish")).click();
    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
