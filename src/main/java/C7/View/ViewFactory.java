package C7.View;

import C7.Model.Layer.ILayerManager;
import C7.Model.Project;

/**
 * Factory for the creation of instances of {@link IView}.
 */
public final class ViewFactory {

    /**
     * Creates an instance of a {@link IView}, given a {@link C7.Model.Layer.LayerManager, LayerManager}..
     * @param manager the given manager which the view will fetch render data from.
     *                It is this object which will be rendered by the view.
     * @return the view instance
     */
    public static IView createView(Project project){
        return new View(project);
    }
}
