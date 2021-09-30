package C7.Model.Layer;

import C7.Model.Color;
import C7.Model.Vector.Vector2D;

import java.util.*;

/**
 * @author Love Gustafsson
 * @version 1.0
 */
public class LayerManager implements ILayerManager {

	private List<Map.Entry<Integer, ILayer>> layers;	// Collection of layers managed this layer manager.
	private int nextId;						// ID to assign to the next created layer.

	public LayerManager() {
		layers		= new ArrayList<>();
		nextId		= 1;
	}

	@Override
	public int createLayer(int width, int height, Vector2D position, double rotation, Vector2D scale) {
		// Create and initialize layer object
		ILayer layer = new Layer(width, height, new Color(0, 0, 0, 0));
		layer.setPosition(position);
		layer.setRotation((float)rotation);
		layer.setScale(scale);

		// Add layer to manager
		layers.add(new AbstractMap.SimpleEntry<Integer, ILayer>(nextId, layer));

		return nextId++;
	}

	@Override
	public void destroyLayer(int id) {
		// Remove the entry with matching key
		layers.removeIf(entry -> entry.getKey().equals(id));
	}

	@Override
	public int getActiveLayerId() {
		// TODO
		return 0;
	}

	@Override
	public ILayer getLayer(int id) {
		// Find the layer with the associated ID.
		for (Map.Entry<Integer, ILayer> entry : layers) {
			// Return the layer with matching ID.
			if (entry.getKey().equals(id)) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Override
	public int[] getAllLayerIds() {
		// TODO
		return new int[0];
	}
}
