# Command

**Family:** Behavioral · **Status:** implemented

**Analogy:** a TV remote. Each button press is turned into a small "command" object that knows how to do its action and how to undo it. The remote keeps a history of pressed commands, so an undo button can reverse them one by one, without the remote knowing how a TV works.

Source: [`TvRemoteCommand.java`](TvRemoteCommand.java) · Test: [`TvRemoteCommandTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/command/TvRemoteCommandTest.java)

## Intent
Wrap a request in an object, so requests can be queued, logged and undone.

## When to use
- You need undo or redo.
- You want to queue, schedule, log or replay actions.
- The object that triggers an action (a button) shouldn't depend on the object that performs it (a TV).

## Roles
| Role | In this example |
|------|-----------------|
| Command | `Command`: `execute()`, `undo()` |
| Concrete commands | `PowerToggle`, `ChangeChannel`, `VolumeUp`: each remembers what it needs to undo |
| Receiver | `Tv`: does the real work (`setPower`, `setChannel`, `setVolume`) |
| Invoker | `RemoteControl`: `press()` runs a command and pushes it on the history; `undo()` pops and reverses it |
| Client | `main`: creates commands bound to the TV and hands them to the remote |

## Diagram
```
  client ──new ChangeChannel(tv, 42)──> RemoteControl.press(cmd)
                                          │ cmd.execute() ──> tv.setChannel(42)  (remembers 1)
                                          └ history.push(cmd)
  RemoteControl.undo() ──> history.pop().undo() ──> tv.setChannel(1)

  history (top first): VolumeUp, ChangeChannel, PowerToggle
```

## Pros
- Undo and redo come naturally: each command carries its own reversal.
- The invoker is decoupled from receivers, so the remote works with any command.
- Commands are objects that can be stored, queued, logged or combined into macros.

## Cons
- There's one class per action, so many small classes.
- Each command must capture enough state to undo correctly. Here, `ChangeChannel` and `VolumeUp` store the previous value.
- A command that fails half-way needs care. Here, `press()` executes before recording, so a failed command never enters the history.

## Related patterns
- **Memento:** can store the state a command needs for undo, instead of the command tracking it.
- **Chain of Responsibility:** requests passed along a chain are often commands.
- **Strategy:** also wraps behavior in objects, but to choose *how* something is done, not to record *what* was done.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.command.TvRemoteCommand
mvn test -Dtest=TvRemoteCommandTest
```
