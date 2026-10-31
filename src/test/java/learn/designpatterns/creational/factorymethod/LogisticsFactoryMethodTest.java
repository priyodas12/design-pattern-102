package learn.designpatterns.creational.factorymethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import learn.designpatterns.creational.factorymethod.LogisticsFactoryMethod.Logistics;
import learn.designpatterns.creational.factorymethod.LogisticsFactoryMethod.RoadLogistics;
import learn.designpatterns.creational.factorymethod.LogisticsFactoryMethod.SeaLogistics;
import learn.designpatterns.creational.factorymethod.LogisticsFactoryMethod.Ship;
import learn.designpatterns.creational.factorymethod.LogisticsFactoryMethod.Truck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LogisticsFactoryMethodTest {

    @Test
    @DisplayName("road logistics creates a Truck")
    void roadCreatesTruck() {
        assertInstanceOf(Truck.class, new RoadLogistics().createTransport());
    }

    @Test
    @DisplayName("sea logistics creates a Ship")
    void seaCreatesShip() {
        assertInstanceOf(Ship.class, new SeaLogistics().createTransport());
    }

    @Test
    @DisplayName("planDelivery uses whatever transport the subclass creates")
    void planDeliveryUsesFactoryMethod() {
        assertEquals("Truck delivers 10 boxes by road", new RoadLogistics().planDelivery("10 boxes"));
        assertEquals("Ship delivers 2 containers by sea", new SeaLogistics().planDelivery("2 containers"));
    }

    @Test
    @DisplayName("client code works only with the Logistics type")
    void clientNeverNamesConcreteTransport() {
        List<Logistics> companies = List.of(new RoadLogistics(), new SeaLogistics());
        List<String> plans = companies.stream().map(c -> c.planDelivery("1 crate")).toList();
        assertEquals(List.of("Truck delivers 1 crate by road", "Ship delivers 1 crate by sea"), plans);
    }

    @Test
    @DisplayName("blank cargo is rejected")
    void blankCargoRejected() {
        assertThrows(IllegalArgumentException.class, () -> new RoadLogistics().planDelivery("  "));
    }
}
