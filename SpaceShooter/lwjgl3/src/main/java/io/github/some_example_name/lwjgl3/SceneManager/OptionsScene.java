package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;




public class OptionsScene extends Scene{
	
    // Options screen variables
	private SpriteBatch batch;
    private Texture gameBackground;
    private BitmapFont titleFont, buttonFont;
    private Rectangle volumeSliderBounds, soundSliderBounds, backButtonBounds, soundToggleBounds;
    private float buttonAnimationTime = 0;
    private SceneManager sceneManager;
    private float musicVolume = 1.0f;
    private float soundVolume = 1.0f;
    private final float[] VOLUME_STEPS = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f};
    private int currentMusicVolumeStep = 9;
    private int currentSoundVolumeStep = 9;
    //private final Preferences prefs = Gdx.app.getPreferences("MyGamePreferences");

    private boolean soundEnabled = true;
    private AudioManager audioManager;
    
    public OptionsScene(SceneManager sceneManager, AudioManager audioManager) {
        this.sceneManager = sceneManager;  // Assign the SceneManager passed from GameMaster
        this.audioManager = audioManager;
        
        batch = new SpriteBatch();

        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.GOLD);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        gameBackground = new Texture("space_black.jpg");

        // Button positions
        volumeSliderBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 300, 400, 80);
        soundSliderBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 200, 400, 80); 
        soundToggleBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 100, 400, 80); 
        backButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 50, 400, 80);   
       
    }
    
    @Override
    public void render(SpriteBatch batch) {
    	
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        String title = "Settings";
        GlyphLayout titleLayout = new GlyphLayout(titleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() * 0.75f;
        titleFont.draw(batch, title, titleX, titleY);

        // Button animation
        float scale = 1 + MathUtils.sin(buttonAnimationTime * 2) * 0.1f;
        buttonFont.getData().setScale(scale);
        
        batch.end();
        drawButton(volumeSliderBounds, "Music Volume: " + (int) (audioManager.getMusicVolume() * 100) + "%");
        drawButton(soundSliderBounds, "Effects Volume: " + (int) (audioManager.getSoundVolume() * 100) + "%");
        drawButton(soundToggleBounds, "Mute: " + (soundEnabled ? "Off" : "On"));
        drawButton(backButtonBounds, "Back");
        
    }
    
    public void update() {
    	buttonAnimationTime += Gdx.graphics.getDeltaTime();
    	
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (volumeSliderBounds.contains(mouseX, mouseY)) {
            	currentMusicVolumeStep = (currentMusicVolumeStep + 1) % VOLUME_STEPS.length;
                musicVolume = VOLUME_STEPS[currentMusicVolumeStep];
                audioManager.setMusicVolume(musicVolume);
                //prefs.putFloat("musicVolume", musicVolume);
                //prefs.flush();
            } else if (soundSliderBounds.contains(mouseX, mouseY)) {
            	currentSoundVolumeStep = (currentSoundVolumeStep + 1) % VOLUME_STEPS.length;
                soundVolume = VOLUME_STEPS[currentSoundVolumeStep];
                audioManager.setSoundVolume(soundVolume);
                //prefs.putFloat("soundVolume", soundVolume);
                //prefs.flush();
            } else if (soundToggleBounds.contains(mouseX, mouseY)) {
                soundEnabled = !soundEnabled;
                audioManager.muteAll(musicVolume,soundVolume);
                //prefs.putBoolean("soundEnabled", soundEnabled);
                //prefs.flush();
            } else if (backButtonBounds.contains(mouseX, mouseY)) {
            	Scene titleScene = new TitleScreen(sceneManager);
                sceneManager.setCurrentScene(titleScene);
            }
        }
    	
    }
    
    private void drawButton(Rectangle bounds, String text) {
        buttonFont.getData().setScale(2);
        GlyphLayout layout = new GlyphLayout(buttonFont, text);
        float textX = bounds.x + (bounds.width - layout.width) / 2;
        float textY = bounds.y + (bounds.height + layout.height) / 2;
        batch.begin();
        buttonFont.draw(batch, text, textX, textY);
        batch.end();
    }
    
    
    public void dispose() {
        // Dispose resources
        titleFont.dispose();
        buttonFont.dispose();
        gameBackground.dispose();

    }

}
