package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;




public class TopicScene extends Scene{
	
    // Topic screen variables
	private SpriteBatch batch;
    private Texture gameBackground;
    private BitmapFont titleFont, buttonFont;
    private SceneManager sceneManager;
    private float buttonAnimationTime = 0;
    private Rectangle additionButtonBounds, subtractionButtonBounds, multiplicationButtonBounds, divisionButtonBounds, randomButtonBounds;
    
    
    public TopicScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;  // Assign the SceneManager passed from GameMaster
        
        batch = new SpriteBatch();

        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.GOLD);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        gameBackground = new Texture("space_black.jpg");

        // Button positions
        additionButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 350, 400, 30);
        
        subtractionButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 300, 400, 30);
        
        multiplicationButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 250, 400, 30);
        
        divisionButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 200, 400, 30);
        
        randomButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 150, 400, 30);
    
    }
    
    @Override
    public void render(SpriteBatch batch) {
    	
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        String title = "Space Shooter";
        GlyphLayout titleLayout = new GlyphLayout(titleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() * 0.75f;
        titleFont.draw(batch, title, titleX, titleY);

        // Button animation
        float scale = 1 + MathUtils.sin(buttonAnimationTime * 2) * 0.1f;
        buttonFont.getData().setScale(scale);
        
        batch.end();

        drawButton(additionButtonBounds, "Addition");
        drawButton(subtractionButtonBounds, "Subtraction");
        drawButton(multiplicationButtonBounds, "Multiplication");
        drawButton(divisionButtonBounds, "Division");
        drawButton(randomButtonBounds, "Random");
        
    }
    
    public void update() {
    	buttonAnimationTime += Gdx.graphics.getDeltaTime();
    	
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
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
    
    private void drawButton(Rectangle bounds, String text) {
        buttonFont.getData().setScale(2);
        GlyphLayout layout = new GlyphLayout(buttonFont, text);
        float textX = bounds.x + (bounds.width - layout.width) / 2;
        float textY = bounds.y + (bounds.height + layout.height) / 2;
        batch.begin();
        buttonFont.draw(batch, text, textX, textY);
        batch.end();
    }
    
    
    public void dispose() {
        // Dispose resources
        titleFont.dispose();
        buttonFont.dispose();
        gameBackground.dispose();

    }

}
