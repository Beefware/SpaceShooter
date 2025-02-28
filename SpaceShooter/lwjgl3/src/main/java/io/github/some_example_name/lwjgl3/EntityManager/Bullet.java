package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;


public class Bullet extends Entity{

	private int bulletDamage;
	private Rectangle bounds;
	
	public Bullet() {
		
	}
	
	//Create Bullet Entity
	public Bullet(float x, float y,Color color, float speed, int damage) {
		super(x,y,color,speed,0);
		this.bulletDamage = damage;
		
	}
	
	//Draw Bullet
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
	
	//Return BulletDamage
	public int getBulletDamage() {
		return bulletDamage;
	}

	//Set BulletDamage
	public void setBulletDamage(int bulletDamage) {
		this.bulletDamage = bulletDamage;
	}
	
	//Bullet Movement
	public void movement() {
		
	}
	
	//Bullet Update
	public void update() {

	}
	
	//Bullet Dispose
	public void dispose() {
		
	}
}
