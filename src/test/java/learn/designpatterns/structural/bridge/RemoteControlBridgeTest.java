package learn.designpatterns.structural.bridge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Supplier;
import learn.designpatterns.structural.bridge.RemoteControlBridge.AdvancedRemote;
import learn.designpatterns.structural.bridge.RemoteControlBridge.Device;
import learn.designpatterns.structural.bridge.RemoteControlBridge.Radio;
import learn.designpatterns.structural.bridge.RemoteControlBridge.RemoteControl;
import learn.designpatterns.structural.bridge.RemoteControlBridge.Tv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RemoteControlBridgeTest {

    private static final List<Supplier<Device>> DEVICES = List.of(Tv::new, Radio::new);

    @Test
    @DisplayName("a basic remote toggles power on any device")
    void basicRemoteTogglesAnyDevice() {
        for (Supplier<Device> make : DEVICES) {
            Device device = make.get();
            RemoteControl remote = new RemoteControl(device);
            remote.togglePower();
            assertTrue(device.isOn(), device.name());
            remote.togglePower();
            assertFalse(device.isOn(), device.name());
        }
    }

    @Test
    @DisplayName("a basic remote changes volume in steps of 10 on any device")
    void basicRemoteChangesVolume() {
        for (Supplier<Device> make : DEVICES) {
            Device device = make.get();
            int start = device.volume();
            RemoteControl remote = new RemoteControl(device);
            remote.volumeUp();
            assertEquals(start + 10, device.volume(), device.name());
            remote.volumeDown();
            remote.volumeDown();
            assertEquals(start - 10, device.volume(), device.name());
        }
    }

    @Test
    @DisplayName("an advanced remote adds mute, working on any device")
    void advancedRemoteMutesAnyDevice() {
        for (Supplier<Device> make : DEVICES) {
            Device device = make.get();
            new AdvancedRemote(device).mute();
            assertEquals(0, device.volume(), device.name());
        }
    }

    @Test
    @DisplayName("volume stays between 0 and 100")
    void volumeIsClamped() {
        Tv tv = new Tv();
        RemoteControl remote = new RemoteControl(tv);
        for (int i = 0; i < 20; i++) {
            remote.volumeUp();
        }
        assertEquals(100, tv.volume());
        new AdvancedRemote(tv).mute();
        remote.volumeDown();
        assertEquals(0, tv.volume());
    }

    @Test
    @DisplayName("each device reports its own status format")
    void devicesDifferInImplementation() {
        assertEquals("TV: off, volume 30", new Tv().status());
        assertEquals("Radio: off, volume 20", new Radio().status());
    }

    @Test
    @DisplayName("a remote needs a device")
    void nullDeviceRejected() {
        assertThrows(IllegalArgumentException.class, () -> new RemoteControl(null));
    }
}
