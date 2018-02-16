package web_eye_care.tests;

import web_eye_care.base_classes.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import web_eye_care.pages.MainPage;
import io.qameta.allure.Description;
import web_eye_care.pages.RegistrationAndLoginPage;

public class UserRegistration_Test extends BaseTest{

    // test cases link
    // https://docs.google.com/spreadsheets/d/1XruN8JvT2ihSf0bA_86V0Zqp_kA9VmDi3b_cw9GDQZU/edit?pli=1#gid=0

    @Parameters({"mainPageUrl"})
    @Test
    @Description("Opening main page of the site")
    public void testGoToMainPage(String url) {
        MainPage.goToMainPage(url);
        Assert.assertTrue(MainPage.isMainPageOpened(), "The site is unreachable");
    }

    @Parameters ({"loginAndRegistrationPageUrl"})
    @Test(dependsOnMethods = "testGoToMainPage")
    @Description("Opening registration page")
    public void testRegistration(String url){
        MainPage.goToRegistrationAndLoginPage();
        Assert.assertTrue(RegistrationAndLoginPage.isRegistrationAndLoginPageOpened(url), "Registration page was not opened");

    }
}
