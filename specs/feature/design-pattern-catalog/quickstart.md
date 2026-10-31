# Quickstart: Design Pattern Catalog

## Prerequisites

- JDK 21 or newer (checked with OpenJDK 27.0.1): `java -version`
- Maven 3.6.3 or newer (checked with 3.10.0): `mvn -v`
- Branch: `git switch feature/design-pattern-catalog`

## Setup

```bash
cd design-pattern-102
mvn -q compile        # first run downloads JUnit Jupiter and the plugins
```

## US1: Catalog skeleton

1. Run `mvn test`.
   **Expected:** `BUILD SUCCESS`, 0 failures, `CatalogStructureTest` passes, and every pattern still marked placeholder is reported as **skipped** (23 skipped before US2 starts).
2. Open `README.md` and go to **Pattern Catalog**.
   **Expected:** 3 tables with 5, 7 and 11 rows, each with a status.
3. Click any pattern link, for example Visitor.
   **Expected:** `src/main/java/learn/designpatterns/behavioral/visitor/README.md` opens and shows the name, family, intent and status.
4. Negative check: temporarily rename one README (for example, `mv .../creational/builder/README.md /tmp/`), then run `mvn test`.
   **Expected:** the build fails, and the message names `creational/builder`. Restore the file afterwards.

## US2: Creational patterns

For each of `AppConfigSingleton`, `LogisticsFactoryMethod`, `UiThemeAbstractFactory`, `BurgerOrderBuilder` and `DocumentTemplatePrototype`:

```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.creational.builder.BurgerOrderBuilder
mvn test -Dtest=BurgerOrderBuilderTest
```

**Expected:** readable demo output describing the analogy (for example, the assembled burger order). Tests report `Skipped: 0`, all passing. The README shows `Status: implemented` and all 9 sections. The index row reads "implemented".

## US3: Structural patterns

Same steps, using the `structural` package and these classes: `TravelPlugAdapter`, `RemoteControlBridge`, `FileSystemComposite`, `CoffeeDecorator`, `HomeTheaterFacade`, `ForestFlyweight`, `ImageLoaderProxy`.

**Spot check:** `CoffeeDecorator` prints the description and price after each add-on is applied.

## US4: Behavioral patterns

Same steps, using the `behavioral` package and these classes: `SupportEscalationChain`, `TvRemoteCommand`, `ArithmeticInterpreter`, `PlaylistIterator`, `ChatRoomMediator`, `TextEditorMemento`, `NewsChannelObserver`, `TrafficLightState`, `PaymentStrategy`, `BeverageTemplateMethod`, `ShoppingCartVisitor`.

**Spot check:** in the `TextEditorMemento` test, undo restores the exact previous text.

## Final verification

```bash
for i in 1 2 3; do mvn -q test; done   # identical results, each under 30 s
```

**Expected:** 0 failures and 0 skipped, with all 23 rows in the index reading "implemented".
