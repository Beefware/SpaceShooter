package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class TimeFreeze extends Powerup {

    // Texture for the Time Freeze power-up
    private static Texture freezeTexture;

    // Fixed dimensions for the power-up sprite
    private static final float FIXED_WIDTH = 60;
    private static final float FIXED_HEIGHT = 60;

    // Duration for which the Time Freeze effect lasts
    private static final float POWERUP_DURATION = 5.0f;

    // Flag to indicate if time is currently frozen
    private static boolean timeFrozen = false;

    // Getter for the timeFrozen flag
    public static boolean isTimeFrozen() {
        return timeFrozen;
    }

    // Setter for the timeFrozen flag
    public static void setTimeFrozen(boolean frozen) {
        timeFrozen = frozen;
    }

    // Ends the Time Freeze effect early
    public static void endTimeFreeze() {
        if (timeFrozen) {
            setTimeFrozen(false);
            System.out.println("Time Freeze ended early due to player's action.");
        }
    }

    // Constructor to initialize the Time Freeze power-up
    public TimeFreeze(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);

        // Load the texture if it hasn't been loaded yet
        if (freezeTexture == null) {
            freezeTexture = new Texture(Gdx.files.internal("freeze.png")); 
        }
    }

    // Applies the Time Freeze effect
    @Override
    public void applyEffect() {
        // If time is already frozen, do nothing
        if (timeFrozen) return;

        // Set time as frozen
        setTimeFrozen(true);

        // Schedule a task to unfreeze time after the duration ends
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setTimeFrozen(false);
            }
        }, POWERUP_DURATION);
    }

    // Draws the Time Freeze power-up on the screen
    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(freezeTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
        batch.end();
    }

    // Returns the bounding rectangle for collision detection
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    // Disposes of the texture to free up resources
    public static void disposeTexture() {
        if (freezeTexture != null) {
            freezeTexture.dispose();
            freezeTexture = null;
        }
    }
}
