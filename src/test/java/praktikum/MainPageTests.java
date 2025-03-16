package praktikum;

import handlers.Parameters;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.MainPage;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.containsString;

@DisplayName("Проверки конструктора (главной страницы)")
public class MainPageTests {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void startUp() {
        driver = getWebDriver();
        driver.get(Parameters.URL_MAIN_PAGE);
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка работы вкладки Булочки в разделе с ингредиентами")
    public void checkNavBunsIsSuccess() {
        mainPage.clickToppingsButton();
        mainPage.waitToppingsTabVisible();

        mainPage.clickBunsButton();
        mainPage.waitBunsTabVisible();

        MatcherAssert.assertThat(
                "Вкладка 'Булочки' не активна",
                mainPage.getBunsTabClass(),
                containsString("tab_tab_type_current__2BEPc") // Проверяем класс родительского элемента
        );
    }

    @Test
    @DisplayName("Проверка работы вкладки Соусы в разделе с ингредиентами")
    public void checkNavToppingsIsSuccess() {
        mainPage.clickToppingsButton();
        mainPage.waitToppingsTabVisible();

        MatcherAssert.assertThat(
                "Вкладка 'Соусы' не активна",
                mainPage.getToppingsTabClass(),
                containsString("tab_tab_type_current__2BEPc") // Проверяем класс родительского элемента
        );
    }

    @Test
    @DisplayName("Проверка работы вкладки Начинки в разделе с ингредиентами")
    public void checkNavFillingsIsSuccess() {
        mainPage.clickFillingsButton();
        mainPage.waitFillingsTabVisible();

        MatcherAssert.assertThat(
                "Вкладка 'Начинки' не активна",
                mainPage.getFillingsTabClass(),
                containsString("tab_tab_type_current__2BEPc") // Проверяем класс родительского элемента
        );
    }
}