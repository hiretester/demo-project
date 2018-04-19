package web_eye_care.pages;

import web_eye_care.base_classes.BasePage;
import org.openqa.selenium.By;
import io.qameta.allure.Step;

public class MainPage extends BasePage {

    private static By menuMyAccountLocator = By.xpath("//div[@class='header-account']");
    private static By menuItemSignInSingUpLocator = By.xpath("//ul[@class='header-account-list']/li[5]/a");

    private static By productCategoryLocator = By.xpath("//ul[@class='cat-list']/li[1]/a");

    private MainPage(){
    }

    @Step("Trying to load main page of the site")
    public static void goToMainPage(String url){
        driver.navigate().to(url);
    }

    @Step("Checking if main page is opened")
    public static boolean isMainPageOpened(){
        return isElementVisible(wait, menuMyAccountLocator,"Main page was not opened");
    }

    @Step("Goes to registration and login page")
    public static void goToRegistrationAndLoginPage(){
        closePopUpWindow();
        moveToElement(menuMyAccountLocator, "My Account does not present");
        moveToElementAndClickOnIt(menuItemSignInSingUpLocator,"Sign in / Sign up does not present",
                "Sign in / Sign up does not clickable");
    }

    @Step("Goes to product category page")
    public static void goToProductCategoryPage (){
        moveToElementAndClickOnIt(productCategoryLocator, "Product category does not present on the page",
                "Product category does not clickable");
    }
}
