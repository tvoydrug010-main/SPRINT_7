package yandex.praktikum.api.listOrder;


import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import yandex.praktikum.api.base.BaseApiTest;
import yandex.praktikum.api.authorizationCourier.AuthCourier;
import yandex.praktikum.api.authorizationCourier.DataFactory;
import yandex.praktikum.api.createCourier.CreateCourier;
import yandex.praktikum.api.createOrder.CreateOrder;


import static org.apache.commons.lang3.RandomUtils.nextInt;

public class ListOrderTest extends BaseApiTest {
    @Test
    public void getListOrderSuccessTest(){
        CreateCourier courier = DataFactory.generateValidCourier();
        courier.validateResponseSuccess(courier.sendCreateCourier());


        AuthCourier login = new AuthCourier(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResp = login.sendLogin();
        login.validateResponseSuccess(loginResp);

        Integer courierId = loginResp.extract().path("id");
        Assertions.assertNotNull(courierId, "Ответ на создание логин должен содержать поле 'id'");

        CreateOrder order = DataFactory.generateValidOrder();
        ValidatableResponse orderResp = order.sendCreateOrder();
        order.validateResponseSuccess(orderResp);


        ListOrder listOrder = new ListOrder(courierId);
        listOrder.validateResponseSuccess(listOrder.sendOrderCount());
    }
    @Test
    public void getListOrderNotFoundTest(){
        int id = nextInt();
        ListOrder order = new ListOrder(id);
        order.validateResponseNotFound(order.sendOrderCount());
    }
}
