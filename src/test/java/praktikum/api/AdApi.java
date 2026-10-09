package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import praktikum.config.Config;
import praktikum.models.Ad;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class AdApi extends BaseApi {

    @Step("Создать объявление")
    public Response create(String token, Ad ad) {
        setUp();
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .multiPart(part("name", text(ad.getName())))
                .multiPart(part("category", text(ad.getCategory())))
                .multiPart(part("condition", text(ad.getCondition())))
                .multiPart(part("city", text(ad.getCity())))
                .multiPart(part("description", text(ad.getDescription())))
                .multiPart(part("price", ad.getPrice() == null ? "" : String.valueOf(ad.getPrice())))
                .when()
                .post(Config.CREATE_LISTING);
    }

    public boolean exists(String token, int id) {
        int page = 1;
        int totalPages = 1;
        while (page <= totalPages) {
            setUp();
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get(Config.PROFILE_LISTINGS + "/" + page);
            if (response.statusCode() != 200) {
                throw new IllegalStateException(
                        "Не удалось проверить объявление " + id + ": " + response.statusCode()
                                + " " + response.asString());
            }
            List<Integer> ids = response.jsonPath().getList("offers.id", Integer.class);
            if (ids != null && ids.contains(id)) {
                return true;
            }
            totalPages = response.jsonPath().getInt("totalPages");
            page++;
        }
        return false;
    }

    @Step("Найти страницу каталога с объявлением «{title}»")
    public int catalogPage(String token, String title) {
        int page = 1;
        int totalPages = 1;
        while (page <= totalPages) {
            setUp();
            Response response = RestAssured.given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get(Config.LISTINGS + "/" + page);
            if (response.statusCode() != 200) {
                throw new IllegalStateException(
                        "Не удалось прочитать страницу " + page + " каталога: "
                                + response.statusCode() + " " + response.asString());
            }
            List<String> names = response.jsonPath().getList("offers.name", String.class);
            if (names != null && names.contains(title)) {
                return page;
            }
            totalPages = response.jsonPath().getInt("totalPages");
            page++;
        }
        throw new IllegalStateException("В каталоге нет объявления «" + title + "»");
    }

    @Step("Удалить объявление")
    public Response delete(String token, int id) {
        setUp();
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(Config.LISTINGS + "/" + id);
    }

    private static MultiPartSpecification part(String name, String value) {
        return new MultiPartSpecBuilder(value)
                .controlName(name)
                .charset(StandardCharsets.UTF_8)
                .build();
    }

    private static String text(String value) {
        return value == null ? "" : value;
    }
}
