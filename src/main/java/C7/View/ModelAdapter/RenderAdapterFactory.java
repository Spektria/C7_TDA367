package C7.View.ModelAdapter;

import C7.Model.IProject;
import C7.Model.Layer.ILayer;

/**
 * Factory for adapting classes to IRender instances.
 *
 * @author Hugo Ekstrand
 */
public final class RenderAdapterFactory {

    /**
     * Creates an IRender from a IProject.
     * @param proj the project to be adapted
     * @return the IRender instance
     */
    public static IRender createAdapter(IProject proj){
        return new ProjectAdapter(proj);
    }

    /**
     * Creates an IRender from an ILayer.
     * @param layer the layer to be adapted
     * @return the IRender instance
     */
    public static IRender createAdapter(ILayer layer){
        return new LayerAdapter(layer);
    }
}
