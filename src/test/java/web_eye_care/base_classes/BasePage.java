package web_eye_care.base_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_eye_care.utils.ResultValidationMessage;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    private static WebElement element;
    private static Actions builder;

    private static By popUpWindowLocator = By.xpath("//div[@id='usi_content']");
    private static By popUpWindowCloseButtonLocator = By.xpath("//div[@id='usi_close']");

    //-------------------Методы общие для всех страниц

    public static boolean isElementPresent (By locator){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        List<WebElement> list = driver.findElements(locator);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
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

    public static WebElement findElementByLocator(By locator){
        return driver.findElement(locator);
    }

    public static void fillElementWithData(By locator, String data){
        element =  driver.findElement(locator);
        element.clear();
        element.sendKeys(data);
    }

    public static void clickOnElement(By locator, String msg){
        element = driver.findElement(locator);
        tryToWaitForElementToBeClickable(wait, locator, msg);
        element.click();
    }

    public static void moveToElement(By locator, String msg){
        tryToWaitForPresenceOfElementLocated(wait, locator,msg);
        element = driver.findElement(locator);
        builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    public static void moveToElementAndClickOnIt(By locator, String msg, String msg2){
        tryToWaitForPresenceOfElementLocated(wait, locator,msg);
        element = driver.findElement(locator);
        tryToWaitForElementToBeClickable(wait, locator,msg2);
        builder = new Actions(driver);
        builder.moveToElement(element).click().build().perform();
    }

    public static void closePopUpWindow(){
        boolean popUpWindowIsShown = isElementPresent(popUpWindowLocator);
        if (popUpWindowIsShown){
            tryToWaitForElementToBeClickable(wait,popUpWindowCloseButtonLocator,"Pop-up window close button does not clickable");
            WebElement closeButton = driver.findElement(popUpWindowCloseButtonLocator);
            closeButton.click();
        }
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