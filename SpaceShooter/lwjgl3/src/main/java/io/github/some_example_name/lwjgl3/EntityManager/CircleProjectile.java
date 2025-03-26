package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.graphics.Color;


public class CircleProjectile extends Projectile{

	private Player player;
	private Circle circle;
	
	public CircleProjectile() {
		
	}
	
	public CircleProjectile(Color color, float speed, int damage, Player player, Circle circle) {
		//Create CircleProjectile.
		super(circle.getX(),circle.getY(),color,speed,damage);
		
		//Triangle and Circle to detect collisions and positions
		this.player = player;
		this.circle = circle;
		
		
	}
	
	public Circle getCircle() {
	    return this.circle;
	}
	

	//Movement of CircleProjectile
	public void movement() {

	}
	
	public void update() {

	}
	
	public void dispose() {
		circle.dispose();
		player.dispose();
	}
}
