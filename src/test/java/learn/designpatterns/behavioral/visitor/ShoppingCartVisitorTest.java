package learn.designpatterns.behavioral.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import learn.designpatterns.behavioral.visitor.ShoppingCartVisitor.Book;
import learn.designpatterns.behavioral.visitor.ShoppingCartVisitor.Cart;
import learn.designpatterns.behavioral.visitor.ShoppingCartVisitor.Electronics;
import learn.designpatterns.behavioral.visitor.ShoppingCartVisitor.Food;
import learn.designpatterns.behavioral.visitor.ShoppingCartVisitor.ShippingVisitor;
import learn.designpatterns.behavioral.visitor.ShoppingCartVisitor.TaxVisitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShoppingCartVisitorTest {

    private final Book book = new Book("Java Patterns", 2_000);
    private final Food food = new Food("Apples", 400);
    private final Electronics headphones = new Electronics("Headphones", 10_000);

    @Test
    @DisplayName("the tax visitor applies a different rate per item type")
    void taxPerCategory() {
        TaxVisitor tax = new TaxVisitor();
        assertEquals(0, book.accept(tax));
        assertEquals(20, food.accept(tax));
        assertEquals(1_800, headphones.accept(tax));
    }

    @Test
    @DisplayName("tax rounds half a cent up")
    void taxRounding() {
        assertEquals(21, new Food("Bread", 410).accept(new TaxVisitor()));
    }

    @Test
    @DisplayName("the cart totals any visitor's results over all items")
    void cartTotalsTax() {
        Cart cart = new Cart();
        cart.add(book);
        cart.add(food);
        cart.add(headphones);
        assertEquals(1_820, cart.total(new TaxVisitor()));
    }

    @Test
    @DisplayName("a second operation (shipping) works without changing any item class")
    void secondOperation() {
        Cart cart = new Cart();
        cart.add(book);
        cart.add(food);
        cart.add(headphones);
        assertEquals(300 + 500 + 0, cart.total(new ShippingVisitor()));
        assertEquals(800, new Electronics("Cable", 1_500).accept(new ShippingVisitor()));
    }

    @Test
    @DisplayName("tax stays correct for very large prices")
    void largeTaxIsCorrect() {
        assertEquals(36_000_000, new Electronics("Server rack", 200_000_000).accept(new TaxVisitor()));
    }

    @Test
    @DisplayName("a cart total that doesn't fit in an int fails instead of wrapping around")
    void cartTotalOverflowDetected() {
        Cart cart = new Cart();
        for (int i = 0; i < 6; i++) {
            cart.add(new Electronics("Rack " + i, 2_000_000_000)); // tax 360,000,000 each; 6 × = 2.16 billion > Integer.MAX_VALUE
        }
        assertThrows(ArithmeticException.class, () -> cart.total(new TaxVisitor()));
    }

    @Test
    @DisplayName("an empty cart totals 0")
    void emptyCart() {
        assertEquals(0, new Cart().total(new TaxVisitor()));
    }

    @Test
    @DisplayName("a negative price is rejected")
    void negativePriceRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Book("Broken", -1));
    }
}
