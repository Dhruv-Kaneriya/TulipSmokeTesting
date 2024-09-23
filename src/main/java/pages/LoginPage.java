package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // Locators for login page elements
    private By emailInput = By.xpath("//input[@id='dwfrm_login_email']");
    private By passwordInput = By.xpath("//input[@id='dwfrm_login_password']");
    private By loginButton = By.xpath("//button[contains(text(),'Login')]");
    private By loginErrorMessage = By.cssSelector("[data-ref='errorMessageLabel']");
    private By emailInputErrorMessage = By.xpath("//div[@id='dwfrm_login_email-error']");
    private By passwordInputErrorMessage = By.xpath("//div[@id='dwfrm_login_password-error']");

    // Methods for interacting with the login page
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public WebElement getLoginButtonElement() {
        return driver.findElement(loginButton); // Use the WebDriver instance to find the element
    }

    public String getLoginErrorMessage() {
        return driver.findElement(loginErrorMessage).getText();
    }

    public String getEmailInputErrorMessage() {
        return driver.findElement(emailInputErrorMessage).getText();
    }

    public String getPasswordInputErrorMessage() {
        return driver.findElement(passwordInputErrorMessage).getText();
    }

    // Method to clear the email field
    public void clearEmailField() {
        driver.findElement(emailInput).clear();
    }

    // Method to clear the password field
    public void clearPasswordField() {
        driver.findElement(passwordInput).clear();
    }

    public WebElement getLoginErrorMessageElement() {
        return driver.findElement(loginErrorMessage);
    }
}
