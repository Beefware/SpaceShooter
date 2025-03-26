package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import io.github.some_example_name.lwjgl3.EntityManager.Player;
import io.github.some_example_name.lwjgl3.EntityManager.TriangleProjectile;
import io.github.some_example_name.lwjgl3.MovementManager.MovementManager;
import io.github.some_example_name.lwjgl3.PowerupManager.ExtraLife;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;

public class GameScene extends Scene {
    private Texture gameBackground; // Background texture
    private SpriteBatch batch; // Used for rendering sprites
    private ShapeRenderer shape; // Used for rendering shapes
    private EntityManager entityManager; // Manages game entities
    private Player player; // Player entity
    private MathOptions mOptions; // Math options entity
    private TriangleProjectile trProj; // Projectile entity
    private MovementManager movementManager; // Handles movement logic
    private AudioManager audioManager; // Handles audio
    private SceneManager sceneManager; // Manages scenes
    private PowerupManager powerupManager; // Manages power-ups
    private BitmapFont scoreFont; // Font for displaying score
    private int score; // Current score
    private int topic; // Topic for the game

    private int bgheight = 0; // Background scrolling height
    private boolean borderCollision = false; // Tracks border collision
    private boolean isPaused = false; // Flag to track if the game is paused

    public GameScene(SceneManager sceneManager, int topic) {
        this.sceneManager = sceneManager;
        this.audioManager = sceneManager.getAudioManager();
        this.topic = topic;

        // Initialize font for score display
        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(2);
        scoreFont.setColor(Color.RED);

        // Initialize rendering tools
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        // Initialize entity manager and power-up manager
        entityManager = new EntityManager();
        powerupManager = PowerupManager.getInstance();

        // Initialize player, math options, and projectile
        player = new Player("Player.png", 200, 5, 0, 3);
        mOptions = new MathOptions("Meteor.png", 1, 1, topic);
        trProj = new TriangleProjectile(Color.BLUE, 10, 1, player);

        // Add entities to the entity manager
        entityManager.addEntities(player);
        entityManager.addEntities(mOptions);
        entityManager.addEntities(trProj);

        // Load background texture
        gameBackground = new Texture("space_black.jpg");

        // Initialize movement manager
        movementManager = new MovementManager();
    }

    // Save the score to a file
    private void saveScoreToFile(int score) {
        FileHandle file = Gdx.files.local("score.txt");
        String scoreLine = score + "\n";
        file.writeString(scoreLine, true); // Append score to the file
    }

    @Override
    public void render(SpriteBatch batch) {
        // Clear the screen with a dark blue color
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Begin rendering
        batch.begin();

        // Draw scrolling background
        batch.draw(gameBackground, 0, bgheight, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(gameBackground, 0, bgheight + 630, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Display the score
        score = MathOptions.getScore();
        String scoreText = "Score: " + score;
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, scoreText);
        float scoreX = (Gdx.graphics.getWidth() - scoreLayout.width) - 10;
        float scoreY = Gdx.graphics.getHeight() - 10;
        scoreFont.draw(batch, scoreText, scoreX, scoreY);

        batch.end();

        // Scroll the background
        bgheight -= 1;
        if (bgheight < -630) {
            bgheight = 0;
        }

        // Render entities and power-ups if the game is not paused
        if (!isPaused) {
            entityManager.draw(batch, shape);
            entityManager.update();
            powerupManager.render(batch);
        }
    }

    @Override
    public void update() {
        if (!isPaused) {
            // Play sound when the projectile is fired
            if (trProj.isJustFired()) {
                audioManager.playSoundEffect("fire");
                trProj.setJustFired(false);
            }

            // Check for collisions
            CollisionResult projectileCollision = CollisionManager.checkTriangleProjectileCollision(trProj, mOptions);
            borderCollision = CollisionManager.checkCirclesBorderCollision(mOptions, player);

            // Handle collision results
            if (projectileCollision == CollisionResult.CORRECT_OPTION) {
                System.out.println("Correct answer! Playing 'correct' sound.");
                audioManager.playSoundEffect("correct");
            } else if (projectileCollision == CollisionResult.WRONG_OPTION) {
                System.out.println("Wrong answer! Playing 'wrong' sound.");
                audioManager.playSoundEffect("wrong");
            } else if (borderCollision) {
                System.out.println("Circle hit the border! Playing 'collision' sound.");
                audioManager.playSoundEffect("collision");
            }

            // Check if the player has lost all health
            if (player.getHealth() < 1) {
                saveScoreToFile(score); // Save the score
                Scene gameOverScene = new GameOverScene(sceneManager, topic);
                sceneManager.setCurrentScene(gameOverScene); // Switch to Game Over scene
            }

            // Update entity movements and power-ups
            entityManager.movement(movementManager);
            entityManager.update();
            powerupManager.update(Gdx.graphics.getDeltaTime());
            powerupManager.checkCollision(player, trProj);
        }

        // Check for pause input
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            isPaused = true; // Pause the game
            PowerupManager.getInstance().stopSpawning(); // Stop spawning power-ups

            // Save the current GameScene
            Scene currentScene = sceneManager.getCurrentScene();
            if (currentScene instanceof GameScene) {
                sceneManager.setGameScene((GameScene) currentScene);
            }

            // Switch to the PauseScene
            Scene pauseScene = new PauseScene(sceneManager);
            sceneManager.setCurrentScene(pauseScene);
        }
    }

    // Resume the game
    public void resumeGame() {
        isPaused = false;
    }

    // Dispose of resources
    public void dispose() {
        batch.dispose();
        shape.dispose();
        gameBackground.dispose();
        ExtraLife.disposeTexture();
        // Dispose of other resources
    }
}
