package C7.Model.Tools.StrokeInterpolation;

import C7.Model.Vector.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * LinearStrokeInterpolator is a {@link IStrokeInterpolator} which adds extra {@link Vector2D, Vector2Ds} to
 * an interval between two given {@link Vector2D, Vector2Ds} linearly. This means that every interpolated
 * {@link Vector2D, Vector2Ds} will be on the line between the two given {@link Vector2D, Vector2Ds}.
 *
 * @author Hugo Ekstrand
 */
public class LinearStrokeInterpolator implements IStrokeInterpolator {

    @Override
    public List<Vector2D> interpolate(Vector2D p1, Vector2D p2, double pointsPerDistance) {
        if(p1.equals(p2))
            return List.of(p1);

        int amountOfPointsToCreate = (int)(p1.sub(p2).len() * pointsPerDistance);

        Vector2D delta = p2.sub(p1).mult(1d/amountOfPointsToCreate);

        var points = IntStream.range(0, amountOfPointsToCreate + 1)
                .mapToObj(index -> p1.add(delta.mult(index)))
                .collect(Collectors.toList());
        return points;
    }
}
