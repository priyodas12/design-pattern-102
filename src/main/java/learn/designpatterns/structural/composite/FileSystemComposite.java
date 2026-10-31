package learn.designpatterns.structural.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Composite pattern.
 *
 * <p>Analogy: a file system. A folder can hold files and other folders, and you can ask either one
 * "how big are you?". A file answers with its own size; a folder adds up the answers of everything
 * inside it. Code that asks the question never needs to know which kind it is talking to.
 */
public final class FileSystemComposite {

    private FileSystemComposite() {
    }

    /** Component: the common interface for files and folders. */
    sealed interface Node permits FileLeaf, Folder {
        String name();

        long size();

        /** Renders this node and everything below it, one line per node. */
        default String render() {
            List<String> lines = new ArrayList<>();
            renderInto(lines, "");
            return String.join("\n", lines);
        }

        void renderInto(List<String> lines, String indent);
    }

    /** Leaf: has no children. */
    record FileLeaf(String name, long size) implements Node {
        FileLeaf {
            if (size < 0) {
                throw new IllegalArgumentException("File size must not be negative: " + name + " = " + size);
            }
        }

        public void renderInto(List<String> lines, String indent) {
            lines.add(indent + name + " (" + size + ")");
        }
    }

    /** Composite: holds children and delegates to them. */
    static final class Folder implements Node {
        private final String name;
        private final List<Node> children = new ArrayList<>();

        Folder(String name) {
            this.name = name;
        }

        /** Adds a child; refuses to create a cycle (a folder inside itself). */
        void add(Node child) {
            if (child == this || (child instanceof Folder folder && folder.contains(this))) {
                throw new IllegalArgumentException("Cannot put folder '" + child.name() + "' inside itself");
            }
            children.add(child);
        }

        List<Node> children() {
            return Collections.unmodifiableList(children);
        }

        private boolean contains(Node target) {
            for (Node child : children) {
                if (child == target || (child instanceof Folder folder && folder.contains(target))) {
                    return true;
                }
            }
            return false;
        }

        public String name() {
            return name;
        }

        /** The composite's answer: the sum of its children's answers. */
        public long size() {
            return children.stream().mapToLong(Node::size).sum();
        }

        public void renderInto(List<String> lines, String indent) {
            lines.add(indent + name + "/ (" + size() + ")");
            for (Node child : children) {
                child.renderInto(lines, indent + "  ");
            }
        }
    }

    public static void main(String[] args) {
        Folder photos = new Folder("photos");
        photos.add(new FileLeaf("beach.jpg", 300));
        photos.add(new FileLeaf("party.jpg", 200));

        Folder home = new Folder("home");
        home.add(new FileLeaf("notes.txt", 10));
        home.add(photos);

        System.out.println(home.render());
    }
}
