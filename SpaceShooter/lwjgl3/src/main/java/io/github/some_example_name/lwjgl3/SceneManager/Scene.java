package io.github.some_example_name.lwjgl3.SceneManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene {
    public abstract void update();
    public abstract void render(SpriteBatch batch);
}
