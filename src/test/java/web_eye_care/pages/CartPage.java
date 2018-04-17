package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

import java.util.List;

public class CartPage extends BasePage{

    private static By shoppingCartFormLocator = By.id("shop-cart-form");
    private static By priceLocator = By.xpath("//td[@class='item-each']/span");
    private static By subtotalLocator = By.xpath("//td[@class='item-subtotal align-center']/span");
    private static By totalLocator = By.xpath("//tfoot//span[@class='txt-color shopping-total']");
    private static By quantityLocator = By.xpath("//td[@class='item-qty']//option[1]");
    private static By proceedToCheckoutLocator = By.xpath("//td[@class='cart-right']/button");

    private static By returningCustomerFormLocator = By.xpath("//form[@class='white-form shopping-cart-login']");
    private static By emailLocator = By.xpath("//input[@class='login-username']");
    private static By passwordLocator = By.xpath("//input[@class='login-password']");
    private static By loginAndCheckoutButtonLocator = By.xpath("//button[@class='btn m-check m-green login-checkout']");

    private static By cartListLocator = By.xpath("//table[@class='cart-table']/tbody/tr");

    private static By processingFeeLocator = By.xpath("//tfoot//span[@class='fee-value']");

    private static float price;
    private static float subtotal;
    private static float total;
    private static int quantity;

    private CartPage (){
    }

    @Step("Check if cart page is loaded")
    public static boolean isCartPageOpened(String url){
        boolean isShoppingCartFormVisible = tryToWaitForVisibilityOfElementLocated(wait, shoppingCartFormLocator,"Shopping cart form does not visible");

        if (!isShoppingCartFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    @Step("Proceed to checkout")
    public static void proceedToCheckout (){
        clickOnElement(proceedToCheckoutLocator, "Proceed to checkout button does not present", "Proceed to checkout button does not clickable");
    }

    @Step("Remember product price from Cart page")
    public static void rememberPrice() {
        price = getPriceFromLocator(priceLocator);
    }

    @Step("Get product price from Cart page")
    public static float getPrice() {
        return price;
    }

    @Step("Remember total price from Cart page")
    public static void rememberTotalPrice() {
        total = getPriceFromLocator(totalLocator);
    }

    @Step("Get total price from Cart page")
    public static float getTotalPrice() {
        return total;
    }

    @Step("Remember subtotal price from Cart page")
    public static void rememberSubtotalPrice() {
        subtotal = getPriceFromLocator(subtotalLocator);
    }

    @Step("Get subtotal price from Cart page")
    public static float getSubtotalPrice() {
        return subtotal;
    }

    @Step("Remember product quantity from Cart page")
    public static void rememberQuantity() {
        quantity = getIntFromLocator(quantityLocator);
    }

    @Step("Get subtotal price from Cart page")
    public static int getQuantity() {
        return quantity;
    }

    @Step("Check if price equals to the price from product page")
    public static boolean isPriceEqualsToPriceFromProductPage(){
        boolean isEqual = false;

        if(price == ProductPage.getPrice()){
            isEqual = true;
        }

        return isEqual;
    }

    @Step("Check if subtotal price equals to the price multiplied by the quantity")
    public static boolean isSubtotalEqualsToPriceMultipliedByQuantity(){
        boolean isEqual = false;

        float subtotalPrice = price;
        subtotalPrice = subtotalPrice * quantity;

        if(subtotalPrice == subtotal){
            isEqual = true;
        }

        return isEqual;
    }

    @Step("Check if total price equals to subtotal price")
    public static boolean isTotalEqualsToSubtotal(){
        boolean isEqual = false;

        if(total == subtotal){
            isEqual = true;
        }

        return isEqual;
    }

    @Step("Check if total price calculated right on cart page")
    public static float calculateSubtotal(){

        tryToWaitForVisibilityOfElementLocated(wait, cartListLocator,"Cart list does not visible");
        List<WebElement> cartList = driver.findElements(cartListLocator);
        int listSize = cartList.size();

        if (listSize == 0){
            return 0;
        }

        float subtotal = 0;
        float fee;

        By locator;

        tryToWaitForVisibilityOfElementLocated(wait, shoppingCartFormLocator,"Product form does not visible");
        for (int i = listSize; i > 0; i--){
            locator = By.xpath("//tr[" + i + "]/td[@class='item-subtotal align-center']/span");
            subtotal = subtotal + readSubtotalPrice(locator);
        }

        fee = readProcessingFee();
        subtotal = subtotal + fee;

        return subtotal;
    }

    private static float readSubtotalPrice (By locator) {
        return getPriceFromLocator(locator);
    }

    private static float readProcessingFee() {
        if (isElementPresent(processingFeeLocator)){
            return getFloatFromLocator(processingFeeLocator);
        }else{
            return 0;
        }
    }

    @Step("Check if returning customer form is visible")
    public static boolean isReturningCustomerFormVisible(){
        return tryToWaitForVisibilityOfElementLocated(wait, returningCustomerFormLocator,"Returning customer form does not visible");
    }

    @Step("Fill returning customer form and sending it in order to login and proceed to checkout")
    public static void loginAndCheckout(String email, String password){
        fillEmailField(email);
        fillPasswordField(password);
        sendReturningCustomerForm();
    }

    @Step("Fill email field with" + "{0}")
    private static void fillEmailField(String email){
        fillElementWithData(emailLocator, email);
    }

    @Step("Fill password field with" + "{0}")
    private static void fillPasswordField(String password){
        fillElementWithData(passwordLocator,password);
    }

    @Step("Confirm login and checkout on returning customer form")
    private static void sendReturningCustomerForm(){
        clickOnElement(loginAndCheckoutButtonLocator, "Login and checkout button does not present",
                "Login and checkout button does not clickable");
    }

    //--------------------------------------------- clean the cart

    @Step("Open Cart page")
    public static void goToTheCart(String url){
        driver.navigate().to(url);
    }

    @Step("Clean the cart")
    public static void cleanTheCart(){
        tryToWaitForVisibilityOfElementLocated(wait, shoppingCartFormLocator,"Product form does not visible");
        tryToWaitForElementToBeClickable(wait, proceedToCheckoutLocator, "Proceed to checkout button does not clickable");

        List<WebElement> cartList = driver.findElements(cartListLocator);
        int listSize = cartList.size();

        if (listSize == 0){
            return;
        }

        By locator;

        for (int i = listSize; i > 0; i--){
            locator = By.xpath("//table[@class='cart-table']/tbody/tr[" + i + "]/td[@class='item-remove']/div");
            moveToElementAndClickOnIt(locator,"Remove item " + i + "does not present",
                    "Remove item " + i + "does not clickable");
            tryToWaitForVisibilityOfElementLocated(wait, shoppingCartFormLocator,"Product form does not visible");
        }
    }

}
