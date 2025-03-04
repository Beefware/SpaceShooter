package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;
import io.github.some_example_name.lwjgl3.CollisionManager.CollisionManager;
import io.github.some_example_name.lwjgl3.EntityManager.Circle;
import io.github.some_example_name.lwjgl3.EntityManager.EntityManager;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.MovementManager.MovementManager;
import io.github.some_example_name.lwjgl3.SceneManager.SceneManager;
import io.github.some_example_name.lwjgl3.SceneManager.TitleScreen;


public class GameMaster extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Triangle triangle;
    private Circle circle;
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
        audioManager.playBackgroundMusic();
        
        
        sceneManager = new SceneManager(this);
        
     // Add TitleScreen scene to the scene manager
        sceneManager.setCurrentScene(new TitleScreen(sceneManager));
    }
    // Resets the game
    public void resetGame() {
        countdownTime = 3;
        
        Circle.resetScore();
        // Reinitialize game entities
        
        triangle = new Triangle("Player.png", 200, 5, 0, 3);
        trProj = new TriangleProjectile(Color.BLUE, 10, 1, triangle);

        circle = new Circle(Color.RED, 120, 650, 50, 0, 1);

        entityManager = new EntityManager();
        entityManager.addEntities(triangle);
        entityManager.addEntities(circle);
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
