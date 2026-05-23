package yandex.praktikum.api.createOrder;


import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import yandex.praktikum.api.base.BaseApiTest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;


public class CreateOrderTest extends BaseApiTest {


    private static CreateOrder validOrderBase() {
        return new CreateOrder(
                "Oleg" + RandomStringUtils.randomAlphabetic(1),
                "Popov" + RandomStringUtils.randomAlphabetic(1),
                "Moscow" + RandomStringUtils.randomAlphabetic(1),
                "4" + RandomStringUtils.randomNumeric(1),
                "+7 900 " + RandomStringUtils.randomNumeric(7),
                4,
                LocalDate.now().plusDays(1).toString(), //deliveryDate
                "Auto-comment " + RandomStringUtils.randomAlphabetic(3),
                Collections.emptyList()
        );
    }

    private static Stream<Arguments> colorCases() {
        return Stream.of(
                Arguments.of("Один цвет: BLACK", Collections.singletonList("BLACK")),
                Arguments.of("Один цвет: GREY",  Collections.singletonList("GREY")),
                Arguments.of("Оба цвета",        Arrays.asList("BLACK", "GREY")),
                Arguments.of("Без цвета",        null) // null = поле не отправится (при правильной сериализации)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("colorCases")
    public void testCreateOrderWithColors(String description, List<String> colors) {
        CreateOrder order = validOrderBase();
        order.setColor(colors); // Подставляем нужный цвет
        order.validateResponseSuccess(order.sendCreateOrder());
    }
}
