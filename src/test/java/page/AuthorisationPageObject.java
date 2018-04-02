package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class AuthorisationPageObject extends PageObject {

    private WebDriver driver = null;

    private By bodyLocator = By.xpath("//body");
    private By usernameLocator = By.id("j_username");
    private By passwordLocator = By.name("j_password");
    private By submitButtonLocator = By.name("Submit");


    public AuthorisationPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    // Заполнение username
    public AuthorisationPageObject setUsername(String name) {
        driver.findElement(usernameLocator).clear();
        driver.findElement(usernameLocator).sendKeys(name);
        return this;
    }

    public String getUsername() {
        return driver.findElement(usernameLocator).getAttribute("value");
    }

    // Заполнение password
    public AuthorisationPageObject setPassword(String password) {
        driver.findElement(passwordLocator).clear();
        driver.findElement(passwordLocator).sendKeys(password);
        return this;
    }

    public String getPassword() {
        return driver.findElement(passwordLocator).getAttribute("value");
    }

    //Отправка данных из формы.
    public AuthorisationPageObject submitForm() {
        driver.findElement(submitButtonLocator).click();
        return this;
    }

    // Проверка вхождения подстроки в текст страницы.
    public boolean pageTextContains(String searchString) {
        return driver.findElement(bodyLocator).getText().contains(searchString);
    }

    public String getErrorOnTextAbsence(String searchString) {
        if (!pageTextContains(searchString)) {
            return "No '" + searchString + "' is found inside page text!\n";
        } else {
            return "";
        }
    }
}



