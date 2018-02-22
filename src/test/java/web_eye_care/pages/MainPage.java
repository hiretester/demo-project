package web_eye_care.pages;

import web_eye_care.base_classes.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import io.qameta.allure.Step;

public class MainPage extends BasePage {

    private static By menuMyAccountLocator = By.xpath("//div[@class='header-account']");
    private static By menuItemSignInSingUpLocator = By.xpath("//ul[@class='header-account-list']/li[5]/a");

    private static By popUpWindowLocator = By.xpath("//div[@id='usi_content']");
    private static By popUpWindowCloseButtonLocator = By.xpath("//div[@id='usi_close']");

    private static By productCategoryLocator = By.xpath("//ul[@class='cat-list']/li[1]/a");

    private MainPage(){
    }

    @Step("Trying to load main page of the site")
    public static void goToMainPage(String url){
        driver.navigate().to(url);
    }

    @Step("Checking if main page is opened")
    public static boolean isMainPageOpened(){
        return tryToWaitForPresenceOfElementLocated(wait, menuMyAccountLocator,"TimeoutException in isMainPageOpened");
    }

    @Step("Goes to registration and login page")
    public static void goToRegistrationAndLoginPage(){

        closePopUpWindow();

        WebElement menuMyAccount = driver.findElement(menuMyAccountLocator);
        Actions builder = new Actions(driver);
        builder.moveToElement(menuMyAccount).build().perform();

        boolean isPresent = tryToWaitForPresenceOfElementLocated(wait, menuItemSignInSingUpLocator,"Sign in / Sign up does not present");
        WebElement menuItemSignInSignUp = driver.findElement(menuItemSignInSingUpLocator);
        builder = new Actions(driver);
        builder.moveToElement(menuItemSignInSignUp).click().build().perform();

    }

    @Step("Goes to product category page")
    public static void goToProductCategoryPage (){
        WebElement product = driver.findElement(productCategoryLocator);
        product.click();
    }

    private static void closePopUpWindow(){
        boolean popUpWindowIsShown = tryToWaitForVisibilityOfElementLocated(wait, popUpWindowLocator,"Pop-up window was not found");
        if (popUpWindowIsShown){
            WebElement closeButton = driver.findElement(popUpWindowCloseButtonLocator);
            closeButton.click();
        }
    }
}
