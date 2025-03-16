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
import pageobjects.AuthPage;
import pageobjects.ForgotPasswordPage;
import pageobjects.MainPage;
import pageobjects.RegisterPage;

import java.util.UUID;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Авторизация пользователя")
public class AuthTests {
    private WebDriver webDriver;
    private AuthPage authPage;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
    private String name, email, password;
    private ApiClient apiClient;

    @Before
    public void startUp() {
        webDriver = getWebDriver();
        webDriver.get(Parameters.URL_MAIN_PAGE);

        authPage = new AuthPage(webDriver);
        mainPage = new MainPage(webDriver);
        registerPage = new RegisterPage(webDriver);
        forgotPasswordPage = new ForgotPasswordPage(webDriver);

        name = "name";
        email = "email_" + UUID.randomUUID() + "@gmail.com";
        password = "pass_" + UUID.randomUUID();

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);

        apiClient = new ApiClient();
        apiClient.createUser(name, email, password);
    }

    @After
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
        if (apiClient != null) {
            apiClient.deleteTestUser(email, password);
        }
    }

    private void authUser() {
        authPage.setEmail(email);
        authPage.setPassword(password);
        authPage.clickAuthButton();
        authPage.waitFormSubmitted();
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void authFromMainIsSuccess() {
        mainPage.clickAuthButton();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void authFromLinkToProfileIsSuccess() {
        mainPage.clickLinkToProfile();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void authLinkFromRegFormIsSuccess() {
        webDriver.get(Parameters.URL_REGISTER_PAGE);

        registerPage.clickAuthLink();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void authLinkFromForgotPasswordFormIsSuccess() {
        webDriver.get(Parameters.URL_FORGOT_PASSWORD_PAGE);

        forgotPasswordPage.clickAuthLink();
        authPage.waitAuthFormVisible();

        authUser();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }
}