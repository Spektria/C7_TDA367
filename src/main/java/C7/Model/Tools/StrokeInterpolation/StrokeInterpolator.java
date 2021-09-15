package C7.Model.Tools.StrokeInterpolation;

import C7.Model.Vector.Vector2D;

import java.util.List;

/**
 * A StrokeInterpolator approximates the continuous values between a series discrete points.
 */
public interface StrokeInterpolator {

    List<Vector2D> interpolate(Vector2D p1, Vector2D p2, double pointsPerDistance);

}
