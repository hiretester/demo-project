package web_eye_care.listeners;

import web_eye_care.base_classes.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Attachment;


public class TestListener extends BaseTest implements ITestListener{
    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        saveScreenshotPNG(BaseTest.driver, getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    private static String getTestMethodName (ITestResult iTestResult){
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }
/*TODO: Всё, что касается работы с Аллюр отчетом выносим в отдельтный класс AllureReport:
    там реализуем методы на добавление скриншотов, текстов, html, pdf, json-ов и прочего, что тебе необходимо для того, чтобы сделать отчет максимально читабельным.
    в конце концов при аттаче чего- нибудь тебе необходимо будет сделать просто вызов екземпляра класса и нужный метод например: report.addScreenshot('название скриншота')

 */
    @Attachment (value = "{1}", type = "image/png")
    private byte[] saveScreenshotPNG(WebDriver driver, String imageName){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}