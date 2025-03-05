package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;
import io.github.some_example_name.lwjgl3.CollisionManager.CollisionManager;
import io.github.some_example_name.lwjgl3.EntityManager.Circle;
import io.github.some_example_name.lwjgl3.EntityManager.EntityManager;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.MovementManager.MovementManager;

public class GameScene extends Scene {
	private Texture gameBackground;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private EntityManager entityManager;
    private Triangle triangle;
    private Circle circle;
    private TriangleProjectile trProj;
    private MovementManager movementManager;
    private AudioManager audioManager;
    private SceneManager sceneManager;
    
    private int bgheight=0;

    private boolean isPaused = false;  // Flag to track if the game is paused
    

    public GameScene(SceneManager sceneManager) {
    	this.sceneManager = sceneManager;
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        entityManager = new EntityManager();
        
        // Initialize player, enemy, and bullets
        triangle = new Triangle("Player.png", 200, 5, 0, 3);
        circle = new Circle(Color.RED, 120, 650, 50, 0, 1);
        trProj = new TriangleProjectile(Color.BLUE, 10, 1, triangle);
        
        // Add entities to the EntityManager
        entityManager.addEntities(triangle);
        entityManager.addEntities(circle);
        entityManager.addEntities(trProj);
        
        gameBackground = new Texture("space_black.jpg");
        
        movementManager = new MovementManager();
        audioManager = new AudioManager();
    }

    @Override
    public void render(SpriteBatch batch) {
    	
    	ScreenUtils.clear(0, 0, 0.2f, 1);
    	batch.begin();
		batch.draw(gameBackground, 0, bgheight, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(gameBackground, 0, bgheight+630, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		bgheight-=1;
		if(bgheight<-630) {
			bgheight=0;
		}
    		
    	if (!isPaused) {
    		entityManager.draw(batch, shape);
    		entityManager.update();
    	}
    }
    

    
    @Override
    public void update() {
        if (!isPaused) {

            // Game logic (e.g., collisions, movement)
            if (CollisionManager.checkTriangleProjectileCollision(trProj, circle)) {
            	// Play explosion sound
            	audioManager.playExplosionSound();
            }
            
            
            if(CollisionManager.checkCirclesBorderCollision(circle, triangle)) {;
		    	// Play explosion sound
		    	audioManager.playExplosionSound();
            }

            if (triangle.getHealth() < 1) {
            	Scene gameOverScene = new GameOverScene(sceneManager);
            	sceneManager.setCurrentScene(gameOverScene);
            }
            
            entityManager.movement(movementManager);
            entityManager.update();
        }
        
        // Check for pause input
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            isPaused = true;
            // Save the reference to the current GameScene
            Scene currentScene = sceneManager.getCurrentScene();
            if (currentScene instanceof GameScene) {
            	// Save the current GameScene
                sceneManager.setGameScene((GameScene) currentScene);  
            }
            
            // Switch to the PauseScene
            Scene pauseScene = new PauseScene(sceneManager);
            sceneManager.setCurrentScene(pauseScene);
        }
    }
    
    public void resumeGame() {
        isPaused = false; // Resume the game
    }

    
    public void dispose() {
        batch.dispose();
        shape.dispose();
        gameBackground.dispose();
        // Dispose of other resources
    }
}

