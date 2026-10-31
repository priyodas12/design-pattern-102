# Observer

**Family:** Behavioral · **Status:** implemented

**Analogy:** a news channel and its subscribers. Readers subscribe once. Whenever the channel publishes a headline, every current subscriber is notified automatically. The channel doesn't know what each subscriber does with the news, and readers can unsubscribe at any time.

Source: [`NewsChannelObserver.java`](NewsChannelObserver.java) · Test: [`NewsChannelObserverTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/observer/NewsChannelObserverTest.java)

## Intent
Define a one-to-many dependency so that when one object changes, all its dependents are notified.

## When to use
- Several objects must react when one object changes, and the set of reactors changes at runtime.
- The object that changes shouldn't depend on the concrete classes that react.
- For event systems, UI updates, notifications and publish/subscribe designs.

## Roles
| Role | In this example |
|------|-----------------|
| Subject | `NewsChannel`: `subscribe()`, `unsubscribe()`, `publish(headline)` |
| Observer | `Subscriber`: a functional interface with one method, `update(channelName, headline)` |
| Concrete observer | `PhoneApp`: stores notifications. A lambda works too, as in the TV ticker in `main`. |

## Diagram
```
                       NewsChannel ("Daily Tech")
   publish("New phone released")  │ for each subscriber, in order:
                     ┌────────────┼──────────────┐
                     ▼            ▼              ▼
              PhoneApp(Ana)  PhoneApp(Ben)   lambda (TV ticker)
              .update(...)   .update(...)    .update(...)

   unsubscribe(Ben) → later headlines skip Ben
```

## Pros
- The subject knows only the `Subscriber` interface, not concrete classes.
- Observers can be added and removed at runtime. A single-method interface allows lambdas.
- One change automatically updates everything that depends on it.

## Cons
- Notification order is a design decision. Here it's subscription order, which is easy to come to rely on by accident.
- Forgotten subscriptions keep objects alive (the "lapsed listener" leak).
- Long chains of observers that trigger each other are hard to follow and debug.

## Related patterns
- **Mediator:** often uses Observer to notify its colleagues.
- **Singleton:** a global event bus is commonly a single shared subject.
- **Command:** notifications can be sent as command objects for queueing or replay.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.observer.NewsChannelObserver
mvn test -Dtest=NewsChannelObserverTest
```
