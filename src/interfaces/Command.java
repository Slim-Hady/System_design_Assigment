package interfaces;

/**
 * ===========================================================================================
 * COMMAND PATTERN: Command interface
 * ===========================================================================================
 * Purpose: Encapsulate operations (insert, delete) as objects to support undo/redo functionality.
 * Each command knows how to execute itself and how to undo its effects.
 */
public interface Command {
    /**
     * Execute the command's operation.
     */
    void execute();

    /**
     * Undo the command's operation by restoring the previous state.
     */
    void undo();
}
