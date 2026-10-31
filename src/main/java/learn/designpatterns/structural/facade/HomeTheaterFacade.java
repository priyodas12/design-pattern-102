package learn.designpatterns.structural.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Facade pattern.
 *
 * <p>Analogy: a home theater has lights, a projector, an amplifier and a streaming player, each with
 * its own controls. To watch a movie you would have to work all of them in the right order. A single
 * "watch movie" button (the facade) does that for you; the devices are still there if you need them.
 *
 * <p>Every subsystem writes what it did to a shared log, so the order of operations is visible.
 */
public final class HomeTheaterFacade {

    private final List<String> log = new ArrayList<>();
    private final Lights lights = new Lights(log);
    private final Projector projector = new Projector(log);
    private final Amplifier amplifier = new Amplifier(log);
    private final StreamingPlayer player = new StreamingPlayer(log);
    private boolean playing;

    /** Subsystem classes: each is usable on its own, but fiddly to coordinate. */
    record Lights(List<String> log) {
        void dim(int percent) {
            log.add("Lights: dimmed to " + percent + "%");
        }

        void on() {
            log.add("Lights: on to 100%");
        }
    }

    record Projector(List<String> log) {
        void on() {
            log.add("Projector: on");
        }

        void setInput(String input) {
            log.add("Projector: input set to " + input);
        }

        void off() {
            log.add("Projector: off");
        }
    }

    record Amplifier(List<String> log) {
        void on() {
            log.add("Amplifier: on");
        }

        void setVolume(int level) {
            log.add("Amplifier: volume set to " + level);
        }

        void off() {
            log.add("Amplifier: off");
        }
    }

    record StreamingPlayer(List<String> log) {
        void play(String title) {
            log.add("Player: playing \"" + title + "\"");
        }

        void stop() {
            log.add("Player: stopped");
        }
    }

    /** Facade operation: one call that runs the whole start-up sequence. */
    public void watchMovie(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Movie title must not be blank");
        }
        if (playing) {
            throw new IllegalStateException("A movie is already playing");
        }
        lights.dim(10);
        projector.on();
        projector.setInput("streaming");
        amplifier.on();
        amplifier.setVolume(5);
        player.play(title);
        playing = true;
    }

    /** Facade operation: shuts everything down in reverse order. */
    public void endMovie() {
        if (!playing) {
            throw new IllegalStateException("No movie is playing");
        }
        player.stop();
        amplifier.off();
        projector.off();
        lights.on();
        playing = false;
    }

    /** What the subsystems did, in order. */
    public List<String> log() {
        return Collections.unmodifiableList(log);
    }

    public static void main(String[] args) {
        HomeTheaterFacade theater = new HomeTheaterFacade();
        theater.watchMovie("Space Voyage");
        theater.endMovie();
        theater.log().forEach(System.out::println);
    }
}
