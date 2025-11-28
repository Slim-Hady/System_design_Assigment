package interfaces;

public interface Subject {
    /**
     * Attach an observer to receive notifications.
     */
    void attach(Observer observer);

    /**
     * Detach an observer from receiving notifications.
     */
    void detach(Observer observer);

    /**
     * Notify all attached observers of a state change.
     */
    void notifyObservers();
}
