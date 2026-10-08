package praktikum.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class AdPage {

    private final SelenideElement title = $x("//h1[@class='h1' or @class='h1Mobile']");
    private final SelenideElement editButton = $x("//button[text()='Редактировать объявление']");
    private final SelenideElement deleteButton = $x("//button[text()='Удалить']");

    @Step("Получить название объявления")
    public String getTitle() {
        return title.shouldBe(visible).getText();
    }

    @Step("Нажать «Редактировать объявление»")
    public void clickEdit() {
        editButton.shouldBe(visible).click();
    }

    @Step("Нажать «Удалить»")
    public void clickDelete() {
        deleteButton.shouldBe(visible).click();
    }

    @Step("Проверить, что видны кнопки владельца")
    public boolean isOwnerActionsDisplayed() {
        editButton.shouldBe(visible);
        deleteButton.shouldBe(visible);
        return true;
    }
}
