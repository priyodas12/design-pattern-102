# Contract: README Documents (pattern, family, index)

**Serves:** FR-003, FR-006, FR-007, FR-009, FR-015

## Pattern README: placeholder

Path: `src/main/java/learn/designpatterns/<family>/<pattern>/README.md`

```markdown
# <Display Name>

**Family:** <Creational|Structural|Behavioral> · **Status:** placeholder

## Intent
<one sentence>

> Not implemented yet. Planned example: <analogy from plan.md>.
```

## Pattern README: implemented

The same file, with these headings **in this order** (FR-009). `CatalogStructureTest` reads the `**Status:**` text.

```markdown
# <Display Name>

**Family:** <...> · **Status:** implemented

**Analogy:** <one or two sentences>

Source: [`<Main>.java`](<Main>.java) · Test: [`<Main>Test.java`](../../../../../../test/java/learn/designpatterns/<family>/<pattern>/<Main>Test.java)

## Intent
## When to use
## Roles            <!-- table: Role | In this example -->
## Diagram          <!-- plain-text diagram in a code block -->
## Pros
## Cons
## Related patterns
## Run it           <!-- the exec:java and -Dtest commands -->
```

**Errors (review failures):** a heading is missing or out of order; the Status line does not match the code; the diagram is an image instead of text; the example uses `Foo` or `Bar` names.

## Family README

Path: `src/main/java/learn/designpatterns/<family>/README.md`

```markdown
# <Family> patterns

<one paragraph: what this family is about>

| Pattern | Intent | Status |
|---------|--------|--------|
| [<Name>](<pattern>/README.md) | <one line> | placeholder |
```

## Catalog index (root `README.md`)

Root README gets a `## Pattern Catalog` section with 3 tables, one per family: `Pattern | Family | Status`. Each pattern name links to `src/main/java/learn/designpatterns/<family>/<pattern>/README.md`. Its existing sections (Summary, Features, Dependencies, Folder Structure, Tests, Vulnerabilities, Future Scope) are updated to match the new code, not deleted.
