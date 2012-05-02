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
		shape.setAsBox(width /2, height/2);
		
		return shape;
	}
	
}
