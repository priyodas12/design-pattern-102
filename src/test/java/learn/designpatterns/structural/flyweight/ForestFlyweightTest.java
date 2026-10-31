package learn.designpatterns.structural.flyweight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.IdentityHashMap;
import java.util.Map;
import learn.designpatterns.structural.flyweight.ForestFlyweight.Forest;
import learn.designpatterns.structural.flyweight.ForestFlyweight.Tree;
import learn.designpatterns.structural.flyweight.ForestFlyweight.TreeTypeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ForestFlyweightTest {

    @Test
    @DisplayName("1,000 trees of 2 species share exactly 2 species objects")
    void manyTreesShareFewTypes() {
        TreeTypeFactory factory = new TreeTypeFactory();
        Forest forest = new Forest(factory);
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                forest.plant(i, i * 2, "Oak", "green", "rough bark");
            } else {
                forest.plant(i, i * 2, "Birch", "white", "smooth bark");
            }
        }

        Map<Object, Boolean> distinctTypes = new IdentityHashMap<>();
        for (Tree tree : forest.trees()) {
            distinctTypes.put(tree.type(), true);
        }
        assertEquals(1000, forest.trees().size());
        assertEquals(2, distinctTypes.size());
        assertEquals(2, factory.typesCreated());
    }

    @Test
    @DisplayName("the factory returns the same object for the same species")
    void sameSpeciesSameObject() {
        TreeTypeFactory factory = new TreeTypeFactory();
        assertSame(factory.get("Oak", "green", "rough bark"), factory.get("Oak", "green", "rough bark"));
        assertNotSame(factory.get("Oak", "green", "rough bark"), factory.get("Pine", "dark green", "needles"));
    }

    @Test
    @DisplayName("each tree keeps its own position while sharing species data")
    void extrinsicStateStaysPerTree() {
        Forest forest = new Forest(new TreeTypeFactory());
        forest.plant(1, 2, "Oak", "green", "rough bark");
        forest.plant(30, 40, "Oak", "green", "rough bark");

        Tree first = forest.trees().get(0);
        Tree second = forest.trees().get(1);
        assertEquals(1, first.x());
        assertEquals(40, second.y());
        assertSame(first.type(), second.type());
        assertEquals("Oak (green, rough bark) at (30, 40)", second.draw());
    }

    @Test
    @DisplayName("a species needs a name")
    void blankNameRejected() {
        assertThrows(IllegalArgumentException.class, () -> new TreeTypeFactory().get(" ", "green", "rough bark"));
    }
}
