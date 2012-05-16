package coffeegame;

import ec.Component;
import ec.Entity;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

/**
 * Handles player input.
 * 
 * Broadcasts messages: "moveLeft", "moveRight", "jump" as null.
 */
public class OfficeGuyInput extends Component{
	
	enum Keys {
		LEFT, RIGHT, JUMP
	}
	
	static Map<Keys, Boolean> keys = new HashMap<OfficeGuyInput.Keys, Boolean>();
	
	static{
			keys.put(Keys.LEFT, false);
			keys.put(Keys.RIGHT, false);
			keys.put(Keys.JUMP, false);		
	};

	public OfficeGuyInput(Entity parent) {
		super(parent);
	}
	
	//Painetaanko nappia?
    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }
    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }
    public void jumpPressed() {
        keys.get(keys.put(Keys.JUMP, true));
    }

    //Päästetäänkö napista irti?
    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }
    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }
    public void jumpReleased() {
        keys.get(keys.put(Keys.JUMP, false));
    }


	private void sendOutput(){
		if(keys.get(Keys.LEFT)){
			broadcast("moveLeft", null);
		}
		if(keys.get(Keys.RIGHT)){
			broadcast("moveRight", null);
		}
		if(keys.get(Keys.JUMP)){
			broadcast("jump", null);
		}
	}
	
	// TODO: handle actual keypresses
	private void processInput() {
		
	}
	
	public void update(float deltaTime) {
		processInput();
		sendOutput();
	}

	
}