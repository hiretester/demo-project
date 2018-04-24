package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

/**
 * ProductCategoryPage class is a PageObject class that used to work with page that contains list of products from certain manufacturer
 */
public class ProductCategoryPage extends BasePage{

    private static By productListLocator = By.xpath("//ul[@class='products-list two-columns products-container']");
    private static By productLocator = By.xpath("//ul[@class='products-list two-columns products-container']/li[1]/a");
    private static By productPriceLocator = By.xpath("//ul[@class='products-list two-columns products-container']/li[1]/div[@class='our-price']//span");

    private static float price;

    private ProductCategoryPage (){
    }

    /**
     * isProductCategoryPageOpened method is used to check if ProductCategory page was successfully opened
     * @param url - url address of the page
     * @return method returns true if element with productListLocator locator is visible on the page and url address of
     * current page is equals to url address of ProductCategory page, otherwise returns false
     */
    @Step("Checking if product category page is loaded")
    public static boolean isProductCategoryPageOpened(String url){
        boolean isProductListVisible = isElementVisible(wait, productListLocator,"Product list does not visible");

        if (!isProductListVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    /**
     * goToProductPage method is used to open page with certain product specified by productLocator
     */
    @Step("Goes to product page")
    public static void goToProductPage (){
        clickOnElement(productLocator, "Product does not present", "Product does not clickable");
    }

    /**
     * rememberPrice method is used to save price of the product from ProductCategory page in price static variable of ProductCategoryPage class
     */
    @Step("Remember product price from Product category page")
    public static void rememberPrice() {
        WebElement productPrice = findElementByLocator(productPriceLocator);
        String strPrice = productPrice.getText().trim();
        price = Float.valueOf(strPrice);
    }

    /**
     * getPrice method is used to get value of price variable that contains price of the product from ProductCategory page
     * @return method returns value of price variable
     */
    @Step("Get product price from Product category page")
    public static float getPrice() {
        return price;
    }
}

