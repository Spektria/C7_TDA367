package C7.Model.Tools.StrokeInterpolation;

import C7.Util.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * LinearStrokeInterpolator is a {@link IStrokeInterpolator} which adds extra {@link Vector2D , Vector2Ds} to
 * an interval between two given {@link Vector2D, Vector2Ds} linearly. This means that every interpolated
 * {@link Vector2D, Vector2Ds} will be on the line between the two given {@link Vector2D, Vector2Ds}.
 *
 * @author Hugo Ekstrand
 */
class LinearStrokeInterpolator implements IStrokeInterpolator {

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

    @Override
    public List<Vector2D> interpolate(double pointsPerDistance, Vector2D... points) {
        Objects.requireNonNull(points);

        if(points.length == 1)
            return List.of(points[0]);
        else {
            ArrayList<Vector2D> interpolatedPoints = new ArrayList<>();

            for(int i = 1; i < points.length; i++){
                interpolatedPoints.addAll(interpolate(points[i - 1], points[i], pointsPerDistance));
            }
            return interpolatedPoints;
        }
    }
}
