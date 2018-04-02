package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class MainPageObject extends PageObject {
    private By manageJenkinsLocator = By.linkText("Manage Jenkins");

    private WebDriver driver = null;

    public MainPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public boolean isManageJenkinsDisplayed() {
        return isElementDisplayed(manageJenkinsLocator);
    }

    public void clickOnManageJenkins() {
        driver.findElement(manageJenkinsLocator).click();
    }


}
