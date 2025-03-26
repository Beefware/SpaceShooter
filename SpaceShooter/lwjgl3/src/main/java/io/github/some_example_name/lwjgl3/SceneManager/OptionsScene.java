package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;

public class OptionsScene extends Scene {
    
    // Options screen variables
    private SpriteBatch batch; // Used for rendering textures and fonts
    private Texture gameBackground; // Background texture for the options screen
    private BitmapFont titleFont, buttonFont; // Fonts for the title and buttons
    private Rectangle volumeSliderBounds, soundSliderBounds, backButtonBounds, soundToggleBounds; // Button bounds
    private float buttonAnimationTime = 0; // Animation timer for button scaling
    private SceneManager sceneManager; // Reference to the SceneManager
    private float musicVolume = 1.0f; // Current music volume
    private float soundVolume = 1.0f; // Current sound effects volume
    private final float[] VOLUME_STEPS = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f}; // Volume levels
    private int currentMusicVolumeStep = 9; // Index for current music volume step
    private int currentSoundVolumeStep = 9; // Index for current sound volume step

    private boolean soundEnabled = true; // Flag to toggle sound on/off
    private AudioManager audioManager; // Reference to the AudioManager
    
    // Constructor to initialize the options scene
    public OptionsScene(SceneManager sceneManager, AudioManager audioManager) {
        this.sceneManager = sceneManager;  // Assign the SceneManager passed from GameMaster
        this.audioManager = audioManager; // Assign the AudioManager
        
        batch = new SpriteBatch(); // Initialize the SpriteBatch

        // Initialize fonts
        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.GOLD);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        // Load the background texture
        gameBackground = new Texture("space_black.jpg");

        // Define button positions and sizes
        volumeSliderBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 300, 400, 80);
        soundSliderBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 200, 400, 80); 
        soundToggleBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 100, 400, 80); 
        backButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 50, 400, 80);   
    }
    
    @Override
    public void render(SpriteBatch batch) {
        // Clear the screen with a dark blue color
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Begin rendering
        batch.begin();
        // Draw the background
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw the title
        String title = "Settings";
        GlyphLayout titleLayout = new GlyphLayout(titleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() * 0.75f;
        titleFont.draw(batch, title, titleX, titleY);

        // Button animation (scaling effect)
        float scale = 1 + MathUtils.sin(buttonAnimationTime * 2) * 0.1f;
        buttonFont.getData().setScale(scale);
        
        batch.end();

        // Draw buttons
        drawButton(volumeSliderBounds, "Music Volume: " + (int) (audioManager.getMusicVolume() * 100) + "%");
        drawButton(soundSliderBounds, "Effects Volume: " + (int) (audioManager.getSoundVolume() * 100) + "%");
        drawButton(soundToggleBounds, "Mute: " + (soundEnabled ? "Off" : "On"));
        drawButton(backButtonBounds, "Back");
    }
    
    public void update() {
        // Update the button animation timer
        buttonAnimationTime += Gdx.graphics.getDeltaTime();
        
        // Check for user input
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX(); // Get mouse X position
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Get mouse Y position (inverted)

            // Check if the user clicked on the volume slider
            if (volumeSliderBounds.contains(mouseX, mouseY)) {
                currentMusicVolumeStep = (currentMusicVolumeStep + 1) % VOLUME_STEPS.length;
                musicVolume = VOLUME_STEPS[currentMusicVolumeStep];
                audioManager.setMusicVolume(musicVolume);
            } 
            // Check if the user clicked on the sound slider
            else if (soundSliderBounds.contains(mouseX, mouseY)) {
                currentSoundVolumeStep = (currentSoundVolumeStep + 1) % VOLUME_STEPS.length;
                soundVolume = VOLUME_STEPS[currentSoundVolumeStep];
                audioManager.setSoundVolume(soundVolume);
            } 
            // Check if the user clicked on the mute toggle
            else if (soundToggleBounds.contains(mouseX, mouseY)) {
                soundEnabled = !soundEnabled;
                audioManager.muteAll(musicVolume, soundVolume);
            } 
            // Check if the user clicked on the back button
            else if (backButtonBounds.contains(mouseX, mouseY)) {
                Scene titleScene = new TitleScreen(sceneManager);
                sceneManager.setCurrentScene(titleScene);
            }
        }
    }
    
    // Helper method to draw a button with text
    private void drawButton(Rectangle bounds, String text) {
        buttonFont.getData().setScale(2); // Set font scale
        GlyphLayout layout = new GlyphLayout(buttonFont, text); // Layout for text
        float textX = bounds.x + (bounds.width - layout.width) / 2; // Center text horizontally
        float textY = bounds.y + (bounds.height + layout.height) / 2; // Center text vertically
        batch.begin();
        buttonFont.draw(batch, text, textX, textY); // Draw the text
        batch.end();
    }
    
    // Dispose resources to free memory
    public void dispose() {
        titleFont.dispose();
        buttonFont.dispose();
        gameBackground.dispose();
    }
}
