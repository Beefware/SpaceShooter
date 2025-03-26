package io.github.some_example_name.lwjgl3.SceneManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.EntityManager.MathOptions;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;

public class GameOverScene extends Scene {
    // Background texture for the game over screen
    private Texture gameBackground;
    // Fonts for displaying text
    private BitmapFont gameOverFont, leaderboardFont;
    // Bounds for retry and quit buttons
    private Rectangle retryButtonBounds, quitButtonBounds;
    // SpriteBatch for rendering
    private SpriteBatch batch;
    // Reference to the scene manager
    private SceneManager sceneManager;
    // Player's final score
    private int score;
    // Topic identifier
    private int topic;

    public GameOverScene(SceneManager sceneManager, int topic) {
        this.sceneManager = sceneManager;
        this.topic = topic;
        // Stop spawning power-ups
        PowerupManager.getInstance().stopSpawning();
        // Initialize fonts
        gameOverFont = new BitmapFont();
        gameOverFont.getData().setScale(3);
        gameOverFont.setColor(Color.RED);
        leaderboardFont = new BitmapFont();
        leaderboardFont.getData().setScale(2.5f);
        leaderboardFont.setColor(Color.YELLOW);
        // Define button bounds
        retryButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 100, 200, 200, 60);
        quitButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 110, 100, 200, 60);
        // Initialize SpriteBatch and background texture
        batch = new SpriteBatch();
        gameBackground = new Texture("space_black.jpg");
    }

    @Override
    public void render(SpriteBatch batch) {
        // Clear the screen with a dark blue color
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        // Draw the background
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw "Game Over" text
        String gameOverText = "GAME OVER";
        GlyphLayout layout = new GlyphLayout(gameOverFont, gameOverText);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = Gdx.graphics.getHeight() * 0.90f;
        gameOverFont.draw(batch, gameOverText, x, y);

        // Draw the final score
        score = MathOptions.getScore();
        String scoreText = "Final Score: " + score;
        GlyphLayout scoreLayout = new GlyphLayout(gameOverFont, scoreText);
        float scoreX = (Gdx.graphics.getWidth() - scoreLayout.width) / 2;
        float scoreY = Gdx.graphics.getHeight() / 2 - scoreLayout.height * 0.3f;
        gameOverFont.draw(batch, scoreText, scoreX, scoreY);

        // Read leaderboard scores
        FileHandle handle = Gdx.files.local("score.txt");
        String text = handle.exists() ? handle.readString() : "";
        String[] wordsArray = text.split("\n");
        List<Integer> numbers = new ArrayList<>();
        for (String word : wordsArray) {
            if (!word.isEmpty()) {
            numbers.add(Integer.parseInt(word));
            }
        }
        numbers.sort(Comparator.reverseOrder());

        // Save the top 3 scores back to the file
        StringBuilder topScores = new StringBuilder();
        for (int i = 0; i < Math.min(3, numbers.size()); i++) {
            topScores.append(numbers.get(i)).append("\n");
        }
        handle.writeString(topScores.toString(), false); // Overwrite with top 3 scores

        // Display leaderboard scores
        String leaderboardScore = topScores.toString();

        // Draw leaderboard title
        String leaderboardText = "Leaderboard:";
        GlyphLayout leaderboardLayout = new GlyphLayout(leaderboardFont, leaderboardText);
        float leaderboardX = (Gdx.graphics.getWidth() - leaderboardLayout.width) / 2;
        float leaderboardY = Gdx.graphics.getHeight() * 0.80f;
        leaderboardFont.draw(batch, leaderboardText, leaderboardX, leaderboardY);

        // Draw leaderboard scores
        GlyphLayout leaderboardscoreLayout = new GlyphLayout(leaderboardFont, leaderboardScore);
        float leaderboardscoreX = (Gdx.graphics.getWidth() - leaderboardscoreLayout.width) / 2;
        float leaderboardscoreY = Gdx.graphics.getHeight() * 0.73f;
        leaderboardFont.draw(batch, leaderboardScore, leaderboardscoreX, leaderboardscoreY);

        batch.end();

        // Draw retry and quit buttons
        drawButton(retryButtonBounds, "Retry");
        drawButton(quitButtonBounds, "Quit");
        }
        


        @Override
        public void update() {
        // Check for user input
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Check if retry button is clicked
            if (retryButtonBounds.contains(mouseX, mouseY)) {
                // Restart the game
                sceneManager.getGameMaster().resetGame();
                // Switch to the countdown scene
                Scene countdownScene = new CountdownScene(sceneManager, topic);
                sceneManager.setCurrentScene(countdownScene);
            } 
            // Check if quit button is clicked
            else if (quitButtonBounds.contains(mouseX, mouseY)) {
                // Stop spawning power-ups and switch to the title screen
                PowerupManager.getInstance().stopSpawning();
                Scene titleScene = new TitleScreen(sceneManager);
                sceneManager.setCurrentScene(titleScene);
            }
        }
    }

    private void drawButton(Rectangle bounds, String text) {
        // Draw a button with the specified text
        GlyphLayout layout = new GlyphLayout(gameOverFont, text);
        float textX = bounds.x + (bounds.width - layout.width) / 2;
        float textY = bounds.y + (bounds.height + layout.height) / 2;
        batch.begin();
        gameOverFont.draw(batch, text, textX, textY);
        batch.end();
    }

    public void dispose() {
        // Dispose of resources
        gameOverFont.dispose();
        batch.dispose();
        gameBackground.dispose();
    }
}
