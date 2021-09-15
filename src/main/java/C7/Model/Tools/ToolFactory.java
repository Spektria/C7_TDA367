package C7.Model.Tools;

import C7.Color;
import C7.ILayer;

public class ToolFactory {
    public static ITool CreateCircularBrush(ILayer layer, int strokeSize, Color color){
        BaseBrush base = new BaseBrush(layer, new DiskDrawEffect(strokeSize, color));
        return base;
    }

    public static ITool CreateCalligraphyBrush(ILayer layer, int strokeSize, Color color, double angle){
        BaseBrush base = new BaseBrush(layer, new CalligraphyDrawEffect(strokeSize, color, angle));
        return base;
    }

    public static ITool CreateFillBucket(ILayer layer, Color fill, int threshold){
        return new FillBucket(layer, fill, threshold);
    }
}
