package C7.View;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents the view portion in the mvc pattern. This view can render an image, given
 * a layer and graphics context.
 * @author Hugo Ekstrand
 */
public interface IView {

    /**
     * Renders the whole view.
     */
    void render();

    /**
     * Tells the view to render a portion of an area, given by a rectangle described by two points.
     * @throws IllegalArgumentException if x > x1 or y1 > y. I.e. the given rectangular are must be positive.
     * @param x the start x-coord
     * @param y the start y-coord
     * @param x1 the end x-coord
     * @param y1 the end y-coord
     */
    void render(int x, int y, int x1, int y1);

    /**
     * Sets this views to a given graphics context.
     * This is the context which this view will draw upon.
     * @param gc the given graphics context.
     */
    void setGraphicsContext(GraphicsContext gc);

    /**
     * Sets the bounds of this view. That is, how wide and tall the image drawn by this view should be.
     * @param height the height property of this view.
     * @param width the width property of this view.
     */
    void setBounds(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height);
}
