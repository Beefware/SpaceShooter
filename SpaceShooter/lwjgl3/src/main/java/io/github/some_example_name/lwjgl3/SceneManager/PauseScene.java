package io.github.some_example_name.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import io.github.some_example_name.lwjgl3.PowerupManager.PowerupManager;

public class PauseScene extends Scene{

	private SpriteBatch batch;
    private Texture gameBackground;
    private BitmapFont titleFont, buttonFont;
    private Rectangle resumeButtonBounds, quitButtonBounds;
    private float buttonAnimationTime = 0;
    private SceneManager sceneManager;
    
    public PauseScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        batch = new SpriteBatch();

        titleFont = new BitmapFont();
        titleFont.getData().setScale(3);
        titleFont.setColor(Color.WHITE);

        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2);
        buttonFont.setColor(Color.WHITE);

        gameBackground = new Texture("space_black.jpg");

        resumeButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 200, 400, 80);
        quitButtonBounds = new Rectangle(Gdx.graphics.getWidth() / 2 - 200, 100, 400, 80);
    }
    
    @Override
    public void render(SpriteBatch batch) {
    	
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        String title = "Game Paused";
        GlyphLayout titleLayout = new GlyphLayout(titleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() * 0.75f;
        titleFont.draw(batch, title, titleX, titleY);

        // Button animation
        float scale = 1 + MathUtils.sin(buttonAnimationTime * 2) * 0.1f;
        buttonFont.getData().setScale(scale);
        
        batch.end();

        drawButton(resumeButtonBounds, "Resume");
        drawButton(quitButtonBounds, "Quit");
        
    }
    
    @Override
    public void update() {
        // Handle button clicks during pause screen
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (resumeButtonBounds.contains(mouseX, mouseY)) {
                // Get the previously saved GameScene
                GameScene gameScene = sceneManager.getGameScene();
                PowerupManager.getInstance().startSpawning();
                // Call the resume method to continue the Game
                gameScene.resumeGame();  
                // Switch back to the GameScene
                sceneManager.setCurrentScene(gameScene);  
                
            	
            } else if (quitButtonBounds.contains(mouseX, mouseY)) {
                // Quit to title
            	PowerupManager.getInstance().stopSpawning();
                Scene titleScene = new TitleScreen(sceneManager);
                sceneManager.setCurrentScene(titleScene);
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
