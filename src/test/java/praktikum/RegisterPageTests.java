package praktikum;

import handlers.ApiClient;
import handlers.Parameters;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.RegisterPage;

import java.util.UUID;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Регистрация пользователя")
public class RegisterPageTests {
    private WebDriver webDriver;
    private RegisterPage registerPage;
    private String email, name, password;

    @Before
    public void startUp() {
        webDriver = getWebDriver();
        webDriver.get(Parameters.URL_REGISTER_PAGE);
        registerPage = new RegisterPage(webDriver);

        email = "email_" + UUID.randomUUID() + "@gmail.com";
        name = "name";
        password = "pass_" + UUID.randomUUID();

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);
    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
        new ApiClient().deleteTestUser(email, password);
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void registerNewUserIsSuccess() {
        registerPage.setEmail(email);
        registerPage.setName(name);
        registerPage.setPassword(password);

        registerPage.clickRegisterButton();

        registerPage.waitFormSubmitted("Вход");

        checkFormReload();
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    public void registerNewUserLowPasswordIsFailed() {
        registerPage.setEmail(email);
        registerPage.setName(name);
        registerPage.setPassword(password.substring(0, 3));

        registerPage.clickRegisterButton();

        registerPage.waitErrorIsVisible();

        checkErrorMessage();
    }

    private void checkFormReload() {
        MatcherAssert.assertThat(
                "Форма регистрации не перезагрузилась",
                webDriver.getCurrentUrl(),
                containsString("/login")
        );
    }

    private void checkErrorMessage() {
        MatcherAssert.assertThat(
                "Некорректное сообщение об ошибке",
                registerPage.getErrorMessage(),
                equalTo("Некорректный пароль")
        );
    }
}