package C7.Model.Tools;

import C7.Color;

public abstract class Brush implements ITool {

    protected final int strokeSize;
    protected final Color color;


    protected Brush(int strokeSize, Color color) {
        this.strokeSize = strokeSize;
        this.color = color;
    }


}
