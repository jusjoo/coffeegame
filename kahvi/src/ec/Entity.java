package ec;


import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Entity, which holds => 0 amount of components.
 * 
 * Messaging system will let you broadcast messages from components to other components
 * without direct references. 
 * 
 */
public class Entity{
	

	public Transform transform;
	
	/*
	 * Holds all attached components
	 */
	private HashMap<Class<? extends Component>, Component> components = new HashMap<Class<? extends Component>, Component>();
	
	/*
	 * Holds renderable components
	 */
	private ArrayList<Renderable> renderables = new ArrayList<Renderable>();
	
	/*
	 * Holds all messaging system subscriptions
	 */
	private HashMap<String, ArrayList<Component>> subscriptions = new HashMap<String, ArrayList<Component>>();
	

	public Entity() {
		transform = new Transform();
		
	}
	
	public void setTransform(Transform t) {
		transform = t;
	}
	
	public void addComponent(Component c) {	
		components.put(c.getClass(), c);
		
		if (c instanceof Renderable) {
			renderables.add((Renderable) c);
		}
	}
	
	public void remove(Class<? extends Component> componentClass) {
		Component c = components.get(componentClass);
		components.remove(componentClass);
		
		if (c instanceof Renderable) {
			renderables.remove((Renderable) c);
		}
	}
	
	public void remove(Component c) {
		components.remove(c.getClass());
		
		if (c instanceof Renderable) {
			renderables.remove((Renderable) c);
		}
	}
	
	/**
	 * Updates every component
	 */
	public void update(float deltaTime) {
		for (Component c : components.values()){
			c.update(deltaTime);

		}
	}
	
	
	/**
	 * Broadcasts the message to all components that have subscribed to that messageType.
	 * 
	 * @pre	value != null
	 * 
	 * @param messageType
	 * @param value
	 */
	public void broadcast(String messageType, Object value) {

		ArrayList<Component> subscribers = subscriptions.get(messageType);
		
		if (subscribers != null){
			for (Component c : subscribers) {
				c.handleMessage(messageType, value);
			}
		} else {
			Debug.log("Warning: no subscribers for message type: " + messageType);
		}
	}
	
	
	/**
	 * Subscribe component c for messageType -messages
	 * 
	 * @param c
	 * @param messageType
	 */
	public void subscribe(Component c, String messageType) {
		
		ArrayList<Component> currentSubscribers = subscriptions.get(messageType);
		
		if (currentSubscribers == null) {
			ArrayList<Component> subscribers = new ArrayList<Component>();
			subscribers.add(c);
			subscriptions.put(messageType, subscribers);
		} else {
			currentSubscribers.add(c);
		}
		
		Debug.log("Subscription added to component: "+ c.getClass() + ", type: " + messageType);
	}
	
	
	
	
	public static void main(String[] args) {
		
		// Test messaging
		Entity e = new Entity();
		Component c1 = new Component(e);
		Component c2 = new Component(e);
		
		e.addComponent(c1);
		e.addComponent(c2);
		
		c1.subscribe("testimessage");
		
		// Should print a warning message about receiver not implemented
		c2.broadcast("testimessage", "dataa t�h�n");
		
		// Should print a warning about no subscribers for messagetype
		c2.broadcast("nosubscribersforthistype", "dataa t�h�n");
	}

	
	public void render(SpriteBatch batch) {
		
		for (Renderable r: renderables) {
			r.render(batch);
		}
		
	}


}
