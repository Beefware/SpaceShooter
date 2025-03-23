package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;
import io.github.some_example_name.lwjgl3.AudioManager.MusicPlayer;
import io.github.some_example_name.lwjgl3.AudioManager.SoundEffect;
import io.github.some_example_name.lwjgl3.EntityManager.EntityManager;
import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.SceneManager.SceneManager;
import io.github.some_example_name.lwjgl3.SceneManager.TitleScreen;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupAudioListener;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;
import io.github.some_example_name.lwjgl3.PowerupManager.TimeFreeze;

public class GameMaster extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Triangle triangle;
    private MathOptions mOptions;
    private TriangleProjectile trProj;
    private EntityManager entityManager;
    private AudioManager audioManager;
    private SceneManager sceneManager;  // SceneManager to handle different scenes
    
    private float countdownTime = 3;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        
        // Initialize the AudioManager and play background music
        audioManager = new AudioManager();
        MusicPlayer backgroundMusic = new MusicPlayer("background_music.wav");
        audioManager.setBackgroundMusic(backgroundMusic);
        audioManager.playBackgroundMusic();
        // Load sound effects
        audioManager.loadDefaultSounds();
        System.out.println("Loaded sound effects:");
        for (String key : audioManager.getSoundEffects().keySet()) {
            System.out.println("- " + key);
        }
        sceneManager = new SceneManager(this, audioManager);
        PowerupManager.getInstance().addCollectionListener(new PowerupAudioListener(audioManager));
        
     // Add TitleScreen scene to the scene manager
        sceneManager.setCurrentScene(new TitleScreen(sceneManager));
    }
    // Resets the game
    public void resetGame() {
        countdownTime = 3;
        MathOptions.resetScore();
        
        TimeFreeze.setTimeFrozen(false);
        
        float initialSpeed = 20.0f;
        triangle = new Triangle("Player.png", 200, 5, initialSpeed, 3);

        trProj = new TriangleProjectile(Color.BLUE, 10, 1, triangle);

        entityManager = new EntityManager();
        entityManager.addEntities(triangle);
        entityManager.addEntities(mOptions);
        entityManager.addEntities(trProj);
        
    }

    @Override
    public void render() {
        // Call the update and render methods of the SceneManager to handle the current scene
        sceneManager.update();  // Update the current scene
        sceneManager.render(batch);  // Render the current scene
    }


    @Override
    public void dispose() {
        batch.dispose();
        shape.dispose();
        audioManager.dispose();
    }
}
