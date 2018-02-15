package base_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ResultValidationMessage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

//Методы общие для всех страниц ----------------------------------------------------------------------------------

    public static boolean isElementPresent (By locator){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> list = driver.findElements(locator);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        if (list.size() == 0){
            return false;
        } else {
            return list.get(0).isDisplayed();
        }
    }

    public static boolean isValidationFieldEqualsTo(By locator, String validationMessage, ResultValidationMessage resultValidationMessage){
        boolean elementPresent = isElementPresent(locator);
        if (!elementPresent){
            resultValidationMessage.validationMessage = "";
            return false;
        }
        WebElement element = driver.findElement(locator);
        resultValidationMessage.validationMessage = element.getText();
        return element.getText().equals(validationMessage);
    }

    //-------------------Ожидания

    public static boolean tryToWaitForPresenceOfElementLocated(WebDriverWait wait, By locator, String msg){
        boolean isPresent = true;
        try{
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch(TimeoutException te){
            isPresent = false;
            System.out.println(msg);
        }
        return isPresent;
    }

    public static boolean tryToWaitForElementToBeClickable(WebDriverWait wait, By locator, String msg) {
        boolean isClickable = true;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        }catch (TimeoutException te){
            System.out.println(msg);
            isClickable = false;
        }
        return isClickable;
    }

    public static boolean tryToWaitForVisibilityOfElementLocated(WebDriverWait wait, By locator, String msg) {
        boolean isVisible = true;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (TimeoutException te){
            System.out.println(msg);
            isVisible = false;
        }
        return isVisible;
    }
}