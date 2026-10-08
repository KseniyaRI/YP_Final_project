package praktikum.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import praktikum.config.Config;

public class BaseApi {

    protected BaseApi() {
    }

    protected void setUp() {
        // reset сбрасывает старые фильтры, иначе AllureRestAssured накапливается и в отчёте появляется много одинаковых Request/Response
        RestAssured.reset();
        RestAssured.baseURI = Config.BASE_URL;
        RestAssured.config = RestAssuredConfig.config().encoderConfig(
                EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.filters(new AllureRestAssured());
    }

    protected RequestSpecification givenJson() {
        setUp();
        return RestAssured.given()
                .contentType(ContentType.JSON);
    }
}
