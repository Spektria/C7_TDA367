package C7.View;

import C7.Model.Layer.ILayer;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.Canvas;
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
     * Tells the view to render a portion of an area, given by a rectangle.
     * @param x the start x-coord
     * @param y the start y-coord
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    void render(int x, int y, int width, int height);

    void setGraphicsContext(GraphicsContext gc);

    /**
     * Sets this views layer.
     * //NOTE: temporary until LayerManager is done.
     * @param layer the layer to be set to.
     */
    void setLayer(ILayer layer);

    /**
     * Sets the bounds of this view. That is, how wide and tall the image drawn by this view should be.
     * @param height the height property of this view.
     * @param width the width property of this view.
     */
    void setBounds(DoubleProperty width, DoubleProperty height);
}
