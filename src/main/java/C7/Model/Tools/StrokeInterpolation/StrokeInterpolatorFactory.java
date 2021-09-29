package C7.Model.Tools.StrokeInterpolation;

/**
 * Creates instances of {@link IStrokeInterpolator}.
 * @author Hugo Ekstrand
 */
public final class StrokeInterpolatorFactory {

    /**
     * Creates an instance of a linear interpolator. The interpolator
     * simply draws a straight line between the given points as pairs and
     * interpolates points on that line.
     * @return the linear interpolator.
     */
    public static IStrokeInterpolator createLinearInterpolator(){
        return new LinearStrokeInterpolator();
    }

}
