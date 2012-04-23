package ec;

public class Component {

	private Entity parent;
	
	public Component(Entity parent) {
		this.parent = parent;
	}
	
	public void update(float deltaTime) {
		
	}

	public Entity getParent() {
		return parent;
	}

	/**
	 * Override this and handle sent messages here
	 */
	public void handleMessage(String messageType, Object value) {
		Debug.log("Warning: Message " + messageType + " was sent to component: " + this.getClass().toString() +", but component doesn't implement receiving messages."); 
		Debug.log("Sent data: " + value);

	}
	
	public void subscribe(String messageType) {
		parent.subscribe(this, messageType);
	}
	
	public void broadcast(String messageType, Object value) {
		parent.broadcast(messageType, value);
	}

}
