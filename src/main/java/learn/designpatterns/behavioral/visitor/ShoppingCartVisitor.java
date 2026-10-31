package learn.designpatterns.behavioral.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor pattern.
 *
 * <p>Analogy: a shopping cart holds books, food and electronics. Different "visitors" walk through the
 * cart and each applies its own rule per item type: a tax calculator (books 0%, food 5%, electronics
 * 18%) or a shipping calculator. New calculations are new visitors, and the item classes never change.
 */
public final class ShoppingCartVisitor {

    private ShoppingCartVisitor() {
    }

    /** Visitor: one method per element type. R is what the operation computes. */
    interface Visitor<R> {
        R visit(Book book);

        R visit(Food food);

        R visit(Electronics electronics);
    }

    /** Element: every item accepts a visitor and calls back the right visit method (double dispatch). */
    sealed interface Item permits Book, Food, Electronics {
        String name();

        int priceCents();

        <R> R accept(Visitor<R> visitor);
    }

    /** Concrete elements. */
    record Book(String name, int priceCents) implements Item {
        Book {
            requirePrice(priceCents);
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    record Food(String name, int priceCents) implements Item {
        Food {
            requirePrice(priceCents);
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    record Electronics(String name, int priceCents) implements Item {
        Electronics {
            requirePrice(priceCents);
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visit(this);
        }
    }

    private static void requirePrice(int priceCents) {
        if (priceCents < 0) {
            throw new IllegalArgumentException("Price must not be negative: " + priceCents);
        }
    }

    /** Concrete visitor: tax in cents, rounded half up. */
    static final class TaxVisitor implements Visitor<Integer> {
        public Integer visit(Book book) {
            return 0;
        }

        public Integer visit(Food food) {
            return percent(food.priceCents(), 5);
        }

        public Integer visit(Electronics electronics) {
            return percent(electronics.priceCents(), 18);
        }

        /** Computed in long so large prices can't overflow; the result always fits in an int. */
        private static int percent(int cents, int rate) {
            return (int) ((cents * (long) rate + 50) / 100);
        }
    }

    /** A second operation, added without touching any item class. */
    static final class ShippingVisitor implements Visitor<Integer> {
        public Integer visit(Book book) {
            return 300;
        }

        public Integer visit(Food food) {
            return 500;
        }

        public Integer visit(Electronics electronics) {
            return electronics.priceCents() >= 5_000 ? 0 : 800;
        }
    }

    /** Object structure: holds the items and runs a visitor over all of them. */
    static final class Cart {
        private final List<Item> items = new ArrayList<>();

        void add(Item item) {
            items.add(item);
        }

        int total(Visitor<Integer> visitor) {
            return items.stream().mapToInt(item -> item.accept(visitor)).reduce(0, Math::addExact);
        }
    }

    public static void main(String[] args) {
        Cart cart = new Cart();
        List<Item> items = List.of(new Book("Java Patterns", 2_000), new Food("Apples", 400), new Electronics("Headphones", 10_000));
        items.forEach(cart::add);
        for (Item item : items) {
            System.out.printf("%-14s tax %5d cents, shipping %4d cents%n",
                    item.name(), item.accept(new TaxVisitor()), item.accept(new ShippingVisitor()));
        }
        System.out.println("Total tax:      " + cart.total(new TaxVisitor()) + " cents");
        System.out.println("Total shipping: " + cart.total(new ShippingVisitor()) + " cents");
    }
}
