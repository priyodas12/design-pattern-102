# Facade

**Family:** Structural · **Status:** implemented

**Analogy:** a home theater has lights, a projector, an amplifier and a streaming player, each with its own controls. To watch a movie you'd have to operate all of them in the right order. A single "watch movie" button, the facade, does it for you. The individual devices are still there if you need them.

Source: [`HomeTheaterFacade.java`](HomeTheaterFacade.java) · Test: [`HomeTheaterFacadeTest.java`](../../../../../../test/java/learn/designpatterns/structural/facade/HomeTheaterFacadeTest.java)

## Intent
Provide one simplified interface to a set of interfaces in a subsystem.

## When to use
- A subsystem has many parts, and common tasks need several calls in a specific order.
- You want to decouple clients from the subsystem's internals.
- You want a clear entry point into a layer of the system.

## Roles
| Role | In this example |
|------|-----------------|
| Facade | `HomeTheaterFacade`: `watchMovie(title)`, `endMovie()` |
| Subsystem classes | `Lights`, `Projector`, `Amplifier`, `StreamingPlayer` |
| Client | `main` and the tests: they call the facade only |

Each subsystem writes what it did to a shared log, available through `log()`, so the order of operations is visible.

## Diagram
```
  client ──watchMovie("Space Voyage")──> HomeTheaterFacade
                                            │ 1. lights.dim(10)
                                            │ 2. projector.on(), setInput("streaming")
                                            │ 3. amplifier.on(), setVolume(5)
                                            └ 4. player.play(title)
  client ──endMovie()──> facade: player.stop → amplifier.off → projector.off → lights.on
```

## Pros
- Clients make one simple call instead of knowing the whole subsystem.
- The correct order is coded once (start-up, and shutdown in reverse).
- Subsystem internals can change without affecting clients.

## Cons
- A facade can grow into a "god object" that does too much.
- It can hide useful fine-grained control. Advanced clients may still need the subsystem classes directly.

## Related patterns
- **Adapter:** changes an interface to fit; Facade simplifies a whole group of interfaces.
- **Mediator:** also coordinates objects, but the colleagues talk *to* the mediator. With a facade, the subsystem doesn't know the facade exists.
- **Singleton:** a facade is often one shared instance.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.facade.HomeTheaterFacade
mvn test -Dtest=HomeTheaterFacadeTest
```
