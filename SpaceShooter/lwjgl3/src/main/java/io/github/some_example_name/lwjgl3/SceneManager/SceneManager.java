package io.github.some_example_name.lwjgl3.SceneManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.GameMaster;

public class SceneManager {
    private Scene currentScene;
    private GameMaster gameMaster;
    private GameScene gameScene; // Store reference to GameScene


    public SceneManager(GameMaster gameMaster) {
        // Start with the title screen or any initial scene
    	
    	this.gameMaster = gameMaster; // Change to the actual GameMaster reference
    }

    public void setCurrentScene(Scene scene) {
    	System.out.println("Setting current scene to: " + scene.getClass().getSimpleName());
        this.currentScene = scene;
    }
    
    public Scene getCurrentScene() {
    	System.out.println("Getting current scene to: " + currentScene.getClass().getSimpleName());
        return currentScene;
    }
    
    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }
    
    public GameScene getGameScene() {
        return gameScene;
    }

    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public void render(SpriteBatch batch) {
        if (currentScene != null) {
            currentScene.render(batch);
        }
    }
    
    public GameMaster getGameMaster() {
        return gameMaster;
    }
}

