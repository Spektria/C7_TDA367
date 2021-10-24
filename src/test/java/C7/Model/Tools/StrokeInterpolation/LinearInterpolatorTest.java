package C7.Model.Tools.StrokeInterpolation;

import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Hugo Ekstrand
 */
public class LinearInterpolatorTest {

    public static Stream<Arguments> interpolationPointCountTestArgs() {
        return Stream.of(
                Arguments.of(new Vector2D(0, 0), new Vector2D(1,0), 1, 2),
                Arguments.of(new Vector2D(0, 0), new Vector2D(0, 5), 1, 6),

                // Distance between (5,5) and (10,10) is sqrt((10-5)^2 + (10-5)^2) = sqrt(25 + 25) = sqrt(50)
                // We have 2.3 points per unit, thus there should be sqrt(50) * 2.3 + 1
                Arguments.of(new Vector2D(5, 5), new Vector2D(10, 10), 2.3d, (int)(Math.sqrt(50) * 2.3d) + 1)
        );
    }

    @ParameterizedTest
    @MethodSource("interpolationPointCountTestArgs")
    public void interpolationPointsCountTest(Vector2D v0, Vector2D v1, double frequency, int expectedLength){
        IStrokeInterpolator interpolator = StrokeInterpolatorFactory.createLinearInterpolator();
        Collection<Vector2D> points = interpolator.interpolate(frequency, v0, v1);
        Assertions.assertEquals(0, Double.compare(points.size(), expectedLength));
    }

    public static Stream<Arguments> interpolationPointTestArgs() {
        return Stream.of(
                Arguments.of(new Vector2D(0, 0), new Vector2D(0, 5), 1,
                        Arrays.asList(
                                new Vector2D(0, 0), new Vector2D(0, 1),
                                new Vector2D(0, 2), new Vector2D(0, 3),
                                new Vector2D(0, 4), new Vector2D(0, 5))),
                Arguments.of(new Vector2D(1, 1), new Vector2D(5, 4), 0.5d,
                        Arrays.asList(
                                new Vector2D(1,1),
                                new Vector2D(1,1).add(new Vector2D(4d/((int)(5d * 0.5d)), 3d/((int)(5d * 0.5d))).mult(1)),
                                new Vector2D(5,4)))
        );
    }


    @ParameterizedTest
    @MethodSource("interpolationPointTestArgs")
    public void interpolationPointsTest(Vector2D v0, Vector2D v1, double frequency, Collection<Vector2D> expectedPoints){
        IStrokeInterpolator interpolator = StrokeInterpolatorFactory.createLinearInterpolator();
        Collection<Vector2D> points = interpolator.interpolate(frequency, v0, v1);

        Assertions.assertEquals(expectedPoints, points);
    }

}
