package learn.designpatterns.structural.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import learn.designpatterns.structural.decorator.CoffeeDecorator.Beverage;
import learn.designpatterns.structural.decorator.CoffeeDecorator.Espresso;
import learn.designpatterns.structural.decorator.CoffeeDecorator.HouseBlend;
import learn.designpatterns.structural.decorator.CoffeeDecorator.Milk;
import learn.designpatterns.structural.decorator.CoffeeDecorator.Sugar;
import learn.designpatterns.structural.decorator.CoffeeDecorator.WhippedCream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoffeeDecoratorTest {

    @Test
    @DisplayName("a plain base drink has its own description and price")
    void plainBase() {
        Beverage espresso = new Espresso();
        assertEquals("Espresso", espresso.description());
        assertEquals(200, espresso.priceCents());
    }

    @Test
    @DisplayName("each add-on wraps the drink and adds to description and price, in order")
    void addOnsStack() {
        Beverage drink = new Sugar(new Milk(new Espresso()));
        assertEquals("Espresso, Milk, Sugar", drink.description());
        assertEquals(200 + 50 + 20, drink.priceCents());
    }

    @Test
    @DisplayName("the same add-on can be applied twice")
    void doubleAddOn() {
        Beverage drink = new Milk(new Milk(new HouseBlend()));
        assertEquals("House Blend, Milk, Milk", drink.description());
        assertEquals(150 + 50 + 50, drink.priceCents());
    }

    @Test
    @DisplayName("a decorated drink is still a Beverage and can be decorated again")
    void decoratorIsABeverage() {
        Beverage withMilk = new Milk(new Espresso());
        Beverage deluxe = new WhippedCream(withMilk);
        assertEquals("Espresso, Milk, Whipped Cream", deluxe.description());
        assertEquals(320, deluxe.priceCents());
        assertEquals("Espresso, Milk = $2.50", CoffeeDecorator.receipt(withMilk));
    }

    @Test
    @DisplayName("an add-on needs a drink to wrap")
    void nullInnerRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Milk(null));
    }
}
