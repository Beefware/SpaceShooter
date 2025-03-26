package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

// Circle class extends the Entity class and represents a circular entity in the game
public class Circle extends Entity {
	
	private float radius;	// Radius of the circle
	
	// Default constructor
	public Circle() {
		
	}
	
	// Constructor to create a Circle entity with specified properties
	public Circle(Color color, float x, float y, float radius, float speed, int health) {
		super(x, y, color, speed, health); // Call the parent class constructor
		this.radius = radius; // Set the radius of the circle
	}
	
	// Getter method to return the radius of the circle
	public float getRadius() {
		return radius;
	}

	// Setter method to update the radius of the circle
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	// Method to get the bounding rectangle of the circle
	public Rectangle getBounds() {
		return new Rectangle(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
	}

	// Method to draw the circle using a ShapeRenderer
	public void draw(ShapeRenderer shape) {
		// Implementation for drawing the circle with ShapeRenderer
	}
	
	// Method to draw the circle using a SpriteBatch
	public void draw(SpriteBatch batch) {
		// Implementation for drawing the circle with SpriteBatch
	}
	
	// Method to handle the movement of the circle
	public void movement() {
		// Implementation for circle movement logic
	}
	
	// Method to update the circle's state (e.g., handle collisions, respawn, etc.)
	public void update() {		
		// Implementation for updating the circle's state
	}
	
	// Method to dispose of resources used by the circle
	public void dispose() {
		this.dispose(); // Dispose of resources (potentially incorrect recursive call)
	}
	
}
