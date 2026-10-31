package learn.designpatterns;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Catalog completeness check (FR-001, FR-005, FR-006, FR-015).
 *
 * <p>Fails, naming the pattern, when any of the 23 patterns is missing its README,
 * {@code package-info.java} or test class, or when its README status line does not match the code.
 * Paths are relative to the Maven base directory, which Surefire uses as the working directory.
 */
class CatalogStructureTest {

    private static final Path MAIN = Path.of("src/main/java/learn/designpatterns");
    private static final Path TEST = Path.of("src/test/java/learn/designpatterns");
    private static final Pattern STATUS = Pattern.compile("\\*\\*Status:\\*\\*\\s*(placeholder|implemented)");

    /** One catalog entry: family folder, package folder and main class name (see plan.md pattern table). */
    record Entry(String family, String pkg, String mainClass) {
        String testClass() {
            return mainClass + "Test";
        }

        Path mainDir() {
            return MAIN.resolve(family).resolve(pkg);
        }

        Path testFile() {
            return TEST.resolve(family).resolve(pkg).resolve(testClass() + ".java");
        }

        @Override
        public String toString() {
            return family + "/" + pkg;
        }
    }

    static final List<Entry> CATALOG = List.of(
            new Entry("creational", "singleton", "AppConfigSingleton"),
            new Entry("creational", "factorymethod", "LogisticsFactoryMethod"),
            new Entry("creational", "abstractfactory", "UiThemeAbstractFactory"),
            new Entry("creational", "builder", "BurgerOrderBuilder"),
            new Entry("creational", "prototype", "DocumentTemplatePrototype"),
            new Entry("structural", "adapter", "TravelPlugAdapter"),
            new Entry("structural", "bridge", "RemoteControlBridge"),
            new Entry("structural", "composite", "FileSystemComposite"),
            new Entry("structural", "decorator", "CoffeeDecorator"),
            new Entry("structural", "facade", "HomeTheaterFacade"),
            new Entry("structural", "flyweight", "ForestFlyweight"),
            new Entry("structural", "proxy", "ImageLoaderProxy"),
            new Entry("behavioral", "chainofresponsibility", "SupportEscalationChain"),
            new Entry("behavioral", "command", "TvRemoteCommand"),
            new Entry("behavioral", "interpreter", "ArithmeticInterpreter"),
            new Entry("behavioral", "iterator", "PlaylistIterator"),
            new Entry("behavioral", "mediator", "ChatRoomMediator"),
            new Entry("behavioral", "memento", "TextEditorMemento"),
            new Entry("behavioral", "observer", "NewsChannelObserver"),
            new Entry("behavioral", "state", "TrafficLightState"),
            new Entry("behavioral", "strategy", "PaymentStrategy"),
            new Entry("behavioral", "templatemethod", "BeverageTemplateMethod"),
            new Entry("behavioral", "visitor", "ShoppingCartVisitor"));

    static Stream<Entry> catalog() {
        return CATALOG.stream();
    }

    @Test
    @DisplayName("catalog has 23 unique patterns: 5 creational, 7 structural, 11 behavioral")
    void catalogHasTheClassic23() {
        Map<String, Long> perFamily = CATALOG.stream()
                .collect(Collectors.groupingBy(Entry::family, Collectors.counting()));
        assertAll(
                () -> assertEquals(23, CATALOG.size(), "total patterns"),
                () -> assertEquals(Map.of("creational", 5L, "structural", 7L, "behavioral", 11L), perFamily),
                () -> assertEquals(23, new HashSet<>(CATALOG.stream().map(Entry::pkg).toList()).size(),
                        "package names must be unique"),
                () -> assertTrue(CATALOG.stream().allMatch(e -> e.pkg().matches("[a-z]+")),
                        "package names must be lowercase letters only"));
    }

    @ParameterizedTest(name = "{0} has an overview README")
    @ValueSource(strings = {"creational", "structural", "behavioral"})
    void familyHasOverview(String family) {
        Path readme = MAIN.resolve(family).resolve("README.md");
        assertTrue(Files.isRegularFile(readme), () -> "missing family overview: " + readme);
    }

    @ParameterizedTest(name = "{0} has README, package-info and test class")
    @MethodSource("catalog")
    void patternHasRequiredFiles(Entry e) {
        assertAll(e.toString(),
                () -> assertTrue(Files.isRegularFile(e.mainDir().resolve("README.md")),
                        () -> e + ": missing " + e.mainDir().resolve("README.md")),
                () -> assertTrue(Files.isRegularFile(e.mainDir().resolve("package-info.java")),
                        () -> e + ": missing " + e.mainDir().resolve("package-info.java")),
                () -> assertTrue(Files.isRegularFile(e.testFile()),
                        () -> e + ": missing " + e.testFile()));
    }

    @ParameterizedTest(name = "{0} README status matches the code")
    @MethodSource("catalog")
    void statusMatchesCode(Entry e) throws IOException {
        Path readme = e.mainDir().resolve("README.md");
        assertTrue(Files.isRegularFile(readme), () -> e + ": missing " + readme);

        Matcher m = STATUS.matcher(Files.readString(readme));
        assertTrue(m.find(), () -> e + ": README has no '**Status:** placeholder|implemented' line");

        boolean implemented = Files.isRegularFile(e.mainDir().resolve(e.mainClass() + ".java"));
        String expected = implemented ? "implemented" : "placeholder";
        assertEquals(expected, m.group(1),
                () -> e + ": README says '" + m.group(1) + "' but " + e.mainClass() + ".java "
                        + (implemented ? "exists" : "does not exist"));

        if (implemented && Files.isRegularFile(e.testFile())) {
            String tests = Files.readString(e.testFile());
            long testCount = Pattern.compile("@Test\\b").matcher(tests).results().count();
            assertAll(e.toString(),
                    () -> assertFalse(tests.contains("@Disabled"), () -> e + ": implemented but tests are @Disabled"),
                    () -> assertTrue(testCount >= 2, () -> e + ": implemented but has " + testCount + " @Test (need >= 2)"));
        }
    }
}
