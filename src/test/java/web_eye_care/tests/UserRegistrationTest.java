package web_eye_care.tests;

import web_eye_care.base_classes.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import web_eye_care.pages.MainPage;
import io.qameta.allure.Description;
import web_eye_care.pages.MyAccountPage;
import web_eye_care.pages.RegistrationAndLoginPage;
import web_eye_care.pages.Spam4Me;

public class UserRegistrationTest extends BaseTest{

    // test cases link
    // https://docs.google.com/spreadsheets/d/1XruN8JvT2ihSf0bA_86V0Zqp_kA9VmDi3b_cw9GDQZU/edit?pli=1#gid=0
    //Test case ID - REG-01

    @Parameters ({"email", "spam4meUrl"})
    @Test
    @Description("Creating email for registration")
    public void testCreateEmail(String email, String url){
        Spam4Me.goToSpam4Me(url);
        Assert.assertTrue(Spam4Me.isSpam4MePageOpened(url), "spam4.me was not opened");
        Spam4Me.createEmail(email);
    }

    @Parameters({"mainPageUrl"})
    @Test(dependsOnMethods = "testCreateEmail")
    @Description("Opening main page of the site")
    public void testGoToMainPage(String url) {
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

    @Parameters ({"email", "password", "myAccountPageUrl"})
    @Test(dependsOnMethods = "testRegistrationOfNewUser")
    @Description("Sign in as created user")
    public void testSignInOfCreatedUser(String email, String password, String url){
        RegistrationAndLoginPage.signIn(email, password);
        Assert.assertTrue(MyAccountPage.isMyAccountPageOpened(url),"My Account page was not opened");
        MyAccountPage.signOut();
    }

    @Parameters ({"spam4meUrl"})
    @Test(dependsOnMethods = "testRegistrationOfNewUser")
    @Description("Sign in as created user")
    public void testLetterInInbox(String url){
        Spam4Me.goToSpam4Me(url);
        Assert.assertTrue(Spam4Me.isSpam4MePageOpened(url), "spam4.me was not opened");
        Assert.assertTrue(Spam4Me.isRegistrationLetterInInbox(),"There is no confirmation letter in inbox after registration");
    }

}
