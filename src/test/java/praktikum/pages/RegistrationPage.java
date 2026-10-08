package praktikum.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import praktikum.models.User;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class RegistrationPage {

    private final SelenideElement form = $x("//form[contains(@class,'popUp_shell')]");
    private final SelenideElement title = form.$x(".//h1[text()='Зарегистрироваться']");
    private final SelenideElement emailInput = form.$("input[name='email']");
    private final SelenideElement passwordInput = form.$("input[name='password']");
    private final SelenideElement submitPasswordInput = form.$("input[name='submitPassword']");
    private final SelenideElement createAccountButton = form.$x(".//button[text()='Создать аккаунт']");
    private final SelenideElement alreadyHaveAccountButton = form.$x(".//button[text()='Уже есть аккаунт']");
    private final SelenideElement errorText = $x(
            "//form[contains(@class,'popUp_shell')]//input[@name='email']"
                    + "/following::span[contains(@class,'input_span')][1]");

    @Step("Проверить, что открыта форма регистрации")
    public boolean isDisplayed() {
        form.shouldBe(visible);
        return title.shouldBe(visible).isDisplayed();
    }

    @Step("Заполнить форму регистрации")
    public void fill(User user) {
        emailInput.shouldBe(visible).setValue(user.getEmail());
        passwordInput.shouldBe(visible).setValue(user.getPassword());
        submitPasswordInput.shouldBe(visible).setValue(user.getSubmitPassword());
    }

    @Step("Нажать «Создать аккаунт»")
    public void clickCreateAccount() {
        createAccountButton.shouldBe(visible).click();
    }

    @Step("Зарегистрировать пользователя")
    public void register(User user) {
        fill(user);
        clickCreateAccount();
    }

    @Step("Нажать «Уже есть аккаунт»")
    public void clickAlreadyHaveAccount() {
        alreadyHaveAccountButton.shouldBe(visible).click();
    }

    @Step("Получить текст ошибки под полем email")
    public String getErrorText() {
        return errorText.shouldBe(visible).getText();
    }
}
