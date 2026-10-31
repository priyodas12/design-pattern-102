# Iterator

**Family:** Behavioral · **Status:** implemented

**Analogy:** a music player's "next" button. You step through a playlist one song at a time without knowing how the playlist stores its songs. The playlist can also offer other ways to step through it (here, in reverse), and two listeners can each keep their own place.

Source: [`PlaylistIterator.java`](PlaylistIterator.java) · Test: [`PlaylistIteratorTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/iterator/PlaylistIteratorTest.java)

## Intent
Access the elements of a collection one by one without exposing how it is stored.

## When to use
- A collection's storage (list, tree, database page) should stay hidden from its users.
- You need more than one way to traverse the same collection, such as forward, reverse or filtered.
- Several traversals should be able to run at the same time, each with its own position.

## Roles
| Role | In this example |
|------|-----------------|
| Iterator | `java.util.Iterator<Song>`: `hasNext()`, `next()` |
| Concrete iterators | `Playlist.ForwardIterator`, `Playlist.ReverseIterator`: private inner classes, each with its own position |
| Aggregate | `Playlist` (implements `Iterable<Song>`): `iterator()`, `reverseIterator()`; its `List` is never exposed |
| Element | `Song(title, artist)` |

## Diagram
```
  Playlist ("Road Trip")             private List<Song> songs  (hidden)
     │ iterator()        ──> ForwardIterator  next=0 → 1 → 2 → NoSuchElementException
     │ reverseIterator() ──> ReverseIterator  next=2 → 1 → 0
     └ for (Song s : playlist)   (uses iterator() through Iterable)
```

## Pros
- The collection's internals stay private.
- New traversal orders can be added without changing callers.
- Iterators are independent, so two traversals don't interfere.
- Using Java's `Iterator`/`Iterable` makes the collection work in for-each loops.

## Cons
- Overkill for a plain list you could simply expose as read-only.
- Changing the collection during iteration isn't detected here. Java's built-in collections throw `ConcurrentModificationException` in that case.
- Going past the end throws `NoSuchElementException` (the `Iterator` contract) rather than the project's usual `IllegalStateException`.

## Related patterns
- **Composite:** iterators are often used to walk composite trees.
- **Factory Method:** `iterator()` is a factory method that returns the right iterator subclass.
- **Memento:** an iterator's position can be saved and restored like a memento.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.iterator.PlaylistIterator
mvn test -Dtest=PlaylistIteratorTest
```
