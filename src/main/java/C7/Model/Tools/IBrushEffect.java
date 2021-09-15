package C7.Model.Tools;

import C7.Color;
import C7.Layer.ILayer;
import C7.Model.Vector.Vector2D;


import java.util.Map;

/**
 * Represents an effect which could done on a collection of pixels in a brush.
 * @author Hugo Ekstrand
 */
interface IBrushEffect {

    /**
     * Perform this IBrushEffect's effect on the given collection of pixels. The effect is
     * may be affected by pixel data sounding the epicenter of the effect. The surrounding data
     * is given as a {@link ILayer layer}.
     * @param surface the surface data
     * @param center the epicenter of the effect
     * @param pixels the pixels the effect should affect
     * @return
     */
    Map<Vector2D, Color> handle(ILayer surface, Vector2D center, Map<Vector2D, Color> pixels);
}
