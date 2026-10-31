package learn.designpatterns.creational.abstractfactory;

import java.util.List;

/**
 * Abstract Factory pattern.
 *
 * <p>Analogy: a furniture store sells matching sets. Pick "dark oak" and the chair and the table both
 * come in dark oak. Here, a theme factory makes a matching Button and Checkbox, so a screen can never
 * mix a dark button with a light checkbox.
 */
public final class UiThemeAbstractFactory {

    private UiThemeAbstractFactory() {
    }

    /** Abstract product A. */
    interface Button {
        String theme();

        default String render(String label) {
            return "[" + theme() + " button: " + label + "]";
        }
    }

    /** Abstract product B. */
    interface Checkbox {
        String theme();

        default String render(String label) {
            return "[" + theme() + " checkbox: " + label + "]";
        }
    }

    /** Concrete products of the dark family. */
    record DarkButton() implements Button {
        public String theme() {
            return "dark";
        }
    }

    record DarkCheckbox() implements Checkbox {
        public String theme() {
            return "dark";
        }
    }

    /** Concrete products of the light family. */
    record LightButton() implements Button {
        public String theme() {
            return "light";
        }
    }

    record LightCheckbox() implements Checkbox {
        public String theme() {
            return "light";
        }
    }

    /** Abstract factory: one creation method per product in the family. */
    interface ThemeFactory {
        Button createButton();

        Checkbox createCheckbox();
    }

    /** Concrete factory: always makes dark products. */
    static final class DarkThemeFactory implements ThemeFactory {
        public Button createButton() {
            return new DarkButton();
        }

        public Checkbox createCheckbox() {
            return new DarkCheckbox();
        }
    }

    /** Concrete factory: always makes light products. */
    static final class LightThemeFactory implements ThemeFactory {
        public Button createButton() {
            return new LightButton();
        }

        public Checkbox createCheckbox() {
            return new LightCheckbox();
        }
    }

    /** Client: knows only the abstract factory and products, never the concrete classes. */
    static final class SettingsScreen {
        private final ThemeFactory factory;

        SettingsScreen(ThemeFactory factory) {
            if (factory == null) {
                throw new IllegalArgumentException("A theme factory is required");
            }
            this.factory = factory;
        }

        List<String> render() {
            return List.of(factory.createButton().render("Save"), factory.createCheckbox().render("Notifications"));
        }
    }

    public static void main(String[] args) {
        for (ThemeFactory theme : List.of(new DarkThemeFactory(), new LightThemeFactory())) {
            System.out.println(theme.getClass().getSimpleName() + " -> " + new SettingsScreen(theme).render());
        }
    }
}
