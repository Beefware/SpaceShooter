package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;
import io.github.some_example_name.lwjgl3.AudioManager.MusicPlayer;
import io.github.some_example_name.lwjgl3.EntityManager.EntityManager;
import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupAudioListener;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;
import io.github.some_example_name.lwjgl3.PowerupManager.TimeFreeze;
import io.github.some_example_name.lwjgl3.SceneManager.SceneManager;
import io.github.some_example_name.lwjgl3.SceneManager.TitleScreen;

public class GameMaster extends ApplicationAdapter {
    private SpriteBatch batch; // Used for rendering 2D textures
    private ShapeRenderer shape; // Used for rendering shapes
    private Player player; // Player-controlled player entity
    private MathOptions mOptions; // Math-related options (e.g., score handling)
    private TriangleProjectile trProj; // Projectile fired by the triangle
    private EntityManager entityManager; // Manages all game entities
    private AudioManager audioManager; // Manages audio (music and sound effects)
    private SceneManager sceneManager; // Manages different game scenes
    
    private float countdownTime = 3; // Countdown timer for resetting the game

    @Override
    public void create() {
        // Initialize rendering tools
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        
        // Initialize the AudioManager and play background music
        audioManager = new AudioManager();
        MusicPlayer backgroundMusic = new MusicPlayer("background_music.wav");
        audioManager.setBackgroundMusic(backgroundMusic);
        audioManager.playBackgroundMusic();
        
        // Load default sound effects
        audioManager.loadDefaultSounds();
        System.out.println("Loaded sound effects:");
        for (String key : audioManager.getSoundEffects().keySet()) {
            System.out.println("- " + key);
        }
        
        // Initialize the SceneManager and set the initial scene
        sceneManager = new SceneManager(this, audioManager);
        PowerupManager.getInstance().addCollectionListener(new PowerupAudioListener(audioManager));
        
        // Add TitleScreen scene to the scene manager
        sceneManager.setCurrentScene(new TitleScreen(sceneManager));
    }

    // Resets the game state to its initial configuration
    public void resetGame() {
        countdownTime = 3; // Reset countdown timer
        MathOptions.resetScore(); // Reset the player's score
        
        TimeFreeze.setTimeFrozen(false); // Ensure time is not frozen
        
        // Initialize the player-controlled player with default parameters
        float initialSpeed = 20.0f;
        player = new Player("Player.png", 200, 5, initialSpeed, 3);

        // Initialize the player's projectile
        trProj = new TriangleProjectile(Color.BLUE, 10, 1, player);

        // Initialize the EntityManager and add entities
        entityManager = new EntityManager();
        entityManager.addEntities(player); // Add the triangle
        entityManager.addEntities(mOptions); // Add math options
        entityManager.addEntities(trProj); // Add the projectile
    }

    @Override
    public void render() {
        // Update and render the current scene using the SceneManager
        sceneManager.update(); // Update the current scene
        sceneManager.render(batch); // Render the current scene
    }

    @Override
    public void dispose() {
        // Dispose of resources to prevent memory leaks
        batch.dispose();
        shape.dispose();
        audioManager.dispose();
    }
}
