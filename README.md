# design-pattern-102

A Maven project for working through design patterns in Java, the follow-on to `design-pattern-101`. For now it is still the stock Maven quickstart: one `App` class that prints "Hello World!" and one placeholder JUnit test. No pattern implementations exist yet. Stack: Java, Maven, JUnit 4.

## Features

- `org.example.App`: a console entry point that prints `Hello World!`.
- A Maven build (`jar` packaging, UTF-8 source encoding) with a JUnit 4 test that runs.

No design patterns are implemented yet.

## Dependencies

| Name | Version | Scope | Purpose |
|------|---------|-------|---------|
| `junit:junit` | 4.13.2 | test | Unit testing (JUnit 3-style `TestCase` API) |

There are no runtime dependencies.

**Prerequisites**

- A JDK. The `pom.xml` does not set a Java version (`maven.compiler.release`), so the compiler plugin's default is used. The build was checked with OpenJDK 27.0.1.
- Maven. Checked with Apache Maven 3.10.0. There is no Maven wrapper (`mvnw`) in the repo.

**Build and run**

```bash
mvn package
java -cp target/design-pattern-102-1.0-SNAPSHOT.jar org.example.App
```

## Folder Structure

```
design-pattern-102/
├── pom.xml                                  # Maven build: coordinates, JUnit dependency
├── .gitignore                               # Ignores secrets, IDE files, build output, caches
└── src/
    ├── main/java/org/example/
    │   └── App.java                         # Entry point; prints "Hello World!"
    └── test/java/org/example/
        └── AppTest.java                     # Placeholder JUnit test
```

## Tests

- **Framework:** JUnit 4.13.2, run by Maven Surefire.
- **Location:** `src/test/java/org/example/AppTest.java`
- **Run:** `mvn test`
- **Coverage:** none in practice. The only test, `testApp`, is `assertTrue(true)` and checks no behaviour. The last run passed: 1 test, 0 failures, 0 errors.

## Vulnerabilities

No issues found in this scan.

| Severity | Location | Description | Suggested fix |
|----------|----------|-------------|---------------|
| — | — | No hardcoded secrets, `eval`/exec calls, SQL, network or TLS code, or committed `.env` files were found. | — |

What was checked:

- `grep -rIEi 'password\s*[=:]|secret|api[_-]?key|token|BEGIN .*PRIVATE KEY'` over the project (no matches)
- A manual review of `pom.xml`. The only dependency is `junit:junit:4.13.2`, which is version-pinned, test-scoped, and newer than the fix for CVE-2020-15250 (fixed in 4.13.1).
- No Maven dependency audit tool (for example OWASP `dependency-check-maven`) is set up or was run.

This scan is not a substitute for a full security audit.

## Future Scope

- **Add design pattern implementations.** This is the project's stated purpose, and `App.java` is still the quickstart stub.
- **Write real tests** for each pattern to replace the `assertTrue(true)` placeholder in `AppTest`.
- **Move to JUnit 5 (Jupiter).** The current test uses the legacy JUnit 3 `TestCase`/`TestSuite` style.
- **Set the Java version** with `<maven.compiler.release>` in `pom.xml`, and pin plugin versions (compiler, surefire), so builds are the same on every machine.
- **Rename the package** from `org.example` to a project-specific one.
- **Set up dependency scanning,** for example OWASP `dependency-check-maven`, once more dependencies are added.
- **Add a Maven wrapper** (`mvn wrapper:wrapper`) so contributors don't need Maven installed.
