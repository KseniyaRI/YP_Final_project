package praktikum.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import praktikum.config.Config;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private static final Pattern PAGE_LABEL = Pattern.compile("(\\d+) из (\\d+)");

    private final SelenideElement loginAndRegisterButton = $x("//button[text()='Вход и регистрация']");
    private final SelenideElement logoutButton = $x("//button[text()='Выйти']");
    private final SelenideElement placeAdButton = $x("//button[text()='Разместить объявление']");
    private final SelenideElement avatar = $x(
            "//button[text()='Выйти']/parent::div/preceding-sibling::button");
    private final SelenideElement home = $x("//div[contains(@class,'homePage_homepageStyle')]");
    private final SelenideElement pageLabel = home.$x(
            ".//p[contains(@class,'spanGlobal')][contains(normalize-space(.),' из ')]");
    private final SelenideElement nextPageButton = pageLabel.$x(
            "./following-sibling::button[contains(@class,'arrowButton--right')]");

    @Step("Открыть главную страницу")
    public void open() {
        Selenide.open(Config.BASE_URL);
    }

    @Step("Нажать «Вход и регистрация»")
    public void clickLoginAndRegister() {
        loginAndRegisterButton.shouldBe(visible).click();
    }

    @Step("Проверить, что пользователь вошёл")
    public void userShouldBeSignedIn() {
        logoutButton.shouldBe(visible.because("Кнопка «Выйти» не отображается"));
    }

    @Step("Нажать «Разместить объявление»")
    public void clickPlaceAd() {
        placeAdButton.shouldBe(visible).click();
    }

    @Step("Дождаться главной страницы")
    public void waitForHome() {
        home.shouldBe(visible);
    }

    @Step("Открыть объявление «{title}» на странице {page} каталога")
    public void openCatalogAd(String title, int page) {
        waitForHome();
        showCatalogPage(page, title);
        SelenideElement cardTitle = catalogTitle(title);
        if (!cardTitle.is(visible)) {
            throw new IllegalStateException(missingCard(title));
        }
        cardTitle.closest(".card").$("div.about").click();
    }

    @Step("Открыть профиль")
    public void openProfile() {
        avatar.shouldBe(visible).click();
    }

    private void showCatalogPage(int page, String title) {
        waitUntilCatalogPageReady(title, page);
        int steps = 0;
        while (currentPage() != page) {
            if (currentPage() > page || !hasNextPage() || steps >= page) {
                throw new IllegalStateException(pageNotOpened(title, page));
            }
            advanceCatalogPage(title, page);
            steps++;
        }
    }

    private void waitUntilCatalogPageReady(String title, int page) {
        try {
            Selenide.Wait()
                    .withTimeout(Duration.ofMillis(Configuration.timeout))
                    .pollingEvery(Duration.ofMillis(200))
                    .until(driver -> catalogPageReady());
        } catch (TimeoutException ex) {
            throw new IllegalStateException(pageNotOpened(title, page));
        }
    }

    private boolean catalogPageReady() {
        try {
            if (!pageLabel.is(visible)) {
                return false;
            }
            String text = pageLabel.getText().trim();
            if (!PAGE_LABEL.matcher(text).matches()) {
                return false;
            }
            if (home.$("div.card").is(visible)) {
                return true;
            }
            if (hasNextPage()) {
                return true;
            }
            return text.startsWith("0 из");
        } catch (WebDriverException ex) {
            return false;
        }
    }

    private void advanceCatalogPage(String title, int targetPage) {
        int page = currentPage();
        String marker = firstCatalogTitle();
        nextPageButton.shouldBe(enabled).click();
        pageLabel.shouldHave(matchText(page + 1 + " из \\d+")
                .because(pageNotOpened(title, targetPage)));
        if (marker != null) {
            home.$("div.about h2").shouldNotHave(exactText(marker)
                    .because(pageNotOpened(title, targetPage)));
        }
    }

    private boolean hasNextPage() {
        return nextPageButton.is(visible) && nextPageButton.is(enabled);
    }

    private SelenideElement catalogTitle(String title) {
        return home.$$("div.about h2").findBy(exactText(title));
    }

    private String firstCatalogTitle() {
        SelenideElement title = home.$("div.about h2");
        if (!title.is(visible)) {
            return null;
        }
        return title.getText();
    }

    private int currentPage() {
        return pageNumber(1);
    }

    private int pageNumber(int group) {
        String text = pageLabel.shouldBe(visible).getText().trim();
        Matcher matcher = PAGE_LABEL.matcher(text);
        if (!matcher.matches()) {
            throw new IllegalStateException("Нет номера страницы каталога: " + text);
        }
        return Integer.parseInt(matcher.group(group));
    }

    private static String missingCard(String title) {
        return "В каталоге нет объявления «" + title + "»";
    }

    private static String pageNotOpened(String title, int page) {
        return "Не удалось открыть страницу " + page + " каталога для объявления «" + title + "»";
    }
}
