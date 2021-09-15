package C7;

import C7.Model.Vector.Vector2D;

public interface ILayer {
    Color getPixel(int x, int y);
    void setPixel(int x, int y, Color color);

    boolean isInBounds(int x, int y);
    Vector2D getScale();

    int getHeight();
    int getWidth();
}
