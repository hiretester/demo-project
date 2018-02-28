package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

public class ProductCategoryPage extends BasePage{

    private static By productListLocator = By.xpath("//ul[@class='products-list two-columns products-container']");
    private static By productLocator = By.xpath("//ul[@class='products-list two-columns products-container']/li[1]/a");
    private static By productPriceLocator = By.xpath("//ul[@class='products-list two-columns products-container']/li[1]/div[@class='our-price']//span");

    private static float price;

    private ProductCategoryPage (){
    }

    @Step("Checking if product category page is loaded")
    public static boolean isProductCategoryPageOpened(String url){
        boolean isProductListVisible = tryToWaitForVisibilityOfElementLocated(wait, productListLocator,"Product list does not visible");

        if (!isProductListVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    @Step("Goes to product page")
    public static void goToProductPage (){
        clickOnElement(productLocator, "Product does not clickable");
    }

    @Step("Remember product price from Product category page")
    public static void setPrice () {
        WebElement productPrice = findElementByLocator(productPriceLocator);
        String strPrice = productPrice.getText().trim();
        price = Float.valueOf(strPrice);
    }

    @Step("Get product price from Product category page")
    public static float getPrice() {
        return price;
    }
}

