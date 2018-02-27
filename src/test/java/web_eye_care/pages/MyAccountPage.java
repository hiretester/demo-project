package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import web_eye_care.base_classes.BasePage;

public class MyAccountPage extends BasePage {

    private static By myAccountTextLocator = By.xpath("//div[@class='container']/h1[@class='page-h1']");
    private static By menuMyAccountLocator = By.xpath("//div[@class='header-account']");
    private static By menuItemSignOutLocator = By.xpath("//ul[@class='header-account-list']/li[6]/a");

    private MyAccountPage (){
    }

    @Step("Checking if MyAccount page is loaded")
    public static boolean isMyAccountPageOpened(String url){
        boolean isMyAccountTextVisible = tryToWaitForVisibilityOfElementLocated(wait, myAccountTextLocator,"My Account text does not visible");

        if (!isMyAccountTextVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    /*@Step("Checking if right user email is shown on MyAccount page")
    public static boolean isUserEmailIsShown (){
     return false;
    }*/

    @Step("Signing out from account")
    public static void signOut(){
        moveToElement(menuMyAccountLocator, "My Account menu does not present");
        moveToElementAndClickOnIt(menuItemSignOutLocator, "Sign out item does not present", "Sign out item does not clickable");
    }
}
