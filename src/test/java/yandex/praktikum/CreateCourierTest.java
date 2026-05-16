package yandex.praktikum;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CreateCourierTest {

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.education-services.ru";
    }

    @Test
    public void createCourierSuccessTest() {
        String login = RandomStringUtils.randomAlphanumeric(8);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(6);

        CreateCourier createCourier = new CreateCourier(login, password, firstName);
        createCourier.validateResponseSuccess(createCourier.sendCreateCourier());
    }

    @Test
    public void createSameCourierTest(){
        String login = RandomStringUtils.randomAlphanumeric(8);
        String password = "password123";
        String firstName = "Alex";
        CreateCourier firstCourier = new CreateCourier(login, password, firstName);
        firstCourier.sendCreateCourier();


        CreateCourier secondCourier = new CreateCourier(login, password, firstName);
        secondCourier.sendCreateCourier();
        secondCourier.validateResponseConflict(secondCourier.sendCreateCourier());
    }

    @Test
    public void createInvalidLoginCourierTest() {
        String login = "";
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphabetic(6);

        CreateCourier createCourier = new CreateCourier(login, password, firstName);
        createCourier.validateResponseBadRequest(createCourier.sendCreateCourier());
    }
    @Test
    public void createInvalidPasswordCourierTest() {
        String login = RandomStringUtils.randomAlphanumeric(8);
        String password = "";
        String firstName = RandomStringUtils.randomAlphabetic(6);

        CreateCourier createCourier = new CreateCourier(login, password, firstName);
        createCourier.validateResponseBadRequest(createCourier.sendCreateCourier());
    }
}
