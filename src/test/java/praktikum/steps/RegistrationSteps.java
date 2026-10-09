package praktikum.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import praktikum.context.ScenarioContext;
import praktikum.data.UserGenerator;
import praktikum.models.User;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegistrationPage;

public class RegistrationSteps {

    private final ScenarioContext context;
    private final MainPage mainPage;
    private final LoginPage loginPage;
    private final RegistrationPage registrationPage;

    public RegistrationSteps(ScenarioContext context, MainPage mainPage, LoginPage loginPage,
                             RegistrationPage registrationPage) {
        this.context = context;
        this.mainPage = mainPage;
        this.loginPage = loginPage;
        this.registrationPage = registrationPage;
    }

    @Given("the guest opens the registration form")
    public void openRegistrationForm() {
        mainPage.open();
        mainPage.clickLoginAndRegister();
        loginPage.clickNoAccount();
    }

    @When("the guest registers with a new email")
    public void registerWithNewEmail() {
        User user = UserGenerator.randomUser();
        context.setUser(user);
        registrationPage.register(user);
    }

    @When("the guest registers with the same email")
    public void registerWithSameEmail() {
        registrationPage.register(context.getUser());
    }
}
