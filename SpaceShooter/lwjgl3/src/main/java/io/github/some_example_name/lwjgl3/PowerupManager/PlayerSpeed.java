package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerSpeed extends Powerup {

    private static Texture speedTexture; // Load once
    private static final float FIXED_WIDTH = 60;
    private static final float FIXED_HEIGHT = 60;

    public PlayerSpeed(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);

        if (speedTexture == null) {
            speedTexture = new Texture(Gdx.files.internal("speed.png")); // Ensure "speed.png" exists
            System.out.println("⚡ Speed Power-up texture loaded.");
        }
    }

    @Override
    public void applyEffect() {
        System.out.println("⚡ PlayerSpeed collected! Effect will be applied in PowerupManager.");
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (speedTexture != null) {
            if (!batch.isDrawing()) batch.begin(); // Ensure batch has started
            batch.draw(speedTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
            batch.end(); // ✅ End batch after drawing
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    public static void disposeTexture() {
        if (speedTexture != null) {
            speedTexture.dispose();
            speedTexture = null;
        }
    }
}
