package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

public class CartPage extends BasePage{

    private static WebElement emailField, passwordField, loginAndCheckoutButton;

    private static By shoppingCartFormLocator = By.id("shop-cart-form");
    private static By priceLocator = By.xpath("//td[@class='item-each']/span");
    private static By subtotalLocator = By.xpath("//td[@class='item-subtotal align-center']/span");
    private static By totalLocator = By.xpath("//tfoot//span[@class='txt-color shopping-total']");

    private static By quantityLocator = By.xpath("//td[@class='item-qty']//option[@value='2']");

    private static By proceedToCheckoutLocator = By.xpath("//td[@class='cart-right']/button");

    private static By returningCustomerFormLocator = By.xpath("//form[@class='white-form shopping-cart-login']");
    private static By emailLocator = By.xpath("//input[@class='login-username']");
    private static By passwordLocator = By.xpath("//input[@class='login-password']");
    private static By loginAndCheckoutButtonLocator = By.xpath("//button[@class='btn m-check m-green login-checkout']");


    private static float price;
    private static float subtotal;
    private static float total;

    private static int quantity;

    private CartPage (){
    }

    @Step("Checking if cart page is loaded")
    public static boolean isCartPageOpened(String url){
        boolean isShoppingCartFormVisible = tryToWaitForVisibilityOfElementLocated(wait, shoppingCartFormLocator,"Product form does not visible");

        if (!isShoppingCartFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    @Step("Proceed to checkout")
    public static void proceedToCheckout (){
        WebElement product = driver.findElement(proceedToCheckoutLocator);
        product.click();
    }

    @Step("Remember product price from Cart page")
    public static void setPrice () {
        WebElement productPrice = driver.findElement(priceLocator);
        String strPrice = productPrice.getText().trim();
        strPrice = strPrice.substring(1);
        price = Float.valueOf(strPrice);
    }

    @Step("Get product price from Cart page")
    public static Float getPrice() {
        return price;
    }

    @Step("Remember total price from Cart page")
    public static void setTotalPrice () {
        WebElement totalProductPrice = driver.findElement(totalLocator);
        String strPrice = totalProductPrice.getText().trim();
        strPrice = strPrice.substring(1);
        total = Float.valueOf(strPrice);
    }

    @Step("Get total price from Cart page")
    public static Float getTotalPrice() {
        return total;
    }

    @Step("Remember subtotal price from Cart page")
    public static void setSubtotalPrice () {
        WebElement subtotalProductPrice = driver.findElement(subtotalLocator);
        String strPrice = subtotalProductPrice.getText().trim();
        strPrice = strPrice.substring(1);
        subtotal = Float.valueOf(strPrice);
    }

    @Step("Get subtotal price from Cart page")
    public static Float getSubtotalPrice() {
        return subtotal;
    }

    @Step("Remember subtotal price from Cart page")
    public static void setQuantity() {
        WebElement productQuantity = driver.findElement(quantityLocator);
        String strQuantity = productQuantity.getText().trim();
        quantity = Integer.valueOf(strQuantity);
    }

    @Step("Get subtotal price from Cart page")
    public static Integer getQuantity() {
        return quantity;
    }

    public static boolean isPriceEqualsToPriceFromProductPage(){
        boolean isEqual = false;

        if(price == ProductPage.getPrice()){
            isEqual = true;
        }

        return isEqual;
    }

    @Step("Checking if subtotal price equals to the price multiplied by the quantity")
    public static boolean isSubtotalEqualsToPriceMultipliedByQuantity(){
        boolean isEqual = false;

        float subtotalPrice = price;
        subtotalPrice = subtotalPrice * quantity;

        if(subtotalPrice == subtotal){
            isEqual = true;
        }

        return isEqual;
    }

    @Step("Checking if total price equals to subtotal price")
    public static boolean isTotalEqualsToSubtotal(){
        boolean isEqual = false;

        if(total == subtotal){
            isEqual = true;
        }

        return isEqual;
    }

    @Step("Checking if returning customer form is visible")
    public static boolean isReturningCustomerFormVisible(){
        return tryToWaitForVisibilityOfElementLocated(wait, returningCustomerFormLocator,"Returning customer form does not visible");
    }

    @Step("Filling registration form and sending it in order to register a new user")
    public static void loginAndCheckout(String email, String password){
        fillEmailField(email, emailLocator);
        fillPasswordField(password, passwordLocator);
        sendReturningCustomerForm();
    }

    @Step("Fill email field with" + "{0}")
    private static void fillEmailField(String email, By locator){
        emailField = driver.findElement(locator);
        emailField.clear();
        emailField.sendKeys(email);
    }

    @Step("Fill password field with" + "{0}")
    private static void fillPasswordField(String password, By locator){
        passwordField = driver.findElement(locator);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    @Step("Confirm login and checkout on returning customer form")
    private static void sendReturningCustomerForm(){
        loginAndCheckoutButton = driver.findElement(loginAndCheckoutButtonLocator);
        loginAndCheckoutButton.click();
    }
}
