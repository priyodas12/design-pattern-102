# Strategy

**Family:** Behavioral · **Status:** implemented

**Analogy:** paying at a shop checkout. The checkout process is always the same: work out the total, take payment, print a receipt. How you pay changes the details: a card adds a 2% fee, UPI is free, and cash is free but capped at $500. Each payment method is a swappable "strategy" object.

Source: [`PaymentStrategy.java`](PaymentStrategy.java) · Test: [`PaymentStrategyTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/strategy/PaymentStrategyTest.java)

## Intent
Define a family of interchangeable algorithms and let the client pick one at runtime.

## When to use
- There are several ways to do one job (pricing, sorting, routing, paying), and the choice varies.
- You want to avoid a growing `if`/`switch` on "which method?" inside the main code.
- New variants should be added without editing the code that uses them.

## Roles
| Role | In this example |
|------|-----------------|
| Strategy | `PaymentMethod`: `name()`, `feeCents(amount)` |
| Concrete strategies | `CardPayment` (2% fee), `UpiPayment` (free), `CashPayment` (free, max $500) |
| Context | `Checkout`: `use(method)`, `total(amount)`, `pay(amount)`; it never changes when a method is added |
| Client | `main` and the tests: choose a strategy and pass it to the checkout |

## Diagram
```
                 Checkout
   use(method) ──> method: PaymentMethod
   pay(10_000) ──> total = amount + method.feeCents(amount)
                          ▲
       ┌──────────────────┼───────────────────┐
  CardPayment        UpiPayment          CashPayment
  fee 2% → $2.00     fee $0.00           fee $0.00 (≤ $500, else error)
```

## Pros
- Each algorithm is in its own class and can be tested on its own.
- Strategies can be swapped at runtime on the same context.
- Open/closed: a gift card is just another `PaymentMethod`, and `Checkout` stays untouched.

## Cons
- Clients must know the strategies well enough to pick one.
- There are more classes for what might be a small difference.
- For a single-method strategy, a plain lambda can replace the class. This interface has two methods, so it uses classes.

## Related patterns
- **State:** same structure, but states replace themselves; a strategy is chosen by the client.
- **Template Method:** varies steps through inheritance; Strategy varies the whole algorithm through composition.
- **Bridge:** also delegates to a swappable object, but to separate an abstraction from its platform.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.strategy.PaymentStrategy
mvn test -Dtest=PaymentStrategyTest
```
