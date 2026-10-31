package learn.designpatterns.structural.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import learn.designpatterns.structural.proxy.ImageLoaderProxy.Disk;
import learn.designpatterns.structural.proxy.ImageLoaderProxy.Image;
import learn.designpatterns.structural.proxy.ImageLoaderProxy.ImageProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageLoaderProxyTest {

    private Disk disk;

    @BeforeEach
    void setUp() {
        disk = new Disk();
    }

    @Test
    @DisplayName("creating a proxy does not touch the disk")
    void creationIsLazy() {
        ImageProxy proxy = new ImageProxy("beach.jpg", disk);
        assertFalse(proxy.isLoaded());
        assertEquals(List.of(), disk.reads());
    }

    @Test
    @DisplayName("the first display loads the real image from disk")
    void firstDisplayLoads() {
        Image image = new ImageProxy("beach.jpg", disk);
        assertEquals("Showing beach.jpg", image.display());
        assertEquals(List.of("beach.jpg"), disk.reads());
    }

    @Test
    @DisplayName("later displays reuse the cached image, with no extra disk reads")
    void secondDisplayUsesCache() {
        ImageProxy proxy = new ImageProxy("beach.jpg", disk);
        proxy.display();
        proxy.display();
        proxy.display();
        assertTrue(proxy.isLoaded());
        assertEquals(List.of("beach.jpg"), disk.reads());
    }

    @Test
    @DisplayName("only the images actually displayed are loaded")
    void onlyDisplayedImagesLoad() {
        List<Image> gallery = List.of(
                new ImageProxy("a.jpg", disk), new ImageProxy("b.jpg", disk), new ImageProxy("c.jpg", disk));
        gallery.get(2).display();
        assertEquals(List.of("c.jpg"), disk.reads());
    }

    @Test
    @DisplayName("a blank file name is rejected")
    void blankFileNameRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ImageProxy(" ", disk));
    }
}
