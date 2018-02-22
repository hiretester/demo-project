package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Optional;
import web_eye_care.base_classes.BasePage;

public class ProductPage extends BasePage{

    private static By productFormLocator = By.xpath("//form[@name='MainForm']");
    private static By priceLocator = By.xpath("//div[@class='product-price']//span");
    private static By addToCartButtonLocator = By.xpath("//div[@class='add-to-cart']/button");

    private static float price;

    private ProductPage (){
    }

    @Step("Checking if product page is loaded")
    public static boolean isProductPageOpened(@Optional("http://hrqzq.qvfht.servertrust.com/Acuvue-Oasys-Contacts-p/2308.htm") String url){
        boolean isProductFormVisible = tryToWaitForVisibilityOfElementLocated(wait, productFormLocator,"Product form does not visible");

        if (!isProductFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    @Step("Add product to cart")
    public static void addToCart (){
        WebElement product = driver.findElement(addToCartButtonLocator);
        product.click();
    }

    @Step("Remember product price from Product category page")
    public static void setPrice () {
        WebElement productPrice = driver.findElement(priceLocator);
        String strPrice = productPrice.getText().trim();
        price = Float.valueOf(strPrice);
    }

    @Step("Get product price from Product category page")
    public static Float getPrice() {
        return price;
    }

    public static boolean isPriceEqualsToPriceFromProductCategoryPage(){
        boolean isEqual = false;

        if(price == ProductCategoryPage.getPrice()){
            isEqual = true;
        }

        return isEqual;
    }
}
