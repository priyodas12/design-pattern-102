# Decorator

**Family:** Structural · **Status:** implemented

**Analogy:** ordering coffee at a cafe. You start with a base drink and add milk, sugar or whipped cream. Each add-on wraps the drink so far: it adds its own name and price, then defers to the drink inside. Any add-ons can stack in any order without a class for every combination.

Source: [`CoffeeDecorator.java`](CoffeeDecorator.java) · Test: [`CoffeeDecoratorTest.java`](../../../../../../test/java/learn/designpatterns/structural/decorator/CoffeeDecoratorTest.java)

## Intent
Attach extra responsibilities to an object dynamically, as a flexible alternative to subclassing.

## When to use
- You need to add behavior to individual objects, not to a whole class.
- Features combine freely, so subclassing every combination would explode the number of classes.
- You want to add or remove responsibilities at runtime.

## Roles
| Role | In this example |
|------|-----------------|
| Component | `Beverage`: `description()`, `priceCents()` |
| Concrete components | `Espresso`, `HouseBlend` |
| Decorators | `Milk`, `Sugar`, `WhippedCream`: each wraps an `inner` `Beverage` and is a `Beverage` itself |
| Client | `main`: wraps the drink step by step and prints a receipt after each add-on |

## Diagram
```
  WhippedCream ──inner──> Sugar ──inner──> Milk ──inner──> Espresso
   +70 ¢                   +20 ¢            +50 ¢            200 ¢

  description(): "Espresso" → ", Milk" → ", Sugar" → ", Whipped Cream"
  priceCents():  200 + 50 + 20 + 70 = 340
```

## Pros
- Combine features freely at runtime, with no subclass per combination.
- Each decorator does one thing.
- The base classes stay untouched (open/closed).

## Cons
- Many small wrapper objects can make debugging harder.
- The order of wrapping can matter, and it isn't enforced.
- Code that checks the concrete type (`instanceof Espresso`) breaks once the object is wrapped.

## Related patterns
- **Adapter:** wraps an object to *change* its interface; Decorator keeps the interface and *adds* behavior.
- **Composite:** a similar recursive wrapping, but with many children instead of one.
- **Proxy:** same structure, but a proxy controls access instead of adding features.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.decorator.CoffeeDecorator
mvn test -Dtest=CoffeeDecoratorTest
```
