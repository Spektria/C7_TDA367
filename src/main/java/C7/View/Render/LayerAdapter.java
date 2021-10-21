package C7.View.Render;

import C7.Model.Layer.ILayer;
import C7.Util.*;

/**
 * Adapter for ILayer to IRender.
 * @author Hugo Ekstrand
 */
class LayerAdapter implements IRender {

    private final ILayer layer;     // The instance being adapted

    LayerAdapter(ILayer layer){
        this.layer = layer;
    }

    @Override
    public Color[][] render(int x0, int y0, int width, int height) {
        if(width < 0 || height < 0)
            throw new IllegalArgumentException();

        Color[][] render = new Color[width][height];

        // Create 2d color array from given layer instance.
        // Do this in the requested area.
        for (int x = x0; x < width + x0; x++) {
            for (int y = y0; y < height + y0; y++) {

                // If the current (x,y) point is not on the layer,
                // we should return transparent since we have no data here.
                Color color = new Color(0,0,0,0);

                // If it isn't, that is the point is on the layer, we should return the data we have
                // at that point.
                if(layer.isGlobalPointOnLayer(new Vector2D(x,y)))
                    color = layer.getGlobalPixel(x,y);

                render[x - x0][y - y0] = color;
            }
        }

        return render;
    }

    @Override
    public void addObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        layer.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        layer.removeObserver(observer);
    }
}
