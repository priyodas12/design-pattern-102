package learn.designpatterns.creational.factorymethod;

import java.util.List;

/**
 * Factory Method pattern.
 *
 * <p>Analogy: a logistics company plans deliveries the same way every time (pick a vehicle, load the
 * cargo, send it). Only the vehicle differs: road logistics uses a truck, sea logistics uses a ship.
 * The shared planning code calls a "factory method" and lets each subclass decide which vehicle to create.
 */
public final class LogisticsFactoryMethod {

    private LogisticsFactoryMethod() {
    }

    /** Product: anything that can deliver cargo. */
    interface Transport {
        String deliver(String cargo);
    }

    /** Concrete product. */
    static final class Truck implements Transport {
        @Override
        public String deliver(String cargo) {
            return "Truck delivers " + cargo + " by road";
        }
    }

    /** Concrete product. */
    static final class Ship implements Transport {
        @Override
        public String deliver(String cargo) {
            return "Ship delivers " + cargo + " by sea";
        }
    }

    /** Creator: owns the shared business logic and declares the factory method. */
    abstract static class Logistics {

        /** The factory method: subclasses decide which Transport to create. */
        abstract Transport createTransport();

        /** Shared logic that works with any Transport, without knowing its concrete class. */
        public final String planDelivery(String cargo) {
            if (cargo == null || cargo.isBlank()) {
                throw new IllegalArgumentException("Cargo must not be blank");
            }
            Transport transport = createTransport();
            return transport.deliver(cargo);
        }
    }

    /** Concrete creator: road deliveries use trucks. */
    static final class RoadLogistics extends Logistics {
        @Override
        Transport createTransport() {
            return new Truck();
        }
    }

    /** Concrete creator: sea deliveries use ships. */
    static final class SeaLogistics extends Logistics {
        @Override
        Transport createTransport() {
            return new Ship();
        }
    }

    public static void main(String[] args) {
        List<Logistics> companies = List.of(new RoadLogistics(), new SeaLogistics());
        for (Logistics company : companies) {
            System.out.println(company.getClass().getSimpleName() + ": " + company.planDelivery("20 pallets of books"));
        }
    }
}
