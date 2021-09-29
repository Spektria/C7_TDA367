package C7.Model.Tools.Pattern;

import C7.Model.Vector.Vector2D;

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
     * @param scale the patterns scale.
     * @param rotation the rotation of the pattern in radians
     * @return the list of points for this pattern
     */
    Collection<Vector2D> getPoints(int size, Vector2D scale, double rotation);

}
