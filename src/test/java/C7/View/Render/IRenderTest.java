package C7.View.Render;

import C7.Model.IProject;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.LayerFactory;
import C7.Model.ProjectFactory;
import C7.Util.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class IRenderTest {

    public static Stream<Arguments> params() {
        ILayer layer = LayerFactory.createDefaultLayer(100, 150, Color.BLUE);
        IProject proj = ProjectFactory.createProject("tst",200,200);
        proj.addLayer(layer);
        layer.setLocalPixel(52, 50, Color.BLACK);

        return Stream.of(
                Arguments.of(RenderAdapterFactory.createAdapter(layer)),
                Arguments.of(RenderAdapterFactory.createAdapter(proj))
        );
    }

    @ParameterizedTest
    @MethodSource("params")
    public void adaptionTest(IRender render){

        Color[][] colors = render.render(49, 49, 50, 55);

        Assertions.assertEquals(Color.BLACK, colors[3][1]);
        Assertions.assertEquals(Color.BLUE, colors[2][1]);
        Assertions.assertEquals(50, colors.length);
        Assertions.assertEquals(55, colors[0].length);
    }

    @ParameterizedTest
    @MethodSource("params")
    public void adaptionArgumentTest(IRender render){

        // Should not be able to render at pixels that are negative
        Assertions.assertThrows(IllegalArgumentException.class, () -> render.render(5,1,-1,1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> render.render(5,1,1,-1));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void adaptorOutsideBoundsTest(IRender render){
        Color[][] colors = render.render(200, 200, 2, 2);

        // Anything outside the layer should be transparent since there is no data there.
        Assertions.assertEquals(new Color(0,0,0,0), colors[0][0]);
    }

}
