package learn.designpatterns.behavioral.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import learn.designpatterns.behavioral.iterator.PlaylistIterator.Playlist;
import learn.designpatterns.behavioral.iterator.PlaylistIterator.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaylistIteratorTest {

    private Playlist playlist;

    @BeforeEach
    void setUp() {
        playlist = new Playlist("Road Trip");
        playlist.add(new Song("Open Road", "The Drivers"));
        playlist.add(new Song("Desert Sun", "Mirage"));
        playlist.add(new Song("Night Drive", "Neon Lines"));
    }

    private static List<String> titles(Iterator<Song> songs) {
        List<String> titles = new ArrayList<>();
        while (songs.hasNext()) {
            titles.add(songs.next().title());
        }
        return titles;
    }

    @Test
    @DisplayName("the iterator visits songs in order")
    void visitsInOrder() {
        assertEquals(List.of("Open Road", "Desert Sun", "Night Drive"), titles(playlist.iterator()));
    }

    @Test
    @DisplayName("the playlist works in a for-each loop")
    void worksInForEach() {
        List<String> artists = new ArrayList<>();
        for (Song song : playlist) {
            artists.add(song.artist());
        }
        assertEquals(List.of("The Drivers", "Mirage", "Neon Lines"), artists);
    }

    @Test
    @DisplayName("a second kind of traversal (reverse) needs no access to the storage")
    void reverseTraversal() {
        assertEquals(List.of("Night Drive", "Desert Sun", "Open Road"), titles(playlist.reverseIterator()));
    }

    @Test
    @DisplayName("two iterators keep separate positions")
    void independentIterators() {
        Iterator<Song> first = playlist.iterator();
        Iterator<Song> second = playlist.iterator();
        first.next();
        first.next();
        assertEquals("Open Road", second.next().title());
        assertEquals("Night Drive", first.next().title());
    }

    @Test
    @DisplayName("asking for a song past the end is an error")
    void nextPastEndRejected() {
        Iterator<Song> songs = playlist.iterator();
        songs.next();
        songs.next();
        songs.next();
        assertFalse(songs.hasNext());
        assertThrows(NoSuchElementException.class, songs::next);
    }

    @Test
    @DisplayName("an empty playlist has nothing to visit")
    void emptyPlaylist() {
        assertFalse(new Playlist("Empty").iterator().hasNext());
    }

    @Test
    @DisplayName("a song needs a title")
    void blankTitleRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Song(" ", "Nobody"));
    }
}
