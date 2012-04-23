package coffeegame;

import com.badlogic.gdx.math.Vector2;

import ec.Component;
import ec.Debug;
import ec.Entity;

public class WorldPosition extends Component {

	private Vector2 position;
	
	
	public WorldPosition(Entity parent) {
		super(parent);	
		position = new Vector2(0,0);
		this.subscribe("position");
	}
	
	public WorldPosition(Entity parent, Vector2 position) {
		super(parent);
		this.position = position;
		this.subscribe("position");
	}
	
	public void setPosition(Vector2 pos) {
		position = pos;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void handleMessage(String messageType, Object value) {
		
		if (messageType == "position" && value instanceof Vector2) {
			this.setPosition((Vector2)value);
		} else {
			Debug.log("Warning! messageType or value type not supported.");
		}
		
	}
}
