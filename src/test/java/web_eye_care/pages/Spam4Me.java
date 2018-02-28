package web_eye_care.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import web_eye_care.base_classes.BasePage;

import java.util.List;

public class Spam4Me extends BasePage{

    private static By emailBoxLocator = By.xpath("//div[@class='main-panel']");
    private static By emailButtonLocator = By.xpath("//span[@id='inbox-id']");
    private static By emailFieldLocator = By.xpath("//span[@id='inbox-id']/input");
    private static By setButtonLocator = By.xpath("//span[@id='inbox-id']/button[@class='save button small']");
    private static By emailListLocator = By.xpath("//tbody[@id='email_list']/tr");

    private Spam4Me(){
    }

    @Step("Trying to load email box page")
    public static void goToSpam4Me(String url){
        driver.navigate().to(url);
    }

    @Step("Checking if Email page is loaded")
    public static boolean isSpam4MePageOpened(String url){
        boolean isEmailBoxVisible = tryToWaitForVisibilityOfElementLocated(wait, emailBoxLocator,"Spam4me email box does not visible");

        if (!isEmailBoxVisible){
            return false;
        }

        return driver.getCurrentUrl().equals(url);
    }

    @Step("Creating email")
    public static void createEmail (String email){
        clickOnElement(emailButtonLocator, "Email button on Spam4me does not clickable");
        tryToWaitForVisibilityOfElementLocated(wait, emailFieldLocator, "Email field does not visible");
        fillElementWithData(emailFieldLocator,email);
        clickOnElement(setButtonLocator, "Set button on Spam4me does not clickable");
    }

    @Step("Checking if confirmation letter is received")
    public static boolean isConfirmationLetterInInbox(){

        waitForLetter();

        boolean isLetter = false;
        List<WebElement> emailList = driver.findElements(emailListLocator);

        int listSize = emailList.size();

        if (listSize == 0){
            return isLetter;
        }

        String emailSender;
        WebElement emailSenderElement;

        for (int i = listSize; i > 0; i--){
            By locator = By.xpath("//tbody[@id='email_list']/tr[" + i + "]/td[2]");
            emailSenderElement = driver.findElement(locator);
            emailSender = emailSenderElement.getText();

            if (emailSender.trim().equals("rewards@webeyecare.com")){
                isLetter = true;
                break;
            }
        }

        return isLetter;
    }

    @Step("Delay for letter receiving")
    private static void waitForLetter (){
        try {
            Thread.sleep(30000);
        }catch (InterruptedException e) {
            System.out.println(e);
        }
    }

}
