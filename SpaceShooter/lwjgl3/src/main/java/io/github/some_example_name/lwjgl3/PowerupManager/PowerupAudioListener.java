package io.github.some_example_name.lwjgl3.PowerupManager;

import io.github.some_example_name.lwjgl3.AudioManager.AudioManager;

public class PowerupAudioListener implements PowerupCollectionListener {
	private AudioManager audioManager;
	
	public PowerupAudioListener(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
	
	@Override
    public void onPowerupCollected(Powerup powerup) {
        if (powerup instanceof ExtraLife) {
            audioManager.playSoundEffect("life");
        } else if (powerup instanceof PlayerSpeed) {
            audioManager.playSoundEffect("power");
        } else if (powerup instanceof HintPowerup) {
            audioManager.playSoundEffect("power");
        } else if (powerup instanceof TimeFreeze) {
            audioManager.playSoundEffect("power");
        }
    }
}
