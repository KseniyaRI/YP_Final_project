package praktikum.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.openqa.selenium.OutputType;
import praktikum.api.AdApi;
import praktikum.config.Config;
import praktikum.context.ScenarioContext;

import java.io.ByteArrayInputStream;

public class Hooks {

    private final ScenarioContext context;
    private final AdApi adApi;

    public Hooks(ScenarioContext context, AdApi adApi) {
        this.context = context;
        this.adApi = adApi;
    }

    @Before
    public void setUpBrowser() {
        Configuration.browser = Config.BROWSER;
        Configuration.baseUrl = Config.BASE_URL;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15_000;
        Allure.parameter("browser", Config.BROWSER);
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                attachScreenshot();
            }
            try {
                deleteAdIfPresent();
            } catch (RuntimeException ex) {
                if (!scenario.isFailed()) {
                    throw ex;
                }
                String text = ex.getMessage() == null ? ex.toString() : ex.getMessage();
                Allure.addAttachment("Очистка объявления", text);
            }
        } finally {
            Selenide.closeWebDriver();
        }
    }

    private void attachScreenshot() {
        try {
            byte[] png = Selenide.screenshot(OutputType.BYTES);
            if (png != null && png.length > 0) {
                Allure.addAttachment("Скриншот", "image/png", new ByteArrayInputStream(png), "png");
            }
        } catch (RuntimeException ignored) {
            // браузер мог не открыться
        }
    }

    private void deleteAdIfPresent() {
        Integer id = context.getAdId();
        String token = context.getToken();
        if (id == null || id <= 0 || token == null || token.isEmpty()) {
            return;
        }
        if (!adApi.exists(token, id)) {
            return;
        }
        Response response = adApi.delete(token, id);
        int status = response.statusCode();
        if (status == 200 || (status == 500 && !adApi.exists(token, id))) {
            return;
        }
        throw new IllegalStateException(
                "Не удалось удалить объявление " + id + ": " + status + " " + response.asString());
    }
}
