package io.github.some_example_name.lwjgl3.AudioManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;


public class SoundEffect implements ISoundEffect {
	private Sound sound;
	
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
    public void dispose() {
        sound.dispose();
    }
}
