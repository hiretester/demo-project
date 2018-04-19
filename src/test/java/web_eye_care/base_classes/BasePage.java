package web_eye_care.base_classes;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    private static WebElement element;
    private static Actions builder;

    private static By popUpWindowLocator = By.xpath("//div[@id='usi_content']");
    private static By popUpWindowCloseButtonLocator = By.xpath("//div[@id='usi_close']");

    //-------------------General methods for all pages

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

    public static WebElement findElementByLocator(By locator){
        return waitForElement(wait,locator,"Element with locator " + locator.toString() + " does not located.");
    }

    public static void fillElementWithData(By locator, String data){
        element = waitForElement(wait,locator,"Element with locator " + locator.toString() + " does not located.");
        element.clear();
        element.sendKeys(data);
    }

    public static String getElementData(By locator, String msg){
        isElementVisible(wait, locator, msg);
        element = driver.findElement(locator);
        return element.getAttribute("value");
    }

    public static void clickOnElement(By locator, String msg, String msg2){
        element = waitForElement(wait,locator,msg);
        isElementClickable(wait, locator, msg2);
        element.click();
    }

    public static void moveToElement(By locator, String msg){
        element = waitForElement(wait, locator,msg);
        builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    public static void moveToElementAndClickOnIt(By locator, String msg, String msg2){
        element = waitForElement(wait, locator,msg);
        isElementClickable(wait, locator,msg2);
        builder = new Actions(driver);
        builder.moveToElement(element).click().build().perform();
    }

    public static void closePopUpWindow(){
        boolean popUpWindowIsShown = isElementPresent(popUpWindowLocator);
        if (popUpWindowIsShown){
            clickOnElement(popUpWindowCloseButtonLocator, "","Pop-up window close button does not clickable");
        }
    }

    public static float getPriceFromLocator (By locator){
        element = findElementByLocator(locator);
        String strPrice = element.getText().trim();
        if (strPrice.length()<2){
           System.out.println("Problems with price " + locator.toString());
           return -1;
        }
        strPrice = strPrice.substring(1);
        return  Float.valueOf(strPrice);
    }

    public static String getStringFromLocator (By locator){
        element = findElementByLocator(locator);
        return element.getText().trim();
    }

    public static int getIntFromLocator (By locator){
        return Integer.valueOf(getStringFromLocator(locator));
    }

    public static float getFloatFromLocator (By locator){
        return Float.valueOf(getStringFromLocator(locator));
    }

    //-------------------Expectations

    public static WebElement waitForElement(WebDriverWait wait, By locator, String msg){
        WebElement webElement;
        try{
           webElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }catch(TimeoutException te){
            webElement = null;
            System.out.println(msg);
        }
        return webElement;
    }

    public static boolean isElementClickable(WebDriverWait wait, By locator, String msg) {
        boolean isClickable = true;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        }catch (TimeoutException te){
            System.out.println(msg);
            isClickable = false;
        }
        return isClickable;
    }

    public static boolean isElementVisible(WebDriverWait wait, By locator, String msg) {
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