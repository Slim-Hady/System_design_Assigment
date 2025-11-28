package classes;

import interfaces.Command;

/**
 * ===========================================================================================
 * COMMAND PATTERN: Concrete Command - InsertCommand
 * ===========================================================================================
 * Encapsulates an insert operation. Uses Memento pattern to save state before execution.
 */
class InsertCommand implements Command {
    private Editor editor;
    private String text;
    private int position;
    private EditorMemento memento; // Memento pattern: stores state before execution

    public InsertCommand(Editor editor, String text, int position) {
        this.editor = editor;
        this.text = text;
        this.position = position;
    }

    @Override
    public void execute() {
        // MEMENTO PATTERN: Save the current state before making changes
        memento = editor.save();

        // Delegate to the current state to perform the insertion
        editor.getState().insertText(editor, text, position);
    }

    @Override
    public void undo() {
        // MEMENTO PATTERN: Restore the previous state
        if (memento != null) {
            editor.restore(memento);
        }
    }
}
