package C7.Util;

public class Bitmap {
    private float[][] r;
    private float[][] g;
    private float[][] b;
    private float[][] a;

    int width, height;

    public Bitmap(int width, int height){
        r = new float[width][height];
        g = new float[width][height];
        b = new float[width][height];
        a = new float[width][height];

        this.width = width;
        this.height = height;
    }

    public int getWidth() {return width;}
    public int getHeight() {return height;}

    public void setColor(int x, int y, float rD, float gD, float bD, float aD){
        r[x][y] = rD;
        g[x][y] = gD;
        b[x][y] = bD;
        a[x][y] = aD;
    }

    public void setColor(int x, int y, Color color){
        r[x][y] = color.getRed();
        g[x][y] = color.getGreen();
        b[x][y] = color.getBlue();
        a[x][y] = color.getAlpha();
    }

    public Color getColor(Color color, int x, int y){
        color.setColor(r[x][y], g[x][y], b[x][y], a[x][y]);
        return color;
    }
}
