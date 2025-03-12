package handlers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver getWebDriver(String browserName) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/paper/OneDrive/Рабочий стол/Diplom_Dmitriy_Zavgorodniy_14_90/Diplom_3/src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        switch (browserName) {
            case "chrome":
                return new ChromeDriver(options);

            case "yandex":
                return new ChromeDriver(options.setBinary("C:/Users/paper/AppData/Local/Yandex/YandexBrowser/Application/browser.exe"));

            default:
                throw new RuntimeException("Incorrect browser name");
        }
    }
}
