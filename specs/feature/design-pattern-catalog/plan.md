# Implementation Plan: Design Pattern Catalog

**Branch:** `feature/design-pattern-catalog` · **Spec:** [spec.md](spec.md) · **Research:** [research.md](research.md) · **Data model:** [data-model.md](data-model.md) · **Contracts:** [contracts/](contracts/) · **Quickstart:** [quickstart.md](quickstart.md) · **Tasks:** [tasks.md](tasks.md) · **Project rules:** [CLAUDE.md](../../../CLAUDE.md)

## Summary

The goal is to build a catalog of the 23 classic design patterns in 3 families, each with an explanation document and tests (see [spec.md](spec.md)). First, a skeleton for all 23 is created: a package, a README stub and a disabled test for each, plus family overviews, a root index and an automated completeness check (US1). Then each family is filled in with standalone, analogy-based Java examples, full READMEs and behavior tests (US2 to US4).

## Technical Context

| Item | Value | Source |
|------|-------|--------|
| Language | Java, `maven.compiler.release=21`; local JDK is OpenJDK 27.0.1 | Proposed (confirm): [R1](research.md#r1-java-version) |
| Build | Maven (3.10.0 installed), with plugins pinned: enforcer 3.7.0, compiler 3.16.0, surefire 3.6.1, exec 3.6.4 | Detected `pom.xml` + proposed: [R8](research.md#r8-build-plugins) |
| Dependencies | `org.junit.jupiter:junit-jupiter:6.1.3` (test scope) only, replacing `junit:junit:4.13.2` | Proposed (confirm): [R2](research.md#r2-test-framework), [R7](research.md#r7-logging-and-output) |
| Storage | none | — |
| Testing | JUnit Jupiter via Surefire; `@Disabled` for placeholders; `CatalogStructureTest` for completeness | [R2](research.md#r2-test-framework), [R6](research.md#r6-catalog-completeness-check-fr-005-fr-015) |
| Layout | base package `learn.designpatterns`; README next to the code | Proposed (confirm): [R3](research.md#r3-where-the-per-pattern-readme-lives), [R4](research.md#r4-base-package-and-naming) |
| Platform | any OS with JDK 21+ | — |
| Constraints | examples under ~120 lines, JDK only, deterministic tests, full suite under 30 s | spec NFRs |

## CLAUDE.md Check (gate)

| Rule (../../../CLAUDE.md) | Complies? |
|---------------------------|-----------|
| Spec-driven: one feature = branch = `specs/feature/<gist>/` | Yes: `feature/design-pattern-catalog` |
| Don't add dependencies without saying why | Yes: one test dependency (JUnit Jupiter), justified in R2; JUnit 4 is removed |
| Never commit secrets or `.env` | Yes: examples use fake data only |
| Tests deterministic; no sleeps, network or clock | Yes: required by the [pattern-module contract](contracts/pattern-module.md) |
| Each example standalone; no shared code across patterns | Yes: FR-011, enforced in review |
| Don't change spec requirements without telling the user | Yes |
| Don't modify `.idea/` or `.iml` | Yes |

**Complexity tracking:** no violations.

## Design

| Component | What it is | FRs |
|-----------|------------|-----|
| `pom.xml` | Java 21, JUnit Jupiter, pinned plugins, enforcer, exec | FR-013, FR-014 |
| Pattern package (×23) | `package-info.java` + `README.md` + test; later the main class ([contract](contracts/pattern-module.md)) | FR-002 to FR-004, FR-008, FR-010 to FR-012 |
| Family README (×3) | the overview table for each family ([contract](contracts/pattern-readme.md)) | FR-006 |
| Root README index | the `## Pattern Catalog` section | FR-007 |
| `CatalogStructureTest` | the 23-entry table; checks that files exist and that status matches the code ([data-model V1 to V3](data-model.md#validation-rules)) | FR-001, FR-005, FR-015 |

Data flow is trivial: `mvn test` compiles everything, Surefire runs each pattern test plus `CatalogStructureTest`, and placeholders are reported as skipped.

### Pattern table (proposed analogies; confirm or change)

| Family | Pattern | Package | Main class | Defining behavior to test |
|--------|---------|---------|------------|---------------------------|
| creational | Singleton | `singleton` | `AppConfigSingleton` | same instance every time; thread-safe lazy init |
| creational | Factory Method | `factorymethod` | `LogisticsFactoryMethod` | road → Truck, sea → Ship; client never names the concrete type |
| creational | Abstract Factory | `abstractfactory` | `UiThemeAbstractFactory` | each factory yields a matching Button and Checkbox |
| creational | Builder | `builder` | `BurgerOrderBuilder` | step-by-step build; immutable result; missing bun → error |
| creational | Prototype | `prototype` | `DocumentTemplatePrototype` | the copy equals the original but changes don't leak between them |
| structural | Adapter | `adapter` | `TravelPlugAdapter` | a US plug works through the adapter in an EU socket |
| structural | Bridge | `bridge` | `RemoteControlBridge` | any remote works with any device (2×2 combinations) |
| structural | Composite | `composite` | `FileSystemComposite` | folder size = sum of nested files and folders |
| structural | Decorator | `decorator` | `CoffeeDecorator` | add-ons stack price and description in order |
| structural | Facade | `facade` | `HomeTheaterFacade` | one `watchMovie()` call drives all subsystems in order |
| structural | Flyweight | `flyweight` | `ForestFlyweight` | 1,000 trees share 2 species objects |
| structural | Proxy | `proxy` | `ImageLoaderProxy` | lazy load on first display; second display is served from the cache |
| behavioral | Chain of Responsibility | `chainofresponsibility` | `SupportEscalationChain` | each ticket is handled by the first capable level; unhandled → error |
| behavioral | Command | `command` | `TvRemoteCommand` | execute and undo restore the previous state |
| behavioral | Interpreter | `interpreter` | `ArithmeticInterpreter` | `"5 + 3 - 2"` → 6; bad token → error |
| behavioral | Iterator | `iterator` | `PlaylistIterator` | visits songs in order; `next()` past the end → error |
| behavioral | Mediator | `mediator` | `ChatRoomMediator` | a message reaches all members except the sender |
| behavioral | Memento | `memento` | `TextEditorMemento` | undo restores the exact earlier text |
| behavioral | Observer | `observer` | `NewsChannelObserver` | subscribers are notified; unsubscribed ones are not |
| behavioral | State | `state` | `TrafficLightState` | red → green → yellow → red; an invalid action → error |
| behavioral | Strategy | `strategy` | `PaymentStrategy` | the same checkout gives a different fee per payment method |
| behavioral | Template Method | `templatemethod` | `BeverageTemplateMethod` | tea and coffee share the step order; differ in brew and add-ons |
| behavioral | Visitor | `visitor` | `ShoppingCartVisitor` | the tax visitor computes a per-category rate over book, food and electronics |

## Project Structure

```
pom.xml                                         # MODIFY: Java 21, Jupiter 6.1.3, pinned plugins
README.md                                       # MODIFY: add Pattern Catalog index; refresh sections
CLAUDE.md                                       # NEW (this run)
src/main/java/org/example/App.java              # DELETE
src/test/java/org/example/AppTest.java          # DELETE
src/main/java/learn/designpatterns/
  creational/README.md                          # NEW: family overview
  creational/<pattern>/package-info.java        # NEW ×5
  creational/<pattern>/README.md                # NEW ×5
  creational/<pattern>/<Main>.java              # NEW ×5 (US2)
  structural/...                                # same, ×7 (US3)
  behavioral/...                                # same, ×11 (US4)
src/test/java/learn/designpatterns/
  CatalogStructureTest.java                     # NEW
  <family>/<pattern>/<Main>Test.java            # NEW ×23
```

## Testing Approach

- **Unit:** each `<Main>Test` checks the defining behavior from the pattern table above, plus error paths (FR-010, FR-012).
- **Integration (structure):** `CatalogStructureTest` checks all 23 entries against the file system and README Status lines (FR-005, FR-015).
- **Manual end-to-end:** [quickstart.md](quickstart.md) walkthroughs (run examples, follow index links).
- **Defining check per story:**
  - US1: `mvn test` reports 0 failures and 23 skipped, and `CatalogStructureTest` passes.
  - US2, US3 and US4: that family's tests report 0 skipped, and its Status lines read `implemented`.

## Phases

1. **Setup:** update `pom.xml`, remove the quickstart code, add CLAUDE.md.
2. **Foundational:** `CatalogStructureTest` with the 23-entry table.
3. **US1 (P1):** create the skeleton for all 23 patterns, plus the family READMEs and root index.
4. **US2 (P2):** creational ×5.
5. **US3 (P3):** structural ×7.
6. **US4 (P4):** behavioral ×11.
7. **Polish:** refresh the root README, run the full suite 3 times, update the CLAUDE.md feature status.

## Risks and Mitigations

| Risk | Impact | Mitigation |
|------|--------|------------|
| README links break when files move | Learners hit 404s | Links are relative; `CatalogStructureTest` fixes the paths; review links in the Polish phase |
| Status lines drift from reality | Misleading index (FR-015) | `CatalogStructureTest` checks status against whether the main class exists |
| Examples grow large or clever | Harder to learn from | ~120-line target; one analogy per pattern; review against the contract |
| Placeholders linger as skipped tests | False sense of completeness | The skipped count is visible in every `mvn test`; the index shows status |
| Scope question open (US1 only vs all) | Rework or an oversized PR | US1 is a standalone phase and can be merged on its own once the clarification is resolved |
| JDK below 21 on another machine | Confusing compile errors | Enforcer rule `requireJavaVersion [21,)` |
