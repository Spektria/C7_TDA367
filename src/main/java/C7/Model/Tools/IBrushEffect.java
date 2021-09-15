package C7.Model.Tools;

import C7.Color;
import C7.ILayer;
import C7.Model.Vector.Vector2D;


import java.util.Map;

public interface IBrushEffect {
    Map<Vector2D, Color> handle(ILayer surface, Vector2D center, Map<Vector2D, Color> pixels);
}
