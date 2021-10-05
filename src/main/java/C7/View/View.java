package C7.View;

import C7.Model.IObserver;
import C7.Model.Layer.ILayer;
import C7.Model.Util.Tuple2;
import C7.Model.Vector.Vector2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * An {@link IView} implementation.
 * @author Hugo Ekstrand
 */
class View implements IView, IObserver<Tuple2<Vector2D, Vector2D>> {

    private ILayer layer;
    private GraphicsContext gc;
    private Tuple2<Vector2D, Vector2D> lastUpdateRect;
    private Canvas canvas;

    public View(ILayer layer){
        Objects.requireNonNull(layer);
        this.layer = layer;

        layer.addObserver(this);
    }

    @Override
    public void render() {
        render(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
    }

    private Color toJFXColor(C7.Model.Color color){
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void render(int x0, int y0, int width, int height) {
        Objects.requireNonNull(gc);

        PixelWriter pw = gc.getPixelWriter();
        for (int y = y0; y < height; y++) {
            for (int x = x0; x < width; x++) {
                Color color = Color.TRANSPARENT;

                // If the point is on the layer, take the layers color, else put transparent.
                if(layer.isPointOnLayer(new Vector2D(x, y))){
                    color = toJFXColor(layer.getGlobalPixel(x, y));
                }

                //Update to take into account canvas transform
                pw.setColor(x, y, color);
            }
        }
    }

    @Override
    public void setGraphicsContext(GraphicsContext gc) {
        Objects.requireNonNull(gc);

        this.gc = gc;
    }

    @Override
    public void setLayer(ILayer layer) {
        Objects.requireNonNull(layer);

        this.layer.removeObserver(this);
        this.layer = layer;
        layer.addObserver(this);
    }

    @Override
    public void setCanvas(Canvas canvas) {
        Objects.requireNonNull(canvas);

        this.canvas = canvas;
    }

    @Override
    public void notify(Tuple2<Vector2D, Vector2D> data) {
        render(
                (int)data.getVal1().getX(),
                (int)data.getVal1().getY(),
                (int)data.getVal2().getX() + 1,
                (int)data.getVal2().getY() + 1
        );

        // We update the last rectangle too, so that no leftovers are visible on the view.
        // That is, if we were not to do this ghost images may be left on the view.
        if(lastUpdateRect != null)
            render(
                    (int)lastUpdateRect.getVal1().getX(),
                    (int)lastUpdateRect.getVal1().getY(),
                    (int)lastUpdateRect.getVal2().getX() + 1,
                    (int)lastUpdateRect.getVal2().getY() + 1
            );

        this.lastUpdateRect = data;
    }
}
