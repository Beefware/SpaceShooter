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

public class TopicScene extends Scene {
    
    // Topic screen variables
    private SpriteBatch batch;
    private Texture gameBackground;
    private Texture addition;
    private Texture subtraction;
    private Texture multiplication;
    private Texture division;
    private Texture random;
    private BitmapFont titleFont, buttonFont;
    private SceneManager sceneManager;
    private float buttonAnimationTime = 0; // Animation time for button scaling
    private Rectangle additionButtonBounds, subtractionButtonBounds, multiplicationButtonBounds, divisionButtonBounds, randomButtonBounds;
    
    // Constructor to initialize the scene
    public TopicScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;  // Assign the SceneManager passed from GameMaster
        
        batch = new SpriteBatch();

        // Initialize fonts
        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.GOLD);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        // Load background texture
        gameBackground = new Texture("space_black.jpg");

        // Load button textures and set their positions
        addition = new Texture("Addition.png");
        additionButtonBounds = new Rectangle((Gdx.graphics.getWidth() - addition.getWidth()/5)/2, 350, addition.getWidth()/5, addition.getHeight()/5);
        
        subtraction = new Texture("Subtraction.png");
        subtractionButtonBounds = new Rectangle((Gdx.graphics.getWidth() - subtraction.getWidth()/5)/2, 275, subtraction.getWidth()/5, subtraction.getHeight()/5);
        
        multiplication = new Texture("Multiplication.png");
        multiplicationButtonBounds = new Rectangle((Gdx.graphics.getWidth() - multiplication.getWidth()/5)/2, 200, multiplication.getWidth()/5, multiplication.getHeight()/5);
        
        division = new Texture("Division.png");
        divisionButtonBounds = new Rectangle((Gdx.graphics.getWidth() - division.getWidth()/5)/2, 125, division.getWidth()/5, division.getHeight()/5);

        random = new Texture("Random.png");
        randomButtonBounds = new Rectangle((Gdx.graphics.getWidth() - random.getWidth()/5)/2, 50, random.getWidth()/5, random.getHeight()/5);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        
        // Clear the screen with a dark blue color
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        
        // Draw the background
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       
        // Draw the title
        String title = "Select Topic:";
        GlyphLayout titleLayout = new GlyphLayout(titleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() * 0.75f;
        titleFont.draw(batch, title, titleX, titleY);
        
        // Draw buttons
        batch.draw(addition, additionButtonBounds.getX(), additionButtonBounds.getY(),
                    additionButtonBounds.getWidth(), additionButtonBounds.getHeight());

        batch.draw(subtraction, subtractionButtonBounds.getX(), subtractionButtonBounds.getY(), 
                    subtractionButtonBounds.getWidth(), subtractionButtonBounds.getHeight());
        
        batch.draw(multiplication, multiplicationButtonBounds.getX(), multiplicationButtonBounds.getY(), 
                    multiplicationButtonBounds.getWidth(), multiplicationButtonBounds.getHeight());
        
        batch.draw(division, divisionButtonBounds.getX(), divisionButtonBounds.getY(), 
                    divisionButtonBounds.getWidth(), divisionButtonBounds.getHeight());
        
        batch.draw(random, randomButtonBounds.getX(), randomButtonBounds.getY(), 
                    randomButtonBounds.getWidth(), randomButtonBounds.getHeight());
         
        // Button animation (scaling effect)
        float scale = 1 + MathUtils.sin(buttonAnimationTime * 2) * 0.1f;
        buttonFont.getData().setScale(scale);
        
        batch.end();
    }
    
    // Update method to handle button animations and input
    public void update() {
        buttonAnimationTime += Gdx.graphics.getDeltaTime(); // Update animation time
        
        if (Gdx.input.justTouched()) { // Check for mouse click
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convert to screen coordinates
            
            // Check which button was clicked and switch scenes
            if (additionButtonBounds.contains(mouseX, mouseY)) {
                Scene countdownScene = new CountdownScene(sceneManager, 1);
                sceneManager.setCurrentScene(countdownScene);
            } else if (subtractionButtonBounds.contains(mouseX, mouseY)) {
                Scene countdownScene = new CountdownScene(sceneManager, 2);
                sceneManager.setCurrentScene(countdownScene);
            } else if (multiplicationButtonBounds.contains(mouseX, mouseY)) {
                Scene countdownScene = new CountdownScene(sceneManager, 3);
                sceneManager.setCurrentScene(countdownScene);
            } else if (divisionButtonBounds.contains(mouseX, mouseY)) {
                Scene countdownScene = new CountdownScene(sceneManager, 4);
                sceneManager.setCurrentScene(countdownScene);
            } else if (randomButtonBounds.contains(mouseX, mouseY)) {
                Scene countdownScene = new CountdownScene(sceneManager, 5);
                sceneManager.setCurrentScene(countdownScene);
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
