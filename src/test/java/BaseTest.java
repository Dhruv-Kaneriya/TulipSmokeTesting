import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.BasePage;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        basePage = new BasePage(driver);  // Initialize BasePage
        basePage.openUrl("https://storefront:fpg2022@development.tulipcremation.com/");
        driver.findElement(By.linkText("Accept Without Tracking")).click();
//        driver.get("https://storefront:fpg2022@development.tulipcremation.com/");  // Navigate to the website
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close the browser
        }
    }
}