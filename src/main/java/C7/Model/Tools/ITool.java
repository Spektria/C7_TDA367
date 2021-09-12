package C7.Model.Tools;

import C7.ISurface;

/**
 * An ITool performs actions on a {@link ISurface}'s pixels.
 */
public interface ITool {

    /**
     * Performs an action on the given coordinates on the given 2d surface.
     * @param x the x coordinate in the 2d plane
     * @param y the y coordinate in the 2d plane
     * @param surface the plane which is being worked on.
     */
    void draw(int x, int y, ISurface surface);
}
