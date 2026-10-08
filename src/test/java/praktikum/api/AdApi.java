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

    public boolean exists(Integer id) {
        if (id == null) {
            return false;
        }
        setUp();
        int page = 1;
        int totalPages = 1;
        while (page <= totalPages) {
            Response response = RestAssured.given()
                    .get(Config.LISTINGS + "/" + page);
            if (response.statusCode() != 200) {
                return false;
            }
            List<Object> ids = response.jsonPath().getList("offers.id");
            if (containsId(ids, id)) {
                return true;
            }
            totalPages = response.jsonPath().getInt("totalPages");
            page++;
        }
        return false;
    }

    @Step("Удалить объявление")
    public Response delete(String token, int id) {
        setUp();
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(Config.LISTINGS + "/" + id);
    }

    private static boolean containsId(List<Object> ids, int id) {
        if (ids == null) {
            return false;
        }
        for (Object value : ids) {
            if (value != null && Integer.parseInt(value.toString()) == id) {
                return true;
            }
        }
        return false;
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
