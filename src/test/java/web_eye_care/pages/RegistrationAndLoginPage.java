package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

public class RegistrationAndLoginPage extends BasePage{

    private static WebElement emailField, passwordField, repeatPasswordField, continueButton;

    private static By registrationFormLocator = By.xpath("//form[@class='login-box m-new']");
    private static By emailFieldLocator = By.xpath("//form[@class='login-box m-new']//input[@name='Email']");
    private static By passwordFieldLocator = By.xpath("//form[@class='login-box m-new']//input[@name='password']");
    private static By repeatPasswordFieldLocator = By.xpath("//form[@class='login-box m-new']//input[@name='passwordagain']");
    private static By continueButtonLocator = By.xpath("//form[@class='login-box m-new']//button");

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

    @Step("Filling registration form and sending it in order to register a new user")
    public static void registerNewUser(String email, String password){
        fillEmailField(email);
        fillPasswordField(password);
        fillRepeatePasswordField(password);
        sendRegistrationForm();
    }

    @Step("Заполняем поле \"Имя\" значением " + "{0}")
    private static void fillEmailField(String email){
        emailField = driver.findElement(emailFieldLocator);
        emailField.sendKeys(email);
    }

    @Step("Заполняем поле \"Email\" значением " + "{0}")
    private static void fillPasswordField(String password){
        passwordField = driver.findElement(passwordFieldLocator);
        passwordField.sendKeys(password);
    }

    @Step("Заполняем поле \"Сообщение\" значением " + "{0}")
    private static void fillRepeatePasswordField(String password){
        repeatPasswordField = driver.findElement(repeatPasswordFieldLocator);
        repeatPasswordField.sendKeys(password);
    }

    @Step("Нажимаем кнопку подтверждения")
    private static void sendRegistrationForm(){
        continueButton = driver.findElement(continueButtonLocator);
        continueButton.click();
    }
}
