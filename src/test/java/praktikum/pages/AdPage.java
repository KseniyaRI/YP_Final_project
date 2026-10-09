package praktikum.pages;

import com.codeborne.selenide.ObjectCondition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.conditions.webdriver.UrlCondition;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.webdriver;

public class AdPage {

    private static final Pattern LISTING_ID = Pattern.compile("/listing/(\\d+)");

    private final SelenideElement deleteButton = $x(
            "//div[contains(@class,'contact_shell')]"
                    + "[.//button[text()='Редактировать объявление']]"
                    + "//button[text()='Удалить']");

    @Step("Нажать «Удалить»")
    public void clickDelete() {
        deleteButton.shouldBe(visible.because(
                "Кнопка «Удалить» рядом с «Редактировать объявление» не отображается")).click();
        deleteButton.should(disappear);
    }

    @Step("Прочитать id объявления из адреса")
    public int readId() {
        webdriver().shouldHave(urlMatching(".*/listing/\\d+"));
        String url = WebDriverRunner.url();
        Matcher matcher = LISTING_ID.matcher(url);
        if (!matcher.find()) {
            throw new IllegalStateException("В адресе нет id объявления: " + url);
        }
        int id = Integer.parseInt(matcher.group(1));
        if (id <= 0) {
            throw new IllegalStateException("В адресе нет id объявления: " + url);
        }
        return id;
    }

    private static ObjectCondition<WebDriver> urlMatching(String regex) {
        return new UrlCondition("matching", regex) {
            private final Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean test(WebDriver webDriver) {
                return pattern.matcher(webDriver.getCurrentUrl()).find();
            }
        };
    }
}
