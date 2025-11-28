package classes;

import interfaces.EditorState;

/**
 * ===========================================================================================
 * STATE PATTERN: Concrete State - InsertState
 * ===========================================================================================
 * Default state where all editing operations are allowed.
 */
public class InsertState implements EditorState {
    @Override
    public void insertText(Editor editor, String text, int position) {
        // Perform the actual insertion by modifying the editor's content
        String content = editor.getContent();
        if (position < 0) position = 0;
        if (position > content.length()) position = content.length();

        String newContent = content.substring(0, position) + text + content.substring(position);
        editor.setContent(newContent);
    }

    @Override
    public void deleteText(Editor editor, int length, int position) {
        // Perform the actual deletion by modifying the editor's content
        String content = editor.getContent();
        if (position < 0) position = 0;
        if (position >= content.length() || length <= 0) return;

        int endPosition = Math.min(position + length, content.length());
        String newContent = content.substring(0, position) + content.substring(endPosition);
        editor.setContent(newContent);
    }

    @Override
    public String getStateName() {
        return "Insert Mode";
    }
}
