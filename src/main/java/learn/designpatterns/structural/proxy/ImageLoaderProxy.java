package learn.designpatterns.structural.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Proxy pattern (virtual proxy).
 *
 * <p>Analogy: a photo gallery shows thumbnails for hundreds of photos, but only loads a full photo
 * from disk when you actually open it, and keeps it once loaded. The gallery holds lightweight
 * stand-ins (proxies) that look exactly like real images; each one loads the real image on first use.
 */
public final class ImageLoaderProxy {

    private ImageLoaderProxy() {
    }

    /** Subject: what clients use, whether they hold a real image or a proxy. */
    interface Image {
        String display();
    }

    /** Simulated slow disk; records each read so the loading behavior can be checked. */
    static final class Disk {
        private final List<String> reads = new ArrayList<>();

        byte[] read(String fileName) {
            reads.add(fileName);
            return ("pixels of " + fileName).getBytes();
        }

        List<String> reads() {
            return Collections.unmodifiableList(reads);
        }
    }

    /** Real subject: expensive to create, because it reads from disk in its constructor. */
    static final class RealImage implements Image {
        private final String fileName;
        private final byte[] pixels;

        RealImage(String fileName, Disk disk) {
            this.fileName = fileName;
            this.pixels = disk.read(fileName);
        }

        @Override
        public String display() {
            return "Showing " + fileName;
        }

        int sizeBytes() {
            return pixels.length;
        }
    }

    /** Proxy: same interface as the real image; creates it lazily and caches it. */
    static final class ImageProxy implements Image {
        private final String fileName;
        private final Disk disk;
        private RealImage real;

        ImageProxy(String fileName, Disk disk) {
            if (fileName == null || fileName.isBlank()) {
                throw new IllegalArgumentException("Image file name must not be blank");
            }
            this.fileName = fileName;
            this.disk = disk;
        }

        @Override
        public String display() {
            if (real == null) {
                real = new RealImage(fileName, disk);
            }
            return real.display();
        }

        boolean isLoaded() {
            return real != null;
        }
    }

    public static void main(String[] args) {
        Disk disk = new Disk();
        List<ImageProxy> gallery = List.of(
                new ImageProxy("beach.jpg", disk), new ImageProxy("party.jpg", disk), new ImageProxy("sunset.jpg", disk));
        System.out.println("Gallery opened, disk reads so far: " + disk.reads());

        System.out.println(gallery.get(0).display());
        System.out.println(gallery.get(0).display());
        System.out.println("After showing beach.jpg twice, disk reads: " + disk.reads());
    }
}
