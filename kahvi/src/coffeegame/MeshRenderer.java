package coffeegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ec.Component;
import ec.Debug;
import ec.Entity;
import ec.Renderable;

public class MeshRenderer extends Component implements Renderable {

	private Mesh mesh;
	private Vector2 meshPosition;
	private Texture texture;
	private Sprite sprite;
	
	public MeshRenderer(Entity entity, Mesh mesh, Texture texture, Vector2 position) {
		super(entity);
		
		this.mesh = mesh;
		this.meshPosition = position;
		this.texture = texture;
		sprite = new Sprite(this.texture);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		
		sprite.setPosition(meshPosition.x, meshPosition.y);
		sprite.draw(spriteBatch);
	   
	}

}
