import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.GlobalFooter;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://storefront:fpg2022@development.tulipcremation.com/");  // Navigate to the website
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close the browser
        }
    }
}
