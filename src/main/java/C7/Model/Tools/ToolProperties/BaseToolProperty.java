package C7.Model.Tools.ToolProperties;

import C7.Model.Color;

import java.util.function.Consumer;
import java.util.function.Supplier;

abstract class BaseToolProperty<T> implements IToolProperty {

    private final String name;
    private final String description;
    protected final Consumer<T> setter;
    protected final Supplier<T> getter;


    BaseToolProperty(String name, String description,
                 Consumer<T> setter,
                 Supplier<T> getter){

        this.name = name;
        this.description = description;
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
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
    public void setFloat(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getFloat() {
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
