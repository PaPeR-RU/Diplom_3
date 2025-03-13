package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    private final By basketButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");
    private final By ingredientsButtons = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");
    private final By bunsTab = By.xpath(".//div[contains(@class, 'tab_tab_type_current__2BEPc') and contains(text(), 'Булочки')]");
    private final By toppingsTab = By.xpath(".//div[contains(@class, 'tab_tab_type_current__2BEPc') and contains(text(), 'Соусы')]");
    private final By fillingsTab = By.xpath(".//div[contains(@class, 'tab_tab_type_current__2BEPc') and contains(text(), 'Начинки')]");
    private final By header = By.xpath(".//main//h1");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
    }

    @Step("Нажатие на кнопку авторизации")
    public void clickAuthButton() {
        waitButtonIsClickable();
        driver.findElement(basketButton).click();
    }

    @Step("Ожидание, пока кнопка станет кликабельной")
    public void waitButtonIsClickable() {
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }

    @Step("Ожидание видимости заголовка")
    public void waitHeaderIsVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(header));
    }

    @Step("Получение текста кнопки корзины")
    public String getBasketButtonText() {
        return driver.findElement(basketButton).getText();
    }

    @Step("Нажатие на ссылку 'Личный кабинет'")
    public void clickLinkToProfile() {
        waitButtonIsClickable();
        driver.findElements(headerLinks).get(2).click();
    }

    @Step("Нажатие на кнопку 'Булочки'")
    public void clickBunsButton() {
        waitButtonIsClickable();
        getBunsButton().click();
    }

    @Step("Нажатие на кнопку 'Начинки'")
    public void clickToppingsButton() {
        waitButtonIsClickable();
        getToppingsButton().click();
    }

    @Step("Нажатие на кнопку 'Соусы'")
    public void clickFillingsButton() {
        waitButtonIsClickable();
        getFillingsButton().click();
    }

    public By getBunsTab() {
        return bunsTab;
    }

    public By getToppingsTab() {
        return toppingsTab;
    }

    public By getFillingsTab() {
        return fillingsTab;
    }

    public WebElement getBunsButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(ingredientsButtons).get(0)));
    }

    public WebElement getToppingsButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(ingredientsButtons).get(1)));
    }

    public WebElement getFillingsButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(ingredientsButtons).get(2)));
    }
}