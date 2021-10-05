package C7.View;

import C7.Model.Layer.ILayer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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

    void setCanvas(Canvas canvas);
}
