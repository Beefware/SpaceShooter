package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;

public class HintPowerup extends Powerup {

    private static Texture hintTexture; // Static texture to ensure it's loaded only once
    private static final float FIXED_WIDTH = 60; // Fixed width of the power-up sprite
    private static final float FIXED_HEIGHT = 60; // Fixed height of the power-up sprite
    private static final float POWERUP_DURATION = 5.0f; // Duration of the power-up effect in seconds

    // Constructor to initialize the HintPowerup
    public HintPowerup(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);

        // Load the texture only once to optimize memory usage
        if (hintTexture == null) {
            hintTexture = new Texture(Gdx.files.internal("hint.png")); // Ensure "hint.png" exists in assets
            System.out.println("Hint Power-up texture loaded.");
        }
    }

    @Override
    public void applyEffect() {
        System.out.println("Hint Power-up Activated! Correct answer highlighted.");

        // Enable highlighting of the correct answer
        MathOptions.setRevealAnswer(true);

        // Start the power-up effect timer in PowerupManager
        PowerupManager powerupManager = PowerupManager.getInstance();
        powerupManager.startPowerupTimer(POWERUP_DURATION);

        // Schedule a task to disable the effect after the duration ends
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                MathOptions.setRevealAnswer(false); // Disable highlighting
                System.out.println("Hint Power-up Expired! Answer no longer highlighted.");
            }
        }, POWERUP_DURATION);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Draw the power-up sprite on the screen
        if (hintTexture != null) {
            if (!batch.isDrawing()) batch.begin(); // Ensure the batch has started
            batch.draw(hintTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
            batch.end(); // End the batch after drawing
        }
    }

    // Get the bounding rectangle of the power-up for collision detection
    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    // Dispose of the texture to free up memory when no longer needed
    public static void disposeTexture() {
        if (hintTexture != null) {
            hintTexture.dispose();
            hintTexture = null;
        }
    }
}
