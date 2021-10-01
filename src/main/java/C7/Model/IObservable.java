package C7.Model;

/**
 * Interface for an observable in the observer pattern. This object can be observed by
 * observers, which this object may notify via the {@link IObserver#notify(Object)} method.
 * @param <T> the type this observable will provide when it notifies an observer
 * @author Hugo Ekstrand
 */
public interface IObservable<T> {

    /**
     * Add a new observer to this observable object.
     * @param observer the observer to be added
     */
    void addObserver(IObserver<T> observer);

    /**
     * Removes an observer to this observable object.
     * @param observer the observer to be added
     */
    void removeListener(IObserver<T> observer);
}
