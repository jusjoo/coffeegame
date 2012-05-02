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

	public World physicsWorld;
	private OrthographicCamera debugCam;
	private Box2DDebugRenderer debugRenderer;
	
	private TiledMap map;
	private TileMapRenderer tileMapRenderer;
	
	private final String GROUND_LAYER = "Ground";
	
	private ArrayList<Entity> worldEntities;
	
	private Texture debugTexture;
	private Sprite debugSprite;
	
	private int tileSize = Config.tileSize;
	
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
		
		// find the correct ground layer
		TiledLayer layer = null;
		for (TiledLayer l :map.layers) {
			if(l.name.equals(GROUND_LAYER)){
				layer = l;
				break;
			}
		}
		
		int[][] tiles = layer.tiles;
					
			for(int y=0; y<tiles.length; y++) {
				
				for(int x=0; x<tiles[y].length; x++) {
					
					if(tiles[y][x] != 0) {
						

							Entity entity = new Entity();
							PolygonShape shape = new PolygonShape();
							
							shape.setAsBox(tileSize / (2 * Config.PIXELS_PER_METER), tileSize / (2 * Config.PIXELS_PER_METER));
							
							new PhysicsBody(entity, shape, physicsWorld, new Vector2(x * tileSize, y * tileSize), true);
							
									//new BodyComponent(null, new Vector2(tileSize + tileSize*tilesSkipped, tileSize), true, 1.0f, false, shape, false);
							//tile.addToWorld(world, new Vector2(x*tileSize - (tilesSkipped)*tileSize/2 , -y*tileSize+map.height*tileSize));
									
							worldEntities.add(entity);
							
						
					}
				}
			}	
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
		for (Entity e:worldEntities) {
			e.update(deltaTime);
		}
		
	}
	
	public void render(OrthographicCamera cam, SpriteBatch spriteBatch) {
		
		
		//debugSprite.setPosition(cam.position.x, cam.position.y);
		
		spriteBatch.begin();
		//debugSprite.draw(spriteBatch);
		
		for (Entity e: worldEntities) {
			e.render(spriteBatch);
		}
		
		spriteBatch.end();
		
		debugRenderer.render( physicsWorld, cam.combined.scale(Config.PIXELS_PER_METER, Config.PIXELS_PER_METER,
				Config.PIXELS_PER_METER) );
		
	}

	public void addEntity(Entity e) {
		this.worldEntities.add(e);
	}
}
