package learn.designpatterns.behavioral.state;

/**
 * State pattern.
 *
 * <p>Analogy: a traffic light. It behaves differently depending on its current colour: whether cars may
 * go, which colour comes next, and what the pedestrian button does. Each colour is its own small
 * class, and the light simply swaps one state object for another, instead of a big if/else on the colour.
 */
public final class TrafficLightState {

    private TrafficLightState() {
    }

    /** State: behaviour that changes with the light's colour. */
    sealed interface LightState permits Red, Green, Yellow {
        String color();

        boolean carsMayGo();

        LightState next();

        /** Returns the state to switch to, or throws if the button makes no sense now. */
        LightState onPedestrianButton();
    }

    /** Concrete states. */
    record Red() implements LightState {
        public String color() {
            return "RED";
        }

        public boolean carsMayGo() {
            return false;
        }

        public LightState next() {
            return new Green();
        }

        public LightState onPedestrianButton() {
            throw new IllegalStateException("Pedestrians can already cross on red");
        }
    }

    record Green() implements LightState {
        public String color() {
            return "GREEN";
        }

        public boolean carsMayGo() {
            return true;
        }

        public LightState next() {
            return new Yellow();
        }

        /** Shortens the green phase so pedestrians get to cross sooner. */
        public LightState onPedestrianButton() {
            return new Yellow();
        }
    }

    record Yellow() implements LightState {
        public String color() {
            return "YELLOW";
        }

        public boolean carsMayGo() {
            return false;
        }

        public LightState next() {
            return new Red();
        }

        public LightState onPedestrianButton() {
            throw new IllegalStateException("Light is already changing");
        }
    }

    /** Context: delegates to its current state and lets the state choose the next one. */
    static final class TrafficLight {
        private LightState state = new Red();

        void change() {
            state = state.next();
        }

        void pressPedestrianButton() {
            state = state.onPedestrianButton();
        }

        LightState state() {
            return state;
        }

        String color() {
            return state.color();
        }

        boolean carsMayGo() {
            return state.carsMayGo();
        }
    }

    public static void main(String[] args) {
        TrafficLight light = new TrafficLight();
        for (int i = 0; i < 4; i++) {
            System.out.println(light.color() + " - cars " + (light.carsMayGo() ? "go" : "stop"));
            light.change();
        }
        light.pressPedestrianButton();
        System.out.println("Pedestrian button on GREEN -> " + light.color());
        try {
            light.pressPedestrianButton();
        } catch (IllegalStateException e) {
            System.out.println("Pedestrian button on YELLOW -> rejected: " + e.getMessage());
        }
    }
}
