package io.github.some_example_name.lwjgl3.AudioManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;


public class SoundEffect implements ISoundEffect {
	private Sound sound;
	private float volume;
	
	// Load sound effect
	public SoundEffect(String filePath) {
		this.sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
	}
	
	@Override
    public void play() {
        sound.play(1.0f); // Default volume
    }

    @Override
    public void play(float volume) {
    	System.out.println("Playing sound at volume: " + volume); // Debug log
    	sound.play(volume);
    }
    
    @Override
    public void setVolume(float volume) {
    	this.volume = Math.max(0, Math.min(volume, 1)); // Clamp volume between 0 and 1
    }
    
    @Override
    public void mute() {
    	this.volume = 0.0f;
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
