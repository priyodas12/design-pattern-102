package learn.designpatterns.behavioral.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import learn.designpatterns.behavioral.state.TrafficLightState.Green;
import learn.designpatterns.behavioral.state.TrafficLightState.Red;
import learn.designpatterns.behavioral.state.TrafficLightState.TrafficLight;
import learn.designpatterns.behavioral.state.TrafficLightState.Yellow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TrafficLightStateTest {

    private TrafficLight light;

    @BeforeEach
    void setUp() {
        light = new TrafficLight();
    }

    @Test
    @DisplayName("a new light starts red, and cars must stop")
    void startsRed() {
        assertInstanceOf(Red.class, light.state());
        assertEquals("RED", light.color());
        assertFalse(light.carsMayGo());
    }

    @Test
    @DisplayName("change() cycles red, green, yellow, red")
    void fullCycle() {
        light.change();
        assertInstanceOf(Green.class, light.state());
        assertTrue(light.carsMayGo());
        light.change();
        assertInstanceOf(Yellow.class, light.state());
        assertFalse(light.carsMayGo());
        light.change();
        assertInstanceOf(Red.class, light.state());
    }

    @Test
    @DisplayName("the pedestrian button on green switches to yellow early")
    void pedestrianButtonOnGreen() {
        light.change();
        light.pressPedestrianButton();
        assertEquals("YELLOW", light.color());
    }

    @Test
    @DisplayName("the pedestrian button on red is an invalid action")
    void pedestrianButtonOnRedRejected() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, light::pressPedestrianButton);
        assertEquals("Pedestrians can already cross on red", ex.getMessage());
        assertEquals("RED", light.color());
    }

    @Test
    @DisplayName("the pedestrian button on yellow is an invalid action")
    void pedestrianButtonOnYellowRejected() {
        light.change();
        light.change();
        IllegalStateException ex = assertThrows(IllegalStateException.class, light::pressPedestrianButton);
        assertEquals("Light is already changing", ex.getMessage());
    }
}
