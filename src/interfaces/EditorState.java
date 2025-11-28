package interfaces;

import classes.Editor;

/**
 * ===========================================================================================
 * STATE PATTERN: State interface
 * ===========================================================================================
 * Purpose: Define different behaviors for the editor based on its current mode.
 * This allows the editor to change its behavior dynamically without large conditional statements.
 */
public interface EditorState {
    /**
     * Insert text at the specified position.
     * Behavior depends on the current state (allowed in classes.InsertState, blocked in classes.ReadOnlyState).
     */
    void insertText(Editor editor, String text, int position);

    /**
     * Delete text of specified length at the specified position.
     * Behavior depends on the current state (allowed in classes.InsertState, blocked in classes.ReadOnlyState).
     */
    void deleteText(Editor editor, int length, int position);

    /**
     * Get the name of the current state for display purposes.
     */
    String getStateName();
}
