package web_eye_care.tests;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import web_eye_care.base_classes.BaseTest;
import web_eye_care.pages.*;

public class BuyAsNewUserTest extends BaseTest{

    // test cases link
    // https://docs.google.com/spreadsheets/d/1XruN8JvT2ihSf0bA_86V0Zqp_kA9VmDi3b_cw9GDQZU/edit?pli=1#gid=0
    //Test case ID - Buy-01

    @Parameters({"mainPageUrl"})
    @Test()
    @Description("Opening main page of the site")
    public void testGoToMainPage(String url) {
        MainPage.goToMainPage(url);
        Assert.assertTrue(MainPage.isMainPageOpened(), "The site is unreachable");
    }

    @Parameters({"productXpath","productPriceXpath", "productCategoryPageUrl"})
    @Test(dependsOnMethods = "testGoToMainPage")
    @Description("1")
    public void testGoToProductCategoryPage(String productXpath, String productPriceXpath, String url){
        MainPage.goToProductCategoryPage(productXpath);
        Assert.assertTrue(ProductCategoryPage.isProductCategoryPageOpened(url), "");
        ProductCategoryPage.setPrice(productPriceXpath);
    }

    @Parameters({"xpath", "productPageUrl"})
    @Test(dependsOnMethods = "testGoToProductCategoryPage")
    @Description("2")
    public void testGoToProductPage(String xpath, String url){
        ProductCategoryPage.goToProductPage(xpath);
        Assert.assertTrue(ProductPage.isProductPageOpened(url), "");
        ProductPage.setPrice();
        Assert.assertTrue(ProductPage.isPriceEqualsToPriceFromProductCategoryPage(), "");
    }

    @Parameters({"cartPageUrl"})
    @Test(dependsOnMethods = "testGoToProductPage")
    @Description("3")
    public void testAddToCart(String url){
        ProductPage.addToCart();
        Assert.assertTrue(CartPage.isCartPageOpened(url), "");
        CartPage.setPrice();
        CartPage.setTotalPrice();
        CartPage.setQuantity();
        CartPage.setSubtotalPrice();
        Assert.assertTrue(CartPage.isPriceEqualsToPriceFromProductPage(), "");
        Assert.assertTrue(CartPage.isSubtotalEqualsToPriceMultipliedByQuantity(), "");
    }

    @Parameters({"orderPageUrl"})
    @Test(dependsOnMethods = "testAddToCart")
    @Description("4")
    public void testProceedToCheckout(String url){
        CartPage.proceedToCheckout();
        Assert.assertTrue(OrderPage.isProductCategoryPageOpened(url),"");
    }
}
