package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

import java.util.List;

/**
 * CartPage class is a PageObject class that used to work with cart page of the website
 */
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

    /**
     * isCartPageOpened method is used to check if Cart page was successfully opened
     * @param url - url address of the page
     * @return method returns true if element with shoppingCartFormLocator locator is visible on the page and url
     * address of current page is equals to url address of Cart page, otherwise returns false
     */
    @Step("Check if cart page is loaded")
    public static boolean isCartPageOpened(String url){
        boolean isShoppingCartFormVisible = isElementVisible(wait, shoppingCartFormLocator,"Shopping cart form does not visible");

        if (!isShoppingCartFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    /**
     * proceedToCheckout method is used to open Order page via clicking on "Proceed to checkout" button
     */
    @Step("Proceed to checkout")
    public static void proceedToCheckout (){
        clickOnElement(proceedToCheckoutLocator, "Proceed to checkout button does not present", "Proceed to checkout button does not clickable");
    }

    /**
     * rememberPrice method is used to save price of the product from Cart page in price static variable of CartPage class
     */
    @Step("Remember product price from Cart page")
    public static void rememberPrice() {
        price = getPriceFromLocator(priceLocator);
    }

    /**
     * getPrice method is used to get value of price variable that contains price of the product from Cart page
     * @return method returns value of price variable
     */
    @Step("Get product price from Cart page")
    public static float getPrice() {
        return price;
    }

    /**
     * rememberTotalPrice method is used to save total price of the order from Cart page in total static variable of CartPage class
     */
    @Step("Remember total price from Cart page")
    public static void rememberTotalPrice() {
        total = getPriceFromLocator(totalLocator);
    }

    /**
     * getTotalPrice method is used to get value of total variable that contains total price of the order from Cart page
     * @return method returns value of total variable
     */
    @Step("Get total price from Cart page")
    public static float getTotalPrice() {
        return total;
    }

    /**
     * rememberSubtotalPrice method is used to save subtotal price of product (price of product multiplied by its quantity)
     * from Cart page in subtotal static variable of CartPage class
     */
    @Step("Remember subtotal price from Cart page")
    public static void rememberSubtotalPrice() {
        subtotal = getPriceFromLocator(subtotalLocator);
    }

    /**
     * getSubtotalPrice method is used to get value of subtotal variable that contains subtotal price of product
     * (price of product multiplied by its quantity)from Cart page
     * @return method returns value of subtotal variable
     */
    @Step("Get subtotal price from Cart page")
    public static float getSubtotalPrice() {
        return subtotal;
    }

    /**
     * rememberQuantity method is used to save quantity of the product from Cart page in quantity static variable of CartPage class
     */
    @Step("Remember product quantity from Cart page")
    public static void rememberQuantity() {
        quantity = getIntFromLocator(quantityLocator);
    }

    /**
     * getQuantity method is used to get value of quantity variable that contains quantity of the product
     * @return method returns value of quantity variable
     */
    @Step("Get subtotal price from Cart page")
    public static int getQuantity() {
        return quantity;
    }

    /**
     * isSubtotalEqualsToPriceMultipliedByQuantity  method is used to check if subtotal price equals to the price multiplied by its quantity
     * @return method returns true if subtotal = price * quantity, otherwise returns false
     */
    @Step("Check if subtotal price equals to the price multiplied by the quantity")
    public static boolean isSubtotalEqualsToPriceMultipliedByQuantity(){
        boolean isEqual = false;

        float subtotalPrice = price * quantity;

        if(subtotalPrice == subtotal){
            isEqual = true;
        }

        return isEqual;
    }

    /**
     * calculateSubtotal method is used to calculate the sum of all subtotal prices of products in the cart and processing fee
     * @return method returns 0 if there is no products in the cart, otherwise returns sum of all subtotal prices and processing fee
     */
    @Step("Check if total price calculated right on cart page")
    public static float calculateSubtotal(){

        isElementVisible(wait, cartListLocator,"Cart list does not visible");
        List<WebElement> cartList = driver.findElements(cartListLocator);
        int listSize = cartList.size();

        if (listSize == 0){
            return 0;
        }

        float subtotal = 0;
        float fee;

        By locator;

        isElementVisible(wait, shoppingCartFormLocator,"Shopping cart form does not visible");
        for (int i = listSize; i > 0; i--){
            locator = By.xpath("//tr[" + i + "]/td[@class='item-subtotal align-center']/span");
            subtotal = subtotal + readSubtotalPrice(locator);
        }

        fee = readProcessingFee();
        subtotal = subtotal + fee;

        return subtotal;
    }

    //Get subtotal price from Cart page
    private static float readSubtotalPrice(By locator) {
        return getPriceFromLocator(locator);
    }

    //Get processing fee from Cart page
    private static float readProcessingFee() {
        if (isElementPresent(processingFeeLocator)){
            return getFloatFromLocator(processingFeeLocator);
        }else{
            return 0;
        }
    }

    /**
     * isReturningCustomerFormVisible method is used to check if form for login of returning customer is shown on the page
     * @return method returns true if element with returningCustomerFormLocator locator is visible on the page,
     * otherwise returns false
     */
    @Step("Check if returning customer form is visible")
    public static boolean isReturningCustomerFormVisible(){
        return isElementVisible(wait, returningCustomerFormLocator,"Returning customer form does not visible");
    }

    /**
     * loginAndCheckout method is used to fill returning customer form with email and password and to click on
     * "Login and checkout" button for moving to order page as registered user
     * @param email - email of returning customer
     * @param password - password of returning customer
     */
    @Step("Fill returning customer form and sending it in order to login and proceed to checkout")
    public static void loginAndCheckout(String email, String password){
        fillEmailField(email);
        fillPasswordField(password);
        sendReturningCustomerForm();
    }

    @Step("Fill email field")
    private static void fillEmailField(String email){
        fillElementWithData(emailLocator, email);
    }

    @Step("Fill password field")
    private static void fillPasswordField(String password){
        fillElementWithData(passwordLocator,password);
    }

    @Step("Confirm login and checkout on returning customer form")
    private static void sendReturningCustomerForm(){
        clickOnElement(loginAndCheckoutButtonLocator, "Login and checkout button does not present",
                "Login and checkout button does not clickable");
    }

    //--------------------------------------------- clean the cart

    /**
     * goToTheCart method is used to open Cart page via navigating to url of the cart page
     * @param url - url address of the page
     */
    @Step("Open Cart page")
    public static void goToTheCart(String url){
        driver.navigate().to(url);
    }

    /**
     * cleanTheCart method is used to delete products from the cart
     */
    @Step("Clean the cart")
    public static void cleanTheCart(){
        isElementVisible(wait, shoppingCartFormLocator,"Product form does not visible");
        isElementClickable(wait, proceedToCheckoutLocator, "Proceed to checkout button does not clickable");

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
            isElementVisible(wait, shoppingCartFormLocator,"Product form does not visible");
        }
    }

}
