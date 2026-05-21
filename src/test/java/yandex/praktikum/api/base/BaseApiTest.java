package yandex.praktikum.api.base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;


public abstract class BaseApiTest {

    protected int createdCourierId = 0;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "https://qa-scooter.education-services.ru";
    }

    @AfterEach
    void tearDown() {
        if (createdCourierId != 0) {
            given()
                    .contentType(ContentType.JSON)
                    .pathParam("id", createdCourierId)
                    .body("{\"id\":\"" + createdCourierId + "\"}") // По вашему контракту
                    .delete("/api/v1/courier/{id}")
                    .then()
                    .statusCode(200);

            createdCourierId = 0;
        }
    }
}