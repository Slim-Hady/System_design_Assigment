package classes;

import interfaces.Command;

import java.util.Stack;

/**
 * ===========================================================================================
 * COMMAND PATTERN: CommandHistory (Caretaker)
 * ===========================================================================================
 * Purpose: Manage the history of executed commands to support undo/redo operations.
 * Acts as the Caretaker in the Memento pattern by managing command objects that contain mementos.
 */
class CommandHistory {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public CommandHistory() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Execute a command and add it to the undo history.
     * Clears the redo stack since a new action invalidates any previously undone actions.
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // New command clears redo history
    }

    /**
     * Undo the most recent command.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    /**
     * Redo the most recently undone command.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
