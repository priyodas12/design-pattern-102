# design-pattern-102

A Java learning catalog of the 23 classic (GoF) design patterns, grouped into three families: creational (5), structural (7) and behavioral (11). Each pattern has its own package containing a `README.md` explanation, a standalone example built on an everyday analogy, and JUnit tests. All 23 patterns are implemented. Stack: Java 21, Maven, JUnit Jupiter 6.

## Features

- **The whole catalog, all 23 patterns:** each has a package under `learn.designpatterns.<family>.<pattern>` with a `README.md`, a `package-info.java` and a test class. There is an overview README for each family and an index below.
- **5 implemented creational patterns:** each is a runnable `main` example, under ~120 lines, using only the JDK:
  - `AppConfigSingleton`: one shared app configuration, created lazily and thread-safely with the holder idiom.
  - `LogisticsFactoryMethod`: road logistics creates a `Truck` and sea logistics creates a `Ship`.
  - `UiThemeAbstractFactory`: dark and light theme factories each make a matching `Button` and `Checkbox`.
  - `BurgerOrderBuilder`: a step-by-step, validated, immutable burger order with a computed price.
  - `DocumentTemplatePrototype`: deep-copied document templates, with a template registry.
- **7 implemented structural patterns**, in the same style:
  - `TravelPlugAdapter`: a US plug works in an EU socket through an adapter.
  - `RemoteControlBridge`: basic and advanced remotes work with any device (TV or radio).
  - `FileSystemComposite`: folders and files share one interface; a folder's size is the total of its contents.
  - `CoffeeDecorator`: milk, sugar and whipped-cream add-ons stack onto a base drink.
  - `HomeTheaterFacade`: one `watchMovie()` call drives the lights, projector, amplifier and player in order.
  - `ForestFlyweight`: 1,000 trees share just 2 species objects.
  - `ImageLoaderProxy`: images load lazily on first display and are cached afterwards.
- **11 implemented behavioral patterns**, in the same style:
  - `SupportEscalationChain`: a ticket passes from agent to manager to director until someone can handle it.
  - `TvRemoteCommand`: remote button presses become command objects with undo history.
  - `ArithmeticInterpreter`: parses and evaluates sentences like `"5 + 3 - 2"` as an expression tree.
  - `PlaylistIterator`: forward and reverse traversal of a playlist without exposing its storage.
  - `ChatRoomMediator`: users talk only through the chat room (broadcast and private messages).
  - `TextEditorMemento`: sealed text snapshots give exact undo.
  - `NewsChannelObserver`: subscribers, including lambdas, are notified of each headline in subscription order.
  - `TrafficLightState`: red, green and yellow are state classes; invalid button presses are rejected.
  - `PaymentStrategy`: card (2% fee), UPI and cash (capped at $500) are swappable at the checkout.
  - `BeverageTemplateMethod`: tea and coffee share a `final` recipe; a hook can skip the extras.
  - `ShoppingCartVisitor`: tax and shipping visitors run over books, food and electronics.
- **A README for each pattern** with these sections: intent, analogy, when to use, roles, a text diagram, pros, cons, related patterns, and the run commands.
- **A catalog completeness check:** `CatalogStructureTest` fails the build if any pattern is missing its README, `package-info.java` or test, or if its README's `**Status:**` line doesn't match the code.
- **Clear errors:** examples throw `IllegalArgumentException` for bad input and `IllegalStateException` for an invalid state, each with a descriptive message.

### Pattern Catalog

All 23 patterns by family. Each link opens that pattern's README. **Status** is `placeholder` (README stub and a skipped test) or `implemented` (runnable example, full README, passing tests). Family overviews: [creational](src/main/java/learn/designpatterns/creational/README.md), [structural](src/main/java/learn/designpatterns/structural/README.md), [behavioral](src/main/java/learn/designpatterns/behavioral/README.md).

#### Creational

| Pattern | Family | Status |
|---------|--------|--------|
| [Singleton](src/main/java/learn/designpatterns/creational/singleton/README.md) | Creational | implemented |
| [Factory Method](src/main/java/learn/designpatterns/creational/factorymethod/README.md) | Creational | implemented |
| [Abstract Factory](src/main/java/learn/designpatterns/creational/abstractfactory/README.md) | Creational | implemented |
| [Builder](src/main/java/learn/designpatterns/creational/builder/README.md) | Creational | implemented |
| [Prototype](src/main/java/learn/designpatterns/creational/prototype/README.md) | Creational | implemented |

#### Structural

| Pattern | Family | Status |
|---------|--------|--------|
| [Adapter](src/main/java/learn/designpatterns/structural/adapter/README.md) | Structural | implemented |
| [Bridge](src/main/java/learn/designpatterns/structural/bridge/README.md) | Structural | implemented |
| [Composite](src/main/java/learn/designpatterns/structural/composite/README.md) | Structural | implemented |
| [Decorator](src/main/java/learn/designpatterns/structural/decorator/README.md) | Structural | implemented |
| [Facade](src/main/java/learn/designpatterns/structural/facade/README.md) | Structural | implemented |
| [Flyweight](src/main/java/learn/designpatterns/structural/flyweight/README.md) | Structural | implemented |
| [Proxy](src/main/java/learn/designpatterns/structural/proxy/README.md) | Structural | implemented |

#### Behavioral

| Pattern | Family | Status |
|---------|--------|--------|
| [Chain of Responsibility](src/main/java/learn/designpatterns/behavioral/chainofresponsibility/README.md) | Behavioral | implemented |
| [Command](src/main/java/learn/designpatterns/behavioral/command/README.md) | Behavioral | implemented |
| [Interpreter](src/main/java/learn/designpatterns/behavioral/interpreter/README.md) | Behavioral | implemented |
| [Iterator](src/main/java/learn/designpatterns/behavioral/iterator/README.md) | Behavioral | implemented |
| [Mediator](src/main/java/learn/designpatterns/behavioral/mediator/README.md) | Behavioral | implemented |
| [Memento](src/main/java/learn/designpatterns/behavioral/memento/README.md) | Behavioral | implemented |
| [Observer](src/main/java/learn/designpatterns/behavioral/observer/README.md) | Behavioral | implemented |
| [State](src/main/java/learn/designpatterns/behavioral/state/README.md) | Behavioral | implemented |
| [Strategy](src/main/java/learn/designpatterns/behavioral/strategy/README.md) | Behavioral | implemented |
| [Template Method](src/main/java/learn/designpatterns/behavioral/templatemethod/README.md) | Behavioral | implemented |
| [Visitor](src/main/java/learn/designpatterns/behavioral/visitor/README.md) | Behavioral | implemented |

## Dependencies

| Name | Version | Scope | Purpose |
|------|---------|-------|---------|
| `org.junit.jupiter:junit-jupiter` | 6.1.3 | test | Unit tests and parameterized tests (`CatalogStructureTest`) |
| `maven-enforcer-plugin` | 3.7.0 | build | Fails fast unless Maven ≥ 3.6.3 and Java ≥ 21 |
| `maven-compiler-plugin` | 3.16.0 | build | Compiles for `release` 21 |
| `maven-surefire-plugin` | 3.6.1 | build | Runs the JUnit tests |
| `exec-maven-plugin` | 3.6.4 | build | Runs one example's `main` (`exec:java`) |

There are no runtime dependencies; the examples use only the JDK.

**Prerequisites**

- JDK 21 or newer. Checked with OpenJDK 27.0.1.
- Maven 3.6.3 or newer. Checked with Apache Maven 3.10.0. There is no Maven wrapper.

**Build, test and run**

```bash
mvn -q compile                         # build
mvn test                               # all tests
mvn test -Dtest=BurgerOrderBuilderTest # one pattern's tests
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.creational.builder.BurgerOrderBuilder
```

Sample output (Builder):

```
Classic: sesame bun, beef patty, lettuce, tomato, mayo, extra cheese ($6.75)
Light:   whole-wheat bun, veggie patty, mustard ($4.00)
```

## Folder Structure

```
design-pattern-102/
├── pom.xml                                   # Java 21, JUnit Jupiter, pinned plugins, enforcer
├── CLAUDE.md                                 # project rules, conventions, spec-driven workflow
├── README.md                                 # this file, including the Pattern Catalog index
├── .gitignore                                # secrets, IDE files, build output, caches
├── specs/feature/design-pattern-catalog/     # spec, plan, research, data model, contracts, quickstart, tasks
└── src/
    ├── main/java/learn/designpatterns/
    │   ├── creational/                       # README.md overview + 5 pattern packages (implemented)
    │   │   └── builder/                      # e.g. BurgerOrderBuilder.java, README.md, package-info.java
    │   ├── structural/                       # README.md overview + 7 pattern packages (implemented)
    │   └── behavioral/                       # README.md overview + 11 pattern packages (implemented)
    └── test/java/learn/designpatterns/
        ├── CatalogStructureTest.java         # completeness and status check for all 23 patterns
        └── <family>/<pattern>/<Main>Test.java# behavior tests for each pattern
```

Each pattern's README sits next to its code, so opening a pattern folder on GitHub shows the explanation.

## Tests

- **Framework:** JUnit Jupiter 6.1.3, run by Maven Surefire 3.6.1.
- **Location:** `src/test/java/learn/designpatterns/`, in the same package as the code under test.
- **Run:** `mvn test` (all) or `mvn test -Dtest=<Main>Test` (one pattern).
- **Last run:** 187 tests, 0 failures, 0 errors, 0 skipped; `BUILD SUCCESS` in about 2 s.

| Test | Count | Covers |
|------|-------|--------|
| `CatalogStructureTest` | 50 | 23 patterns with 5/7/11 per family and unique package names; all 3 family overviews exist; each pattern has its 3 files; README status matches whether the main class exists; implemented patterns have ≥ 2 tests and no `@Disabled` |
| `AppConfigSingletonTest` | 7 | same instance every time; 16 threads at once create exactly one instance; private constructor; read-only settings; unknown key error; non-numeric `getInt` gives a clear error |
| `LogisticsFactoryMethodTest` | 5 | road → Truck, sea → Ship; shared `planDelivery` uses the subclass's transport; blank cargo rejected |
| `UiThemeAbstractFactoryTest` | 5 | matching dark and light families; switching factories switches the family; missing factory rejected |
| `BurgerOrderBuilderTest` | 7 | step-by-step build, price, description; immutable result; missing bun or patty and unknown patty rejected |
| `DocumentTemplatePrototypeTest` | 6 | deep copy in both directions; registry hands out fresh copies; unknown template and blank title rejected |
| `TravelPlugAdapterTest` | 5 | EU plug fits directly; US plug works through the adapter; adapter translates the interface; flat pins rejected without an adapter; null plug rejected |
| `RemoteControlBridgeTest` | 6 | basic and advanced remotes on both TV and radio (power, volume, mute); volume kept within 0–100; null device rejected |
| `FileSystemCompositeTest` | 7 | file size; folder total at any depth; empty folder; files and folders handled alike; tree rendering; negative size and cycles rejected |
| `CoffeeDecoratorTest` | 5 | base price; add-ons stack description and price in order; the same add-on twice; a decorated drink is still a `Beverage`; null drink rejected |
| `HomeTheaterFacadeTest` | 5 | one call runs the 6 start-up steps in order; shutdown in reverse; blank title rejected before any device moves; end without a movie, or a double start, rejected |
| `ForestFlyweightTest` | 4 | 1,000 trees share 2 species objects; the factory returns the same object per species; each tree keeps its own position; blank species rejected |
| `ImageLoaderProxyTest` | 5 | creating a proxy doesn't read the disk; first display loads once; repeats use the cache; only displayed images load; blank file name rejected |
| `SupportEscalationChainTest` | 8 | each severity reaches the first capable level; the chain can be reordered; an unhandled ticket fails clearly; duplicate handlers rejected; re-chaining never loops; severity < 1 rejected |
| `TvRemoteCommandTest` | 5 | execute; undo restores the previous state; several undos in reverse order; undo with an empty history fails; a failed command isn't recorded |
| `ArithmeticInterpreterTest` | 7 | `"5 + 3 - 2"` = 6; a single number; left-to-right grouping; grammar classes combine directly; `int` overflow throws; unknown operator and malformed input rejected |
| `PlaylistIteratorTest` | 7 | in-order traversal; for-each; reverse traversal; independent positions; `next()` past the end fails; empty playlist; blank title rejected |
| `ChatRoomMediatorTest` | 6 | broadcast skips the sender; private messages; the room log; leaving stops delivery and sending; duplicate name and unknown target rejected |
| `TextEditorMementoTest` | 6 | undo restores exact text; several undos; undo a deletion; a snapshot is unaffected by later edits; undo with no history fails; invalid delete or restore rejected |
| `NewsChannelObserverTest` | 5 | all subscribers notified; unsubscribed ones skipped; subscription order, with lambdas; subscribing twice notifies once; blank headline and null subscriber rejected |
| `TrafficLightStateTest` | 5 | starts red; full red → green → yellow → red cycle; button on green → yellow; button on red or yellow rejected |
| `PaymentStrategyTest` | 8 | fee per strategy; receipts; a custom strategy plugs in; cash limited to $500; correct card fee on huge amounts; total overflow throws; no strategy chosen fails; negative amount rejected |
| `BeverageTemplateMethodTest` | 5 | tea and coffee steps; shared steps in the same positions; the hook skips extras; `prepare()` is `final` |
| `ShoppingCartVisitorTest` | 8 | tax rate per type; half-cent rounding; cart total; shipping as a second visitor; correct tax on huge prices; cart-total overflow throws; empty cart; negative price rejected |

## Vulnerabilities

No issues found in this scan. Earlier scans raised 5 Info-level notes, none exploitable. 4 have been fixed, each with a regression test that failed before the fix, and the fifth needs no change:

| Severity | Location | Finding | Status |
|----------|----------|---------|--------|
| Info | `src/main/java/learn/designpatterns/behavioral/interpreter/ArithmeticInterpreter.java`, `src/main/java/learn/designpatterns/behavioral/strategy/PaymentStrategy.java`, `src/main/java/learn/designpatterns/behavioral/visitor/ShoppingCartVisitor.java` | `int` arithmetic could silently overflow: `"2147483647 + 1"` gave a negative result, and fees or tax on huge amounts came out negative. | **Fixed.** `Math.addExact`/`subtractExact` throw on overflow; fee and tax are computed in `long`; cart and checkout totals use `addExact`. |
| Info | `src/main/java/learn/designpatterns/behavioral/chainofresponsibility/SupportEscalationChain.java` (`SupportHandler.chain`) | Listing a handler twice, or reusing handlers in a new chain, could create a loop and a `StackOverflowError`. | **Fixed.** `chain()` rejects duplicate instances with `IllegalArgumentException` and clears the last handler's old link. |
| Info | `src/main/java/learn/designpatterns/creational/singleton/AppConfigSingleton.java` (`getInt`) | A non-numeric setting threw `NumberFormatException` instead of the project's `IllegalArgumentException`. | **Fixed.** Rethrown as `IllegalArgumentException("Setting <key> is not a number: <value>")`. |
| Info | `pom.xml:11` | Leftover quickstart `<url>http://maven.apache.org</url>`. | **Fixed.** Now `https://github.com/priyodas12/design-pattern-102`. |
| Info | `.idea/jarRepositories.xml` | The JBoss repository is listed for the IDE only. | No change needed: the file is IDE-only, ignored by git and not used by Maven. |

What was checked:

- `grep -rnIEi` over all tracked files for credentials, API keys and tokens (AWS, GitHub, Slack, Stripe, Google, Anthropic and OpenAI formats, JWTs), private keys, connection strings, basic-auth URLs, emails, phone numbers and card numbers. Nothing found beyond false positives in documentation.
- A search for sensitive files (`.env*`, key stores, `credentials*`, `secrets*`, `*.tfvars`, SQL dumps, `.bak`). None found, now or anywhere in git history.
- Code patterns: process execution, SQL, deserialization, weak crypto or `Random`, TLS overrides, XML parsers, network calls, environment access, format strings. None found. `EuSocket` (Adapter) and `FileLeaf` (Composite) matched the patterns by name only, and "token" in the Interpreter means a parser token. All are in-memory examples. `String.format`/`printf` calls use constant format strings, and the Interpreter's split regex (`\s+`) is simple.
- The full git history (8 commits, including PRs #1–#3 and the merges from `main`, as pushed to GitHub) for secrets. Clean. The remote URL contains no credentials.
- **No dependency audit tool was available.** OWASP `dependency-check`, `osv-scanner` and `trivy` aren't installed. I reviewed the versions by hand instead: everything is pinned, and the only library, JUnit Jupiter, is test-scoped.

This scan is not a substitute for a full security audit.

## Future Scope

- **Close the open spec question** in [`spec.md`](specs/feature/design-pattern-catalog/spec.md) (whether US2–US4 belong in this feature). All four stories have now been built on this branch, so the marker can be resolved to "yes".
- **Clean up `pom.xml` metadata:** `groupId` is still `org.example`.
- **Improve test report names:** parameterized `CatalogStructureTest` cases show as `[1]`, `[2]`, ... in Surefire output, though each failure message names its pattern.
- **Run the Phase 7 polish tasks** (T061–T064 in [`tasks.md`](specs/feature/design-pattern-catalog/tasks.md)): a final README and links review, 3 identical full-suite runs, and the feature status in `CLAUDE.md`.
- **Add CI:** there is no `.github/workflows/` yet, so pull requests aren't checked automatically. A GitHub Actions job running `mvn -B test` on JDK 21 would enforce `CatalogStructureTest` and the behavior tests on every PR.
- **Set up dependency scanning** (`dependency-check-maven` or `osv-scanner`) before adding any runtime dependency, and enable GitHub secret scanning and Dependabot alerts for the repository.
- **Make `ImageLoaderProxy` thread-safe if it's reused:** its lazy loading can read the disk twice under concurrent first calls. Its README notes this as a con.
- **Shorten the examples over the ~120-line target:** `TvRemoteCommand` (146 lines), `ShoppingCartVisitor` (131) and `RemoteControlBridge` (126).
- **Add a Maven wrapper** (`mvn wrapper:wrapper`) so contributors don't need Maven installed. Note that `.gitignore` currently ignores `.mvn/wrapper/maven-wrapper.jar`.
