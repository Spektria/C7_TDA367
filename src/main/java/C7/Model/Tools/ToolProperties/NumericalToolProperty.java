package C7.Model.Tools.ToolProperties;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A numerical property. This property has bounds, i.e. a max and min value.
 * @param <T> the number type this property represents
 * @author Hugo Ekstrand
 */
abstract class NumericalToolProperty<T extends Number & Comparable<T>> extends BaseToolProperty<T> {

    private final T min;
    private final T max;

    NumericalToolProperty(String name, String description, Consumer<T> setter, Supplier<T> getter, T min, T max) {
        super(name, description, setter, getter);
        this.max = max;
        this.min = min;
    }

    void requireBounds(T number){
        if(!(number.compareTo(upperBound()) <= 0 && number.compareTo(lowerBound()) >= 0))
            throw new IllegalArgumentException("Input number out of property bounds. Input: "
                    + number + " is not withing the range [" + lowerBound() + ", " + upperBound() + "].");
    }

    @Override
    public T upperBound() {
        return this.max;
    }

    @Override
    public T lowerBound() {
        return this.min;
    }
}
