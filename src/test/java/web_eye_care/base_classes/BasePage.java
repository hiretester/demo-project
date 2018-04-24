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

/**
 * BasePage is an abstract class that contains general methods for working with elements on the web page
 *
 */
public abstract class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    private static WebElement element;
    private static Actions builder;

    private static By popUpWindowLocator = By.xpath("//div[@id='usi_content']");
    private static By popUpWindowCloseButtonLocator = By.xpath("//div[@id='usi_close']");

    //-------------------General methods for all pages

    /**
     * isElementPresent method is searching for element without implicitly wait
     * @param locator - the locator of an element we need to find
     * @return method returns true if element is located on the page, otherwise returns false
     */
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

    /**
     * findElementByLocator method is searching for element with explicitly wait
     * @param locator - locator of an element we need to find
     * @return method returns WebElement object or null if element was not found
     */
    public static WebElement findElementByLocator(By locator){
        return waitForElement(wait,locator,"Element with locator " + locator.toString() + " does not located.");
    }

    /**
     * fillElementWithData method finds element by locator and fill it with data as if it was typed from the keyboard
     * used to enter data to text fields
     * @param locator - locator of an element we need to find
     * @param data - data to fill element with
     */
    public static void fillElementWithData(By locator, String data){
        element = waitForElement(wait,locator,"Element with locator " + locator.toString() + " does not located.");
        element.clear();
        element.sendKeys(data);
    }

    /**
     * getElementData method finds element by locator and returns its value
     * @param locator - locator of an element
     * @param msg - message that will be printed on the console if the element was not found
     * @return method returns value attribute of the element
     */
    public static String getElementData(By locator, String msg){
        isElementVisible(wait, locator, msg);
        element = driver.findElement(locator);
        return element.getAttribute("value");
    }

    /**
     * clickOnElement method finds element by locator and click on it
     * @param locator - locator of an element
     * @param msg - message that will be printed on the console if the element was not found
     * @param msg2 - message that will be printed on the console if the element is not clickable
     */
    public static void clickOnElement(By locator, String msg, String msg2){
        element = waitForElement(wait,locator,msg);
        isElementClickable(wait, locator, msg2);
        element.click();
    }

    /**
     * moveToElement method finds element by locator and move the focus to it
     * @param locator - locator of an element
     * @param msg - message that will be printed on the console if the element was not found
     */
    public static void moveToElement(By locator, String msg){
        element = waitForElement(wait, locator,msg);
        builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    /**
     * moveToElementAndClickOnIt method finds element by locator, move the focus to it and click on it
     * @param locator - locator of an element
     * @param msg - message that will be printed on the console if the element was not found
     * @param msg2 - message that will be printed on the console if the element is not clickable
     */
    public static void moveToElementAndClickOnIt(By locator, String msg, String msg2){
        element = waitForElement(wait, locator,msg);
        isElementClickable(wait, locator,msg2);
        builder = new Actions(driver);
        builder.moveToElement(element).click().build().perform();
    }

    /**
     * closePopUpWindow method is used to close pop up window with popUpWindowLocator locator
     */
    public static void closePopUpWindow(){
        boolean popUpWindowIsShown = isElementPresent(popUpWindowLocator);
        if (popUpWindowIsShown){
            clickOnElement(popUpWindowCloseButtonLocator, "","Pop-up window close button does not clickable");
        }
    }

    /**
     * getPriceFromLocator method finds element by locator and tries to retrieve price from the text of element
     * It is expected that price starts with '$' symbol, so first symbol is cropped and the result string is converting
     * to float
     * @param locator - locator of an element
     * @return method returns float value of price or -1 if price was not retrieved
     */
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

    /**
     * getStringFromLocator method finds element by locator and returns its text
     * @param locator - locator of an element
     * @return method returns String value of element text
     */
    public static String getStringFromLocator (By locator){
        element = findElementByLocator(locator);
        return element.getText().trim();
    }

    /**
     * getIntFromLocator method finds element by locator and returns integer value of its text
     * @param locator - locator of an element
     * @return method returns int value of element text
     */
    public static int getIntFromLocator (By locator){
        return Integer.valueOf(getStringFromLocator(locator));
    }

    /**
     * getFloatFromLocator method finds element by locator and returns float value of its text
     * @param locator - locator of an element
     * @return method returns float value of element text
     */
    public static float getFloatFromLocator (By locator){
        return Float.valueOf(getStringFromLocator(locator));
    }

    //-------------------Expectations

    /**
     * waitForElement method waits until presence of element is located on the page for preset time and returns element
     * or null if element was not located
     * @param wait - WebDriverWait object with preset time for waiting
     * @param locator - locator of an element
     * @param msg - message that will be printed on the console if element was not found
     * @return method returns WebElement object or null if element was not found
     */
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

    /**
     * isElementClickable method waits for element to be clickable for preset time and returns boolean value of is element
     * clickable or not
     * @param wait - WebDriverWait object with preset time for waiting
     * @param locator - locator of an element
     * @param msg - message that will be printed on the console if element isn`t clickable
     * @return method returns true if element is clickable, otherwise returns false
     */
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

    /**
     * isElementVisible method waits for element to be visible for preset time and returns boolean value of element visibility
     * @param wait - WebDriverWait object with preset time for waiting
     * @param locator - locator of an element
     * @param msg - message that will be printed on the console if element isn`t visible
     * @return method returns true if element is visible, otherwise returns false
     */
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