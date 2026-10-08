package praktikum.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {

    private final SelenideElement myAds = $x(
            "//div[contains(@class,'profilePage_listningBlock')][.//h1[text()='Мои объявления']]");
    private final SelenideElement myAdsTitle = myAds.$x(".//h1[text()='Мои объявления']");

    @Step("Проверить, что открыт раздел «Мои объявления»")
    public boolean isMyAdsDisplayed() {
        return myAdsTitle.shouldBe(visible).isDisplayed();
    }

    @Step("Проверить, что объявление «{title}» отображается")
    public boolean isAdDisplayed(String title) {
        return card(title).shouldBe(visible).isDisplayed();
    }

    @Step("Проверить, что объявления «{title}» нет в «Мои объявления»")
    public void adShouldNotBeDisplayed(String title) {
        adTitle(title).shouldNot(exist);
    }

    @Step("Нажать кнопку редактирования у объявления «{title}»")
    public void clickEdit(String title) {
        card(title).$x(".//button[@class='editButton']").shouldBe(visible).click();
    }

    @Step("Открыть объявление «{title}»")
    public void openAd(String title) {
        adTitle(title).shouldBe(visible).click();
    }

    private SelenideElement adTitle(String title) {
        return myAds.$$("h2.h2").findBy(exactText(title));
    }

    private SelenideElement card(String title) {
        return adTitle(title).ancestor("div.card");
    }
}
