package praktikum.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import praktikum.models.Ad;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AdFormPage {

    private final SelenideElement nameInput = $("input[name='name']");
    private final SelenideElement categoryArrow = $x("//input[@name='category']/following-sibling::button");
    private final SelenideElement cityArrow = $x("//input[@name='city']/following-sibling::button");
    private final SelenideElement descriptionInput = $("textarea[name='description']");
    private final SelenideElement priceInput = $("input[name='price']");
    private final SelenideElement publishButton = $x("//button[text()='Опубликовать']");
    private final SelenideElement saveButton = $x("//button[text()='Сохранить изменения']");

    @Step("Ввести название «{name}»")
    public void setName(String name) {
        nameInput.shouldBe(visible).setValue(name);
    }

    @Step("Выбрать категорию «{category}»")
    public void selectCategory(String category) {
        categoryArrow.shouldBe(visible).click();
        dropdownOption(category).shouldBe(visible).click();
    }

    @Step("Выбрать город «{city}»")
    public void selectCity(String city) {
        cityArrow.shouldBe(visible).click();
        dropdownOption(city).shouldBe(visible).click();
    }

    @Step("Ввести описание")
    public void setDescription(String description) {
        descriptionInput.shouldBe(visible).setValue(description);
    }

    @Step("Ввести стоимость «{price}»")
    public void setPrice(String price) {
        priceInput.shouldBe(visible).setValue(price);
    }

    @Step("Нажать «Опубликовать»")
    public void clickPublish() {
        publishButton.shouldBe(visible).click();
    }

    @Step("Нажать «Сохранить изменения»")
    public void clickSave() {
        saveButton.shouldBe(visible).click();
    }

    @Step("Опубликовать объявление")
    public void publish(Ad ad) {
        setName(ad.getName());
        selectCategory(ad.getCategory());
        selectCity(ad.getCity());
        setDescription(ad.getDescription());
        setPrice(ad.getPrice() == null ? "" : String.valueOf(ad.getPrice()));
        clickPublish();
    }

    private SelenideElement dropdownOption(String value) {
        return $x("//button[.='" + value + "']");
    }
}
