package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;
import io.github.some_example_name.lwjgl3.CollisionManager.CollisionManager;
import io.github.some_example_name.lwjgl3.EntityManager.Enemy;
import io.github.some_example_name.lwjgl3.EntityManager.EnemyBullet;
import io.github.some_example_name.lwjgl3.EntityManager.EntityManager;
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.PlayerBullet;
import io.github.some_example_name.lwjgl3.MovementManager.MovementManager;

public class GameScene extends Scene {
	
	private Texture gameBackground;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private EntityManager em;
    private Player player;
    private Enemy enemy;
    private PlayerBullet bullet;
    private EnemyBullet enBullet;
    private MovementManager movementManager;
    private AudioManager audioManager;
    private SceneManager sceneManager;
    
    private int bgheight=0;
    
    private boolean isPaused = false;  // Flag to track if the game is paused
    

    public GameScene(SceneManager sceneManager) {
    	this.sceneManager = sceneManager;
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        em = new EntityManager();
        
        // Initialize player, enemy, and bullets
        player = new Player("Player.png", 200, 5, 0, 3);
        enemy = new Enemy("Enemy.png", 100, 800, 0, 1);
        bullet = new PlayerBullet(Color.BLUE, 10, 1, player, enemy);
        enBullet = new EnemyBullet(Color.RED, 10, 1, player, enemy);
        
        // Add entities to the EntityManager
        em.addEntities(player);
        em.addEntities(enemy);
        em.addEntities(bullet);
        em.addEntities(enBullet);
        
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
    		em.draw(batch, shape);
    		em.update();
    	}
    }
    

    
    @Override
    public void update() {
        if (!isPaused) {

            // Game logic (e.g., collisions, movement)
            if (CollisionManager.checkPlayerBulletCollision(bullet, enemy)) {
            	// Play explosion sound
            	audioManager.playExplosionSound();
            }
            CollisionManager.checkEnemyBulletCollision(enBullet, player);

            if (player.getHealth() < 1) {
            	Scene gameOverScene = new GameOverScene(sceneManager);
            	sceneManager.setCurrentScene(gameOverScene);
            }
            
            em.movement(movementManager);
            em.update();
        }
        
        // Check for pause input
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            isPaused = true;
            // Save the reference to the current GameScene
            Scene currentScene = sceneManager.getCurrentScene();
            if (currentScene instanceof GameScene) {
                sceneManager.setGameScene((GameScene) currentScene);  // Save reference to GameScene
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

