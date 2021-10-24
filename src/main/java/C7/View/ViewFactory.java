package C7.View;

import C7.Model.IProject;
import C7.Model.Layer.ILayer;
import C7.View.Render.RenderAdapterFactory;

/**
 * Factory for the creation of instances of {@link IView}.
 */
public final class ViewFactory {

    /**
     * Creates an instance of a {@link IView}, given an {@link IProject}.
     * @param project the given project which the view will fetch render data from.
     *                It is this object which will be rendered by the view.
     * @return the view instance
     */
    public static IView createView(IProject project){
        return new View(RenderAdapterFactory.createAdapter(project));
    }

    /**
     * Creates an instance of a {@link IView}, given an {@link ILayer}.
     * @param layer the given layer which the view will fetch render data from.
     *              It is this object which will be rendered by the view.
     * @return the view instance.
     */
    public static IView createView(ILayer layer){
        return new View(RenderAdapterFactory.createAdapter(layer));
    }
}
