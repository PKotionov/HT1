package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class JenkinsNGTest {
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver = null;
    private page.AuthorisationPageObject page = null;

    private final String URL = "http://localhost:8080/";
    private final String USERS_PAGE_URL = "/securityRealm";
    private final String JENKINS_USERNAME = "pkotionov";
    private final String JENKINS_PASSWORD = "test";
    private final String TITLE_DASHBOARD = "Dashboard [Jenkins]";
    private final String DELETE_USER_TEXT = "Are you sure about deleting the user from Jenkins?";
    private static final String[] SOME_USER_DATA = {"someuser", "somepassword", "Some Full Name", "some@addr.dom"};

    //  private final String COLOR = "#4b758b";
    //  private final String MESSAGE_FOR_TEST = "is prohibited as a full name for security reasons.";

    @DataProvider(name = "manageDataProvider")
    public Object[][] manageDataProvider() {
        return new Object[][]{
                {"Manage Jenkins", "Manage Users", "Create/delete/modify users that can log in to this Jenkins"}
        };
    }

    @DataProvider(name = "findLinkCreateUserDataProvider")
    public Object[][] createUserLinkDataProvider() {
        return new Object[][]{
                {"/manage", "Create User"}
        };
    }

    @DataProvider(name = "findFormCreateUserDataProvider")
    public Object[][] createUserFormDataProvider() {
        return new Object[][]{
                {"/securityRealm", "Create User [Jenkins]",}
        };
    }

    @DataProvider(name = "fillUserFormDataProvider")
    public Object[][] fillUserFormDataProvider() {
        return new Object[][]{
                {"/securityRealm/addUser", SOME_USER_DATA, DELETE_USER_TEXT}
        };
    }

    public static String[] getSOME_USER_DATA() {
        return SOME_USER_DATA;
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        page = new page.AuthorisationPageObject(driver);
        // authorisation
        driver.get(URL);
        page.setUsername(JENKINS_USERNAME);
        Assert.assertEquals(page.getUsername(), JENKINS_USERNAME, "[Authorisation error: Unable to fill 'username' filed]");
        page.setPassword(JENKINS_PASSWORD);
        Assert.assertEquals(page.getPassword(), JENKINS_PASSWORD, "[Authorisation error: Unable to fill 'password' filed]");
        page.submitForm();
        Assert.assertEquals(driver.getTitle(), TITLE_DASHBOARD, "[Authorisation error: wrong login or password!]");
    }

    @AfterClass
    public void afterClass() {
        driver.close();
        driver.quit();
        String verificationString = verificationErrors.toString();
        if (!"".equals(verificationString)) {
            Assert.fail(verificationString);
        }
    }


    // Тест 1. После перехода по ссылке «Manage Jenkins» на странице появляется элемент dt с текстом «Manage Users» и
    // элемент dd с текстом «Create/delete/modify users that can log in to this Jenkins».

    @Test(dataProvider = "manageDataProvider")
    public void findCreateDeleteModifyUserTest(String manageJenkinsHrefText, String manageUsersHrefText, String createDeleteModifyUserText) {
        //Созддаем page.PageObject главной страницы
        MainPageObject mainPageObject = new MainPageObject(driver);
        // на главной странице после авторизации
        //проверяем, есть ли на странице элемент с текстом "Manage Jenkins"
        try {
            Assert.assertTrue(mainPageObject.isManageJenkinsDisplayed());
        } catch (Error e) {
            verificationErrors.append(mainPageObject.getErrorOnTextAbsence(manageJenkinsHrefText));
        }
        // кликаем по "Manage Jenkins"
        mainPageObject.clickOnManageJenkins();
        ManageJenkinsPage manageJenkinsPage = new ManageJenkinsPage(driver);
        //проверяем, есть ли на странице "Manage Users"
        try {
            Assert.assertTrue(manageJenkinsPage.isManageUserDisplayed());
        } catch (Error e) {
            verificationErrors.append(manageJenkinsPage.getErrorOnTextAbsence(manageUsersHrefText));
        }
        //проверяем, есть ли на странице элемент с текстом "Create/delete/modify users that can log in to this Jenkins"
        Assert.assertTrue(manageJenkinsPage.isModifyUserElementDisplayed());
    }

    //Тест 2. После клика по ссылке, внутри которой содержится элемент dt с текстом «Manage Users»,
    // становится доступна ссылка «Create User».

    @Test(dataProvider = "findLinkCreateUserDataProvider")
    public void findCreateUserTest(String link, String textElement) {
        //переходим на страницу "Manage Jenkins"
        driver.get(URL + link);
        //Созддаем page.PageObject страницы "Manage Jenkins"
        ManageJenkinsPage pageObject = new ManageJenkinsPage(driver);
        // кликаем по "Manage Users"
        pageObject.clickOnModifyUser();
        //Созддаем page.PageObject страницы "Users [Jenkins]"
        UsersPageObject usersPageObject = new UsersPageObject(driver);
        //проверяем переход на страницу "Users [Jenkins]"
        try {
            Assert.assertTrue(usersPageObject.checkTitle());
        } catch (Error e) {
            verificationErrors.append(usersPageObject.getErrorOnTextTitle());
        }
        //проверяем, есть ли на странице элемент с текстом "Create User"
        Assert.assertTrue(usersPageObject.isCreateUserElementDisplayed());
    }
    //Тест 3.	После клика по ссылке «Create User» появляется форма с тремя полями
    // типа text и двумя полями типа password, причём все поля должны быть пустыми.

    @Test(dataProvider = "findFormCreateUserDataProvider")
    public void findCreateUserFormTest(String link, String title) {
        //переходим на страницу "Users [Jenkins]"
        driver.get(URL + link);
        //Создаем PageObject страницы "Users [Jenkins]"
        UsersPageObject usersPageObject = new UsersPageObject(driver);
        //кликаем по «Create User»
        usersPageObject.clickOnCreateUser();
        // Создаем PageObject страницы "Create User [Jenkins]"
        CreateUserPageObject createUserPage = PageFactory.initElements(driver, CreateUserPageObject.class);

        //проверяем переход на страницу «Create User [Jenkins]»
        try {
            Assert.assertTrue(createUserPage.checkTitle());
        } catch (Error e) {
            verificationErrors.append(createUserPage.getErrorOnTextTitle());
        }
        //проверяем, есть ли на странице форма с тремя полями типа text и двумя полями типа password
        try {
            Assert.assertTrue(createUserPage.isCreateUserFormExist());
        } catch (Error e) {
            verificationErrors.append("[New user form not found!]");
        }
        //проверяем, что эта форма пуста:
        Assert.assertTrue(createUserPage.isCreateUserFormEmpty());
    }

    // Тест 4
    //После заполнения полей формы («Username» = «someuser», «Password» = «somepassword»,
    // «Confirm password» = «somepassword», «Full name» = «Some Full Name», «E-mail address» = «some@addr.dom»)
    // и клика по кнопке с надписью «Create User» на странице появляется строка таблицы (элемент tr), в которой есть
    // ячейка (элемент td) с текстом «someuser».
    //После клика по ссылке с атрибутом href равным «user/someuser/delete» появляется текст
    // «Are you sure about deleting the user from Jenkins?».
    //После клика по кнопке с надписью «Yes» на странице отсутствует строка таблицы (элемент tr),
    // с ячейкой (элемент td) с текстом «someuser». На странице отсутствует ссылка с атрибутом href
    // равным «user/someuser/delete».

    @Test(dataProvider = "fillUserFormDataProvider")
    public void createAndDeleteUserTest(String link, String[] userData, String text) {
        //переходим на страницу "Create User[Jenkins]"
        driver.get(URL + link);
        // Создаем PageObject страницы "Create User [Jenkins]"
        CreateUserPageObject createUserPage = PageFactory.initElements(driver, CreateUserPageObject.class);
        //заполняем форму:
        try {
            Assert.assertTrue(createUserPage.fillCreateUserForm(userData));
        } catch (Error e) {
            verificationErrors.append("[Can't fill the new user form.]");
        }
        //проверяем, есть ли на странице кнопка "Create User"
        try {
            Assert.assertTrue(createUserPage.isCreateUserButtonDisplayed());
        } catch (Error e) {
            verificationErrors.append("[Can't find 'Create user' button.]");
        }
        //кликаем по кнопке "Create User"
        createUserPage.clickOnCreateUserButton();

        //Создаем PageObject страницы "Users [Jenkins]"
        UsersPageObject usersPageObject = new UsersPageObject(driver);

        //проверяем, появилась ли на странице новая строка таблицы с ячейкой "someuser"
        try {
            Assert.assertTrue(usersPageObject.isNewUserDisplayed());
        } catch (Error e) {
            verificationErrors.append("[User didn't add: can't find 'someuser' button.]");
        }
        //проверяем, есть ли на странице ссылка с атрибутом href равным «user/someuser/delete»:
        try {
            Assert.assertTrue(usersPageObject.isUserDeleteHrefDisplayed());
        } catch (Error e) {
            verificationErrors.append("[Can't find link = «user/someuser/delete».]");
        }
        // кликаем на ссылку с атрибутом href равным «user/someuser/delete»:
        usersPageObject.clickOnUserDeleteHref();
        //проверяем, появляется ли на странице текст "Are you sure about deleting the user from Jenkins?";

        try {
            Assert.assertTrue(usersPageObject.pageTextContains(text));
        } catch (Error e) {
            verificationErrors.append(usersPageObject.getErrorOnTextAbsence(text));
        }
        //проверяем, есть ли на странице кнопка "yes":
        try {
            Assert.assertTrue(usersPageObject.isButtonYesDisplayed());
        } catch (Error e) {
            verificationErrors.append("[Can't find 'YES' button.]");
        }
        //кликаем на кнопку "yes" и удаляем юзера "someuser"
        usersPageObject.clickOnYesButton();
        //проверяем отсутствие на странице строки таблицы с ячейкой "someuser"
        try {
            Assert.assertTrue(usersPageObject.isNotNewUserDisplayed());
        } catch (Error e) {
            verificationErrors.append("[There is shown user after deleting.]");
        }
        //проверяем отсутствие на странице ссылки с атрибутом href равным «user/someuser/delete»:
        Assert.assertTrue(usersPageObject.isNotUserDeleteHrefDisplayed());
    }

    @Test
    public void adminDeleteTest() {
        //переходим на страницу "Users [Jenkins]" (все юзеры)
        driver.get(URL + USERS_PAGE_URL);
        //Создаем PageObject страницы "Users [Jenkins]"
        UsersPageObject usersPageObject = new UsersPageObject(driver);
        //проверяем отсутствие на странице ссылки с атрибутом href равным «user/admin/delete»:
        Assert.assertTrue(usersPageObject.isNotAdminDeleteHrefDisplayed(), "[There is link to delete admin on the page.]");
    }
}
