package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;

public class CountdownScene extends Scene {
    private Texture gameBackground; // Background texture for the countdown scene
    private float countdownTime = 3; // Countdown timer in seconds
    private BitmapFont timerFont; // Font used to display the countdown timer
    private SpriteBatch batch; // SpriteBatch for rendering
    private SceneManager sceneManager; // Reference to the scene manager
    private int topic; // Topic identifier for the game scene

    public CountdownScene(SceneManager sceneManager, int topic) {
        this.sceneManager = sceneManager;
        this.topic = topic;
        timerFont = new BitmapFont(); // Initialize the font
        timerFont.getData().setScale(3); // Set font scale
        timerFont.setColor(Color.RED); // Set font color
        batch = new SpriteBatch(); // Initialize the SpriteBatch
        gameBackground = new Texture("space_black.jpg"); // Load the background texture
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0, 0, 0.2f, 1); // Clear the screen with a dark blue color
        batch.begin();
        // Draw the background texture
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Calculate and display the countdown timer
        String timerText = String.valueOf((int) Math.ceil(countdownTime));
        GlyphLayout timerLayout = new GlyphLayout(timerFont, timerText);
        float timerX = (Gdx.graphics.getWidth() - timerLayout.width) / 2; // Center the timer horizontally
        float timerY = Gdx.graphics.getHeight() / 2; // Center the timer vertically
        timerFont.draw(batch, timerText, timerX, timerY); // Draw the timer text
        batch.end();
    }

    @Override
    public void update() {
        countdownTime -= Gdx.graphics.getDeltaTime(); // Decrease the countdown timer
        if (countdownTime <= 0) {
            // Transition to the game scene when the countdown ends
            PowerupManager.getInstance().startSpawning(); // Start spawning power-ups
            Scene gameScene = new GameScene(sceneManager, topic); // Create the game scene
            sceneManager.setCurrentScene(gameScene); // Set the game scene as the current scene
            System.out.println("Topic:" + topic); // Log the topic
        }
    }

    public void dispose() {
        // Dispose of resources to prevent memory leaks
        timerFont.dispose();
        batch.dispose();
        gameBackground.dispose();
    }
}
