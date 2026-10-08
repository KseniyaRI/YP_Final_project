package praktikum.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import praktikum.models.User;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    private final SelenideElement form = $x("//form[contains(@class,'popUp_shell')]");
    private final SelenideElement title = form.$x(".//h1[text()='Войти']");
    private final SelenideElement emailInput = form.$("input[name='email']");
    private final SelenideElement passwordInput = form.$("input[name='password']");
    private final SelenideElement signInButton = form.$x(".//button[text()='Войти']");
    private final SelenideElement noAccountButton = form.$x(".//button[text()='Нет аккаунта']");

    @Step("Проверить, что открыта форма входа")
    public boolean isDisplayed() {
        form.shouldBe(visible);
        return title.shouldBe(visible).isDisplayed();
    }

    @Step("Заполнить форму входа")
    public void fill(User user) {
        emailInput.shouldBe(visible).setValue(user.getEmail());
        passwordInput.shouldBe(visible).setValue(user.getPassword());
    }

    @Step("Нажать «Войти»")
    public void clickSignIn() {
        signInButton.shouldBe(visible).click();
    }

    @Step("Войти")
    public void login(User user) {
        fill(user);
        clickSignIn();
    }

    @Step("Нажать «Нет аккаунта»")
    public void clickNoAccount() {
        noAccountButton.shouldBe(visible).click();
    }
}
