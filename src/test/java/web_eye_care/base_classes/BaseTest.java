package web_eye_care.base_classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

// Методы для инициализации и работы с драйвером ------------------------------------------------------------------

/*
TODO
+Поднятие/закрытие браузера делаем до и после прохождения набора тестов. Дабы не терять время и рессурсы на поднятие каждого класса тестов
+Подготовку почты (генерацию емейла, подготовку почтового сервера выносим в предусловие для той ГРУППЫ/класса тестов, в которой необходим емейл
Все манипуляции с имейлом(методы страницы, генерация алиаса, манипуляции с почтовыми письмами выносятся отдельный класс и вызываются здесь по необходимости
+в начале лучше писать аннотируемые пред и пост условия а потом уже методы которые они используют, так удобнее будет
+ Добавить аллюр анотации для пред и пост условий тестов.*/

    private WebDriver getDriver(String browser, String driverPath) {
        switch (browser) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", driverPath);
                return new FirefoxDriver();
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", driverPath);
                return new ChromeDriver();
        }
    }

    private RemoteWebDriver getRemoteDriver(String hubUrl, String browser) throws MalformedURLException {
        DesiredCapabilities capabilities;
        switch (browser) {
            case "firefox":
                capabilities = DesiredCapabilities.firefox();
                break;
            case "chrome":
            default:
                capabilities = DesiredCapabilities.chrome();
                break;
        }
        return new RemoteWebDriver(new URL(hubUrl), capabilities);
    }

    @BeforeTest
    @Parameters({"selenium.hub", "selenium.browser", "selenium.driver.path"})
    public void setUp(@Optional("") String hubURL, @Optional("chrome") String browser, @Optional("src/test/resources/drivers/chromedriver") String driverPath)
            throws MalformedURLException {

        driver = hubURL.isEmpty() ? getDriver(browser, driverPath) : getRemoteDriver(hubURL, browser);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);

        BasePage.driver = driver;
        BasePage.wait = wait;
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
