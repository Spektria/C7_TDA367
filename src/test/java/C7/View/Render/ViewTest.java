package C7.View.Render;

import C7.Model.Layer.ILayer;
import C7.Model.Layer.LayerFactory;
import C7.Util.Color;
import C7.View.IView;
import C7.View.ViewFactory;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Hugo Ekstrand
 */
public class ViewTest {

    @Test
    public void viewTestRequirermentsTest(){
        ILayer layer = LayerFactory.createDefaultLayer(10, 10, Color.RED);
        IView view = ViewFactory.createView(layer);
        Canvas canvas = new Canvas();

        Assertions.assertThrows(NullPointerException.class, view::render);

        view.setGraphicsContext(canvas.getGraphicsContext2D());
        Assertions.assertThrows(NullPointerException.class, view::render);

        view.setBounds(canvas.widthProperty(), canvas.heightProperty());

        view.render();
    }

    @Test
    public void setNullGCTest(){
        ILayer layer = LayerFactory.createDefaultLayer(10, 10, Color.RED);
        IView view = ViewFactory.createView(layer);

        Assertions.assertThrows(NullPointerException.class, () -> view.setGraphicsContext(null));
    }

    @Test
    public void setNullBoundsTest(){
        ILayer layer = LayerFactory.createDefaultLayer(10, 10, Color.RED);
        IView view = ViewFactory.createView(layer);

        Assertions.assertThrows(NullPointerException.class, () -> view.setBounds(null, null));
    }

    @Test
    public void setNullRenderTest(){
        ILayer layer = LayerFactory.createDefaultLayer(10, 10, Color.RED);
        IView view = ViewFactory.createView(layer);

        Assertions.assertThrows(NullPointerException.class, () -> view.setIRenderSource(null));
    }
}
