# Adapter

**Family:** Structural · **Status:** implemented

**Analogy:** a US hair dryer has flat pins and won't fit a European wall socket, which needs round pins. A travel plug adapter sits in between. The socket sees round pins, the dryer sees a US outlet, and neither has to change.

Source: [`TravelPlugAdapter.java`](TravelPlugAdapter.java) · Test: [`TravelPlugAdapterTest.java`](../../../../../../test/java/learn/designpatterns/structural/adapter/TravelPlugAdapterTest.java)

## Intent
Convert the interface of a class into another interface clients expect, so incompatible classes can work together.

## When to use
- You want to use an existing class, but its interface doesn't match the one your code expects.
- You can't, or shouldn't, change either side: one is third-party, legacy code or shared by others.
- You need several incompatible classes to fit one common interface.

## Roles
| Role | In this example |
|------|-----------------|
| Target | `EuPlug`: what the socket expects (`pinShape()`, `deviceName()`) |
| Client | `EuSocket`: accepts only `EuPlug`s with round pins |
| Adaptee | `UsPlug` / `UsHairDryer`: an incompatible interface (`usDeviceName()`) |
| Adapter | `UsToEuPlugAdapter`: implements `EuPlug` and forwards to the wrapped `UsPlug` |

## Diagram
```
  EuSocket ──plugIn(EuPlug)──> EuPlug (target)
                                  ▲
                    ┌─────────────┴─────────────┐
             EuLaptopCharger            UsToEuPlugAdapter ──wraps──> UsPlug (adaptee)
             (fits directly)            pinShape() = "round"           └─ UsHairDryer
                                        deviceName() → usDeviceName()
```

## Pros
- Reuses existing classes without modifying them.
- Translation logic is kept in one small class.
- The client stays simple: it only knows the target interface.

## Cons
- Adds an extra layer of indirection.
- If many methods need translating, the adapter can become large.

## Related patterns
- **Facade:** also wraps other classes, but to *simplify* an interface, not to *convert* one.
- **Decorator:** wraps an object while keeping the *same* interface, adding behavior instead of translating.
- **Bridge:** designed up front to separate two sides; Adapter is applied after the fact to make existing pieces fit.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.adapter.TravelPlugAdapter
mvn test -Dtest=TravelPlugAdapterTest
```
