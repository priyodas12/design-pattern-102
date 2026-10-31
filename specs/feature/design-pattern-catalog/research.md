# Research: Design Pattern Catalog

These decisions are based on the current repository (Maven quickstart: `org.example.App`, JUnit 4.13.2, no Java version set) and the owner's earlier `design-pattern-101` project (parent repo, commit `743c51b`). That project already built 3 of these patterns, with conventions that worked. Everything here is a **proposal (confirm)** unless CLAUDE.md or the spec states it.

## R1: Java version

- **Decision:** target Java 21 with `maven.compiler.release=21`. The JDK installed locally is OpenJDK 27.0.1.
- **Rationale:** Java 21 is an LTS release and has records, sealed interfaces and pattern-matching `switch`, which keep the examples short (spec NFR: under about 120 lines). `design-pattern-101` used the same target. Today's `pom.xml` sets no version, so the build depends on the compiler plugin's default.
- **Alternatives considered:** Java 17 (older LTS; lacks pattern-matching `switch` and record patterns). Java 25 or 27 (newer, but would shut out learners on the most common LTS).

## R2: Test framework

- **Decision:** replace JUnit 4.13.2 with **JUnit Jupiter 6.1.3** (`org.junit.jupiter:junit-jupiter`, test scope). Placeholders use `@Disabled("Placeholder: not implemented yet")`.
- **Rationale:** the current `AppTest` uses the legacy JUnit 3 `TestCase` style. Jupiter has `@Disabled`, which gives FR-004's "skipped, not failed" with no extra tooling, plus `@DisplayName`, `@Nested` and `assertThrows`, which suit FR-010 and FR-012. Version 6.1.3 is already in the local Maven cache, and `design-pattern-101` used Jupiter 6.
- **Alternatives considered:** JUnit Jupiter 5.11.3 (also cached; equally fine, but 6.x is current). Keeping JUnit 4 (no `@Disabled` reporting in the modern Surefire provider; dated API). TestNG (unfamiliar here; no benefit).

## R3: Where the per-pattern README lives

- **Decision:** `README.md` sits **next to the code**, in each pattern's package folder: `src/main/java/learn/designpatterns/<family>/<pattern>/README.md`. Each family folder gets its own `README.md` too.
- **Rationale:** the request asks for "README.md for each". Placing the file in the pattern folder means GitHub renders it when someone opens that folder, so code and explanation are found together (spec SC-005). Maven compiles only `.java` from `src/main/java`, and the resources plugin copies only `src/main/resources`, so these files never reach the JAR.
- **Alternatives considered:** `docs/<family>/<pattern>.md`, which `design-pattern-101` used. That keeps source roots `.java`-only, but splits the explanation from the code and doesn't match "README.md for each". Adding `src/main/resources/...` is also rejected: it would package the docs into the JAR.

## R4: Base package and naming

- **Decision:** base package `learn.designpatterns`; package `learn.designpatterns.<family>.<pattern>`, where the pattern part is lowercase with words joined (for example, `chainofresponsibility`). Main class: `<Analogy><Pattern>` (for example, `CoffeeDecorator`). Test class: `<Main>Test` in the same package. The `org.example` quickstart (`App`, `AppTest`) is removed.
- **Rationale:** this reuses `design-pattern-101`'s convention. Package names can't contain `-`, so words are joined, which keeps names predictable (spec edge case). Putting the analogy in the class name makes the example easy to recognize.
- **Alternatives considered:** keeping `org.example` (meaningless placeholder). Using underscores in package names (legal, but against Java naming style).

## R5: How a placeholder is represented

- **Decision:** a placeholder pattern has `package-info.java` (Javadoc: pattern name, family, intent), a `README.md` stub (name, family, intent, status line), and `<Main>Test.java` with one `@Disabled` test. No main class yet. The test class name is fixed in advance from the analogy table in `plan.md`.
- **Rationale:** `package-info.java` is the idiomatic way to give a package Java-visible content and documentation, and it gives the package a source file before any implementation exists. A disabled test shows up in the test report as skipped, which makes progress visible.
- **Alternatives considered:** empty stub classes (dead code that must be deleted or rewritten later). A `.gitkeep` file (not Java; carries no information).

## R6: Catalog completeness check (FR-005, FR-015)

- **Decision:** add one JUnit test, `learn.designpatterns.CatalogStructureTest`. It holds the list of 23 `(family, package, testClass)` entries and, for each one, checks that `src/main/java/<pkg path>/README.md` and `src/test/java/<pkg path>/<TestClass>.java` exist, and that the README `Status:` line matches whether a main class exists.
- **Rationale:** Surefire runs tests with the project base directory as the working directory, so relative paths from `src/...` work. This is a simple, deterministic file-system check with no extra dependencies.
- **Alternatives considered:** the Maven enforcer `requireFilesExist` rule (no per-pattern messages and no status check). A shell script (not run by `mvn test`, so easy to forget).

## R7: Logging and output

- **Decision:** no logging dependency. Examples expose results through return values and state, which the tests check. The `main` method prints a short demo with `System.out.println`.
- **Rationale:** the request names no logging, and CLAUDE.md requires a reason for every dependency. Tests check behavior, not output, so a logger adds nothing.
- **Alternatives considered:** SLF4J and `slf4j-simple`, as in `design-pattern-101` (more realistic, but adds 2 dependencies and a config file for demo output).

## R8: Build plugins

- **Decision:** pin `maven-enforcer-plugin` 3.7.0 (Maven >= 3.6.3, JDK >= 21), `maven-compiler-plugin` 3.16.0, `maven-surefire-plugin` 3.6.1 and `exec-maven-plugin` 3.6.4. All four versions are cached locally and were used in `design-pattern-101`.
- **Rationale:** pinning makes builds the same on every machine. Exec provides FR-014's one-command run (`exec:java`). Enforcer gives a clear error on an old JDK.
- **Alternatives considered:** relying on the plugin versions bundled with Maven (they drift between Maven versions). Running examples with `java -cp target/classes ...` (works, but takes more typing).
