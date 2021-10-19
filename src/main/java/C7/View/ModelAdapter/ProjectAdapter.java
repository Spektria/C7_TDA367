package C7.View.ModelAdapter;

import C7.Model.IProject;
import C7.Util.*;

/**
 * Adapter for IProject to IRender.
 * @author Hugo Ekstrand
 */
class ProjectAdapter implements IRender {

    private final IProject proj;    // The instance being adapted

    ProjectAdapter(IProject proj){
        this.proj = proj;
    }

    @Override
    public Color[][] render(int x, int y, int width, int height) {
        // Project already has a method for doing the requested action,
        // so we just use that.
        return proj.renderProject(x,y,width, height);
    }

    @Override
    public void addObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        proj.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
        proj.removeObserver(observer);
    }

}
