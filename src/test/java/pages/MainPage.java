package pages;

import base_classes.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MainPage extends BasePage {

    private static By menuMyAccountLocator = By.xpath("//div[@class='header-account']");
    private static By menuItemSignInSingUpLocator = By.xpath("//ul[@class = 'header-account-list']/li[5]");

    private MainPage(){
    }

    @Step("Переход на главную страницу сайта")
    public static void goToMainPage(String url){
        driver.navigate().to(url);
    }

    @Step("Проверяем открыта ли главная страница сайта")
    public static boolean isMainPageOpened(){
        return tryToWaitForPresenceOfElementLocated(wait, menuMyAccountLocator,"TimeoutException in isMainPageOpened");
    }

    @Step("Переход на страницу регистрации")
    public static void goToRegistrationAndLoginPage(){

        WebElement menuMyAccount = driver.findElement(menuMyAccountLocator);
        Actions builder = new Actions(driver);
        builder.moveToElement(menuMyAccount).build().perform();

        //boolean isVisible = tryToWaitForVisibilityOfElementLocated(wait, menuItemSignInSingUpLocator, "");
        WebElement menuItemSignInSignUp = driver.findElement(menuItemSignInSingUpLocator);
        menuItemSignInSignUp.click();

    }
}
