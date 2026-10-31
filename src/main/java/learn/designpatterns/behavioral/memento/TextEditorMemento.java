package learn.designpatterns.behavioral.memento;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Memento pattern.
 *
 * <p>Analogy: undo in a text editor. Before a change, the editor takes a sealed snapshot of its text.
 * The undo history stores these snapshots without being able to read or change them. Undo hands the
 * latest snapshot back to the editor, which restores itself exactly.
 */
public final class TextEditorMemento {

    private TextEditorMemento() {
    }

    /** Memento: an immutable snapshot. Only the editor (in this file) can create or read it. */
    static final class Memento {
        private final String text;

        private Memento(String text) {
            this.text = text;
        }
    }

    /** Originator: the object whose state is saved and restored. */
    static final class Editor {
        private final StringBuilder text = new StringBuilder();

        void type(String words) {
            text.append(words);
        }

        void deleteLast(int count) {
            if (count < 0 || count > text.length()) {
                throw new IllegalArgumentException(
                        "Cannot delete " + count + " characters from a " + text.length() + "-character text");
            }
            text.setLength(text.length() - count);
        }

        String text() {
            return text.toString();
        }

        Memento save() {
            return new Memento(text.toString());
        }

        void restore(Memento snapshot) {
            if (snapshot == null) {
                throw new IllegalArgumentException("Nothing to restore");
            }
            text.setLength(0);
            text.append(snapshot.text);
        }
    }

    /** Caretaker: keeps snapshots in order, but never looks inside them. */
    static final class History {
        private final Editor editor;
        private final Deque<Memento> snapshots = new ArrayDeque<>();

        History(Editor editor) {
            this.editor = editor;
        }

        void backup() {
            snapshots.push(editor.save());
        }

        void undo() {
            if (snapshots.isEmpty()) {
                throw new IllegalStateException("Nothing to undo");
            }
            editor.restore(snapshots.pop());
        }
    }

    public static void main(String[] args) {
        Editor editor = new Editor();
        History history = new History(editor);

        editor.type("Dear team,");
        history.backup();
        editor.type(" the meeting is cancelled.");
        System.out.println("Typed:  \"" + editor.text() + "\"");

        history.backup();
        editor.deleteLast("cancelled.".length());
        editor.type("moved to Friday.");
        System.out.println("Edited: \"" + editor.text() + "\"");

        history.undo();
        System.out.println("Undo:   \"" + editor.text() + "\"");
        history.undo();
        System.out.println("Undo:   \"" + editor.text() + "\"");
    }
}
