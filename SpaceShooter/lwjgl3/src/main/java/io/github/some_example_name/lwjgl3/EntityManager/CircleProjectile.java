package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


public class CircleProjectile extends Projectile{

	private Triangle triangle;
	private Circle circle;
	
	public CircleProjectile() {
		
	}
	
	public CircleProjectile(Color color, float speed, int damage, Triangle triangle, Circle circle) {
		//Create CircleProjectile.
		super(circle.getX(),circle.getY(),color,speed,damage);
		
		//Triangle and Circle to detect collisions and positions
		this.triangle = triangle;
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
		triangle.dispose();
	}
}
