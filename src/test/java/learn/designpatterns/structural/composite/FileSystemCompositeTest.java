package learn.designpatterns.structural.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import learn.designpatterns.structural.composite.FileSystemComposite.FileLeaf;
import learn.designpatterns.structural.composite.FileSystemComposite.Folder;
import learn.designpatterns.structural.composite.FileSystemComposite.Node;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileSystemCompositeTest {

    private static Folder sampleTree() {
        Folder photos = new Folder("photos");
        photos.add(new FileLeaf("beach.jpg", 300));
        photos.add(new FileLeaf("party.jpg", 200));

        Folder home = new Folder("home");
        home.add(new FileLeaf("notes.txt", 10));
        home.add(photos);
        return home;
    }

    @Test
    @DisplayName("a file reports its own size")
    void fileSize() {
        assertEquals(42, new FileLeaf("a.txt", 42).size());
    }

    @Test
    @DisplayName("a folder's size is the total of everything inside it, at any depth")
    void folderSumsNestedContent() {
        assertEquals(510, sampleTree().size());
    }

    @Test
    @DisplayName("an empty folder has size 0")
    void emptyFolder() {
        assertEquals(0, new Folder("empty").size());
    }

    @Test
    @DisplayName("clients treat files and folders the same way")
    void uniformTreatment() {
        List<Node> items = List.of(new FileLeaf("todo.md", 5), sampleTree());
        long total = items.stream().mapToLong(Node::size).sum();
        assertEquals(515, total);
    }

    @Test
    @DisplayName("the tree renders with indentation and sizes")
    void rendersTree() {
        assertEquals(String.join("\n",
                        "home/ (510)",
                        "  notes.txt (10)",
                        "  photos/ (500)",
                        "    beach.jpg (300)",
                        "    party.jpg (200)"),
                sampleTree().render());
    }

    @Test
    @DisplayName("a negative file size is rejected")
    void negativeSizeRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FileLeaf("bad.bin", -1));
    }

    @Test
    @DisplayName("a folder cannot be placed inside itself or its own subfolder")
    void cyclesRejected() {
        Folder home = sampleTree();
        Folder photos = (Folder) home.children().get(1);
        assertThrows(IllegalArgumentException.class, () -> home.add(home));
        assertThrows(IllegalArgumentException.class, () -> photos.add(home));
    }
}
