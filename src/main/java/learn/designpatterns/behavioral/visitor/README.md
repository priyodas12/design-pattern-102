# Visitor

**Family:** Behavioral · **Status:** implemented

**Analogy:** a shopping cart holds books, food and electronics. Different "visitors" walk through the cart, and each applies its own rule per item type: a tax calculator (books 0%, food 5%, electronics 18%) or a shipping calculator. A new calculation is a new visitor, and the item classes never change.

Source: [`ShoppingCartVisitor.java`](ShoppingCartVisitor.java) · Test: [`ShoppingCartVisitorTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/visitor/ShoppingCartVisitorTest.java)

## Intent
Add new operations over a set of element types without changing those element classes.

## When to use
- The set of element types is stable, but you keep adding operations over them (tax, shipping, export, ...).
- Each operation needs different logic per element type.
- You want each operation's logic together in one class, rather than spread across every element.

## Roles
| Role | In this example |
|------|-----------------|
| Visitor | `Visitor<R>`: `visit(Book)`, `visit(Food)`, `visit(Electronics)`; `R` is the result type |
| Concrete visitors | `TaxVisitor` (rates 0/5/18%, rounded half up), `ShippingVisitor` (300 / 500 / free over $50, otherwise 800) |
| Element | `Item`: a sealed interface with `accept(Visitor<R>)` |
| Concrete elements | `Book`, `Food`, `Electronics`: each `accept` calls `visitor.visit(this)` (double dispatch) |
| Object structure | `Cart`: `total(visitor)` runs a visitor over every item |

## Diagram
```
  cart.total(new TaxVisitor())
     for each item: item.accept(visitor) ──> visitor.visit(this)   (double dispatch)
        Book("Java Patterns", $20.00)   → TaxVisitor.visit(Book)        →     0
        Food("Apples", $4.00)           → TaxVisitor.visit(Food)        →    20
        Electronics("Headphones", $100) → TaxVisitor.visit(Electronics) → 1,800
                                                               total tax  1,820 cents
  A new operation (ShippingVisitor) is a new class; Book, Food and Electronics are unchanged.
```

## Pros
- New operations are added without touching the element classes.
- Each operation's logic is in one place, with one method per element type.
- The compiler checks that every visitor handles every element type.

## Cons
- Adding a new element type (for example, `Clothing`) means changing the `Visitor` interface and every visitor.
- Elements must expose enough data (`priceCents()`) for visitors to work.
- Double dispatch can feel indirect at first. In modern Java, a `switch` over a sealed interface is often a simpler alternative.

## Related patterns
- **Composite:** visitors are commonly run over composite trees.
- **Interpreter:** visitors can add operations (print, optimise) to an expression tree.
- **Iterator:** walks the structure, while the visitor decides what to do at each element.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.visitor.ShoppingCartVisitor
mvn test -Dtest=ShoppingCartVisitorTest
```
