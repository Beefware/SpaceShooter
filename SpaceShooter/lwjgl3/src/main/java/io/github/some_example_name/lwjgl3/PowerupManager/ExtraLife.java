package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ExtraLife extends Powerup {

    private static Texture heartTexture; // Static texture shared across all instances of ExtraLife
    private static final float FIXED_WIDTH = 60;  // Fixed width of the power-up
    private static final float FIXED_HEIGHT = 60; // Fixed height of the power-up

    // Constructor to initialize the ExtraLife power-up
    public ExtraLife(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);
        
        // Load the texture once for all instances of ExtraLife
        if (heartTexture == null) {
            heartTexture = new Texture(Gdx.files.internal("heart.png"));
            System.out.println("Heart texture loaded.");
        }
    }
    
    // Method to update the state of the power-up
    @Override
    public void applyEffect() {
        System.out.println("Extra Life Gained!");
    }
    
    // Method to draw the power-up on the screen
    @Override
    public void draw(SpriteBatch batch) {
        if (heartTexture != null) {
            batch.begin(); // Begin the SpriteBatch for rendering
            batch.draw(heartTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
            batch.end(); // End the SpriteBatch after rendering
        }
    }

    // Method to get the bounding rectangle for collision detection
    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    // Static method to dispose of the texture when the game closes
    public static void disposeTexture() {
        if (heartTexture != null) {
            heartTexture.dispose(); // Free up memory used by the texture
            heartTexture = null; // Set the reference to null
        }
    }
}
