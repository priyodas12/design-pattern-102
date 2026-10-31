package learn.designpatterns.structural.decorator;

/**
 * Decorator pattern.
 *
 * <p>Analogy: ordering coffee at a cafe. You start with a base drink and add milk, sugar or whipped
 * cream. Each add-on "wraps" the drink so far: it adds its own name and price, then defers to the drink
 * inside. You can stack any add-ons in any order without a class for every combination.
 */
public final class CoffeeDecorator {

    private CoffeeDecorator() {
    }

    /** Component: what every drink, plain or decorated, can tell you. */
    interface Beverage {
        String description();

        int priceCents();
    }

    /** Concrete components: the base drinks. */
    record Espresso() implements Beverage {
        public String description() {
            return "Espresso";
        }

        public int priceCents() {
            return 200;
        }
    }

    record HouseBlend() implements Beverage {
        public String description() {
            return "House Blend";
        }

        public int priceCents() {
            return 150;
        }
    }

    /** Concrete decorators: each wraps a Beverage and is itself a Beverage. */
    record Milk(Beverage inner) implements Beverage {
        Milk {
            requireDrink(inner);
        }

        public String description() {
            return inner.description() + ", Milk";
        }

        public int priceCents() {
            return inner.priceCents() + 50;
        }
    }

    record Sugar(Beverage inner) implements Beverage {
        Sugar {
            requireDrink(inner);
        }

        public String description() {
            return inner.description() + ", Sugar";
        }

        public int priceCents() {
            return inner.priceCents() + 20;
        }
    }

    record WhippedCream(Beverage inner) implements Beverage {
        WhippedCream {
            requireDrink(inner);
        }

        public String description() {
            return inner.description() + ", Whipped Cream";
        }

        public int priceCents() {
            return inner.priceCents() + 70;
        }
    }

    private static void requireDrink(Beverage inner) {
        if (inner == null) {
            throw new IllegalArgumentException("An add-on needs a drink to wrap");
        }
    }

    /** Formats a drink as "description = $x.yy". */
    static String receipt(Beverage drink) {
        int cents = drink.priceCents();
        return drink.description() + String.format(" = $%d.%02d", cents / 100, cents % 100);
    }

    public static void main(String[] args) {
        Beverage drink = new Espresso();
        System.out.println(receipt(drink));
        drink = new Milk(drink);
        System.out.println(receipt(drink));
        drink = new Sugar(drink);
        System.out.println(receipt(drink));
        drink = new WhippedCream(drink);
        System.out.println(receipt(drink));
    }
}
