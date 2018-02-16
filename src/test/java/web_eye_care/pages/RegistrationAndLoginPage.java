package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import web_eye_care.base_classes.BasePage;

public class RegistrationAndLoginPage extends BasePage{

    private static By registrationFormLocator = By.xpath("//form[@class='login-box m-new']");

    private RegistrationAndLoginPage(){
    }

    @Step("Checking if registration form is loaded")
    public static boolean isRegistrationAndLoginPageOpened(String url){
        boolean isRegistrationFormVisible = tryToWaitForVisibilityOfElementLocated(wait, registrationFormLocator,"Registration form does not visible");

        if (!isRegistrationFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }
}
