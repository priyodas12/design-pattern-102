package learn.designpatterns.structural.flyweight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Flyweight pattern.
 *
 * <p>Analogy: drawing a forest of thousands of trees. Every oak has the same name, colour and bark
 * texture; only its position differs. So each tree stores just its x and y plus a pointer to one shared
 * "Oak" object, instead of its own copy of the species data. Thousands of trees, a handful of species.
 */
public final class ForestFlyweight {

    private ForestFlyweight() {
    }

    /** Flyweight: intrinsic (shared, immutable) state. */
    record TreeType(String name, String color, String texture) {
    }

    /** Context: extrinsic (per-tree) state plus a reference to the shared flyweight. */
    record Tree(int x, int y, TreeType type) {
        String draw() {
            return type.name() + " (" + type.color() + ", " + type.texture() + ") at (" + x + ", " + y + ")";
        }
    }

    /** Flyweight factory: returns an existing TreeType when one matches, creating it only once. */
    static final class TreeTypeFactory {
        private final Map<String, TreeType> cache = new HashMap<>();

        TreeType get(String name, String color, String texture) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Tree species needs a name");
            }
            String key = name + "|" + color + "|" + texture;
            return cache.computeIfAbsent(key, k -> new TreeType(name, color, texture));
        }

        int typesCreated() {
            return cache.size();
        }
    }

    /** Client: plants trees, asking the factory for the shared species object each time. */
    static final class Forest {
        private final TreeTypeFactory factory;
        private final List<Tree> trees = new ArrayList<>();

        Forest(TreeTypeFactory factory) {
            this.factory = factory;
        }

        void plant(int x, int y, String name, String color, String texture) {
            trees.add(new Tree(x, y, factory.get(name, color, texture)));
        }

        List<Tree> trees() {
            return Collections.unmodifiableList(trees);
        }
    }

    public static void main(String[] args) {
        TreeTypeFactory factory = new TreeTypeFactory();
        Forest forest = new Forest(factory);
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                forest.plant(i % 50, i / 50, "Oak", "green", "rough bark");
            } else {
                forest.plant(i % 50, i / 50, "Birch", "white", "smooth bark");
            }
        }

        Map<TreeType, Boolean> shared = new IdentityHashMap<>();
        forest.trees().forEach(t -> shared.put(t.type(), true));
        System.out.println("Trees planted:        " + forest.trees().size());
        System.out.println("Species objects made: " + factory.typesCreated());
        System.out.println("Distinct objects used:" + " " + shared.size());
        System.out.println("First tree:           " + forest.trees().get(0).draw());
    }
}
