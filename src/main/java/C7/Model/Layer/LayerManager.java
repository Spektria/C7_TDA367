package C7.Model.Layer;

import C7.Util.Color;
import C7.Util.IObserver;
import C7.Util.Tuple2;
import C7.Util.Vector2D;
import jdk.jshell.spi.ExecutionControl;

import java.io.Serializable;
import java.nio.channels.NotYetBoundException;
import java.util.*;

/**
 * @author Love Gustafsson
 * @version 1.1
 */
public class LayerManager implements ILayerManager, IObserver<Tuple2<Vector2D, Vector2D>>, Serializable {

	private class LayerInfo implements Serializable {

		final private ILayer layer;
		final private int id;

		private boolean visible;

		public LayerInfo(ILayer layer, int id, boolean visible) {
			this.layer = layer;
			this.id = id;
			this.visible = visible;
		}

		public LayerInfo(ILayer layer, int id) {
			this(layer, id, true);
		}

		public ILayer getLayer() {
			return this.layer;
		}

		public int getId() {
			return this.id;
		}

		public void setIsVisible(boolean visible) {
			this.visible = visible;
		}

		public boolean isVisible() {
			return this.visible;
		}
	}

	final private List<LayerInfo> layers;	// Collection of layers managed this layer manager.
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
		layers.add(new LayerInfo(layer, nextId));

		layer.addObserver(this);

		return nextId++;
	}

	@Override
	public int addLayer(ILayer layer) {
		// Check if this layer already exists in this manager
		for (LayerInfo entry : layers) {
			if (entry.getLayer() == layer) {
				return entry.getId();
			}
		}

		// Add layer to manager
		layers.add(new LayerInfo(layer, nextId));

		// Observe layer
		layer.addObserver(this);

		return nextId++;
	}

	@Override
	public void destroyLayer(int id) {
		// Remove the entry with matching key
		layers.removeIf(entry -> (entry.getId() == id));
	}

	@Override
	public int getActiveLayerId() {
		return activeLayerId;
	}

	@Override
	public void setActiveLayer(int id) {
		for (LayerInfo entry : layers) {
			if (entry.getId() == id) {
				activeLayerId = id;
				return;
			}
		}
	}

	@Override
	public ILayer getLayer(int id) {
		// Find the layer with the associated ID.
		for (LayerInfo entry : layers) {
			// Return the layer with matching ID.
			if (entry.getId() == id) {
				return entry.getLayer();
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
		for (LayerInfo entry : layers) {
			ids[i++] = entry.getId();
		}

		return ids;
	}

	@Override
	public Color getPixel(int x, int y) {
		Color color = new Color(0, 0, 0, 0); // Final color

		// Find colors at center of current pixel
		Vector2D point = new Vector2D(x + 0.5, y + 0.5);

		for (LayerInfo entry : layers) {

			if (!entry.isVisible())
				continue;

			ILayer layer = entry.getLayer();

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
		for (LayerInfo entry : layers) {
			if (entry.getId() == id) {
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

	@Override
	public void setLayerVisibility(int id, boolean visible) {
		for (LayerInfo layerInfo : layers) {
			if (layerInfo.getId() == id) {
				layerInfo.setIsVisible(visible);
			}
		}
	}

	@Override
	public boolean getLayerVisibility(int id) {
		for (LayerInfo layerInfo : layers) {
			if (layerInfo.getId() == id) {
				return layerInfo.isVisible();
			}
		}

		throw new IllegalArgumentException();
	}

	//Gets called after deserialization,
	//currently uses default deserialization and then connects observers.
	private Object readResolve(){
		observers = new ArrayList<>();

		for (int i = 0; i < layers.size(); i++) {
			ILayer layer = layers.get(i).getLayer();
			//layer.removeObserver(this);
			layer.addObserver(this);
		}
		return this;
	}
}
