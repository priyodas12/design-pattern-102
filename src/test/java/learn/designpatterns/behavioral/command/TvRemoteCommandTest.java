package learn.designpatterns.behavioral.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import learn.designpatterns.behavioral.command.TvRemoteCommand.ChangeChannel;
import learn.designpatterns.behavioral.command.TvRemoteCommand.PowerToggle;
import learn.designpatterns.behavioral.command.TvRemoteCommand.RemoteControl;
import learn.designpatterns.behavioral.command.TvRemoteCommand.Tv;
import learn.designpatterns.behavioral.command.TvRemoteCommand.VolumeUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TvRemoteCommandTest {

    private Tv tv;
    private RemoteControl remote;

    @BeforeEach
    void setUp() {
        tv = new Tv();
        remote = new RemoteControl();
    }

    @Test
    @DisplayName("pressing a button executes its command on the TV")
    void executesCommand() {
        remote.press(new ChangeChannel(tv, 7));
        assertEquals(7, tv.channel());
    }

    @Test
    @DisplayName("undo restores the previous state")
    void undoRestoresPreviousState() {
        remote.press(new ChangeChannel(tv, 7));
        remote.undo();
        assertEquals(1, tv.channel());
    }

    @Test
    @DisplayName("several undos reverse the commands in reverse order")
    void undoInReverseOrder() {
        remote.press(new PowerToggle(tv));
        remote.press(new ChangeChannel(tv, 5));
        remote.press(new VolumeUp(tv));
        assertEquals(15, tv.volume());

        remote.undo();
        assertEquals(10, tv.volume());
        assertEquals(5, tv.channel());
        remote.undo();
        assertEquals(1, tv.channel());
        assertTrue(tv.isOn());
        remote.undo();
        assertFalse(tv.isOn());
        assertEquals(0, remote.historySize());
    }

    @Test
    @DisplayName("undo with nothing to undo is an error")
    void undoWithEmptyHistoryRejected() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, remote::undo);
        assertEquals("Nothing to undo", ex.getMessage());
    }

    @Test
    @DisplayName("a command that fails is not added to the history")
    void failedCommandNotRecorded() {
        assertThrows(IllegalArgumentException.class, () -> remote.press(new ChangeChannel(tv, 0)));
        assertEquals(0, remote.historySize());
        assertEquals(1, tv.channel());
    }
}
