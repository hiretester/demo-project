package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

/**
 * ProductPage class is a PageObject class that used to work with page that contains details about certain product
 */
public class ProductPage extends BasePage{

    private static By productFormLocator = By.xpath("//form[@name='MainForm']");
    private static By priceLocator = By.xpath("//div[@class='product-price']/b/span");
    private static By addToCartButtonLocator = By.xpath("//div[@class='add-to-cart']/button");

    private static By selectPrescriptionButtonLocator = By.xpath("//ul[@class = 'prescription-list']/li[4]/div[@class = 'pp-select']");

    private static float price;

    private ProductPage (){
    }

    /**
     * isProductPageOpened method is used to check if Product page was successfully opened
     * @param url - url address of the page
     * @return method returns true if element with productFormLocator locator is visible on the page and url address of
     * current page is equals to url address of Product page, otherwise returns false
     */
    @Step("Checking if product page is loaded")
    public static boolean isProductPageOpened(String url){
        boolean isProductFormVisible = isElementVisible(wait, productFormLocator,"Product form does not visible");

        if (!isProductFormVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    /**
     * addToCart method is used to add product to the cart and to open Cart page via clicking on "Proceed to checkout" button
     */
    @Step("Add product to cart")
    public static void addToCart (){
        clickOnElement(addToCartButtonLocator, "Add to cart button does not present", "Add to cart button does not clickable");
        selectPrescription();
    }

    /**
     * rememberPrice method is used to save price of the product from Product page in price static variable of ProductPage class
     */
    @Step("Remember product price from Product category page")
    public static void rememberPrice() {
        WebElement productPrice = driver.findElement(priceLocator);
        String strPrice = productPrice.getText().trim();
        price = Float.valueOf(strPrice);
    }

    /**
     * getPrice method is used to get value of price variable that contains price of the product from Product page
     * @return method returns value of price variable
     */
    @Step("Get product price from Product category page")
    public static float getPrice() {
        return price;
    }

    /**
     * selectPrescription method is used to select prescription variant "I Will Provide Prescription Later via Email or Fax"
     * to continue adding product to the cart
     */
    @Step("Chose prescription")
    public static void selectPrescription (){
        boolean isPrescriptionShown = isElementVisible(wait, selectPrescriptionButtonLocator,"Prescription form does not visible");
        if (isPrescriptionShown){
            clickOnElement(selectPrescriptionButtonLocator,"Select button does not present", "Select button does not clickable");
        }
    }
}
