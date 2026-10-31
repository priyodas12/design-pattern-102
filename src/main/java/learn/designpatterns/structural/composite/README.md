# Composite

**Family:** Structural · **Status:** implemented

**Analogy:** a file system. A folder can hold files and other folders, and you can ask either one "how big are you?". A file answers with its own size. A folder adds up the answers of everything inside it. The code asking never needs to know which one it's talking to.

Source: [`FileSystemComposite.java`](FileSystemComposite.java) · Test: [`FileSystemCompositeTest.java`](../../../../../../test/java/learn/designpatterns/structural/composite/FileSystemCompositeTest.java)

## Intent
Compose objects into tree structures and let clients treat single objects and groups the same way.

## When to use
- Your data is naturally a tree: folders and files, menus and items, an org chart.
- Clients should handle one item and a group of items with the same code.
- Operations such as size, render or count should naturally work across the whole tree.

## Roles
| Role | In this example |
|------|-----------------|
| Component | `Node`: a sealed interface with `name()`, `size()` and `render()` |
| Leaf | `FileLeaf`: has its own size and no children |
| Composite | `Folder`: holds child `Node`s; its `size()` is the sum of its children's |
| Client | `main`, and test code that sums any `List<Node>` |

## Diagram
```
                Node
          name() / size()
           ▲           ▲
       FileLeaf      Folder ──children──> Node, Node, ...
       (size)        size() = Σ child.size()

  home/ (510)
    notes.txt (10)
    photos/ (500)
      beach.jpg (300)
      party.jpg (200)
```

## Pros
- Clients use one uniform interface, without `if (isFolder)` branches.
- New node types slot in easily.
- Recursive operations (`size`, `render`) are short and natural.

## Cons
- A uniform interface can be too general. For example, it's hard to stop a leaf being given child-related operations, which is why `add` lives only on `Folder` here.
- Deep trees need care against cycles. `Folder.add` refuses to put a folder inside itself.

## Related patterns
- **Iterator:** to walk through a composite tree.
- **Visitor:** to add new operations over a tree without changing node classes.
- **Decorator:** a similar recursive structure, but each decorator wraps exactly one object.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.composite.FileSystemComposite
mvn test -Dtest=FileSystemCompositeTest
```
