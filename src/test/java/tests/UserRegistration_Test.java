package tests;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.MainPage;

public class UserRegistration_Test {

    // Ссылка на тест кейсы
    // https://docs.google.com/spreadsheets/d/1XruN8JvT2ihSf0bA_86V0Zqp_kA9VmDi3b_cw9GDQZU/edit?pli=1#gid=0

    @Parameters({"mainPageUrl"})
    @Test
    @Description("Открытие главной страници сайта.")
    public void testGoToMainPage(String url) {
        MainPage.goToMainPage(url);
        Assert.assertTrue(MainPage.isMainPageOpened(), "Сайт недоступен. Переход не был осуществлен.");
    }
}
