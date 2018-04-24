package web_eye_care.pages;

import web_eye_care.base_classes.BasePage;
import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * MainPage class is a PageObject class that used to work with main page of the www.wecsandbox.com website
 */
public class MainPage extends BasePage {

    private static By menuMyAccountLocator = By.xpath("//div[@class='header-account']");
    private static By menuItemSignInSingUpLocator = By.xpath("//ul[@class='header-account-list']/li[5]/a");

    private static By productCategoryLocator = By.xpath("//ul[@class='cat-list']/li[1]/a");

    private MainPage(){
    }

    /**
     * goToMainPage method is used to navigate to main page of the site
     * @param url - url address of the site
     */
    @Step("Trying to load main page of the site")
    public static void goToMainPage(String url){
        driver.navigate().to(url);
    }

    /**
     * isMainPageOpened method is used to check if main page was successfully opened
     * @return method returns true if element with menuMyAccountLocator locator is visible on the page, otherwise returns false
     */
    @Step("Checking if main page is opened")
    public static boolean isMainPageOpened(){
        return isElementVisible(wait, menuMyAccountLocator,"Main page was not opened");
    }

    /**
     * goToRegistrationAndLoginPage method is used to open page with registration and login forms
     */
    @Step("Goes to registration and login page")
    public static void goToRegistrationAndLoginPage(){
        closePopUpWindow();
        moveToElement(menuMyAccountLocator, "My Account does not present");
        moveToElementAndClickOnIt(menuItemSignInSingUpLocator,"Sign in / Sign up does not present",
                "Sign in / Sign up does not clickable");
    }

    /**
     * goToProductCategoryPage method is used to open page with list of products from certain manufacturer specified by productCategoryLocator
     */
    @Step("Goes to product category page")
    public static void goToProductCategoryPage (){
        moveToElementAndClickOnIt(productCategoryLocator, "Product category does not present on the page",
                "Product category does not clickable");
    }
}
