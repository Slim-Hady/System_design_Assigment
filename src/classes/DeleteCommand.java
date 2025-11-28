package classes;

import interfaces.Command;

/**
 * ===========================================================================================
 * COMMAND PATTERN: Concrete Command - DeleteCommand
 * ===========================================================================================
 * Encapsulates a delete operation. Uses Memento pattern to save state before execution.
 */
class DeleteCommand implements Command {
    private Editor editor;
    private int length;
    private int position;
    private EditorMemento memento; // Memento pattern: stores state before execution

    public DeleteCommand(Editor editor, int length, int position) {
        this.editor = editor;
        this.length = length;
        this.position = position;
    }

    @Override
    public void execute() {
        // MEMENTO PATTERN: Save the current state before making changes
        memento = editor.save();

        // Delegate to the current state to perform the deletion
        editor.getState().deleteText(editor, length, position);
    }

    @Override
    public void undo() {
        // MEMENTO PATTERN: Restore the previous state
        if (memento != null) {
            editor.restore(memento);
        }
    }
}
