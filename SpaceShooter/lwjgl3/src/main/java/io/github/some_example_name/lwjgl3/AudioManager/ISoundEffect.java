package io.github.some_example_name.lwjgl3.AudioManager;

/**
 * Interface to define the methods that a sound effect player should implement.
 */
public interface ISoundEffect {
    
    /**
     * Plays the sound effect with the default volume.
     */
    void play();

    /**
     * Plays the sound effect with a specified volume.
     * 
     * @param volume The volume level to play the sound effect at (e.g., 0.0 for mute, 1.0 for full volume).
     */
    void play(float volume);

    /**
     * Sets the volume for the sound effect.
     * 
     * @param volume The volume level to set (e.g., 0.0 for mute, 1.0 for full volume).
     */
    void setVolume(float volume);

    /**
     * Mutes the sound effect.
     */
    void mute();

    /**
     * Releases any resources associated with the sound effect.
     */
    void dispose();
}
