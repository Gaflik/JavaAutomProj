package test.java;

import app.AppConfig;
import app.pages.signUp;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;


public class SignUpTest {
    private WebDriver driver;
    private signUp page;
    private static final Logger log = LogManager.getLogger(SignUpTest.class);

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Gala\\Browser drivers\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(AppConfig.baseUrl);
        log.info("Chrome is started");
    }
    @Test (priority = 0, description="SigUP scenario.")
    @Step("Enter email {typeEmail} into field")
    public void signUp() {
        page = new signUp(driver);
        page.typeEmail("gptest@bk.ru");
        page.clickConfirm();
        log.info("Email is entered");
        Assert.assertTrue(page.isStep1(),"Находимся на неправильной странице");
    }
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
