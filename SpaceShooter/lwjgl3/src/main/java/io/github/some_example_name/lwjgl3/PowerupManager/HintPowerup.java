package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import com.badlogic.gdx.utils.Timer;

public class HintPowerup extends Powerup {

    private static Texture hintTexture; // Load once
    private static final float FIXED_WIDTH = 60;
    private static final float FIXED_HEIGHT = 60;
    private static final float POWERUP_DURATION = 5.0f; // Lasts for 5 seconds

    public HintPowerup(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);

        if (hintTexture == null) {
            hintTexture = new Texture(Gdx.files.internal("hint.png")); // Ensure "hint.png" exists
            System.out.println("💡 Hint Power-up texture loaded.");
        }
    }

    @Override
    public void applyEffect() {
        System.out.println("💡 Hint Power-up Activated! Correct answer highlighted.");

        MathOptions.setRevealAnswer(true); // Enable highlighting of the correct answer

        // Start the power-up effect timer in PowerupManager
        PowerupManager powerupManager = PowerupManager.getInstance();
        powerupManager.startPowerupTimer(POWERUP_DURATION);

        // Restore normal answer colors after 5 seconds
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                MathOptions.setRevealAnswer(false); // Disable highlighting
                System.out.println("💡 Hint Power-up Expired! Answer no longer highlighted.");
            }
        }, POWERUP_DURATION);
    }


    @Override
    public void draw(SpriteBatch batch) {
        if (hintTexture != null) {
            if (!batch.isDrawing()) batch.begin(); // Ensure batch has started
            batch.draw(hintTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
            batch.end(); // ✅ End batch after drawing
        }
    }


    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    public static void disposeTexture() {
        if (hintTexture != null) {
            hintTexture.dispose();
            hintTexture = null;
        }
    }
}
