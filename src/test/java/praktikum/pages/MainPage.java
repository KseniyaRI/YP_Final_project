package praktikum.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import praktikum.config.Config;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private final SelenideElement loginAndRegisterButton = $x("//button[text()='Вход и регистрация']");
    private final SelenideElement logoutButton = $x("//button[text()='Выйти']");
    private final SelenideElement profileName = $x("//h3[contains(@class,'profileText')]");
    private final SelenideElement placeAdButton = $x("//button[text()='Разместить объявление']");
    private final SelenideElement avatar = $(".svgSmall");

    @Step("Открыть главную страницу")
    public void open() {
        Selenide.open(Config.BASE_URL);
    }

    @Step("Нажать «Вход и регистрация»")
    public void clickLoginAndRegister() {
        loginAndRegisterButton.shouldBe(visible).click();
    }

    @Step("Проверить, что видна кнопка «Вход и регистрация»")
    public boolean isLoginAndRegisterButtonDisplayed() {
        return loginAndRegisterButton.shouldBe(visible).isDisplayed();
    }

    @Step("Нажать «Выйти»")
    public void clickLogout() {
        logoutButton.shouldBe(visible).click();
    }

    @Step("Проверить, что пользователь вошёл")
    public boolean isUserSignedIn() {
        return logoutButton.shouldBe(visible).isDisplayed();
    }

    @Step("Получить имя пользователя")
    public String getUserName() {
        return profileName.shouldBe(visible).getText();
    }

    @Step("Нажать «Разместить объявление»")
    public void clickPlaceAd() {
        placeAdButton.shouldBe(visible).click();
    }

    @Step("Открыть профиль")
    public void openProfile() {
        avatar.shouldBe(visible).click();
    }
}
