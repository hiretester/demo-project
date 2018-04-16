package web_eye_care.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import web_eye_care.base_classes.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import web_eye_care.listeners.TestListener;
import web_eye_care.pages.MainPage;
import io.qameta.allure.Description;
import web_eye_care.pages.MyAccountPage;
import web_eye_care.pages.RegistrationAndLoginPage;
import web_eye_care.utils.Spam4Me;

@Listeners({TestListener.class})
@Feature("User registration")
@Story("User registration on the http://www.wecsandbox.com")

public class UserRegistrationTest extends BaseTest{

    // test cases link
    // https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0
    // Test case ID - REG-01

    @Parameters({"spam4meUrl"})
    @BeforeClass
    private void setUpEmailBox(String url){
        Spam4Me.goToSpam4Me(url);
        Spam4Me.createEmail();
    }

    @Parameters({"mainPageUrl"})
    @Test()
    @Description("Opening main page of the site" + "\n" + "The step of the REG-01 Test case")
    @Link("https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0")
    public void testGoToMainPage(String url) {
        MainPage.goToMainPage(url);
        Assert.assertTrue(MainPage.isMainPageOpened(), "The site is unreachable. Url http://www.wecsandbox.com");
    }

    @Parameters ({"loginAndRegistrationPageUrl"})
    @Test(dependsOnMethods = "testGoToMainPage")
    @Description("Opening registration page" + "\n" + "The step of the REG-01 Test case")
    @Link("https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0")
    public void testGoToRegistrationPage(String url){
        MainPage.goToRegistrationAndLoginPage();
        Assert.assertTrue(RegistrationAndLoginPage.isRegistrationPageOpened(url), "Registration page was not opened");
    }

    @Parameters ({"password", "myAccountPageUrl"})
    @Test(dependsOnMethods = "testGoToRegistrationPage")
    @Description("Register new user" + "\n" + "The step of the REG-01 Test case")
    @Link("https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0")
    public void testRegistrationOfNewUser(String password, String url){
        RegistrationAndLoginPage.registerNewUser(Spam4Me.getNewRandomEmail(), password);
        Assert.assertTrue(MyAccountPage.isMyAccountPageOpened(url),"My Account page was not opened");
        MyAccountPage.signOut();
    }

    @Parameters ({"password", "myAccountPageUrl", "loginAfterSignOutPageUrl"})
    @Test(dependsOnMethods = "testRegistrationOfNewUser")
    @Description("Sign in as created user" + "\n" + "The step of the REG-01 Test case")
    @Link("https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0")
    public void testSignInOfCreatedUser(String password, String url, String url2){
        Assert.assertTrue(RegistrationAndLoginPage.isLoginPageOpened(url2), "Login page was not opened");
        RegistrationAndLoginPage.signIn(Spam4Me.getNewRandomEmail(), password);
        Assert.assertTrue(MyAccountPage.isMyAccountPageOpened(url),"My Account page was not opened");
        MyAccountPage.signOut();
        Assert.assertTrue(RegistrationAndLoginPage.isLoginPageOpened(url2), "Login page was not opened after sign out");
    }

    @Parameters ({"spam4meUrl"})
    @Test(dependsOnMethods = "testSignInOfCreatedUser")
    @Description("Checking the email box" + "\n" + "The step of the REG-01 Test case")
    @Link("https://docs.google.com/spreadsheets/d/1UTtHeSP3ovSTiGV6Kow1BH6eWGZE8wBcY7-XLLMnjTk/edit#gid=0")
    public void testLetterInInbox(String url){
        Spam4Me.goToSpam4Me(url);
        Assert.assertTrue(Spam4Me.isSpam4MePageOpened(url), "spam4.me was not opened");
        Assert.assertTrue(Spam4Me.isConfirmationLetterInInbox(),"There is no confirmation letter in inbox after registration");
    }

}
