package io.github.some_example_name.lwjgl3.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	private float x;
	private float y;
	private Color color;
	private float speed;
	private int health;
	private Rectangle bounds;
	
	public Entity() {
		
	}
	
	//Create Entity
	public Entity(float x,float y ,Color color, float speed, int health) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.speed = speed;
		this.health = health;
	}

	//Return x value
	public float getX() {
		return x;
	}

	//Set x value
	public void setX(float x) {
		this.x = x;
	}

	//return y value
	public float getY() {
		return y;
	}

	//set y value
	public void setY(float y) {
		this.y = y;
	}

	//return entity color
	public Color getColor() {
		return color;
	}

	//set entity color
	public void setColor(Color color) {
		this.color = color;
	}

	//return entity speed
	public float getSpeed() {
		return speed;
	}

	//set entity speed
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	//return entity health
	public int getHealth() {
		return health;
	}

	//set entity health
	public void setHealth(int health) {
		this.health = health;
	}
	
	//draw shapes
	public void draw(ShapeRenderer shape) {
		
	}
	
	//draw sprites
	public void draw(SpriteBatch batch) {
		
	}
	
	//Entity movements
	public void movement() {
	}
	
	//abstract void
	public abstract void update();
	
	private void updateBounds() {
        bounds.setPosition(x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }
	
	public void dispose() {
		this.dispose();
	}
	
	
}


