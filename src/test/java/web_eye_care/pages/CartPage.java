package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

public class CartPage extends BasePage{

    private static By shoppingCartFormLocator = By.id("shop-cart-form");
    private static By priceLocator = By.xpath("//td[@class='item-each']/span");
    private static By subtotalLocator = By.xpath("//td[@class='item-subtotal align-center']/span");
    private static By totalLocator = By.xpath("//tfoot//span[@class='txt-color shopping-total']");

    private static By quantityLocator = By.xpath("//td[@class='item-qty']//option[@value='2']");

    private static By proceedToCheckoutLocator = By.xpath("//td[@class='cart-right']/button");

    private static Float price;
    private static Float subtotal;
    private static Float total;

    private static Integer quantity;

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

        if(price.floatValue() == ProductPage.getPrice().floatValue()){
            isEqual = true;
        }

        return isEqual;
    }

    public static boolean isSubtotalEqualsToPriceMultipliedByQuantity(){
        boolean isEqual = false;

        float subtotalPrice = price.floatValue();
        subtotalPrice = subtotalPrice * quantity.intValue();

        if(subtotalPrice == subtotal.floatValue()){
            isEqual = true;
        }

        return isEqual;
    }
}
