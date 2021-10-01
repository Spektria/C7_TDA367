package C7.Model.Util;

import C7.Model.IObserver;

import java.util.Objects;

/**
 * A 2 tuple is a structure containing 2 different objects of some type A and B.
 * @param <A> the type of the first value in the tuple
 * @param <B> the type of the second value in the tuple
 *
 * @author Hugo Ekstrand
 */
public final class Tuple2<A,B> {
    private final A val1;
    private final B val2;

    /**
     * Creates a tuple with two objects.
     * The objects must not be null.
     * @param val1 the first object
     * @param val2 the second object
     */
    public Tuple2(A val1, B val2) {
        Objects.requireNonNull(val1);
        Objects.requireNonNull(val2);

        this.val1 = val1;
        this.val2 = val2;
    }

    /**
     * Returns the first value in the tuple.
     * @return the first value in the tuple.
     */
    public A getVal1() {
        return val1;
    }

    /**
     * Returns the second value in the tuple.
     * @return the second value in the tuple.
     */
    public B getVal2() {
        return val2;
    }
}
