package praktikum;

import handlers.Parameters;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.MainPage;

import static handlers.WebDriverFactory.getWebDriver;
import static org.hamcrest.Matchers.containsString;

@DisplayName("Проверки конструктора (главной страницы)")
public class MainPageTests {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    @Before
    public void startUp() {
        driver = getWebDriver();
        wait = new WebDriverWait(driver, 30);
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
    @DisplayName("Проверка работы вкладки Булки в разделе с ингредиентами")
    public void checkNavBunsIsSuccess() {
        mainPage.clickToppingsButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.getToppingsTab()));

        mainPage.clickBunsButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.getBunsTab()));

        MatcherAssert.assertThat(
                "Вкладка 'Булки' не активна",
                driver.findElement(mainPage.getBunsTab()).getAttribute("class"),
                containsString("tab_tab_type_current__2BEPc")
        );
    }

    @Test
    @DisplayName("Проверка работы вкладки Соусы в разделе с ингредиентами")
    public void checkNavToppingsIsSuccess() {
        mainPage.clickToppingsButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.getToppingsTab()));

        MatcherAssert.assertThat(
                "Вкладка 'Соусы' не активна",
                driver.findElement(mainPage.getToppingsTab()).getAttribute("class"),
                containsString("tab_tab_type_current__2BEPc")
        );
    }

    @Test
    @DisplayName("Проверка работы вкладки Начинки в разделе с ингредиентами")
    public void checkNavFillingsIsSuccess() {
        mainPage.clickFillingsButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.getFillingsTab()));

        MatcherAssert.assertThat(
                "Вкладка 'Начинки' не активна",
                driver.findElement(mainPage.getFillingsTab()).getAttribute("class"),
                containsString("tab_tab_type_current__2BEPc")
        );
    }
}