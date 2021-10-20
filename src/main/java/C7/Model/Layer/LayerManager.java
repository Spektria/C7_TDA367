package C7.Model.Layer;

import C7.Util.Color;
import C7.Util.IObserver;
import C7.Util.Tuple2;
import C7.Util.Vector2D;

import java.io.Serializable;
import java.util.*;

/**
 * @author Love Gustafsson
 * @version 1.1
 */
public class LayerManager implements ILayerManager, IObserver<Tuple2<Vector2D, Vector2D>>, Serializable {

	final private List<Map.Entry<Integer, ILayer>> layers;	// Collection of layers managed this layer manager.
	private int nextId;										// ID number to assign to the next created layer.
	private int activeLayerId;								// The ID number of the currently active layer.
	private transient Collection<IObserver<Tuple2<Vector2D, Vector2D>>> observers;	// Update area observers

	public LayerManager() {
		layers			= new ArrayList<>();
		nextId			= 1;
		activeLayerId	= 0;
		observers		= new ArrayList<>();
	}

	@Override
	public void addObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver<Tuple2<Vector2D, Vector2D>> observer) {
		observers.remove(observer);
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

		layer.addObserver(this);

		return nextId++;
	}

	@Override
	public int addLayer(ILayer layer) {
		// Check if this layer already exists in this manager
		for (Map.Entry<Integer, ILayer> entry : layers) {
			if (entry.getValue() == layer) {
				return entry.getKey();
			}
		}

		// Add layer to manager
		layers.add(new AbstractMap.SimpleEntry<Integer, ILayer>(nextId, layer));

		// Observe layer
		layer.addObserver(this);

		return nextId++;
	}

	@Override
	public void destroyLayer(int id) {
		// Remove the entry with matching key
		layers.removeIf(entry -> entry.getKey().equals(id));
	}

	@Override
	public int getActiveLayerId() {
		return activeLayerId;
	}

	@Override
	public void setActiveLayer(int id) {
		for (Map.Entry<Integer, ILayer> entry : layers) {
			if (entry.getKey().equals(id)) {
				activeLayerId = id;
				return;
			}
		}
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
		// Create integer array to hold all IDs
		int[] ids = new int[layers.size()];
		int i = 0;

		// Iterate over layers and add all IDs to array
		for (Map.Entry<Integer, ILayer> entry : layers) {
			ids[i++] = entry.getKey();
		}

		return ids;
	}

	@Override
	public Color getPixel(int x, int y) {
		Color color = new Color(0, 0, 0, 0); // Final color

		// Find colors at center of current pixel
		Vector2D point = new Vector2D(x + 0.5, y + 0.5);

		for (Map.Entry<Integer, ILayer> entry : layers) {
			ILayer layer = entry.getValue();

			if (layer.isGlobalPointOnLayer(point)) {
				Vector2D pixelPos = layer.toLocalPixel(point);

				// Layer color to blend with
				Color blendColor = layer.getLocalPixel((int)pixelPos.getX(), (int)pixelPos.getY());

				// Blend colors
				color = Color.blend(color, blendColor);
			}
		}

		return color;
	}

	@Override
	public void setLayerIndex(int id, int index) {
		// Find the index with the specified id
		for (Map.Entry<Integer, ILayer> entry : layers) {
			if (entry.getKey() == id) {
				int currentIndex = layers.indexOf(entry);

				// If we're already in the right index, do nothing
				if (currentIndex == index) {
					return;
				}

				// Calculate new index
				int newIndex = index;
				if (newIndex < 0)
					newIndex = 0;
				if (newIndex >= layers.size())
					newIndex = layers.size() - 1;

				// Remove from layers
				layers.remove(entry);

				// Add at index
				layers.add(newIndex, entry);

				return;
			}
		}
	}

	@Override
	public void notify(Tuple2<Vector2D, Vector2D> data) {
		for (IObserver<Tuple2<Vector2D, Vector2D>> observer : observers) {
			observer.notify(data);
		}
	}

	//Gets called after deserialization,
	//currently uses default deserialization and then connects observers.
	private Object readResolve(){
		observers = new ArrayList<>();

		for (int i = 0; i < layers.size(); i++) {
			ILayer layer = layers.get(i).getValue();
			//layer.removeObserver(this);
			layer.addObserver(this);
		}
		return this;
	}
}
