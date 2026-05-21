package yandex.praktikum.api.listOrder;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ListOrder {
    private int courierId;
    private String nearestStation;
    private int limit;
    private int page;

    public ListOrder(int courierId, String nearestStation, int limit, int page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    public ListOrder(int courierId) {
        this.courierId = courierId;
    }
@Step("Отправляю GET запрос на получение заказов")
    public ValidatableResponse sendOrderCount(){
        return
                given()
                        .contentType(ContentType.JSON)
                        .log().all()
                        .when()
                        .queryParam("courierId", courierId)
                        .get("/api/v1/orders")
                        .then();
    }
@Step("Проверяю успешный ответ Status=200")
    public void validateResponseSuccess(ValidatableResponse response){
        response
                .statusCode(200)
                .body("orders", instanceOf(java.util.List.class))
                .body("pageInfo", notNullValue())
                .body("pageInfo.page", notNullValue())
                .body("pageInfo.total", notNullValue())
                .body("pageInfo.limit", notNullValue())
                .body("availableStations", instanceOf(java.util.List.class))
                .log().all();
    }
@Step("Проверяю неуспешный ответ NotFound Status=404")
    public void validateResponseNotFound(ValidatableResponse response){
        response
                .statusCode(404)
                .body("message", org.hamcrest.Matchers.containsString("не найден"))
                .log().all();
    }
    public int getId() {
        return courierId;
    }

    public void setId(int id) {
        this.courierId = id;
    }
}
