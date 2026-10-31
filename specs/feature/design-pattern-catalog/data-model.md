# Data Model: Design Pattern Catalog

There is no persistent storage. The "data" is the catalog's structure: families, patterns and their files. It is defined once in `CatalogStructureTest` and in the top-level `README.md` index.

## Family

| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| name | enum | yes | `creational`, `structural` or `behavioral` |
| overview | file | yes | `src/main/java/learn/designpatterns/<name>/README.md` (FR-006) |
| patterns | list of Pattern | yes | exactly 5, 7 or 11 entries, matching the family (FR-001) |

## Pattern

| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| displayName | text | yes | the classic GoF name, for example "Chain of Responsibility" |
| family | Family | yes | exactly one (FR-001) |
| packageName | identifier | yes | lowercase, words joined, `[a-z]+`, for example `chainofresponsibility` (FR-002) |
| mainClass | identifier | yes | `<Analogy><Pattern>`, UpperCamelCase, fixed in `plan.md` |
| testClass | identifier | yes | `<mainClass>Test` |
| intent | text | yes | one sentence (FR-003) |
| analogy | text | yes once implemented | an everyday scenario; no "Foo" or "Bar" (FR-008) |
| status | enum | yes | `placeholder` or `implemented` (FR-007, FR-015) |
| readme | file | yes | `<pkg dir>/README.md`; its sections depend on status (see [contracts/pattern-readme.md](contracts/pattern-readme.md)) |
| packageInfo | file | yes | `<pkg dir>/package-info.java` |
| tests | file | yes | `src/test/java/<pkg path>/<testClass>.java` (FR-004) |

### Validation rules

- V1 (FR-001): there are 23 patterns in total, and their `packageName` values are unique.
- V2 (FR-005): `readme`, `packageInfo` and `tests` exist for every pattern. Otherwise `CatalogStructureTest` fails and names the pattern.
- V3 (FR-015): `status = implemented` if and only if `mainClass.java` exists, and the README `**Status:**` line equals `status`.
- V4 (FR-010): when `status = implemented`, the test class has at least 2 `@Test` methods and no `@Disabled`.

### State transitions

```
placeholder ──(add main class + real tests + full README, flip Status line)──> implemented
```

There is no transition backwards. If a pattern needs rework, it stays `implemented` while its tests are fixed.

## Catalog index

| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| file | file | yes | the root `README.md` (FR-007) |
| rows | 23 entries | yes | one row per pattern: family, displayName, relative link to the pattern README, status |
