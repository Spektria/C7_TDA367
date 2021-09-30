package C7.Model.Layer;

import C7.Model.Color;
import C7.Model.Vector.Vector2D;
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
}
