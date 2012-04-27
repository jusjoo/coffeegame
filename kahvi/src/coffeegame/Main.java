package coffeegame;



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;


public class Main implements ApplicationListener{

	private SpriteBatch spriteBatch;
	private Box2DDebugRenderer debugRenderer;
	private Camera cam;
	private GameMap currentMap;
	
	@Override
	public void create() {
		
		spriteBatch = new SpriteBatch();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		cam.position.x = Gdx.graphics.getWidth()/4;
		cam.position.y = Gdx.graphics.getHeight()/4;
		cam.update();
		debugRenderer = new Box2DDebugRenderer();
		
		
		spriteBatch.setProjectionMatrix(cam.combined);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.graphics.setTitle(Integer.toString(Gdx.graphics.getFramesPerSecond()));
		
		Gdx.gl.glClearColor(0, 0, 0, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		cam.update();
		
		
		// check if we have a map loaded and game is not paused
		if (currentMap != null) {
			update();
		}
		
	}

	private void update() {
		
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		currentMap.update(deltaTime);
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void loadMap() {
		
	}
	
	//trolololo & toinen trolollo & kolmas oolololkoasdadf
	public static void main (String[] args) {
		new LwjglApplication(new Main(), "Game", Config.windowSizeX, Config.windowSizeY, false);
	}

}
