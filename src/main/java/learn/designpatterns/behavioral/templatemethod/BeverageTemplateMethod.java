package learn.designpatterns.behavioral.templatemethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Template Method pattern.
 *
 * <p>Analogy: making tea and making coffee follow the same recipe: boil water, brew, pour into a cup,
 * add extras. Only the brewing and the extras differ. The base class fixes the order of the steps once
 * (the "template method"); each drink fills in just the steps that are different.
 */
public final class BeverageTemplateMethod {

    private BeverageTemplateMethod() {
    }

    /** Abstract class: owns the algorithm's skeleton. */
    abstract static class Beverage {

        /** The template method: final, so no subclass can reorder or skip the fixed steps. */
        final List<String> prepare() {
            List<String> steps = new ArrayList<>();
            steps.add(boilWater());
            steps.add(brew());
            steps.add(pourInCup());
            if (wantsCondiments()) {
                steps.add(addCondiments());
            }
            return List.copyOf(steps);
        }

        /** Shared steps: the same for every drink. */
        private String boilWater() {
            return "Boil water";
        }

        private String pourInCup() {
            return "Pour into cup";
        }

        /** Primitive operations: each drink must supply these. */
        protected abstract String brew();

        protected abstract String addCondiments();

        /** Hook: optional; subclasses may override it to change the flow. */
        protected boolean wantsCondiments() {
            return true;
        }
    }

    /** Concrete classes: fill in only what differs. */
    static final class Tea extends Beverage {
        protected String brew() {
            return "Steep the tea for 3 minutes";
        }

        protected String addCondiments() {
            return "Add lemon";
        }
    }

    static final class Coffee extends Beverage {
        private final boolean withSugarAndMilk;

        Coffee(boolean withSugarAndMilk) {
            this.withSugarAndMilk = withSugarAndMilk;
        }

        protected String brew() {
            return "Drip coffee through a filter";
        }

        protected String addCondiments() {
            return "Add sugar and milk";
        }

        @Override
        protected boolean wantsCondiments() {
            return withSugarAndMilk;
        }
    }

    public static void main(String[] args) {
        System.out.println("Tea:          " + new Tea().prepare());
        System.out.println("Coffee:       " + new Coffee(true).prepare());
        System.out.println("Black coffee: " + new Coffee(false).prepare());
    }
}
