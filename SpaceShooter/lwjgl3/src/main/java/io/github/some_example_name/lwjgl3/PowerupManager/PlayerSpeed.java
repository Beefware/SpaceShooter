package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerSpeed extends Powerup {

    private static Texture speedTexture; // Texture for the speed power-up, loaded once
    private static final float FIXED_WIDTH = 60; // Fixed width of the power-up
    private static final float FIXED_HEIGHT = 60; // Fixed height of the power-up

    // Constructor to initialize the PlayerSpeed power-up
    public PlayerSpeed(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);

        // Load the texture if it hasn't been loaded yet
        if (speedTexture == null) {
            speedTexture = new Texture(Gdx.files.internal("speed.png")); // Ensure "speed.png" exists
            System.out.println("Speed Power-up texture loaded.");
        }
    }

    // Apply the effect of the power-up 
    @Override
    public void applyEffect() {
        System.out.println("PlayerSpeed collected! Effect will be applied in PowerupManager.");
    }

    // Draw the power-up on the screen
    @Override
    public void draw(SpriteBatch batch) {
        if (speedTexture != null) {
            if (!batch.isDrawing()) batch.begin(); // Ensure the SpriteBatch has started
            batch.draw(speedTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
            batch.end(); // End the SpriteBatch after drawing
        }
    }

    // Get the bounding rectangle of the power-up for collision detection
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    // Dispose of the texture to free up resources
    public static void disposeTexture() {
        if (speedTexture != null) {
            speedTexture.dispose();
            speedTexture = null;
        }
    }
}
