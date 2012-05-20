package coffeegame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dynamicmusic.MusicPlayer;


public class Main implements ApplicationListener{

	// Draw all sprites in this, it is sent down with all render() calls.
	private SpriteBatch spriteBatch;	
	
	// World camera, draws everything but tiledMapRenderer
	private OrthographicCamera cam;
	
	// Map object, that holds all entities, tiles etc.
	private GameMap currentMap;
	
	// Dynamic music player
	private MusicPlayer musicPlayer;
	
	// Handles the delay between debug keystrokes.
	private float keyWasPressed; 

	@Override
	public void create() {
		// draw all Sprites to this batch
		spriteBatch = new SpriteBatch();
		
		// our camera, move this with the player
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// scale the viewport according to our metric scale
		//cam.viewportHeight = cam.viewportHeight/Config.PIXELS_PER_METER;
		//cam.viewportWidth = cam.viewportWidth/Config.PIXELS_PER_METER;
		

		cam.position.x = 0;
		cam.position.y = 0;
		
		// updates the camera's viewport scaling etc.
		cam.update();

		// gets the projection matrix from our cam, so sprites drawn in spriteBatch are scaled correctly
		spriteBatch.setProjectionMatrix(cam.combined);
		
		// create the GameMap for testing
		currentMap = new GameMap(Gdx.files.internal("assets/maps/untitled.tmx"));
					
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
	    
		// get the mouse position in pixels
	    Vector2 mouse = new Vector2(Gdx.input.getX() + cam.position.x - Gdx.graphics.getWidth()/2, 
	    							Gdx.input.getY() + cam.position.y - Gdx.graphics.getHeight()/2);
	    
	    // convert pixels to meter coordinates
	    mouse.mul(1/Config.PIXELS_PER_METER);
	    
	    // set the mouse coordinates as title, for debugging
		Gdx.graphics.setTitle(Integer.toString(Gdx.graphics.getFramesPerSecond()) 
				+ " (" + mouse.x + ", " + mouse.y + ")");
		
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
		// Simulate one step of the physics world
		currentMap.physicsWorld.step(1/60f, 3, 3);
		
		// deltaTime is the time between each frame
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// update map and it's entities
		currentMap.update(deltaTime);
		
		// handle debug input
		handleDebugInput(deltaTime);
		
		// update the music player
		musicPlayer.update(deltaTime);
		
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportHeight = height/Config.PIXELS_PER_METER;
		cam.viewportWidth = width/Config.PIXELS_PER_METER;
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void loadMap(FileHandle mapFile) {
		currentMap = new GameMap(mapFile);
		
		
	}
	
	/*
	 * Put all debug cheat input thingies here.
	 */
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
	
	/*
	 * Launches the game!
	 */
	public static void main (String[] args) {
		new LwjglApplication(new Main(), "Game", Config.windowSizeX, Config.windowSizeY, false);
	}

	
}
