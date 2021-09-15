package C7.Model.Tools.StrokeInterpolation;

import C7.Model.Vector.*;

import java.util.List;
import java.util.stream.IntStream;


public class LinearStrokeInterpolator implements StrokeInterpolator{

    @Override
    public List<Vector2D> interpolate(Vector2D p1, Vector2D p2, double pointsPerDistance) {
        if(p1.equals(p2))
            return List.of(p1);

        int amountOfPointsToCreate = (int)(p1.sub(p2).len()/pointsPerDistance);

        Vector2D delta = p1.sub(p2).mult(1d/amountOfPointsToCreate);

        var points = IntStream.range(0, amountOfPointsToCreate + 1)
                .mapToObj(index -> p1.add(delta.mult(index)))
                .toList();
        return points;
    }
}
