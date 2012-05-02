package coffeegame;



import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import ec.Component;
import ec.Entity;

public class PhysicsBody extends Component {

	private FixtureDef bodyFixtureDef;
	private BodyDef	bodyDef;
	private Body body;
	private Fixture bodyFixture;

	
	public PhysicsBody(Entity parent, Shape shape, World world, Vector2 position, boolean isStatic) {
		super(parent);
		
		bodyFixtureDef = new FixtureDef();
		bodyDef = new BodyDef();
		
		if (isStatic){
			bodyDef.type = BodyDef.BodyType.StaticBody;
		} else {
			bodyDef.type = BodyDef.BodyType.DynamicBody;
		}
		
		bodyFixtureDef.shape = shape;
		
		bodyDef.position.set(position.x,position.y);


		body = world.createBody(bodyDef);
		
		
		// Sets the parent's position so it follows physics
		parent.setTransform(body.getTransform());
		
		bodyFixtureDef.density = 13.5f;
		bodyFixtureDef.friction = Config.physicsFrictionDef;

		bodyFixture = body.createFixture(bodyFixtureDef);
		
		body.setLinearDamping(Config.physicsLinearDamping);
		
		body.setActive(true);
	}

	
	
}
