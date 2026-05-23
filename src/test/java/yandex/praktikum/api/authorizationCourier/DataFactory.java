package yandex.praktikum.api.authorizationCourier;

import org.apache.commons.lang3.RandomStringUtils;
import yandex.praktikum.api.createCourier.CreateCourier;
import yandex.praktikum.api.createOrder.CreateOrder;

import java.time.LocalDate;
import java.util.Collections;

public class DataFactory {

    public static CreateCourier generateValidCourier(){
        String uniqueLogin = RandomStringUtils.randomAlphabetic(4);
        return new CreateCourier(uniqueLogin, "password999", "TestCourier");
    }

    public static CreateOrder generateValidOrder(){
        return new CreateOrder("Петр" + RandomStringUtils.randomAlphabetic(5), "Иванов", "Москва, ул. Тестовая, 1",
                "10", "+7999" + RandomStringUtils.randomNumeric(7), 30,
                LocalDate.now().plusDays(1).toString(), "Автотест",
                Collections.singletonList("BLACK"));
    }

}
