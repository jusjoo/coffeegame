package coffeegame;

import com.badlogic.gdx.math.Vector2;

import ec.Component;
import ec.Entity;


/**
 * Handles entity movement
 * 
 * Handles messages: "moveLeft", "moveRight", "jump" as null
 * Broadcasts message: "applyLinearImpulse" as Vector2
 */
public class MovementMotor extends Component {

	private float speed;
	private final String impulseMessage = "applyLinearImpulse";
	
	public MovementMotor(Entity parent, float speed) {
		super(parent);
		this.speed = speed;
		// TODO Auto-generated constructor stub
	}
	
	
	public void move(Vector2 direction) {
		
		// copy the Vector so the caller can use the original again
		Vector2 movement = direction.cpy();
		
		// get the normalized direction and multiply with speed
		movement.nor().mul(speed);
		
		// broadcast
		this.broadcast(impulseMessage, movement);
		
	}
	
	
	public void moveLeft() {
		move(new Vector2(-1, 0));
	}
	
	public void moveRight(){
		move(new Vector2(1, 0));
	}
	
	public void jump() {
		move(new Vector2(0,1));
	}
	
	public void handleMessage(String name, Object value) {
		if (name == "moveLeft") {
			moveLeft();
		}
		if (name == "moveRight") {
			moveRight();
		}
		if (name == "jump") {
			jump();
		}
		
	}
}
