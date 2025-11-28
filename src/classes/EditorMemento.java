package classes;

/**
 * ===========================================================================================
 * MEMENTO PATTERN: Memento class
 * ===========================================================================================
 * Purpose: Store a snapshot of the classes.Editor's text content state for undo/redo operations.
 * This is an immutable object that preserves the editor's state at a specific point in time.
 */
class EditorMemento {
    private final String content;

    public EditorMemento(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
