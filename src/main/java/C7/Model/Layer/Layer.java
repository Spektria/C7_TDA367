package C7.Model.Layer;

import C7.Model.Color;
import C7.Model.IObserver;
import C7.Model.Util.Tuple2;
import C7.Model.Vector.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a basic image layer.
 * @author Elias Ersson
 * @author Love Gustafsson
 * @author Hugo Ekstrand
 * @version 2.0
 */
public class Layer implements ILayer {

    private final Collection<IObserver<Tuple2<Vector2D, Vector2D>>> observers = new ArrayList<>();

    private Color[][] pixels;   // This layer's pixel data.
    private int width;          // The width, in pixels, of this layer.
    private int height;         // The height, in pixels of this layer.

    private double rotation = 0;    // Rotation in radians
    private Vector2D position = Vector2D.ZERO;
    private Vector2D scale = new Vector2D(1,1);


    /**
     * Constructs a new empty layer
     * @param width The desired width of the new layer in pixels
     * @param height The desired height of the new layer in pixels
     */
    public Layer(int width, int height, Color color) {

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

        Vector2D localPos = getPixelPositionAtPoint(new Vector2D(x, y));
        int localX = (int)localPos.getX();
        int localY = (int)localPos.getY();

        // Check if pixel is on this layer.
        if (!isPixelOnLayer(localX, localY)) {
            throw new IllegalArgumentException();
        }
        // Get the color at the specified position.
        Color pixelColor = pixels[localX][localY];

        return new Color(pixelColor);
    }

    @Override
    public Color getLocalPixel(int x, int y) {
        if(!isPixelOnLayer(x, y))
           throw new IllegalArgumentException();
        return pixels[x][y];
    }

    @Override
    public void setGlobalPixel(int x, int y, Color color) {
        Vector2D localPos = getPixelPositionAtPoint(new Vector2D(x, y));
        int localX = (int)localPos.getX();
        int localY = (int)localPos.getY();

        // Check if pixel is on this layer.
        if (!isPixelOnLayer(localX, localY)) {
            throw new IllegalArgumentException();
        }

        pixels[localX][localY] = new Color(color);
    }

    @Override
    public void setLocalPixel(int x, int y, Color color) {
        if(!isPixelOnLayer(x, y))
            throw new IllegalArgumentException();
        pixels[x][y] = color;
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
    public boolean isPixelOnLayer(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private Vector2D getLocalCenterPoint(){
        return new Vector2D(width / 2d, height / 2d);
    }

    private Vector2D toGlobal(Vector2D point){
        //Vector2D inverseScale = new Vector2D(1d / scale.getX(), 1d / scale.getY()); // TODO: scaling
        return point.rotatedAround(getLocalCenterPoint(), rotation).add(position);//.scale(inverseScale);
    }

    private Vector2D toLocal(Vector2D point){
        //Vector2D inverseScale = new Vector2D(1d / scale.getX(), 1d / scale.getY()); // TODO: scaling
        return point.sub(position).rotatedAround(getLocalCenterPoint(), -rotation);//.scale(inverseScale);
    }

    @Override
    public void setRotation(double angle) {
        this.rotation = angle;
    }

    @Override
    public double getRotation() {
        return this.rotation;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public void setScale(Vector2D scale) {
        this.scale = scale;
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
    public boolean isPointOnLayer(Vector2D point) {
        Vector2D localPosition = toLocal(point);
        return localPosition.getX() >= 0 && localPosition.getX() < width
                && localPosition.getY() >= 0 && localPosition.getY() < height;
    }

    @Override
    public Vector2D getPixelPositionAtPoint(Vector2D point) {
        Vector2D localPoint = toLocal(point);
        return new Vector2D((int)localPoint.getX(), (int)localPoint.getY());
    }

    private void setDimensions(int width, int height) {
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
    }


    @Override
    public void addObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        observers.add(observer);
    }

    @Override
    public void removeListener(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        observers.remove(observer);
    }
}
