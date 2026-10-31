package learn.designpatterns.creational.prototype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import learn.designpatterns.creational.prototype.DocumentTemplatePrototype.Document;
import learn.designpatterns.creational.prototype.DocumentTemplatePrototype.TemplateRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DocumentTemplatePrototypeTest {

    private Document invoice;

    @BeforeEach
    void setUp() {
        invoice = new Document("Invoice", "Example Traders");
        invoice.addSection("Header");
        invoice.addSection("Line items");
    }

    @Test
    @DisplayName("a copy has the same content but is a different object")
    void copyMatchesOriginal() {
        Document copy = invoice.copy();
        assertNotSame(invoice, copy);
        assertEquals(invoice.title(), copy.title());
        assertEquals(invoice.author(), copy.author());
        assertEquals(invoice.sections(), copy.sections());
    }

    @Test
    @DisplayName("editing the copy does not change the original")
    void copyChangesDoNotLeakBack() {
        Document copy = invoice.copy();
        copy.setTitle("Invoice #42");
        copy.addSection("Notes");

        assertEquals("Invoice", invoice.title());
        assertEquals(List.of("Header", "Line items"), invoice.sections());
    }

    @Test
    @DisplayName("editing the original does not change an existing copy")
    void originalChangesDoNotLeakForward() {
        Document copy = invoice.copy();
        invoice.addSection("Terms");
        assertEquals(List.of("Header", "Line items"), copy.sections());
    }

    @Test
    @DisplayName("the registry hands out fresh, independent copies")
    void registryCreatesIndependentCopies() {
        TemplateRegistry registry = new TemplateRegistry();
        registry.register("invoice", invoice);
        invoice.addSection("Added after registering");

        Document a = registry.create("invoice");
        Document b = registry.create("invoice");
        a.addSection("Only in a");

        assertNotSame(a, b);
        assertEquals(List.of("Header", "Line items"), b.sections());
    }

    @Test
    @DisplayName("an unknown template name is rejected")
    void unknownTemplateRejected() {
        assertThrows(IllegalArgumentException.class, () -> new TemplateRegistry().create("contract"));
    }

    @Test
    @DisplayName("a blank title is rejected")
    void blankTitleRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Document(" ", "Example Traders"));
    }
}
