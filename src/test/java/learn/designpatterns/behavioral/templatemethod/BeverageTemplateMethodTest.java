package learn.designpatterns.behavioral.templatemethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.List;
import learn.designpatterns.behavioral.templatemethod.BeverageTemplateMethod.Beverage;
import learn.designpatterns.behavioral.templatemethod.BeverageTemplateMethod.Coffee;
import learn.designpatterns.behavioral.templatemethod.BeverageTemplateMethod.Tea;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BeverageTemplateMethodTest {

    @Test
    @DisplayName("tea follows the shared recipe with its own brew and condiment steps")
    void teaSteps() {
        assertEquals(List.of("Boil water", "Steep the tea for 3 minutes", "Pour into cup", "Add lemon"),
                new Tea().prepare());
    }

    @Test
    @DisplayName("coffee follows the same recipe with different details")
    void coffeeSteps() {
        assertEquals(List.of("Boil water", "Drip coffee through a filter", "Pour into cup", "Add sugar and milk"),
                new Coffee(true).prepare());
    }

    @Test
    @DisplayName("the shared steps happen in the same positions for every drink")
    void sharedStepOrder() {
        List<String> tea = new Tea().prepare();
        List<String> coffee = new Coffee(true).prepare();
        assertEquals(tea.get(0), coffee.get(0));
        assertEquals(tea.get(2), coffee.get(2));
    }

    @Test
    @DisplayName("the hook lets a subclass skip the condiments step")
    void hookSkipsCondiments() {
        assertEquals(List.of("Boil water", "Drip coffee through a filter", "Pour into cup"),
                new Coffee(false).prepare());
    }

    @Test
    @DisplayName("the template method is final, so subclasses cannot reorder the steps")
    void templateMethodIsFinal() throws NoSuchMethodException {
        assertTrue(Modifier.isFinal(Beverage.class.getDeclaredMethod("prepare").getModifiers()));
    }
}
