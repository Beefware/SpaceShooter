package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
	private float x; // X-coordinate of the entity
	private float y; // Y-coordinate of the entity
	private Color color; // Color of the entity
	private float speed; // Speed of the entity
	private int health; // Health of the entity
	private Rectangle bounds; // Bounding box for collision detection
	
	// Default constructor
	public Entity() {
	}
	
	// Constructor to initialize an entity with specific attributes
	public Entity(float x, float y, Color color, float speed, int health) {
		this.x = x; // Set the X-coordinate
		this.y = y; // Set the Y-coordinate
		this.color = color; // Set the color
		this.speed = (speed <= 0) ? 200.0f : speed; // Ensure speed is never 0.0
		this.health = health; // Set the health
	}

	// Return the X-coordinate of the entity
	public float getX() {
		return x;
	}

	// Set the X-coordinate of the entity
	public void setX(float x) {
		this.x = x;
	}

	// Return the Y-coordinate of the entity
	public float getY() {
		return y;
	}

	// Set the Y-coordinate of the entity
	public void setY(float y) {
		this.y = y;
	}

	// Return the color of the entity
	public Color getColor() {
		return color;
	}

	// Set the color of the entity
	public void setColor(Color color) {
		this.color = color;
	}

	// Return the speed of the entity
	public float getSpeed() {
		return speed;
	}

	// Set the speed of the entity
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	// Return the health of the entity
	public int getHealth() {
		return health;
	}

	// Set the health of the entity
	public void setHealth(int health) {
		this.health = health;
	}
	
	// Draw shapes for the entity (to be implemented by subclasses)
	public void draw(ShapeRenderer shape) {
	}
	
	// Draw sprites for the entity (to be implemented by subclasses)
	public void draw(SpriteBatch batch) {
	}
	
	// Handle entity movements (to be implemented by subclasses)
	public void movement() {
	}
	
	// Abstract method to update the entity (must be implemented by subclasses)
	public abstract void update();
	
	// Update the position of the bounding box
	private void updateBounds() {
		bounds.setPosition(x, y);
	}

	// Return the bounding box of the entity
	public Rectangle getBounds() {
		return bounds;
	}
	
	// Dispose of resources used by the entity
	public void dispose() {
		this.dispose();
	}
}
