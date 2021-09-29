package C7.Model.Tools.StrokeInterpolation;

import C7.Model.Vector.Vector2D;

import java.util.List;

/**
 * A StrokeInterpolator approximates the {@link Vector2D, Vector2Ds} between two given
 * {@link Vector2D, Vector2Ds} with a given precision.
 *
 * @author Hugo Ekstrand
 */
public interface IStrokeInterpolator {

    /**
     * Interpolates a collection of points from the given points and returns the interpolated points and the given points.
     * The given points may be the same points. The returned points include the given points.
     * @param pointsPerDistance how many points should be interpolated per distance unit
     * @param points the given points to interpolate form.
     * @return the interpolated points including the given points.
     */
    List<Vector2D> interpolate(double pointsPerDistance, Vector2D...points);

}
