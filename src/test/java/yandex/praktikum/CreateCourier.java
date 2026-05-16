package yandex.praktikum;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourier {
    private String login;
    private String password;
    private String firstName;

    public CreateCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }


    public ValidatableResponse sendCreateCourier(){
        return given()
                .contentType(ContentType.JSON)
                .body(this)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public void validateResponseSuccess(ValidatableResponse response){
        response
                .statusCode(201)
                .and()
                .body("ok", equalTo(true))
                .log().all();
    }

    public void validateResponseConflict(ValidatableResponse response){
        response
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется."))
                .log().all();
    }

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
