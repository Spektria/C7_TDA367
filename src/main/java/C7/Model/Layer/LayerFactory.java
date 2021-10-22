package C7.Model.Layer;

import C7.Util.Color;
import C7.Util.Vector2D;

/**
 * <code>LayerFactory</code> provides factory methods for creating layer objects of different
 * types using the {@link ILayer} interface.
 * @author Love Svalby
 * @version 1.0
 */
public final class LayerFactory {

	private LayerFactory() {
	}

	/**
	 * Creates a new layer using the default color format.
	 * @param width		Width, in pixels, of the new layer.
	 * @param height	Height, in pixels, of the new layer.
	 * @param color		Default fill color of the new layer.
	 * @return A new layer object.
	 */
	static public ILayer createDefaultLayer(int width, int height, Color color) {
		return createDefaultLayer(width, height, color, new Vector2D(0, 0), 0, new Vector2D(1, 1));
	}

	/**
	 * Creates a new layer using the default color format.
	 * @param width		Width, in pixels, of the new layer.
	 * @param height	Height, in pixels, of the new layer.
	 * @param color		Default fill color of the new layer.
	 * @param position	Position in global space of the new layer.
	 * @param angle		Rotation angle of the new layer.
	 * @param scale		Scale of the new layer.
	 * @return A new layer object.
	 */
	static public ILayer createDefaultLayer(int width, int height, Color color, Vector2D position, double angle, Vector2D scale) {
		return createRGBA32f32f32f32fLayer(width, height, color, position, angle, scale);
	}

	/**
	 * Creates a new layer using the default color format from a color matrix.
	 * @param colorMatrix Color matrix to create layer from.
	 * @return A new layer with the same dimensions and pixel data as the specified color matrix.
	 */
	static public ILayer createDefaultLayer(Color[][] colorMatrix) {
		return createRGBA32f32f32f32fLayer(colorMatrix);
	}

	/**
	 * Creates a new layer using the RGBA32f32f32f32f color format.
	 * @param width		Width, in pixels, of the new layer.
	 * @param height	Height, in pixels, of the new layer.
	 * @param color		Default fill color of the new layer.
	 * @return A new layer object.
	 */
	static public ILayer createRGBA32f32f32f32fLayer(int width, int height, Color color) {
		return createRGBA32f32f32f32fLayer(width, height, color, new Vector2D(0, 0), 0, new Vector2D(1, 1));
	}

	/**
	 * Creates a new layer using the RGBA32f32f32f32f color format.
	 * @param width		Width, in pixels, of the new layer.
	 * @param height	Height, in pixels, of the new layer.
	 * @param color		Default fill color of the new layer.
	 * @param position	Position in global space of the new layer.
	 * @param angle		Rotation angle of the new layer.
	 * @param scale		Scale of the new layer.
	 * @return A new layer object.
	 */
	static public ILayer createRGBA32f32f32f32fLayer(int width, int height, Color color, Vector2D position, double angle, Vector2D scale) {
		ILayer layer = new DDLayer(width, height, color);
		layer.setPosition(position);
		layer.setRotation(angle);
		layer.setScale(scale);
		layer.update();

		return layer;
	}

	/**
	 * Creates a new layer using the RGBA32f32f32f32f color format from a color matrix.
	 * @param colorMatrix Color matrix to create layer from.
	 * @return A new layer with the same dimensions and pixel data as the specified color matrix.
	 */
	static public ILayer createRGBA32f32f32f32fLayer(Color[][] colorMatrix) {
		return new DDLayer(colorMatrix);
	}
}
