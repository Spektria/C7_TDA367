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
    public Bitmap render(int x0, int y0, int width, int height) {
        if(width < 0 || height < 0)
            throw new IllegalArgumentException();

        Bitmap render = new Bitmap(width,height);

        Color pixelColor = new Color(0,0,0,0);

        // Create 2d color array from given layer instance.
        // Do this in the requested area.
        for (int x = x0; x < width + x0; x++) {
            for (int y = y0; y < height + y0; y++) {
                if(layer.isGlobalPointOnLayer(new Vector2D(x,y)))
                    layer.getGlobalPixel(x,y, pixelColor);
                else pixelColor.setColor(0,0,0,0);

                render.setColor(x - x0, y - y0, pixelColor);
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
