package interfaces;

/**
 * ===========================================================================================
 * OBSERVER PATTERN: Subject and Observer interfaces
 * ===========================================================================================
 * Purpose: Allow multiple views (observers) to automatically update when the editor content changes.
 * The classes.Editor acts as the Subject, and views like classes.WordCountView and classes.CharacterCountView are Observers.
 */
public interface Observer {
    /**
     * Called when the subject's state changes.
     *
     * @param content The new content of the editor
     */
    void update(String content);
}
