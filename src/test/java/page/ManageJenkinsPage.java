package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class ManageJenkinsPage extends PageObject {

    private By modifyUserLocator = By.xpath("//dd[text()='Create/delete/modify users that can log in to this Jenkins']");
    private By manageUserLocator = By.xpath("//dt[text()='Manage Users']");

    private WebDriver driver = null;

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void clickOnModifyUser() {
        driver.findElement(modifyUserLocator).click();
    }

    public boolean isManageUserDisplayed() {
        return isElementDisplayed(manageUserLocator);
    }

    public boolean isModifyUserElementDisplayed() {
        return isElementDisplayed(modifyUserLocator);
    }
}
