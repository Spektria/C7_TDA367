package C7.Model.Tools.StrokeInterpolation;

import C7.Model.Vector.Vector2D;

import java.util.List;

/**
 * A StrokeInterpolator approximates the {@link Vector2D, Vector2Ds} between two given
 * {@link Vector2D, Vector2Ds} with a given precision.
 */
public interface StrokeInterpolator {

    List<Vector2D> interpolate(Vector2D p1, Vector2D p2, double pointsPerDistance);

}
