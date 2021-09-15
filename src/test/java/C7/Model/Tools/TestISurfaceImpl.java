package C7.Model.Tools;

import C7.Color;
import C7.ILayer;
import C7.Model.Vector.Vector2D;

public class TestISurfaceImpl implements ILayer {
    private Color[][] ar;
    private Vector2D scale;

    TestISurfaceImpl(int width, int height, Vector2D scale){
        ar = new Color[width][height];
        this.scale = scale;
    }


    @Override
    public Color getPixel(int x, int y) {
        return ar[x][y];
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        ar[x][y] = color;
    }

    @Override
    public boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    @Override
    public Vector2D getScale() {
        return scale;
    }

    @Override
    public int getHeight() {
        return ar[0].length;
    }

    @Override
    public int getWidth() {
        return ar.length;
    }

    public String getContentAs2DString(){
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < ar.length; x++) {
            for (int y = 0; y < ar[x].length; y++) {
                sb.append(ar[x][y] == null ? "-" : "#").append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
