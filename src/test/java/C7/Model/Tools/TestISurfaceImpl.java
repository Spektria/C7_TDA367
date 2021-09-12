package C7.Model.Tools;

import C7.Color;
import C7.ISurface;

public class TestISurfaceImpl implements ISurface {
    private Color[][] ar;

    TestISurfaceImpl(int width, int height){
        ar = new Color[width][height];
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
