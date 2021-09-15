package C7.Model.Tools;

import C7.Model.Vector.Vector2D;

/**
 * An ITool performs actions on a {@link C7.Layer.ILayer}'s pixels. A tool can be used over a time period
 * and should thus, when the position of the tool's usage is changed, be informed when it has moved.
 * It should also be informed when it stops being used.
 * @author Hugo Ekstrand
 */
public interface ITool {

    /**
     * Informs the tool it should start affecting the given start position.
     * @param pos the position
     */
    void beginDraw(Vector2D pos);

    /**
     * Informs the tools of its new position.
     * @param pos the position it has moved to
     */
    void move(Vector2D pos);

    /**
     * Informs the tool it should stop being used.
     * @param pos the position where the tool is ceasing usage.
     */
    void endDraw(Vector2D pos);
}
