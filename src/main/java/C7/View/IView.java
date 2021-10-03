package C7.View;

import C7.Model.Layer.ILayer;
import javafx.scene.canvas.GraphicsContext;

public interface IView {

    public void updateCanvas(GraphicsContext gc, ILayer layer, int x, int y, int width, int height);
}
