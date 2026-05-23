package yandex.praktikum.api.authorizationCourier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthCourier {
    private String login;
    private String password;
    public AuthCourier(String login, String password) {
        this.login = login;
        this.password = password;
    }
    @Step("Выполняю POST запрос авторизации")
    public ValidatableResponse sendLogin(){
        return given()
                .contentType(ContentType.JSON)
                .body(this)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Проверяю, что курьер успешно авторизовался (статус 200)")
    public void validateResponseSuccess(ValidatableResponse responseInvalid){
        responseInvalid
                    .statusCode(200)
                    .and()
                    .body("id", notNullValue())
                    .log().all();
    }
    @Step("Проверяю ошибку при отсутствии передачи обязательных полей")
    public void validateResponseBadRequest(ValidatableResponse responseInvalid){
        responseInvalid
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"))
                .log().all();
    }

    @Step("Проверяю ошибку 404 Not Found")

    public void validateResponseNotFound(ValidatableResponse response){
        response
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"))
                .log().all();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
