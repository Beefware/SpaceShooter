package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

// Class representing a projectile entity in the game
public class Projectile extends Entity {

	private int projectileDamage; // Damage dealt by the projectile
	private Rectangle bounds; // Rectangle used for collision detection

	// Default constructor
	public Projectile() {
	}

	// Constructor to create a projectile entity with specified properties
	public Projectile(float x, float y, Color color, float speed, int damage) {
		super(x, y, color, speed, 0); // Call the parent class constructor
		this.projectileDamage = damage; // Set the damage of the projectile
	}

	// Method to draw the projectile on the screen
	public void draw(ShapeRenderer shape) {
		shape.begin(ShapeRenderer.ShapeType.Filled); // Begin shape rendering
		shape.setColor(this.getColor()); // Set the color of the projectile
		shape.rect(this.getX(), this.getY(), 10, 30); // Draw the projectile as a rectangle
		shape.end(); // End shape rendering

		// Create a rectangle for collision detection
		bounds = new Rectangle(this.getX(), this.getY(), 10, 30);
	}

	// Method to return the collision bounds of the projectile
	public Rectangle getBounds() {
		return bounds;
	}

	// Method to get the damage dealt by the projectile
	public int getProjectileDamage() {
		return projectileDamage;
	}

	// Method to set the damage dealt by the projectile
	public void setProjectileDamage(int projectileDamage) {
		this.projectileDamage = projectileDamage;
	}

	// Method to handle projectile movement 
	public void movement() {
	}

	// Method to update the projectile's state 
	public void update() {
	}

	// Method to dispose of the projectile's resources
	public void dispose() {
		this.dispose();
	}
}
