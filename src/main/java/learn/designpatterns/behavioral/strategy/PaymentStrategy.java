package learn.designpatterns.behavioral.strategy;

/**
 * Strategy pattern.
 *
 * <p>Analogy: paying at a shop checkout. The checkout process is always the same (work out the total,
 * take payment, print a receipt), but how you pay changes the details: a card adds a 2% fee, UPI is
 * free, cash is free but capped at $500. Each payment method is a swappable "strategy" object.
 */
public final class PaymentStrategy {

    private PaymentStrategy() {
    }

    /** Strategy: one way of paying. */
    interface PaymentMethod {
        String name();

        int feeCents(int amountCents);
    }

    /** Concrete strategies. */
    static final class CardPayment implements PaymentMethod {
        public String name() {
            return "card";
        }

        /** 2% fee, rounded to the nearest cent; computed in long so large amounts can't overflow. */
        public int feeCents(int amountCents) {
            return (int) ((amountCents * 2L + 50) / 100);
        }
    }

    static final class UpiPayment implements PaymentMethod {
        public String name() {
            return "UPI";
        }

        public int feeCents(int amountCents) {
            return 0;
        }
    }

    static final class CashPayment implements PaymentMethod {
        private static final int LIMIT_CENTS = 50_000;

        public String name() {
            return "cash";
        }

        public int feeCents(int amountCents) {
            if (amountCents > LIMIT_CENTS) {
                throw new IllegalArgumentException("Cash payments are limited to " + money(LIMIT_CENTS));
            }
            return 0;
        }
    }

    /** Context: the checkout. Its code never changes when a new payment method is added. */
    static final class Checkout {
        private PaymentMethod method;

        Checkout use(PaymentMethod method) {
            if (method == null) {
                throw new IllegalArgumentException("Payment method must not be null");
            }
            this.method = method;
            return this;
        }

        int total(int amountCents) {
            if (amountCents < 0) {
                throw new IllegalArgumentException("Amount must not be negative: " + amountCents);
            }
            if (method == null) {
                throw new IllegalStateException("Choose a payment method first");
            }
            return Math.addExact(amountCents, method.feeCents(amountCents));
        }

        String pay(int amountCents) {
            int total = total(amountCents);
            return "Paid " + money(total) + " by " + method.name() + " (fee " + money(total - amountCents) + ")";
        }
    }

    static String money(int cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }

    public static void main(String[] args) {
        Checkout checkout = new Checkout();
        int basket = 10_000;
        for (PaymentMethod method : new PaymentMethod[] {new CardPayment(), new UpiPayment(), new CashPayment()}) {
            System.out.println(checkout.use(method).pay(basket));
        }
    }
}
