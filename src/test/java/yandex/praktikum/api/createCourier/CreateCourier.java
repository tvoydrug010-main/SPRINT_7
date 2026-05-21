package yandex.praktikum.api.createCourier;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.qameta.allure.Step;

public class CreateCourier {
    private String login;
    private String password;
    private String firstName;

    public CreateCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Отправляю POST запрос на создание курьера")
    public ValidatableResponse sendCreateCourier(){
        return given()
                .contentType(ContentType.JSON)
                .body(this)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Проверяю, что курьер успешно создан (статус 201)")
    public void validateResponseSuccess(ValidatableResponse response){
        response
                .statusCode(201)
                .and()
                .body("ok", equalTo(true))
                .log().all();
    }
    @Step("Проверяю ошибку конфликта при повторной регистрации с повторяющимеся даннными")
    public void validateResponseConflict(ValidatableResponse response){
        response
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .log().all();
    }
    @Step("Проверяю ошибку при отсутствии передачи обязательных полей")
    public void validateResponseBadRequest(ValidatableResponse response){
        response
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
