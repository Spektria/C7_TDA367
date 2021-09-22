package C7.Model.Tools;

import C7.Model.Layer.ILayer;
import C7.Model.Tools.ToolProperties.IToolProperty;
import C7.Model.Vector.Vector2D;

import java.util.Collection;

/**
 * An ITool performs actions on a {@link C7.Model.Layer.ILayer}.
 * @author Hugo Ekstrand
 */
public interface ITool {

    /**
     * Returns this Tools properties.
     * @return this Tools properties.
     */
    Collection<IToolProperty> getProperties();

    /**
     * Applies this Tools effect on a given layer at a given position.
     * <p>
     *     Precondition: v0, v1, and layer mustn't be null.
     *     Postcondition: layer may have been affected.
     * </p>
     * @param v0 start position
     * @param v1 end position
     * @param layer the affected layer
     */
    void apply(ILayer layer, Vector2D v0, Vector2D v1);

    /**
     * Returns true if this Tool requires continuous positional input,
     * via the {@link ITool#apply(ILayer, Vector2D, Vector2D), apply} method.
     * If it doesn't the Tool should be applied once to do its whole effect.
     * For example, a brush needs a lot of inputs to draw a curvy line while a
     * straight line only requires 2 positions.
     * @return if this Tool is continuous.
     */
    boolean isContinuous();

}
