# Memento

**Family:** Behavioral · **Status:** implemented

**Analogy:** undo in a text editor. Before a change, the editor takes a sealed snapshot of its text. The undo history stores these snapshots without being able to read or change them. Undo hands the latest snapshot back to the editor, which restores itself exactly.

Source: [`TextEditorMemento.java`](TextEditorMemento.java) · Test: [`TextEditorMementoTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/memento/TextEditorMementoTest.java)

## Intent
Capture an object's internal state so it can be restored later, without breaking encapsulation.

## When to use
- You need undo, checkpoints or "restore to saved".
- The object's internals should stay private, even from the code that stores the snapshots.
- Saving the whole state is simpler than recording every change.

## Roles
| Role | In this example |
|------|-----------------|
| Originator | `Editor`: `type()`, `deleteLast()`, `save()` → `Memento`, `restore(Memento)` |
| Memento | `Memento`: an immutable snapshot whose text field is private, and whose constructor only the editor (in the same file) calls |
| Caretaker | `History`: `backup()` pushes `editor.save()`, and `undo()` pops and restores; it never reads inside a memento |

## Diagram
```
  Editor ──save()──> Memento("Dear team,")  ──push──> History (stack)
     │                                                   │
     │ type(" the meeting is cancelled.")                │
     │                                                   │ undo()
     ◄──────────────── restore(pop()) ───────────────────┘
  text: "Dear team,"   (exactly as saved)
```

## Pros
- The originator's internals stay hidden, and the caretaker treats snapshots as opaque.
- Undo restores the exact earlier state, with no reverse operations to write.
- Snapshots are immutable, so later edits can't corrupt them.

## Cons
- Memory use grows with each snapshot, which matters for big objects or long histories.
- Saving whole states can be wasteful when changes are tiny. Commands that record only the change can be cheaper.
- Java can't make a memento's contents invisible to every other class. Here, it relies on being private within one file.

## Related patterns
- **Command:** can use mementos to undo itself.
- **Iterator:** a memento can record an iterator's position.
- **Prototype:** another way to copy state, but to create new objects, not to roll back an existing one.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.memento.TextEditorMemento
mvn test -Dtest=TextEditorMementoTest
```
