# Template Method

**Family:** Behavioral · **Status:** implemented

**Analogy:** making tea and making coffee follow the same recipe: boil water, brew, pour into a cup, add extras. Only the brewing and the extras differ. The base class fixes the order of the steps once (the "template method"), and each drink fills in just the steps that are different.

Source: [`BeverageTemplateMethod.java`](BeverageTemplateMethod.java) · Test: [`BeverageTemplateMethodTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/templatemethod/BeverageTemplateMethodTest.java)

## Intent
Define the skeleton of an algorithm once and let subclasses fill in specific steps without changing its order.

## When to use
- Several classes share the same overall procedure but differ in a few steps.
- The order of steps must stay fixed, so subclasses shouldn't be able to rearrange it.
- You want optional extension points (hooks) with sensible defaults.

## Roles
| Role | In this example |
|------|-----------------|
| Abstract class | `Beverage`: `prepare()` is the `final` template method |
| Shared steps | `boilWater()`, `pourInCup()`: private, the same for every drink |
| Primitive operations | `brew()`, `addCondiments()`: abstract, and every drink must supply them |
| Hook | `wantsCondiments()`: returns `true` by default; `Coffee(false)` overrides it to skip extras |
| Concrete classes | `Tea`, `Coffee` |

## Diagram
```
  Beverage.prepare()  (final)
    1. boilWater()          ← shared
    2. brew()               ← Tea: "Steep the tea for 3 minutes" | Coffee: "Drip coffee through a filter"
    3. pourInCup()          ← shared
    4. if wantsCondiments() ← hook (Coffee(false) says no)
         addCondiments()    ← Tea: "Add lemon" | Coffee: "Add sugar and milk"
```

## Pros
- Shared steps are written once, so there's no duplication across drinks.
- The algorithm's order is enforced, because `prepare()` is `final`.
- Hooks let subclasses adjust the flow without overriding the whole algorithm.

## Cons
- It's based on inheritance: a subclass is tied to its base class, and Java allows only one.
- It can be hard to follow which steps run where when there are many hooks.
- Changing the skeleton affects every subclass.

## Related patterns
- **Strategy:** swaps the whole algorithm by composition; Template Method swaps steps by inheritance.
- **Factory Method:** often one of the steps inside a template method.
- **Hook methods:** the optional steps here are a small form of the same open/closed idea.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.templatemethod.BeverageTemplateMethod
mvn test -Dtest=BeverageTemplateMethodTest
```
