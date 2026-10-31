package learn.designpatterns.creational.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Builder pattern.
 *
 * <p>Analogy: ordering a custom burger. You choose the bun, then the patty, then any toppings, sauces
 * and extras, one step at a time. The order is only "built" when you finish, and it is checked then
 * (no bun, no burger). After that the order is fixed and can't change under the kitchen's feet.
 */
public final class BurgerOrderBuilder {

    private BurgerOrderBuilder() {
    }

    /** Patty prices in cents; the builder rejects anything not on the menu. */
    private static final Map<String, Integer> PATTY_PRICES = Map.of("beef", 500, "chicken", 450, "veggie", 400);
    private static final int TOPPING_PRICE = 50;
    private static final int EXTRA_CHEESE_PRICE = 75;

    /** Product: an immutable, fully validated order. */
    record BurgerOrder(String bun, String patty, List<String> toppings, List<String> sauces,
                       boolean extraCheese, int priceCents) {

        static Builder builder() {
            return new Builder();
        }

        String describe() {
            List<String> parts = new ArrayList<>();
            parts.add(bun + " bun");
            parts.add(patty + " patty");
            parts.addAll(toppings);
            parts.addAll(sauces);
            if (extraCheese) {
                parts.add("extra cheese");
            }
            return String.join(", ", parts) + String.format(" ($%d.%02d)", priceCents / 100, priceCents % 100);
        }

        /** Builder: collects the parts step by step and produces the product in build(). */
        static final class Builder {
            private String bun;
            private String patty;
            private final List<String> toppings = new ArrayList<>();
            private final List<String> sauces = new ArrayList<>();
            private boolean extraCheese;

            Builder bun(String bun) {
                this.bun = bun;
                return this;
            }

            Builder patty(String patty) {
                if (!PATTY_PRICES.containsKey(patty)) {
                    throw new IllegalArgumentException("Unknown patty: " + patty + " (menu: " + PATTY_PRICES.keySet() + ")");
                }
                this.patty = patty;
                return this;
            }

            Builder topping(String topping) {
                toppings.add(topping);
                return this;
            }

            Builder sauce(String sauce) {
                sauces.add(sauce);
                return this;
            }

            Builder extraCheese() {
                this.extraCheese = true;
                return this;
            }

            /** Validates and creates an immutable order; the builder can keep being used afterwards. */
            BurgerOrder build() {
                if (bun == null || bun.isBlank()) {
                    throw new IllegalStateException("A burger needs a bun");
                }
                if (patty == null) {
                    throw new IllegalStateException("A burger needs a patty");
                }
                int price = PATTY_PRICES.get(patty) + toppings.size() * TOPPING_PRICE
                        + (extraCheese ? EXTRA_CHEESE_PRICE : 0);
                return new BurgerOrder(bun, patty, List.copyOf(toppings), List.copyOf(sauces), extraCheese, price);
            }
        }
    }

    public static void main(String[] args) {
        BurgerOrder classic = BurgerOrder.builder()
                .bun("sesame").patty("beef").topping("lettuce").topping("tomato").sauce("mayo").extraCheese()
                .build();
        BurgerOrder light = BurgerOrder.builder().bun("whole-wheat").patty("veggie").sauce("mustard").build();

        System.out.println("Classic: " + classic.describe());
        System.out.println("Light:   " + light.describe());
    }
}
