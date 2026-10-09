package praktikum.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import praktikum.models.Ad;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class AdFormPage {

    private final SelenideElement form = $x(
            "//form[.//button[text()='Опубликовать' or text()='Сохранить изменения']]");
    private final SelenideElement nameInput = form.$("input[name='name']");
    private final SelenideElement descriptionInput = form.$("textarea[name='description']");
    private final SelenideElement priceInput = form.$("input[name='price']");
    private final SelenideElement publishButton = form.$x(".//button[text()='Опубликовать']");
    private final SelenideElement saveButton = form.$x(".//button[text()='Сохранить изменения']");

    @Step("Ввести название «{name}»")
    public void setName(String name) {
        nameInput.shouldBe(visible).setValue(name);
    }

    @Step("Ввести описание")
    public void setDescription(String description) {
        descriptionInput.shouldBe(visible).setValue(description);
    }

    @Step("Ввести стоимость «{price}»")
    public void setPrice(String price) {
        priceInput.shouldBe(visible).setValue(price);
    }

    @Step("Выбрать категорию «{category}»")
    public void setCategory(String category) {
        selectDropdown("category", category);
    }

    @Step("Выбрать состояние «{condition}»")
    public void setCondition(String condition) {
        SelenideElement option = form.$x(
                ".//label[text()='" + condition + "']/parent::div");
        if (option.$("input[type='radio']").isSelected()) {
            return;
        }
        option.$("div").shouldBe(visible).click();
    }

    @Step("Выбрать город «{city}»")
    public void setCity(String city) {
        selectDropdown("city", city);
    }

    @Step("Нажать «Опубликовать»")
    public void clickPublish() {
        publishButton.shouldBe(visible).click();
        publishButton.should(disappear);
    }

    @Step("Нажать «Сохранить изменения»")
    public void clickSave() {
        saveButton.shouldBe(visible).click();
        saveButton.should(disappear);
    }

    @Step("Опубликовать объявление")
    public void publish(Ad ad) {
        setName(ad.getName());
        setDescription(ad.getDescription());
        setPrice(ad.getPrice() == null ? "" : String.valueOf(ad.getPrice()));
        setCategory(ad.getCategory());
        setCondition(ad.getCondition());
        setCity(ad.getCity());
        clickPublish();
    }

    private void selectDropdown(String fieldName, String selected) {
        SelenideElement field = form.$("input[name='" + fieldName + "']");
        field.shouldBe(visible);
        if (selected.equals(field.getValue())) {
            return;
        }
        SelenideElement dropdown = field.parent().parent();
        dropdown.$("button").shouldBe(visible).click();
        dropdown.$x(".//button[.//span[text()='" + selected + "']]")
                .shouldBe(visible.because("В списке нет значения «" + selected + "»"))
                .click();
        field.shouldHave(value(selected).because("В поле не выбрано «" + selected + "»"));
    }
}
