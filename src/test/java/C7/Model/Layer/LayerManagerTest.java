package C7.Model.Layer;

import C7.Util.Color;
import C7.Util.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LayerManagerTest {

	/**
	 * Tests that a layer can be created and returns a non-zero ID.
	 */
	@Test
	public void createLayerTest() {
		ILayerManager layerManager = new LayerManager();

		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));
		Assertions.assertNotEquals(id, 0);
	}

	/**
	 * Tests that a layer is returned when given a valid ID.
	 */
	@Test
	public void getLayerTest() {
		ILayerManager layerManager = new LayerManager();

		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));

		ILayer layer = layerManager.getLayer(id);
		Assertions.assertNotNull(layer);
	}

	@Test
	public void defaultLayerTest() {
		ILayerManager layerManager = new LayerManager();

		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));

		ILayer layer = layerManager.getLayer(id);

		Assertions.assertEquals(new Layer(16, 16, new Color(0, 0, 0, 0)), layer);
	}

	/**
	 * Tests that accessing a layer through an ID which was previously created
	 * and subsequently destroyed returns null.
	 */
	@Test
	public void destroyLayerTest() {
		ILayerManager layerManager = new LayerManager();

		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0,  new Vector2D(1, 1));

		layerManager.destroyLayer(id);

		ILayer layer = layerManager.getLayer(id);

		Assertions.assertNull(layer);
	}

	/**
	 * Tests that getAllLayerIds returns an array with the same size as the total
	 * number of layers created.
	 */
	@Test
	public void layerCountTest() {
		ILayerManager layerManager = new LayerManager();

		layerManager.createLayer(16, 16, new Vector2D(0, 0), 0,  new Vector2D(1, 1));
		int destroyId = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0,  new Vector2D(1, 1));
		layerManager.destroyLayer(destroyId);
		layerManager.createLayer(16, 16, new Vector2D(0, 0), 0,  new Vector2D(1, 1));
		layerManager.createLayer(16, 16, new Vector2D(0, 0), 0,  new Vector2D(1, 1));

		Assertions.assertEquals(3, layerManager.getAllLayerIds().length);
	}

	/**
	 * Tests that adding an existing layer returns a valid ID.
	 */
	@Test
	public void addLayerTest() {
		ILayerManager layerManager = new LayerManager();
		ILayer layer = new Layer(16, 16, new Color(0, 0, 0, 0));

		int id = layerManager.addLayer(layer);

		Assertions.assertNotEquals(0, id);
	}

	/**
	 * Tests that adding an existing layer to a layer manager multiple times
	 * always returns the same ID.
	 */
	@Test
	public void addLayerSameIdTest() {
		ILayerManager layerManager = new LayerManager();
		ILayer layer = new Layer(16, 16, new Color(0, 0, 0, 0));

		int existingId = layerManager.addLayer(layer);
		int newId = layerManager.addLayer(layer);

		Assertions.assertEquals(existingId, newId);
	}

	/**
	 * Tests that adding an existing layer to a layer manager and then retrieving it
	 * results in retrieving the same layer.
	 */
	@Test
	public void addLayerEqualsTest() {
		ILayerManager layerManager = new LayerManager();
		ILayer layer = new Layer(16, 16, new Color(0, 0, 0, 0));

		int id = layerManager.addLayer(layer);

		Assertions.assertEquals(layer, layerManager.getLayer(id));
	}

	/**
	 * Tests that setting the active layer changes the active layer ID.
	 */
	@Test
	public void setActiveLayerChangesTest() {
		ILayerManager layerManager = new LayerManager();

		layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));
		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));

		layerManager.setActiveLayer(id);

		Assertions.assertEquals(id, layerManager.getActiveLayerId());
	}

	/**
	 * Tests that setting the active layer to an invalid ID does not change the
	 * active layer ID.
	 */
	@Test
	public void setActiveLayerInvalidTest() {
		ILayerManager layerManager = new LayerManager();

		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));

		layerManager.setActiveLayer(id);

		// Invalid ID
		layerManager.setActiveLayer(id + 1);

		Assertions.assertEquals(id, layerManager.getActiveLayerId());
	}

	@Test
	public void getPixelTest() {
		ILayerManager layerManager = new LayerManager();

		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));
		ILayer layer = layerManager.getLayer(id);
		layer.setLocalPixel(0, 0, new Color(1, 0, 0, 1));

		Assertions.assertEquals(new Color(1, 0, 0, 1), layerManager.getPixel(0, 0));
	}

	@Test
	public void getPixelMultipleLayersTest() {
		ILayerManager layerManager = new LayerManager();

		// Layer 1
		int id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));
		ILayer layer = layerManager.getLayer(id);
		layer.setLocalPixel(0, 0, new Color(0, 1, 0, 1));

		// Layer 2
		id = layerManager.createLayer(16, 16, new Vector2D(0, 0), 0, new Vector2D(1, 1));
		layer = layerManager.getLayer(id);
		layer.setLocalPixel(0, 0, new Color(1, 0, 0, 1));

		Assertions.assertEquals(new Color(1, 0, 0, 1), layerManager.getPixel(0, 0));
	}
}
