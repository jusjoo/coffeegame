package coffeegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ec.Component;
import ec.Entity;
import ec.Renderable;

public class MeshRenderer extends Component implements Renderable {

	private Mesh mesh;
	private Vector2 meshPosition;
	private Texture texture;
	
	public MeshRenderer(Entity entity, Mesh mesh, Texture texture, Vector2 position) {
		super(entity);
		
		this.mesh = mesh;
		this.meshPosition = position;
		this.texture = texture;
		
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
	    texture.bind();
	    
	    mesh.render(GL10.GL_TRIANGLES, 0, 3);
	   
	    
	}

}
