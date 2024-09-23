import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.GlobalFooter;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlobalFooterTest extends BaseTest {

    private GlobalFooter globalFooter;


    @Override
    @BeforeClass
    public void setUp() {
        super.setUp();
        globalFooter = new GlobalFooter(driver);
    }

    @Test
    public void testFooterLinks() {
        int flag = 0;
        List<WebElement> elements = globalFooter.getFooterLinks();

        Set<String> links = new HashSet<>();


        for (WebElement element : elements) {
            String linkText = element.getText();      // Get the link text
            String expectedUrl = element.getAttribute("href");
            links.add(expectedUrl);
        }
        int index = 1;
        Reporter.log("Founded links: <br>");
        for (String url : links) {
            Reporter.log(index + ". " + url + "<br>", true);
            if (url.startsWith("mailto:")) {
                Reporter.log("Skipping assertion for mail link: " + url + "<br>", true);
                index++;
                continue;
            }
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
            String successMessage = "<b><span style='color:green;'>All footer links are working as expected. Test passed!</span></b><br>";
            Reporter.log(successMessage, true);
        } else {
            String failureMessage = "<b><span style='color:red;'>Some footer links are not working as expected. Test Failed!</span></b><br>";
            Reporter.log(failureMessage, true);
        }
        // Highlight and log the success message

    }

    @Test
    public void testEmptyEmailSubscription() {
        boolean testPassed = true;
        driver.get("https://development.tulipcremation.com");
        driver.findElement(globalFooter.getEmailInput()).clear(); // Clear any text in email input
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement subscribeButton = driver.findElement(globalFooter.getSubscribeButton());
        js.executeScript("arguments[0].click();", subscribeButton);

        // Validate required field messages
        String emailErrorMessage = driver.findElement(globalFooter.getEmailValidationMessage()).getText();
        try {
            Assert.assertEquals(emailErrorMessage, "This field is required.", "Expected email validation message not found.");
            Reporter.log("Test passed: Email validation message displayed as expected: " + emailErrorMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected email validation message not found. Actual: " + emailErrorMessage + "<br>", true);
            testPassed = false; // Set flag to false if assertion fails
        }
        String agreementErrorMessage = driver.findElement(globalFooter.getAgreementCheckboxMessage()).getText();
        try {
            Assert.assertEquals(agreementErrorMessage, "You must accept Privacy and Cookie Policies before subscription.", "Expected agreement message not found.");
            Reporter.log("Test passed: Agreement message displayed as expected: " + agreementErrorMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected agreement message not found. Actual: " + agreementErrorMessage + "<br>", true);
            testPassed = false; // Set flag to false if assertion fails
        }

        // Overall test result log
        if (testPassed) {
            Reporter.log("<b><span style='color:green;'>Overall Test Passed!</span></b>", true);
        } else {
            Reporter.log("<b><span style='color:red;'>Overall Test Failed!</span></b>", true);
        }
    }

    @Test
    public void testCheckedAgreementWithoutEmail() {
        driver.get("https://development.tulipcremation.com");

        // Check the agreement checkbox
        WebElement agreementCheckbox = driver.findElement(globalFooter.getAgreementCheckbox());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", agreementCheckbox);

        // Click the subscribe button
        WebElement subscribeButton = driver.findElement(globalFooter.getSubscribeButton());
        js.executeScript("arguments[0].click();", subscribeButton);

        boolean testPassed = true;

        String emailErrorMessage = driver.findElement(globalFooter.getEmailValidationMessage()).getText();
        try {
            Assert.assertEquals(emailErrorMessage, "This field is required.", "Expected email validation message not found.");
            Reporter.log("Test passed: Email validation message displayed as expected: " + emailErrorMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected email validation message not found. Actual: " + emailErrorMessage + "<br>", true);
            testPassed = false;
        }

        // Overall test result log
        if (testPassed) {
            Reporter.log("<b><span style='color:green;'>Overall Test Passed!</span></b>", true);
        } else {
            Reporter.log("<b><span style='color:red;'>Overall Test Failed!</span></b>", true);
        }
    }

    @Test
    public void testCheckedAgreementWithInvalidEmail() {
        driver.get("https://development.tulipcremation.com");
        String invalidEmail = "invalid-email";
        driver.findElement(globalFooter.getEmailInput()).sendKeys(invalidEmail);

        // Check the agreement checkbox
        WebElement agreementCheckbox = driver.findElement(globalFooter.getAgreementCheckbox());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", agreementCheckbox);

        // Click the subscribe button
        WebElement subscribeButton = driver.findElement(globalFooter.getSubscribeButton());
        js.executeScript("arguments[0].click();", subscribeButton);

        boolean testPassed = true;

        String emailErrorMessage = driver.findElement(globalFooter.getEmailValidationMessage()).getText();
        try {
            Assert.assertEquals(emailErrorMessage, "Please enter a valid email address.", "Expected invalid email message not found.");
            Reporter.log("Test passed: Invalid email message displayed as expected: " + emailErrorMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected invalid email message not found. Actual: " + emailErrorMessage + "<br>", true);
            testPassed = false;
        }

        // Overall test result log
        if (testPassed) {
            Reporter.log("<b><span style='color:green;'>Overall Test Passed!</span></b>", true);
        } else {
            Reporter.log("<b><span style='color:red;'>Overall Test Failed!</span></b>", true);
        }
    }

    @Test
    public void testValidEmailSubscription() {
        driver.get("https://development.tulipcremation.com");
        String validEmail = "d.kaneriya+26@astounddigital.com";
        driver.findElement(globalFooter.getEmailInput()).clear();
        driver.findElement(globalFooter.getEmailInput()).sendKeys(validEmail);

        // Click the agreement checkbox
        WebElement agreementCheckbox = driver.findElement(globalFooter.getAgreementCheckbox());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", agreementCheckbox);

        // Click the subscribe button
        WebElement subscribeButton = driver.findElement(globalFooter.getSubscribeButton());
        js.executeScript("arguments[0].click();", subscribeButton);

        boolean testPassed = true;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(globalFooter.getSubscriptionMessage())));
        String subscriptionMessage = driver.findElement(globalFooter.getSubscriptionMessage()).getText();
        try {
            Assert.assertEquals(subscriptionMessage, "Thanks for signing up!", "Expected subscription success message not found.");
            Reporter.log("Test passed: Subscription success message displayed as expected: " + subscriptionMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected subscription success message not found. Actual: " + subscriptionMessage + "<br>", true);
            testPassed = false;
        }

        // Overall test result log
        if (testPassed) {
            Reporter.log("<b><span style='color:green;'>Overall Test Passed!</span></b>", true);
        } else {
            Reporter.log("<b><span style='color:red;'>Overall Test Failed!</span></b>", true);
        }
    }


}
