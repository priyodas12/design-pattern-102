# Mediator

**Family:** Behavioral · **Status:** implemented

**Analogy:** a group chat. Members never message each other directly or keep a list of each other. They post to the chat room, and the room decides who receives what. Adding or removing a member affects only the room, not every other member.

Source: [`ChatRoomMediator.java`](ChatRoomMediator.java) · Test: [`ChatRoomMediatorTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/mediator/ChatRoomMediatorTest.java)

## Intent
Define an object that controls how a group of objects interact, so they don't refer to each other directly.

## When to use
- Many objects talk to many others, and the web of references is getting tangled.
- Interaction rules, such as who gets which message, should live in one place.
- You want to reuse the individual objects without dragging their contacts along.

## Roles
| Role | In this example |
|------|-----------------|
| Mediator | `ChatRoom`: membership, `broadcast()`, `sendPrivate()`, and the room `log()` |
| Colleagues | `User`: holds a reference to the room only; `send()`, `sendTo()`, `inbox()` |
| Client | `main` and the tests: `room.join(name)` creates members |

## Diagram
```
         Ana        Ben        Cy           (users never reference each other)
          │  send()  ▲          ▲
          ▼          │          │
        ┌──────────── ChatRoom ────────────┐
        │ broadcast: everyone except sender │
        │ sendPrivate: one named member     │
        │ log: all broadcasts, in order     │
        └───────────────────────────────────┘
```

## Pros
- Many-to-many links become one-to-many: each user knows only the room.
- Routing rules live in one class and are easy to change.
- Members can join or leave without changes to any other member.

## Cons
- The mediator can grow into a large "god object" holding all the logic.
- It's a single point of failure: if the room is wrong, every interaction is wrong.

## Related patterns
- **Observer:** a mediator often uses observer-style notifications to reach its colleagues.
- **Facade:** also centralises access, but the subsystem doesn't know the facade exists. Colleagues actively talk *to* the mediator.
- **Command:** messages sent through a mediator are sometimes command objects.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.mediator.ChatRoomMediator
mvn test -Dtest=ChatRoomMediatorTest
```
