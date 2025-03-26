package io.github.some_example_name.lwjgl3.EntityManager;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import io.github.some_example_name.lwjgl3.PowerupManager.Powerup;

public class TriangleProjectile extends Entity {

    private Player player; // Reference to the player that fired the projectile
    private int projectileDamage; // Damage dealt by the projectile
    private Rectangle bounds; // Rectangle representing the projectile's bounds for collision detection
    private boolean shouldRemove = false; // Flag to indicate if the projectile should be removed
    private boolean justFired = false; // Flag to indicate if the projectile was just fired

    // Default constructor
    public TriangleProjectile() {
    }

    // Constructor to initialize the projectile with specific properties
    public TriangleProjectile(Color color, float speed, int damage, Player player) {
        super(player.getX() + 50, player.getY(), color, speed, damage); // Initialize the base Entity class
        this.projectileDamage = damage;
        this.player = player;

        // Initialize bounds with the projectile's position and size
        this.bounds = new Rectangle(getX(), getY(), 15, 35);
    }

    // Draw the projectile on the screen
    public void draw(ShapeRenderer shape) {
        if (shouldRemove) return; // Skip drawing if the projectile should be removed

        shape.begin(ShapeRenderer.ShapeType.Filled);
        if (this.getY() > 5) { // Ensure the projectile is within the visible area
            shape.setColor(this.getColor());
            shape.rect(this.getX(), this.getY(), 15, 35); // Draw the projectile as a rectangle
        }
        shape.end();
    }

    // Get the bounds of the projectile, updating its position
    public Rectangle getBounds() {
        bounds.setPosition(getX(), getY());
        return bounds;
    }

    // Get the damage dealt by the projectile
    public int getProjectileDamage() {
        return projectileDamage;
    }

    // Set the damage dealt by the projectile
    public void setProjectileDamage(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    // Get the player that fired the projectile
    public Player getTriangle() {
        return this.player;
    }

    // Handle the movement of the projectile (currently empty)
    public void movement() {
    }

    // Reset the projectile's position to the player's position
    public void reset() {
        this.setY(this.getTriangle().getY());
        this.setX(this.getTriangle().getX() + 50);
    }

    // Check if the projectile was just fired
    public boolean isJustFired() {
        return justFired;
    }

    // Set the "just fired" status of the projectile
    public void setJustFired(boolean justFired) {
        this.justFired = justFired;
    }

    // Update the projectile's state
    public void update() {
        if (!shouldRemove) {
            bounds.setPosition(getX(), getY()); // Update bounds to match the current position
        }
    }

    // Check for collisions with powerups and entities
    public void checkCollision(List<Powerup> powerups, List<Entity> entities) {
        if (shouldRemove) return; // Skip collision checks if the projectile should be removed

        // Handle collisions with powerups
        Iterator<Powerup> powerupIterator = powerups.iterator();
        while (powerupIterator.hasNext()) {
            Powerup powerup = powerupIterator.next();
            if (this.getBounds().overlaps(powerup.getBounds())) {
                powerup.applyEffect(); // Apply the powerup's effect
                powerupIterator.remove(); // Remove the powerup
                shouldRemove = true; // Mark the projectile for removal
                return;
            }
        }

        // Handle collisions with entities
        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();

            // Handle collisions with MathOptions entities
            if (entity instanceof MathOptions) {
                MathOptions mOptions = (MathOptions) entity;

                if (this.getBounds().overlaps(mOptions.getBounds1())) {
                    if (mOptions.isOption1()) {
                        mOptions.correctOptionHit(); // Correct option hit
                    } else {
                        player.damage(); // Player takes damage
                    }

                    shouldRemove = true; // Mark the projectile for removal
                    return;
                } else if (this.getBounds().overlaps(mOptions.getBounds2())) {
                    if (mOptions.isOption2()) {
                        mOptions.correctOptionHit(); // Correct option hit
                    } else {
                        player.damage(); // Player takes damage
                    }

                    shouldRemove = true; // Mark the projectile for removal
                    return;
                }
            }

            // Handle collisions with Circle entities (excluding MathOptions)
            if (entity instanceof Circle && !(entity instanceof MathOptions)) {
                if (this.getBounds().overlaps(entity.getBounds())) {
                    entityIterator.remove(); // Remove the entity
                    shouldRemove = true; // Mark the projectile for removal
                    return;
                }
            }
        }
    }

    // Dispose of resources used by the projectile
    public void dispose() {
        player.dispose(); // Dispose of the player
        this.dispose(); // Dispose of the projectile
    }
}
