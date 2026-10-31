# Factory Method

**Family:** Creational · **Status:** implemented

**Analogy:** a logistics company plans every delivery the same way: pick a vehicle, load the cargo, send it. Only the vehicle differs. Road logistics uses a truck and sea logistics uses a ship. The shared planning code asks a "factory method" for the vehicle and lets each kind of company decide which one to create.

Source: [`LogisticsFactoryMethod.java`](LogisticsFactoryMethod.java) · Test: [`LogisticsFactoryMethodTest.java`](../../../../../../test/java/learn/designpatterns/creational/factorymethod/LogisticsFactoryMethodTest.java)

## Intent
Define an interface for creating an object, but let subclasses decide which class to instantiate.

## When to use
- A class has shared logic, but the exact type of object it works with varies by subclass.
- You want to add new product types by adding a subclass, without editing the shared code.
- Client code should depend on an interface (`Transport`), not on concrete classes (`Truck`, `Ship`).

## Roles
| Role | In this example |
|------|-----------------|
| Product | `Transport` |
| Concrete products | `Truck`, `Ship` |
| Creator | `Logistics`: declares `createTransport()` and uses it in `planDelivery()` |
| Concrete creators | `RoadLogistics`, `SeaLogistics` |

## Diagram
```
        Logistics (creator)                    Transport (product)
   planDelivery(cargo) ──calls──> createTransport()        ▲
              ▲                        │               ┌────┴────┐
     ┌────────┴────────┐               │             Truck      Ship
 RoadLogistics    SeaLogistics         │               ▲          ▲
 createTransport() createTransport() ──┴── returns ────┘          │
        └─ new Truck()   └─ new Ship() ───────────────────────────┘
```

## Pros
- Shared logic (`planDelivery`) is written once and works with any transport.
- Open/closed: add `AirLogistics` and `Plane` without touching existing classes.
- Creation is in one overridable place, which is easy to swap in tests.

## Cons
- Each new product usually needs a new creator subclass, so the number of classes grows.
- It can be overkill when a simple `switch` or constructor call would do.

## Related patterns
- **Abstract Factory:** often implemented with several factory methods, one per product.
- **Template Method:** `planDelivery` is a template method, and the factory method is one of its steps.
- **Prototype:** an alternative that creates by copying, with no subclass per product.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.creational.factorymethod.LogisticsFactoryMethod
mvn test -Dtest=LogisticsFactoryMethodTest
```
