package io.github.some_example_name.lwjgl3.AudioManager;

// Interface to determine what methods an audio player should implement (music) 
public interface IAudioPlayer {
	void play(); // Play music
	void stop(); // Stop music 
	void setVolume(float volume); // Set music volume
	void mute();
	void fadeOut(float duration); // Fades music out
	void fadeIn(float duraction); // Fades music in
	void dispose(); // Dispose music resources
}
