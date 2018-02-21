package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Optional;
import web_eye_care.base_classes.BasePage;

public class ProductCategoryPage extends BasePage{

    private static By productListLocator = By.xpath("//ul[@class='products-list two-columns products-container']");
    private static Float price;

    private ProductCategoryPage (){
    }

    @Step("Checking if product category page is loaded")
    public static boolean isProductCategoryPageOpened(@Optional("http://hrqzq.qvfht.servertrust.com/Acuvue-Disposable-Contact-Lenses-s/157.htm") String url){
        boolean isProductListVisible = tryToWaitForVisibilityOfElementLocated(wait, productListLocator,"Product list does not visible");

        if (!isProductListVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    @Step("Goes to product page")
    public static void goToProductPage (@Optional("//ul[@class='products-list two-columns products-container']/li[1]/a") String xpass){
        By productLocator = By.xpath(xpass);
        WebElement product = driver.findElement(productLocator);
        product.click();
    }

    @Step("Remember product price from Product category page")
    public static void setPrice (@Optional("//ul[@class='products-list two-columns products-container']/li[1]/div[@class='our-price']//span") String xpass) {
        By productPriceLocator = By.xpath(xpass);
        WebElement productPrice = driver.findElement(productPriceLocator);
        String strPrice = productPrice.getText().trim();
        price = Float.valueOf(strPrice);
    }

    @Step("Get product price from Product category page")
    public static Float getPrice() {
        return price;
    }
}
