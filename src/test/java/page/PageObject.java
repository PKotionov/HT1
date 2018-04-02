package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public abstract class PageObject {
    private By bodyLocator = By.xpath("//body");
    private WebDriver driver = null;

    public PageObject(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }
    public boolean isElementDisplayed(By by) {
        if (driver.findElement(by).isDisplayed()) {
            return true;
        } else {
            return false;
        }
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
