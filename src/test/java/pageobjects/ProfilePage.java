package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By headerLinks = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText')]");
    private final By logoLink = By.xpath(".//div[starts-with(@class,'AppHeader_header__logo')]/a");
    private final By profileNavLink = By.xpath(".//a[contains(@class, 'Account_link_active')]");
    private final By logOutLink = By.xpath(".//nav[starts-with(@class, 'Account_nav')]/ul/li/button");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
    }

    @Step("Ожидание видимости формы авторизации")
    public void waitAuthFormVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileNavLink));
    }

    @Step("Ожидание, пока кнопка станет кликабельной")
    public void waitButtonIsClickable() {
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(modalOverlay)));
    }

    @Step("Нажатие на ссылку 'Конструктор'")
    public void clickLinkToConstructor() {
        waitButtonIsClickable();
        WebElement constructorLink = wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(headerLinks).get(0)));
        constructorLink.click();
    }

    @Step("Нажатие на логотип Stellar Burgers")
    public void clickLinkOnLogo() {
        waitButtonIsClickable();
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(logoLink));
        logo.click();
    }

    @Step("Нажатие на кнопку 'Выйти'")
    public void clickLogoutLink() {
        waitButtonIsClickable();
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(logOutLink));
        logoutButton.click();
    }
}