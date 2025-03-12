package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class ExtraLife extends Powerup {

    private static Texture heartTexture; // ✅ Static so it's loaded once
    private static final float FIXED_WIDTH = 60;  
    private static final float FIXED_HEIGHT = 60; 

    public ExtraLife(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);
        
        // ✅ Load the texture once for all power-ups
        if (heartTexture == null) {
            heartTexture = new Texture(Gdx.files.internal("heart.png"));
            System.out.println("✅ Heart texture loaded.");
        }
    }

    @Override
    public void applyEffect() {
        System.out.println("❤️ Extra Life Gained!");
		// ✅ Add logic to increase player's life
    }
    
    @Override
    public void draw(SpriteBatch batch) {
        if (heartTexture != null) {
            batch.begin();
            batch.draw(heartTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
            batch.end();
        }
    }

    // ✅ Improved hitbox detection using Rectangle
    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    // Dispose the texture when the game closes
    public static void disposeTexture() {
        if (heartTexture != null) {
            heartTexture.dispose();
            heartTexture = null;
        }
    }
}
