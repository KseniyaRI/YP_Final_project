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

    @Step("Авторизовать пользователя")
    public Response login(User user) {
        User credentials = new User(user.getEmail(), user.getPassword(), null);
        return givenJson()
                .body(credentials)
                .when()
                .post(Config.SIGNIN);
    }

    public String token(Response response) {
        String fromSignup = response.jsonPath().getString("access_token.access_token");
        if (fromSignup != null) {
            return fromSignup;
        }
        return response.jsonPath().getString("token.access_token");
    }
}
