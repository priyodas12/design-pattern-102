package learn.designpatterns.creational.abstractfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import learn.designpatterns.creational.abstractfactory.UiThemeAbstractFactory.DarkThemeFactory;
import learn.designpatterns.creational.abstractfactory.UiThemeAbstractFactory.LightThemeFactory;
import learn.designpatterns.creational.abstractfactory.UiThemeAbstractFactory.SettingsScreen;
import learn.designpatterns.creational.abstractfactory.UiThemeAbstractFactory.ThemeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UiThemeAbstractFactoryTest {

    @Test
    @DisplayName("dark factory makes a matching dark button and checkbox")
    void darkFactoryMakesDarkFamily() {
        ThemeFactory factory = new DarkThemeFactory();
        assertEquals("dark", factory.createButton().theme());
        assertEquals("dark", factory.createCheckbox().theme());
    }

    @Test
    @DisplayName("light factory makes a matching light button and checkbox")
    void lightFactoryMakesLightFamily() {
        ThemeFactory factory = new LightThemeFactory();
        assertEquals("light", factory.createButton().theme());
        assertEquals("light", factory.createCheckbox().theme());
    }

    @Test
    @DisplayName("the client renders only widgets from one family")
    void screenRendersMatchingWidgets() {
        assertEquals(List.of("[dark button: Save]", "[dark checkbox: Notifications]"),
                new SettingsScreen(new DarkThemeFactory()).render());
    }

    @Test
    @DisplayName("switching the whole theme means swapping one factory")
    void swappingFactorySwitchesFamily() {
        assertEquals(List.of("[light button: Save]", "[light checkbox: Notifications]"),
                new SettingsScreen(new LightThemeFactory()).render());
    }

    @Test
    @DisplayName("a screen without a factory is rejected")
    void nullFactoryRejected() {
        assertThrows(IllegalArgumentException.class, () -> new SettingsScreen(null));
    }
}
