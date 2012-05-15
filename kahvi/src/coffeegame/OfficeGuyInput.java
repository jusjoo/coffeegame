package coffeegame;

import ec.Component;
import ec.Entity;
import java.util.HashMap;
import java.util.Map;


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

    //TODO: Odottelee Movementin toteutusta
	private void processInput(){
		if(keys.get(Keys.LEFT)){
			
		}
		if(keys.get(Keys.RIGHT)){
			
		}
		if(keys.get(Keys.JUMP)){
			
		}
	}
	
	public void update(float deltaTime) {
		processInput();
	}

	
}