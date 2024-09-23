import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    @Test
    public void testEmailFieldErrorMessages() {
        loginPage = new LoginPage(driver);
        driver.get("https://development.tulipcremation.com/login");

        // Attempt login without entering details
        clickLoginButtonUsingJs();  // Use JS Executor to click the button

        boolean testPassed = true;  // Flag to track overall test status

        // Check error message for empty email input
        String emailErrorMessage = loginPage.getEmailInputErrorMessage();
        try {
            Assert.assertEquals(emailErrorMessage, "This field is required.", "Empty email field error message not as expected.");
            Reporter.log("Test passed: Empty email field error message displayed as expected: " + emailErrorMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected 'This field is required.' but got: " + emailErrorMessage + "<br>", true);
            testPassed = false;
        }

        // Check error message for empty password input
        String passwordErrorMessage = loginPage.getPasswordInputErrorMessage();
        try {
            Assert.assertEquals(passwordErrorMessage, "This field is required.", "Empty password field error message not as expected.");
            Reporter.log("Test passed: Empty password field error message displayed as expected: " + passwordErrorMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected 'This field is required.' but got: " + passwordErrorMessage + "<br>", true);
            testPassed = false;
        }

        // Now, enter an invalid email and check the error message
        loginPage.enterEmail("invalidEmail");  // Input an invalid email
        clickLoginButtonUsingJs();  // Attempt login again

        // Check error message for invalid email input
        emailErrorMessage = loginPage.getEmailInputErrorMessage();
        try {
            Assert.assertEquals(emailErrorMessage, "Please enter a valid email address.", "Invalid email field error message not as expected.");
            Reporter.log("Test passed: Invalid email field error message displayed as expected: " + emailErrorMessage + "<br>", true);
        } catch (AssertionError e) {
            Reporter.log("Test failed: Expected 'Please enter a valid email address.' but got: " + emailErrorMessage + "<br>", true);
            testPassed = false;
        }

        // Overall test result log
        if (testPassed) {
            Reporter.log("<b><span style='color:green;'>Overall test passed: Email and password field validation completed successfully.</span></b><br>", true);
        } else {
            Reporter.log("<b><span style='color:red;'>Overall test failed: One or more validations did not pass.</span></b><br>", true);
        }
    }

    // Method to click the login button using JavaScript Executor
    private void clickLoginButtonUsingJs() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", loginPage.getLoginButtonElement());
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
                {"invalid-email@example.com", "invalid-password", "Sorry, your email or password doesn't match our records. Please check that you have entered them correctly"},  // Invalid email, invalid pass
                {"invalid-email@example.com", "Asd123$%^", "Sorry, your email or password doesn't match our records. Please check that you have entered them correctly"},    // Invalid email, valid pass
                {"d.kaneriya+080920241@astounddigital.com", "invalid-password", "Sorry, your email or password doesn't match our records. Please check that you have entered them correctly"},    // Valid email, invalid pass
                {"d.kaneriya+080920241@astounddigital.com", "Asd123$%^", null}  // Valid email, valid pass (no error)
        };
    }

    @Test(dataProvider = "loginData")
    public void testLoginCombinations(String email, String password, String expectedErrorMessage) {
        loginPage = new LoginPage(driver);
        driver.get("https://development.tulipcremation.com/login");  // Navigate to the login page

        // Clear the input fields before entering new values
        loginPage.clearEmailField();
        loginPage.clearPasswordField();

        // Enter email and password
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        clickLoginButtonUsingJs();

        boolean testPassed = true;  // Assume the test passes until a failure occurs
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            if (expectedErrorMessage != null) {
                // Wait for the error message to be visible
                wait.until(ExpectedConditions.visibilityOf(loginPage.getLoginErrorMessageElement()));
                String actualErrorMessage = loginPage.getLoginErrorMessage();
                Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Login error message not as expected.");
                Reporter.log("Test passed: Expected error message displayed: " + actualErrorMessage + "<br>", true);
            } else {
                String expectedUrl = "https://development.tulipcremation.com/dashboard/";
                // Wait for the URL to change to the expected URL
                wait.until(ExpectedConditions.urlToBe(expectedUrl));
                Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed: Unexpected URL after login.");
                Reporter.log("Test passed: Login Successful<br>", true);
            }
        } catch (AssertionError e) {
            // If an assertion fails, log the failure message
            testPassed = false;  // Mark the test as failed
            Reporter.log("Test failed: " + e.getMessage() + "<br>", true);
        } finally {
            // Report the overall test result once, regardless of test outcome
            if (testPassed) {
                Reporter.log("<b><span style='color:green;'>Overall Test Passed for the current data set!</span></b><br>", true);
            } else {
                Reporter.log("<b><span style='color:red;'>Overall Test Failed for the current data set!</span></b><br>", true);
            }
        }
    }
}
