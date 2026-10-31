# State

**Family:** Behavioral · **Status:** implemented

**Analogy:** a traffic light. It behaves differently depending on its current colour: whether cars may go, which colour comes next, and what the pedestrian button does. Each colour is its own small class, and the light simply swaps one state object for another, instead of a big if/else on the colour.

Source: [`TrafficLightState.java`](TrafficLightState.java) · Test: [`TrafficLightStateTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/state/TrafficLightStateTest.java)

## Intent
Let an object change its behavior when its internal state changes, as if it changed its class.

## When to use
- An object's behavior depends on its state, and that logic is spreading into `if`/`switch` blocks.
- There are clear states with defined transitions between them.
- Some actions are valid only in certain states, and invalid ones should be rejected clearly.

## Roles
| Role | In this example |
|------|-----------------|
| State | `LightState`: a sealed interface with `color()`, `carsMayGo()`, `next()`, `onPedestrianButton()` |
| Concrete states | `Red`, `Green`, `Yellow`: each decides its own behavior and next state |
| Context | `TrafficLight`: holds the current state and delegates to it (`change()`, `pressPedestrianButton()`) |

## Diagram
```
          change()            change()             change()
   ┌─────┐ ───────> ┌───────┐ ───────> ┌────────┐ ───────> ┌─────┐
   │ RED │          │ GREEN │          │ YELLOW │          │ RED │
   └─────┘          └───────┘          └────────┘          └─────┘
   button: error    button: → YELLOW   button: error
   cars: stop       cars: go           cars: stop
```

## Pros
- Each state's behavior lives in one small class, so there's no long `switch` on the colour.
- Transitions are explicit, because each state names its successor.
- Invalid actions are rejected by the state itself, with a clear message.
- A sealed interface makes the full set of states visible to the compiler.

## Cons
- More classes than a simple `enum` plus `switch`, which is overkill for 2 or 3 trivial states.
- Transition rules are spread across state classes rather than shown in one table.

## Related patterns
- **Strategy:** structurally the same (the context delegates to an object), but strategies are chosen by the client, while states switch themselves.
- **Singleton / Flyweight:** stateless state objects, like these records, can be shared.
- **Memento:** can save and restore which state the context is in.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.state.TrafficLightState
mvn test -Dtest=TrafficLightStateTest
```
