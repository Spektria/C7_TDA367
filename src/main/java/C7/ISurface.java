package C7;

public interface ISurface {
    public Color getPixel(int x, int y);
    public void setPixel(int x, int y, Color color);
    public int getHeight();
    public int getWidth();
}
