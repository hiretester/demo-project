package web_eye_care.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
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
@Story("Register a new user")
public class UserRegistrationTest extends BaseTest{

    // test cases link
    // https://docs.google.com/spreadsheets/d/1XruN8JvT2ihSf0bA_86V0Zqp_kA9VmDi3b_cw9GDQZU/edit?pli=1#gid=0
    //Test case ID - REG-01

    @Step("Creating email for registration")
    private void createEmail(String email, String url){
        Spam4Me.goToSpam4Me(url);
        Assert.assertTrue(Spam4Me.isSpam4MePageOpened(url), "spam4.me was not opened");
        Spam4Me.createEmail(email);
    }

    @Parameters({"mainPageUrl", "email", "spam4meUrl"})
    @Test()
    @Description("Opening main page of the site")
    public void testGoToMainPage(String url, String email, String mailUrl) {
        createEmail(email, mailUrl);
        MainPage.goToMainPage(url);
        Assert.assertTrue(MainPage.isMainPageOpened(), "The site is unreachable");
    }

    @Parameters ({"loginAndRegistrationPageUrl"})
    @Test(dependsOnMethods = "testGoToMainPage")
    @Description("Opening registration page")
    public void testGoToRegistrationPage(String url){
        MainPage.goToRegistrationAndLoginPage();
        Assert.assertTrue(RegistrationAndLoginPage.isRegistrationPageOpened(url), "Registration page was not opened");
    }

    @Parameters ({"email", "password", "myAccountPageUrl"})
    @Test(dependsOnMethods = "testGoToRegistrationPage")
    @Description("Register new user")
    public void testRegistrationOfNewUser(String email, String password, String url){
        RegistrationAndLoginPage.registerNewUser(email, password);
        Assert.assertTrue(MyAccountPage.isMyAccountPageOpened(url),"My Account page was not opened");
        MyAccountPage.signOut();
    }

    @Parameters ({"email", "password", "myAccountPageUrl", "loginAfterSignOutPageUrl"})
    @Test(dependsOnMethods = "testRegistrationOfNewUser")
    @Description("Sign in as created user")
    public void testSignInOfCreatedUser(String email, String password, String url, String url2){
        Assert.assertTrue(RegistrationAndLoginPage.isLoginPageOpened(url2), "Login page was not opened");
        RegistrationAndLoginPage.signIn(email, password);
        Assert.assertTrue(MyAccountPage.isMyAccountPageOpened(url),"My Account page was not opened");
        MyAccountPage.signOut();
        Assert.assertTrue(RegistrationAndLoginPage.isLoginPageOpened(url2), "Login page was not opened after sign out");
    }

    @Parameters ({"spam4meUrl"})
    @Test(dependsOnMethods = "testSignInOfCreatedUser")
    @Description("Checking the email box")
    public void testLetterInInbox(String url){
        Spam4Me.goToSpam4Me(url);
        Assert.assertTrue(Spam4Me.isSpam4MePageOpened(url), "spam4.me was not opened");
        Assert.assertTrue(Spam4Me.isConfirmationLetterInInbox(),"There is no confirmation letter in inbox after registration");
    }

}
