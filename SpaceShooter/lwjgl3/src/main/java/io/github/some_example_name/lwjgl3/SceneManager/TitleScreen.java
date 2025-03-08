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




public class TitleScreen extends Scene{
	
    // Title screen variables
	private SpriteBatch batch;
    private Texture gameBackground;
    private BitmapFont titleFont, buttonFont;
    private Rectangle startButtonBounds, optionsButtonBounds, exitButtonBounds;
    private float buttonAnimationTime = 0;
    private SceneManager sceneManager;
    
    public TitleScreen(SceneManager sceneManager) {
        this.sceneManager = sceneManager;  
        
        batch = new SpriteBatch();

        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.GOLD);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        gameBackground = new Texture("space_black.jpg");

        // Button positions
        startButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 200, 400, 80);
        optionsButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 150, 400, 80);
        exitButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 100, 400, 80);
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

        drawButton(startButtonBounds, "Start Game");
        //drawButton(optionsButtonBounds, "Options");
        drawButton(exitButtonBounds, "Exit");
        
    }
    
    public void update() {
    	buttonAnimationTime += Gdx.graphics.getDeltaTime();
    	
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (startButtonBounds.contains(mouseX, mouseY)) {
                // Switch to the topic scene
            	sceneManager.getGameMaster().resetGame();
                Scene topicScene = new TopicScene(sceneManager);
                sceneManager.setCurrentScene(topicScene);
            } else if (optionsButtonBounds.contains(mouseX, mouseY)) {
                Scene optionsScene = new OptionsScene(sceneManager);
                sceneManager.setCurrentScene(optionsScene);
            } else if (exitButtonBounds.contains(mouseX, mouseY)) {
                Gdx.app.exit();
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
