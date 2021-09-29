package C7.Model.Layer;

import C7.Model.Vector.Vector2D;

/**
 * @author Love Gustafsson
 * @version 1.0
 */
public class LayerManager implements ILayerManager {

	@Override
	public int createLayer(int width, int height, Vector2D position, double rotation, Vector2D scale) {
		// TODO
		return 0;
	}

	@Override
	public void destroyLayer(int layer) {
		// TODO
	}

	@Override
	public int getActiveLayerId() {
		// TODO
		return 0;
	}

	@Override
	public ILayer getLayer(int id) {
		// TODO
		return null;
	}

	@Override
	public int[] getAllLayerIds() {
		// TODO
		return new int[0];
	}
}
