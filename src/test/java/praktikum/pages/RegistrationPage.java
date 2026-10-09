package praktikum.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import praktikum.models.User;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class RegistrationPage {

    private final SelenideElement form = $x("//form[.//button[text()='Создать аккаунт']]");
    private final SelenideElement emailInput = form.$("input[name='email']");
    private final SelenideElement passwordInput = form.$("input[name='password']");
    private final SelenideElement submitPasswordInput = form.$("input[name='submitPassword']");
    private final SelenideElement createAccountButton = form.$x(".//button[text()='Создать аккаунт']");
    private final SelenideElement errorText = form.$x(
            ".//div[.//input[@name='email']][not(.//input[@name='password'])]"
                    + "//span[contains(@class,'input_span')]");

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

    @Step("Проверить текст ошибки под полем email")
    public void errorShouldHaveText(String expected) {
        errorText.shouldHave(exactText(expected).because(
                "Под полем email должен быть текст «" + expected + "»"));
    }
}
