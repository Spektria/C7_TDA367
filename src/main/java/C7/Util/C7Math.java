package C7.Util;

import java.util.Arrays;
import java.util.Objects;

/**
 * Has some basic math based operations. Used mainly to reduce code duplication.
 * @author Hugo Ekstrand
 */
public final class C7Math {

    /**
     * Returns the minimum value in a list of comparable values.
     * @param comparables the values
     * @param <T> the value type
     * @return the minimum value
     */
    public static<T extends Comparable<T>> T min(T...comparables){
        Objects.requireNonNull(comparables);
        if(comparables.length == 0)
            throw new IllegalArgumentException();

        T min = comparables[0];
        for (int i = 1; i < comparables.length; i++) {
            if(min.compareTo(comparables[i]) == 1)
                min = comparables[i];
        }
        return min;
    }

    /**
     * Returns the maximum value in a list of comparable values
     * @param comparables the values
     * @param <T> the value type
     * @return the maximum value
     */
    public static<T extends Comparable<T>> T max(T...comparables){
        Objects.requireNonNull(comparables);
        if(comparables.length == 0)
            throw new IllegalArgumentException();

        T max = comparables[0];
        for (int i = 1; i < comparables.length; i++) {
            if(max.compareTo(comparables[i]) == -1)
                max = comparables[i];
        }
        return max;
    }

    /**
     * Forces a given value to be the value it's closest to in a range.
     * If the value is outside the range it gets set to the closest value inside the range.
     * The minimum value mustn't be less than the maximum value.
     * @throws IllegalArgumentException if the minimum value is larger than the maximum value
     * @param val the value to force to be in a range
     * @param min the minimum value in the range
     * @param max the maximum value in the range
     * @param <T> the value type
     * @return the value forced within the range
     */
    public static<T extends Comparable<T>> T limit(T val, T min, T max){
        Objects.requireNonNull(val);
        Objects.requireNonNull(min);
        Objects.requireNonNull(max);

        if(min.compareTo(max) == 1)
            throw new IllegalArgumentException();

        if(val.compareTo(min) == -1)
            return min;
        else if(val.compareTo(max) == 1)
            return max;
        else
            return val;
    }
}
