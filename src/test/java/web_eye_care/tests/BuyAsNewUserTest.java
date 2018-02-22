package web_eye_care.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import web_eye_care.base_classes.BaseTest;
import web_eye_care.listeners.TestListener;
import web_eye_care.pages.*;

@Listeners({TestListener.class})
@Feature("Product ordering")
@Story("Ordering product as a new user")
public class BuyAsNewUserTest extends BaseTest{

    // test cases link
    // https://docs.google.com/spreadsheets/d/1XruN8JvT2ihSf0bA_86V0Zqp_kA9VmDi3b_cw9GDQZU/edit?pli=1#gid=0
    //Test case ID - BUY-01

    @Parameters({"mainPageUrl"})
    @Test()
    @Description("Opening main page of the site")
    public void testGoToMainPage(String url) {
        MainPage.goToMainPage(url);
        Assert.assertTrue(MainPage.isMainPageOpened(), "The site is unreachable");
    }

    @Parameters({"productCategoryPageUrl"})
    @Test(dependsOnMethods = "testGoToMainPage")
    @Description("Opening  \"Acuvue\" product category page")
    public void testGoToProductCategoryPage(String url){
        MainPage.goToProductCategoryPage();
        Assert.assertTrue(ProductCategoryPage.isProductCategoryPageOpened(url), "Product category page was not opened");
        ProductCategoryPage.setPrice();
    }

    @Parameters({"productPageUrl"})
    @Test(dependsOnMethods = "testGoToProductCategoryPage")
    @Description("Opening page of the first product from \"Acuvue\" category")
    public void testGoToProductPage(String url){
        ProductCategoryPage.goToProductPage();
        Assert.assertTrue(ProductPage.isProductPageOpened(url), "Product page was not opened");
        ProductPage.setPrice();
        Assert.assertTrue(ProductPage.isPriceEqualsToPriceFromProductCategoryPage(),
                "Price from product category page does not equal to the price from product page");
    }

    @Parameters({"cartPageUrl"})
    @Test(dependsOnMethods = "testGoToProductPage")
    @Description("Adding chosen product to the cart")
    public void testAddToCart(String url){
        ProductPage.addToCart();
        Assert.assertTrue(CartPage.isCartPageOpened(url), "Cart page was not opened");
        CartPage.setPrice();
        CartPage.setTotalPrice();
        CartPage.setQuantity();
        CartPage.setSubtotalPrice();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(CartPage.isPriceEqualsToPriceFromProductPage(),
                "Price from product page does not equal to the price from cart page");
        softAssert.assertTrue(CartPage.isSubtotalEqualsToPriceMultipliedByQuantity(),
                "Subtotal price does not equal to the price multiplied by quantity");
        softAssert.assertTrue(CartPage.isTotalEqualsToSubtotal(), "Subtotal price does not equal to the total price");
        softAssert.assertAll();
    }

    @Parameters({"orderPageUrl"})
    @Test(dependsOnMethods = "testAddToCart")
    @Description("Checking order page")
    public void testProceedToCheckout(String url){
        CartPage.proceedToCheckout();
        Assert.assertTrue(OrderPage.isOrderPageOpened(url),"Order page was not opened");
        OrderPage.setSubtotalPrice();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(OrderPage.isRegistrationFormVisible(),"Registration form does not visible");
        softAssert.assertTrue(OrderPage.isSubtotalEqualsToTotalFromCartPage(),
                "Subtotal price does not equal to the total price from cart page");

        softAssert.assertTrue(OrderPage.isPlaceOrderButtonClickable(), "\"Place Order\" button does not clickable");
        softAssert.assertAll();
    }
}
