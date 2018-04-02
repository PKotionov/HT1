package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class CreateUserPageObject {
    private WebDriver driver = null;
    private final String TITLE = "Create User [Jenkins]";

    // Подготовка элементов страницы.
    @FindBy(xpath = "//body")
    private WebElement body;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@name='password1']")
    private WebElement password1Field;

    @FindBy(xpath = "//input[@name='password2']")
    private WebElement password2Field;

    @FindBy(xpath = "//input[@name='fullname']")
    private WebElement fullNameField;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//button[@id='yui-gen4-button'][text()='Create User']")
    private WebElement createUserButton;


    public CreateUserPageObject(WebDriver webDriver) {
        driver = webDriver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public boolean checkTitle() {
        return driver.getTitle().equalsIgnoreCase(TITLE);
    }

    public String getErrorOnTextTitle() {
        if (!pageTextContains(TITLE)) {
            return "No '" + TITLE + "' is found inside page text!\n";
        } else {
            return "";
        }
    }

    public boolean pageTextContains(String searchString) {
        return body.getText().contains(searchString);
    }


    public boolean isCreateUserFormExist() {
        boolean form_found = false;
        if (usernameField.getAttribute("type").equalsIgnoreCase("text") &&
                password1Field.getAttribute("type").equalsIgnoreCase("password") &&
                password2Field.getAttribute("type").equalsIgnoreCase("password") &&
                fullNameField.getAttribute("type").equalsIgnoreCase("text") &&
                emailField.getAttribute("type").equalsIgnoreCase("text")) {
            form_found = true;
        }
        return form_found;
    }

    public boolean isCreateUserFormEmpty() {
        boolean form_found = false;

        if (usernameField.getText().isEmpty() &&
                password1Field.getText().isEmpty() &&
                password2Field.getText().isEmpty() &&
                fullNameField.getText().isEmpty() &&
                emailField.getText().isEmpty()) {
            form_found = true;
        }
        return form_found;
    }

    public boolean fillCreateUserForm(String[] someUserData) {
        int fillCount = 0;
        usernameField.sendKeys(someUserData[0]);
        if (usernameField.getAttribute("value").equalsIgnoreCase(someUserData[0])) {
            fillCount++;
        }
        password1Field.sendKeys(someUserData[1]);
        if (password1Field.getAttribute("value").equals(someUserData[1])) {
            fillCount++;
        }
        password2Field.sendKeys(someUserData[1]);
        if (password2Field.getAttribute("value").equals(someUserData[1])) {
            fillCount++;
        }
        fullNameField.sendKeys(someUserData[2]);
        if (fullNameField.getAttribute("value").equals(someUserData[2])) {
            fillCount++;
        }
        emailField.sendKeys(someUserData[3]);
        if (emailField.getAttribute("value").equals(someUserData[3])) {
            fillCount++;
        }
        if (fillCount == 5) {
            return true;
        } else return false;
    }

    public void clickOnCreateUserButton() {
        createUserButton.click();
    }

    public boolean isCreateUserButtonDisplayed() {
        return createUserButton.isDisplayed();
    }
}
