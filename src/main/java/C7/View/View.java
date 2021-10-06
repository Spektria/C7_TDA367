package C7.View;

import C7.Model.IObserver;
import C7.Model.Layer.ILayerManager;
import C7.Util.Tuple2;
import C7.Util.Vector2D;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * An {@link IView} implementation. This view renders data from a LayerManager model object onto a graphics context.
 * The graphics context is to be set before usage of this class via the {@link #setGraphicsContext(GraphicsContext)}
 * otherwise this object will throw an exception. A width and heigth property needs to be set too before usage via
 * the {@link #setBounds(ReadOnlyDoubleProperty, ReadOnlyDoubleProperty)} method.
 * @author Hugo Ekstrand
 */
class View implements IView, IObserver<Tuple2<Vector2D, Vector2D>> {

    private final ILayerManager manager;    // The model this view reads from
    private GraphicsContext gc;             // The graphics context this view draws onto

    private ReadOnlyDoubleProperty width;   // The width and height property of this view.
    private ReadOnlyDoubleProperty height;  // How large of a rectangle should this view draw?
                                            // This is given by these two properties.

    private Tuple2<Vector2D, Vector2D> lastUpdateRect;  // The last updated rectangle this view did.
                                                        // This is used so that the view can clean
                                                        // the last drawn spot so that there is no leftover pixels
                                                        // in the form of "ghosts".

    /**
     * Creates an instance of this type.
     * @param manager the LayerManager this object will read from.
     */
    View(ILayerManager manager){
        Objects.requireNonNull(manager);
        this.manager = manager;

        manager.addObserver(this);
    }

    @Override
    public void render() {
        Objects.requireNonNull(height);
        Objects.requireNonNull(width);

        render(0, 0, (int)width.get(), (int)height.get());
    }

    private Color toJFXColor(C7.Model.Color color){
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void render(int x0, int y0, int width, int height) {
        Objects.requireNonNull(gc);

        // Simply goes inside the given bounds and
        // draws onto the graphics context pixel by pixel.
        PixelWriter pw = gc.getPixelWriter();
        for (int y = y0; y < height; y++) {
            for (int x = x0; x < width; x++) {
                // Note, we need to change the color type from C7 color to JavaFX color.
                pw.setColor(x, y, toJFXColor(manager.getPixel(x, y)));
            }
        }
    }

    @Override
    public void setGraphicsContext(GraphicsContext gc) {
        Objects.requireNonNull(gc);
        this.gc = gc;
    }

    @Override
    public void setBounds(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        Objects.requireNonNull(width);
        Objects.requireNonNull(height);
        this.width = width;
        this.height = height;
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
