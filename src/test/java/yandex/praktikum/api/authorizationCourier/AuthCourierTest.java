package yandex.praktikum.api.authorizationCourier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import yandex.praktikum.api.base.BaseApiTest;
import yandex.praktikum.api.createCourier.CreateCourier;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class AuthCourierTest extends BaseApiTest {

    @Test
    public void authCourierSuccessTest(){
        String login = RandomStringUtils.randomAlphanumeric(8);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(6);
        CreateCourier courier = new CreateCourier(login, password, firstName);
        courier.validateResponseSuccess(courier.sendCreateCourier());

        AuthCourier loginCourier = new AuthCourier(login, password);
        loginCourier.validateResponseSuccess(loginCourier.sendLogin());
    }
    @Test
    public void authCourierInvalidTest(){
        String login = RandomStringUtils.randomAlphabetic(6);
        String password = RandomStringUtils.randomAlphabetic(6);

        AuthCourier loginCourier = new AuthCourier(login,password);
        loginCourier.validateResponseNotFound(loginCourier.sendLogin());
    }
    @Test
    @Step("Проверяю ошибку при передаче пустого значения поля login ")
    public void authLoginNullTest(){
        AuthCourier loginCourier = new AuthCourier(null, RandomStringUtils.randomAlphanumeric(6));
        loginCourier.validateResponseBadRequest(loginCourier.sendLogin());
    }
    @Test
    @Disabled("Бэкенд зависает при передаче password=null")
    @Step("Проверяю ошибку при передаче пустого значения поля password")
    public void authPasswordNullTest(){
        AuthCourier loginCourier = new AuthCourier(RandomStringUtils.randomAlphanumeric(6), null);
        loginCourier.validateResponseBadRequest(loginCourier.sendLogin());

    }

    @Test
    @Step("Проверяю ошибку при отсутствии передачи обязательного поля login")
    public void authNoFiledLoginTest(){
        Map <String, String> body = new HashMap<>();
        body.put("password", "qwerty55");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post("/api/v1/courier/login")
                .then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Disabled("Бэкенд зависает при отсутствии передачи поля password")
    @Step("Проверяю ошибку при отсутствии передачи обязательного поля password")
    public void authNoFieldPasswordTest(){
        Map <String,String> body = new HashMap<>();
        body.put("login", "qwerty55");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(body)
                .post("/api/v1/courier/login")
                .then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));

    }
}
