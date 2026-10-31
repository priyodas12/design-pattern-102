package learn.designpatterns.creational.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Prototype pattern.
 *
 * <p>Analogy: an office keeps master templates (an invoice, a letter). Instead of typing a new
 * document from scratch, you photocopy the template and edit the copy. Scribbling on the copy never
 * changes the master.
 */
public final class DocumentTemplatePrototype {

    private DocumentTemplatePrototype() {
    }

    /** Prototype interface: objects that know how to copy themselves. */
    interface Prototype<T> {
        T copy();
    }

    /** Concrete prototype. Its section list is mutable, so copy() must make a deep copy. */
    static final class Document implements Prototype<Document> {
        private String title;
        private final String author;
        private final List<String> sections;

        Document(String title, String author) {
            this(title, author, new ArrayList<>());
        }

        private Document(String title, String author, List<String> sections) {
            this.title = requireText(title, "title");
            this.author = requireText(author, "author");
            this.sections = sections;
        }

        /** Copies every field; the section list is new, so edits on either side stay separate. */
        @Override
        public Document copy() {
            return new Document(title, author, new ArrayList<>(sections));
        }

        String title() {
            return title;
        }

        String author() {
            return author;
        }

        List<String> sections() {
            return List.copyOf(sections);
        }

        void setTitle(String title) {
            this.title = requireText(title, "title");
        }

        void addSection(String section) {
            sections.add(requireText(section, "section"));
        }

        @Override
        public String toString() {
            return "\"" + title + "\" by " + author + " " + sections;
        }

        private static String requireText(String value, String field) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Document " + field + " must not be blank");
            }
            return value;
        }
    }

    /** Prototype registry: stores masters and hands out fresh copies. */
    static final class TemplateRegistry {
        private final Map<String, Document> masters = new HashMap<>();

        /** Stores a copy, so later edits to the caller's document don't change the master. */
        void register(String name, Document template) {
            masters.put(name, template.copy());
        }

        Document create(String name) {
            Document master = masters.get(name);
            if (master == null) {
                throw new IllegalArgumentException("No template named '" + name + "' (have: " + masters.keySet() + ")");
            }
            return master.copy();
        }
    }

    public static void main(String[] args) {
        Document invoice = new Document("Invoice", "Example Traders");
        invoice.addSection("Header");
        invoice.addSection("Line items");

        TemplateRegistry registry = new TemplateRegistry();
        registry.register("invoice", invoice);

        Document march = registry.create("invoice");
        march.setTitle("Invoice #2026-03");
        march.addSection("Late-payment note");

        System.out.println("Master: " + registry.create("invoice"));
        System.out.println("Copy:   " + march);
    }
}
