package coffeegame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import dynamicmusic.MusicPlayer;

import ec.Entity;


public class Main implements ApplicationListener{

	private SpriteBatch spriteBatch;	
	private OrthographicCamera cam;
	private GameMap currentMap;
	private Texture texture;
	private MusicPlayer musicPlayer;
	private float keyWasPressed; //ei liikaa napinpainalluksia

	@Override
	public void create() {
		// draw all Sprites to this batch
		spriteBatch = new SpriteBatch();
		
		// our camera, move this with the player
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// scale the viewport according to our metric scale
		cam.viewportHeight = cam.viewportHeight/Config.PIXELS_PER_METER;
		cam.viewportWidth = cam.viewportWidth/Config.PIXELS_PER_METER;
		
		cam.position.x = 0;
		cam.position.y = 0;
		
		// not sure what this does, actually (object scaling?)
		cam.update();

		spriteBatch.setProjectionMatrix(cam.combined);
		
		currentMap = new GameMap(Gdx.files.internal("assets/maps/untitled.tmx"));
		

		// third entity is animated
		Entity e3 = new Entity();
		Vector2 pos3 = new Vector2(-1.02f, 3f);
		
		Texture tex = new Texture(Gdx.files.internal("assets/animations/testSprite.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 32, 32);
		TextureRegion[] frames = new TextureRegion[4 * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
                for (int j = 0; j < 4; j++) {
                        frames[index++] = tmp[i][j];
                }
        }
        
		Animation animation = new Animation(1/10f, frames);
		
		new SpriteAnimator(e3, animation, new Vector2(0,0));
		new PhysicsBody(e3, ShapeFactory.createBox(32, 32), currentMap.physicsWorld, pos3, false);
		currentMap.addEntity(e3);
		
		
		// test the music player
		musicPlayer = new MusicPlayer();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Render the game
	 */
	public void render() {
		// clear screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
		
	    Vector2 mouse = new Vector2(Gdx.input.getX() + cam.position.x - Gdx.graphics.getWidth()/2, 
	    							Gdx.input.getY() + cam.position.y - Gdx.graphics.getHeight()/2);
	    
		Gdx.graphics.setTitle(Integer.toString(Gdx.graphics.getFramesPerSecond()) 
				+ " (" + mouse.x + ", " + mouse.y + ")");
		
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    Gdx.graphics.getGL10().glEnable(GL10.GL_TEXTURE_2D);
		
		// check if we have a map loaded and game is not paused
		if (currentMap != null) {
			currentMap.render(cam, spriteBatch);
			update();
		}
		
		
	}

	/**
	 * Update the game state
	 */
	private void update() {
		
		
		
		currentMap.physicsWorld.step(1/60f, 3, 3);
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		currentMap.update(deltaTime);
		handleDebugInput(deltaTime);
		musicPlayer.update(deltaTime);
		
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportHeight = cam.viewportHeight/Config.PIXELS_PER_METER;
		cam.viewportWidth = cam.viewportWidth/Config.PIXELS_PER_METER;
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void loadMap(FileHandle mapFile) {
		currentMap = new GameMap(mapFile);
		
		
	}
	
	private void handleDebugInput(float deltaTime) {
		if (keyWasPressed <= 0) {
			if (Gdx.input.isKeyPressed(Input.Keys.P)) musicPlayer.play();
			
			if (Gdx.input.isKeyPressed(Input.Keys.F8)) musicPlayer.setExcitement(musicPlayer.getExcitementLevel() + 1);
			if (Gdx.input.isKeyPressed(Input.Keys.F7)) musicPlayer.setExcitement(musicPlayer.getExcitementLevel() - 1);
			
			keyWasPressed = 0.2f;
		} else {
			keyWasPressed -= deltaTime;
		}
	}
	
	public static void main (String[] args) {
		new LwjglApplication(new Main(), "Game", Config.windowSizeX, Config.windowSizeY, false);
	}

	
}
