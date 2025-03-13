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
import pageobjects.MainPage;
import pageobjects.ProfilePage;

import java.util.UUID;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.*;

@DisplayName("Проверки личного кабинета пользователя")
public class ProfilePageTests {
    private WebDriver driver;
    private AuthPage authPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private String name, email, password;
    private ApiClient apiClient;

    @Before
    public void startUp() {
        driver = getWebDriver();
        driver.get(Parameters.URL_MAIN_PAGE);

        authPage = new AuthPage(driver);
        mainPage = new MainPage(driver);
        profilePage = new ProfilePage(driver);

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
        if (driver != null) {
            driver.quit();
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

    private void goToProfile() {
        driver.get(Parameters.URL_LOGIN_PAGE);
        authPage.waitAuthFormVisible();

        authUser();

        mainPage.clickLinkToProfile();
        profilePage.waitAuthFormVisible();
    }

    @Test
    @DisplayName("Проверка перехода по клику на «Личный кабинет»")
    public void checkLinkToProfileIsSuccess() {
        goToProfile();

        MatcherAssert.assertThat(
                "Некорректный URL страницы Личного кабинета",
                driver.getCurrentUrl(),
                containsString("/account/profile")
        );
    }

    @Test
    @DisplayName("Проверка перехода из личного кабинета по клику на «Конструктор»")
    public void checkLinkToConstructorIsSuccess() {
        goToProfile();

        profilePage.clickLinkToConstructor();
        mainPage.waitHeaderIsVisible();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Проверка перехода из личного кабинета по клику на логотип Stellar Burgers")
    public void checkLinkOnLogoIsSuccess() {
        goToProfile();

        profilePage.clickLinkOnLogo();
        mainPage.waitHeaderIsVisible();

        MatcherAssert.assertThat(
                "Ожидается надпись «Оформить заказ» на кнопке в корзине",
                mainPage.getBasketButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Проверка выхода из личного кабинета по клику на кнопку Выйти")
    public void checkLinkLogOutIsSuccess() {
        goToProfile();

        profilePage.clickLogoutLink();
        authPage.waitAuthFormVisible();

        MatcherAssert.assertThat(
                "Некорректный URL страницы Авторизации",
                driver.getCurrentUrl(),
                containsString("/login")
        );
    }
}