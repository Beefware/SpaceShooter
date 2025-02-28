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

public class CountdownScene extends Scene {
	private Texture gameBackground;
    private float countdownTime = 3;
    private BitmapFont timerFont;
    private SpriteBatch batch;
    private SceneManager sceneManager;

    public CountdownScene(SceneManager sceneManager) {
    	this.sceneManager = sceneManager;
    	
        timerFont = new BitmapFont();
        timerFont.getData().setScale(3);
        timerFont.setColor(Color.RED);
        batch = new SpriteBatch();
        
        gameBackground = new Texture("space_black.jpg");
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        batch.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        String timerText = String.valueOf((int) Math.ceil(countdownTime));
        GlyphLayout timerLayout = new GlyphLayout(timerFont, timerText);
        float timerX = (Gdx.graphics.getWidth() - timerLayout.width) / 2;
        float timerY = Gdx.graphics.getHeight() / 2;
        timerFont.draw(batch, timerText, timerX, timerY);
        
        batch.end();
    }

    @Override
    public void update() {
        countdownTime -= Gdx.graphics.getDeltaTime();
        if (countdownTime <= 0) {
            // Transition to the game scene
            Scene gameScene = new GameScene(sceneManager);
            sceneManager.setCurrentScene(gameScene);
        }
    }

    public void dispose() {
        timerFont.dispose();
        batch.dispose();
    }
}
