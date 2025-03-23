package io.github.some_example_name.lwjgl3.SceneManager;

import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;

import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.files.FileHandle;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class GameOverScene extends Scene {
	private Texture gameBackground;
    private BitmapFont gameOverFont, leaderboardFont;
    private Rectangle retryButtonBounds, quitButtonBounds;
    private SpriteBatch batch;
    private SceneManager sceneManager;
    private int score;
    private int topic;

    public GameOverScene(SceneManager sceneManager, int topic) {
    	this.sceneManager = sceneManager;
    	this.topic = topic;
    	PowerupManager.getInstance().stopSpawning();
        gameOverFont = new BitmapFont();
        gameOverFont.getData().setScale(3);
        gameOverFont.setColor(Color.RED);
        leaderboardFont = new BitmapFont();
        leaderboardFont.getData().setScale(2.5f);
        leaderboardFont.setColor(Color.BLUE);
        retryButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 100, 200, 200, 60);
        quitButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 110, 100, 200, 60);
        batch = new SpriteBatch();
        gameBackground = new Texture("space_black.jpg");
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // "Game Over" text
        String gameOverText = "GAME OVER";
        GlyphLayout layout = new GlyphLayout(gameOverFont, gameOverText);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = Gdx.graphics.getHeight() * 0.90f;
        gameOverFont.draw(batch, gameOverText, x, y);
        // Score text
        score = MathOptions.getScore();
        String scoreText = "Final Score: " + score;
        GlyphLayout scoreLayout = new GlyphLayout(gameOverFont, scoreText);
        float scoreX = (Gdx.graphics.getWidth() - scoreLayout.width) / 2;
        float scoreY = Gdx.graphics.getHeight() / 2 - scoreLayout.height * 0.3f;
        gameOverFont.draw(batch, scoreText, scoreX, scoreY);
        
        
        FileHandle handle = Gdx.files.local("score.txt");
        String text = handle.readString();
        String[] wordsArray = text.split("\n");
        
        List<Integer> numbers = new ArrayList<>();

        for (String word : wordsArray) {
            numbers.add(Integer.parseInt(word));
        }
        
        numbers.sort(Comparator.reverseOrder());
        
        String leaderboardScore = "";
        for (int i = 0; i < Math.min(3, numbers.size()); i++) {
        	leaderboardScore += numbers.get(i) + "\n";
        }
        
        String leaderboardText = "Leaderboard:";
        GlyphLayout leaderboardLayout = new GlyphLayout(leaderboardFont, leaderboardText);
        float leaderboardX = (Gdx.graphics.getWidth() - leaderboardLayout.width) / 2;
        float leaderboardY = Gdx.graphics.getHeight() * 0.80f; 
        leaderboardFont.draw(batch, leaderboardText, leaderboardX, leaderboardY);
        
        GlyphLayout leaderboardscoreLayout = new GlyphLayout(leaderboardFont, leaderboardScore);
        float leaderboardscoreX = (Gdx.graphics.getWidth() - leaderboardscoreLayout.width) / 2;
        float leaderboardscoreY = Gdx.graphics.getHeight() * 0.73f; 
        leaderboardFont.draw(batch, leaderboardScore, leaderboardscoreX, leaderboardscoreY);
        
        
        batch.end();
        // Retry button
        drawButton(retryButtonBounds, "Retry");
        drawButton(quitButtonBounds, "Quit");
        
    }
    
    @Override
    public void update() {
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            
            if (retryButtonBounds.contains(mouseX, mouseY)) {
            	// Restart the game
            	sceneManager.getGameMaster().resetGame(); 
            	// Switch to the countdown scene
            	Scene countdownScene = new CountdownScene(sceneManager, topic); 
                sceneManager.setCurrentScene(countdownScene);
              // Switch to the Title Screen
            } else if (quitButtonBounds.contains(mouseX, mouseY)) {
            	PowerupManager.getInstance().stopSpawning();
            	Scene titleScene = new TitleScreen(sceneManager);
                sceneManager.setCurrentScene(titleScene);
            }
        }
    }

    private void drawButton(Rectangle bounds, String text) {
        GlyphLayout layout = new GlyphLayout(gameOverFont, text);
        float textX = bounds.x + (bounds.width - layout.width) / 2;
        float textY = bounds.y + (bounds.height + layout.height) / 2;
        batch.begin();
        gameOverFont.draw(batch, text, textX, textY);
        batch.end();
    }

    
    public void dispose() {
        gameOverFont.dispose();
        batch.dispose();
        gameBackground.dispose();

    }
}
