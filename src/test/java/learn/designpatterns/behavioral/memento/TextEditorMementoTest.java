package learn.designpatterns.behavioral.memento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import learn.designpatterns.behavioral.memento.TextEditorMemento.Editor;
import learn.designpatterns.behavioral.memento.TextEditorMemento.History;
import learn.designpatterns.behavioral.memento.TextEditorMemento.Memento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TextEditorMementoTest {

    private Editor editor;
    private History history;

    @BeforeEach
    void setUp() {
        editor = new Editor();
        history = new History(editor);
    }

    @Test
    @DisplayName("undo restores the exact text from before the edit")
    void undoRestoresExactText() {
        editor.type("Hello");
        history.backup();
        editor.type(", world");
        assertEquals("Hello, world", editor.text());

        history.undo();
        assertEquals("Hello", editor.text());
    }

    @Test
    @DisplayName("several undos step back through the saved versions")
    void multipleUndos() {
        history.backup();
        editor.type("one");
        history.backup();
        editor.type(" two");
        history.backup();
        editor.type(" three");

        history.undo();
        assertEquals("one two", editor.text());
        history.undo();
        assertEquals("one", editor.text());
        history.undo();
        assertEquals("", editor.text());
    }

    @Test
    @DisplayName("a deletion can be undone too")
    void undoDeletion() {
        editor.type("abcdef");
        history.backup();
        editor.deleteLast(3);
        assertEquals("abc", editor.text());
        history.undo();
        assertEquals("abcdef", editor.text());
    }

    @Test
    @DisplayName("a snapshot is unaffected by later edits")
    void snapshotIsImmutable() {
        editor.type("Draft");
        Memento snapshot = editor.save();
        editor.type(" v2");
        editor.restore(snapshot);
        assertEquals("Draft", editor.text());
    }

    @Test
    @DisplayName("undo with no saved versions is an error")
    void undoWithoutHistoryRejected() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, history::undo);
        assertEquals("Nothing to undo", ex.getMessage());
    }

    @Test
    @DisplayName("invalid edits and restores are rejected")
    void invalidOperationsRejected() {
        editor.type("abc");
        assertThrows(IllegalArgumentException.class, () -> editor.deleteLast(4));
        assertThrows(IllegalArgumentException.class, () -> editor.restore(null));
    }
}
