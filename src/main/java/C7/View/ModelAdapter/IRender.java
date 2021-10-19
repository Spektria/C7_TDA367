package C7.View.ModelAdapter;

import C7.Util.Color;
import C7.Util.IObservable;
import C7.Util.Tuple2;
import C7.Util.Vector2D;

/**
 * IRender is an interface for classes which can return a
 * 2d color array at a given position. They should also be able to be observed
 * for changes between two given coordinates.
 * @author Hugo Ekstrand
 */
public interface IRender extends IObservable<Tuple2<Vector2D, Vector2D>> {

    /**
     * Returns a 2d color array for a given rectangular area of this IRender.
     * The given area must be positive, and width/height mustn't be negative.
     * The returned array will be of the size Color[width][height].
     * @param x x co-ordinate position of rectangle
     * @param y y co-ordinate position of rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @return the 2d color array for the given rectangular area.
     */
    Color[][] render(int x, int y, int width, int height);


}
