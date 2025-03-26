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

public class TitleScreen extends Scene {
    
    // Title screen variables
    private SpriteBatch batch; // Used for rendering textures
    private Texture gameBackground; // Background image
    private Texture gameTitle; // Title image
    private BitmapFont titleFont, buttonFont; // Fonts for title and buttons
    private Rectangle startButtonBounds, optionsButtonBounds, exitButtonBounds; // Button boundaries
    private float buttonAnimationTime = 0; // Animation timer for buttons
    private SceneManager sceneManager; // Reference to the scene manager
    
    public TitleScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;  
        
        batch = new SpriteBatch();

        // Initialize fonts
        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.GOLD);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        // Load textures
        gameBackground = new Texture("space_black.jpg");
        gameTitle = new Texture("MathShooterLogo.png");

        // Define button positions and sizes
        startButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 200, 400, 80);
        optionsButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 150, 400, 80);
        exitButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 100, 400, 80);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        
        // Clear the screen with a dark blue color
        ScreenUtils.clear(0, 0, 0.2f, 1);
        
        // Begin rendering
        batch.begin();
        // Draw the background
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        // Draw the title image
        float titleX = (Gdx.graphics.getWidth() - gameTitle.getWidth()/10) / 2;
        float titleY = Gdx.graphics.getHeight() * 0.4f;
        batch.draw(gameTitle, titleX, titleY, gameTitle.getWidth()/10, gameTitle.getHeight()/10);

        // Button animation (scaling effect)
        float scale = 1 + MathUtils.sin(buttonAnimationTime * 2) * 0.1f;
        buttonFont.getData().setScale(scale);
        
        batch.end();

        // Draw buttons
        drawButton(startButtonBounds, "Start Game");
        drawButton(optionsButtonBounds, "Options");
        drawButton(exitButtonBounds, "Exit");
    }
    
    public void update() {
        // Update the button animation timer
        buttonAnimationTime += Gdx.graphics.getDeltaTime();
        
        // Check for user input (mouse click)
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            
            // Check if the start button is clicked
            if (startButtonBounds.contains(mouseX, mouseY)) {
                // Switch to the topic scene
                sceneManager.getGameMaster().resetGame();
                Scene topicScene = new TopicScene(sceneManager);
                sceneManager.setCurrentScene(topicScene);
            } 
            // Check if the options button is clicked
            else if (optionsButtonBounds.contains(mouseX, mouseY)) {
                Scene optionsScene = new OptionsScene(sceneManager, sceneManager.getAudioManager());
                sceneManager.setCurrentScene(optionsScene);
            } 
            // Check if the exit button is clicked
            else if (exitButtonBounds.contains(mouseX, mouseY)) {
                Gdx.app.exit();
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
    
    // Dispose resources to avoid memory leaks
    public void dispose() {
        titleFont.dispose();
        buttonFont.dispose();
        gameBackground.dispose();
    }
}
