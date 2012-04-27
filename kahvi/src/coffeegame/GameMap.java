package coffeegame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;

import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import ec.Debug;
import ec.Entity;

public class GameMap {

	private World physicsWorld;
	private OrthographicCamera debugCam;
	private Box2DDebugRenderer debugRenderer;
	
	private TiledMap map;
	private TileMapRenderer tileMapRenderer;
	
	private final String GROUND_LAYER = "Ground";
	
	private ArrayList<Entity> worldEntities;
	
	private Texture debugTexture;
	private Sprite debugSprite;
	
	public GameMap(FileHandle mapFile) {
		
		debugTexture = new Texture(Gdx.files.internal("assets/maps/mrEggEverything.png"));
		debugSprite = new Sprite(debugTexture);
		
		// create physics stuff
		physicsWorld = new World(new Vector2(0.0f, Config.physicsWorldGravity), true);
		physicsWorld.setContactListener(new KahviContactListener());
		debugCam = new OrthographicCamera( 24, 16 );
		debugRenderer = new Box2DDebugRenderer(true,true,true,true);
		
		
		// load map
		map = TiledLoader.createMap(mapFile);
		SimpleTileAtlas atlas = new SimpleTileAtlas(map, Gdx.files.internal("assets/maps/"));
		tileMapRenderer = new TileMapRenderer(map, atlas, 50, 50);
		
		createGround();
		
	}
	
	
	
	private void createGround() {
		
		for (TiledObjectGroup group :map.objectGroups) {
			
				// TODO: Add a check for the proper object group name
				for (TiledObject object : group.objects) {
										
					Debug.log(object.polygon);
					PolygonShape shape = createPolygonShape(object.polygon);
					
							
							//createPolygonShape(object.polygon);
					
					
					Entity entity = new Entity();
					
					Vector2 position = new Vector2(object.x, object.y);
					
					Debug.log(shape.toString());
					new PhysicsBody(entity, shape, physicsWorld, position);
					
					
				}
			
		}
	}
	
	/**
	 * Creates a new PolygonShape from a given String of coordinates.
	 * @param polygon
	 * @return
	 */
	private PolygonShape createPolygonShape(String polygon) {
		/*PolygonShape shape = new PolygonShape();
		
		
		ArrayList<Vector2> vectors = new ArrayList<Vector2>();
		
		for (String piece :polygon.split(" ")) {
			String[] coords = piece.split(",");
			Vector2 vector = new Vector2(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]));
			if (vector.x != 0 && vector.y != 0) {
				vectors.add(vector); 
			}
		}
		

		
		Vector2[] array = new Vector2[vectors.size()];
		vectors.toArray(array);
		
		shape.set(array);*/
		
		Vector2[] vertices = new Vector2[8];

	    vertices[0] = new Vector2(82f  , 0f  );
	    vertices[1] = new Vector2(146f , 40f  );
	    vertices[2] = new Vector2(385f , 268f);
	    vertices[3] = new Vector2(322f , 341f);
	    vertices[4] = new Vector2(225f , 322f);
	    vertices[5] = new Vector2(282f , 398f);     
	    vertices[6] = new Vector2(161f , 457f);
	    vertices[7] = new Vector2(135f , 298f);
	    PolygonShape shape = new PolygonShape();
	    
	    for (int i=0; i<vertices.length; i++) {
	    	Debug.log(vertices[i]);
	    }
	    
	    shape.set(vertices);
		
		
		return shape;
		
	}


	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	public void render(OrthographicCamera cam, SpriteBatch spriteBatch) {
		debugRenderer.render( physicsWorld, debugCam.combined );
		
		debugSprite.setPosition(cam.position.x, cam.position.y);
		debugSprite.draw(spriteBatch);
		
		//spriteBatch.draw(debugTexture, cam.position.x, cam.position.y);
		
		
		tileMapRenderer.render(cam);
	}

}
