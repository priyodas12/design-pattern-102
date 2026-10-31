# Prototype

**Family:** Creational · **Status:** implemented

**Analogy:** an office keeps master templates such as an invoice or a letter. Instead of typing a new document from scratch, you photocopy the template and edit the copy. Writing on the copy never changes the master.

Source: [`DocumentTemplatePrototype.java`](DocumentTemplatePrototype.java) · Test: [`DocumentTemplatePrototypeTest.java`](../../../../../../test/java/learn/designpatterns/creational/prototype/DocumentTemplatePrototypeTest.java)

## Intent
Create new objects by copying an existing instance instead of building them from scratch.

## When to use
- New objects are mostly like an existing one, with small changes.
- Setting an object up is costly or fiddly, and copying a ready-made one is simpler.
- You want to add "kinds" of objects at runtime, by registering templates, instead of writing subclasses.

## Roles
| Role | In this example |
|------|-----------------|
| Prototype | `Prototype<T>`: declares `copy()` |
| Concrete prototype | `Document`: copies its title, author and a *new* list of sections (a deep copy) |
| Prototype registry | `TemplateRegistry`: stores masters, and `create(name)` returns a fresh copy |
| Client | `main`: copies the invoice master and edits the copy |

## Diagram
```
  TemplateRegistry
    "invoice" ──> Document (master) ──copy()──> Document (new, independent)
                   sections: [Header, Line items]     sections: [Header, Line items, Late-payment note]
                   (unchanged)                         (edited by the client)
```

## Pros
- New objects are created without knowing their concrete class or setup steps.
- New template types can be added at runtime.
- Fewer subclasses than Factory Method when the variants differ only in data.

## Cons
- Deep copies must be written carefully. A shallow copy would share the mutable section list and let edits leak between documents.
- Objects with circular references are hard to copy correctly.
- Java's built-in `Cloneable`/`clone()` is awkward, so this example uses an explicit `copy()` method instead.

## Related patterns
- **Factory Method:** an alternative that creates by subclassing instead of copying.
- **Abstract Factory:** a factory can keep prototypes and copy them instead of calling `new`.
- **Memento:** also captures an object's state, but in order to restore it later.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.creational.prototype.DocumentTemplatePrototype
mvn test -Dtest=DocumentTemplatePrototypeTest
```
