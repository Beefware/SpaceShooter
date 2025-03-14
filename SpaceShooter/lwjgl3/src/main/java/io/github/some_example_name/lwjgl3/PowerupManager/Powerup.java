package io.github.some_example_name.lwjgl3.PowerupManager;

import io.github.some_example_name.lwjgl3.EntityManager.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Powerup extends Circle {
    
    public Powerup(float x, float y, float radius, float speed) {
        super(null, x, y, radius, speed, 1); // Passing null for color
    }

    // 🔹 Each power-up must define its effect
    public abstract void applyEffect();
    
    public void draw(SpriteBatch batch) {
        // This method should be overridden in subclasses like ExtraLife
    }


    @Override
    public void update() {
        setY(getY() - getSpeed()); // Moves downward
        
    }
    public Rectangle getBounds() {
        return new Rectangle(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);
    }
}
