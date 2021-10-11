package C7.Model.Tools.Pattern;

import C7.Util.Vector2D;

import java.util.Collection;

/**
 * An IPattern is a strategy which calculates a collection of points by a given size, scale, and rotation.
 * It may be a circle, star, or any other pattern which may be described with discreet points in 2d space.
 * The pattern is centered at (0,0) and should thus be translated if one doesn't want the points at this position.
 * @author Hugo Ekstrand
 */
public interface IPattern {

    /**
     * Returns a list of discreet points for this pattern.
     * The patterns shape may be modified by its xy-scale, size, or rotation.
     * @param size the size of the pattern's diameter.
     *             Or if the shape doesn't have a diameter it is
     *             the largest distance between two points in the shape.
     * @param scale the patterns scale.
     * @param rotation the rotation of the pattern in radians
     * @return the list of points for this pattern centered at origo (0,0).
     */
    Collection<Vector2D> getPoints(int size, Vector2D scale, double rotation);

}
