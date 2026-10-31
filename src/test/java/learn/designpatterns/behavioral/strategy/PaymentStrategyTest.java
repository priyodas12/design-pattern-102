package learn.designpatterns.behavioral.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import learn.designpatterns.behavioral.strategy.PaymentStrategy.CardPayment;
import learn.designpatterns.behavioral.strategy.PaymentStrategy.CashPayment;
import learn.designpatterns.behavioral.strategy.PaymentStrategy.Checkout;
import learn.designpatterns.behavioral.strategy.PaymentStrategy.PaymentMethod;
import learn.designpatterns.behavioral.strategy.PaymentStrategy.UpiPayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentStrategyTest {

    private Checkout checkout;

    @BeforeEach
    void setUp() {
        checkout = new Checkout();
    }

    @Test
    @DisplayName("the same checkout charges a different fee for each payment method")
    void feeDependsOnStrategy() {
        assertEquals(10_200, checkout.use(new CardPayment()).total(10_000));
        assertEquals(10_000, checkout.use(new UpiPayment()).total(10_000));
        assertEquals(10_000, checkout.use(new CashPayment()).total(10_000));
    }

    @Test
    @DisplayName("the receipt names the method and its fee")
    void receiptDescribesPayment() {
        assertEquals("Paid $102.00 by card (fee $2.00)", checkout.use(new CardPayment()).pay(10_000));
        assertEquals("Paid $100.00 by UPI (fee $0.00)", checkout.use(new UpiPayment()).pay(10_000));
    }

    @Test
    @DisplayName("a new strategy plugs in without changing the checkout")
    void customStrategy() {
        PaymentMethod giftCard = new PaymentMethod() {
            public String name() {
                return "gift card";
            }

            public int feeCents(int amountCents) {
                return 0;
            }
        };
        assertEquals("Paid $25.00 by gift card (fee $0.00)", checkout.use(giftCard).pay(2_500));
    }

    @Test
    @DisplayName("cash is limited to $500")
    void cashLimitEnforced() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> checkout.use(new CashPayment()).total(50_001));
        assertEquals("Cash payments are limited to $500.00", ex.getMessage());
    }

    @Test
    @DisplayName("the card fee stays correct for very large amounts")
    void largeCardFeeIsCorrect() {
        assertEquals(40_000_000, new CardPayment().feeCents(2_000_000_000));
    }

    @Test
    @DisplayName("a total that doesn't fit in an int fails instead of wrapping around")
    void totalOverflowDetected() {
        assertThrows(ArithmeticException.class, () -> checkout.use(new CardPayment()).total(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("paying without choosing a method is an error")
    void noStrategyRejected() {
        assertThrows(IllegalStateException.class, () -> checkout.total(1_000));
    }

    @Test
    @DisplayName("a negative amount is rejected")
    void negativeAmountRejected() {
        assertThrows(IllegalArgumentException.class, () -> checkout.use(new UpiPayment()).total(-1));
    }
}
