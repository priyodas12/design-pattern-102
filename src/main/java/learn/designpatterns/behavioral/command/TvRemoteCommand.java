package learn.designpatterns.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Command pattern.
 *
 * <p>Analogy: a TV remote. Each button press is turned into a small "command" object that knows how to
 * do its action and how to undo it. The remote keeps a history of pressed commands, so an undo button
 * can reverse them one by one, without the remote knowing how a TV works.
 */
public final class TvRemoteCommand {

    private TvRemoteCommand() {
    }

    /** Receiver: the object that actually does the work. */
    static final class Tv {
        private boolean on;
        private int channel = 1;
        private int volume = 10;

        boolean isOn() {
            return on;
        }

        int channel() {
            return channel;
        }

        int volume() {
            return volume;
        }

        void setPower(boolean on) {
            this.on = on;
        }

        void setChannel(int channel) {
            if (channel < 1 || channel > 999) {
                throw new IllegalArgumentException("Channel must be between 1 and 999, got " + channel);
            }
            this.channel = channel;
        }

        void setVolume(int volume) {
            this.volume = Math.max(0, Math.min(100, volume));
        }

        String status() {
            return "TV " + (on ? "on" : "off") + ", channel " + channel + ", volume " + volume;
        }
    }

    /** Command: one action plus how to reverse it. */
    interface Command {
        void execute();

        void undo();
    }

    /** Concrete commands: each remembers what it needs in order to undo itself. */
    record PowerToggle(Tv tv) implements Command {
        public void execute() {
            tv.setPower(!tv.isOn());
        }

        public void undo() {
            tv.setPower(!tv.isOn());
        }
    }

    static final class ChangeChannel implements Command {
        private final Tv tv;
        private final int channel;
        private int previous;

        ChangeChannel(Tv tv, int channel) {
            this.tv = tv;
            this.channel = channel;
        }

        public void execute() {
            previous = tv.channel();
            tv.setChannel(channel);
        }

        public void undo() {
            tv.setChannel(previous);
        }
    }

    static final class VolumeUp implements Command {
        private final Tv tv;
        private int previous;

        VolumeUp(Tv tv) {
            this.tv = tv;
        }

        public void execute() {
            previous = tv.volume();
            tv.setVolume(previous + 5);
        }

        public void undo() {
            tv.setVolume(previous);
        }
    }

    /** Invoker: runs commands and keeps the history for undo; knows nothing about TVs. */
    static final class RemoteControl {
        private final Deque<Command> history = new ArrayDeque<>();

        /** Executes first, then records, so a command that fails never enters the history. */
        void press(Command command) {
            command.execute();
            history.push(command);
        }

        void undo() {
            if (history.isEmpty()) {
                throw new IllegalStateException("Nothing to undo");
            }
            history.pop().undo();
        }

        int historySize() {
            return history.size();
        }
    }

    public static void main(String[] args) {
        Tv tv = new Tv();
        RemoteControl remote = new RemoteControl();
        remote.press(new PowerToggle(tv));
        remote.press(new ChangeChannel(tv, 42));
        remote.press(new VolumeUp(tv));
        System.out.println("After 3 presses: " + tv.status());
        remote.undo();
        System.out.println("Undo volume:     " + tv.status());
        remote.undo();
        System.out.println("Undo channel:    " + tv.status());
    }
}
