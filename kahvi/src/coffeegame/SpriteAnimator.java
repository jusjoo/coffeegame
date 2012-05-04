package coffeegame;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ec.Component;
import ec.Entity;
import ec.Renderable;

public class SpriteAnimator extends Component implements Renderable {

	private Animation animation;
	
	private HashMap<TextureRegion, Sprite> savedSprites;
	
	private float stateTime;
	private Sprite currentSprite;
	private Vector2 offset;
	
	public SpriteAnimator(Entity parent, Animation animation, Vector2 positionOffset) {
		super(parent);
		
		this.savedSprites = new HashMap<TextureRegion, Sprite>();
		this.animation = animation;
		
		
		this.offset = positionOffset.cpy();
		this.offset.add(getStaticOffset());
		
		// update once so we don't get null pointer exception on first render
		update(0f);
				
	}

	public void update(float deltaTime) {
		this.stateTime += deltaTime;
		TextureRegion currentFrame = this.animation.getKeyFrame(stateTime, true);
		
		/*
		 * If the sprite is new, we save it to savedSprites,
		 * otherwise get the previously saved sprite.
		 */
		currentSprite = savedSprites.get(currentFrame);
		if (currentSprite == null) {
			Sprite sprite = new Sprite(currentFrame);
			savedSprites.put(currentFrame, sprite);
			currentSprite = sprite;
		}

	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		
		currentSprite.setRotation((float) (parent.transform.getRotation()*180/Math.PI));
		
		updateSpritePosition();
		currentSprite.setScale(1/Config.PIXELS_PER_METER);
        
        currentSprite.draw(spriteBatch);  
		
	}
	
	private void updateSpritePosition() {
		Vector2 position = parent.transform.getPosition();
		currentSprite.setPosition(position.x + offset.x, position.y + offset.y);
	}
	
	/*
	 * This is used to center the texture with parent.transform.position
	 */
	private Vector2 getStaticOffset() {
		return new Vector2(-animation.getKeyFrame(0, false).getRegionWidth()/2,
							-animation.getKeyFrame(0, false).getRegionHeight()/2);
	}
}
