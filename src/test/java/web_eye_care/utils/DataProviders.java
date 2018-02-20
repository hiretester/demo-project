package web_eye_care.utils;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider
    public static Object[][] registrationData() {
        return new Object[][]{
                {"test@spam4.me","qwerty"},};
    }
}
