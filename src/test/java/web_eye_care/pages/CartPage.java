package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

import java.util.List;

public class CartPage extends BasePage{

/* TODO: повыносить дублируемый код в универсальные методы, касается всего проекта
   привести подпись аллюр шагов к единому формату например : "Enter email", "Check price" , "Select item from the list" итп. ввести, сделать проверить и тп, также как при ручном описании бага
   написать оббертки под базовые действия управления элементами в классе BasePage.
   гораздо удобнее будет использовать аннотацию FyndBy (избавит от рутины) и дублирования кода
   */

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

    private static By cartListLocator = By.xpath("//table[@class='cart-table']/tbody/tr");

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
        clickOnElement(proceedToCheckoutLocator, "Proceed to checkout button does not clickable");
    }

    @Step("Remember product price from Cart page")
    public static void setPrice () {
        WebElement productPrice = findElementByLocator(priceLocator);
        String strPrice = productPrice.getText().trim();
        strPrice = strPrice.substring(1);
        price = Float.valueOf(strPrice);
    }

    @Step("Get product price from Cart page")
    public static float getPrice() {
        return price;
    }

    @Step("Remember total price from Cart page")
    public static void setTotalPrice () {
        WebElement totalProductPrice = findElementByLocator(totalLocator);
        String strPrice = totalProductPrice.getText().trim();
        strPrice = strPrice.substring(1);
        total = Float.valueOf(strPrice);
    }

    @Step("Get total price from Cart page")
    public static float getTotalPrice() {
        return total;
    }

    @Step("Remember subtotal price from Cart page")
    public static void setSubtotalPrice () {
        WebElement subtotalProductPrice = findElementByLocator(subtotalLocator);
        String strPrice = subtotalProductPrice.getText().trim();
        strPrice = strPrice.substring(1);
        subtotal = Float.valueOf(strPrice);
    }

    @Step("Get subtotal price from Cart page")
    public static float getSubtotalPrice() {
        return subtotal;
    }

    @Step("Remember product quantity from Cart page")
    public static void setQuantity() {
        WebElement productQuantity = findElementByLocator(quantityLocator);
        String strQuantity = productQuantity.getText().trim();
        quantity = Integer.valueOf(strQuantity);
    }

    @Step("Get subtotal price from Cart page")
    public static int getQuantity() {
        return quantity;
    }

    @Step("Checking if price equals to the price from product page")
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

    @Step("Filling returning customer form and sending it in order to login and proceed to checkout")
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
        clickOnElement(loginAndCheckoutButtonLocator, "Login and checkout button does not clickable");
    }

    //--------------------------------------------- clean the cart

    public static void goToTheCart(String url){
        driver.navigate().to(url);
    }

    public static void cleanTheCart(){
        tryToWaitForVisibilityOfElementLocated(wait, shoppingCartFormLocator,"Product form does not visible");
        tryToWaitForElementToBeClickable(wait, proceedToCheckoutLocator, "Proceed to checkout button does not clickable");

        List<WebElement> cartList = driver.findElements(cartListLocator);
        int listSize = cartList.size();

        if (listSize == 0){
            return;
        }

        for (int i = listSize; i > 0; i--){
            By locator = By.xpath("//table[@class='cart-table']/tbody/tr[" + i + "]/td[@class='item-remove']/div");
            moveToElementAndClickOnIt(locator,"Remove item " + i + "does not present",
                    "Remove item " + i + "does not clickable");
            tryToWaitForVisibilityOfElementLocated(wait, shoppingCartFormLocator,"Product form does not visible");
        }
    }

}
