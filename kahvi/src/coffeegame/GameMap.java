package coffeegame;

import java.util.ArrayList;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
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
		
		worldEntities = new ArrayList<Entity>();
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
					
					Vector2 position = new Vector2(object.x, object.y);
					
					// parse vertices from the string
					Vector2[] vertices = getPolygonVertices(object.polygon);
					
					PolygonShape shape = new PolygonShape();
					shape.set(vertices);
					
					
					Mesh mesh = new Mesh(true, 50, 50, new VertexAttribute(Usage.Position, 3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4, "a_color"));
					
					
					
					mesh.setVertices(getMeshVertices(vertices));
					Texture texture = new Texture(Gdx.files.internal("assets/maps/mrEggEverything.png"));
							
							//createPolygonShape(object.polygon);
					
					
					Entity entity = new Entity();
					
					
					
					Debug.log(shape.toString());
					new PhysicsBody(entity, shape, physicsWorld, position);
					//new MeshRenderer(entity, mesh, texture, position);
					
					
					worldEntities.add(entity);
					
				}
			
		}
	}
	
	/**
	 * Converts Vector2 vertices into float[] vertices
	 */
	private float[] getMeshVertices(Vector2[] vertices) {
		float[] result = new float[vertices.length*4];
		
		for (int i=0; i < vertices.length; i++) {
			result[i*4] = vertices[i].x;
			result[i*4+1] = vertices[i].y;
			result[i*4+2] = 0f;
			result[i*4+3] = Color.toFloatBits(0, 0, 255, 255);
		}
		
		
		return result;
	}



	/**
	 * Creates a new PolygonShape from a given String of coordinates.
	 * @param polygon
	 * @return
	 */
	private Vector2[] getPolygonVertices(String polygon) {
		
		
		
		
		ArrayList<Vector2> vectors = new ArrayList<Vector2>();
		
		for (String piece :polygon.split(" ")) {
			String[] coords = piece.split(",");
			Vector2 vector = new Vector2(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]));
			
			vectors.add(vector); 
			
		}
		

		
		Vector2[] array = new Vector2[vectors.size()];
		vectors.toArray(array);
		
			

		
		
		return array;
		
	}


	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	public void render(OrthographicCamera cam, SpriteBatch spriteBatch) {
		debugRenderer.render( physicsWorld, cam.combined.scale(Config.PIXELS_PER_METER, Config.PIXELS_PER_METER,
				Config.PIXELS_PER_METER) );
		
		debugSprite.setPosition(cam.position.x, cam.position.y);
		
		spriteBatch.begin();
		debugSprite.draw(spriteBatch);
		
		for (Entity e: worldEntities) {
			e.render(spriteBatch);
		}
		
		spriteBatch.end();
		
		tileMapRenderer.render(cam);
		
		
	}

}
