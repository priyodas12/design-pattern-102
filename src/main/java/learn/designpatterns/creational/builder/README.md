# Builder

**Family:** Creational · **Status:** implemented

**Analogy:** ordering a custom burger. You choose the bun, then the patty, then any toppings, sauces and extras, one step at a time. The order is only "built" when you finish, and it is checked at that point: no bun, no burger. After that, the order is fixed and can't change.

Source: [`BurgerOrderBuilder.java`](BurgerOrderBuilder.java) · Test: [`BurgerOrderBuilderTest.java`](../../../../../../test/java/learn/designpatterns/creational/builder/BurgerOrderBuilderTest.java)

## Intent
Separate the construction of a complex object from its representation, so the same steps can build different results.

## When to use
- An object has many parts, some optional, and a constructor would need a long, confusing parameter list.
- The object should be immutable once created, but is easier to assemble step by step.
- Validation should happen once, when the whole object is complete.

## Roles
| Role | In this example |
|------|-----------------|
| Product | `BurgerOrder`: an immutable record with its price computed |
| Builder | `BurgerOrder.Builder`: `bun()`, `patty()`, `topping()`, `sauce()`, `extraCheese()` |
| Build step | `Builder.build()`: validates and creates the product |
| Client (director) | `main`: decides which steps to call, and in what order |

## Diagram
```
  client
    │  builder()
    ▼
  Builder ──bun("sesame")──patty("beef")──topping("lettuce")──extraCheese()──┐
                                                                            │ build()
                                                     validate (bun? patty?) ▼
                                                   BurgerOrder (immutable, priced)
```

## Pros
- Readable, self-describing construction code, with no long constructor parameter lists.
- The product can be immutable while still being assembled gradually.
- Validation happens in one place (`build()`).
- The same builder can produce several variants.

## Cons
- More code than a plain constructor, which isn't worth it for objects with only 2 or 3 fields.
- Mistakes such as a missing bun are caught when `build()` runs, not when the code compiles.

## Related patterns
- **Abstract Factory:** also creates objects, but in one call per product; Builder works step by step.
- **Composite:** builders are often used to assemble composite trees.
- **Prototype:** an alternative when building means "copy and tweak an existing one".

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.creational.builder.BurgerOrderBuilder
mvn test -Dtest=BurgerOrderBuilderTest
```
