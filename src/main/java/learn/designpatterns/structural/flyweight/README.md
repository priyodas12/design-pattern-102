# Flyweight

**Family:** Structural · **Status:** implemented

**Analogy:** drawing a forest of thousands of trees. Every oak has the same name, colour and bark texture; only its position differs. So each tree stores just its x and y plus a pointer to one shared "Oak" object, instead of its own copy of the species data. The result is thousands of trees but only a handful of species objects.

Source: [`ForestFlyweight.java`](ForestFlyweight.java) · Test: [`ForestFlyweightTest.java`](../../../../../../test/java/learn/designpatterns/structural/flyweight/ForestFlyweightTest.java)

## Intent
Share common state between many fine-grained objects so large numbers of them stay cheap.

## When to use
- You need a very large number of similar objects, and memory is a concern.
- Most of each object's state can be shared (intrinsic) and only a little is unique (extrinsic).
- The shared part can be immutable.

## Roles
| Role | In this example |
|------|-----------------|
| Flyweight | `TreeType`: an immutable record with name, colour and texture (intrinsic state) |
| Flyweight factory | `TreeTypeFactory`: caches and returns an existing `TreeType`; `typesCreated()` |
| Context | `Tree`: x and y (extrinsic state) plus a reference to the shared `TreeType` |
| Client | `Forest`: plants trees by asking the factory for the species each time |

## Diagram
```
  Tree(0,0) ─┐
  Tree(2,0) ─┼──> TreeType("Oak", green, rough bark)     ← one shared object
  Tree(4,0) ─┘
  Tree(1,0) ─┐
  Tree(3,0) ─┴──> TreeType("Birch", white, smooth bark)  ← one shared object

  1,000 Tree objects (x, y, type) → only 2 TreeType objects, via TreeTypeFactory's cache
```

## Pros
- Large memory savings when many objects share most of their data.
- The shared state is immutable, so it's safe to share.
- The factory is the one place that controls object creation.

## Cons
- Code gets more complex: state is split into intrinsic and extrinsic parts.
- Extrinsic state must be passed around or stored separately.
- There's no benefit if few objects share data.

## Related patterns
- **Composite:** leaf nodes of a large tree are often flyweights.
- **Singleton:** the flyweight factory is usually a single shared cache.
- **Prototype:** another way to keep object creation cheap, by copying instead of sharing.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.flyweight.ForestFlyweight
mvn test -Dtest=ForestFlyweightTest
```
