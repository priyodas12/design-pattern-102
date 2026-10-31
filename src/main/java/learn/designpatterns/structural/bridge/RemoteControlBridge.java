package learn.designpatterns.structural.bridge;

/**
 * Bridge pattern.
 *
 * <p>Analogy: remote controls and the devices they control. There are basic and advanced remotes,
 * and there are TVs and radios. Instead of building BasicTvRemote, AdvancedTvRemote, BasicRadioRemote
 * and so on, each remote holds a reference (the "bridge") to any device, so the two sides can grow
 * independently: 2 remotes + 2 devices = 4 combinations from 4 classes, not 4 special classes.
 */
public final class RemoteControlBridge {

    private RemoteControlBridge() {
    }

    /** Implementation side: what every device can do. */
    interface Device {
        String name();

        boolean isOn();

        void setPower(boolean on);

        int volume();

        void setVolume(int volume);

        default String status() {
            return name() + ": " + (isOn() ? "on" : "off") + ", volume " + volume();
        }
    }

    /** Shared device state; keeps volume within 0..100. */
    abstract static class BaseDevice implements Device {
        private final String name;
        private boolean on;
        private int volume;

        BaseDevice(String name, int startVolume) {
            this.name = name;
            this.volume = startVolume;
        }

        public String name() {
            return name;
        }

        public boolean isOn() {
            return on;
        }

        public void setPower(boolean on) {
            this.on = on;
        }

        public int volume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = Math.max(0, Math.min(100, volume));
        }
    }

    /** Concrete implementations. */
    static final class Tv extends BaseDevice {
        Tv() {
            super("TV", 30);
        }
    }

    static final class Radio extends BaseDevice {
        Radio() {
            super("Radio", 20);
        }
    }

    /** Abstraction: a remote that controls a device through the Device interface only. */
    static class RemoteControl {
        protected final Device device;

        RemoteControl(Device device) {
            if (device == null) {
                throw new IllegalArgumentException("A remote needs a device to control");
            }
            this.device = device;
        }

        void togglePower() {
            device.setPower(!device.isOn());
        }

        void volumeUp() {
            device.setVolume(device.volume() + 10);
        }

        void volumeDown() {
            device.setVolume(device.volume() - 10);
        }
    }

    /** Refined abstraction: adds features without touching any device class. */
    static final class AdvancedRemote extends RemoteControl {
        AdvancedRemote(Device device) {
            super(device);
        }

        void mute() {
            device.setVolume(0);
        }
    }

    public static void main(String[] args) {
        Tv tv = new Tv();
        RemoteControl basic = new RemoteControl(tv);
        basic.togglePower();
        basic.volumeUp();
        System.out.println("Basic remote    -> " + tv.status());

        Radio radio = new Radio();
        AdvancedRemote advanced = new AdvancedRemote(radio);
        advanced.togglePower();
        advanced.mute();
        System.out.println("Advanced remote -> " + radio.status());
    }
}
