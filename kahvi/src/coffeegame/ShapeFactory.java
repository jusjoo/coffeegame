package coffeegame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class ShapeFactory {
	
	/**
	 * Constructs a new Box shape. Size is absolute.
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public static PolygonShape createBox(float width, float height) {
		
		PolygonShape shape = new PolygonShape();
		float PIXELS_PER_METER = Config.PIXELS_PER_METER;
		
		shape.setAsBox(width / (PIXELS_PER_METER * 2), height/(PIXELS_PER_METER * 2));
		
		return shape;
	}
	
}
