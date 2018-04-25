package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

/**
 * OrderPage class is a PageObject class that used to work with order page of the website
 */
public class OrderPage extends BasePage {

    private static By billingFormLocator = By.id("billing-section");
    private static By registrationFormLocator = By.id("registration-section");
    private static By placeOrderButtonLocator = By.id("btnSubmitOrder-v2");

    private static By subtotalLocator = By.xpath("//div[@class='order-summary-sub']//span");

    private static float subtotal;

    private OrderPage(){
    }

    /**
     * isOrderPageOpened method is used to check if Order page was successfully opened
     * @param url - url address of the page
     * @return method returns true if element with billingFormLocator locator is visible on the page and url
     * address of current page is equals to url address of Order page, otherwise returns false
     */
    @Step("Checking if order page is loaded")
    public static boolean isOrderPageOpened(String url){
        boolean isBillingFormVisible = isElementVisible(wait, billingFormLocator,"Billing form does not visible");

        if (!isBillingFormVisible){
            return false;
        }
        return driver.getCurrentUrl().equals(url);
    }

    /**
     * isRegistrationFormVisible method is used to check if registration form is shown on the Order page
     * @return method returns true if element with registrationFormLocator locator is visible on the page,
     * otherwise returns false
     */
    @Step("Checking if registration form is visible")
    public static boolean isRegistrationFormVisible(){
        return isElementVisible(wait, registrationFormLocator,"Registration form does not visible");
    }

    /**
     * isPlaceOrderButtonClickable method is used to check if "Place Order" button is clickable on the Order page
     * @return method returns true if button with placeOrderButtonLocator locator is clickable,
     * otherwise returns false
     */
    @Step("Checking if \"Place Order\" button is clickable")
    public static boolean isPlaceOrderButtonClickable(){

        return isElementClickable(wait, placeOrderButtonLocator,"\"Place Order\" button does not clickable");
    }

    /**
     * rememberSubtotalPrice method is used to save subtotal price (without delivery) of the order from Order page in
     * subtotal static variable of OrderPage class
     */
    @Step("Remember subtotal price from Cart page")
    public static void rememberSubtotalPrice() {
        WebElement subtotalPrice = findElementByLocator(subtotalLocator);
        String strPrice = subtotalPrice.getText().trim();
        strPrice = strPrice.substring(1);
        subtotal = Float.valueOf(strPrice);
    }

    /**
     * getSubtotalPrice method is used to get value of subtotal variable that contains subtotal price of order from Order page
     * @return method returns value of subtotal variable
     */
    public static float getSubtotalPrice(){
        return subtotal;
    }
}
