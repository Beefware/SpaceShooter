package io.github.some_example_name.lwjgl3.AudioManager;

// Interface to determine what methods a sound effect player should implement
public interface ISoundEffect {
	void play();
    void play(float volume);
    void setVolume(float volume);
    void mute();
    void dispose();
}
