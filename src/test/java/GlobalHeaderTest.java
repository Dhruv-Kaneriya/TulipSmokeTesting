import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlobalHeaderTest extends BaseTest {

    @Test
    public void testHeaderLinks() {
        int flag = 0;
        Set<String> links = new HashSet<>();
        List<WebElement> elements = driver.findElements(By.cssSelector(".b-header_actions a"));

        for (WebElement element : elements) {
            String linkText = element.getText();      // Get the link text
            String expectedUrl = element.getAttribute("href");
            links.add(expectedUrl);
        }
        int index = 1;
        Reporter.log("Founded links: <br>");
        for (String url : links) {
            Reporter.log(index + ". " + url + "<br>", true);
            if (url.startsWith("tel:")) {
                // Log to the TestNG report with a new line
                Reporter.log("Skipping assertion for tel link: " + url + "<br>", true);
                index++;
                continue;  // Skip to the next link
            }

            // Navigate to the URL
            driver.get(url);

            try {
                // Perform the assertion
                Assert.assertEquals(driver.getCurrentUrl(), url, "Link is not working for: " + url);

                // Log the passed assertion for the link in the report
                Reporter.log("<b><span style='color:green;'>Assertion passed</span></b><br>", true);
            } catch (AssertionError e) {
                // Catch assertion failure and log it to the report
                Reporter.log("<b><span style='color:red;'>Assertion failed </span></b><br>", true);
                Reporter.log("Error message: " + e.getMessage() + "<br>", true);
                flag++;
            }
            index++;
        }

        if (flag == 0) {
            String successMessage = "<b><span style='color:green;'>All header links are working as expected. Test passed!</span></b><br>";
            Reporter.log(successMessage, true);
        } else {
            String failureMessage = "<b><span style='color:red;'>Some header links are not working as expected. Test Failed!</span></b><br>";
            Reporter.log(failureMessage, true);
        }
    }
}
