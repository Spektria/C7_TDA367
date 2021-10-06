package C7.Model.Tools.ToolProperties;

import C7.Model.Color;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A base abstract implementation a {@link IToolProperty}.
 * @param <T> the type of the property
 * @author Hugo Ekstrand
 */
abstract class BaseToolProperty<T> implements IToolProperty {

    private final String name;          // This property's name
    private final String description;   // Description of what this property is

    // The getter and setter used for the property on its owner.
    // e.g. it may be a direct reference to a field or set method.
    protected final Consumer<T> setter;
    protected final Supplier<T> getter;

    private final T defaultValue;


    BaseToolProperty(String name, String description,
                 Consumer<T> setter,
                 Supplier<T> getter){
        Objects.requireNonNull(name);
        Objects.requireNonNull(setter);
        Objects.requireNonNull(getter);

        this.name = name;
        this.description = description;
        this.setter = setter;
        this.getter = getter;

        this.defaultValue = getter.get();   // The default value is the value found when this object is created.
        Objects.requireNonNull(this.defaultValue);
    }

    @Override
    public void setToDefault(){
        this.setter.accept(defaultValue);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Number upperBound() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Number lowerBound() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInteger(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getInteger() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDouble(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getDouble() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBoolean(boolean bool) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBoolean() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColor(Color color) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color getColor() {
        throw new UnsupportedOperationException();
    }
}
