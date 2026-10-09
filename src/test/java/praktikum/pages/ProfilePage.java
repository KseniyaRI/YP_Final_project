package praktikum.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {

    private final SelenideElement myAds = $x("//h1[text()='Мои объявления']/parent::div");

    @Step("Проверить, что объявление «{title}» отображается")
    public void adShouldBeDisplayed(String title, String message) {
        adTitle(title).shouldBe(visible.because(message));
    }

    @Step("Проверить, что объявления «{title}» нет в «Мои объявления»")
    public void adShouldNotBeDisplayed(String title) {
        adTitle(title).shouldNot(exist.because(
                "Объявление «" + title + "» осталось в «Мои объявления»"));
    }

    @Step("Нажать кнопку редактирования у объявления «{title}»")
    public void clickEdit(String title) {
        card(title).$("button.editButton").shouldBe(visible).click();
    }

    @Step("Открыть объявление «{title}»")
    public void openAd(String title) {
        adTitle(title).shouldBe(visible).click();
    }

    private ElementsCollection titles() {
        return myAds.$$("div.card div.about h2");
    }

    private SelenideElement adTitle(String title) {
        return titles().findBy(exactText(title));
    }

    private SelenideElement card(String title) {
        return adTitle(title).closest(".card");
    }
}
