package learn.designpatterns.structural.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import learn.designpatterns.structural.adapter.TravelPlugAdapter.EuLaptopCharger;
import learn.designpatterns.structural.adapter.TravelPlugAdapter.EuPlug;
import learn.designpatterns.structural.adapter.TravelPlugAdapter.EuSocket;
import learn.designpatterns.structural.adapter.TravelPlugAdapter.UsHairDryer;
import learn.designpatterns.structural.adapter.TravelPlugAdapter.UsToEuPlugAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TravelPlugAdapterTest {

    private final EuSocket socket = new EuSocket();

    @Test
    @DisplayName("an EU plug fits the EU socket directly")
    void euPlugWorksDirectly() {
        assertEquals("EU socket powers laptop charger (230V)", socket.plugIn(new EuLaptopCharger()));
    }

    @Test
    @DisplayName("a US plug works in the EU socket through the adapter")
    void usPlugWorksThroughAdapter() {
        EuPlug adapted = new UsToEuPlugAdapter(new UsHairDryer());
        assertEquals("EU socket powers hair dryer (230V)", socket.plugIn(adapted));
    }

    @Test
    @DisplayName("the adapter presents round pins and passes the device through")
    void adapterTranslatesInterface() {
        UsToEuPlugAdapter adapter = new UsToEuPlugAdapter(new UsHairDryer());
        assertEquals("round", adapter.pinShape());
        assertEquals("hair dryer", adapter.deviceName());
    }

    @Test
    @DisplayName("without an adapter, flat pins are rejected by the EU socket")
    void flatPinsRejected() {
        EuPlug flatPinPlug = new EuPlug() {
            public String pinShape() {
                return "flat";
            }

            public String deviceName() {
                return "toaster";
            }
        };
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> socket.plugIn(flatPinPlug));
        assertEquals("EU socket needs round pins, got flat (toaster)", ex.getMessage());
    }

    @Test
    @DisplayName("an adapter needs a plug to adapt")
    void nullPlugRejected() {
        assertThrows(IllegalArgumentException.class, () -> new UsToEuPlugAdapter(null));
    }
}
