package classes;

import interfaces.EditorState;
import interfaces.Subject;
import interfaces.Observer;
import interfaces.Command;
import java.util.ArrayList;
import java.util.List;

/**
 * ===========================================================================================
 * CORE EDITOR CLASS
 * ===========================================================================================
 * This class serves three roles in the design patterns:
 * 1. SUBJECT (Observer Pattern): Notifies observers when content changes
 * 2. ORIGINATOR (Memento Pattern): Creates and restores mementos of its state
 * 3. CONTEXT (State Pattern): Delegates behavior to the current state object
 */
public class Editor implements Subject {
    private String content;
    private List<Observer> observers;
    private EditorState currentState;
    private CommandHistory history;

    public Editor() {
        this.content = "";
        this.observers = new ArrayList<>();
        this.currentState = new InsertState(); // Default state
        this.history = new CommandHistory();
    }

    // ===========================================================================================
    // PUBLIC API: Methods for modifying editor content via commands
    // ===========================================================================================

    /**
     * Insert text at the specified position using the Command pattern.
     */
    public void insert(String text, int position) {
        Command command = new InsertCommand(this, text, position);
        history.executeCommand(command);
    }

    /**
     * Delete text of specified length at the specified position using the Command pattern.
     */
    public void delete(int length, int position) {
        Command command = new DeleteCommand(this, length, position);
        history.executeCommand(command);
    }

    /**
     * Undo the last command.
     */
    public void undo() {
        history.undo();
    }

    /**
     * Redo the last undone command.
     */
    public void redo() {
        history.redo();
    }

    public boolean canUndo() {
        return history.canUndo();
    }

    public boolean canRedo() {
        return history.canRedo();
    }

    // ===========================================================================================
    // CONTENT MANAGEMENT: Called by State objects and Memento restoration
    // ===========================================================================================

    public String getContent() {
        return content;
    }

    /**
     * Set content and notify observers.
     * This method is called by State objects when performing operations
     * and by the restore() method when applying a memento.
     */
    public void setContent(String content) {
        this.content = content;
        notifyObservers(); // OBSERVER PATTERN: Notify all observers of the change
    }

    // ===========================================================================================
    // MEMENTO PATTERN: Originator methods
    // ===========================================================================================

    /**
     * Create a memento containing the current state.
     */
    public EditorMemento save() {
        return new EditorMemento(content);
    }

    /**
     * Restore state from a memento.
     */
    public void restore(EditorMemento memento) {
        if (memento != null) {
            setContent(memento.getContent());
        }
    }

    // ===========================================================================================
    // STATE PATTERN: Context methods
    // ===========================================================================================

    /**
     * Change the editor's state (mode).
     */
    public void setState(EditorState state) {
        this.currentState = state;
    }

    /**
     * Get the current state.
     */
    public EditorState getState() {
        return currentState;
    }

    // ===========================================================================================
    // OBSERVER PATTERN: Subject methods
    // ===========================================================================================

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(content);
        }
    }
}
