package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import web_eye_care.base_classes.BasePage;

public class OrderPage extends BasePage {

    private static By billingFormLocator = By.id("billing-section");

    private OrderPage(){
    }

    @Step("Checking if billing page is loaded")
    public static boolean isProductCategoryPageOpened( String url){
        boolean isProductListVisible = tryToWaitForVisibilityOfElementLocated(wait, billingFormLocator,"Billing form does not visible");

        if (!isProductListVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

}
