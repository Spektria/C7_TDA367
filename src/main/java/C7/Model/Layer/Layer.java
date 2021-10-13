package C7.Model.Layer;

import C7.Util.Color;
import C7.Model.IObserver;
import C7.Util.Tuple2;
import C7.Util.Vector2D;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a basic image layer.
 * @author Elias Ersson
 * @author Love Gustafsson
 * @author Hugo Ekstrand
 * @version 2.0
 */
public class Layer implements ILayer, Serializable {

    private final Collection<IObserver<Tuple2<Vector2D, Vector2D>>> observers = new ArrayList<>();

    private Color[][] pixels;   // This layer's pixel data.
    private int width;          // The width, in pixels, of this layer.
    private int height;         // The height, in pixels of this layer.

    private double rotation = 0;    // Rotation in radians
    private Vector2D position = Vector2D.ZERO;
    private Vector2D scale = new Vector2D(1,1);
    private Vector2D inverseScale = new Vector2D(1, 1);

    // represents the rectangle in which any pixel has been modified. E.g.
    // if one were to modify pixel (20, 20), (25, 20), and (30, 30) the rectangle of change would be ((20,20), (30,30)).
    private transient Optional<Vector2D> rectangleOfChangeMin = Optional.empty(); // Stores smallest x and y
    private transient Optional<Vector2D> rectangleOfChangeMax = Optional.empty(); // Stores largest x and y


    /**
     * Constructs a new empty layer
     * @param width The desired width of the new layer in pixels
     * @param height The desired height of the new layer in pixels
     */
    public Layer(int width, int height, Color color) {
        Objects.requireNonNull(color);
        if(width < 0 || height < 0)
            throw new IllegalArgumentException();

        pixels = new Color[width][height];
        this.width  = width;
        this.height = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Copy color values
                pixels[x][y] = new Color(color);
            }
        }
    }

    /**
     * Constructs a new layer from a 2D matrix of Color
     * @param colorMatrix The matrix of color data of the new layer
     */
    public Layer(Color[][] colorMatrix) {
        /*pixels = colorMatrix; OPTIMIZATION this works faster but is technically bad because
        * it leaves an open reference somewhere to the new layer's color data
        * DEFENSIVE COPYING */

        pixels = new Color[colorMatrix.length][];

        for (int x = 0; x < colorMatrix.length; x++) {
            pixels[x] = new Color[colorMatrix[x].length];

            for (int y = 0; y < colorMatrix[x].length; y++) {
                Color oldColor = colorMatrix[x][y];
                //Would like a copy method for copying color data instead of this repeating pattern of GET
                pixels[x][y] = new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), oldColor.getAlpha());
            }
        }

        //Some sketchy stuff that will surely get replaced by more robust code later
        width = colorMatrix.length;
        if (width == 0) height = 0;
        else height = colorMatrix[0].length;
    }

    @Override
    public Color getGlobalPixel(int x, int y) {

        Vector2D localPos = toLocalPixel(new Vector2D(x, y));
        int localX = (int)localPos.getX();
        int localY = (int)localPos.getY();

        return getLocalPixel(localX, localY);
    }

    @Override
    public Color getLocalPixel(int x, int y) {
        if(!isPixelOnLocalLayer(x, y))
           throw new IllegalArgumentException();
        return pixels[x][y];
    }

    @Override
    public void setGlobalPixel(int x, int y, Color color) {
        Vector2D localPos = toLocalPixel(new Vector2D(x, y));
        int localX = (int)localPos.getX();
        int localY = (int)localPos.getY();

        setLocalPixel(localX, localY, color);
    }

    @Override
    public void setLocalPixel(int x, int y, Color color) {
        if(!isPixelOnLocalLayer(x, y))
            throw new IllegalArgumentException();

        Vector2D global = toGlobal(new Vector2D(x, y));
        updateRectangleOfChange((int)global.getX(), (int)global.getY());
        pixels[x][y] = color;
    }

    /**
     * Checks if the given local coordinate is outside
     * the current rectangle of change and enlarges the rectangle if
     * it is. If it is not, nothing happens.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    private void updateRectangleOfChange(int x, int y){
        // Check if there exists any values, if not make the rect points the given point
        if(rectangleOfChangeMin.isEmpty())
            rectangleOfChangeMin = Optional.of(new Vector2D(x, y));
        if(rectangleOfChangeMax.isEmpty())
            rectangleOfChangeMax = Optional.of(new Vector2D(x, y));

        int xMin = (int) rectangleOfChangeMin.get().getX();
        int yMin = (int) rectangleOfChangeMin.get().getY();
        int xMax = (int) rectangleOfChangeMax.get().getX();
        int yMax = (int) rectangleOfChangeMax.get().getY();

        // Check if the point is outside the rect. If it is change the rect so that it
        // encompasses the point.
        if(x < xMin)
            rectangleOfChangeMin = Optional.of(new Vector2D(x, yMin));
        if(y < yMin)
            rectangleOfChangeMin = Optional.of(new Vector2D(xMin, y));
        if(x > xMax)
            rectangleOfChangeMax = Optional.of(new Vector2D(x, yMax));
        if(y > yMax)
            rectangleOfChangeMax = Optional.of(new Vector2D(xMax, y));
    }

    /**
     * Makes the rectangle of change be for the whole layer. That is
     * makes the rectangle points be (0, 0) and (width, height).
     */
    private void maxRectangleOfChange(){
        Vector2D v0 = toGlobalPixel(Vector2D.ZERO);
        Vector2D v1 = toGlobalPixel(new Vector2D(0, width));
        Vector2D v2 = toGlobalPixel(new Vector2D(height, 0));
        Vector2D v3 = toGlobalPixel(new Vector2D(width, height));

        List<Double> xVals = List.of(v0.getX(), v1.getX(), v2.getX(), v3.getX());
        List<Double> yVals = List.of(v0.getY(), v1.getY(), v2.getY(), v3.getY());

        int xMin = xVals.stream().min(Double::compareTo).get().intValue();
        int xMax = xVals.stream().max(Double::compareTo).get().intValue();
        int yMin = yVals.stream().min(Double::compareTo).get().intValue();
        int yMax = yVals.stream().max(Double::compareTo).get().intValue();

        rectangleOfChangeMin = Optional.of(new Vector2D(xMin, yMin));
        rectangleOfChangeMax = Optional.of(new Vector2D(xMax, yMax));
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        setDimensions(width, getHeight());
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        setDimensions(getWidth(), height);
    }

    @Override
    public boolean isPixelOnLocalLayer(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Vector2D getLocalCenterPoint(){
        return new Vector2D(width / 2d, height / 2d);
    }

    private Vector2D toGlobal(Vector2D point){
        return point
                .sub(getLocalCenterPoint())             // Translate so that the center of the layer is at (0,0)
                .rotatedAround(Vector2D.ZERO, rotation) // Rotate the layer
                .scale(scale)                           // Scale the layer
                .add(getLocalCenterPoint())             // Translate the layer back to its original position
                .add(position);                         // Translate it to its global position
    }

    private Vector2D toGlobalPixel(Vector2D point){
        Vector2D globalPoint = toGlobal(point);
        return new Vector2D((int)globalPoint.getX(), (int)globalPoint.getY());
    }

    private Vector2D toLocal(Vector2D point){
        return point
                .sub(position)                          // Translate to local
                .sub(getLocalCenterPoint())             // Translate so that the center of the layer is at (0,0)
                .scale(inverseScale)                    // Scale the plane
                .rotatedAround(Vector2D.ZERO, -rotation)// Rotate it
                .add(getLocalCenterPoint());            // Move it back to its local position
    }

    @Override
    public void setRotation(double angle) {
        this.rotation = angle;
        maxRectangleOfChange();
    }

    @Override
    public double getRotation() {
        return this.rotation;
    }

    @Override
    public void setPosition(Vector2D position) {
        Objects.requireNonNull(position);
        this.position = position;
        maxRectangleOfChange();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public void setScale(Vector2D scale) {
        Objects.requireNonNull(scale);
        this.scale = scale;
        this.inverseScale = new Vector2D(1d / scale.getX(), 1d / scale.getY());
        maxRectangleOfChange();
    }

    @Override
    public Vector2D getScale() {
        return this.scale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Layer layer = (Layer) o;
        return width == layer.width && height == layer.height && Arrays.deepEquals(pixels, layer.pixels);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height);
        result = 31 * result + Arrays.hashCode(pixels);
        return result;
    }

    @Override
    public boolean isGlobalPointOnLayer(Vector2D point) {
        Vector2D localPosition = toLocal(point);
        return localPosition.getX() >= 0 && localPosition.getX() < width
                && localPosition.getY() >= 0 && localPosition.getY() < height;
    }

    @Override
    public Vector2D toLocalPixel(Vector2D point) {
        Vector2D localPoint = toLocal(point);
        return new Vector2D((int)localPoint.getX(), (int)localPoint.getY());
    }

    private void setDimensions(int width, int height) {
        if(width < 0 || height < 0)
            throw new IllegalArgumentException();

        Color[][] newPixels = new Color[width][height];

        final Color emptyColor = new Color(0, 0, 0, 1);

        for (int x = 0; x < width; x++) {
            if(x < this.width){
                System.arraycopy(pixels[x], 0, newPixels[x], 0, Math.min(pixels[x].length, newPixels[x].length) - 1);
                if(newPixels[x].length - pixels[x].length > 0)
                    Arrays.fill(newPixels[x], pixels[x].length, newPixels[x].length, emptyColor);
            }
            else{
                Arrays.fill(newPixels[x], emptyColor);
            }
        }

        pixels      = newPixels;
        this.width  = width;
        this.height = height;
        maxRectangleOfChange();
    }


    @Override
    public void addObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        observers.remove(observer);
    }

    @Override
    public void update() {
        if(rectangleOfChangeMin.isPresent() && rectangleOfChangeMax.isPresent()){

            // Notify each and every observer
            observers.forEach(ob -> ob.notify(new Tuple2<>(rectangleOfChangeMin.get(), rectangleOfChangeMax.get())));

            // Reset triangle
            rectangleOfChangeMin = Optional.empty();
            rectangleOfChangeMax = Optional.empty();
        }
    }

    //Create empty references for Optional:s so that they are not null
    private Object readResolve(){
        this.rectangleOfChangeMin = Optional.empty();
        this.rectangleOfChangeMax = Optional.empty();
        return this;
    }
}
