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

public class View implements IView, IObserver<Tuple2<Vector2D, Vector2D>> {

    private ILayer layer;
    private GraphicsContext gc;
    private Tuple2<Vector2D, Vector2D> lastUpdateRect;
    private Canvas canvas;

    public View(ILayer layer){
        Objects.requireNonNull(layer);
        this.layer = layer;
    }

    @Override
    public void render() {
        render(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
    }

    @Override
    public void render(int x, int y, int width, int height) {
        Objects.requireNonNull(gc);

        PixelWriter pw = gc.getPixelWriter();
        for (int i = y; i < height; i++) {
            for (int j = x; j < width; j++) {
                C7.Model.Color color = new C7.Model.Color(0, 0, 0, 0);

                if(layer.isPointOnLayer(new Vector2D(j, i)))
                    color = layer.getLocalPixel(j, i);

                //Update to take into account canvas transform
                pw.setColor(j, i, new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
            }
        }
    }

    @Override
    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void notify(Tuple2<Vector2D, Vector2D> data) {
        render(
                (int)data.getVal1().getX(),
                (int)data.getVal1().getY(),
                (int)data.getVal2().getX(),
                (int)data.getVal2().getY()
        );
    }
}
