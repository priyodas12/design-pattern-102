# Abstract Factory

**Family:** Creational · **Status:** implemented

**Analogy:** a furniture store sells matching sets. Pick "dark oak" and the chair and table both come in dark oak. Here, a theme factory makes a matching Button and Checkbox, so a screen can never mix a dark button with a light checkbox.

Source: [`UiThemeAbstractFactory.java`](UiThemeAbstractFactory.java) · Test: [`UiThemeAbstractFactoryTest.java`](../../../../../../test/java/learn/designpatterns/creational/abstractfactory/UiThemeAbstractFactoryTest.java)

## Intent
Provide an interface for creating families of related objects without naming their concrete classes.

## When to use
- You have several product types (button, checkbox, ...) that come in variants (dark, light, ...).
- Mixing variants would be a bug.
- The client should switch the whole family by swapping one object.

## Roles
| Role | In this example |
|------|-----------------|
| Abstract factory | `ThemeFactory` |
| Concrete factories | `DarkThemeFactory`, `LightThemeFactory` |
| Abstract products | `Button`, `Checkbox` |
| Concrete products | `DarkButton`, `DarkCheckbox`, `LightButton`, `LightCheckbox` |
| Client | `SettingsScreen` (sees only the interfaces) |

## Diagram
```
                 ThemeFactory
           createButton() / createCheckbox()
              ▲                      ▲
   DarkThemeFactory           LightThemeFactory
     ├─> DarkButton             ├─> LightButton
     └─> DarkCheckbox           └─> LightCheckbox

   SettingsScreen(ThemeFactory) ──uses──> Button, Checkbox
```

## Pros
- Products from one factory are guaranteed to match.
- The client is decoupled from concrete classes; switching the theme is one line.
- Each family's creation code lives in one place.

## Cons
- Adding a new product type (for example, `Slider`) means changing the factory interface and every factory.
- It adds many small types for what may be a simple choice.

## Related patterns
- **Factory Method:** each `createX()` here is a factory method.
- **Singleton:** a concrete factory is often a single shared instance.
- **Prototype:** factories can be built from prototypes instead of subclasses.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.creational.abstractfactory.UiThemeAbstractFactory
mvn test -Dtest=UiThemeAbstractFactoryTest
```
