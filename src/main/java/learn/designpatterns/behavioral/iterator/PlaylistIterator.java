package learn.designpatterns.behavioral.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterator pattern.
 *
 * <p>Analogy: a music player's "next" button. You step through a playlist one song at a time without
 * knowing how the playlist stores its songs. The playlist can also offer other ways to step through
 * (here, in reverse), and two listeners can each keep their own place.
 */
public final class PlaylistIterator {

    private PlaylistIterator() {
    }

    /** Element. */
    record Song(String title, String artist) {
        Song {
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("A song needs a title");
            }
        }
    }

    /** Aggregate: owns the songs and hands out iterators; its list is never exposed. */
    static final class Playlist implements Iterable<Song> {
        private final String name;
        private final List<Song> songs = new ArrayList<>();

        Playlist(String name) {
            this.name = name;
        }

        void add(Song song) {
            songs.add(song);
        }

        /** Forward traversal; also makes the playlist usable in a for-each loop. */
        @Override
        public Iterator<Song> iterator() {
            return new ForwardIterator();
        }

        /** A second traversal strategy, added without exposing the storage. */
        Iterator<Song> reverseIterator() {
            return new ReverseIterator();
        }

        /** Concrete iterator: each instance keeps its own position. */
        private final class ForwardIterator implements Iterator<Song> {
            private int next = 0;

            public boolean hasNext() {
                return next < songs.size();
            }

            public Song next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("End of playlist '" + name + "'");
                }
                return songs.get(next++);
            }
        }

        private final class ReverseIterator implements Iterator<Song> {
            private int next = songs.size() - 1;

            public boolean hasNext() {
                return next >= 0;
            }

            public Song next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Start of playlist '" + name + "'");
                }
                return songs.get(next--);
            }
        }
    }

    public static void main(String[] args) {
        Playlist roadTrip = new Playlist("Road Trip");
        roadTrip.add(new Song("Open Road", "The Drivers"));
        roadTrip.add(new Song("Desert Sun", "Mirage"));
        roadTrip.add(new Song("Night Drive", "Neon Lines"));

        System.out.println("Forward:");
        for (Song song : roadTrip) {
            System.out.println("  " + song.title() + " - " + song.artist());
        }
        System.out.println("Reverse:");
        Iterator<Song> back = roadTrip.reverseIterator();
        while (back.hasNext()) {
            System.out.println("  " + back.next().title());
        }
    }
}
