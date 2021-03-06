package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import web_eye_care.base_classes.BasePage;

/**
 * RegistrationAndLoginPage class is a PageObject class that used to work with page that contains registration and login forms
 */
public class RegistrationAndLoginPage extends BasePage{

    private static By registrationFormLocator = By.xpath("//form[@class='login-box m-new']");
    private static By loginFormLocator = By.xpath("//form[@class='login-box m-login']");
    private static By emailFieldLocator = By.xpath("//form[@class='login-box m-new']//input[@name='Email']");
    private static By emailFieldForLoginLocator = By.xpath("//form[@class='login-box m-login']//input[@name='email']");
    private static By passwordFieldLocator = By.xpath("//form[@class='login-box m-new']//input[@name='password']");
    private static By passwordFieldForLoginLocator = By.xpath("//form[@class='login-box m-login']//input[@name='password']");
    private static By repeatPasswordFieldLocator = By.xpath("//form[@class='login-box m-new']//input[@name='passwordagain']");
    private static By continueButtonLocator = By.xpath("//form[@class='login-box m-new']//button");
    private static By signInButtonLocator = By.xpath("//form[@class='login-box m-login']//button");

    private RegistrationAndLoginPage(){
    }

    /**
     * isRegistrationPageOpened method is used to check if registration and login page was successfully opened
     * @param url - url address of the page
     * @return method returns true if element with registrationFormLocator locator is visible on the page and url
     * address of current page is equals to url address of Registration and Login page, otherwise returns false
     */
    @Step("Checking if registration and login page is loaded")
    public static boolean isRegistrationPageOpened(String url){
        boolean isRegistrationFormVisible = isElementVisible(wait, registrationFormLocator,"Registration form does not visible");

        if (!isRegistrationFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    /**
     * isLoginPageOpened method is used to check if registration and login page was successfully opened
     * @param url - url address of the page
     * @return method returns true if element with loginFormLocator locator is visible on the page and url
     * address of current page is equals to url address of Registration and Login page, otherwise returns false
     */
    @Step("Checking if registration and login page is loaded")
    public static boolean isLoginPageOpened(String url){
        boolean isLoginFormVisible = isElementVisible(wait, loginFormLocator,"Login form does not visible");

        if (!isLoginFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    /**
     * registerNewUser method is used to fill email, password and repeat password fields of registration form and to
     * submit registration
     * @param email - email of new user
     * @param password - password of new user
     */
    @Step("Filling registration form and sending it in order to register a new user")
    public static void registerNewUser(String email, String password){
        fillEmailField(email, emailFieldLocator);
        fillPasswordField(password, passwordFieldLocator);
        fillRepeatPasswordField(password, repeatPasswordFieldLocator);
        sendRegistrationForm();
    }

    /**
     * signIn method is used to sign in as a registered user into MyAccount
     * @param email - email of registered user
     * @param password  - password of registered user
     */
    @Step("Filling returning customer form and sending it in order to sign in")
    public static void signIn(String email, String password){
        fillEmailField(email, emailFieldForLoginLocator);
        fillPasswordField(password, passwordFieldForLoginLocator);
        sendLoginForm();
    }

    @Step("Fill email field with" + "{0}")
    private static void fillEmailField(String email, By locator){
        fillElementWithData(locator, email);
    }

    @Step("Fill password field with" + "{0}")
    private static void fillPasswordField(String password, By locator){
        fillElementWithData(locator, password);fillElementWithData(locator, password);
    }

    @Step("Fill repeat password field with" + "{0}")
    private static void fillRepeatPasswordField(String password, By locator){
        fillElementWithData(locator, password);
    }

    @Step("Confirm user registration on registration form")
    private static void sendRegistrationForm(){
        clickOnElement(continueButtonLocator, "Continue button on registration form does not present",
                "Continue button on registration form does not clickable");
    }

    @Step("Sign In on login form")
    private static void sendLoginForm(){
        clickOnElement(signInButtonLocator, "Sign In button on login form does not present",
                "Sign In button on login form does not clickable");
    }
}