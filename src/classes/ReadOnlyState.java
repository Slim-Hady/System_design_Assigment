package classes;

import interfaces.EditorState;

/**
 * ===========================================================================================
 * STATE PATTERN: Concrete State - ReadOnlyState
 * ===========================================================================================
 * State where all editing operations are blocked. Throws exceptions when modifications are attempted.
 */
public class ReadOnlyState implements EditorState {
    @Override
    public void insertText(Editor editor, String text, int position) {
        // Block the operation and notify the user
        throw new IllegalStateException("Cannot insert text in Read-Only mode!");
    }

    @Override
    public void deleteText(Editor editor, int length, int position) {
        // Block the operation and notify the user
        throw new IllegalStateException("Cannot delete text in Read-Only mode!");
    }

    @Override
    public String getStateName() {
        return "Read-Only Mode";
    }
}
