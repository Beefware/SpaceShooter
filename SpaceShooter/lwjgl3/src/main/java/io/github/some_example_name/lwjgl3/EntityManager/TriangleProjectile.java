package io.github.some_example_name.lwjgl3.EntityManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.PowerupManager.Powerup; 
import java.util.Iterator;
import java.util.List;


public class TriangleProjectile extends Entity {

    private Triangle triangle;
    private int projectileDamage;
    private Rectangle bounds;
    private boolean shouldRemove = false;

    public TriangleProjectile() {
    }

    public TriangleProjectile(Color color, float speed, int damage, Triangle triangle) {
        // Create TriangleProjectile
        super(triangle.getX() + 50, triangle.getY(), color, speed, damage);
        this.projectileDamage = damage;
        this.triangle = triangle;

        // ✅ Initialize bounds with correct position and size
        this.bounds = new Rectangle(getX(), getY(), 15, 35);
    }

    // Draw Projectile
    public void draw(ShapeRenderer shape) {
    	if (shouldRemove) return;
    	
        shape.begin(ShapeRenderer.ShapeType.Filled);
        if (this.getY() > 5) {
            shape.setColor(this.getColor());
            shape.rect(this.getX(), this.getY(), 15, 35); // Match bounds size
        }
        shape.end();
    }

    // ✅ Updated `getBounds()` to always reflect the latest projectile position
    public Rectangle getBounds() {
        bounds.setPosition(getX(), getY());
        return bounds;
    }

    // Return ProjectileDamage
    public int getProjectileDamage() {
        return projectileDamage;
    }

    // Set ProjectileDamage
    public void setProjectileDamage(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    public Triangle getTriangle() {
        return this.triangle;
    }

    public void movement() {
    }
    
    public void reset() {
    	this.setY(this.getTriangle().getY());
        this.setX(this.getTriangle().getX()+50);
    }

    public void update() {
    	if (!shouldRemove) {
        bounds.setPosition(getX(), getY());
       
        }
    }
    
    public void checkCollision(List<Powerup> powerups, List<Entity> entities) {
    	if (shouldRemove) return; // Don't process again!

        // Handle powerups
        Iterator<Powerup> powerupIterator = powerups.iterator();
        while (powerupIterator.hasNext()) {
            Powerup powerup = powerupIterator.next();
            if (this.getBounds().overlaps(powerup.getBounds())) {
                powerup.applyEffect();
                powerupIterator.remove();
                shouldRemove = true;
                return; // ✅ Exit after handling
            }
        }

        // Handle entities
        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();

            if (entity instanceof MathOptions) {
                MathOptions mOptions = (MathOptions) entity;

                if (this.getBounds().overlaps(mOptions.getBounds1())) {
                    if (mOptions.isOption1()) {
                        mOptions.correctOptionHit();
                    } else {
                        triangle.damage();
                    }

                    shouldRemove = true;
                    return; // ✅ Exit after handling
                } 
                else if (this.getBounds().overlaps(mOptions.getBounds2())) { // ✅ else if prevents double check
                    if (mOptions.isOption2()) {
                        mOptions.correctOptionHit();
                    } else {
                        triangle.damage();
                    }

                    shouldRemove = true;
                    return; // ✅ Exit after handling
                }
            }

            if (entity instanceof Circle && !(entity instanceof MathOptions)) {
                if (this.getBounds().overlaps(entity.getBounds())) {
                    entityIterator.remove();
                    shouldRemove = true;
                    return;
                }
            }
        }
    }

    public void dispose() {
        triangle.dispose();
        this.dispose();
    }
}
