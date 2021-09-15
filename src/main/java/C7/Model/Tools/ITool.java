package C7.Model.Tools;

import C7.ILayer;
import C7.Model.Vector.Vector2D;

/**
 * An ITool performs actions on a {@link ILayer}'s pixels.
 */
public interface ITool {

    void beginDraw(Vector2D pos);
    void move(Vector2D pos);
    void endDraw(Vector2D pos);
}
