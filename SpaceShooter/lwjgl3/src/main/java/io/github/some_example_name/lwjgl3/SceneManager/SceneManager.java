package io.github.some_example_name.lwjgl3.SceneManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.GameMaster;
import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;

public class SceneManager {
    private Scene currentScene;
    private GameMaster gameMaster;
    private GameScene gameScene; // Store reference to GameScene
    private AudioManager audioManager;

    public SceneManager(GameMaster gameMaster, AudioManager audioManager) {
    	this.gameMaster = gameMaster;
    	this.audioManager = audioManager;
    }
    
    public AudioManager getAudioManager() {
    	return audioManager;
    }
    
    // Sets the desired scene
    public void setCurrentScene(Scene scene) {
    	System.out.println("Setting current scene to: " + scene.getClass().getSimpleName());
        this.currentScene = scene;
    }
    // Gets the current scene
    public Scene getCurrentScene() {
    	System.out.println("Getting current scene to: " + currentScene.getClass().getSimpleName());
        return currentScene;
    }
    // Sets the game scene
    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }
    // Gets the game scene
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

