package C7.View;

import C7.Model.Layer.ILayer;
import C7.Model.Vector.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class View implements IView {

    @Override
    public void updateCanvas(GraphicsContext gc, ILayer layer, int x, int y, int width, int height) {
        PixelWriter pw = gc.getPixelWriter();
        for (int i = y; i < height; i++) {
            for (int j = x; j < width; j++) {
                C7.Model.Color color = new C7.Model.Color(0, 0, 0, 0);
                if(layer.isPointOnLayer(new Vector2D(j, i)))
                    color = layer.getLocalPixel(j, i);

                //Update to take into account canvas transform
                pw.setColor(j, i, new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
            }
        }
    }
}
