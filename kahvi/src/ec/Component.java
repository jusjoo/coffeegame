package ec;

public class Component {

	private Entity parent;
	
	
	public Component(Entity parent) {
		this.parent = parent;
		parent.addComponent(this);
	}
	
	
	/*
	 * Override to handle update events
	 */
	public void update(float deltaTime) {}

	
	public Entity getParent() {
		return parent;
	}

	/*
	 * If the component subscribes to any messages
	 * override this method and handle sent messages there.
	 */
	public void handleMessage(String messageType, Object value) {
		Debug.log("Warning: Message " + messageType + " was sent to component: " + this.getClass().toString() +", but component doesn't implement receiving messages."); 
		Debug.log("Sent data: " + value);
	}
	
	/*
	 * Subscribe this component to parent's messages of messageType
	 */
	public void subscribe(String messageType) {
		parent.subscribe(this, messageType);
	}
	
	/*
	 * Broadcast a message to the entity
	 */
	public void broadcast(String messageType, Object value) {
		parent.broadcast(messageType, value);
	}

}
