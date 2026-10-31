# CLAUDE.md

## Project

`design-pattern-102` is a Java learning catalog of the 23 classic (GoF) design patterns, in three families: creational (5), structural (7) and behavioral (11). Each pattern lives in its own package. It has a `README.md` explanation next to the code, a standalone example built on an everyday analogy, and JUnit tests. Unimplemented patterns exist as placeholders: a README stub and a `@Disabled` test.

## Tech Stack

- Java with `maven.compiler.release=21`; the local JDK is OpenJDK 27.0.1.
- Maven 3.6.3+ (3.10.0 installed). No Maven wrapper.
- JUnit Jupiter 6.1.3 (test scope). No other dependencies.
- Pinned plugins: enforcer 3.7.0, compiler 3.16.0, surefire 3.6.1, exec 3.6.4.
- Build checked (enforcer rules pass) on JDK 27.0.1 and Maven 3.10.0.

## Commands

| Purpose | Command |
|---------|---------|
| Build | `mvn -q compile` |
| Test all | `mvn test` |
| Test one pattern | `mvn test -Dtest=CoffeeDecoratorTest` |
| Run one example | `mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.decorator.CoffeeDecorator` |
| Package | `mvn package` |
| Lint / format | none yet; record the command here if one is added |

## Folder Structure

```
pom.xml
README.md                                   # project overview + Pattern Catalog index (all 23, with status)
CLAUDE.md                                   # this file
src/main/java/learn/designpatterns/
  <family>/README.md                        # family overview: creational | structural | behavioral
  <family>/<pattern>/package-info.java      # pattern name, family, intent
  <family>/<pattern>/README.md              # pattern explanation (placeholder or full)
  <family>/<pattern>/<Analogy><Pattern>.java# standalone example (once implemented)
src/test/java/learn/designpatterns/
  CatalogStructureTest.java                 # fails if any pattern is missing files or has a wrong status
  <family>/<pattern>/<Main>Test.java        # behavior tests (@Disabled while placeholder)
specs/feature/<gist>/                       # one folder per feature, mirroring branch feature/<gist>; dev specs, not reader docs
```

Package names are lowercase with words joined (`chainofresponsibility`, `templatemethod`).

## Coding Conventions

- One public `final` main class per pattern, named `<Analogy><Pattern>` (for example, `BurgerOrderBuilder`). Pattern roles are nested types named after the analogy. Use records, sealed types and `switch` patterns where they make the code clearer.
- Each example is standalone: JDK only, no imports from other pattern packages, no shared utilities. Target under about 120 lines.
- Use real-life analogies; never `Foo` or `Bar`. Javadoc on the main class names the pattern and the analogy.
- Expose results through return values and state, so tests check behavior. `main` prints a short demo with `System.out.println`. No logging framework.
- Errors: `IllegalArgumentException` for bad input and `IllegalStateException` for an invalid state or transition, with a clear message. Never swallow exceptions.
- 4-space indent, UTF-8.
- Tests: JUnit Jupiter, in the same package as the code. At least 2 behavior tests plus error paths. Deterministic: no sleeps, wall clock, unseeded randomness or network.
- The README for each pattern follows `specs/feature/design-pattern-catalog/contracts/pattern-readme.md`, including the `**Status:**` line.

## Development Workflow (spec-driven)

- One feature = branch `feature/<gist>` = folder `specs/feature/<gist>/` (no number prefix). Create it with `/gen-spec`.
- Before coding, read the feature's `spec.md`, `plan.md`, `research.md`, `data-model.md`, `contracts/` and `tasks.md`. Resolve every `[NEEDS CLARIFICATION]` first.
- Work through `tasks.md` in order. Tick each task in the same commit as its code. Keep commits small and focused (one pattern per commit).
- Run `mvn test` (and the example's `main` once) before declaring a task or story done. Fix failures; don't weaken tests.
- Merge the specs to the base branch together with the code. Never delete a feature's specs.

## Rules / Guardrails

- Never commit secrets or `.env`. Examples use fake data only.
- Don't add dependencies without noting why, in the feature's `research.md`.
- Don't change a feature's `spec.md` requirements without telling the user.
- Ask when a requirement is ambiguous.
- Don't modify `.idea/` or `*.iml`.
- Keep `CatalogStructureTest` and the README status lines accurate. A pattern is `implemented` only when its main class exists and it has no skipped tests.

## Features

| Feature | Branch | Specs | Status |
|---------|--------|-------|--------|
| Design Pattern Catalog | `feature/design-pattern-catalog` | `specs/feature/design-pattern-catalog/` | Draft (specs written; not implemented) |
