package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;

public class PauseScene extends Scene {

    // Rendering components
    private SpriteBatch batch;
    private Texture gameBackground;
    private BitmapFont titleFont, buttonFont;

    // Button bounds for resume and quit
    private Rectangle resumeButtonBounds, quitButtonBounds;

    // Animation time for button scaling
    private float buttonAnimationTime = 0;

    // Reference to the SceneManager
    private SceneManager sceneManager;

    // Constructor to initialize the PauseScene
    public PauseScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        batch = new SpriteBatch();

        // Initialize fonts
        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.WHITE);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        // Load background texture
        gameBackground = new Texture("space_black.jpg");

        // Define button bounds
        resumeButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 200, 400, 80);
        quitButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 100, 400, 80);
    }

    // Render method to draw the pause screen
    @Override
    public void render(SpriteBatch batch) {
        // Clear the screen with a dark blue color
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Begin drawing
        batch.begin();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw the title
        String title = "Game Paused";
        GlyphLayout titleLayout = new GlyphLayout(titleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() * 0.75f;
        titleFont.draw(batch, title, titleX, titleY);

        // Button animation (scaling effect)
        float scale = 1 + MathUtils.sin(buttonAnimationTime * 2) * 0.1f;
        buttonFont.getData().setScale(scale);

        batch.end();

        // Draw buttons
        drawButton(resumeButtonBounds, "Resume");
        drawButton(quitButtonBounds, "Quit");
    }

    // Update method to handle input and button clicks
    @Override
    public void update() {
        // Check for mouse click
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Check if resume button is clicked
            if (resumeButtonBounds.contains(mouseX, mouseY)) {
                // Resume the game
                GameScene gameScene = sceneManager.getGameScene();
                PowerupManager.getInstance().startSpawning();
                gameScene.resumeGame();
                sceneManager.setCurrentScene(gameScene);

            // Check if quit button is clicked
            } else if (quitButtonBounds.contains(mouseX, mouseY)) {
                // Quit to title screen
                PowerupManager.getInstance().stopSpawning();
                Scene titleScene = new TitleScreen(sceneManager);
                sceneManager.setCurrentScene(titleScene);
            }
        }
    }

    // Helper method to draw a button with text
    private void drawButton(Rectangle bounds, String text) {
        buttonFont.getData().setScale(2);
        GlyphLayout layout = new GlyphLayout(buttonFont, text);
        float textX = bounds.x + (bounds.width - layout.width) / 2;
        float textY = bounds.y + (bounds.height + layout.height) / 2;
        batch.begin();
        buttonFont.draw(batch, text, textX, textY);
        batch.end();
    }

    // Dispose method to release resources
    public void dispose() {
        titleFont.dispose();
        buttonFont.dispose();
        gameBackground.dispose();
    }
}
