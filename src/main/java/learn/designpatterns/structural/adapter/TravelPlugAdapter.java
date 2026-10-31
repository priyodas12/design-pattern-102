package learn.designpatterns.structural.adapter;

/**
 * Adapter pattern.
 *
 * <p>Analogy: a US hair dryer has flat pins and won't fit a European wall socket, which needs round
 * pins. A travel plug adapter sits in between: the socket sees round pins, the dryer sees a US outlet,
 * and neither of them has to change.
 */
public final class TravelPlugAdapter {

    private TravelPlugAdapter() {
    }

    /** Target interface: what the EU socket (the client) expects. */
    interface EuPlug {
        String pinShape();

        String deviceName();
    }

    /** A device that already speaks the target interface. */
    record EuLaptopCharger() implements EuPlug {
        public String pinShape() {
            return "round";
        }

        public String deviceName() {
            return "laptop charger";
        }
    }

    /** Adaptee interface: an existing, incompatible interface we can't change. */
    interface UsPlug {
        String usDeviceName();
    }

    /** Adaptee: a US device with flat pins. */
    record UsHairDryer() implements UsPlug {
        public String usDeviceName() {
            return "hair dryer";
        }
    }

    /** Adapter: implements the target interface by translating calls to the adaptee. */
    static final class UsToEuPlugAdapter implements EuPlug {
        private final UsPlug usPlug;

        UsToEuPlugAdapter(UsPlug usPlug) {
            if (usPlug == null) {
                throw new IllegalArgumentException("Adapter needs a US plug to adapt");
            }
            this.usPlug = usPlug;
        }

        @Override
        public String pinShape() {
            return "round";
        }

        @Override
        public String deviceName() {
            return usPlug.usDeviceName();
        }
    }

    /** Client: works only with EuPlug and knows nothing about US plugs or adapters. */
    static final class EuSocket {
        String plugIn(EuPlug plug) {
            if (!"round".equals(plug.pinShape())) {
                throw new IllegalArgumentException(
                        "EU socket needs round pins, got " + plug.pinShape() + " (" + plug.deviceName() + ")");
            }
            return "EU socket powers " + plug.deviceName() + " (230V)";
        }
    }

    public static void main(String[] args) {
        EuSocket hotelWall = new EuSocket();
        System.out.println(hotelWall.plugIn(new EuLaptopCharger()));
        System.out.println(hotelWall.plugIn(new UsToEuPlugAdapter(new UsHairDryer())));
    }
}
