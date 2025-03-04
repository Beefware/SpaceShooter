package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


public class TriangleProjectile extends Entity{

	private Triangle triangle;
	
	private int projectileDamage;
	private Rectangle bounds;
	
	public TriangleProjectile() {
		
		
	}
	
	public TriangleProjectile(Color color, float speed, int damage, Triangle triangle) {
		//Create TriangleProjectile
		super(triangle.getX()+50,triangle.getY(),color,speed,damage);
		this.projectileDamage = damage;
		this.triangle = triangle;
		
	}
	
	//Draw Projectile
	public void draw(ShapeRenderer shape) {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		if(this.getY()>5) {
			shape.setColor(this.getColor());
			shape.rect(this.getX(), this.getY(), 10, 30);
		}
		shape.end();  
		//Create Rectangle to detect collisions
        bounds = new Rectangle(this.getX(), this.getY(), 10, 30);
	}
	
	//Return Rectangle
	public Rectangle getBounds(){
		return bounds;
	}
	
	//Return ProjectileDamage
	public int getProjectileDamage() {
		return projectileDamage;
	}

	//Set ProjectileDamage
	public void setProjectileDamage(int projectileDamage) {
		this.projectileDamage = projectileDamage;
	}

	public Triangle getTriangle() {
	    return this.triangle;
	}
	
	
	public void movement() {

	}
	
	public void update() {


	}
	
	public void dispose() {
		triangle.dispose();
		this.dispose();
	}
}
