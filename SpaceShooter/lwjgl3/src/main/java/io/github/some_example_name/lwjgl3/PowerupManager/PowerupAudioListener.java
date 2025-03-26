package io.github.some_example_name.lwjgl3.PowerupManager;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;

/**
 * PowerupAudioListener is responsible for playing specific sound effects
 * when different types of power-ups are collected in the game.
 */
public class PowerupAudioListener implements PowerupCollectionListener {
    private AudioManager audioManager; // Reference to the AudioManager for playing sound effects
    
    /**
     * Constructor to initialize the PowerupAudioListener with an AudioManager.
     * 
     * @param audioManager The AudioManager instance used to play sound effects.
     */
    public PowerupAudioListener(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
    
    /**
     * This method is triggered when a power-up is collected. It plays a specific
     * sound effect based on the type of power-up collected.
     * 
     * @param powerup The power-up that was collected.
     */
    @Override
    public void onPowerupCollected(Powerup powerup) {
        if (powerup instanceof ExtraLife) {
            // Play the "life" sound effect for ExtraLife power-up
            audioManager.playSoundEffect("life");
        } else if (powerup instanceof PlayerSpeed) {
            // Play the "power" sound effect for PlayerSpeed power-up
            audioManager.playSoundEffect("power");
        } else if (powerup instanceof HintPowerup) {
            // Play the "power" sound effect for HintPowerup power-up
            audioManager.playSoundEffect("power");
        } else if (powerup instanceof TimeFreeze) {
            // Play the "power" sound effect for TimeFreeze power-up
            audioManager.playSoundEffect("power");
        }
    }
}
