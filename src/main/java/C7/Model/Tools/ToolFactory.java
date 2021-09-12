package C7.Model.Tools;

import C7.Color;

public class ToolFactory {
    public static ITool CreateCircularBrush(int strokeSize, Color color){
        return new CircularBrush(strokeSize, color);
    }


}
