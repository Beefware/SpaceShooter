package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;
import io.github.some_example_name.lwjgl3.CollisionManager.CollisionManager;
import io.github.some_example_name.lwjgl3.CollisionManager.CollisionManager.CollisionResult;
import io.github.some_example_name.lwjgl3.EntityManager.EntityManager;
import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.EntityManager.Triangle;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.MovementManager.MovementManager;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;
import io.github.some_example_name.lwjgl3.PowerupManager.ExtraLife;

public class GameScene extends Scene {
	private Texture gameBackground;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private EntityManager entityManager;
    private Triangle triangle;
    private MathOptions mOptions;
    private TriangleProjectile trProj;
    private MovementManager movementManager;
    private AudioManager audioManager;
    private SceneManager sceneManager;
    private PowerupManager powerupManager;
    private BitmapFont scoreFont;
    private int score;
    private int topic;
    
    private int bgheight=0;

    private boolean isPaused = false;  // Flag to track if the game is paused
    

    public GameScene(SceneManager sceneManager, int topic) {
    	this.sceneManager = sceneManager;
    	this.audioManager = sceneManager.getAudioManager();
    	this.topic = topic;
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(2);
        scoreFont.setColor(Color.RED);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        entityManager = new EntityManager();
        powerupManager = PowerupManager.getInstance();
        
        // Initialize player, enemy, and bullets
        triangle = new Triangle("Player.png", 200, 5, 0, 3);
        mOptions = new MathOptions("Meteor.png", 1, 1, topic);
        trProj = new TriangleProjectile(Color.BLUE, 10, 1, triangle);
        
        // Add entities to the EntityManager
        entityManager.addEntities(triangle);
        entityManager.addEntities(mOptions);
        entityManager.addEntities(trProj);
        
        gameBackground = new Texture("space_black.jpg");
        
        movementManager = new MovementManager();
    }

    @Override
    public void render(SpriteBatch batch) {
    	
    	ScreenUtils.clear(0, 0, 0.2f, 1);
    	batch.begin();
		batch.draw(gameBackground, 0, bgheight, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(gameBackground, 0, bgheight+630, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
        score = MathOptions.getScore();
        String scoreText = "Score: " + score;
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, scoreText);
        float scoreX = (Gdx.graphics.getWidth() - scoreLayout.width) - 10;
        float scoreY = Gdx.graphics.getHeight() - 10;
        scoreFont.draw(batch, scoreText, scoreX, scoreY);
		
		batch.end();
		bgheight-=1;
		if(bgheight<-630) {
			bgheight=0;
		}
    		
    	if (!isPaused) {
    		entityManager.draw(batch, shape);
    		entityManager.update();
    		
    		powerupManager.render(batch); 
    	}
    }
    

    
    @Override
    public void update() {
    	
        if (!isPaused) {
        	boolean borderCollision = false;
        	CollisionResult projectileCollision = CollisionResult.NO_COLLISION;
        	projectileCollision = CollisionManager.checkTriangleProjectileCollision(trProj, mOptions);
        	
        	
        	borderCollision = CollisionManager.checkCirclesBorderCollision(mOptions, triangle);
   		 	
        	if (projectileCollision == CollisionResult.CORRECT_OPTION) {
        	    System.out.println("Correct answer! Playing 'correct' sound.");
        	    audioManager.playSoundEffect("correct");
        	}
        	else if (projectileCollision == CollisionResult.WRONG_OPTION) {
        	    System.out.println("Wrong answer! Playing 'wrong' sound.");
        	    audioManager.playSoundEffect("wrong");
        	} else if (borderCollision) {
        		System.out.println("Circle hit the border! Playing 'collision' sound.");
                audioManager.playSoundEffect("collision");
        	}


            if (triangle.getHealth() < 1) {
            	Scene gameOverScene = new GameOverScene(sceneManager, topic);
            	sceneManager.setCurrentScene(gameOverScene);
            }
            
            entityManager.movement(movementManager);
            entityManager.update();
            
            powerupManager.update(Gdx.graphics.getDeltaTime());
            powerupManager.checkCollision(triangle, trProj);

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
        ExtraLife.disposeTexture();
        // Dispose of other resources
    }
    
}

