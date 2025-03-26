package io.github.some_example_name.lwjgl3.AudioManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * A class that represents a sound effect in the game.
 * It provides methods to play, adjust volume, mute, and dispose of the sound.
 */
public class SoundEffect implements ISoundEffect {
    private Sound sound; // The sound object representing the audio file
    private float volume; // The volume level of the sound effect
    
    /**
     * Constructor to load a sound effect from the specified file path.
     * 
     * @param filePath The path to the sound file.
     */
    public SoundEffect(String filePath) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
    }
    
    /**
     * Plays the sound effect at the default volume (1.0f).
     */
    @Override
    public void play() {
        sound.play(1.0f); // Default volume
    }

    /**
     * Plays the sound effect at the specified volume.
     * 
     * @param volume The volume level to play the sound at (0.0f to 1.0f).
     */
    @Override
    public void play(float volume) {
        System.out.println("Playing sound at volume: " + volume); // Debug log
        sound.play(volume);
    }
    
    /**
     * Sets the volume of the sound effect.
     * The volume is clamped between 0.0f (mute) and 1.0f (maximum).
     * 
     * @param volume The desired volume level.
     */
    @Override
    public void setVolume(float volume) {
        this.volume = Math.max(0, Math.min(volume, 1)); // Clamp volume between 0 and 1
    }
    
    /**
     * Mutes the sound effect by setting the volume to 0.0f.
     */
    @Override
    public void mute() {
        this.volume = 0.0f;
    }

    /**
     * Disposes of the sound effect to free up resources.
     * This should be called when the sound effect is no longer needed.
     */
    @Override
    public void dispose() {
        sound.dispose();
    }
}
