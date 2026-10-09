package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.config.Config;
import praktikum.models.User;

public class UserApi extends BaseApi {

    @Step("Зарегистрировать пользователя")
    public Response register(User user) {
        return givenJson()
                .body(user)
                .when()
                .post(Config.SIGNUP);
    }

    public String token(Response response) {
        String fromSignup = response.jsonPath().getString("access_token.access_token");
        if (fromSignup != null) {
            return fromSignup;
        }
        return response.jsonPath().getString("token.access_token");
    }
}
