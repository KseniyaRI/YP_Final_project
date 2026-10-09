package praktikum.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import praktikum.api.UserApi;
import praktikum.context.ScenarioContext;
import praktikum.data.UserGenerator;
import praktikum.models.User;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegistrationPage;

public class CommonSteps {

    private final ScenarioContext context;
    private final UserApi userApi;
    private final MainPage mainPage;
    private final LoginPage loginPage;
    private final RegistrationPage registrationPage;

    public CommonSteps(ScenarioContext context, UserApi userApi, MainPage mainPage, LoginPage loginPage,
                       RegistrationPage registrationPage) {
        this.context = context;
        this.userApi = userApi;
        this.mainPage = mainPage;
        this.loginPage = loginPage;
        this.registrationPage = registrationPage;
    }

    @Given("an account with this email already exists")
    public void accountAlreadyExists() {
        registerUser();
    }

    @Given("the user has signed in")
    public void userHasSignedIn() {
        if (context.getUser() == null) {
            registerUser();
        }
        mainPage.open();
        mainPage.clickLoginAndRegister();
        loginPage.login(context.getUser());
    }

    @Then("the guest is signed in")
    @Then("the user is signed in")
    public void userIsSignedIn() {
        mainPage.userShouldBeSignedIn();
    }

    @Then("the error {string} is shown under the email field")
    public void errorIsShownUnderEmail(String expected) {
        registrationPage.errorShouldHaveText(expected);
    }

    private void registerUser() {
        User user = UserGenerator.randomUser();
        Response response = userApi.register(user);
        if (response.statusCode() != 201) {
            throw new IllegalStateException(
                    "Не удалось зарегистрировать пользователя через API: "
                            + response.statusCode() + " " + response.asString());
        }
        String token = userApi.token(response);
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("В ответе регистрации нет токена");
        }
        context.setUser(user);
        context.setToken(token);
    }
}
