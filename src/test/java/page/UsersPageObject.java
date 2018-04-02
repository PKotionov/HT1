package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class UsersPageObject extends PageObject {
    private final String TITLE = "Users [Jenkins]";
    private By createUserLocator = By.xpath("//a[text()='Create User']");
    private By adminDeleteHrefLocator = By.xpath("//a[@href='user/admin/delete']");
    private By yesButtonLocator = By.xpath("//button[@id='yui-gen4-button'][text()='Yes']");
    private By newUserCellLocator = By.xpath("//tr/td/a[text()='" + page.JenkinsNGTest.getSOME_USER_DATA()[0] + "']");
    private By userDeleteHrefLocator = By.xpath("//a[@href='user/" + page.JenkinsNGTest.getSOME_USER_DATA()[0] + "/delete']");


    WebDriver driver = null;

    public UsersPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public boolean checkTitle() {
        return driver.getTitle().equalsIgnoreCase(TITLE);
    }


    public String getErrorOnTextTitle() {
        return getErrorOnTextAbsence(TITLE);
    }

    public boolean isCreateUserElementDisplayed() {
        return isElementDisplayed(createUserLocator);
    }

    public void clickOnCreateUser() {
        driver.findElement(createUserLocator).click();
    }

    public boolean isUserDeleteHrefDisplayed() {
        return isElementDisplayed(userDeleteHrefLocator);
    }

    public boolean isButtonYesDisplayed() {
        return isElementDisplayed(yesButtonLocator);
    }

    public boolean isNewUserDisplayed() {
        return isElementDisplayed(newUserCellLocator);
    }

    public boolean isNotNewUserDisplayed() {
        return driver.findElements(newUserCellLocator).isEmpty();
    }

    public boolean isNotUserDeleteHrefDisplayed() {
        return driver.findElements(userDeleteHrefLocator).isEmpty();
    }

    public boolean isNotAdminDeleteHrefDisplayed() {
        return driver.findElements(adminDeleteHrefLocator).isEmpty();
    }

    public boolean isElementDisplayed(By by) {
        if (driver.findElement(by).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    public void clickOnUserDeleteHref() {
        driver.findElement(userDeleteHrefLocator).click();
    }

    public void clickOnYesButton() {
        driver.findElement(yesButtonLocator).click();
    }

}
