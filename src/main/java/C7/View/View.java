package C7.View;

import C7.Util.C7Math;
import C7.Util.IObserver;
import C7.Util.Tuple2;
import C7.Util.Vector2D;
import C7.View.Render.IRender;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * An {@link IView} implementation. This view renders data from an {@link IRender} object onto a graphics context.
 * The graphics context is to be set before usage of this class via the {@link #setGraphicsContext(GraphicsContext)}
 * otherwise this object will throw an exception. A width and height property needs to be set too before usage via
 * the {@link #setBounds(ReadOnlyDoubleProperty, ReadOnlyDoubleProperty)} method.
 * @author Hugo Ekstrand
 */
class View implements IView, IObserver<Tuple2<Vector2D, Vector2D>> {

    private IRender render;          // The render this view reads from
    private GraphicsContext gc;             // The graphics context this view draws onto

    private ReadOnlyDoubleProperty width;   // The width and height property of this view.
    private ReadOnlyDoubleProperty height;  // How large of a rectangle should this view draw?
                                            // This is given by these two properties.

    private boolean firstNotify = true;     // Keeps track of if the notify method has been called before.

    private Tuple2<Vector2D, Vector2D> lastUpdateRect;  // The last updated rectangle this view did.
                                                        // This is used so that the view can clean
                                                        // the last drawn spot so that there is no leftover pixels
                                                        // in the form of "ghosts".

    /**
     * Creates an instance of this type.
     * @param render the object which pixel data will be fetched from this object will read from.
     */
    View(IRender render){
        Objects.requireNonNull(render);
        this.render = render;

        render.addObserver(this);
    }

    @Override
    public void render() {
        Objects.requireNonNull(height);
        Objects.requireNonNull(width);

        render(0, 0, (int)width.get(), (int)height.get());
    }

    private Color toJFXColor(C7.Util.Color color){
        if(color == null)
            return Color.TRANSPARENT;
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    @Override
    public void render(int x0, int y0, int x1, int y1) {
        Objects.requireNonNull(gc);

        int xMin = C7Math.limit(x0, 0, this.width.intValue());
        int yMin = C7Math.limit(y0, 0, this.height.intValue());
        int xMax = C7Math.limit(x1, 0, this.width.intValue());
        int yMax = C7Math.limit(y1, 0, this.height.intValue());

        //Get color data from Project
        int width = xMax - xMin;
        int height = yMax - yMin;
        C7.Util.Color[][] colorMatrix = render.render(xMin, yMin, width, height);

        // Simply goes inside the given bounds and
        // draws onto the graphics context pixel by pixel.
        PixelWriter pw = gc.getPixelWriter();
        for (int y = yMin; y < yMax; y++) {
            for (int x = xMin; x < xMax; x++) {
                // Note, we need to change the color type from C7 color to JavaFX color.
                pw.setColor(x, y, toJFXColor(colorMatrix[x - xMin][y - yMin]));
            }
        }
    }

    @Override
    public void setGraphicsContext(GraphicsContext gc) {
        Objects.requireNonNull(gc);
        this.gc = gc;
    }

    @Override
    public void setIRenderSource(IRender render) {
        Objects.requireNonNull(render);
        this.render = render;
        this.render.addObserver(this);
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

        // Render only what is in the canvas. That is, any value under 0 or above the view's width
        // should be ignored.
        render(
                (int)data.getVal1().getX(),
                (int)data.getVal1().getY(),
                (int)data.getVal2().getX() + 1,
                (int)data.getVal2().getY() + 1
        );


        // We update the last rectangle too, so that no leftovers are visible on the view.
        // That is, if we were not to do this ghost images may be left on the view.
        // Before that however, we need to check that this is not the first call to this method.
        // If it is, we should re-render the whole view since we do not know where any potential leftover may be.
        if(firstNotify){
            render();
            firstNotify = false;
        }
        else
            render(
                    (int)lastUpdateRect.getVal1().getX(),
                    (int)lastUpdateRect.getVal1().getY(),
                    (int)lastUpdateRect.getVal2().getX() + 1,
                    (int)lastUpdateRect.getVal2().getY() + 1
            );


        this.lastUpdateRect = data;
    }
}
