package coffeegame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ec.Component;
import ec.Entity;
import ec.Renderable;

public class SpriteRenderer extends Component implements Renderable {

	private Vector2 offset;
	private Sprite sprite;
	
	
	public SpriteRenderer(Entity parent, Sprite sprite, Vector2 offset) {
		super(parent);
		this.sprite  = sprite;
		this.offset = offset.cpy();
		this.offset.add(getStaticOffset());
		updateSpritePosition();
	}


	public SpriteRenderer(Entity parent, Sprite sprite) {
		super(parent);
		this.sprite  = sprite;
		this.offset = getStaticOffset();
		updateSpritePosition();
	}


	public void render(SpriteBatch spriteBatch) {
		
		// set position and rotation according to the entity
		sprite.setRotation((float) (parent.transform.getRotation()*180/Math.PI));
		
		
		updateSpritePosition();
		sprite.setScale(1/Config.PIXELS_PER_METER);
		sprite.draw(spriteBatch);
	}
	
	private void updateSpritePosition() {
		Vector2 position = parent.transform.getPosition();
		sprite.setPosition(position.x + offset.x, position.y + offset.y);
	}
	
	/*
	 * This is used to center the texture with parent.transform.position
	 */
	private Vector2 getStaticOffset() {
		return new Vector2(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}
}
