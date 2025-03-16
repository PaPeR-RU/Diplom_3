package handlers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver getWebDriver() {
        String browserName = System.getProperty("browser", "chrome");

        System.setProperty("webdriver.chrome.driver", "D:/JAVA/Diplom_Dmitriy_Zavgorodniy_14_90/Diplom_3/src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        switch (browserName.toLowerCase()) {
            case "chrome":
                return new ChromeDriver(options);

            case "yandex":
                options.setBinary("C:/Users/paper/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
                return new ChromeDriver(options);

            default:
                throw new RuntimeException("Incorrect browser name: " + browserName);
        }
    }
}