# design-pattern-102

A Java learning catalog of the 23 classic (GoF) design patterns, grouped into three families: creational (5), structural (7) and behavioral (11). Each pattern has its own package containing a `README.md` explanation, a standalone example built on an everyday analogy, and JUnit tests. Patterns not yet implemented are placeholders: a README stub and a skipped test. Stack: Java 21, Maven, JUnit Jupiter 6.

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
- **A README for each implemented pattern** with these sections: intent, analogy, when to use, roles, a text diagram, pros, cons, related patterns, and the run commands.
- **A catalog completeness check:** `CatalogStructureTest` fails the build if any pattern is missing its README, `package-info.java` or test, or if its README's `**Status:**` line doesn't match the code.
- **Clear errors:** examples throw `IllegalArgumentException` for bad input and `IllegalStateException` for an invalid state, each with a descriptive message.
- **Placeholders for the 11 behavioral patterns:** each has a README with its intent and the planned example, and a `@Disabled` test.

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
| [Chain of Responsibility](src/main/java/learn/designpatterns/behavioral/chainofresponsibility/README.md) | Behavioral | placeholder |
| [Command](src/main/java/learn/designpatterns/behavioral/command/README.md) | Behavioral | placeholder |
| [Interpreter](src/main/java/learn/designpatterns/behavioral/interpreter/README.md) | Behavioral | placeholder |
| [Iterator](src/main/java/learn/designpatterns/behavioral/iterator/README.md) | Behavioral | placeholder |
| [Mediator](src/main/java/learn/designpatterns/behavioral/mediator/README.md) | Behavioral | placeholder |
| [Memento](src/main/java/learn/designpatterns/behavioral/memento/README.md) | Behavioral | placeholder |
| [Observer](src/main/java/learn/designpatterns/behavioral/observer/README.md) | Behavioral | placeholder |
| [State](src/main/java/learn/designpatterns/behavioral/state/README.md) | Behavioral | placeholder |
| [Strategy](src/main/java/learn/designpatterns/behavioral/strategy/README.md) | Behavioral | placeholder |
| [Template Method](src/main/java/learn/designpatterns/behavioral/templatemethod/README.md) | Behavioral | placeholder |
| [Visitor](src/main/java/learn/designpatterns/behavioral/visitor/README.md) | Behavioral | placeholder |

## Dependencies

| Name | Version | Scope | Purpose |
|------|---------|-------|---------|
| `org.junit.jupiter:junit-jupiter` | 6.1.3 | test | Unit tests, parameterized tests, `@Disabled` placeholders |
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
    │   └── behavioral/                       # README.md overview + 11 pattern packages (placeholders)
    └── test/java/learn/designpatterns/
        ├── CatalogStructureTest.java         # completeness and status check for all 23 patterns
        └── <family>/<pattern>/<Main>Test.java# behavior tests (or a @Disabled placeholder)
```

Each pattern's README sits next to its code, so opening a pattern folder on GitHub shows the explanation.

## Tests

- **Framework:** JUnit Jupiter 6.1.3, run by Maven Surefire 3.6.1.
- **Location:** `src/test/java/learn/designpatterns/`, in the same package as the code under test.
- **Run:** `mvn test` (all) or `mvn test -Dtest=<Main>Test` (one pattern).
- **Last run:** 127 tests, 0 failures, 0 errors, 11 skipped; `BUILD SUCCESS` in about 1.8 s.

| Test | Count | Covers |
|------|-------|--------|
| `CatalogStructureTest` | 50 | 23 patterns with 5/7/11 per family and unique package names; all 3 family overviews exist; each pattern has its 3 files; README status matches whether the main class exists; implemented patterns have ≥ 2 tests and no `@Disabled` |
| `AppConfigSingletonTest` | 6 | same instance every time; 16 threads at once create exactly one instance; private constructor; read-only settings; unknown key error |
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
| 11 behavioral placeholders | 11 | `@Disabled`, reported as skipped until each pattern is implemented |

## Vulnerabilities

No security issues found in this scan. The table lists the only notes, all Info level:

| Severity | Location | Description | Suggested fix |
|----------|----------|-------------|---------------|
| Info | `src/main/java/learn/designpatterns/creational/singleton/AppConfigSingleton.java:60` | `getInt()` lets `NumberFormatException` escape for non-numeric settings, instead of the project's usual `IllegalArgumentException` with a clear message. This is not a security risk, because all values are hardcoded. | Catch the exception, rethrow it as `IllegalArgumentException`, and add a test. |
| Info | `pom.xml:11` | Leftover quickstart `<url>http://maven.apache.org</url>`. It's metadata only and never fetched. | Remove it, or point it at `https://github.com/priyodas12/design-pattern-102`. |

What was checked:

- `grep -rnIEi` over all tracked files for credentials, API keys and tokens (AWS, GitHub, Slack, Stripe, Google, Anthropic and OpenAI formats, JWTs), private keys, connection strings, basic-auth URLs, emails, phone numbers and card numbers. Nothing found beyond false positives in documentation.
- A search for sensitive files (`.env*`, key stores, `credentials*`, `secrets*`, `*.tfvars`, SQL dumps, `.bak`). None found, now or anywhere in git history.
- Code patterns: process execution, SQL, deserialization, weak crypto or `Random`, TLS overrides, XML parsers, network calls, environment access. None found. `EuSocket` (Adapter) and `FileLeaf` (Composite) matched the patterns by name only; both are in-memory examples with no network or file access.
- The full git history (5 commits, including PR #1 and the merge from `main`, as pushed to GitHub) for secrets. Clean. The remote URL contains no credentials.
- **No dependency audit tool was available.** OWASP `dependency-check`, `osv-scanner` and `trivy` aren't installed. I reviewed the versions by hand instead: everything is pinned, and the only library, JUnit Jupiter, is test-scoped.

This scan is not a substitute for a full security audit.

## Future Scope

- **Implement the 11 behavioral patterns** (Phase 6 / US4 in [`tasks.md`](specs/feature/design-pattern-catalog/tasks.md)): Chain of Responsibility, Command, Interpreter, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor.
- **Resolve the open spec question** in [`spec.md`](specs/feature/design-pattern-catalog/spec.md): should US2–US4 ship in this feature, or in follow-up features?
- **Fix `AppConfigSingleton.getInt`** to throw `IllegalArgumentException` for non-numeric values, and add a test for it.
- **Clean up `pom.xml` metadata:** `groupId` is still `org.example`, and the old `<url>` remains.
- **Improve test report names:** parameterized `CatalogStructureTest` cases show as `[1]`, `[2]`, ... in Surefire output, though each failure message names its pattern.
- **Run the Phase 7 polish tasks** (T061–T064): a final README and links review, 3 identical full-suite runs, and the feature status in `CLAUDE.md`.
- **Add CI:** there is no `.github/workflows/` yet, so pull requests aren't checked automatically. A GitHub Actions job running `mvn -B test` on JDK 21 would enforce `CatalogStructureTest` and the behavior tests on every PR.
- **Set up dependency scanning** (`dependency-check-maven` or `osv-scanner`) before adding any runtime dependency, and enable GitHub secret scanning and Dependabot alerts for the repository.
- **Make `ImageLoaderProxy` thread-safe if it's reused:** its lazy loading can read the disk twice under concurrent first calls. Its README notes this as a con.
- **Shorten `RemoteControlBridge`:** it is 126 lines, slightly over the ~120-line target for examples.
- **Add a Maven wrapper** (`mvn wrapper:wrapper`) so contributors don't need Maven installed. Note that `.gitignore` currently ignores `.mvn/wrapper/maven-wrapper.jar`.
