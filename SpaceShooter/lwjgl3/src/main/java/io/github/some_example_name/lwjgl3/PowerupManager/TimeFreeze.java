
package io.github.some_example_name.lwjgl3.PowerupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class TimeFreeze extends Powerup {

    private static Texture freezeTexture;
    private static final float FIXED_WIDTH = 60;
    private static final float FIXED_HEIGHT = 60;
    private static final float POWERUP_DURATION = 5.0f;
    
    private static boolean timeFrozen = false;

    public static boolean isTimeFrozen() {
        return timeFrozen;
    }

    public static void setTimeFrozen(boolean frozen) {
        timeFrozen = frozen;
    }

    public static void endTimeFreeze() {
        if (timeFrozen) {
            setTimeFrozen(false);
            System.out.println("Time Freeze ended early due to player's action.");
        }
    }

    public TimeFreeze(float x, float y, float radius, float speed) {
        super(x, y, radius, speed);

        if (freezeTexture == null) {
            freezeTexture = new Texture(Gdx.files.internal("freeze.png")); 
        }
    }

    @Override
    public void applyEffect() {
        if (timeFrozen) return;

        setTimeFrozen(true);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setTimeFrozen(false);
            }
        }, POWERUP_DURATION);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(freezeTexture, getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
        batch.end();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX() - FIXED_WIDTH / 2, getY() - FIXED_HEIGHT / 2, FIXED_WIDTH, FIXED_HEIGHT);
    }

    public static void disposeTexture() {
        if (freezeTexture != null) {
            freezeTexture.dispose();
            freezeTexture = null;
        }
    }
}
