package C7.Model.Tools;

import C7.Model.Layer.LayerFormat;
import C7.Util.Color;
import C7.Util.IObserver;
import C7.Model.Layer.ILayer;
import C7.Util.Tuple2;
import C7.Util.Vector2D;

/**
 * Mock class for ILayer implementation
 * The class does not support rotation, translation, or scaling.
 * It simply is a drawing surface in global space.
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
    public Color getGlobalPixel(int x, int y) {
        return ar[x][y];
    }

    @Override
    public Color getLocalPixel(int x, int y) {
        return ar[x][y];
    }

    @Override
    public void setGlobalPixel(int x, int y, Color color) {
        ar[x][y] = color;
    }

    @Override
    public void setLocalPixel(int x, int y, Color color) {
        if(isPixelOnLocalLayer(x, y))
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
    public boolean isPixelOnLocalLayer(int x, int y) {
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    @Override
    public void setRotation(double angle) {

    }

    @Override
    public double getRotation() {
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

    @Override
    public boolean isGlobalPointOnLayer(Vector2D point) {
        return true;
    }

    @Override
    public Vector2D toLocalPixel(Vector2D point) {
        return point;
    }

    @Override
    public Vector2D getLocalCenterPoint() {
        return null;
    }

    @Override
    public void update() {

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

    @Override
    public void addObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {

    }

    @Override
    public void removeObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {

    }

    @Override
    public LayerFormat getFormat() {
        return LayerFormat.RGBA32F32F32F32F;
    }
}
