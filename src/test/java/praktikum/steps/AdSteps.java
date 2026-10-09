package praktikum.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import praktikum.api.AdApi;
import praktikum.context.ScenarioContext;
import praktikum.data.AdGenerator;
import praktikum.models.Ad;
import praktikum.pages.AdFormPage;
import praktikum.pages.AdPage;
import praktikum.pages.MainPage;
import praktikum.pages.ProfilePage;

public class AdSteps {

    private final ScenarioContext context;
    private final MainPage mainPage;
    private final ProfilePage profilePage;
    private final AdFormPage adFormPage;
    private final AdPage adPage;
    private final AdApi adApi;

    public AdSteps(ScenarioContext context, MainPage mainPage, ProfilePage profilePage,
                   AdFormPage adFormPage, AdPage adPage, AdApi adApi) {
        this.context = context;
        this.mainPage = mainPage;
        this.profilePage = profilePage;
        this.adFormPage = adFormPage;
        this.adPage = adPage;
        this.adApi = adApi;
    }

    @When("the user publishes an ad in the {string} category")
    public void publishAd(String category) {
        Ad ad = AdGenerator.randomAd();
        ad.setCategory(category);
        context.setAd(ad);
        mainPage.clickPlaceAd();
        adFormPage.publish(ad);
        openMyAds();
        profilePage.openAd(ad.getName());
        context.setAdId(adPage.readId());
        mainPage.open();
    }

    @Then("the ad appears in the user's ads")
    public void adAppearsInUsersAds() {
        openMyAds();
        profilePage.adShouldBeDisplayed(context.getAd().getName(),
                "Объявление не отображается в разделе «Мои объявления»");
    }

    @Given("the user has a published ad")
    public void userHasPublishedAd() {
        if (context.getToken() == null) {
            throw new IllegalStateException("Нет токена для создания объявления");
        }
        Ad ad = AdGenerator.randomAd();
        Response response = adApi.create(context.getToken(), ad);
        if (response.statusCode() != 201) {
            throw new IllegalStateException(
                    "Не удалось создать объявление через API: "
                            + response.statusCode() + " " + response.asString());
        }
        ad.setId(createdId(response));
        context.setAd(ad);
    }

    @When("the user changes the title of the ad")
    public void changeTitle() {
        context.setNextAdTitle(AdGenerator.randomTitle());
        mainPage.openProfile();
        profilePage.clickEdit(context.getAd().getName());
        adFormPage.setName(context.getNextAdTitle());
        adFormPage.clickSave();
    }

    @Then("the ad is shown with the new title")
    public void adIsShownWithNewTitle() {
        String previousTitle = context.getAd().getName();
        openMyAds();
        profilePage.adShouldBeDisplayed(context.getNextAdTitle(),
                "Объявление с новым названием не отображается в «Мои объявления»");
        profilePage.adShouldNotBeDisplayed(previousTitle);
        context.getAd().setName(context.getNextAdTitle());
    }

    @When("the user deletes the ad")
    public void deleteAd() {
        String title = context.getAd().getName();
        int page = adApi.catalogPage(context.getToken(), title);
        mainPage.waitForHome();
        mainPage.openCatalogAd(title, page);
        adPage.clickDelete();
    }

    @Then("the ad is no longer in the user's ads")
    public void adIsGone() {
        openMyAds();
        profilePage.adShouldNotBeDisplayed(context.getAd().getName());
    }

    private void openMyAds() {
        mainPage.waitForHome();
        mainPage.openProfile();
    }

    private static int createdId(Response response) {
        Object rawId = response.jsonPath().get("id");
        if (!(rawId instanceof Number) || ((Number) rawId).intValue() <= 0) {
            throw new IllegalStateException(
                    "Не удалось создать объявление через API: "
                            + response.statusCode() + " " + response.asString());
        }
        return ((Number) rawId).intValue();
    }
}
