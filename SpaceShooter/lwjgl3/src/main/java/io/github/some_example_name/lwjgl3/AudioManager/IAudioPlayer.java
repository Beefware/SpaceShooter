package io.github.some_example_name.lwjgl3.AudioManager;

/**
 * Interface to define the methods that an audio player should implement for handling music playback.
 */
public interface IAudioPlayer {
	
	/**
	 * Plays the music.
	 */
	void play();

	/**
	 * Stops the music.
	 */
	void stop();

	/**
	 * Sets the volume of the music.
	 * 
	 * @param volume The desired volume level (e.g., between 0.0 and 1.0).
	 */
	void setVolume(float volume);

	/**
	 * Mutes the music.
	 */
	void mute();

	/**
	 * Fades the music out over a specified duration.
	 * 
	 * @param duration The duration (in seconds) over which the music should fade out.
	 */
	void fadeOut(float duration);

	/**
	 * Fades the music in over a specified duration.
	 * 
	 * @param duration The duration (in seconds) over which the music should fade in.
	 */
	void fadeIn(float duration);

	/**
	 * Releases any resources associated with the music.
	 */
	void dispose();
}
