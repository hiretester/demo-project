package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

public class OrderPage extends BasePage {

    private static By billingFormLocator = By.id("billing-section");
    private static By registrationFormLocator = By.id("registration-section");
    private static By placeOrderButtonLocator = By.id("btnSubmitOrder-v2");

    private static By subtotalLocator = By.xpath("//div[@class='order-summary-sub']//span");

    private static float subtotal;

    private OrderPage(){
    }

    @Step("Checking if order page is loaded")
    public static boolean isOrderPageOpened(String url){
        boolean isBillingFormVisible = tryToWaitForVisibilityOfElementLocated(wait, billingFormLocator,"Billing form does not visible");

        if (!isBillingFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    @Step("Checking if registration form is visible")
    public static boolean isRegistrationFormVisible(){
        return tryToWaitForVisibilityOfElementLocated(wait, registrationFormLocator,"Registration form does not visible");
    }

    @Step("Checking if \"Place Order\" button is clickable")
    public static boolean isPlaceOrderButtonClickable(){

        return isElementClickable(wait, placeOrderButtonLocator,"\"Place Order\" button does not clickable");
    }

    @Step("Checking if subtotal price equals to total price from cart page")
    public static boolean isSubtotalEqualsToTotalFromCartPage(){
        boolean isEqual = false;

        if(subtotal == CartPage.getTotalPrice()){
            isEqual = true;
        }

        return isEqual;
    }

    @Step("Remember subtotal price from Cart page")
    public static void rememberSubtotalPrice() {
        WebElement subtotalPrice = findElementByLocator(subtotalLocator);
        String strPrice = subtotalPrice.getText().trim();
        strPrice = strPrice.substring(1);
        subtotal = Float.valueOf(strPrice);
    }

    public static float getSubtotalPrice(){
        return subtotal;
    }
}
