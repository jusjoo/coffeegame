package coffeegame;

import com.badlogic.gdx.Input.Keys;

public class Config {

	public static final int windowSizeX = 1024;
	public static final int windowSizeY = 640;
	public static final float physicsWorldGravity = -20;
	public static final float physicsFrictionDef = 0.02f;
	public static final float PIXELS_PER_METER = 64;
	public static final int tileSize = 32;
	public static final float physicsLinearDamping = 3.0f;
	public static final float playerMovementSpeed = 5;
	
	public static final int playerJumpButton = Keys.SPACE;
	public static final int playerLeftButton = Keys.LEFT;
	public static final int playerRightButton = Keys.RIGHT;
}
