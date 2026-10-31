# Tasks: Design Pattern Catalog

**Branch:** `feature/design-pattern-catalog` · Inputs: [spec.md](spec.md), [plan.md](plan.md), [data-model.md](data-model.md), [contracts/](contracts/), [quickstart.md](quickstart.md)

Format: `- [ ] T### [P] [US#] description`. `[P]` means the task can run in parallel with other `[P]` tasks in the same phase. Tick each task in the same commit as its code.

## Phase 1: Setup (4/4)

- [x] T001 Update `pom.xml`: set `maven.compiler.release` to 21; replace `junit:junit:4.13.2` with `org.junit.jupiter:junit-jupiter:6.1.3` (test scope); pin `maven-enforcer-plugin` 3.7.0 (Maven `[3.6.3,)`, Java `[21,)`), `maven-compiler-plugin` 3.16.0, `maven-surefire-plugin` 3.6.1 and `exec-maven-plugin` 3.6.4 (research R1, R2, R8)
- [x] T002 Delete the quickstart code: `src/main/java/org/example/App.java` and `src/test/java/org/example/AppTest.java` (research R4)
- [x] T003 Create the project rules file `CLAUDE.md` (written by `/gen-spec`)
- [x] T004 Check that `mvn -q compile` succeeds on JDK 27 and that the enforcer rules pass; record any command changes in `CLAUDE.md`

## Phase 2: Foundational (1/1)

- [x] T005 Write `src/test/java/learn/designpatterns/CatalogStructureTest.java`: the 23-entry `(family, package, testClass, mainClass)` table from plan.md; for each entry, check that `README.md`, `package-info.java` and the test file exist, and that the README `**Status:**` line matches whether `<mainClass>.java` exists (data-model V1 to V3; FR-001, FR-005, FR-015). This test should **fail** until Phase 3 is done.

## Phase 3: US1 (P1) Catalog skeleton (6/6)

- [x] T006 [P] [US1] Creational skeleton: for each of `singleton`, `factorymethod`, `abstractfactory`, `builder` and `prototype`, create `src/main/java/learn/designpatterns/creational/<pkg>/package-info.java`, `src/main/java/learn/designpatterns/creational/<pkg>/README.md` (placeholder template, contracts/pattern-readme.md) and `src/test/java/learn/designpatterns/creational/<pkg>/<Main>Test.java` with one `@Disabled("Placeholder: not implemented yet")` test
- [x] T007 [P] [US1] Structural skeleton: the same 3 files for each of `adapter`, `bridge`, `composite`, `decorator`, `facade`, `flyweight` and `proxy`, under `src/main/java/learn/designpatterns/structural/` and `src/test/java/learn/designpatterns/structural/`
- [x] T008 [P] [US1] Behavioral skeleton: the same 3 files for each of `chainofresponsibility`, `command`, `interpreter`, `iterator`, `mediator`, `memento`, `observer`, `state`, `strategy`, `templatemethod` and `visitor`, under `src/main/java/learn/designpatterns/behavioral/` and `src/test/java/learn/designpatterns/behavioral/`
- [x] T009 [P] [US1] Family overviews: `src/main/java/learn/designpatterns/creational/README.md`, `src/main/java/learn/designpatterns/structural/README.md` and `src/main/java/learn/designpatterns/behavioral/README.md`, each with a pattern, intent and status table (FR-006)
- [x] T010 [US1] Add a `## Pattern Catalog` index to the root `README.md`: 3 tables with 23 rows, linking to each pattern README, with status `placeholder` (FR-007)
- [x] T011 [US1] Run the quickstart.md US1 walkthrough: `mvn test` shows 0 failures and 23 skipped, `CatalogStructureTest` passes, and the negative check (a missing README fails the build and names the pattern) passes

**Checkpoint:** the US1 independent test from spec.md passes. US1 can be merged on its own.

## Phase 4: US2 (P2) Creational patterns (11/11)

Tests come first: each test task replaces the `@Disabled` placeholder with real behavior tests, which fail to compile or fail until the matching implementation task is done.

- [x] T012 [P] [US2] Singleton: write behavior and error-path tests in `src/test/java/learn/designpatterns/creational/singleton/AppConfigSingletonTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T013 [US2] Singleton: implement `src/main/java/learn/designpatterns/creational/singleton/AppConfigSingleton.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/creational/singleton/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/creational/singleton/package-info.java` status
- [x] T014 [P] [US2] Factory Method: write behavior and error-path tests in `src/test/java/learn/designpatterns/creational/factorymethod/LogisticsFactoryMethodTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T015 [US2] Factory Method: implement `src/main/java/learn/designpatterns/creational/factorymethod/LogisticsFactoryMethod.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/creational/factorymethod/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/creational/factorymethod/package-info.java` status
- [x] T016 [P] [US2] Abstract Factory: write behavior and error-path tests in `src/test/java/learn/designpatterns/creational/abstractfactory/UiThemeAbstractFactoryTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T017 [US2] Abstract Factory: implement `src/main/java/learn/designpatterns/creational/abstractfactory/UiThemeAbstractFactory.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/creational/abstractfactory/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/creational/abstractfactory/package-info.java` status
- [x] T018 [P] [US2] Builder: write behavior and error-path tests in `src/test/java/learn/designpatterns/creational/builder/BurgerOrderBuilderTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T019 [US2] Builder: implement `src/main/java/learn/designpatterns/creational/builder/BurgerOrderBuilder.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/creational/builder/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/creational/builder/package-info.java` status
- [x] T020 [P] [US2] Prototype: write behavior and error-path tests in `src/test/java/learn/designpatterns/creational/prototype/DocumentTemplatePrototypeTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T021 [US2] Prototype: implement `src/main/java/learn/designpatterns/creational/prototype/DocumentTemplatePrototype.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/creational/prototype/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/creational/prototype/package-info.java` status
- [x] T022 [US2] Set all creational rows to implemented in `src/main/java/learn/designpatterns/creational/README.md` and the root `README.md` index; run quickstart.md US2 walkthrough (`mvn test` shows 0 skipped for `learn.designpatterns.creational`, `CatalogStructureTest` passes)

**Checkpoint:** US2 independent test from spec.md passes: all 5 creational examples run, READMEs are complete, and their tests pass with 0 skipped.

## Phase 5: US3 (P3) Structural patterns (15/15)

Tests come first: each test task replaces the `@Disabled` placeholder with real behavior tests, which fail to compile or fail until the matching implementation task is done.

- [x] T023 [P] [US3] Adapter: write behavior and error-path tests in `src/test/java/learn/designpatterns/structural/adapter/TravelPlugAdapterTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T024 [US3] Adapter: implement `src/main/java/learn/designpatterns/structural/adapter/TravelPlugAdapter.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/structural/adapter/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/structural/adapter/package-info.java` status
- [x] T025 [P] [US3] Bridge: write behavior and error-path tests in `src/test/java/learn/designpatterns/structural/bridge/RemoteControlBridgeTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T026 [US3] Bridge: implement `src/main/java/learn/designpatterns/structural/bridge/RemoteControlBridge.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/structural/bridge/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/structural/bridge/package-info.java` status
- [x] T027 [P] [US3] Composite: write behavior and error-path tests in `src/test/java/learn/designpatterns/structural/composite/FileSystemCompositeTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T028 [US3] Composite: implement `src/main/java/learn/designpatterns/structural/composite/FileSystemComposite.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/structural/composite/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/structural/composite/package-info.java` status
- [x] T029 [P] [US3] Decorator: write behavior and error-path tests in `src/test/java/learn/designpatterns/structural/decorator/CoffeeDecoratorTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T030 [US3] Decorator: implement `src/main/java/learn/designpatterns/structural/decorator/CoffeeDecorator.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/structural/decorator/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/structural/decorator/package-info.java` status
- [x] T031 [P] [US3] Facade: write behavior and error-path tests in `src/test/java/learn/designpatterns/structural/facade/HomeTheaterFacadeTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T032 [US3] Facade: implement `src/main/java/learn/designpatterns/structural/facade/HomeTheaterFacade.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/structural/facade/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/structural/facade/package-info.java` status
- [x] T033 [P] [US3] Flyweight: write behavior and error-path tests in `src/test/java/learn/designpatterns/structural/flyweight/ForestFlyweightTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T034 [US3] Flyweight: implement `src/main/java/learn/designpatterns/structural/flyweight/ForestFlyweight.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/structural/flyweight/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/structural/flyweight/package-info.java` status
- [x] T035 [P] [US3] Proxy: write behavior and error-path tests in `src/test/java/learn/designpatterns/structural/proxy/ImageLoaderProxyTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T036 [US3] Proxy: implement `src/main/java/learn/designpatterns/structural/proxy/ImageLoaderProxy.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/structural/proxy/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/structural/proxy/package-info.java` status
- [x] T037 [US3] Set all structural rows to implemented in `src/main/java/learn/designpatterns/structural/README.md` and the root `README.md` index; run quickstart.md US3 walkthrough (`mvn test` shows 0 skipped for `learn.designpatterns.structural`, `CatalogStructureTest` passes)

**Checkpoint:** US3 independent test from spec.md passes: all 7 structural examples run, READMEs are complete, and their tests pass with 0 skipped.

## Phase 6: US4 (P4) Behavioral patterns (23/23)

Tests come first: each test task replaces the `@Disabled` placeholder with real behavior tests, which fail to compile or fail until the matching implementation task is done.

- [x] T038 [P] [US4] Chain of Responsibility: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/chainofresponsibility/SupportEscalationChainTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T039 [US4] Chain of Responsibility: implement `src/main/java/learn/designpatterns/behavioral/chainofresponsibility/SupportEscalationChain.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/chainofresponsibility/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/chainofresponsibility/package-info.java` status
- [x] T040 [P] [US4] Command: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/command/TvRemoteCommandTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T041 [US4] Command: implement `src/main/java/learn/designpatterns/behavioral/command/TvRemoteCommand.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/command/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/command/package-info.java` status
- [x] T042 [P] [US4] Interpreter: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/interpreter/ArithmeticInterpreterTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T043 [US4] Interpreter: implement `src/main/java/learn/designpatterns/behavioral/interpreter/ArithmeticInterpreter.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/interpreter/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/interpreter/package-info.java` status
- [x] T044 [P] [US4] Iterator: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/iterator/PlaylistIteratorTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T045 [US4] Iterator: implement `src/main/java/learn/designpatterns/behavioral/iterator/PlaylistIterator.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/iterator/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/iterator/package-info.java` status
- [x] T046 [P] [US4] Mediator: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/mediator/ChatRoomMediatorTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T047 [US4] Mediator: implement `src/main/java/learn/designpatterns/behavioral/mediator/ChatRoomMediator.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/mediator/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/mediator/package-info.java` status
- [x] T048 [P] [US4] Memento: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/memento/TextEditorMementoTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T049 [US4] Memento: implement `src/main/java/learn/designpatterns/behavioral/memento/TextEditorMemento.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/memento/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/memento/package-info.java` status
- [x] T050 [P] [US4] Observer: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/observer/NewsChannelObserverTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T051 [US4] Observer: implement `src/main/java/learn/designpatterns/behavioral/observer/NewsChannelObserver.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/observer/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/observer/package-info.java` status
- [x] T052 [P] [US4] State: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/state/TrafficLightStateTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T053 [US4] State: implement `src/main/java/learn/designpatterns/behavioral/state/TrafficLightState.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/state/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/state/package-info.java` status
- [x] T054 [P] [US4] Strategy: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/strategy/PaymentStrategyTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T055 [US4] Strategy: implement `src/main/java/learn/designpatterns/behavioral/strategy/PaymentStrategy.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/strategy/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/strategy/package-info.java` status
- [x] T056 [P] [US4] Template Method: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/templatemethod/BeverageTemplateMethodTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T057 [US4] Template Method: implement `src/main/java/learn/designpatterns/behavioral/templatemethod/BeverageTemplateMethod.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/templatemethod/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/templatemethod/package-info.java` status
- [x] T058 [P] [US4] Visitor: write behavior and error-path tests in `src/test/java/learn/designpatterns/behavioral/visitor/ShoppingCartVisitorTest.java`, removing `@Disabled` (defining behavior: see plan.md pattern table)
- [x] T059 [US4] Visitor: implement `src/main/java/learn/designpatterns/behavioral/visitor/ShoppingCartVisitor.java` per contracts/pattern-module.md; fill `src/main/java/learn/designpatterns/behavioral/visitor/README.md` with all 9 sections and set `Status: implemented`; update `src/main/java/learn/designpatterns/behavioral/visitor/package-info.java` status
- [x] T060 [US4] Set all behavioral rows to implemented in `src/main/java/learn/designpatterns/behavioral/README.md` and the root `README.md` index; run quickstart.md US4 walkthrough (`mvn test` shows 0 skipped for `learn.designpatterns.behavioral`, `CatalogStructureTest` passes)

**Checkpoint:** US4 independent test from spec.md passes: all 11 behavioral examples run, READMEs are complete, and their tests pass with 0 skipped.

## Phase 7: Polish (0/4)

- [ ] T061 Refresh the root `README.md` sections (Summary, Features, Dependencies, Folder Structure, Tests, Future Scope) to match the new code and the JUnit Jupiter dependency
- [ ] T062 [P] Review all 23 pattern READMEs and 3 family READMEs against contracts/pattern-readme.md: section order, working relative links, text diagrams, no `Foo` or `Bar`
- [ ] T063 Final verification: run `mvn -q test` 3 times; results must be identical, with 0 failures and 0 skipped, each run under 30 s (SC-003, SC-006)
- [ ] T064 Set the feature's status in the `## Features` table of `CLAUDE.md`

## Dependencies

- Setup (T001 to T004) → Foundational (T005) → US1 (T006 to T011) → US2, US3 and US4 → Polish.
- US2, US3 and US4 each depend only on US1, not on each other. They can run in any order, but priority order is recommended.
- Within a pattern, the implementation task depends on its test task (for example, T013 depends on T012).
- Each family's wrap-up task (T022, T037, T060) depends on all of that family's implementation tasks.

## Parallel opportunities

- T006, T007, T008 and T009 touch separate folders and can run in parallel.
- Within a story phase, all test tasks marked `[P]` can be written in parallel. Each implementation task can start as soon as its own test task is done.
- After US1, US2, US3 and US4 can proceed in parallel (on separate commits); only the root `README.md` index rows conflict, and those are edited only in the wrap-up tasks.
