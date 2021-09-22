package C7.Model.Tools;

import C7.Model.Color;
import C7.Model.Layer.ILayer;
import C7.Model.Vector.Vector2D;

/**
 * Mock class for ILayer implementation
 * @author Hugo Ekstrand
 */
public class TestISurfaceImpl implements ILayer {
    private Color[][] ar;
    private Vector2D scale;

    TestISurfaceImpl(int width, int height, Vector2D scale){
        ar = new Color[width][height];
        this.scale = scale;

        for (int i = 0; i < ar.length; i++) {
            for (int j = 0; j < ar[i].length; j++) {
                ar[i][j] = getBaseColor();
            }
        }
    }


    @Override
    public Color getPixel(int x, int y) {
        return ar[x][y];
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        if(isPixelOnLayer(x, y))
            ar[x][y] = color;
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
    public void setHeight(int height) {

    }

    @Override
    public Boolean isPixelOnLayer(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    @Override
    public void setRotation(float angle) {

    }

    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public void setPosition(Vector2D position) {

    }

    @Override
    public Vector2D getPosition() {
        return null;
    }

    @Override
    public void setScale(Vector2D scale) {

    }

    @Override
    public int getWidth() {
        return ar.length;
    }

    @Override
    public void setWidth(int width) {

    }

    public String getContentAs2DString(){
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < ar.length; x++) {
            for (int y = 0; y < ar[x].length; y++) {
                sb.append(ar[x][y].equals(getBaseColor()) ? "-" : "#").append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Color getBaseColor() {
        return new Color(0,0,0,0);
    }
}
