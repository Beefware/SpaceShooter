package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


public class Projectile extends Entity{

	private int projectileDamage;
	private Rectangle bounds;
	
	public Projectile() {
		
	}
	
	//Create Projectile Entity
	public Projectile(float x, float y,Color color, float speed, int damage) {
		super(x,y,color,speed,0);
		//set damage of projectile
		this.projectileDamage = damage;
		
	}
	
	//Draw Projectile
	public void draw(ShapeRenderer shape) {
		shape.begin(ShapeRenderer.ShapeType.Filled);
			shape.setColor(this.getColor());
			shape.rect(this.getX(), this.getY(), 10, 30);
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
	
	//Projectile Movement
	public void movement() {
		
	}
	
	//Projectile Update
	public void update() {

	}
	
	//Projectile Dispose
	public void dispose() {
		this.dispose();
	}
}
