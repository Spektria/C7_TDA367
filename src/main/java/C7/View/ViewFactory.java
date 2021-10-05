package C7.View;

import C7.Model.Layer.ILayer;
import javafx.scene.canvas.GraphicsContext;

/**
 * Factory for the creation of instances of {@link IView}.
 */
public final class ViewFactory {

    /**
     * Creates an instance of a {@link IView}, given a layer and graphics context.
     * @param layer the given layer which the view will render
     * @return the view instance
     */
    public static IView createView(ILayer layer){
        return new View(layer);
    }
}
