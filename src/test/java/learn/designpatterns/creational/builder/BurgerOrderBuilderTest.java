package learn.designpatterns.creational.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import learn.designpatterns.creational.builder.BurgerOrderBuilder.BurgerOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BurgerOrderBuilderTest {

    private static BurgerOrder.Builder classicBurger() {
        return BurgerOrder.builder()
                .bun("sesame")
                .patty("beef")
                .topping("lettuce")
                .topping("tomato")
                .sauce("mayo")
                .extraCheese();
    }

    @Test
    @DisplayName("builds the order step by step, keeping topping order")
    void buildsStepByStep() {
        BurgerOrder order = classicBurger().build();
        assertEquals("sesame", order.bun());
        assertEquals("beef", order.patty());
        assertEquals(List.of("lettuce", "tomato"), order.toppings());
        assertEquals(List.of("mayo"), order.sauces());
        assertTrue(order.extraCheese());
    }

    @Test
    @DisplayName("price = patty + 50 per topping + 75 for extra cheese")
    void computesPrice() {
        assertEquals(500 + 2 * 50 + 75, classicBurger().build().priceCents());
    }

    @Test
    @DisplayName("describes the order in plain words")
    void describesOrder() {
        assertEquals("sesame bun, beef patty, lettuce, tomato, mayo, extra cheese ($6.75)",
                classicBurger().build().describe());
    }

    @Test
    @DisplayName("built order is immutable and unaffected by later builder calls")
    void resultIsImmutable() {
        BurgerOrder.Builder builder = classicBurger();
        BurgerOrder first = builder.build();
        BurgerOrder second = builder.topping("onion").build();

        assertEquals(2, first.toppings().size());
        assertEquals(3, second.toppings().size());
        assertThrows(UnsupportedOperationException.class, () -> first.toppings().add("pickles"));
    }

    @Test
    @DisplayName("an order without a bun cannot be built")
    void missingBunRejected() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> BurgerOrder.builder().patty("beef").build());
        assertTrue(ex.getMessage().contains("bun"), ex.getMessage());
    }

    @Test
    @DisplayName("an order without a patty cannot be built")
    void missingPattyRejected() {
        assertThrows(IllegalStateException.class, () -> BurgerOrder.builder().bun("brioche").build());
    }

    @Test
    @DisplayName("an unknown patty is rejected immediately")
    void unknownPattyRejected() {
        assertThrows(IllegalArgumentException.class, () -> BurgerOrder.builder().patty("tofu-bacon"));
    }
}
