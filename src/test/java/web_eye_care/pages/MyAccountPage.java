package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import web_eye_care.base_classes.BasePage;

/**
 * MyAccountPage class is a PageObject class that used to work with MyAccount page
 */
public class MyAccountPage extends BasePage {

    private static By myAccountTextLocator = By.xpath("//div[@class='container']/h1[@class='page-h1']");
    private static By menuMyAccountLocator = By.xpath("//div[@class='header-account']");
    private static By menuItemSignOutLocator = By.xpath("//ul[@class='header-account-list']/li[6]/a");

    private MyAccountPage (){
    }

    /**
     * isMyAccountPageOpened method is used to check if MyAccount page was successfully opened
     * @param url - url address of the page
     * @return method returns true if element with myAccountTextLocator locator is visible on the page and url
     * address of current page is equals to url address of MyAccount page, otherwise returns false
     */
    @Step("Checking if MyAccount page is loaded")
    public static boolean isMyAccountPageOpened(String url){
        boolean isMyAccountTextVisible = isElementVisible(wait, myAccountTextLocator,"My Account text does not visible");

        if (!isMyAccountTextVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    /**
     * signOut method is used to sign out from My Account
     */
    @Step("Signing out from account")
    public static void signOut(){
        moveToElement(menuMyAccountLocator, "My Account menu does not present");
        moveToElementAndClickOnIt(menuItemSignOutLocator, "Sign out item does not present", "Sign out item does not clickable");
    }
}
