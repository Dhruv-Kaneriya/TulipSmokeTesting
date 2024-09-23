package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    // Generic method to open any URL
    public void openUrl(String url) {
        driver.get(url);
    }
}
