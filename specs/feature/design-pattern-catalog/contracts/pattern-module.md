# Contract: Pattern Module (public Java surface of each pattern)

**Serves:** FR-002, FR-004, FR-008, FR-010, FR-011, FR-012, FR-013, FR-014

This is the shape every pattern package must follow. There is no network or CLI API. The "interface" is the Java package, run through Maven.

## Layout (per pattern)

```
src/main/java/learn/designpatterns/<family>/<pattern>/
  package-info.java      # always: Javadoc with name, family, intent
  README.md              # always: see pattern-readme.md
  <Main>.java            # implemented only
src/test/java/learn/designpatterns/<family>/<pattern>/
  <Main>Test.java        # always
```

## Placeholder state

- `package-info.java`: `/** <Name> (<family>): <intent>. Status: placeholder. */ package learn.designpatterns.<family>.<pattern>;`
- `<Main>Test.java`: exactly one test, annotated `@Disabled("Placeholder: not implemented yet")`.

## Implemented state: `<Main>.java`

| Element | Rule |
|---------|------|
| Top-level type | `public final class <Main>`, with Javadoc that names the pattern and its analogy |
| Pattern roles | nested `interface`, `record` or `class` types, named after their role in the analogy (for example, `Beverage`, `MilkDecorator`) |
| Entry point | `public static void main(String[] args)`, which prints a short, readable demo to `System.out` |
| Observable behavior | public (or package-private) methods that return values or expose state, so tests check behavior without parsing output |
| Errors | invalid input throws `IllegalArgumentException`; invalid state or transition throws `IllegalStateException`. The message says what was wrong (FR-012) |
| Dependencies | JDK only; no imports from other pattern packages (FR-011) |
| Size | target under about 120 lines |

## Implemented state: `<Main>Test.java`

- JUnit Jupiter, in the same package as the main class, with no `@Disabled`.
- At least 2 `@Test` methods that check the pattern's **defining** behavior (see the table in `plan.md`), plus one error-path test when FR-012 applies.
- Deterministic: no `Thread.sleep`, no wall-clock time, no randomness without a fixed seed, and no network.

## Invocation

| Purpose | Command |
|---------|---------|
| All tests (FR-013) | `mvn test` |
| One pattern's tests (FR-013) | `mvn test -Dtest=<Main>Test` |
| Run one example (FR-014) | `mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.<family>.<pattern>.<Main>` |

## Example (Decorator)

```java
// learn/designpatterns/structural/decorator/CoffeeDecorator.java
public final class CoffeeDecorator {
    interface Beverage { String description(); int priceCents(); }
    record Espresso() implements Beverage { ... }
    record Milk(Beverage inner) implements Beverage { ... }
    public static void main(String[] args) { ... }  // prints "Espresso, Milk = $2.50"
}
```

```text
$ mvn test -Dtest=CoffeeDecoratorTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```
