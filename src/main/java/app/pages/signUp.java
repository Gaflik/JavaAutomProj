package app.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;


public class signUp {
    private WebDriver driver;
    public signUp(WebDriver driver) { this.driver = driver; }
    private By emailField = By.xpath("//input[@id='id_email_hero_fuji']");
    private By confirmButton = By.xpath("//button[@data-uia='our-story-cta-hero_fuji']");
    private By step1 = By.xpath("//*[contains(text(),'Finish setting up your account')]");

    public signUp typeEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }
    public signUp clickConfirm() {
        driver.findElement(confirmButton).click();
        return this;
    }
    public boolean isStep1() {
        boolean label = isElementPresent(step1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return label;
    }
    public List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }
    public boolean isElementPresent(By by) {
        return findAll(by).size() > 0;
    }
}



