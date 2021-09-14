package C7;

import C7.Layer.ILayer;

import java.util.ArrayList;

/**
 * Basic representation of a project for the purpose of testing functionalities. Not final
 */
public class Project {
    ArrayList<ILayer> layers;

    public Project() {
        layers = new ArrayList<>();
    }

    /**
     * @return An ordered list of the layers in the project, from furthest back to furthest forward
     */
    ArrayList<ILayer> getLayers() {
        ArrayList out = new ArrayList();
        for (ILayer layer:
             layers) {
            out.add(layer);
        }
        return out;
    }

    public Color getPixel(int x, int y, int layerIndex) {
        return layers.get(layerIndex).getPixel(x, y);
    }

    public Color getPixel(int x, int y){
        Color out = new Color(1, 1, 1, 0);
        for (ILayer layer:
             layers) {
            out = blend(layer.getPixel(x, y), out);
        }
        return out;
    }

    public void setPixel(int x, int y, int layerIndex, Color color) {
        layers.get(layerIndex).setPixel(x, y, color);
    }

    public Color blend (Color a, Color b){
        //Probably move this somewhere else later
        //Oops flattened alpha B)
        //Gonna have to look at this when I can muster the effort or have someone else do it
        Color c;
        if(a.getAlpha() > 0) c = a;
        else c = b;
        return c;
    }
}
