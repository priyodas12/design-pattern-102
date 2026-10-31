# Chain of Responsibility

**Family:** Behavioral · **Status:** implemented

**Analogy:** a customer support desk. A ticket goes to a support agent first. If it's too hard, the agent passes it to a manager, and the manager to a director. The customer only ever hands the ticket to the front desk and never needs to know who finally solves it.

Source: [`SupportEscalationChain.java`](SupportEscalationChain.java) · Test: [`SupportEscalationChainTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/chainofresponsibility/SupportEscalationChainTest.java)

## Intent
Pass a request along a chain of handlers until one handles it, so the sender doesn't need to know the receiver.

## When to use
- Several objects might handle a request, and which one should isn't known in advance.
- You want to try handlers in a set order, such as escalation levels, filters or middleware.
- The set or order of handlers should be configurable without changing the sender.

## Roles
| Role | In this example |
|------|-----------------|
| Handler | `SupportHandler`: `handle()` either resolves the ticket or passes it to `next` |
| Concrete handlers | `Agent` (severity ≤ 1), `Manager` (≤ 2), `Director` (≤ 3) |
| Request | `Ticket(id, severity, issue)` |
| Client | `main`: builds the chain with `SupportHandler.chain(...)` and talks only to the first handler |

## Diagram
```
  client ──ticket──> Agent ──can't──> Manager ──can't──> Director ──can't──> IllegalStateException
                       │ severity ≤ 1    │ severity ≤ 2     │ severity ≤ 3
                       └─ resolve        └─ resolve         └─ resolve
```

## Pros
- The sender is decoupled from the receivers: it knows only the head of the chain.
- Handlers can be added, removed or reordered without touching the sender.
- Each handler has one simple rule.

## Cons
- A request might not be handled at all. Here, the end of the chain throws a clear error.
- Following which handler acted can be harder when debugging long chains.

## Related patterns
- **Composite:** a parent component can act as the next handler for its children.
- **Command:** requests passed along a chain are often command objects.
- **Decorator:** a similar linked structure, but every decorator acts, rather than the first capable one.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.chainofresponsibility.SupportEscalationChain
mvn test -Dtest=SupportEscalationChainTest
```
