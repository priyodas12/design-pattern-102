package learn.designpatterns.structural.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HomeTheaterFacadeTest {

    private HomeTheaterFacade theater;

    @BeforeEach
    void setUp() {
        theater = new HomeTheaterFacade();
    }

    @Test
    @DisplayName("one watchMovie call drives every subsystem in the right order")
    void watchMovieDrivesSubsystems() {
        theater.watchMovie("Space Voyage");
        assertEquals(List.of(
                "Lights: dimmed to 10%",
                "Projector: on",
                "Projector: input set to streaming",
                "Amplifier: on",
                "Amplifier: volume set to 5",
                "Player: playing \"Space Voyage\""), theater.log());
    }

    @Test
    @DisplayName("endMovie shuts everything down in reverse order")
    void endMovieShutsDown() {
        theater.watchMovie("Space Voyage");
        theater.endMovie();
        assertEquals(List.of(
                "Player: stopped",
                "Amplifier: off",
                "Projector: off",
                "Lights: on to 100%"), theater.log().subList(6, 10));
    }

    @Test
    @DisplayName("a blank title is rejected before anything is switched on")
    void blankTitleRejected() {
        assertThrows(IllegalArgumentException.class, () -> theater.watchMovie(" "));
        assertEquals(List.of(), theater.log());
    }

    @Test
    @DisplayName("ending a movie when none is playing is an error")
    void endWithoutMovieRejected() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, theater::endMovie);
        assertEquals("No movie is playing", ex.getMessage());
    }

    @Test
    @DisplayName("starting a second movie while one is playing is an error")
    void doubleStartRejected() {
        theater.watchMovie("Space Voyage");
        assertThrows(IllegalStateException.class, () -> theater.watchMovie("Ocean Deep"));
    }
}
