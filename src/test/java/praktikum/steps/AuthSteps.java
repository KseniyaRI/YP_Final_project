package praktikum.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import praktikum.context.ScenarioContext;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;

public class AuthSteps {

    private final ScenarioContext context;
    private final MainPage mainPage;
    private final LoginPage loginPage;

    public AuthSteps(ScenarioContext context, MainPage mainPage, LoginPage loginPage) {
        this.context = context;
        this.mainPage = mainPage;
        this.loginPage = loginPage;
    }

    @Given("the user opens the sign in form")
    public void openSignInForm() {
        mainPage.open();
        mainPage.clickLoginAndRegister();
    }

    @When("the user signs in with a valid email and password")
    public void signIn() {
        loginPage.login(context.getUser());
    }
}
