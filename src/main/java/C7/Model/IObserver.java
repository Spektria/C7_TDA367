package C7.Model;

/**
 * An observer to an {@link IObservable} object. This observer observes changes in
 * an observable and is notified of these changes when the observable calls {@link #notify(Object)} on this observer.
 * @param <T> the type of the data this observer will receive from the observable
 */
public interface IObserver<T> {
    void notify(T data);
}
