package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GlobalFooter extends BasePage {

    public GlobalFooter(WebDriver driver) {
        super(driver);
    }

    // Locators for email subscription
    private By emailInput = By.cssSelector("[placeholder='Enter your email']");
    private By subscribeButton = By.xpath("//form[@name='subscribe-form']/div[@class='b-newsletters-group']/button[@type='submit']");
    private By subscriptionMessage = By.xpath("//div[@class='b-newsletters-message_success']"); // Adjust based on actual selector
    private By agreementCheckbox = By.xpath("//input[@id='dwfrm_emailsubscribe_agreeToPrivacy']");
    private By emailValidationMessage = By.xpath("//div[starts-with(@id, 'dwfrm_emailsubscribe_email_') and contains(@id, '-error')]");
    private By agreementCheckboxMessage = By.xpath("//div[@id='dwfrm_emailsubscribe_agreeToPrivacy-error']");

    public List<WebElement> getFooterLinks() {
        return driver.findElements(By.cssSelector(".b-footer-inner a"));
    }

    // Email subscription methods
    public By getEmailInput() {
        return emailInput; // Return the By locator directly
    }

    public By getSubscribeButton() {
        return subscribeButton;
    }

    public By getSubscriptionMessage() {
        return subscriptionMessage;
    }

    public By getAgreementCheckbox() {
        return agreementCheckbox;
    }

    public By getEmailValidationMessage() {
        return emailValidationMessage;
    }

    public By getAgreementCheckboxMessage() {
        return agreementCheckboxMessage;
    }
}
